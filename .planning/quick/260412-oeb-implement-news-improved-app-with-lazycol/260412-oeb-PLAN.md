---
phase: quick
plan: 260412-oeb
type: execute
wave: 1
depends_on: []
files_modified:
  - ui/news-improved/app/src/main/java/com/maxkeenti/news_improved/News.kt
  - ui/news-improved/app/src/main/java/com/maxkeenti/news_improved/MainActivity.kt
autonomous: true
requirements: []

must_haves:
  truths:
    - "A News card composable exists under the news_improved package"
    - "Launching the app shows a Device ID text line above the list"
    - "Ten News cards are displayed and the list scrolls past the bottom of the screen"
  artifacts:
    - path: "ui/news-improved/app/src/main/java/com/maxkeenti/news_improved/News.kt"
      provides: "News composable adapted to com.maxkeenti.news_improved package"
    - path: "ui/news-improved/app/src/main/java/com/maxkeenti/news_improved/MainActivity.kt"
      provides: "NewsImprovedScreen composable wired into the activity"
  key_links:
    - from: "MainActivity.kt"
      to: "NewsImprovedScreen()"
      via: "setContent lambda"
    - from: "NewsImprovedScreen"
      to: "News.kt"
      via: "News(title, author, date, content) call inside items() lambda"
    - from: "NewsImprovedScreen"
      to: "Settings.Secure.ANDROID_ID"
      via: "Settings.Secure.getString(context.contentResolver, ...)"
---

<objective>
Implement the news-improved app so it displays 10 scrollable News cards and the device UUID, fulfilling the teacher's LazyColumn assignment.

Purpose: Demonstrate LazyColumn with items() DSL and Settings.Secure device ID retrieval — the two new concepts introduced in this task.
Output: A buildable project with News.kt (ported composable) and an updated MainActivity.kt wiring everything together.
</objective>

<execution_context>
@/Users/maximilianogonzalezcalzada/Source/Personal/android-development/.claude/get-shit-done/workflows/execute-plan.md
@/Users/maximilianogonzalezcalzada/Source/Personal/android-development/.claude/get-shit-done/templates/summary.md
</execution_context>

<context>
@.planning/STATE.md

<interfaces>
<!-- News composable contract (ported from ui/news/). Executor must NOT import from com.maxkeenti.news. -->
<!-- Declare a new copy under com.maxkeenti.news_improved with the same signature. -->

Source: ui/news/app/src/main/java/com/maxkeenti/news/News.kt (original — package must change)
```kotlin
@Composable
fun News(title: String, author: String, date: String, content: String)
```

Source: ui/news-improved/app/src/main/java/com/maxkeenti/news_improved/MainActivity.kt (current state — boilerplate only)
- Activity calls setContent { NewsimprovedTheme { Scaffold { Greeting(...) } } }
- Greeting composable and GreetingPreview must be replaced, not left alongside new code
</interfaces>
</context>

<tasks>

<task type="auto">
  <name>Task 1: Create News.kt in news-improved package</name>
  <files>ui/news-improved/app/src/main/java/com/maxkeenti/news_improved/News.kt</files>
  <action>
Create a new file at the path above. Copy the News composable body from ui/news/app/src/main/java/com/maxkeenti/news/News.kt exactly — keep Card, RoundedCornerShape, Row/Column layout, black header row with white title text, author/date/content rows — but change the package declaration to `package com.maxkeenti.news_improved`. All imports stay the same (androidx.compose.*) except the package line.

Do NOT import anything from com.maxkeenti.news.
  </action>
  <verify>File exists at the path and contains `package com.maxkeenti.news_improved` on line 1 and `fun News(` declaration.</verify>
  <done>News.kt compiles under the news_improved package with the identical visual structure as the original.</done>
</task>

<task type="auto">
  <name>Task 2: Update MainActivity.kt with NewsImprovedScreen</name>
  <files>ui/news-improved/app/src/main/java/com/maxkeenti/news_improved/MainActivity.kt</files>
  <action>
Replace the entire file content with the following (preserving the package and theme name `NewsimprovedTheme`):

1. Keep the existing class skeleton: `enableEdgeToEdge()` + `setContent { NewsimprovedTheme { ... } }`.
2. Inside the Scaffold content lambda, replace `Greeting(...)` with `NewsImprovedScreen(modifier = Modifier.padding(innerPadding))`.
3. Remove the `Greeting` composable and `GreetingPreview` entirely.
4. Add `NewsImprovedScreen` composable below the class:

```kotlin
@Composable
fun NewsImprovedScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val deviceId = Settings.Secure.getString(
        context.contentResolver,
        Settings.Secure.ANDROID_ID
    )
    val items = List(10) { i ->
        Triple("Breaking News ${i + 1}", "Author ${i + 1}", "2025-0${(i % 9) + 1}-01")
    }
    Column(modifier = modifier.fillMaxSize().padding(8.dp)) {
        Text(text = "Device ID: $deviceId", modifier = Modifier.padding(bottom = 8.dp))
        LazyColumn {
            items(items) { (title, author, date) ->
                News(title = title, author = author, date = date, content = "Content for $title")
            }
        }
    }
}
```

5. Add a `NewsImprovedPreview` composable annotated with `@Preview(showBackground = true)` that calls `NewsImprovedScreen()` inside `NewsimprovedTheme`.

Required imports to add (the Compose BOM already provides all of these — no new Gradle dependencies):
- `android.provider.Settings`
- `androidx.compose.foundation.lazy.LazyColumn`
- `androidx.compose.foundation.lazy.items`
- `androidx.compose.foundation.layout.Column`
- `androidx.compose.foundation.layout.fillMaxSize`
- `androidx.compose.runtime.Composable`
- `androidx.compose.ui.platform.LocalContext`
- `androidx.compose.material3.Text`
- `androidx.compose.ui.unit.dp`

The `Modifier`, `Scaffold`, `fillMaxSize`, `padding`, `Preview` imports are already present in the template — keep them, do not duplicate.
  </action>
  <verify>File contains `fun NewsImprovedScreen` and `LazyColumn` and `Settings.Secure.ANDROID_ID` and does NOT contain `fun Greeting`.</verify>
  <done>
- MainActivity.kt has no Greeting composable.
- NewsImprovedScreen is the sole content composable.
- LazyColumn with items() renders 10 News cards.
- Device ID is read from Settings.Secure and displayed above the list.
- Project builds without errors (verify with: cd ui/news-improved && ./gradlew assembleDebug).
  </done>
</task>

</tasks>

<threat_model>
## Trust Boundaries

| Boundary | Description |
|----------|-------------|
| Android platform → app | Settings.Secure.ANDROID_ID is provided by the platform; it is read-only and cannot be spoofed by user input |

## STRIDE Threat Register

| Threat ID | Category | Component | Disposition | Mitigation Plan |
|-----------|----------|-----------|-------------|-----------------|
| T-oeb-01 | Information Disclosure | Settings.Secure.ANDROID_ID displayed on screen | accept | Learning app only; device ID display is the explicit assignment goal. Not a production app. |
</threat_model>

<verification>
```bash
cd /Users/maximilianogonzalezcalzada/Source/Personal/android-development/ui/news-improved && ./gradlew assembleDebug
```
Build must succeed with no errors. Warnings about unused imports are acceptable.
</verification>

<success_criteria>
- `News.kt` exists under `com.maxkeenti.news_improved` package with identical layout to the original.
- `MainActivity.kt` contains `NewsImprovedScreen` using `LazyColumn` + `items()` + `Settings.Secure.ANDROID_ID`.
- `./gradlew assembleDebug` exits 0.
- No `Greeting` composable remains in the file.
</success_criteria>

<output>
After completion, create `.planning/quick/260412-oeb-implement-news-improved-app-with-lazycol/260412-oeb-SUMMARY.md` following the summary template.
</output>
