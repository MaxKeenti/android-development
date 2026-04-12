# Architecture Patterns

**Domain:** Android learning repository (Kotlin + Jetpack Compose)
**Researched:** 2026-04-12
**Confidence:** HIGH (grounded in existing codebase analysis + established Android curriculum conventions)

## Recommended Architecture

A two-level hierarchy: topic folders at the root, task folders inside each topic. Each task folder is a self-contained Android Studio project. Notes live next to code.

```
android-development/                  # Monorepo root
├── ui/                               # Topic: Compose UI fundamentals
│   ├── news/                         # Task: news card (existing, relocated)
│   └── {next-ui-task}/               # Task: (assigned by teacher)
├── media/                            # Topic: Audio and video playback
│   └── jukebox/                      # Task: sound board (existing, relocated)
├── kotlin-basics/                    # Topic: Kotlin language fundamentals
│   └── {first-kotlin-task}/          # Task: (assigned by teacher)
├── state/                            # Topic: Compose state management
│   └── {first-state-task}/
├── navigation/                       # Topic: multi-screen navigation
│   └── {first-nav-task}/
├── data/                             # Topic: local data persistence
│   └── {first-data-task}/
├── .planning/
├── .claude/
└── README.md                         # Optional: index of all topics and tasks
```

Each task folder IS a complete, runnable Android Studio project. No shared modules, no Gradle multi-project setup across topics.

## Component Boundaries

| Component | Responsibility | Relationship |
|-----------|---------------|--------------|
| Topic folder (`ui/`, `media/`, etc.) | Groups all tasks teaching the same concept area | Parent container; never contains build files of its own |
| Task folder (`news/`, `jukebox/`, etc.) | One complete Android app; one teacher-assigned assignment | Child of exactly one topic; fully self-contained Gradle project |
| `NOTES.md` inside each task | Explains what the assignment was, what concept it teaches, what the learner observed | Sibling to `app/` inside the task folder |
| App source code | The working Android app (Kotlin + Compose) | Standard Android project structure unchanged |

### What "self-contained" means for each task

Every task folder has its own:
- `settings.gradle.kts` (so it opens directly in Android Studio)
- `app/build.gradle.kts`
- `gradle/libs.versions.toml`
- Gradle wrapper (`gradlew`, `gradle/wrapper/`)
- `NOTES.md` (what was assigned, what concepts were practiced)

No task depends on another task at the Gradle level. This is already established by the existing projects and must be preserved.

## Notes File: Placement and Shape

Notes live inside the task folder, one level above `app/`:

```
ui/news/
├── NOTES.md          <-- notes here, NOT in a separate docs folder
├── app/
│   └── src/...
├── build.gradle.kts
└── settings.gradle.kts
```

`NOTES.md` is the primary documentation artifact per task. It answers:
1. **Assignment** — What did the teacher ask me to build?
2. **Concepts** — What Android/Kotlin concepts does this task teach?
3. **What I built** — One-paragraph summary of the solution
4. **Key patterns** — Code snippets of the most important concepts (2-4 examples)
5. **What I learned** — What was surprising, hard, or newly understood

This structure means when you open a task folder in Android Studio you see the notes alongside the code without switching directories.

## Learning Progression: Topic Ordering

Topics build on each other. The ordering below reflects concept dependencies, not a rigid schedule — the teacher drives which specific task comes next, but this order governs which topic folder to put it in.

### Stage 1: Kotlin Language (topic: `kotlin-basics/`)
Covers the language before the framework. These are typically short programs, not full apps.

Concepts: val/var, data classes, functions, lambdas, null safety, collections (list, map, filter), when expressions, object/companion object, string templates.

**Existing work that fits here:** Nothing yet. The existing apps already use Kotlin but assume the language; a dedicated kotlin-basics task would be language-first.

### Stage 2: Compose UI Fundamentals (topic: `ui/`)
Single-screen apps. No state. Hardcoded data. Focus is on how Compose renders UI declaratively.

Concepts: `@Composable` functions, `Column`, `Row`, `Box`, `Text`, `Image`, `Card`, `Scaffold`, `LazyColumn`, `LazyVerticalGrid`, `Modifier`, Material 3 components, theming.

**Existing work that fits here:** `news/` teaches Card layout + hardcoded content. `jukebox/` teaches `LazyVerticalGrid` + image display. Both belong in `ui/`.

Note: `jukebox/` also teaches media playback, which is a `media/` concern. It belongs in `ui/` because the primary lesson is the grid layout — the audio is secondary. If teacher assigns a task where MediaPlayer IS the lesson (no grid, pure playback API), that goes in `media/`.

### Stage 3: State Management (topic: `state/`)
Apps where user interaction changes what is shown. Introduces `remember`, `mutableStateOf`, recomposition.

Concepts: `remember`, `mutableStateOf`, `State<T>`, hoisting state, `rememberSaveable`, side effects (`LaunchedEffect`, `DisposableEffect`).

Dependencies: Stage 2 (must understand composable functions before adding state to them).

### Stage 4: Navigation (topic: `navigation/`)
Multi-screen apps. Introduces Compose Navigation.

Concepts: `NavController`, `NavHost`, `composable()` routes, passing arguments between screens, back stack management.

Dependencies: Stage 2 + Stage 3 (navigation between screens requires each screen to be a composable; state is often needed per screen).

### Stage 5: Local Data (topic: `data/`)
Persisting data beyond app lifecycle. Introduces Room or DataStore.

Concepts: Room database, DAOs, `@Entity`, `@Database`, Flow, DataStore (preferences), reading/writing files.

Dependencies: Stage 3 (reactive data requires understanding state flow); Stage 4 optional but useful (data usually spans multiple screens).

### Stage 6: Architecture Patterns (topic: `architecture/`)
Separating concerns. Introduces ViewModel, Repository, Dependency Injection.

Concepts: `ViewModel`, `viewModel()` composable, `StateFlow`, `collectAsState()`, Repository pattern, Hilt or Koin (DI).

Dependencies: Stage 3, Stage 5. This is explicitly deferred in PROJECT.md until the teacher introduces it.

### Stage 7: Media and Platform APIs (topic: `media/`)
Device APIs beyond pure UI. Begins with audio but expands.

Concepts: `MediaPlayer`, `ExoPlayer`, `AudioManager`, camera, sensors, permissions.

**Existing work:** `jukebox/` uses `MediaPlayer`. The grid UI lesson fits better in `ui/`. A future task focused purely on playback control, lifecycle-aware media, or permissions belongs in `media/`.

### Stage 8: Networking (topic: `networking/`)
Fetching remote data. Introduces Retrofit, Ktor, or `HttpURLConnection`.

Concepts: Coroutines, `suspend` functions, `Flow`, REST APIs, JSON parsing (Gson/Kotlinx-serialization), error handling.

Dependencies: Stage 5 (persisting fetched data), Stage 6 (ViewModels calling repositories that call network). Currently out of scope per PROJECT.md.

## Placing Existing Apps

### `news/` → `ui/news/`
- Primary concept: Compose Card layout, Column/Row composition, hardcoded data display
- No state, no navigation, no media — pure UI topic
- Move by: `mv news/ ui/news/` at repository root

### `jukebox/` → `ui/jukebox/`
- Primary concept: `LazyVerticalGrid`, `Image`, user tap interaction
- Secondary concept: `MediaPlayer` (audio playback)
- The grid+image lesson is Stage 2 (UI). The MediaPlayer usage is incidental to the primary lesson.
- If teacher later assigns a task where the entire lesson is MediaPlayer lifecycle, permissions, or audio focus — that task goes in `media/`, not `jukebox/` continued.
- Move by: `mv jukebox/ ui/jukebox/` at repository root

Both apps need a `NOTES.md` added after relocation.

## Topic Folder Naming Conventions

| Topic Folder | What Goes In It |
|-------------|----------------|
| `kotlin-basics/` | Language-level exercises (not full Android apps necessarily) |
| `ui/` | Any task where the lesson is composable layout, Material components, theming |
| `state/` | Any task where the lesson is `remember`, `mutableStateOf`, recomposition |
| `navigation/` | Any task introducing NavController or multi-screen structure |
| `data/` | Room, DataStore, file I/O tasks |
| `architecture/` | ViewModel, Repository, DI tasks |
| `media/` | Audio/video/camera platform API tasks |
| `networking/` | Retrofit, Ktor, coroutines+network tasks |

Create a topic folder only when the teacher assigns a task that belongs there. Do not pre-create empty topic folders.

## Anti-Patterns to Avoid

### Anti-Pattern 1: Flat Root
**What:** All apps at the root — `news/`, `jukebox/`, `counter/`, `todo/`, ...
**Why bad:** By task 10, it's a flat list with no conceptual grouping. Finding "everything about state" requires reading every app's notes.
**Instead:** Topic folders. `ui/news/`, `state/counter/`, `state/todo/`.

### Anti-Pattern 2: Separate Docs Folder
**What:** `docs/news-notes.md`, `docs/jukebox-notes.md` in a top-level `docs/` directory
**Why bad:** Notes are disconnected from the code they describe. Updating one without the other is easy to forget.
**Instead:** `NOTES.md` inside each task folder, next to `app/`.

### Anti-Pattern 3: Shared Gradle Module
**What:** A `common/` or `shared/` Gradle module referenced by multiple task apps
**Why bad:** Creates coupling between independent assignments. Changes to shared code break unrelated tasks. Defeats the purpose of each task being a standalone historical record.
**Instead:** Duplicate the boilerplate. Each app has its own theme, its own Gradle config. This is correct for a learning repo.

### Anti-Pattern 4: Reorganizing Apps Mid-Lesson
**What:** Moving a task from one topic folder to another after it's been built because the concepts evolved during the build
**Why bad:** Breaks the historical record; git history becomes confusing; the teacher's assignment context is lost.
**Instead:** Assign the topic at the start based on what the teacher said the lesson is about. Don't reorganize after the fact.

### Anti-Pattern 5: Adding Architecture Prematurely
**What:** Adding ViewModel, Repository, Hilt to early apps because it feels "more correct"
**Why bad:** Obscures what the task was actually teaching. Mixes multiple concepts. PROJECT.md explicitly defers this.
**Instead:** Keep apps at the complexity level the teacher introduced. Add architecture patterns only when the teacher's task specifically introduces them.

## Scalability

| State | Structure | Concern |
|-------|-----------|---------|
| 2-5 apps | Flat root OR `ui/` | Not a problem yet |
| 6-15 apps | Topic folders essential | Without them, discovery fails |
| 15-40 apps | Topic folders + README index | Index becomes useful reference |
| 40+ apps | Topic folders + per-topic README | Each topic folder gets its own index |

The current repo (2 apps) is at the boundary where introducing topic folders now costs minimal migration effort and pays off immediately as tasks accumulate.

## Sources

- Existing codebase analysis: `.planning/codebase/ARCHITECTURE.md`, `.planning/codebase/STRUCTURE.md`
- Project goals: `.planning/PROJECT.md`
- Android learning progression derived from official Android developer curriculum structure (developer.android.com/courses) — HIGH confidence for topic ordering based on concept dependencies
- Notes-alongside-code pattern: consistent with how Google's Codelabs and Android samples organize explanatory content
