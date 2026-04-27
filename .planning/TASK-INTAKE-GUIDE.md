# Task Intake Guide

## Overview

When your teacher assigns a new Android development task, use this workflow to:
1. Create an organized directory structure for the task
2. Initialize the project setup with a README
3. Track the work in git from the start

This workflow is designed to scale — invoke it each time a new task arrives.

## Quick Start

### Option 1: Interactive Script (Recommended)

```bash
cd /path/to/android-development
./.planning/add-task.sh
```

The script will:
- Ask for the task name (kebab-case)
- Show available topics and let you choose
- Ask for the teacher's name and task description
- Create the directory structure
- Commit it to git

### Option 2: Manual Creation

If you prefer to create the structure manually:

```bash
# Create the directory
mkdir -p <topic>/<task-name>

# Create README
cat > <topic>/<task-name>/README.md << 'TASKEOF'
# Task: <Human Readable Name>

**Topic:** <topic>
**Status:** In Progress
**Assigned by:** <Teacher Name>
**Date:** 2026-04-27

## Task Description

<Paste task description here>

## Learning Objectives
- [ ] 
- [ ] 

## Implementation Progress

### Phase 1: Setup
- [ ] Create Android project structure
- [ ] Set up dependencies
- [ ] Initialize git

### Phase 2: Implementation
- [ ] 
- [ ] 

### Phase 3: Testing & Documentation
- [ ] 
- [ ] 

## Key Concepts
<!-- Add as you learn -->

## Challenges & Solutions
<!-- Document as you go -->

## References
<!-- Links to docs and examples -->

## Notes
<!-- Implementation insights -->
TASKEOF

# Commit
git add <topic>/<task-name>/README.md
git commit -m "feat(<topic>): add new task - <task-name>"
```

## Topics

Existing topics in the project:

| Topic | Purpose | Examples |
|-------|---------|----------|
| **ui** | Jetpack Compose UI components, themes, layouts | Button components, theme systems, custom layouts |
| **data** | Data persistence, storage, database operations | SQLite, SharedPreferences, file I/O |
| **sensors** | Hardware sensors, motion, location | Accelerometer, gyroscope, proximity sensors |

### Creating a New Topic

If a task doesn't fit into ui/data/sensors, create a new topic:

```bash
mkdir -p <new-topic>/<task-name>
# Follow the same README structure
```

## Naming Conventions

### Task Names
- Use **kebab-case** (lowercase, hyphens only)
- Be descriptive but concise
- Examples:
  - ✅ `custom-button-component`
  - ✅ `sqlite-crud-app`
  - ✅ `accelerometer-maze-game`
  - ❌ `CustomButtonComponent`
  - ❌ `custom_button_component`

### Directory Structure After Creation

```
<topic>/<task-name>/
├── README.md              # Task description and progress
├── app/                   # Android project (you add this)
│   ├── build.gradle.kts
│   ├── src/
│   │   ├── main/
│   │   ├── test/
│   │   └── androidTest/
│   └── ...
├── build.gradle.kts       # Root build config
├── settings.gradle.kts    # Project settings
├── gradle/
│   └── libs.versions.toml # Version catalog
└── ...
```

## Workflow: After Creating the Task Directory

1. **Create the Android project** (in the task directory)
   ```bash
   cd <topic>/<task-name>
   # Option A: Copy from a template
   cp -r ../reference-project/* .
   
   # Option B: Create from scratch with Android Studio
   # Or use `android-studio <topic>/<task-name> &`
   ```

2. **Start implementing**
   - Update README.md with implementation progress
   - Add code to `app/src/main/java/`
   - Write tests in `app/src/test/` and `app/src/androidTest/`

3. **Commit frequently**
   ```bash
   git add app/
   git commit -m "feat(<topic>): implement <task-name>"
   git add README.md
   git commit -m "docs(<topic>): document <task-name> progress"
   ```

4. **Document your learnings**
   - Update README.md with key concepts
   - Note challenges and how you solved them
   - Link to relevant documentation

## Example Workflows

### Example 1: New UI Component Task

```bash
./.planning/add-task.sh
# Input: task-name = custom-slider
# Input: topic = ui
# Input: description = Build a custom Range Slider component with Material Design 3 styling

# Automatically creates:
# ui/custom-slider/README.md
# ui/custom-slider/.gitkeep

cd ui/custom-slider
# Now scaffold the Android project and start building...
```

### Example 2: New Database Task

```bash
./.planning/add-task.sh
# Input: task-name = room-database-integration
# Input: topic = data
# Input: description = Migrate from SQLite to Room database

# Automatically creates:
# data/room-database-integration/README.md
# data/room-database-integration/.gitkeep

cd data/room-database-integration
# Create the Android project and implement Room integration...
```

## Tips

- **Start early, commit often** — Each task is a learning journey; commit progress as you go
- **Document as you learn** — Update README.md frequently with new insights
- **Reference existing tasks** — Look at completed tasks in the same topic for patterns
- **Test thoroughly** — Android testing patterns are part of learning; include unit and integration tests
- **Keep notes** — Use the README to document challenges, solutions, and key learnings

## Continuous Improvement

This workflow is designed for continuous use:
- Each task invocation is independent
- Tasks can be worked on in any order
- The directory structure scales as more tasks arrive
- Git history preserves the complete learning journey

## FAQ

**Q: What if I want to rename a task after creating it?**
A: You can rename the directory manually:
```bash
git mv <topic>/<old-name> <topic>/<new-name>
```

**Q: Can I move a task to a different topic?**
A: Yes, use git mv:
```bash
git mv <old-topic>/<task-name> <new-topic>/<task-name>
```

**Q: What if a task spans multiple topics?**
A: Create the primary implementation in the most relevant topic, and reference it from others in their READMEs.

**Q: How do I share progress on a task?**
A: Push to the repository and the README.md serves as documentation for anyone reading the history.
