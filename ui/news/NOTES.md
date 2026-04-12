# News App

## 1. Task Assigned

- Build a news card UI in Jetpack Compose
- Display a single news article with title, author, date, and content fields
- Teacher provided the composable structure; goal was to learn how composables receive and display data

## 2. Concept Covered

- Composable functions with named parameters: `News(title, author, date, content)`
- `Card` with `RoundedCornerShape` and `CardDefaults.cardElevation` for Material 3 card styling
- `Column` and `Row` for layout hierarchy — nesting rows inside a column for the header/body split
- Passing `Modifier` as the last parameter with a default value: `modifier: Modifier = Modifier`

## 3. What Was Hard

- Understanding when to use `Row` vs `Column` — the header needed a `Row` for full-width background, the body used nested columns for author/date and content
- The original strings were in German (the teacher's example); they were replaced with English placeholders to make the code self-documenting
- Getting the card to stretch full width required `Modifier.fillMaxWidth()` on the `Card`

## 4. Key Code Snippet

The `News` composable — a card with a dark header row and a body with two columns:

```kotlin
@Composable
fun News(title: String, author: String, date: String, content: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth().background(Color.Black).padding(12.dp)
            ) {
                Text(text = title, color = Color.White)
            }
            Row {
                Column { Text(text = author); Text(text = date) }
                Column { Text(text = content) }
            }
        }
    }
}
```
