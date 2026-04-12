# Domain Pitfalls: Android Learning Repository

**Domain:** Personal Android learning documentation repo
**Researched:** 2026-04-12
**Confidence:** HIGH (grounded in existing codebase analysis + established Android/Compose patterns)

---

## Critical Pitfalls

Mistakes that cause rewrites, loss of learning value, or repo becoming useless as a reference.

---

### Pitfall 1: Apps at Root With No Topic Structure

**What goes wrong:** Projects pile up at the repo root — `news/`, `jukebox/`, `todo/`, `counter/` — with no grouping. After 10+ tasks the repo becomes a flat list with no way to find "everything about state management" or "everything about UI layouts."

**Why it happens:** It feels fast to drop a new project at the root. Topic grouping requires deciding categories upfront, which feels like over-engineering before you know what's coming.

**Consequences:** The repo fails its core purpose. Looking back to find "how did I do layouts?" requires opening every project folder. The teacher assigns a new task and there is no obvious place to put it. Eventually the learner stops maintaining the repo.

**Warning signs:**
- More than 3 project folders at root with no subdirectory grouping
- Can't answer "where would I put a new navigation task?" in under 5 seconds
- Project names encode topic meaning (`ui-news`, `media-jukebox`) rather than folders doing that work

**Prevention:** Establish topic folders (`ui/`, `media/`, `kotlin-basics/`, `state/`) before relocating existing projects. The two current apps (`news/` → `ui/news/`, `jukebox/` → `media/jukebox/`) make the relocation small and low-risk right now. Delay makes it larger and harder.

**Phase that must address it:** Reorganization phase (before any new tasks are added). Every new task added at root makes the eventual migration worse.

---

### Pitfall 2: No README/Notes Per Project = Learning Value Evaporates

**What goes wrong:** Apps are built, committed, and left with no explanation of what the task was, what concept it taught, or what the learner found confusing or surprising. Three months later, the code is opaque.

**Why it happens:** Writing notes feels like extra work after the code is done. The learner thinks "I'll remember this" or plans to add notes later, and later never comes.

**Consequences:** The repo becomes a code archive with zero reflective value. The teacher's intent for each task is lost. A future learner (or the same person in 6 months) reads the code and cannot reconstruct what was being practiced.

**Warning signs:**
- Any project folder containing only code with no `.md` or `NOTES` file
- Notes files exist but contain only "TODO: write this"
- Notes describe only what the code does, not why the task was assigned or what concept it teaches

**Prevention:** Make the notes file the first thing created when starting a task, not the last. A two-sentence template — "Task: [what teacher asked]. Concept: [what this teaches]." — is enough to start and prevents blank-page paralysis. Notes should answer three questions: What was the task? What concept did it teach? What was surprising or hard?

**Phase that must address it:** Documentation bootstrapping phase, applied retroactively to `news/` and `jukebox/` before adding any new tasks.

---

### Pitfall 3: Skipping Architecture Concepts Because They Feel Premature

**What goes wrong:** The learner avoids ViewModel, state hoisting, and separation of concerns for too long because "the teacher hasn't introduced it yet." When the teacher does introduce it, there is no reference implementation to look back on. Worse, the learner has built habits around putting logic directly in `MainActivity` that are hard to unlearn.

**Why it happens:** The PROJECT.md correctly marks ViewModel/DI as out of scope for now. But "out of scope for building" and "out of scope for documenting when teacher introduces it" are different things. The risk is treating the boundary too rigidly.

**Consequences:** The repo misses the conceptual arc of Android architecture. When the teacher assigns a task involving ViewModel, there is no context explaining why the shift happened. The repo shows apps but not progression.

**Warning signs:**
- All apps share identical structure (single Activity, logic in `MainActivity`) with no variation as tasks progress
- No notes anywhere acknowledging "this pattern will need to change when we learn X"
- Teacher assigns ViewModel task and learner treats it as isolated, not as a replacement for a habit built in earlier tasks

**Prevention:** When the teacher introduces a new concept (ViewModel, navigation, state hoisting), add a note to the prior task's README: "Note: this task used [old pattern]. In [new task], this was replaced with [new concept] because [reason]." This creates explicit before/after comparisons that are extremely valuable for review.

**Phase that must address it:** This is a continuous habit, not a one-time fix. Establish the "link forward/back" note convention from the start.

---

### Pitfall 4: Treating Existing Code as Untouchable After Task Completion

**What goes wrong:** Once a task is "done," the learner never revisits it. Issues known at the time (media playback overlap, no error handling, hardcoded content) remain forever because fixing them feels like "changing the past."

**Why it happens:** Learning repos feel like historical records. Modifying past work feels like falsifying history. But a reference repo that contains known-broken code teaches broken patterns.

**Consequences:** The `jukebox/` app has a confirmed MediaPlayer overlap bug (multiple audio streams play simultaneously on rapid clicks). If this stays unfixed, the repo teaches the wrong pattern. The `news/` app has hardcoded German text that will confuse anyone (including the learner) revisiting it.

**Warning signs:**
- CONCERNS.md identifies bugs that have been known for more than one milestone without a fix task
- Past app code contains `// TODO` comments that were never addressed
- The learner avoids opening past projects when reviewing

**Prevention:** Distinguish between two types of revisits: (1) bug fixes that make the code non-misleading — do these as part of the reorganization phase; (2) architectural upgrades that introduce concepts not yet taught — defer these until the teacher introduces them. The MediaPlayer overlap fix is type 1. Adding ViewModel to jukebox is type 2.

**Phase that must address it:** Reorganization phase should include a pass to fix type-1 issues in both existing apps.

---

## Moderate Pitfalls

Mistakes that reduce repo quality without causing rewrites.

---

### Pitfall 5: Notes Describe Code Instead of Explaining Concepts

**What goes wrong:** Notes files read like inline code comments: "This composable takes a `title` parameter and renders it in a `Text` component." This restates what the code already shows. It adds no learning value.

**Why it happens:** Writing about code is hard. Describing what the code does is easier than explaining the concept it demonstrates or what made it click.

**Consequences:** Notes become redundant. The learner skips them when reviewing because they contain nothing the code doesn't already say. The purpose of documentation — capturing the "why" and the "aha moment" — is lost.

**Warning signs:**
- Notes contain more code blocks than prose
- Notes describe function signatures and parameters
- Notes do not mention what the learner found confusing before the task, or what became clear during it

**Prevention:** Use a fixed notes structure: (1) What was the task? One sentence from the teacher's instruction. (2) What Android concept does this demonstrate? (3) What was the hardest part? (4) What would I do differently now? Even rough answers to these four questions are more valuable than polished code descriptions.

**Phase that must address it:** Documentation bootstrapping phase. Apply to retroactive notes for `news/` and `jukebox/`.

---

### Pitfall 6: Jetpack Compose Recomposition Misunderstanding

**What goes wrong:** Beginners treat `@Composable` functions like imperative functions called once. They place side effects (MediaPlayer initialization, logging, network calls) directly inside composable function bodies instead of in `LaunchedEffect`, `DisposableEffect`, or `SideEffect`. The Compose runtime recomposes on every state change, running the function body again — which means MediaPlayer gets created repeatedly, logs fire constantly, and state behaves unpredictably.

**Why it happens:** Imperative thinking is the default. `@Composable` looks like a normal function. The recomposition mental model is non-obvious until something breaks in a confusing way.

**Consequences:** Audio playback bugs (the existing MediaPlayer overlap bug in `jukebox/` is partly a symptom of this), unexpected resource creation, memory pressure, UI glitches during rapid interaction.

**Warning signs:**
- `MediaPlayer.create()` or `MediaPlayer()` called inside a composable function body (not in a click handler or effect)
- State-dependent behavior that fires more or fewer times than expected
- Logging inside composables that shows redundant calls during normal interaction

**Prevention:** Notes for the Jukebox task should explicitly document the recomposition model. Any MediaPlayer initialization belongs in a `DisposableEffect` (so it's cleaned up when the composable leaves the composition) or in a click handler (so it fires only on user action). Never in the composable body itself.

**Phase that must address it:** Media/Jukebox documentation pass. Also relevant for any future tasks using state.

---

### Pitfall 7: `MainActivity` God Object Pattern

**What goes wrong:** All data, logic, and UI composition accumulates in `MainActivity.kt`. For single-screen apps this works. The pitfall is building the habit and not documenting why the pattern will not scale — so when the teacher introduces separation of concerns, the learner has no mental model for what was wrong.

**Why it happens:** Single-file apps are fast to build and feel complete. There is no immediate pain from mixing concerns at this scale.

**Consequences:** The current `jukebox/MainActivity.kt` already mixes data definition (`JukeboxItem` list), audio logic, and UI composition in one file. This is fine for the learning task but needs documentation acknowledging the future refactor path, or the learner thinks this is the right long-term pattern.

**Warning signs:**
- `MainActivity.kt` exceeds ~150 lines
- Data models defined inside the Activity file
- No comment or note acknowledging "this would move to ViewModel/Repository when the teacher introduces it"

**Prevention:** Add a single note to the Jukebox README: "All logic lives in MainActivity for now. When ViewModel is introduced, the audio playback and item list will move out. This is intentional at this stage." This reframes a temporary pattern as a conscious choice, not an oversight.

**Phase that must address it:** Documentation bootstrapping for `jukebox/`.

---

### Pitfall 8: Inconsistent Project Naming and Package Structure

**What goes wrong:** Each new project uses a different package naming convention or folder name style. `com.maxkeenti.news` and `com.maxkeenti.jukebox` are consistent now, but as more tasks are added under topic folders, package names may drift from folder paths.

**Why it happens:** Package names are set once at project creation and rarely reconsidered. When projects move into topic folders, the folder path (`ui/news/`) diverges from the package name if the package isn't updated.

**Consequences:** The repo becomes hard to navigate. Grep for a concept finds files in unexpected packages. New projects created from templates inherit stale conventions.

**Warning signs:**
- Package name does not reflect the topic folder it lives in (e.g., `com.maxkeenti.news` inside `ui/news/`)
- Two projects in the same topic folder use different package name patterns
- A new project is created by copying an old one and the package name wasn't updated

**Prevention:** Establish a naming convention during the reorganization phase and document it. Simplest convention: `com.maxkeenti.[topic].[taskname]`. Apply retroactively to the two existing apps or document the exception explicitly.

**Phase that must address it:** Reorganization phase. Define the convention, apply it or note the exception.

---

## Minor Pitfalls

Easy to fix but worth avoiding from the start.

---

### Pitfall 9: Placeholder Tests Giving False Confidence

**What goes wrong:** Android Studio generates `ExampleUnitTest.kt` and `ExampleInstrumentedTest.kt` in every new project. These files contain passing tests (`assertEquals(4, 2 + 2)`). The test suite reports "all tests pass" even though nothing real is tested.

**Why it happens:** The files are auto-generated and easy to ignore. At the beginner level, tests feel optional.

**Consequences:** The learner is trained to see green test output as meaningful. When they write real code, they expect tests to catch regressions, but the tests catch nothing. The false positive is worse than having no test output at all.

**Warning signs:**
- Test files contain only `assertEquals(4, 2 + 2)` or similar arithmetic assertions
- Test count is always exactly 2 per project (one unit, one instrumented)
- No test has ever failed in the repo

**Prevention:** Delete the placeholder test files when a project is set up. Do not replace them with real tests immediately — just remove the false signal. When the teacher introduces testing, add real tests then. An empty test suite is more honest than a passing-but-useless one.

**Phase that must address it:** Reorganization phase. Delete placeholder tests from both existing apps.

---

### Pitfall 10: Hardcoded Content That Obscures the Concept Being Taught

**What goes wrong:** The News app contains hardcoded German text with mixed-language content ("Lustig", German article body, English labels). This makes it unclear whether the task was about displaying foreign content, internationalization, or simply displaying a card layout. The concept being taught (card UI layout) is obscured by unexplained content choices.

**Why it happens:** Placeholder content gets reused from wherever it came from (a real news article, a test string) without considering how it reads to a future reviewer.

**Consequences:** Reviewing the News app six months later, the learner cannot tell if the German text was intentional (a localization task) or accidental (just placeholder content). The task's teaching intent is lost.

**Warning signs:**
- Content in an app doesn't match the language the rest of the repo is written in
- Placeholder content is specific enough to suggest real data but not connected to any data source
- No notes file explaining why that particular content was used

**Prevention:** Use obviously fake placeholder content (e.g., "Lorem Ipsum Article Title", "Author Name", "2026-01-01") for tasks that are about layout/UI, not content. Or note explicitly: "Content is in German because the task source material was German. The concept being practiced is card layout, not localization."

**Phase that must address it:** News app documentation pass during documentation bootstrapping.

---

## Phase-Specific Warnings

| Phase Topic | Likely Pitfall | Mitigation |
|-------------|---------------|------------|
| Reorganizing `news/` and `jukebox/` into topic folders | Breaking existing Gradle build paths during move | Move at the file system level, update `settings.gradle.kts` include paths, verify `./gradlew build` passes after move before committing |
| Writing retroactive notes for `news/` | Notes describe code instead of teaching intent | Use the four-question template: task / concept / hardest part / what I'd do differently |
| Writing retroactive notes for `jukebox/` | Skipping the MediaPlayer lifecycle explanation because it feels complex | The MediaPlayer overlap bug is the most instructive thing in the repo — document it explicitly as "known limitation, here's why it happens" |
| Adding first new task from teacher | Dropping it at repo root instead of inside a topic folder | Decision gate: before writing any code, decide which topic folder it belongs in |
| Introducing state in future tasks | Treating Compose recomposition like imperative rendering | Add a recomposition explainer note to the first task that uses `remember {}` or `mutableStateOf()` |
| Teacher introduces ViewModel | No prior task connects the new concept to old patterns | Retroactively add a note to the Jukebox README linking its MediaPlayer chaos to the ViewModel solution |
| Repo grows past 5-6 projects | Root-level README not updated, making repo navigation hard | Maintain a root `README.md` as an index: topic → task → what it teaches |

---

## Sources

- Codebase analysis: `.planning/codebase/CONCERNS.md` (2026-04-12) — HIGH confidence
- Project intent: `.planning/PROJECT.md` (2026-04-12) — HIGH confidence
- Android Compose lifecycle and recomposition behavior: training data on Jetpack Compose runtime model (MEDIUM confidence — well-established behavior, verify with official Compose docs if implementing `DisposableEffect` or `LaunchedEffect` for the first time)
- Learning repo organization patterns: training data on personal knowledge management and dev learning repo conventions (MEDIUM confidence)
