# Technology Stack

**Analysis Date:** 2026-04-12

## Languages

**Primary:**
- Kotlin 2.2.10 - All application source code, Compose UI, and tests

**Secondary:**
- Kotlin DSL (Gradle Kotlin Script) - Build configuration (`build.gradle.kts`, `settings.gradle.kts`)
- TOML - Version catalog (`libs.versions.toml`)
- XML - Android manifests, resources, themes

## Runtime

**Environment:**
- Android SDK (compileSdk 36, minSdk 36, targetSdk 36)
- Java compatibility: source/target Java 11

**Package Manager:**
- Gradle 9.3.1 (via wrapper)
- Lockfile: Not present (Gradle version catalog used instead)
- Version catalog: `gradle/libs.versions.toml` per project

## Frameworks

**Core:**
- Android SDK 36 (API 36, release with minorApiLevel 1) - Target platform
- Jetpack Compose (BOM 2024.09.00) - Declarative UI framework
- Material Design 3 (`androidx.compose.material3`) - UI component library
- AndroidX Lifecycle Runtime KTX - Lifecycle-aware components
- AndroidX Activity Compose - Compose integration with activities

**Testing:**
- JUnit 4.13.2 - Unit testing framework
- AndroidX Test JUnit 1.1.5 (news) / 1.3.0 (jukebox) - AndroidX test extensions
- Espresso 3.5.1 (news) / 3.7.0 (jukebox) - Android UI instrumented testing
- Compose UI Test JUnit4 - Compose-specific UI testing

**Build/Dev:**
- Android Gradle Plugin (AGP) 9.1.0 - Build system plugin
- Kotlin Compose Compiler Plugin 2.2.10 - Compose compiler integration
- Gradle Kotlin Script - Build file DSL
- Foojay Resolver Convention Plugin 1.0.0 - JDK auto-provisioning

## Key Dependencies

**Critical:**
- `androidx.compose.ui:ui` - Core Compose UI primitives (layouts, modifiers, input)
- `androidx.compose.material3:material3` - Material Design 3 components
- `androidx.compose.ui:ui-graphics` - Compose graphics APIs
- `androidx.activity:activity-compose` 1.8.0 (news) / 1.13.0 (jukebox) - Compose Activity integration
- `androidx.lifecycle:lifecycle-runtime-ktx` 2.6.1 (news) / 2.10.0 (jukebox) - Lifecycle scope support
- `androidx.core:core-ktx` 1.10.1 (news) / 1.18.0 (jukebox) - Kotlin extensions for Android platform APIs

**Infrastructure:**
- `android.media.MediaPlayer` - Audio playback (jukebox app, Android platform API, not a separate dependency)
- Compose UI Tooling (`ui-tooling`, `ui-tooling-preview`) - Debug-only design previews

## Configuration

**Environment:**
- No `.env` files detected
- No environment variables required for build
- `local.properties` is gitignored (typically holds SDK path)

**Build:**
- `news/build.gradle.kts` - Root-level build config (plugin declarations only)
- `news/app/build.gradle.kts` - App module build config (SDK versions, dependencies, Compose enabled)
- `news/settings.gradle.kts` - Project settings, repository configuration, root project name
- `news/gradle/libs.versions.toml` - Centralized version catalog
- `jukebox/build.gradle.kts` - Root-level build config (plugin declarations only)
- `jukebox/app/build.gradle.kts` - App module build config
- `jukebox/settings.gradle.kts` - Project settings
- `jukebox/gradle/libs.versions.toml` - Centralized version catalog

**Repositories configured:**
- Google (Android, Google, and AndroidX groups)
- Maven Central (all other dependencies)
- Gradle Plugin Portal (for plugin resolution)

## Platform Requirements

**Development:**
- Android Studio (Meerkat or later, supporting AGP 9.1.0 and Kotlin 2.2.10)
- JDK 11+ (compileOptions set to Java 11 compatibility)
- Android SDK with API 36 platform
- Gradle 9.3.1 (wrapper provided)

**Production:**
- Android devices running API 36+ (minSdk = 36)
- No network dependencies required at runtime (jukebox uses local resources; news has hardcoded data)
- Both apps are fully self-contained with no backend dependency

## Project Structure

The repository contains two independent Android projects:
1. **news** - A simple news card display app (`com.maxkeenti.news`)
2. **jukebox** - A sound board app with image grid and audio playback (`com.maxkeenti.jukebox`)

Each project has its own independent Gradle build, version catalog, and source tree. There is no shared module between them.

---

*Stack analysis: 2026-04-12*