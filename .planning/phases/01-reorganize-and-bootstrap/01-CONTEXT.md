# Phase 1: Reorganize and Bootstrap - Context

**Gathered:** 2026-04-12
**Status:** Ready for planning

<domain>
## Phase Boundary

Relocate both existing apps (`news/`, `jukebox/`) into `ui/news/` and `ui/jukebox/`, write retroactive NOTES.md for each, address known issues (MediaPlayer overlap doc, German text replacement, placeholder test deletion), and establish the three conventions that govern all future work: NOTES.md template, topic decision gate, and package naming policy. Creating new tasks or introducing any architecture patterns is out of scope.

</domain>

<decisions>
## Implementation Decisions

### NOTES.md Format
- **D-01:** Each of the 4 sections uses **brief bullets** — 2-4 bullet points per section. No lengthy paragraphs.
- **D-02:** The "key code snippet" section contains **one focused block** — the most important 5-15 lines that demonstrate the core concept. No multi-snippet entries.

### German Text in News App (REM-04)
- **D-03:** Replace the hardcoded German strings with clearly-labeled **English placeholder text** (e.g., "Article Title", "Author Name", "Article content goes here..."). The code will be more readable to any future reader. This is an intentional change to the app source.

### Root README Structure
- **D-04:** Root README opens with a **brief intro paragraph** explaining this is a personal Android learning reference, followed by the topic/task navigation index.
- **D-05:** The NOTES.md template (CONV-01) is embedded **inline in the root README** — not a separate TEMPLATE.md file. One file, one place to look.

### Known Issues Remediation
- **D-06:** MediaPlayer overlap bug (REM-03) — documented in jukebox NOTES.md as a known limitation with an explanation of why rapid taps trigger multiple streams. No code fix.
- **D-07:** Placeholder unit tests (REM-05) — delete `ExampleUnitTest.kt` from both apps entirely. No replacement tests needed.

### Convention Documents
- **D-08:** CONV-02 (topic decision gate) and CONV-03 (package naming) are written inline in the root README alongside the NOTES.md template — keeping all conventions in one place.

### Claude's Discretion
- Exact wording of the intro paragraph in root README
- English placeholder text values for the news app strings
- Formatting of the topic decision gate (table, flowchart description, or bullet rules)
- Whether the root README uses a Markdown table or a bulleted list for the task index

</decisions>

<canonical_refs>
## Canonical References

**Downstream agents MUST read these before planning or implementing.**

### Requirements
- `.planning/REQUIREMENTS.md` — All 14 v1 requirements for Phase 1 (STRUCT-01 through CONV-03) with full acceptance criteria
- `.planning/PROJECT.md` — Core value, constraints, key decisions, out-of-scope items

### Existing Code (apps to be relocated and documented)
- `news/app/src/main/java/com/maxkeenti/news/News.kt` — Contains the hardcoded German strings to be replaced (D-03)
- `jukebox/app/src/main/java/com/maxkeenti/jukebox/MainActivity.kt` — MediaPlayer usage to be documented as a known limitation (D-06)
- `news/app/src/test/java/com/maxkeenti/news/ExampleUnitTest.kt` — Placeholder test to delete (D-07)
- `jukebox/app/src/test/java/com/maxkeenti/jukebox/ExampleUnitTest.kt` — Placeholder test to delete (D-07)

</canonical_refs>

<code_context>
## Existing Code Insights

### Reusable Assets
- Both `news/` and `jukebox/` are self-contained Gradle projects — they can be moved as directories without any internal path changes (no cross-project imports or relative references between them)
- No shared modules, no symlinks — a filesystem move is sufficient

### Established Patterns
- Both apps use identical build stack: Kotlin 2.2.10, Compose BOM 2024.09.00, AGP 9.1.0, minSdk 36
- Package names are `com.maxkeenti.news` and `com.maxkeenti.jukebox` — retroactive refactoring is out of scope per PROJECT.md

### Integration Points
- No CI/CD, no build scripts at root level that reference `news/` or `jukebox/` paths — move won't break automation
- Android Studio opens each project by pointing at its own folder; relocation just means pointing at the new path (`ui/news/`, `ui/jukebox/`)

</code_context>

<specifics>
## Specific Ideas

- Root README: brief intro first, then navigation index (not pure index-only)
- NOTES.md template lives in the root README — not a separate file
- All three convention docs (template, topic gate, package naming) inline in root README
- English placeholder replacements should be clearly labeled, not just generic lorem ipsum

</specifics>

<deferred>
## Deferred Ideas

None — discussion stayed within Phase 1 scope.

</deferred>

---

*Phase: 01-reorganize-and-bootstrap*
*Context gathered: 2026-04-12*
