# Feature Landscape

**Domain:** Personal Android development learning documentation repository
**Researched:** 2026-04-12
**Confidence:** HIGH (grounded in project context; web search unavailable but conclusions are
based on direct analysis of the existing repo and well-established documentation practices)

---

## Table Stakes

Features the repo must have to be useful as a learning reference. Missing any of these and the
repo fails its core purpose: "look back and understand what was built, why, and what concept
it was teaching."

| Feature | Why Expected | Complexity | Notes |
|---------|--------------|------------|-------|
| Per-project notes file | Without it, code is meaningless after a few weeks. Memory of *why* fades fast. | Low | A `NOTES.md` (or `README.md`) per app folder. Should record the task assignment, concepts covered, and what the learner found surprising or hard. |
| Task assignment captured | Teacher assigns tasks; the task statement is the anchor for understanding. | Low | One or two sentences: "Task: build a news card that displays hardcoded content." If the assignment is lost, the project loses its educational frame. |
| Concepts covered list | Makes the repo searchable by topic in the learner's own mind. | Low | Bullet list: "Composable functions, Material 3 Card, hardcoded strings." No need for prose. |
| Topic-based folder structure | Without grouping, finding "everything I did with UI" requires scanning all projects. | Low | Top-level topic folders (`ui/`, `media/`, `kotlin-basics/`) with task subfolders inside. |
| Working, runnable code | Notes without code are theory. Code without notes is archaeology. Both must be present. | Already exists | Both `news/` and `jukebox/` already run. Do not break them during reorganization. |
| Root-level orientation file | A reader (future self) hitting the repo root should immediately understand what this repo is and how it is organized. | Low | A single `README.md` at root that explains the purpose, folder structure, and how to navigate. |

---

## Differentiators

Features that make a learning repo stand out from a bare code dump. These are not expected,
but they meaningfully increase the value of looking back.

| Feature | Value Proposition | Complexity | Notes |
|---------|-------------------|------------|-------|
| "What I struggled with" section in notes | Struggle points are the most valuable learning signal and the hardest to reconstruct later. | Low | One honest paragraph per project. "I didn't understand why `setContent` needed a lambda." This is unique to the learner's experience and cannot be regenerated from code. |
| Code snippets called out in notes | Notes that quote the key 5-10 lines of code anchor explanations to something concrete. | Low | Inline code blocks in Markdown pointing to the specific composable or pattern being documented. Do not copy-paste the entire file — just the interesting bit. |
| "What I would do differently" / retrospective note | Metacognition accelerates learning. Naming what you'd change forces synthesis. | Low | A single bullet or sentence per project. Optional once topics become more complex. |
| Topic-level summary file | A `README.md` inside each topic folder explaining what concepts the topic covers and listing its projects. | Low | Helps future self orient: "ui/ — covers Compose basics, layouts, Material 3 components." |
| Concept index or glossary | Cross-cutting terms (Composable, Activity, Material 3) defined once and linked from notes. | Medium | Only valuable once there are 8+ projects. Premature before that. |
| Screenshots or screen recordings in notes | Visual evidence of what the app looked like at the time of completion. | Medium | Images committed to the repo. Useful for before/after comparisons as complexity grows. Adds repo size; only worth it selectively. |

---

## Anti-Features

Things that would actively hurt this learning repo. These add weight, maintenance burden,
or misdirection without proportional learning value.

| Anti-Feature | Why Avoid | What to Do Instead |
|--------------|-----------|-------------------|
| Production-grade architecture (ViewModel, DI, Repository) added before teacher introduces it | Introduces complexity the learner doesn't yet understand. Obscures the concept being taught. | Add architecture patterns only when the teacher assigns a project that requires them. Let the code match the current learning level. |
| Shared Gradle module coupling apps together | The existing design (each app is a self-contained Gradle project) is correct. Coupling destroys independence. | Keep apps fully standalone. Never create a `:shared` or `:common` module unless explicitly assigned. |
| Centralized notes folder separate from code | Breaks the principle "explanations live next to the code they describe." Notes in a `docs/` folder become disconnected and fall out of sync. | Notes file belongs in the same folder as the app it describes. |
| Automated testing setup (unit tests, UI tests) | Not yet in scope. Adds Gradle complexity, a new mental model, and noise before fundamentals are solid. | Add tests only when teacher assigns a task that involves testing. |
| CI/CD pipeline or GitHub Actions | This is a private learning repo, not a product. CI adds configuration overhead with no learning payoff at this stage. | Skip entirely until the learner is comfortable with the full dev loop. |
| Enforced linting / style rules (ktlint, detekt) | Useful eventually, but forces mechanical fixes before the learner understands *why* a pattern is preferred. | Let code style be organic. Teacher feedback is the right linting mechanism at this stage. |
| Over-organized folder hierarchy (3+ levels deep) | Deep nesting makes adding a new project a ceremony. Kills momentum. | Two levels max: `topic/task-name/`. Flat-ish is better than over-structured. |
| Comprehensive wiki or gitbook | Creates a parallel documentation system that diverges from the code. Maintenance splits effort. | Keep docs co-located with code. Markdown in the repo is sufficient. |
| Changelog or release versioning | Inappropriate for a learning repo with no users and no releases. | Git history is the changelog. |
| Badges, shields, or portfolio polish | Out of scope (explicitly noted in PROJECT.md). Adds cosmetic burden to a private reference. | Skip entirely. |

---

## Feature Dependencies

```
Root README.md
  └── exists and describes topic folder structure
        └── Topic folders (ui/, media/, kotlin-basics/, ...)
              └── Topic README.md (what this topic covers)
                    └── Task folder (news/, jukebox/, ...)
                          └── Per-project NOTES.md
                                └── Task assignment captured
                                └── Concepts covered list
                                └── "What I struggled with" (differentiator)
                                └── Key code snippet (differentiator)
```

The per-project `NOTES.md` is the atomic unit. Everything else scaffolds around it.

---

## MVP Recommendation

Given the learner is at the very beginning and two apps already exist without any notes:

**Phase 1 priority — establish the pattern:**
1. Add a `NOTES.md` to `news/` capturing the task, concepts, and one struggle point
2. Add a `NOTES.md` to `jukebox/` with the same structure
3. Move both apps into topic folders (`ui/news/`, `media/jukebox/`) with no other changes
4. Add a root `README.md` explaining the repo structure

**Defer:**
- Topic-level `README.md` per topic folder: defer until there are 2+ projects in a topic
- Concept index / glossary: defer until 8+ projects exist
- Screenshots: optional, add selectively when a UI milestone is worth preserving
- "What I would do differently" note: optional, add it if writing comes naturally; do not force it

**The NOTES.md template should be dead simple:**

```markdown
# [App Name]

## Task
[One or two sentences describing what the teacher asked for]

## Concepts
- [Concept 1]
- [Concept 2]

## What I Struggled With
[Honest paragraph or bullets]

## Key Code
[5-10 line snippet of the most interesting part]
```

A simpler template gets filled in. A complex template stays blank.

---

## Sources

- Direct analysis of project context: `.planning/PROJECT.md` and `.planning/codebase/ARCHITECTURE.md`
- Observed repo state: `news/` and `jukebox/` apps contain no notes files as of 2026-04-12
- Reasoning grounded in well-established principles of co-located documentation and progressive
  complexity (confidence: HIGH based on domain knowledge; web search unavailable to cross-check
  specific learning repo examples)
