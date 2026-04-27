# Task Intake Guide — Integrated with 02-Ongoing-Task-Accumulation

This guide walks through the complete task intake workflow, which builds on and integrates with the established **Phase 2: Ongoing Task Accumulation** pattern.

See the full workflow spec: `.planning/phases/02-ongoing-task-accumulation/02-01-PLAN.md`

## Overview

When the teacher assigns a new Android development task:

1. **Create task directory** — Run `.planning/add-task.sh` to scaffold `{topic}/{task}/` with NOTES.md
2. **Implement the task** — Build the app in that directory
3. **Complete NOTES.md** — Document all four required sections with real content
4. **Update README files** — Add the task to topic and root README indices
5. **Commit to git** — All work tracked in history

This ensures every task lands in the right place on the same day—no debt accumulation.

## Quick Start

### Step 1: Create the Task Directory

```bash
cd /path/to/android-development
./.planning/add-task.sh
```

The script prompts for:
- Task name (kebab-case)
- Topic (from Topic Decision Gate)
- Teacher name, concept, description

Then creates `{topic}/{task}/NOTES.md` with the required template and commits it.

### Step 2: Implement the Task

Navigate and build the app:
```bash
cd {topic}/{task}
# Scaffold the Android project
# Implement the task
```

### Step 3: Complete NOTES.md

The template has four **mandatory** sections:

```markdown
# Task Name

## 1. Task Assigned
- [2-4 bullets: what the teacher asked you to build]

## 2. Concept Covered
- [2-4 bullets: the primary Android/Compose concept]

## 3. What Was Hard
- [2-4 bullets: friction points, gotchas, surprises]

## 4. Key Code Snippet
[One focused block: 5-15 lines showing the core idea]
```

All four sections **must** have real content—no template placeholders.

### Step 4: Update README Files

Update `{topic}/README.md` to list the new task in its Tasks table.
Update root `README.md` to list the new task in the correct topic section.

Use the helper to find what needs updating:
```bash
./.planning/update-readmes.sh
```

See `.planning/phases/02-ongoing-task-accumulation/02-01-PLAN.md` **Task 3** for exact update rules.

### Step 5: Commit

```bash
# Implementation
git add {topic}/{task}/app/
git commit -m "feat({topic}): implement {task}"

# README updates
git add README.md {topic}/README.md
git commit -m "docs: add {task} to {topic} task index"
```

## Topic Decision Gate

Use this table to classify incoming tasks:

| If the task primarily teaches... | Topic folder |
|----------------------------------|-------------|
| UI layout, Compose components, navigation, theming | `ui/` |
| Audio, video, camera, media playback | `media/` |
| Data persistence, local database, file storage | `data/` |
| Network calls, REST APIs, remote data | `network/` |
| Background work, services, scheduling | `background/` |
| Sensors, location, hardware APIs | `sensors/` |

If unsure, ask the teacher. If a task doesn't fit any topic, create a new one and add it to this table.

## Naming Conventions

Task names use **kebab-case**:
- ✅ `custom-button-component`, `sqlite-crud-app`, `sensor-logger`
- ❌ `CustomButton`, `custom_button`, `Custom Button`

## Verification

After completing a task:

```bash
# NOTES.md exists with all four sections
ls {topic}/{task}/NOTES.md
grep -c "## 1. Task Assigned" {topic}/{task}/NOTES.md      # should be 1
grep -c "## 2. Concept Covered" {topic}/{task}/NOTES.md    # should be 1
grep -c "## 3. What Was Hard" {topic}/{task}/NOTES.md      # should be 1
grep -c "## 4. Key Code Snippet" {topic}/{task}/NOTES.md   # should be 1

# Task appears in both README files
grep "{task}" {topic}/README.md                 # should match
grep "{topic}/{task}/" README.md                # should match (root README link)
```

## Full Workflow

See `.planning/phases/02-ongoing-task-accumulation/02-01-PLAN.md` for the complete, detailed workflow including:
- All four Task steps with detailed instructions
- Threat model and STRIDE analysis
- Acceptance criteria and verification procedures
- Success criteria and definition of done

## See Also

- **Helper script:** `.planning/add-task.sh`
- **README scanner:** `.planning/update-readmes.sh`
- **Existing examples:** `ui/news/`, `data/sqlite-connection/`, `sensors/sensor-management/`
- **Root README:** Topic Decision Gate table, NOTES.md template, package naming
