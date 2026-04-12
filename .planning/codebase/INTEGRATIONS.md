# External Integrations

**Analysis Date:** 2026-04-12

## APIs & External Services

**None detected.** Both apps are entirely self-contained with no external API calls:
- The **news** app uses hardcoded data passed as composable parameters (`News(title, author, date, content)`)
- The **jukebox** app plays audio from local `res/raw/` resources

No HTTP clients, Retrofit, OkHttp, Ktor, or any networking libraries are present in the dependency catalogs or source code.

## Data Storage

**Databases:**
- None. No Room, SQLite, DataStore, or any persistence library is configured in either project.
- Both apps hold all data in memory within composable function parameters or hardcoded lists.

**File Storage:**
- Local filesystem only via Android resources (`res/raw/`, `res/drawable/`)
- Jukebox app bundles MP3 audio files in `jukebox/app/src/main/res/raw/` and PNG/JPEG images in `jukebox/app/src/main/res/drawable/`
- News app has no local file storage beyond standard Android resources

**Caching:**
- None

## Authentication & Identity

**Auth Provider:**
- None. Neither app implements authentication, login, or user identity features.
- No Firebase Auth, OAuth, or custom auth libraries present.

## Monitoring & Observability

**Error Tracking:**
- None. No Crashlytics, Sentry, or error tracking SDK is configured.

**Logs:**
- Default Android logging only (no explicit logging calls found in source code)
- No Timber, custom logger, or analytics SDK

## CI/CD & Deployment

**Hosting:**
- Local development only (Android device/emulator)
- No Play Store publishing configuration detected
- No Fastlane, Bitrise, or CI pipeline configuration found

**CI Pipeline:**
- None. No `.github/workflows` or CI configuration files detected.
- The `.github` directory exists but contains only GSD (Get Shit Done) agent configuration, not CI workflows.

## Environment Configuration

**Required env vars:**
- None. Both apps require no environment variables.
- `local.properties` (gitignored) typically holds `sdk.dir` for local Android SDK path

**Secrets location:**
- No secrets management needed (no API keys, no backend connections)
- No `.env` files present

## Third-Party SDKs

**AndroidX Libraries (via Compose BOM 2024.09.00):**
- `androidx.compose.ui:ui` - Compose UI core
- `androidx.compose.ui:ui-graphics` - Compose graphics
- `androidx.compose.ui:ui-tooling` / `ui-tooling-preview` - Design previews (debugImplementation)
- `androidx.compose.ui:ui-test-manifest` / `ui-test-junit4` - UI testing (androidTestImplementation)
- `androidx.compose.material3:material3` - Material Design 3 components

**AndroidX Platform Libraries:**
- `androidx.core:core-ktx` 1.10.1 (news) / 1.18.0 (jukebox) - Kotlin extensions
- `androidx.lifecycle:lifecycle-runtime-ktx` 2.6.1 (news) / 2.10.0 (jukebox) - Lifecycle
- `androidx.activity:activity-compose` 1.8.0 (news) / 1.13.0 (jukebox) - Activity-Compose bridge

**Android Platform APIs (no external dependency):**
- `android.media.MediaPlayer` - Used in jukebox app for audio playback
- `android.app.Activity` / `android.os.Bundle` - Standard Android Activity lifecycle
- `android.os.Build` - SDK version checks for dynamic theming

**Testing Libraries:**
- JUnit 4.13.2 - Unit testing
- AndroidX Test Ext JUnit 1.1.5 (news) / 1.3.0 (jukebox) - Instrumented test extensions
- Espresso 3.5.1 (news) / 3.7.0 (jukebox) - UI testing

## Webhooks & Callbacks

**Incoming:**
- None

**Outgoing:**
- None

## Version Catalog Discrepancies

The two projects use different versions for several shared dependencies:

| Dependency | news | jukebox |
|---|---|---|
| `core-ktx` | 1.10.1 | 1.18.0 |
| `lifecycle-runtime-ktx` | 2.6.1 | 2.10.0 |
| `activity-compose` | 1.8.0 | 1.13.0 |
| `androidx.test.ext:junit` | 1.1.5 | 1.3.0 |
| `espresso-core` | 3.5.1 | 3.7.0 |

The jukebox project has consistently newer dependency versions, suggesting it was created or updated more recently.

---

*Integration audit: 2026-04-12*