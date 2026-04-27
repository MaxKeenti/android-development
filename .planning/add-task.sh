#!/bin/bash

# add-task.sh - Create a new task directory under an appropriate topic
# Usage: ./add-task.sh

set -e

# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${BLUE}=== Android Development Task Intake ===${NC}\n"

# Get project root
PROJECT_ROOT="$(pwd)"
if [ ! -d ".git" ]; then
  echo "Error: Not in the android-development project root"
  exit 1
fi

# Prompt for task details
read -p "Task name (kebab-case, e.g., custom-button-component): " TASK_NAME
if [ -z "$TASK_NAME" ]; then
  echo "Error: Task name cannot be empty"
  exit 1
fi

echo ""
echo "Available topics:"
echo "  1. ui       — User interface & Compose"
echo "  2. data     — Data persistence & storage"
echo "  3. sensors  — Hardware sensor integration"
echo "  4. [new]    — Create a new topic"
read -p "Select topic (1-4): " TOPIC_CHOICE

case $TOPIC_CHOICE in
  1) TOPIC="ui" ;;
  2) TOPIC="data" ;;
  3) TOPIC="sensors" ;;
  4) read -p "New topic name (kebab-case): " TOPIC
     if [ -z "$TOPIC" ]; then
       echo "Error: Topic name cannot be empty"
       exit 1
     fi
     ;;
  *) echo "Error: Invalid choice"
     exit 1
     ;;
esac

read -p "Teacher/Assigned by (optional): " TEACHER
read -p "Brief description of the task: " DESCRIPTION

# Validate kebab-case
if [[ ! "$TASK_NAME" =~ ^[a-z0-9]([a-z0-9-]*[a-z0-9])?$ ]]; then
  echo "Error: Task name must be kebab-case (lowercase letters, numbers, hyphens only)"
  exit 1
fi

# Create task directory
TASK_DIR="$TOPIC/$TASK_NAME"
if [ -d "$TASK_DIR" ]; then
  echo "Error: Task directory already exists: $TASK_DIR"
  exit 1
fi

mkdir -p "$TASK_DIR"
echo -e "${GREEN}✓ Created directory: $TASK_DIR${NC}\n"

# Generate README
README="$TASK_DIR/README.md"
cat > "$README" << EOF
# Task: $(echo $TASK_NAME | sed 's/-/ /g' | sed 's/\b\(.\)/\u\1/g')

**Topic:** $TOPIC
**Status:** In Progress
$([ -n "$TEACHER" ] && echo "**Assigned by:** $TEACHER")
**Date:** $(date +%Y-%m-%d)

## Task Description

$DESCRIPTION

## Learning Objectives

- [ ] Understand the core concept
- [ ] Implement the feature
- [ ] Write tests
- [ ] Document the solution

## Implementation Progress

### Phase 1: Setup
- [ ] Create Android project structure
- [ ] Set up dependencies
- [ ] Initialize git

### Phase 2: Implementation
- [ ] Build the main feature
- [ ] Handle edge cases
- [ ] Add error handling

### Phase 3: Testing & Documentation
- [ ] Write unit tests
- [ ] Write integration tests
- [ ] Document patterns used

## Key Concepts

<!-- Add key learning points as you discover them -->

## Challenges & Solutions

<!-- Document challenges and how you solved them -->

## References

<!-- Links to documentation, examples, or related tasks -->

## Notes

<!-- Implementation notes and insights -->
EOF

echo -e "${GREEN}✓ Created README: $README${NC}\n"

# Create .gitkeep for the directory structure if it's empty
touch "$TASK_DIR/.gitkeep"

# Stage and commit
git add "$TASK_DIR/"
git commit -m "feat($TOPIC): add new task - $TASK_NAME"

echo -e "${GREEN}✓ Committed to git${NC}\n"
echo -e "${BLUE}Next steps:${NC}"
echo -e "  1. cd $TASK_DIR"
echo -e "  2. Create your Android project (cp -r ../reference-project/* . or scaffold with Android Studio)"
echo -e "  3. Update README.md with progress"
echo -e "  4. git add app/ && git commit -m 'feat($TOPIC): implement $TASK_NAME'"
echo -e "\n${YELLOW}Task directory ready: $TASK_DIR${NC}"
