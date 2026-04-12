# Jukebox App

## 1. Task Assigned

- Build a 3-column sound board grid using Jetpack Compose
- Each cell displays an image with a label; tapping the image plays a sound clip
- Teacher provided Star Wars character assets (images + audio); goal was to learn `LazyVerticalGrid`

## 2. Concept Covered

- `LazyVerticalGrid` with `GridCells.Fixed(3)` — lays out items in a fixed 3-column grid, rendering only visible cells
- `data class JukeboxItem(label, imageRes, soundRes)` — groups related resource IDs into a single model object
- `MediaPlayer.create(context, soundRes)` + `setOnCompletionListener { it.release() }` — creates a player per tap and releases it when playback ends
- `painterResource(id = item.imageRes)` inside a `clickable` modifier to make images interactive

## 3. What Was Hard

- Understanding `LazyVerticalGrid` vs `LazyColumn` — grid requires `columns` parameter specifying fixed or adaptive layout
- Resource IDs (`R.drawable.*`, `R.raw.*`) must match the exact filename in the `res/` directory; typos cause runtime crashes, not compile errors
- MediaPlayer lifecycle: creating a new instance per tap works but causes overlapping audio if the user taps quickly — see Known Limitation below

**Known Limitation — MediaPlayer overlap on rapid taps:**
Each tap creates a brand new `MediaPlayer` instance immediately, before the previous one has finished playing. Android has no tap-rate limiter by default. If the user taps an image three times quickly, three `MediaPlayer` instances start simultaneously, producing overlapping audio. The fix would be to track a shared `MediaPlayer` reference and call `stop()` + `release()` before creating the next one — but that requires `remember { }` state in Compose, which was not covered in this task.

## 4. Key Code Snippet

The clickable image that plays a sound — the core interaction in the grid cell:

```kotlin
Image(
    painter = painterResource(id = item.imageRes),
    contentDescription = item.label,
    modifier = Modifier
        .size(100.dp)
        .clickable {
            val mediaPlayer = MediaPlayer.create(context, item.soundRes)
            mediaPlayer.setOnCompletionListener { it.release() }
            mediaPlayer.start()
        }
)
```
