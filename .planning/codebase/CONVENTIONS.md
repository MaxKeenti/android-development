# Coding Conventions

**Analysis Date:** 2026-04-12

## Naming Patterns

**Files:**
- Kotlin source files use PascalCase matching the class or Composable function name: `MainActivity.kt`, `News.kt`
- Theme files use PascalCase: `Color.kt`, `Type.kt`, `Theme.kt`
- Test files use PascalCase matching the class under test: `ExampleUnitTest.kt`, `ExampleInstrumentedTest.kt`
- Resource directories follow Android convention: `drawable/`, `values/`, `raw/`, `mipmap-*dpi/`

**Functions:**
- Composable functions use PascalCase: `News()`, `UIMainActivity()`, `JukeboxGrid()`
- Lifecycle overrides use camelCase: `onCreate()`
- Test functions use snake_case: `addition_isCorrect()`, `useAppContext()`
- UI entry Composables in `MainActivity.kt` are prefixed with `UI`: `UIMainActivity()`

**Variables:**
- Top-level Compose theme values use PascalCase: `Purple80`, `DarkColorScheme`, `Typography`
- Local variables use camelCase: `items`, `colorScheme`, `context`
- Data class properties use camelCase: `label`, `imageRes`, `soundRes`

**Types:**
- Data classes use PascalCase: `JukeboxItem`
- Theme wrapper functions use PascalCase suffixed with "Theme": `NewsTheme()`, `JukeboxTheme()`
- Color scheme variables use PascalCase: `DarkColorScheme`, `LightColorScheme`

## Code Style

**Formatting:**
- No `.editorconfig`, `ktlint`, or `detekt` configuration detected
- Kotlin code style is set to `official` in `gradle.properties` (`kotlin.code.style=official`)
- Indentation: 4 spaces (Android Studio default)
- Trailing commas: not used consistently
- Max line length: not enforced

**Linting:**
- No static analysis tools configured (no ktlint, detekt, or Android Lint custom rules)
- Default Android Studio lint checks only

**Key Style Observations:**
- String literals in UI code are hardcoded (e.g., `"Heute ist Freitag"`, `"Ich"`) rather than using string resources
- Composable functions with parameters place each parameter on a separate line when the signature is long
- Modifier parameters use default value `Modifier`: `modifier: Modifier = Modifier`
- Single-expression function bodies are used where appropriate
- Comments use `//` for inline comments and `/* */` for block comments (block comments are leftover template comments, not authored)

## Import Organization

**Order:**
1. Android framework imports (`android.os.Bundle`, `android.media.MediaPlayer`)
2. AndroidX imports (`androidx.activity.*`, `androidx.compose.*`, `androidx.test.*`)
3. Third-party imports (none currently)
4. Project imports (`com.maxkeenti.news.ui.theme.*`)

**Style:**
- Imports use `.*` wildcard for Compose sub-packages (e.g., `androidx.compose.foundation.layout.*`)
- No import ordering enforcement tool detected
- Unused imports appear to be cleaned (Android Studio default behavior)

**Path Aliases:**
- No custom path aliases or module aliases detected

## Error Handling

**Patterns:**
- No explicit error handling patterns observed in current code
- `MediaPlayer` in `jukebox/app/src/main/java/com/maxkeenti/jukebox/MainActivity.kt` uses `setOnCompletionListener { it.release() }` for resource cleanup but has no error handling for playback failures
- No try/catch blocks anywhere in the codebase
- No custom exception classes defined

**Recommendations for new code:**
- Wrap `MediaPlayer.create()` calls in try/catch to handle resource loading failures
- Use Compose `remember` with `DisposableEffect` for MediaPlayer lifecycle management rather than inline creation
- Add error states to Composable functions where operations can fail

## Logging

**Framework:** No logging framework configured (no Timber, kotlinx.logging, or similar)

**Patterns:**
- No log statements anywhere in the codebase
- Android `Log` class not used

## Comments

**When to Comment:**
- Current code has minimal comments -- only template-generated block comments in test files and theme files
- One inline comment in `jukebox/app/src/main/java/com/maxkeenti/jukebox/MainActivity.kt`: `// Data class to keep your items organized` and `// Sample Data - Replace with your actual resource IDs`
- Template TODO comments are present in theme files (commented-out color and typography overrides)

**JSDoc/TSDoc:**
- No KDoc comments on any public functions or classes
- Test files have template-generated class-level KDoc referencing Android testing documentation

## Function Design

**Size:**
- Composable functions are short (5-25 lines)
- `onCreate()` in both projects delegates immediately to `setContent {}` -- no business logic in Activities
- Business logic and UI are mixed within Composable functions (no ViewModel separation)

**Parameters:**
- Composable functions take explicit data parameters: `News(title: String, author: String, date: String, content: String)`
- Data classes group related parameters: `JukeboxItem(val label: String, val imageRes: Int, val soundRes: Int)`
- Modifier parameter always last with default value: `modifier: Modifier = Modifier`

**Return Values:**
- Composable functions return `Unit` (standard Compose pattern)
- No explicit return type annotations on Composable functions

## Module Design

**Exports:**
- Each file exports a single primary Composable or class (no barrel/index files)
- Theme files export top-level values: `val Purple80`, `val Typography`, `fun NewsTheme()`

**Barrel Files:**
- No barrel files detected
- Package structure is flat within each module (no sub-packages beyond `ui.theme`)

## Architecture Patterns

**Activity Pattern:**
- Single Activity per app (`MainActivity`)
- Activity delegates to Compose immediately via `setContent {}`
- `enableEdgeToEdge()` called in `onCreate()` before `setContent`

**Compose Pattern:**
- Theme wrapper pattern: each app has a `[AppName]Theme` Composable that wraps `MaterialTheme`
- Theme supports dynamic color (Material You) with fallback to custom color schemes
- Preview functions annotated with `@Preview(showBackground = true)` and wrapped in theme

**Data Pattern:**
- Data classes used for model objects: `data class JukeboxItem`
- Resource references by integer ID: `R.drawable.*`, `R.raw.*`
- No repository pattern, no dependency injection, no ViewModels

**Resource Convention:**
- String resources defined in `res/values/strings.xml` for app name only
- Other strings hardcoded in Kotlin code
- Images stored as PNG/JPEG in `res/drawable/`
- Audio files stored as MP3 in `res/raw/`
- Theme colors defined in `ui/theme/Color.kt` as top-level `val` properties