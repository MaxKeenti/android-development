---
phase: quick
plan: 260412-oeb
subsystem: ui/news-improved
tags: [android, compose, lazycol, lazycolumn, news]
key-files:
  created:
    - ui/news-improved/app/src/main/java/com/maxkeenti/news_improved/News.kt
  modified:
    - ui/news-improved/app/src/main/java/com/maxkeenti/news_improved/MainActivity.kt
decisions:
  - Ported News composable from ui/news with package change only — no layout changes needed
---

# Quick Task 260412-oeb: Implement news-improved LazyColumn list with device UUID

**One-liner:** LazyColumn of 10 News cards with device UUID header using Settings.Secure.ANDROID_ID and items() DSL.

## What Was Done

### Task 1 — Create News.kt
Created `ui/news-improved/app/src/main/java/com/maxkeenti/news_improved/News.kt` by porting the composable from `ui/news`. The only change was the package declaration (`com.maxkeenti.news_improved` instead of `com.maxkeenti.news`). Layout is identical: a Card with a black header row showing the title in white, and a two-column body row with author/date on the left and content on the right.

### Task 2 — Update MainActivity.kt
Replaced the template `Greeting` composable with `NewsImprovedScreen`:
- Reads `Settings.Secure.ANDROID_ID` via `LocalContext.current` to display a device-unique identifier at the top of the screen.
- Builds a list of 10 `Triple<String, String, String>` entries (title, author, date) and renders them in a `LazyColumn` using the `items()` DSL.
- Each item renders a `News` card with a fixed lorem ipsum content string.
- `MainActivity.onCreate` now calls `NewsImprovedScreen` inside the `Scaffold`.
- Preview function renamed to `NewsImprovedPreview` to match the new composable.

## Key Concepts

### LazyColumn
`LazyColumn` is Compose's equivalent of `RecyclerView` — it only composes and lays out items that are currently visible on screen, making it suitable for long lists. Unlike `Column`, items outside the viewport are not kept in the composition tree.

### items() DSL
The `items(list) { item -> ... }` lambda inside a `LazyColumn` block is the idiomatic way to project a list into composable items. Kotlin destructuring (`{ (title, author, date) -> ... }`) works naturally here when the list element is a `Triple` or data class.

### Settings.Secure.ANDROID_ID
`Settings.Secure.ANDROID_ID` returns a 64-bit number expressed as a hex string, unique to each app installation on a device (resets on factory reset and may differ per signing key on API 26+). It requires no runtime permission, making it the simplest way to obtain a stable per-device identifier in an Android app.

## Files Changed

| File | Action |
|------|--------|
| `ui/news-improved/app/src/main/java/com/maxkeenti/news_improved/News.kt` | Created |
| `ui/news-improved/app/src/main/java/com/maxkeenti/news_improved/MainActivity.kt` | Modified |

## Commit

`13281bf` — feat(ui/news-improved): implement LazyColumn news list with device UUID display

## Deviations from Plan

None — plan executed exactly as written.
