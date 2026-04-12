# Technology Stack

**Project:** Android Development Learning Repository
**Researched:** 2026-04-12
**Scope:** Repository organization and documentation — NOT runtime technology (Kotlin + Compose + Material 3 is already decided and fixed)

---

## What "Stack" Means Here

The runtime stack is frozen: Kotlin 2.2.10, Jetpack Compose (BOM 2024.09.00), Material 3, AGP 9.1.0. This document covers the **organizational and documentation stack** — how the repo is laid out, how notes are written, and what tooling is used to document learning.

---

## Recommended Organization Stack

### Repo Layout

| Decision | Recommendation | Why |
|----------|---------------|-----|
| Top-level grouping | Topic folders (`ui/`, `media/`, `kotlin-basics/`) | Tasks map to concepts; learner can find "everything about UI" in one place without scanning all projects |
| Task nesting | Topic folder contains task sub-folders (`ui/news/`, `media/jukebox/`) | Each task is a single teachable unit; nesting signals the relationship between concept and exercise |
| Project isolation | Each task folder is an independent Gradle project | Already established; maintains zero coupling between projects, each opens cleanly in Android Studio |
| Root README | One `README.md` at repo root listing all topics and their tasks | Entry point to the whole repo; table of contents for the learner's own reference |
| Task README | One `README.md` per task folder | The "what and why" of each exercise; lives next to the code it describes |
| No shared module | No `common/` or `shared/` Gradle module | Too early in the learning journey; shared modules introduce multi-project Gradle complexity that distracts from Android fundamentals |

### Canonical Folder Structure

```
android-development/
├── README.md                        # Repo-level index: all topics + tasks
│
├── ui/                              # Topic: Compose UI fundamentals
│   └── news/                        # Task: news card layout
│       ├── README.md                # Task notes (see schema below)
│       ├── app/
│       ├── build.gradle.kts
│       ├── settings.gradle.kts
│       └── gradle/
│
├── media/                           # Topic: audio and media playback
│   └── jukebox/                     # Task: MediaPlayer sound board
│       ├── README.md
│       ├── app/
│       └── ...
│
├── kotlin-basics/                   # Topic: language fundamentals (future)
│   └── data-classes/                # Task: data class exercises (example)
│       ├── README.md
│       └── ...
│
└── .planning/                       # GSD planning (not Android content)
```

### Topic Naming

Use lowercase-hyphenated names. Rationale: consistent with standard directory naming conventions, easy to type, visually distinct from Android project folders which use PascalCase.

| Topic | Folder Name | Content |
|-------|------------|---------|
| Compose UI building blocks | `ui/` | Layouts, components, theming, state |
| Audio, video, media APIs | `media/` | MediaPlayer, ExoPlayer |
| Kotlin language | `kotlin-basics/` | Data classes, coroutines, lambdas |
| State management | `state/` | remember, ViewModel, StateFlow |
| Navigation | `navigation/` | NavController, deep links |
| Lists and grids | `lists/` | LazyColumn, LazyGrid, pagination |
| Networking | `networking/` | Retrofit, Ktor (when introduced) |
| Persistence | `persistence/` | Room, DataStore |

Add topic folders only when a task arrives that belongs there. Do not create empty topic folders speculatively.

### Task Folder Naming

Use the task name as the folder name, lowercase-hyphenated. Match what the teacher calls the exercise if possible.

Good: `ui/news/`, `media/jukebox/`, `kotlin-basics/data-classes/`
Bad: `ui/Task1/`, `media/project2/`, `ui/newsApp2025/`

Rationale: The folder name is the primary label when scanning. Numeric or date-stamped names tell you nothing about what was built. Descriptive names make `ls` immediately readable.

---

## Documentation Stack

### Format

Plain Markdown (`.md`). No wiki system, no static site generator, no Notion, no Confluence.

Rationale: Markdown renders natively in GitHub, Android Studio, and every code editor. It requires zero tooling. It stays in the repo alongside the code. A learner can read it without opening a browser. For a personal learning repo, anything with more setup overhead than a text file is friction that gets abandoned.

### README Schema — Task Level

Every task folder gets exactly one `README.md` using this structure:

```markdown
# [Task Name]

**Topic:** [parent topic, e.g., Compose UI]
**Assigned:** [date teacher assigned it, YYYY-MM-DD]
**Status:** complete | in-progress

## What the Task Was

[One paragraph: what the teacher asked you to build. Write it in your own words.]

## What This Teaches

[Bullet list of Android/Kotlin concepts this exercise introduces.]

- Concept A
- Concept B

## What I Built

[One paragraph: describe what the app does. Be specific — what does the user see/do?]

## Key Code

[1-3 short inline code snippets of the most important thing learned. Not the whole file — just the concept.]

```kotlin
// example: how LazyVerticalGrid works
LazyVerticalGrid(columns = GridCells.Fixed(3)) {
    items(tracks) { track -> TrackCard(track) }
}
```

## What Was Hard / What I'd Do Differently

[Honest notes. What confused you? What would you change now that you understand more?]
```

Rationale for each section:
- **What the task was** — the teacher's intent, in case the code drifts from the original ask
- **What this teaches** — forces reflection; the learner must name the concept, not just run the code
- **What I built** — separates "what was asked" from "what was actually made" (they sometimes differ)
- **Key code** — the one snippet a future-self would need to remember; discourages copy-pasting the entire file
- **What was hard** — the most valuable section for future-self; captures confusion while it's fresh

### README Schema — Root Level

```markdown
# Android Development Learning

Personal repository documenting my Android learning path. Each topic folder contains tasks assigned by my teacher.

## Topics

| Topic | Folder | Tasks |
|-------|--------|-------|
| Compose UI | `ui/` | [news](ui/news/) |
| Media Playback | `media/` | [jukebox](media/jukebox/) |

## How This Works

- Each task lives in `topic/task-name/`
- Each task has a `README.md` explaining what it was, what it taught, and what I built
- All projects use Kotlin + Jetpack Compose + Material 3
```

Update the Topics table every time a new task is added. This is the only file that requires updating when adding a project.

---

## What NOT to Use

| Tool | Why Not |
|------|---------|
| Jekyll / MkDocs / Docusaurus | Static site generators require build pipelines, hosting decisions, and maintenance. This is private reference documentation — it will never be hosted publicly. |
| GitHub Wiki | Disconnected from the code; changes don't appear in the same commit as code changes; harder to see "notes + code" together |
| Jupyter Notebooks | Android code is not Python; notebooks don't run Kotlin/Compose |
| Notion / Confluence | External tools that break the "notes live next to code" principle. They also require a login and create a sync problem when the repo and the external tool diverge. |
| Multiple docs folders | A top-level `docs/` directory that mirrors the topic structure creates two places to look: the code and the notes. Single-location principle: one README per task, in the task folder. |
| CHANGELOG.md per project | Overhead without benefit. The learner is not shipping releases. Git history serves this purpose. |
| KDoc on all functions | Not appropriate for early-stage learning code. KDoc is for library APIs. Add it when the teacher introduces documentation as a concept. |

---

## Existing Projects: Where They Go

| Current Path | New Path | Topic Rationale |
|-------------|----------|----------------|
| `news/` | `ui/news/` | Core task is building a Compose UI card layout — Compose UI fundamentals |
| `jukebox/` | `media/jukebox/` | Core task is MediaPlayer audio playback — media API usage |

Both apps already work. The move is a directory rename + README addition, not a code change. The Gradle project structure inside each folder stays identical.

---

## Confidence Assessment

| Decision | Confidence | Basis |
|----------|------------|-------|
| Topic/task two-level folder structure | HIGH | This is the canonical pattern used in virtually every learning/tutorial monorepo (Google Codelabs, official Jetpack samples, Android-Samples GitHub org all use topic-first grouping). Observed directly in how the PROJECT.md already describes the requirement. |
| Plain Markdown for notes | HIGH | The existing codebase has zero non-Markdown documentation tooling. The PROJECT.md requirement is "a README or notes file" — no mention of a system. Markdown is the zero-friction choice. |
| Task README schema | MEDIUM | Schema is derived from common "learning log" conventions in developer communities. The specific sections are opinionated; the teacher may have their own preferred format. Adjust when teacher provides a template. |
| Do not use static site generators | HIGH | The PROJECT.md explicitly says "Out of scope: Public sharing or portfolio polish." Hosting tooling is definitionally out of scope. |
| No shared Gradle module | HIGH | PROJECT.md says "Keep apps self-contained." Explicitly decided. No shared module coupling. |

---

## Sources

- Codebase analysis: `.planning/codebase/STRUCTURE.md`, `.planning/codebase/ARCHITECTURE.md`, `.planning/codebase/CONVENTIONS.md`
- Project requirements: `.planning/PROJECT.md`
- Pattern basis: Google Android Samples GitHub organization uses topic-first folder grouping (e.g., `compose-samples` repo, `architecture-samples` repo). Confidence HIGH based on training data; could not verify with web search (tool unavailable in this session).
