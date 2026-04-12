# Roadmap: Android Development Learning Journey

## Overview

The repo currently has two working apps sitting at the root with no notes and no topic structure. This roadmap has one bounded phase: move everything into place, document it, and establish the conventions that make every future task trivially repeatable. After that, the repo enters an open-ended accumulation phase — no fixed end, no planned scope. The teacher assigns tasks; they land in the right folder with notes from day one.

## Phases

**Phase Numbering:**
- Integer phases (1, 2, 3): Planned milestone work
- Decimal phases (2.1, 2.2): Urgent insertions (marked with INSERTED)

Decimal phases appear between their surrounding integers in numeric order.

- [ ] **Phase 1: Reorganize and Bootstrap** - Relocate both apps, write retroactive notes, clean up known issues, and establish conventions
- [ ] **Phase 2: Ongoing Task Accumulation** - Continuous phase; each teacher-assigned task lands in the correct topic folder with notes from day one

## Phase Details

### Phase 1: Reorganize and Bootstrap
**Goal**: The repo is organized, documented, and ready to receive future tasks — both existing apps are in their correct topic folders with notes, known issues are addressed, and the conventions that govern all future work are written down
**Depends on**: Nothing (first phase)
**Requirements**: STRUCT-01, STRUCT-02, STRUCT-03, DOCS-01, DOCS-02, DOCS-03, REM-01, REM-02, REM-03, REM-04, REM-05, CONV-01, CONV-02, CONV-03
**Success Criteria** (what must be TRUE):
  1. Both apps are accessible at `ui/news/` and `ui/jukebox/` and still open cleanly in Android Studio
  2. Each app has a `NOTES.md` covering what the task was, what concept it demonstrates, what was hard, and a key code snippet
  3. Root `README.md` exists and lists both tasks under the `ui/` topic — a new reader can navigate the repo from it
  4. A topic-level `README.md` exists at `ui/` since that topic has 2 tasks
  5. The `NOTES.md` template, topic decision gate, and package naming convention are each written down in one place
**Plans:** 5 plans

Plans:
- [x] 01-01-PLAN.md — Relocate news and jukebox apps into ui/<task>/ structure
- [x] 01-02-PLAN.md — Replace German strings in news app; delete placeholder ExampleUnitTest.kt files
- [x] 01-03-PLAN.md — Write retroactive NOTES.md for news app
- [x] 01-04-PLAN.md — Write retroactive NOTES.md for jukebox app (with MediaPlayer limitation)
- [x] 01-05-PLAN.md — Create root README.md and ui/README.md with inline conventions

### Phase 2: Ongoing Task Accumulation
**Goal**: Every teacher-assigned task that arrives in the future lands in the correct topic folder with a `NOTES.md` already written — the repo grows indefinitely without accumulating the debt that Phase 1 had to pay
**Depends on**: Phase 1
**Requirements**: None (steady-state workflow, not a scoped deliverable)
**Success Criteria** (what must be TRUE):
  1. Each new task appears under a topic folder matching its primary concept (never at the repo root)
  2. Each new task folder contains a `NOTES.md` using the template established in Phase 1
  3. Root `README.md` is updated each time a task is added
  4. A topic-level `README.md` is added or updated whenever a topic reaches 2 or more tasks
**Plans**: N/A (open-ended; no plan files; this is a repeatable workflow, not a bounded execution)

## Progress

**Execution Order:**
Phases execute in numeric order: 1 → 2

| Phase | Plans Complete | Status | Completed |
|-------|----------------|--------|-----------|
| 1. Reorganize and Bootstrap | 0/5 | Not started | - |
| 2. Ongoing Task Accumulation | N/A | Not started | - |
