# News Improved

## 1. Task Assigned

- Reuse the `News` (Noticia) composable from the previous task and display 10 instances on screen
- Research and apply `LazyColumn` (LazyView) so the list scrolls when items overflow the screen
- Display the device UUID on screen using a platform API

## 2. Concept Covered

- `LazyColumn` with the `items()` DSL — only composables currently visible on screen are composed, making it efficient for long lists
- `items(list) { item -> ... }` lambda replaces a manual `for` loop inside a `Column`
- `Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)` to read the device's persistent hardware identifier
- `LocalContext.current` to access the Android `Context` from inside a composable without passing it as a parameter

## 3. What Was Hard

- `LazyColumn` requires `items()` from `androidx.compose.foundation.lazy` — it is not the same as a regular `Column` with a `verticalScroll` modifier; the two APIs are intentionally separate
- `Settings.Secure.ANDROID_ID` is a `String` constant, not a property — the call reads `Settings.Secure.getString(...)`, not `Settings.Secure.ANDROID_ID` directly
- `LocalContext.current` can only be called inside a composable scope; calling it outside (e.g., inside a `remember` lambda at the wrong level) causes a runtime crash

## 4. Key Code Snippet

`LazyColumn` displaying 10 `News` cards with the device UUID shown above the list:

```kotlin
@Composable
fun NewsImprovedScreen() {
    val context = LocalContext.current
    val deviceId = Settings.Secure.getString(
        context.contentResolver,
        Settings.Secure.ANDROID_ID
    )
    val items = List(10) { i ->
        Triple("Breaking News ${i + 1}", "Author ${i + 1}", "2025-0${(i % 9) + 1}-01")
    }
    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        Text(text = "Device ID: $deviceId", modifier = Modifier.padding(bottom = 8.dp))
        LazyColumn {
            items(items) { (title, author, date) ->
                News(title = title, author = author, date = date, content = "Content for $title")
            }
        }
    }
}
```
