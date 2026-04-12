# Project Research Summary

**Project:** Android Development Learning Repository
**Domain:** Personal learning documentation monorepo (Kotlin + Jetpack Compose)
**Researched:** 2026-04-12
**Confidence:** HIGH

## Executive Summary

This is a personal learning repository where a beginner Android developer documents tasks assigned by a teacher. Two apps already exist (`news/`, `jukebox/`) but have no accompanying notes, no topic-based structure, and known bugs. The goal is not to build software for users — it is to create a structured reference that future-self can open in six months and immediately understand what was built, why, and what concept each task was teaching. The entire "product" is the organization system and the notes alongside the code.

The recommended approach is minimal and friction-free: a two-level folder hierarchy (`topic/task-name/`), plain Markdown notes co-located with each app, and a root `README.md` as a navigable index. The runtime stack (Kotlin 2.2.10, Jetpack Compose, Material 3, AGP 9.1.0) is already fixed and requires no decisions. Every organizational decision should reduce friction for adding the next task, not add structure for its own sake. No shared Gradle modules, no static site generators, no external documentation tools.

The primary risks are the two that have already materialized: apps without notes (learning value evaporates), and a flat root structure (becomes unsearchable after 10+ tasks). Both must be addressed before any new teacher tasks land — the migration is small now and grows exponentially if deferred. A secondary continuous risk is over-engineering: adding ViewModel, CI, linting, or tests before the teacher introduces those concepts obscures what each task was actually teaching.

---

## Key Findings

### Recommended Stack

The runtime stack is frozen and should not change. The organizational "stack" is the relevant decision space, and the research conclusion is clear: plain Markdown notes, two-level topic/task folder hierarchy, zero external tooling.

**Core technologies:**
- Kotlin 2.2.10 + Jetpack Compose (BOM 2024.09.00) + Material 3: already in use — do not change
- Plain Markdown (`.md`): documentation format — renders natively in GitHub and Android Studio, zero tooling, stays in-repo next to code
- Self-contained Gradle projects per task: already established — each task opens cleanly in Android Studio with no cross-project dependencies

**Do not introduce:**
- Static site generators (Jekyll, MkDocs, Docusaurus) — out of scope, no hosting needed
- Shared Gradle modules — breaks task independence and introduces complexity before it is taught
- External documentation tools (Notion, GitHub Wiki) — creates sync problems and disconnects notes from code

### Expected Features

**Must have (table stakes):**
- `NOTES.md` per task folder — without this, code is opaque after a few weeks; this is the atomic unit of the repo
- Task assignment captured in notes — the teacher's intent is the anchor; once lost, the project loses its educational frame
- Concepts covered list in notes — makes the repo searchable by topic in the learner's own mind
- Topic-based folder structure (`ui/`, `media/`, `kotlin-basics/`) — required before task count exceeds 5; harder to add retroactively
- Root `README.md` as index — the entry point; a reader hitting the repo root must immediately understand how it is organized
- Working, runnable code for every task — notes without code are theory; code without notes is archaeology

**Should have (differentiators):**
- "What I struggled with" section in notes — the most valuable learning signal; impossible to reconstruct later
- Key code snippet (5-10 lines) called out in notes — anchors explanation to something concrete without duplicating the whole file
- "What I would do differently" retrospective note — forces synthesis; accelerates learning
- Forward/backward links between task notes when a concept evolves (e.g., "ViewModel will replace this pattern in task X")

**Defer to later:**
- Topic-level `README.md` per topic folder — add only when a topic has 2+ tasks
- Concept index or glossary — only valuable at 8+ projects; premature before that
- Screenshots or screen recordings — optional, add selectively; adds repo size
- Per-topic summary files — low priority, useful only at scale

### Architecture Approach

The repository architecture is a flat-ish monorepo with exactly two levels of nesting under the root: topic folder, then task folder. Each task folder is a complete, self-contained Android Studio project. Notes files live as siblings to the `app/` directory inside each task folder — never in a separate `docs/` directory. Topic folders are created on-demand when the teacher assigns a task that belongs there; no pre-creating empty folders.

**Major components:**
1. Topic folder (`ui/`, `media/`, `kotlin-basics/`, etc.) — groups all tasks teaching the same concept area; never contains Gradle build files
2. Task folder (`news/`, `jukebox/`, etc.) — one complete Android app per teacher-assigned task; fully self-contained Gradle project; child of exactly one topic
3. `NOTES.md` inside each task — the primary documentation artifact; answers: what was assigned, what concept it teaches, what was built, key code, what was hard
4. Root `README.md` — topic-to-task index; the only file that must be updated every time a new task is added

**Learning progression topic ordering** (concept dependencies drive this, teacher drives actual task timing):
- Stage 1: `kotlin-basics/` (language before framework)
- Stage 2: `ui/` (single-screen composable apps, no state — where both existing apps belong)
- Stage 3: `state/` (remember, mutableStateOf, recomposition)
- Stage 4: `navigation/` (NavController, multi-screen)
- Stage 5: `data/` (Room, DataStore)
- Stage 6: `architecture/` (ViewModel, Repository, DI — explicitly deferred in PROJECT.md)
- Stage 7: `media/` (MediaPlayer lifecycle, permissions — distinct from UI usage of media)
- Stage 8: `networking/` (Retrofit, coroutines — out of scope per PROJECT.md)

### Critical Pitfalls

1. **Flat root structure** — All apps at repo root with no topic grouping becomes unsearchable after 10+ tasks. Establish topic folders now while migration is a 2-project rename. Every new task added at root makes eventual migration worse.

2. **No notes per project** — Without notes, code is archaeology after weeks. Three months later the teacher's intent is gone. Write the notes file first when starting a task; a two-sentence template prevents blank-page paralysis. Apply retroactively to both existing apps immediately.

3. **Notes that describe code instead of explaining concepts** — "This composable takes a `title` parameter and renders it in a `Text`" restates what the code already shows. Notes must answer: what was the task, what concept does it demonstrate, what was hardest, what would I do differently. Prose over code blocks.

4. **Treating existing code as untouchable** — The `jukebox/` app has a known MediaPlayer overlap bug (multiple streams on rapid click) and `news/` has unexplained German text. Type-1 fixes (things that teach wrong patterns) should happen during reorganization. Type-2 upgrades (ViewModel, etc.) wait for teacher introduction.

5. **Premature architecture or tooling** — Adding ViewModel, DI, CI, linting, or tests before the teacher introduces them obscures what a task was teaching. Keep apps at the complexity level the teacher introduced.

---

## Implications for Roadmap

Based on research, suggested phase structure:

### Phase 1: Reorganize and Bootstrap Documentation

**Rationale:** The repo currently has code without notes and apps at the wrong location. This is the highest-risk state — every day without notes is learning value lost, and every new task added at root makes restructuring harder. This phase addresses the two critical pitfalls simultaneously and establishes the pattern all future tasks follow.

**Delivers:**
- Both existing apps moved into correct topic folders (`news/` to `ui/news/`, `jukebox/` to `ui/jukebox/`)
- `NOTES.md` written for both apps using the four-question template (task / concepts / what was hard / what I'd do differently)
- Root `README.md` as navigable index
- MediaPlayer overlap bug documented in jukebox notes as a known limitation with explanation
- German text in news app explained or replaced with clearly labeled placeholder content
- Placeholder test files deleted from both apps

**Addresses:** All table-stakes features from FEATURES.md

**Avoids:** Pitfall 1 (flat root), Pitfall 2 (no notes), Pitfall 4 (untouchable past code), Pitfall 9 (placeholder tests giving false confidence)

### Phase 2: Establish the Task Addition Workflow

**Rationale:** Once the existing apps are documented and reorganized, the next challenge is ensuring every future task lands correctly the first time — in the right topic folder, with notes written before the task is marked done. This phase is about habit and convention, not code.

**Delivers:**
- Documented decision gate: before any new task is written, decide which topic folder it belongs in
- Confirmed `NOTES.md` template that the learner will actually fill in (simple = filled in; complex = stays blank)
- Package naming convention documented (`com.maxkeenti.[topic].[taskname]`) and applied or exceptions noted
- Process for when teacher assigns a task that crosses topic boundaries (primary lesson determines the folder)

**Addresses:** Differentiator features (forward/back concept links, "what I struggled with" habit)

**Avoids:** Pitfall 5 (notes describing code instead of concepts), Pitfall 8 (inconsistent package naming), Pitfall 1 (new tasks landing at root)

### Phase 3: Ongoing Task Accumulation (Continuous)

**Rationale:** The repo grows indefinitely as teacher assigns new tasks. This is not a phase with an end — it is the steady-state operation. The structure from Phases 1 and 2 makes this trivially repeatable.

**Delivers:**
- Each teacher-assigned task appears in the correct topic folder with a `NOTES.md`
- Root `README.md` updated with each addition
- Topic-level `README.md` added when any topic reaches 2+ tasks
- Concept glossary deferred until 8+ total projects exist
- Forward/back concept links added to notes when teacher introduces a concept that supersedes a pattern used in earlier tasks

**Avoids:** Pitfall 3 (skipping architecture documentation when teacher introduces it), Pitfall 6 (recomposition misunderstanding in future state tasks), Pitfall 7 (MainActivity god object going undocumented)

### Phase Ordering Rationale

- Phase 1 comes first because the learning value loss from missing notes is irreversible — reconstruction from memory gets harder every week
- Phase 1 is bounded and completable: exactly two apps need documentation and relocation; no open-ended scope
- Phase 2 is a habits and convention phase, not a code phase; it costs little and prevents drift as the repo grows
- Phase 3 is indefinite by design — PROJECT.md explicitly states "A fixed end date or curriculum — this grows indefinitely as learning continues"
- Architecture complexity (ViewModel, DI, networking) is not a phase — it is a teacher-gated event that appears inside Phase 3 when assigned

### Research Flags

Phases with well-documented patterns (no additional research needed):
- **Phase 1:** Fully resolved by research. Exact file moves, README templates, and note content are specified in STACK.md, FEATURES.md, and ARCHITECTURE.md.
- **Phase 2:** Conventions are clear. Package naming and topic decision gate require no external research.

Phases that may surface new questions during execution:
- **Phase 3 (state management tasks):** When the teacher introduces `remember` and `mutableStateOf`, the Compose recomposition model needs careful documentation. PITFALLS.md flags this (Pitfall 6) — if a state task arrives, consider a targeted research pass on `DisposableEffect` and `LaunchedEffect` before writing the notes.
- **Phase 3 (ViewModel introduction):** When the teacher introduces ViewModel, retroactively linking earlier apps to the new pattern is important. The convention is documented in PITFALLS.md and must be applied deliberately.

---

## Confidence Assessment

| Area | Confidence | Notes |
|------|------------|-------|
| Stack | HIGH | Runtime stack is fixed; organizational stack decisions are grounded in direct codebase analysis and well-established monorepo conventions |
| Features | HIGH | Derived from direct analysis of existing repo state and PROJECT.md requirements; what is missing is observable fact |
| Architecture | HIGH | Two-level topic/task hierarchy is canonical for learning repos; confirmed by Google Android Samples patterns; concept dependency ordering derived from official Android curriculum structure |
| Pitfalls | HIGH | Critical pitfalls 1 and 2 are already present in the repo (observable); pitfalls 3-8 are grounded in Compose runtime behavior and common learning repo failure modes |

**Overall confidence:** HIGH

### Gaps to Address

- **Task file naming (README.md vs NOTES.md):** STACK.md recommends `README.md` per task; FEATURES.md and ARCHITECTURE.md recommend `NOTES.md`. Pick one before Phase 1 begins. Recommendation: use `NOTES.md` to avoid confusion with the topic-level and root-level `README.md` files.
- **`jukebox/` primary topic classification:** Research places `jukebox/` in `ui/jukebox/` because the primary lesson is LazyVerticalGrid. Confirm with teacher if a future jukebox-evolution task belongs in `ui/` or `media/` depending on what that task is teaching.
- **Package name retroactive change:** Changing `com.maxkeenti.news` to `com.maxkeenti.ui.news` requires a refactor inside the app. Evaluate whether the benefit justifies the churn; alternatively document the legacy exception explicitly and move on.

---

## Sources

### Primary (HIGH confidence)
- `.planning/PROJECT.md` — project requirements, scope boundaries, existing app inventory
- `.planning/codebase/STRUCTURE.md`, `ARCHITECTURE.md`, `CONVENTIONS.md` — existing codebase state
- `.planning/codebase/CONCERNS.md` — known bugs in existing apps (MediaPlayer overlap, German text)

### Secondary (MEDIUM confidence)
- Google Android Samples GitHub organization — topic-first folder grouping pattern (training data; live verification unavailable)
- Android developer curriculum (developer.android.com/courses) — topic ordering by concept dependency (training data; dependency relationships are HIGH confidence)
- Google Codelabs notes-alongside-code pattern — confirmed by training data; no live verification

### Tertiary (LOW confidence)
- Specific Compose `DisposableEffect` and `LaunchedEffect` behavior for MediaPlayer lifecycle — well-established in training data but should be verified against official Compose documentation when implementing

---
*Research completed: 2026-04-12*
*Ready for roadmap: yes*
