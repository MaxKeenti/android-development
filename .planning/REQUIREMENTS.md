# Requirements: Android Development Learning Journey

**Defined:** 2026-04-12
**Core Value:** Every task I'm given has a home here — with working code and a note that explains what I learned from it.

## v1 Requirements

### Structure

- [ ] **STRUCT-01**: Repository uses a two-level topic/task folder hierarchy (e.g., `ui/news/`, `media/jukebox/`)
- [ ] **STRUCT-02**: Root `README.md` exists as a navigable index listing all topics and their tasks
- [ ] **STRUCT-03**: A topic-level `README.md` is created when any topic folder reaches 2 or more tasks

### Documentation

- [ ] **DOCS-01**: Each task folder contains a `NOTES.md` using a 4-section template: (1) task assigned, (2) concept covered, (3) what was hard, (4) key code snippet
- [ ] **DOCS-02**: Retroactive `NOTES.md` written for the existing `news` app
- [ ] **DOCS-03**: Retroactive `NOTES.md` written for the existing `jukebox` app

### Remediation

- [ ] **REM-01**: `news/` app relocated to `ui/news/`
- [ ] **REM-02**: `jukebox/` app relocated to `ui/jukebox/`
- [ ] **REM-03**: MediaPlayer overlap bug (multiple streams on rapid click) documented in jukebox `NOTES.md` as a known limitation with an explanation of why it happens
- [ ] **REM-04**: German text in `news` app explained in notes or replaced with clearly labeled English placeholder content
- [ ] **REM-05**: Placeholder unit test files (`ExampleUnitTest.kt`) deleted from both apps — they test `2+2=4` and give false confidence about test coverage

### Convention

- [ ] **CONV-01**: `NOTES.md` template defined once (in root `README.md` or a `TEMPLATE.md`) so every future task uses the same format
- [ ] **CONV-02**: Topic folder decision gate documented — a simple rule for which topic folder a new teacher-assigned task belongs in
- [ ] **CONV-03**: Package naming convention documented — whether to keep existing names (`com.maxkeenti.news`) or establish a topic-scoped pattern, with the reasoning recorded

## v2 Requirements

### Documentation Enhancements

- **DOCS-04**: 5th section added to `NOTES.md` template: "What I'd do differently" — retrospective synthesis of each task
- **DOCS-05**: Concept forward/back links in notes when a teacher introduces a pattern that supersedes an earlier one (e.g., ViewModel replacing inline logic)
- **DOCS-06**: Optional screenshots added to notes for tasks with notable UI output
- **DOCS-07**: Concept glossary file added at 8+ total projects — defines terms introduced across tasks

## Out of Scope

| Feature | Reason |
|---------|--------|
| Static site generators (Jekyll, MkDocs, Docusaurus) | No hosting needed; adds maintenance overhead; violates "notes next to code" principle |
| Shared Gradle modules across task projects | Breaks task independence; each project must open cleanly on its own |
| External documentation tools (Notion, GitHub Wiki) | Disconnects notes from the code they describe; sync drift over time |
| ViewModel, Dependency Injection, Repository pattern | Teacher-gated — only introduced when teacher assigns a task covering these |
| CI/CD pipelines or automated linting | Not yet in scope for a personal learning repo at this stage |
| Package name retroactive refactoring inside app source | Churn is not worth the benefit for a private learning repo; document the exception instead |
| Public portfolio polish | This is a private reference, not a showcase |

## Traceability

| Requirement | Phase | Status |
|-------------|-------|--------|
| STRUCT-01 | Phase 1 | Pending |
| STRUCT-02 | Phase 1 | Pending |
| STRUCT-03 | Phase 1 | Pending |
| DOCS-01 | Phase 1 | Pending |
| DOCS-02 | Phase 1 | Pending |
| DOCS-03 | Phase 1 | Pending |
| REM-01 | Phase 1 | Pending |
| REM-02 | Phase 1 | Pending |
| REM-03 | Phase 1 | Pending |
| REM-04 | Phase 1 | Pending |
| REM-05 | Phase 1 | Pending |
| CONV-01 | Phase 1 | Pending |
| CONV-02 | Phase 1 | Pending |
| CONV-03 | Phase 1 | Pending |

**Coverage:**
- v1 requirements: 13 total
- Mapped to phases: 13
- Unmapped: 0 ✓

---
*Requirements defined: 2026-04-12*
*Last updated: 2026-04-12 after initial definition*
