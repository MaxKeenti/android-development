# Codebase Structure

**Analysis Date:** 2026-04-12

## Directory Layout

```
android-development/                 # Monorepo root
├── .agent/                          # AI agent configurations (Windsurf/Cursor)
├── .claude/                         # Claude Code agent/hooks/commands
├── .gemini/                         # Gemini agent/hooks/commands
├── .github/                         # GitHub Copilot agents + GSD skills
├── .planning/                       # GSD planning documents
│   └── codebase/                    # Codebase analysis docs (this file)
├── .gitignore                       # Shared gitignore for Android projects
├── jukebox/                         # Sound board Android app (independent Gradle project)
│   ├── app/                         # Main application module
│   │   ├── build.gradle.kts         # App module build config
│   │   ├── build/                   # Build output (gitignored)
│   │   └── src/
│   │       ├── main/
│   │       │   ├── AndroidManifest.xml
│   │       │   ├── java/com/maxkeenti/jukebox/
│   │       │   │   ├── MainActivity.kt        # Activity + composables + data model
│   │       │   │   └── ui/theme/
│   │       │   │       ├── Color.kt            # Color definitions
│   │       │   │       ├── Theme.kt            # Material 3 theme composable
│   │       │   │       └── Type.kt             # Typography definitions
│   │       │   └── res/
│   │       │       ├── drawable/               # PNG/JPEG images (character art, launcher)
│   │       │       ├── mipmap-*/               # Launcher icons (multiple densities)
│   │       │       ├── raw/                    # MP3 audio files (sound board clips)
│   │       │       ├── values/                 # strings.xml, colors.xml, themes.xml
│   │       │       └── xml/                    # backup_rules.xml, data_extraction_rules.xml
│   │       ├── test/                            # JVM unit tests
│   │       │   └── java/com/maxkeenti/jukebox/ExampleUnitTest.kt
│   │       └── androidTest/                    # Instrumented tests
│   │           └── java/com/maxkeenti/jukebox/ExampleInstrumentedTest.kt
│   ├── build.gradle.kts             # Root project build config
│   ├── gradle/
│   │   ├── libs.versions.toml       # Version catalog
│   │   └── wrapper/                 # Gradle wrapper (jar + properties)
│   ├── gradle.properties            # Gradle JVM/parallel settings
│   ├── gradlew                      # Unix Gradle wrapper script
│   ├── gradlew.bat                  # Windows Gradle wrapper script
│   └── settings.gradle.kts          # Project settings (rootProject.name = "jukebox")
├── news/                            # News card display Android app (independent Gradle project)
│   ├── app/                         # Main application module
│   │   ├── build.gradle.kts         # App module build config
│   │   ├── build/                   # Build output (gitignored)
│   │   └── src/
│   │       ├── main/
│   │       │   ├── AndroidManifest.xml
│   │       │   ├── java/com/maxkeenti/news/
│   │       │   │   ├── MainActivity.kt         # Activity + composable host
│   │       │   │   ├── News.kt                 # News card composable
│   │       │   │   └── ui/theme/
│   │       │   │       ├── Color.kt            # Color definitions
│   │       │   │       ├── Theme.kt            # Material 3 theme composable
│   │       │   │       └── Type.kt             # Typography definitions
│   │       │   └── res/
│   │       │       ├── drawable/               # Vector drawables (launcher foreground/background)
│   │       │       ├── mipmap-*/               # Launcher icons (multiple densities)
│   │       │       ├── mipmap-anydpi/          # Adaptive icon XML
│   │       │       ├── values/                 # strings.xml, colors.xml, themes.xml
│   │       │       └── xml/                    # backup_rules.xml, data_extraction_rules.xml
│   │       ├── test/                            # JVM unit tests
│   │       │   └── java/com/maxkeenti/news/ExampleUnitTest.kt
│   │       └── androidTest/                    # Instrumented tests
│   │           └── java/com/maxkeenti/news/ExampleInstrumentedTest.kt
│   ├── build.gradle.kts             # Root project build config
│   ├── gradle/
│   │   ├── libs.versions.toml       # Version catalog
│   │   └── wrapper/                 # Gradle wrapper (jar + properties)
│   ├── gradle.properties            # Gradle JVM/parallel settings
│   ├── gradlew                      # Unix Gradle wrapper script
│   ├── gradlew.bat                  # Windows Gradle wrapper script
│   └── settings.gradle.kts          # Project settings (rootProject.name = "news")
└── .gitignore
```

## Directory Purposes

**`jukebox/`:**
- Purpose: Complete standalone Android app project -- a Star Wars sound board
- Contains: Full Gradle project with single `:app` module, sound assets (MP3), character images (PNG), and Compose UI
- Key files: `jukebox/app/src/main/java/com/maxkeenti/jukebox/MainActivity.kt`, `jukebox/gradle/libs.versions.toml`

**`news/`:**
- Purpose: Complete standalone Android app project -- a news card display app
- Contains: Full Gradle project with single `:app` module and Compose UI for displaying a news article card
- Key files: `news/app/src/main/java/com/maxkeenti/news/MainActivity.kt`, `news/app/src/main/java/com/maxkeenti/news/News.kt`

**`{app}/app/src/main/java/com/maxkeenti/{appname}/ui/theme/`:**
- Purpose: Material 3 theming for each app (auto-generated by Android Studio Compose template)
- Contains: `Color.kt` (color constants), `Theme.kt` (theme composable with dark/light/dynamic), `Type.kt` (typography)
- Pattern: Identical structure across both apps; each app defines its own `{AppName}Theme`

**`{app}/app/src/main/res/raw/`:**
- Purpose: Raw audio assets (MP3 files) for the Jukebox app's sound board
- Contains: Character sound clips (`anakin_1.mp3`, `chewie.mp3`, `vader.mp3`, etc.)
- Note: Only present in `jukebox/`, not in `news/`

**`{app}/app/src/main/res/drawable/`:**
- Purpose: Image assets
- Jukebox: Character images (`anakin.png`, `chewie.png`, `han.png`, etc.) plus launcher icons
- News: Only launcher icon drawables (foreground/background XML)

**`.github/`, `.claude/`, `.gemini/`, `.agent/`:**
- Purpose: AI coding assistant configurations (agents, skills, hooks, commands for GSD workflow)
- Contains: Agent definitions, skill configs, copilot instructions
- Not part of the Android build; these are development tooling

**`.planning/`:**
- Purpose: GSD workflow planning documents
- Contains: Codebase analysis documents

## Key File Locations

**Entry Points:**
- `jukebox/app/src/main/java/com/maxkeenti/jukebox/MainActivity.kt`: Jukebox app entry point (Activity + composables + data model all in one file)
- `news/app/src/main/java/com/maxkeenti/news/MainActivity.kt`: News app entry point (Activity + composable host)
- `news/app/src/main/java/com/maxkeenti/news/News.kt`: News card composable (separate file from Activity)

**Configuration:**
- `jukebox/gradle/libs.versions.toml`: Jukebox version catalog (AGP 9.1.0, Kotlin 2.2.10, Compose BOM 2024.09.00)
- `news/gradle/libs.versions.toml`: News version catalog (AGP 9.1.0, Kotlin 2.2.10, Compose BOM 2024.09.00, older support libs)
- `jukebox/app/build.gradle.kts`: Jukebox app module build config (namespace `com.maxkeenti.jukebox`, minSdk 36)
- `news/app/build.gradle.kts`: News app module build config (namespace `com.maxkeenti.news`, minSdk 36)
- `jukebox/app/src/main/AndroidManifest.xml`: Jukebox manifest
- `news/app/src/main/AndroidManifest.xml`: News manifest

**Core Logic:**
- `jukebox/app/src/main/java/com/maxkeenti/jukebox/MainActivity.kt`: All Jukebox business logic, UI, and data model in a single file
- `news/app/src/main/java/com/maxkeenti/news/News.kt`: News composable with Card-based layout
- `news/app/src/main/java/com/maxkeenti/news/MainActivity.kt`: News Activity with hardcoded data

**Testing:**
- `jukebox/app/src/test/java/com/maxkeenti/jukebox/ExampleUnitTest.kt`: Placeholder JVM unit test
- `jukebox/app/src/androidTest/java/com/maxkeenti/jukebox/ExampleInstrumentedTest.kt`: Placeholder instrumented test
- `news/app/src/test/java/com/maxkeenti/news/ExampleUnitTest.kt`: Placeholder JVM unit test
- `news/app/src/androidTest/java/com/maxkeenti/news/ExampleInstrumentedTest.kt`: Placeholder instrumented test

**Theming:**
- `{app}/app/src/main/java/com/maxkeenti/{appname}/ui/theme/Color.kt`: Color constants (Purple80, PurpleGrey80, Pink80, Purple40, PurpleGrey40, Pink40)
- `{app}/app/src/main/java/com/maxkeenti/{appname}/ui/theme/Theme.kt`: `{AppName}Theme` composable with dark/light/dynamic color
- `{app}/app/src/main/java/com/maxkeenti/{appname}/ui/theme/Type.kt`: Typography definition (bodyLarge only)

## Naming Conventions

**Files:**
- Kotlin files: PascalCase matching the class/composable name (e.g., `MainActivity.kt`, `News.kt`, `Color.kt`, `Theme.kt`, `Type.kt`)
- Resource files: snake_case (e.g., `backup_rules.xml`, `data_extraction_rules.xml`, `ic_launcher_foreground.xml`)
- Audio files: snake_case (e.g., `anakin_1.mp3`, `chewie.mp3`)
- Image files: lowercase (e.g., `anakin.png`, `gatito.jpeg`)

**Directories:**
- Kotlin packages follow reverse domain naming: `com/maxkeenti/{appname}/`
- UI theme code grouped under `ui/theme/` sub-package
- Resource directories follow Android convention: `drawable/`, `raw/`, `values/`, `mipmap-{density}/`, `xml/`

**Gradle:**
- Build files: `build.gradle.kts` (Kotlin DSL)
- Settings: `settings.gradle.kts` (Kotlin DSL)
- Version catalog: `gradle/libs.versions.toml` (standard Gradle version catalog)
- Gradle wrapper: `gradle/wrapper/gradle-wrapper.properties`

## Where to Add New Code

**New Android App:**
- Create a new directory at the project root (e.g., `myapp/`)
- Use Android Studio "New Project" or copy the structure from an existing app
- Must include: `app/` module with `build.gradle.kts`, root `build.gradle.kts`, `settings.gradle.kts`, `gradle/libs.versions.toml`, `gradle/wrapper/`
- Package naming: `com.maxkeenti.{appname}`

**New Feature in Existing App (e.g., Jukebox):**
- Primary UI code: `jukebox/app/src/main/java/com/maxkeenti/jukebox/`
- New composables: Create new `.kt` files in the package directory
- New data models: Create new `.kt` files in the package directory (currently `JukeboxItem` is in `MainActivity.kt`)
- New resources: `jukebox/app/src/main/res/` (images in `drawable/`, sounds in `raw/`)
- New theme colors: `jukebox/app/src/main/java/com/maxkeenti/jukebox/ui/theme/Color.kt`

**New Composable Screen:**
- Create a new `.kt` file in `com/maxkeenti/{appname}/`
- Add a navigation framework (currently none exists)
- Update `MainActivity.kt` to integrate the new screen

**New ViewModel / Repository:**
- Create `com/maxkeenti/{appname}/viewmodel/` package for ViewModels
- Create `com/maxkeenti/{appname}/repository/` package for data repositories
- Add lifecycle/viewmodel dependencies to `app/build.gradle.kts`

**Tests:**
- JVM unit tests: `{app}/app/src/test/java/com/maxkeenti/{appname}/`
- Instrumented tests: `{app}/app/src/androidTest/java/com/maxkeenti/{appname}/`
- Compose UI tests: Place in `androidTest/` using `androidx.compose.ui.test.junit4`

**New Resources:**
- Images: `{app}/app/src/main/res/drawable/` (PNG, JPEG, XML vector)
- Audio: `{app}/app/src/main/res/raw/` (MP3, WAV)
- Strings: `{app}/app/src/main/res/values/strings.xml`
- Colors (XML): `{app}/app/src/main/res/values/colors.xml`
- Compose colors: `{app}/app/src/main/java/com/maxkeenti/{appname}/ui/theme/Color.kt`

## Special Directories

**`{app}/app/build/`:**
- Purpose: Gradle build output (compiled classes, merged resources, APK intermediates)
- Generated: Yes (by Gradle build system)
- Committed: No (gitignored)

**`{app}/.gradle/` and `{app}/.idea/`:**
- Purpose: Gradle cache and IDE configuration
- Generated: Yes
- Committed: No (partially gitignored)

**`.github/`, `.claude/`, `.gemini/`, `.agent/`:**
- Purpose: AI coding assistant configurations for GSD (Get Shit Done) workflow system
- Contains: Agent definitions, skills, commands, hooks, contexts, and templates
- Generated: No (manually configured)
- Committed: Yes

**`{app}/gradle/wrapper/`:**
- Purpose: Gradle wrapper for reproducible builds
- Contains: `gradle-wrapper.jar` and `gradle-wrapper.properties`
- Generated: Yes (by `gradle wrapper` task)
- Committed: Yes (standard practice for Gradle projects)

---

*Structure analysis: 2026-04-12*