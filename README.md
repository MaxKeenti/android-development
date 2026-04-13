# Android Development Learning Journey

A personal reference repo for Android development tasks assigned by my teacher. Each task lives in a topic folder alongside working code and notes explaining what I learned. This is a living record of learning in progress — not a course, not a portfolio.

## Topics

### ui/ — User Interface

| Task | What It Demonstrates |
|------|---------------------|
| [news](ui/news/) | Jetpack Compose `Card` layout with composable parameters |
| [jukebox](ui/jukebox/) | `LazyVerticalGrid` with `MediaPlayer` tap-to-play audio |
| [news-improved](ui/news-improved/) | Reuses `News` card in a `LazyColumn` to show 10 items; reads device UUID via `Settings.Secure` |

See [ui/README.md](ui/README.md) for the topic overview.

### sensors/ — Sensors & Hardware APIs

| Task | What It Demonstrates |
|------|---------------------|
| [sensor-management](sensors/sensor-management/) | `SensorManager` + `SensorEventListener` for detecting sensor changes with runtime permission handling |

See [sensors/README.md](sensors/README.md) for the topic overview.

---

## NOTES.md Template

Every task folder contains a `NOTES.md` using this template. Copy it when starting a new task.

```markdown
# [App Name]

## 1. Task Assigned

- [What the teacher asked you to build — 2-4 bullets]

## 2. Concept Covered

- [The primary Android/Compose concept this task teaches — 2-4 bullets]

## 3. What Was Hard

- [The friction points, gotchas, and surprises — 2-4 bullets]

## 4. Key Code Snippet

[One focused block (5-15 lines) showing the most important part of the implementation]

```kotlin
// paste the key code here
```
```

**Rules:**
- 2-4 bullet points per section — no lengthy paragraphs
- Section 4 contains exactly one code snippet — the most important 5-15 lines
- No additional sections without a good reason

---

## Topic Decision Gate

When a new task arrives, pick its topic folder using this rule:

**Ask: "What is the primary concept this task teaches?"**

| If the task primarily teaches... | Topic folder |
|----------------------------------|-------------|
| UI layout, Compose components, navigation, theming | `ui/` |
| Audio, video, camera, media playback | `media/` |
| Data persistence, local database, file storage | `data/` |
| Network calls, REST APIs, remote data | `network/` |
| Background work, services, scheduling | `background/` |
| Sensors, location, hardware APIs | `sensors/` |

**If a task spans multiple concepts:** Use the concept the teacher is *introducing for the first time*. For example, the Jukebox app uses `MediaPlayer`, but the teacher's goal was `LazyVerticalGrid` — so it lives in `ui/`, not `media/`.

**If unsure:** Ask the teacher what concept they want you to focus on.

**Create a new topic folder** when a task does not fit any existing topic. Add it to this table.

---

## Package Naming Convention

**Convention going forward:** New apps use the package name `com.maxkeenti.<topic>.<taskname>` — for example, a future `ui/calculator/` app would use `com.maxkeenti.ui.calculator`.

**Legacy exception:** The two existing apps (`news`, `jukebox`) use flat package names (`com.maxkeenti.news`, `com.maxkeenti.jukebox`) without a topic segment. These are not being refactored — the churn is not worth the benefit for a private learning repo. Document this exception here so it is intentional, not an oversight.
