# Testing Patterns

**Analysis Date:** 2026-04-12

## Test Framework

**Runner:**
- JUnit 4 for local unit tests
- AndroidJUnit4 for instrumented tests
- Test runner configured in `app/build.gradle.kts`: `testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"`

**Assertion Library:**
- JUnit 4 `Assert.*` (imported as `org.junit.Assert.*`)
- Espresso for UI assertions (configured but not used in practice)

**Version Catalog (news project):**
- JUnit: `4.13.2` (`news/gradle/libs.versions.toml`)
- AndroidX JUnit: `1.1.5`
- Espresso Core: `3.5.1`

**Version Catalog (jukebox project):**
- JUnit: `4.13.2` (`jukebox/gradle/libs.versions.toml`)
- AndroidX JUnit: `1.3.0`
- Espresso Core: `3.7.0`

**Run Commands:**
```bash
./gradlew test                            # Run all local unit tests
./gradlew connectedAndroidTest            # Run all instrumented tests (requires device/emulator)
./gradlew testDebugUnitTest               # Run debug unit tests only
./gradlew -c app/build.gradle.kts test    # Run tests for app module explicitly
```

## Test File Organization

**Location:**
- Local unit tests: `[project]/app/src/test/java/com/maxkeenti/[appname]/`
- Instrumented tests: `[project]/app/src/androidTest/java/com/maxkeenti/[appname]/`
- Tests are in a separate source set from production code (not co-located)

**Naming:**
- Test files use PascalCase matching the test class: `ExampleUnitTest.kt`, `ExampleInstrumentedTest.kt`
- Test classes are named after the class under test (convention, though only template tests exist)
- Test functions use snake_case: `addition_isCorrect()`, `useAppContext()`

**Structure:**
```
[project]/app/src/
  test/java/com/maxkeenti/[appname]/
    ExampleUnitTest.kt
  androidTest/java/com/maxkeenti/[appname]/
    ExampleInstrumentedTest.kt
```

## Test Structure

**Suite Organization:**
```kotlin
// Local unit test pattern (from ExampleUnitTest.kt)
package com.maxkeenti.news

import org.junit.Test
import org.junit.Assert.*

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}
```

```kotlin
// Instrumented test pattern (from ExampleInstrumentedTest.kt)
package com.maxkeenti.news

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.maxkeenti.news", appContext.packageName)
    }
}
```

**Patterns:**
- Setup pattern: none (no `@Before` or `@BeforeClass` annotations used)
- Teardown pattern: none (no `@After` or `@AfterClass` annotations used)
- Assertion pattern: static JUnit `assertEquals()` and `assert*()` methods
- Instrumented tests require `@RunWith(AndroidJUnit4::class)` annotation

## Mocking

**Framework:** None configured

**Patterns:**
- No mocking libraries in dependencies (no Mockito, MockK, or similar)
- No test doubles or fakes present
- No `@Mock` annotations or mock initialization code

**What to Mock:**
- When adding tests, mock external dependencies (Context, MediaPlayer, repositories)
- Use MockK for Kotlin/Compose projects (industry standard for Kotlin testing)

**What NOT to Mock:**
- Data classes (simple value objects)
- Composable functions under test (use Compose testing framework instead)

## Fixtures and Factories

**Test Data:**
- No test fixtures or factory patterns present
- No shared test data files
- No test resource directories

**Location:**
- No fixture directory exists
- When adding fixtures, create: `[project]/app/src/test/resources/` for unit test resources
- For instrumented test fixtures: `[project]/app/src/androidTest/assets/`

## Coverage

**Requirements:** None enforced

**View Coverage:**
```bash
./gradlew testDebugUnitTest -Pandroid.testInstrumentationRunnerArguments.coverage=true
./gradlew jacocoTestReport    # Requires JaCoCo plugin (not currently configured)
```

**Current State:**
- No JaCoCo or Kover plugin configured in either project
- No coverage thresholds or CI coverage enforcement
- No coverage reports generated

## Test Types

**Unit Tests:**
- Only template-generated placeholder test exists: `ExampleUnitTest.kt`
- No real business logic tests (because no business logic layer exists)
- Tests run on the JVM (no Android dependencies needed)

**Integration Tests:**
- None beyond the template instrumented test
- Template `ExampleInstrumentedTest.kt` only verifies the application package name
- Espresso dependencies configured but unused

**E2E Tests:**
- Not used
- No UI Automator or Compose UI test patterns in place

**Compose UI Tests:**
- Dependencies configured in `app/build.gradle.kts`:
  - `androidTestImplementation(libs.androidx.compose.ui.test.junit4)`
  - `debugImplementation(libs.androidx.compose.ui.test.manifest)`
- No actual Compose UI tests written yet
- Available Compose test APIs: `createComposeRule()`, `onNodeWithText()`, `performClick()`, etc.

## Compose Testing Setup

**Available infrastructure (configured but unused):**
```kotlin
// Compose UI test pattern (when adding tests, follow this):
@RunWith(AndroidJUnit4::class)
class MyComposeTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun myTest() {
        composeTestRule.setContent {
            MyTheme {
                MyComposable()
            }
        }
        composeTestRule.onNodeWithText("Expected Text").assertIsDisplayed()
    }
}
```

**Required dependencies (already in build.gradle.kts):**
- `androidx.compose.ui:ui-test-junit4` -- Compose test rule and assertions
- `androidx.compose.ui:ui-test-manifest` -- test manifest (debug only)

## Missing Test Infrastructure

**What needs to be added for meaningful testing:**

1. **MockK dependency** for Kotlin mocking:
   ```kotlin
   testImplementation("io.mockk:mockk:1.13.5")
   androidTestImplementation("io.mockk:mockk-android:1.13.5")
   ```

2. **Kotlin Coroutines test** for ViewModel/async testing:
   ```kotlin
   testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
   ```

3. **Compose UI testing** -- dependencies already present, just need test files

4. **Test runner configuration** -- consider switching to `AndroidJUnit5` for Kotlin-friendly parameterized tests

## Common Patterns

**Async Testing:**
- No async testing patterns established
- When adding coroutines/ViewModels, use `kotlinx-coroutines-test` with `runTest {}`

**Error Testing:**
- No error testing patterns established
- When adding error handling, test both success and failure paths

**Testing Composable Functions:**
- Use `createComposeRule()` for Compose UI tests
- Test state changes by setting content and asserting node states
- Test user interactions with `performClick()`, `performTextInput()`, etc.
- Use semantics for testability: `testTag`, `contentDescription`

## Recommendations for New Tests

**When adding a new Composable:**
1. Create a test file at `[project]/app/src/androidTest/java/com/maxkeenti/[appname]/[Feature]Test.kt`
2. Use `createComposeRule()` as the test rule
3. Set content inside the test with the appropriate theme wrapper
4. Assert node visibility and text content
5. Test user interactions (clicks, scrolls)

**When adding business logic (ViewModel/Repository):**
1. Create a test file at `[project]/app/src/test/java/com/maxkeenti/[appname]/[Feature]ViewModelTest.kt`
2. Use JUnit 5 or JUnit 4 with MockK
3. Use `runTest {}` for coroutine testing
4. Test state transitions and side effects

---

*Testing analysis: 2026-04-12*