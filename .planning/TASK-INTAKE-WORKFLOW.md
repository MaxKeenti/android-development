# Task Intake Workflow

**Phase:** Task Reception & Organization (Phase 2 Pattern)

This workflow handles incoming tasks assigned by the teacher. It creates a new directory under an appropriate topic, initializes project structure, and prepares for implementation. Designed to be invoked repeatedly as new tasks arrive.

## Overview

When a new task arrives from the teacher:
1. **Classify** the task into a topic (ui, sensors, data, or suggest new)
2. **Create** the task directory under the topic folder
3. **Initialize** with a README explaining the task
4. **Track** the task in git from the start

## Pattern: Continuous Task Intake

This is a "Phase 2" pattern from GSD v1, designed to:
- Scale as more assignments arrive
- Be invoked many times throughout the course
- Maintain consistent organization across all tasks
- Preserve complete learning history in git

## How to Invoke

### Quick Method: Interactive Script
```bash
cd /path/to/android-development
./.planning/add-task.sh
```

The script:
- Prompts for task name, topic, and description
- Creates the directory structure
- Initializes README.md with a task tracking template
- Commits the setup to git
- Provides next steps

### Topics
- **ui** — User interface, Compose, themes, components
- **data** — Data persistence, SQLite, SharedPreferences, APIs
- **sensors** — Hardware sensors, accelerometer, gyroscope, location
- **[new]** — Create a new topic if needed

### Naming
Tasks use kebab-case: `custom-button-component`, `sqlite-crud-app`, `tilt-maze-game`

## After Task Creation

Once the directory is created:
1. Navigate to the task folder
2. Create the Android project structure (scaffold with Android Studio or copy from template)
3. Update README.md with implementation progress
4. Commit frequently as you build
5. Document key concepts and learnings

## Directory Structure

```
<topic>/<task-name>/
├── README.md              # Task description and progress tracking
├── app/                   # Android project (scaffolded by you)
│   ├── build.gradle.kts
│   ├── src/
│   │   ├── main/
│   │   ├── test/
│   │   └── androidTest/
│   └── ...
├── build.gradle.kts
├── settings.gradle.kts
└── gradle/
    └── libs.versions.toml
```

## Example Workflows

### Task 1: UI Component
```bash
./.planning/add-task.sh
# → task: custom-button-component
# → topic: ui
# → Creates: ui/custom-button-component/README.md

cd ui/custom-button-component
# → Create Android project and build the component
```

### Task 2: Database Integration
```bash
./.planning/add-task.sh
# → task: room-migration
# → topic: data
# → Creates: data/room-migration/README.md

cd data/room-migration
# → Create Android project and implement Room
```

## Workflow Benefits

✅ **Organized** — All tasks in clear topic folders
✅ **Repeatable** — Same process for every new task
✅ **Documented** — README.md tracks progress and learnings
✅ **Scalable** — Works for 1 task or 20+ tasks
✅ **Historical** — Git preserves the complete learning journey
✅ **Reference** — Look at completed tasks for patterns

## See Also

- `.planning/TASK-INTAKE-GUIDE.md` — Comprehensive user guide with examples
- `.planning/add-task.sh` — The interactive script to use
- `.agents/skills/add-task/SKILL.md` — GSD skill documentation
