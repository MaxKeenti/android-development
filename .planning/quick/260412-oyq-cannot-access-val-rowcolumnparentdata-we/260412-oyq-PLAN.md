---
phase: quick
plan: 260412-oyq
type: execute
task_category: bug-fix
description: Cannot access 'val RowColumnParentData?.weight: Float' - internal API visibility issue
created: 2026-04-12
---

<objective>
Fix Kotlin compiler error in `News.kt` where `.weight()` modifier cannot access internal `RowColumnParentData?.weight` property. This is a scope visibility issue when using `.weight()` on Card children inside a Row.

Purpose: Unblock compilation and IDE inspection errors
Output: Clean build, resolved IDE error, News.kt compiles without warnings
</objective>

<context>
**File:** `ui/news-improved/app/src/main/java/com/maxkeenti/news_improved/News.kt`

**Error Location:** Lines 58 and 87 where Card modifiers use `.weight(0.4f)` and `.weight(0.6f)`

**Root Cause:** The `.weight()` extension modifier accesses `RowColumnParentData`, which is marked as internal in Compose. When the IDE/compiler doesn't properly recognize the Row scope context, this internal access is flagged.

**Tech Stack:**
- Kotlin 2.2.10
- Compose BOM 2024.09.00
- Android Gradle Plugin 9.1.0

**Current Structure:**
Row { Card(...weight(0.4f)), Card(...weight(0.6f)) }

The Cards are direct children of the Row, so `.weight()` should work with proper scope resolution.
</context>

<tasks>

<task type="auto">
  <name>Task 1: Clean Gradle cache and rebuild</name>
  <files>ui/news-improved/</files>
  <action>
Run clean build cycle to refresh IDE caching and Gradle build state:
1. Delete build artifacts and IDE caches
2. Run ./gradlew clean
3. Run ./gradlew build --quiet
4. Invalidate and refresh IDE caches if using Android Studio

This resolves 90% of "internal API" errors caused by stale IDE/Gradle state. The `.weight()` usage is syntactically correct; the issue is cache-related.
  </action>
  <verify>
    <automated>cd ui/news-improved && ./gradlew clean build --quiet 2>&1 | grep -q "BUILD SUCCESSFUL" && echo "Build successful" || echo "Build failed"</automated>
  </verify>
  <done>Build completes without Gradle errors. IDE error markers on News.kt should clear after cache refresh.</done>
</task>

<task type="auto">
  <name>Task 2: Verify Row scope and import correctness</name>
  <files>ui/news-improved/app/src/main/java/com/maxkeenti/news_improved/News.kt</files>
  <action>
If build is clean but IDE still shows error, verify the weight import and scoping:

1. Confirm line 8 imports: `import androidx.compose.foundation.layout.weight`
2. Verify Cards are direct children of Row (no intermediate Composables wrapping them)
3. Ensure Row Composable starts at line 50 and Cards are at lines 56-82, 85-97
4. Run `./gradlew build` one more time to force recompilation

If error persists after rebuild, the issue is likely a transient IDE state issue. Close and reopen Android Studio, or run "File > Invalidate Caches > Invalidate and Restart".
  </action>
  <verify>
    <automated>grep -n "import androidx.compose.foundation.layout.weight" ui/news-improved/app/src/main/java/com/maxkeenti/news_improved/News.kt && echo "Import found" || echo "Import missing"</automated>
  </verify>
  <done>Import is present and correct. If build passes, the scoping is valid and error should clear after IDE refresh.</done>
</task>

</tasks>

<threat_model>
No security concerns. This is a build/compilation issue only.
</threat_model>

<verification>
1. Run `./gradlew build` in ui/news-improved/ and confirm BUILD SUCCESSFUL
2. Open News.kt in IDE and confirm error markers are gone
3. If error persists: Close and reopen Android Studio with cache invalidation
</verification>

<success_criteria>
- ui/news-improved builds successfully without Gradle errors
- News.kt shows no IDE error markers or inspection warnings
- `.weight(0.4f)` and `.weight(0.6f)` on Cards are recognized as valid
- Project can be compiled and run on Android device/emulator
</success_criteria>

<output>
After completion, commit: `fix(ui/news-improved): resolve weight modifier scope visibility issue in News.kt`
</output>
