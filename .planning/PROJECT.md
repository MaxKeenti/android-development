# Android Development Learning Journey

## What This Is

A personal reference repository documenting a hands-on Android development learning path. Each project is assigned by a teacher/mentor and gets its own folder under a relevant topic — the code tells the story, and notes explain the concepts alongside it. This is not a course or a product; it's a living record of learning in progress.

## Core Value

Every task I'm given has a home here — with working code and a note that explains what I learned from it.

## Requirements

### Validated

- ✓ News app: single-screen Compose UI displaying a news article card (hardcoded content) — existing
- ✓ Jukebox app: grid of audio tracks with MediaPlayer playback using bundled MP3s — existing

### Active

- [ ] Each app/project has a README or notes file explaining what the task was and what concepts it covers
- [ ] Projects are organized by topic (e.g., `ui/`, `media/`, `kotlin-basics/`) with task folders inside
- [ ] Existing apps (`news`, `jukebox`) are relocated into the appropriate topic folders
- [ ] New projects assigned by teacher are added under the correct topic as they come in

### Out of Scope

- Public sharing or portfolio polish — this is a private reference, not a showcase
- Production-quality architecture (ViewModel, DI, Repository) — too early; add when teacher introduces it
- A fixed end date or curriculum — this grows indefinitely as learning continues
- Backend integration or APIs — not yet in scope

## Context

- Two apps already exist: `news/` and `jukebox/`, both using Jetpack Compose + Material 3 + Kotlin 2.2.10
- Both follow single-activity, single-screen architecture — no navigation, no data layer
- The learner is at the very beginning of Android development; projects are assigned by a teacher/mentor
- The goal is to look back and understand what was built, why, and what concept it was teaching

## Constraints

- **Tech stack**: Kotlin + Jetpack Compose — all Android work uses this stack (established by existing projects)
- **Scope**: Greenfield additions only when a new task is assigned; no unsolicited refactors of existing apps
- **Structure**: Task folders live inside topic folders; flat structure at the root should be avoided

## Key Decisions

| Decision | Rationale | Outcome |
|----------|-----------|---------|
| Organize by topic + task | Tasks map to concepts; topic folders make it easy to find "everything about UI" later | — Pending |
| Keep apps self-contained | Each project is independent Gradle project; no shared module coupling | ✓ Good |
| Notes alongside code | Explanations live next to the code they describe, not in a separate docs folder | — Pending |

## Evolution

This document evolves at phase transitions and milestone boundaries.

**After each phase transition** (via `/gsd-transition`):
1. Requirements invalidated? → Move to Out of Scope with reason
2. Requirements validated? → Move to Validated with phase reference
3. New requirements emerged? → Add to Active
4. Decisions to log? → Add to Key Decisions
5. "What This Is" still accurate? → Update if drifted

**After each milestone** (via `/gsd-complete-milestone`):
1. Full review of all sections
2. Core Value check — still the right priority?
3. Audit Out of Scope — reasons still valid?
4. Update Context with current state

---
*Last updated: 2026-04-12 after initialization*
