# Codebase Concerns

**Analysis Date:** 2026-04-12

## Tech Debt

**Placeholder Test Files:**
- Issue: Both applications contain auto-generated placeholder test files that don't test actual functionality
- Files: 
  - `news/app/src/test/java/com/maxkeenti/news/ExampleUnitTest.kt`
  - `news/app/src/androidTest/java/com/maxkeenti/news/ExampleInstrumentedTest.kt`
  - `jukebox/app/src/test/java/com/maxkeenti/jukebox/ExampleUnitTest.kt`
  - `jukebox/app/src/androidTest/java/com/maxkeenti/jukebox/ExampleInstrumentedTest.kt`
- Impact: Zero actual test coverage of business logic. Any refactoring or feature additions have no safety net. UI behavior, composable state, and media playback are untested.
- Fix approach: Replace placeholder tests with meaningful unit tests for UI logic, composable state management, and integration tests for Android-specific features like MediaPlayer lifecycle in Jukebox app.

**Release Build Not Minified:**
- Issue: ProGuard minification disabled in release builds (`isMinifyEnabled = false`)
- Files: `news/app/build.gradle.kts` (line 26), `jukebox/app/build.gradle.kts` (line 26)
- Impact: Production APKs will be significantly larger (~40-50% larger), slower to load, consume more device memory, and APK size may be a blocker for app store optimization. Also loses obfuscation benefits.
- Fix approach: Enable minification (`isMinifyEnabled = true`), run builds through minification pipeline, and test thoroughly. Add configuration to exclude specific classes if needed (e.g., Compose-related code).

**Unimplemented Data Extraction Rules:**
- Issue: Data extraction and backup rules contain only TODO comments and default empty configurations
- Files: 
  - `jukebox/app/src/main/res/xml/data_extraction_rules.xml`
  - `jukebox/app/src/main/res/xml/backup_rules.xml`
  - `news/app/src/main/res/xml/data_extraction_rules.xml`
  - `news/app/src/main/res/xml/backup_rules.xml`
- Impact: Android 12+ devices will use default cloud backup behavior. Any future app data (preferences, cache, databases) will be automatically backed up without explicit control, potentially exposing sensitive data in cloud backups. User device transfers may not behave as intended.
- Fix approach: Review what data each app needs to backup (currently minimal), explicitly exclude or include data domains in backup rules, document the decision.

## Security Considerations

**Hardcoded UI Text in Preview/Main:**
- Issue: German language text and hardcoded values embedded directly in code rather than externalized to resources
- Files: `news/app/src/main/java/com/maxkeenti/news/MainActivity.kt` (lines 24-27, 35-38)
- Current mitigation: None - text is hardcoded
- Recommendations: Move all user-facing text to `res/values/strings.xml`. This enables localization, easier maintenance, and separation of concerns.

**Missing AllowBackup Configuration Hardening:**
- Issue: Manifest files set `android:allowBackup="true"` with no granular control
- Files: `news/app/src/main/AndroidManifest.xml` (line 6), `jukebox/app/src/main/AndroidManifest.xml` (line 6)
- Risk: Any future sensitive data (user preferences, API tokens, cache) will be automatically backed up to Google Cloud unless explicitly blocked in backup rules (which are currently empty/unimplemented).
- Recommendations: Either set `android:allowBackup="false"` if no backup is needed, or properly configure backup rules to exclude sensitive data domains.

**No Permissions Management:**
- Issue: Jukebox app uses MediaPlayer without declaring required Android permissions in manifest
- Files: `jukebox/app/src/main/AndroidManifest.xml`
- Current mitigation: App may have implicit permissions or relies on defaults
- Recommendations: Declare required permissions explicitly (`INTERNET`, `WRITE_EXTERNAL_STORAGE` if loading from files, etc.). Implement runtime permission requests for Android 6.0+. Note: reading raw resources doesn't require permissions, but good practice to be explicit.

## Performance Bottlenecks

**Unmanaged MediaPlayer Lifecycle in Jukebox:**
- Problem: MediaPlayer instances created on every click with completion listener, but no pause/stop capability between selections
- Files: `jukebox/app/src/main/java/com/maxkeenti/jukebox/MainActivity.kt` (lines 77-82)
- Cause: Each click creates a new MediaPlayer. If user rapidly clicks multiple items, multiple audio streams play simultaneously. No mechanism to stop previous audio before starting new audio. Memory leaks possible if click happens during media playback.
- Improvement path: Implement a singleton MediaPlayer manager or use ExoPlayer. Add logic to stop current playback before starting new playback. Implement proper lifecycle management to handle pause/resume when app goes to background.

**No Resource Management for Large Image Assets:**
- Problem: Jukebox displays 9 PNG/JPEG images in a 3-column grid simultaneously without image scaling or memory optimization
- Files: `jukebox/app/src/main/res/drawable/` contains `gatito.jpeg` (101KB) and other large PNGs (5-14KB each)
- Cause: Compose/Jetpack ImagePainter may load full resolution images into memory. No explicit sampling, caching, or lazy loading.
- Improvement path: Use Coil or Glide for image loading with automatic downsampling. Implement lazy loading for grid items. Consider WebP format for images instead of JPEG/PNG for better compression.

**UI Text Not Using Material Design 3 Semantics:**
- Problem: Text rendering in News.kt (lines 33, 37-38, 41) uses default Text composables without semantic size classes or responsive typography
- Files: `news/app/src/main/java/com/maxkeenti/news/News.kt`
- Cause: Hard-coded `Text()` calls without headline/body/label styles from Typography. May cause readability issues on large/small screens.
- Improvement path: Use `Text(title, style = MaterialTheme.typography.headlineSmall)` pattern. Define app-wide text styles in `Type.kt` and reuse them consistently.

## Fragile Areas

**Jukebox Data Model Mismatch:**
- Files: `jukebox/app/src/main/java/com/maxkeenti/jukebox/MainActivity.kt` (lines 25, 33-43)
- Why fragile: Hard-coded list of `JukeboxItem` data mixed with UI composition in Activity. Adding new items requires modifying Activity code. If a drawable or sound resource is missing, app will crash at runtime with ResourceNotFoundException.
- Safe modification: Extract item list to a separate data class/file. Create a dedicated data initialization function. Validate resources exist in tests before referencing them in UI.
- Test coverage: Zero - resources are never validated. UI composition is not tested.

**News Composable Layout Assumes Specific Content:**
- Files: `news/app/src/main/java/com/maxkeenti/news/News.kt`
- Why fragile: Fixed 2-column Row layout (Title, Author+Date | Content) assumes specific content lengths. Long text will overflow or be cut off. No constraints on text length. Layout breaks with RTL languages or long author names.
- Safe modification: Add proper constraints (maxLines, overflow ellipsis). Use fillMaxWidth()/fillMaxHeight() with weight distribution instead of fixed layouts. Test with various content lengths.
- Test coverage: Zero composable layout tests.

**Hard-Coded German Text in News App:**
- Files: `news/app/src/main/java/com/maxkeenti/news/MainActivity.kt` (lines 24-27, 35-38)
- Why fragile: Text is English-German mixed and contains typos ("Lustig" -> should this be a word?). If UI needs to change or localize, multiple files must be updated. No way to swap content without code changes.
- Safe modification: Move to strings.xml resource file. Use string resources exclusively. Set up string-array resources for different languages.
- Test coverage: No content validation tests.

## Scaling Limits

**Single Activity, Single Composable Architecture:**
- Current capacity: Both apps are single-screen, single-activity applications. Fine for current scope.
- Limit: If either app needs multiple screens, navigation, or deeper feature sets, current architecture cannot scale. No navigation framework present (no Navigation Compose dependencies).
- Scaling path: Add Navigation Compose dependency. Refactor Activities to use navigation graph. Create separate composable screens for each feature/flow. Implement proper back stack management.

**No State Management or ViewModel:**
- Current capacity: Apps work with stateless/single-screen composables.
- Limit: If Jukebox needs playlist management, play queue, or playback state, current architecture cannot maintain state across recompositions. If News needs pagination or filtering, no state container exists.
- Scaling path: Add ViewModelScope and Hilt dependencies. Create ViewModels for each screen. Implement state as StateFlow or LiveData. Separate UI logic from composition.

**No Dependency Injection:**
- Current capacity: All dependencies (MediaPlayer, Context) are created inline.
- Limit: Testing UI becomes difficult without ability to inject mock dependencies. If apps grow to have services, repositories, or data sources, no DI framework to manage them.
- Scaling path: Integrate Hilt for dependency injection. Create app-level modules for singletons. Inject dependencies at Activity/ViewModel level.

## Fragile Resource References

**Missing Resource Validation:**
- Issue: Drawable and raw resource references are hard-coded in data model with no build-time or runtime validation
- Files: `jukebox/app/src/main/java/com/maxkeenti/jukebox/MainActivity.kt` (lines 34-42)
- Problem: If a drawable or sound file is missing, the app compiles fine but crashes at runtime with `Resources.NotFoundException`. No lint checks catch this.
- Impact: Deployment risk - resource file rename/deletion silently breaks app.
- Recommendation: Generate resource IDs at compile time from a data file, or implement resource validation tests that check all referenced resources exist.

## Missing Critical Features

**No Audio Pause/Resume in Jukebox:**
- Problem: Users cannot pause playback, adjust volume, or see what's playing. Clicking same item twice starts it twice (two overlapping audio streams).
- Blocks: Can't use app for actual music selection. Must click back button to close and restart.
- Recommendation: Add playback controls (pause, resume, stop), display current playing item, queue management.

**No Content Persistence in News:**
- Problem: App displays hard-coded news item. Cannot fetch, save, or display different news.
- Blocks: Can't use app to display real news data.
- Recommendation: Add API integration or local database to fetch/display real news content.

**No Error Handling or Graceful Degradation:**
- Problem: No try-catch blocks, error boundaries, or user-facing error messages
- Files: `jukebox/app/src/main/java/com/maxkeenti/jukebox/MainActivity.kt` (lines 79-81)
- Impact: If MediaPlayer.create() fails or sound file is corrupted, app crashes silently.
- Recommendation: Wrap audio playback in try-catch. Implement error callbacks. Show user-friendly error dialogs.

## Test Coverage Gaps

**Zero Unit Tests:**
- What's not tested: All Kotlin logic including JukeboxItem data class, News composable rendering, media playback initiation
- Files: All source files under `src/main/`
- Risk: Refactoring Composable UI breaks silently. Adding new items to Jukebox requires manual testing. No regression detection.
- Priority: **High** - At minimum, add unit tests for:
  - JukeboxItem creation and field validation
  - News composable with various content lengths
  - MediaPlayer lifecycle mock tests

**Zero Integration Tests:**
- What's not tested: Activity lifecycle, Compose preview rendering, resource loading
- Files: All Activities
- Risk: Activity creation, theme application, resource loading failures go undetected until manual testing on device.
- Priority: **High** - Add instrumented tests for:
  - Activity onCreate, onDestroy lifecycle
  - Compose preview rendering validation
  - Resource file existence checks

**Zero UI/E2E Tests:**
- What's not tested: User interactions (clicking items), audio playback, layout responsiveness
- Risk: Layout changes break silently. Click handlers fail undetected.
- Priority: **Medium** - Create UI tests for Jukebox grid clicks and News card rendering.

---

*Concerns audit: 2026-04-12*
