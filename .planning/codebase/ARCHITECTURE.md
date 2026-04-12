# Architecture

**Analysis Date:** 2026-04-12

## Pattern Overview

**Overall:** Monorepo containing independent Android application modules, each following the single-activity Compose-first architecture pattern. This is NOT a multi-module Gradle project -- each app (`news`, `jukebox`) is a fully self-contained Gradle project with its own `settings.gradle.kts`, version catalog, and build pipeline.

**Key Characteristics:**
- Single-Activity architecture: Each app has exactly one `ComponentActivity` subclass as the entry point
- Jetpack Compose UI: All UI is built with `@Composable` functions; no XML layouts
- Material 3 theming: Each app defines its own `{AppName}Theme` composable with dark/light/dynamic color support
- No ViewModel, no Repository, no DI: Business logic and data are inlined in the Activity or as top-level composables
- No navigation framework: Single-screen apps with no `NavController` or navigation graph
- No network layer: All data is hardcoded or bundled as local resources (sounds, images)
- Version catalogs: Dependencies managed via `gradle/libs.versions.toml` per project

## Layers

**Presentation Layer (UI):**
- Purpose: Render composable UI, handle user interactions
- Location: `{app}/app/src/main/java/com/maxkeenti/{appname}/`
- Contains: `MainActivity.kt`, top-level composables (`News.kt`, `JukeboxGrid`), and `ui/theme/` sub-package
- Depends on: Android framework (`MediaPlayer`), Jetpack Compose libraries, local resources (`R.drawable.*`, `R.raw.*`)
- Used by: Android framework (launches the Activity)

**Resource Layer:**
- Purpose: Static assets (images, sounds, strings, themes)
- Location: `{app}/app/src/main/res/`
- Contains: `drawable/` (PNG/JPEG images), `raw/` (MP3 audio), `values/` (strings, colors, themes), `mipmap-*/` (launcher icons)
- Depends on: Nothing
- Used by: Presentation layer via `R` class resource references

**Theme Layer:**
- Purpose: Define Material 3 color scheme and typography
- Location: `{app}/app/src/main/java/com/maxkeenti/{appname}/ui/theme/`
- Contains: `Color.kt`, `Type.kt`, `Theme.kt`
- Depends on: `androidx.compose.material3`, `androidx.compose.ui`
- Used by: Presentation layer (wrapped around composable content)

**Configuration Layer:**
- Purpose: Build configuration and dependency management
- Location: `{app}/build.gradle.kts`, `{app}/app/build.gradle.kts`, `{app}/settings.gradle.kts`, `{app}/gradle/libs.versions.toml`
- Contains: Gradle build scripts, version catalog, wrapper properties
- Depends on: Gradle plugins (AGP, Kotlin Compose compiler)
- Used by: Build system

## Data Flow

**News App Flow:**

1. Android framework launches `MainActivity` (entry point in `news/app/src/main/AndroidManifest.xml`)
2. `MainActivity.onCreate()` calls `setContent { UIMainActivity() }`
3. `UIMainActivity()` composable calls `News(title, author, date, content)` with hardcoded German-language string literals
4. `News()` composable renders a `Card` with title row, author/date column, and content column

**Jukebox App Flow:**

1. Android framework launches `MainActivity` (entry point in `jukebox/app/src/main/AndroidManifest.xml`)
2. `MainActivity.onCreate()` constructs a `List<JukeboxItem>` with hardcoded data referencing `R.drawable.*` and `R.raw.*` resources
3. `setContent { JukeboxTheme { Scaffold { JukeboxGrid(items) } } }`
4. `JukeboxGrid()` renders a `LazyVerticalGrid` with 3 columns of image+label items
5. User taps an image -> `MediaPlayer.create(context, item.soundRes)` plays the MP3 and releases on completion

**State Management:**
- No state management framework (no ViewModel, no StateFlow, no LiveData)
- Local state only: No `remember` or `mutableStateOf` usage observed in current composables
- All data is hardcoded inline in Activity or composable parameters
- `MediaPlayer` instances are created and released per tap (no shared state)

## Key Abstractions

**JukeboxItem (data class):**
- Purpose: Represents a single sound board entry with a label, image resource, and sound resource
- Examples: `jukebox/app/src/main/java/com/maxkeenti/jukebox/MainActivity.kt` (line 25)
- Pattern: Simple `data class` with `String`, `Int` (resource ID) properties
- Note: Defined in the same file as `MainActivity`, not in a separate model file

**{AppName}Theme (composable):**
- Purpose: Wraps composable content with Material 3 theming (light/dark/dynamic color)
- Examples: `news/app/src/main/java/com/maxkeenti/news/ui/theme/Theme.kt`, `jukebox/app/src/main/java/com/maxkeenti/jukebox/ui/theme/Theme.kt`
- Pattern: Standard Android Studio Compose template with `darkColorScheme`/`lightColorScheme` and conditional dynamic color support (API 31+)

**Composable UI functions:**
- Purpose: Declarative UI rendering
- Examples: `News()` in `news/app/src/main/java/com/maxkeenti/news/News.kt`, `JukeboxGrid()` in `jukebox/app/src/main/java/com/maxkeenti/jukebox/MainActivity.kt`
- Pattern: `@Composable` functions taking data parameters, rendering Material 3 components

## Entry Points

**News App - `MainActivity`:**
- Location: `news/app/src/main/java/com/maxkeenti/news/MainActivity.kt`
- Triggers: Android launcher (MAIN/LAUNCHER intent filter in `AndroidManifest.xml`)
- Responsibilities: Calls `enableEdgeToEdge()`, sets content to `UIMainActivity()` composable, provides preview via `GreetingPreview()`

**Jukebox App - `MainActivity`:**
- Location: `jukebox/app/src/main/java/com/maxkeenti/jukebox/MainActivity.kt`
- Triggers: Android launcher (MAIN/LAUNCHER intent filter in `AndroidManifest.xml`)
- Responsibilities: Constructs `JukeboxItem` list, calls `enableEdgeToEdge()`, sets content with `JukeboxTheme` + `Scaffold` + `JukeboxGrid`

## Error Handling

**Strategy:** Minimal / Not implemented

**Patterns:**
- No try/catch blocks observed in any source file
- `MediaPlayer` usage in Jukebox has no error handling for missing resources or playback failures
- No error states in composables (no loading, error, or empty states)

## Cross-Cutting Concerns

**Logging:** No logging framework or `Log`/`Timber` calls observed

**Validation:** No input validation; all data is hardcoded

**Authentication:** None -- single-user offline apps with no backend

**Navigation:** None -- each app is a single screen with no `NavController`

**Dependency Injection:** None -- no Hilt, Koin, or manual DI; all dependencies are created inline

**Build Configuration:**
- Both apps target `compileSdk` 36 / `minSdk` 36 / `targetSdk` 36 (Android 16+ only)
- Both use Kotlin 2.2.10 with Compose compiler plugin
- Both use AGP 9.1.0
- Both use Compose BOM 2024.09.00
- Java 11 source/target compatibility
- ProGuard/R8 disabled for release builds (`isMinifyEnabled = false`)
- Both use Gradle 9.3.1 wrapper

---

*Architecture analysis: 2026-04-12*