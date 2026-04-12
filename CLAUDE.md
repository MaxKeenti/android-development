<!-- GSD:project-start source:PROJECT.md -->
## Project

**Android Development Learning Journey**

A personal reference repository documenting a hands-on Android development learning path. Each project is assigned by a teacher/mentor and gets its own folder under a relevant topic — the code tells the story, and notes explain the concepts alongside it. This is not a course or a product; it's a living record of learning in progress.

**Core Value:** Every task I'm given has a home here — with working code and a note that explains what I learned from it.

### Constraints

- **Tech stack**: Kotlin + Jetpack Compose — all Android work uses this stack (established by existing projects)
- **Scope**: Greenfield additions only when a new task is assigned; no unsolicited refactors of existing apps
- **Structure**: Task folders live inside topic folders; flat structure at the root should be avoided
<!-- GSD:project-end -->

<!-- GSD:stack-start source:codebase/STACK.md -->
## Technology Stack

## Languages
- Kotlin 2.2.10 - All application source code, Compose UI, and tests
- Kotlin DSL (Gradle Kotlin Script) - Build configuration (`build.gradle.kts`, `settings.gradle.kts`)
- TOML - Version catalog (`libs.versions.toml`)
- XML - Android manifests, resources, themes
## Runtime
- Android SDK (compileSdk 36, minSdk 36, targetSdk 36)
- Java compatibility: source/target Java 11
- Gradle 9.3.1 (via wrapper)
- Lockfile: Not present (Gradle version catalog used instead)
- Version catalog: `gradle/libs.versions.toml` per project
## Frameworks
- Android SDK 36 (API 36, release with minorApiLevel 1) - Target platform
- Jetpack Compose (BOM 2024.09.00) - Declarative UI framework
- Material Design 3 (`androidx.compose.material3`) - UI component library
- AndroidX Lifecycle Runtime KTX - Lifecycle-aware components
- AndroidX Activity Compose - Compose integration with activities
- JUnit 4.13.2 - Unit testing framework
- AndroidX Test JUnit 1.1.5 (news) / 1.3.0 (jukebox) - AndroidX test extensions
- Espresso 3.5.1 (news) / 3.7.0 (jukebox) - Android UI instrumented testing
- Compose UI Test JUnit4 - Compose-specific UI testing
- Android Gradle Plugin (AGP) 9.1.0 - Build system plugin
- Kotlin Compose Compiler Plugin 2.2.10 - Compose compiler integration
- Gradle Kotlin Script - Build file DSL
- Foojay Resolver Convention Plugin 1.0.0 - JDK auto-provisioning
## Key Dependencies
- `androidx.compose.ui:ui` - Core Compose UI primitives (layouts, modifiers, input)
- `androidx.compose.material3:material3` - Material Design 3 components
- `androidx.compose.ui:ui-graphics` - Compose graphics APIs
- `androidx.activity:activity-compose` 1.8.0 (news) / 1.13.0 (jukebox) - Compose Activity integration
- `androidx.lifecycle:lifecycle-runtime-ktx` 2.6.1 (news) / 2.10.0 (jukebox) - Lifecycle scope support
- `androidx.core:core-ktx` 1.10.1 (news) / 1.18.0 (jukebox) - Kotlin extensions for Android platform APIs
- `android.media.MediaPlayer` - Audio playback (jukebox app, Android platform API, not a separate dependency)
- Compose UI Tooling (`ui-tooling`, `ui-tooling-preview`) - Debug-only design previews
## Configuration
- No `.env` files detected
- No environment variables required for build
- `local.properties` is gitignored (typically holds SDK path)
- `news/build.gradle.kts` - Root-level build config (plugin declarations only)
- `news/app/build.gradle.kts` - App module build config (SDK versions, dependencies, Compose enabled)
- `news/settings.gradle.kts` - Project settings, repository configuration, root project name
- `news/gradle/libs.versions.toml` - Centralized version catalog
- `jukebox/build.gradle.kts` - Root-level build config (plugin declarations only)
- `jukebox/app/build.gradle.kts` - App module build config
- `jukebox/settings.gradle.kts` - Project settings
- `jukebox/gradle/libs.versions.toml` - Centralized version catalog
- Google (Android, Google, and AndroidX groups)
- Maven Central (all other dependencies)
- Gradle Plugin Portal (for plugin resolution)
## Platform Requirements
- Android Studio (Meerkat or later, supporting AGP 9.1.0 and Kotlin 2.2.10)
- JDK 11+ (compileOptions set to Java 11 compatibility)
- Android SDK with API 36 platform
- Gradle 9.3.1 (wrapper provided)
- Android devices running API 36+ (minSdk = 36)
- No network dependencies required at runtime (jukebox uses local resources; news has hardcoded data)
- Both apps are fully self-contained with no backend dependency
## Project Structure
<!-- GSD:stack-end -->

<!-- GSD:conventions-start source:CONVENTIONS.md -->
## Conventions

## Naming Patterns
- Kotlin source files use PascalCase matching the class or Composable function name: `MainActivity.kt`, `News.kt`
- Theme files use PascalCase: `Color.kt`, `Type.kt`, `Theme.kt`
- Test files use PascalCase matching the class under test: `ExampleUnitTest.kt`, `ExampleInstrumentedTest.kt`
- Resource directories follow Android convention: `drawable/`, `values/`, `raw/`, `mipmap-*dpi/`
- Composable functions use PascalCase: `News()`, `UIMainActivity()`, `JukeboxGrid()`
- Lifecycle overrides use camelCase: `onCreate()`
- Test functions use snake_case: `addition_isCorrect()`, `useAppContext()`
- UI entry Composables in `MainActivity.kt` are prefixed with `UI`: `UIMainActivity()`
- Top-level Compose theme values use PascalCase: `Purple80`, `DarkColorScheme`, `Typography`
- Local variables use camelCase: `items`, `colorScheme`, `context`
- Data class properties use camelCase: `label`, `imageRes`, `soundRes`
- Data classes use PascalCase: `JukeboxItem`
- Theme wrapper functions use PascalCase suffixed with "Theme": `NewsTheme()`, `JukeboxTheme()`
- Color scheme variables use PascalCase: `DarkColorScheme`, `LightColorScheme`
## Code Style
- No `.editorconfig`, `ktlint`, or `detekt` configuration detected
- Kotlin code style is set to `official` in `gradle.properties` (`kotlin.code.style=official`)
- Indentation: 4 spaces (Android Studio default)
- Trailing commas: not used consistently
- Max line length: not enforced
- No static analysis tools configured (no ktlint, detekt, or Android Lint custom rules)
- Default Android Studio lint checks only
- String literals in UI code are hardcoded (e.g., `"Heute ist Freitag"`, `"Ich"`) rather than using string resources
- Composable functions with parameters place each parameter on a separate line when the signature is long
- Modifier parameters use default value `Modifier`: `modifier: Modifier = Modifier`
- Single-expression function bodies are used where appropriate
- Comments use `//` for inline comments and `/* */` for block comments (block comments are leftover template comments, not authored)
## Import Organization
- Imports use `.*` wildcard for Compose sub-packages (e.g., `androidx.compose.foundation.layout.*`)
- No import ordering enforcement tool detected
- Unused imports appear to be cleaned (Android Studio default behavior)
- No custom path aliases or module aliases detected
## Error Handling
- No explicit error handling patterns observed in current code
- `MediaPlayer` in `jukebox/app/src/main/java/com/maxkeenti/jukebox/MainActivity.kt` uses `setOnCompletionListener { it.release() }` for resource cleanup but has no error handling for playback failures
- No try/catch blocks anywhere in the codebase
- No custom exception classes defined
- Wrap `MediaPlayer.create()` calls in try/catch to handle resource loading failures
- Use Compose `remember` with `DisposableEffect` for MediaPlayer lifecycle management rather than inline creation
- Add error states to Composable functions where operations can fail
## Logging
- No log statements anywhere in the codebase
- Android `Log` class not used
## Comments
- Current code has minimal comments -- only template-generated block comments in test files and theme files
- One inline comment in `jukebox/app/src/main/java/com/maxkeenti/jukebox/MainActivity.kt`: `// Data class to keep your items organized` and `// Sample Data - Replace with your actual resource IDs`
- Template TODO comments are present in theme files (commented-out color and typography overrides)
- No KDoc comments on any public functions or classes
- Test files have template-generated class-level KDoc referencing Android testing documentation
## Function Design
- Composable functions are short (5-25 lines)
- `onCreate()` in both projects delegates immediately to `setContent {}` -- no business logic in Activities
- Business logic and UI are mixed within Composable functions (no ViewModel separation)
- Composable functions take explicit data parameters: `News(title: String, author: String, date: String, content: String)`
- Data classes group related parameters: `JukeboxItem(val label: String, val imageRes: Int, val soundRes: Int)`
- Modifier parameter always last with default value: `modifier: Modifier = Modifier`
- Composable functions return `Unit` (standard Compose pattern)
- No explicit return type annotations on Composable functions
## Module Design
- Each file exports a single primary Composable or class (no barrel/index files)
- Theme files export top-level values: `val Purple80`, `val Typography`, `fun NewsTheme()`
- No barrel files detected
- Package structure is flat within each module (no sub-packages beyond `ui.theme`)
## Architecture Patterns
- Single Activity per app (`MainActivity`)
- Activity delegates to Compose immediately via `setContent {}`
- `enableEdgeToEdge()` called in `onCreate()` before `setContent`
- Theme wrapper pattern: each app has a `[AppName]Theme` Composable that wraps `MaterialTheme`
- Theme supports dynamic color (Material You) with fallback to custom color schemes
- Preview functions annotated with `@Preview(showBackground = true)` and wrapped in theme
- Data classes used for model objects: `data class JukeboxItem`
- Resource references by integer ID: `R.drawable.*`, `R.raw.*`
- No repository pattern, no dependency injection, no ViewModels
- String resources defined in `res/values/strings.xml` for app name only
- Other strings hardcoded in Kotlin code
- Images stored as PNG/JPEG in `res/drawable/`
- Audio files stored as MP3 in `res/raw/`
- Theme colors defined in `ui/theme/Color.kt` as top-level `val` properties
<!-- GSD:conventions-end -->

<!-- GSD:architecture-start source:ARCHITECTURE.md -->
## Architecture

## Pattern Overview
- Single-Activity architecture: Each app has exactly one `ComponentActivity` subclass as the entry point
- Jetpack Compose UI: All UI is built with `@Composable` functions; no XML layouts
- Material 3 theming: Each app defines its own `{AppName}Theme` composable with dark/light/dynamic color support
- No ViewModel, no Repository, no DI: Business logic and data are inlined in the Activity or as top-level composables
- No navigation framework: Single-screen apps with no `NavController` or navigation graph
- No network layer: All data is hardcoded or bundled as local resources (sounds, images)
- Version catalogs: Dependencies managed via `gradle/libs.versions.toml` per project
## Layers
- Purpose: Render composable UI, handle user interactions
- Location: `{app}/app/src/main/java/com/maxkeenti/{appname}/`
- Contains: `MainActivity.kt`, top-level composables (`News.kt`, `JukeboxGrid`), and `ui/theme/` sub-package
- Depends on: Android framework (`MediaPlayer`), Jetpack Compose libraries, local resources (`R.drawable.*`, `R.raw.*`)
- Used by: Android framework (launches the Activity)
- Purpose: Static assets (images, sounds, strings, themes)
- Location: `{app}/app/src/main/res/`
- Contains: `drawable/` (PNG/JPEG images), `raw/` (MP3 audio), `values/` (strings, colors, themes), `mipmap-*/` (launcher icons)
- Depends on: Nothing
- Used by: Presentation layer via `R` class resource references
- Purpose: Define Material 3 color scheme and typography
- Location: `{app}/app/src/main/java/com/maxkeenti/{appname}/ui/theme/`
- Contains: `Color.kt`, `Type.kt`, `Theme.kt`
- Depends on: `androidx.compose.material3`, `androidx.compose.ui`
- Used by: Presentation layer (wrapped around composable content)
- Purpose: Build configuration and dependency management
- Location: `{app}/build.gradle.kts`, `{app}/app/build.gradle.kts`, `{app}/settings.gradle.kts`, `{app}/gradle/libs.versions.toml`
- Contains: Gradle build scripts, version catalog, wrapper properties
- Depends on: Gradle plugins (AGP, Kotlin Compose compiler)
- Used by: Build system
## Data Flow
- No state management framework (no ViewModel, no StateFlow, no LiveData)
- Local state only: No `remember` or `mutableStateOf` usage observed in current composables
- All data is hardcoded inline in Activity or composable parameters
- `MediaPlayer` instances are created and released per tap (no shared state)
## Key Abstractions
- Purpose: Represents a single sound board entry with a label, image resource, and sound resource
- Examples: `jukebox/app/src/main/java/com/maxkeenti/jukebox/MainActivity.kt` (line 25)
- Pattern: Simple `data class` with `String`, `Int` (resource ID) properties
- Note: Defined in the same file as `MainActivity`, not in a separate model file
- Purpose: Wraps composable content with Material 3 theming (light/dark/dynamic color)
- Examples: `news/app/src/main/java/com/maxkeenti/news/ui/theme/Theme.kt`, `jukebox/app/src/main/java/com/maxkeenti/jukebox/ui/theme/Theme.kt`
- Pattern: Standard Android Studio Compose template with `darkColorScheme`/`lightColorScheme` and conditional dynamic color support (API 31+)
- Purpose: Declarative UI rendering
- Examples: `News()` in `news/app/src/main/java/com/maxkeenti/news/News.kt`, `JukeboxGrid()` in `jukebox/app/src/main/java/com/maxkeenti/jukebox/MainActivity.kt`
- Pattern: `@Composable` functions taking data parameters, rendering Material 3 components
## Entry Points
- Location: `news/app/src/main/java/com/maxkeenti/news/MainActivity.kt`
- Triggers: Android launcher (MAIN/LAUNCHER intent filter in `AndroidManifest.xml`)
- Responsibilities: Calls `enableEdgeToEdge()`, sets content to `UIMainActivity()` composable, provides preview via `GreetingPreview()`
- Location: `jukebox/app/src/main/java/com/maxkeenti/jukebox/MainActivity.kt`
- Triggers: Android launcher (MAIN/LAUNCHER intent filter in `AndroidManifest.xml`)
- Responsibilities: Constructs `JukeboxItem` list, calls `enableEdgeToEdge()`, sets content with `JukeboxTheme` + `Scaffold` + `JukeboxGrid`
## Error Handling
- No try/catch blocks observed in any source file
- `MediaPlayer` usage in Jukebox has no error handling for missing resources or playback failures
- No error states in composables (no loading, error, or empty states)
## Cross-Cutting Concerns
- Both apps target `compileSdk` 36 / `minSdk` 36 / `targetSdk` 36 (Android 16+ only)
- Both use Kotlin 2.2.10 with Compose compiler plugin
- Both use AGP 9.1.0
- Both use Compose BOM 2024.09.00
- Java 11 source/target compatibility
- ProGuard/R8 disabled for release builds (`isMinifyEnabled = false`)
- Both use Gradle 9.3.1 wrapper
<!-- GSD:architecture-end -->

<!-- GSD:skills-start source:skills/ -->
## Project Skills

No project skills found. Add skills to any of: `.claude/skills/`, `.agents/skills/`, `.cursor/skills/`, or `.github/skills/` with a `SKILL.md` index file.
<!-- GSD:skills-end -->

<!-- GSD:workflow-start source:GSD defaults -->
## GSD Workflow Enforcement

Before using Edit, Write, or other file-changing tools, start work through a GSD command so planning artifacts and execution context stay in sync.

Use these entry points:
- `/gsd-quick` for small fixes, doc updates, and ad-hoc tasks
- `/gsd-debug` for investigation and bug fixing
- `/gsd-execute-phase` for planned phase work

Do not make direct repo edits outside a GSD workflow unless the user explicitly asks to bypass it.
<!-- GSD:workflow-end -->



<!-- GSD:profile-start -->
## Developer Profile

> Profile not yet configured. Run `/gsd-profile-user` to generate your developer profile.
> This section is managed by `generate-claude-profile` -- do not edit manually.
<!-- GSD:profile-end -->
