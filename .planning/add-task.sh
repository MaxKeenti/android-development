#!/bin/bash

# add-task.sh - Create a new task directory following 02-ongoing-task-accumulation workflow
# Guides task creation and NOTES.md completion
# See: .planning/phases/02-ongoing-task-accumulation/02-01-PLAN.md

set -e

# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${BLUE}=== Android Development Task Intake ===${NC}"
echo -e "${BLUE}Following: 02-ongoing-task-accumulation workflow${NC}\n"

# Get project root
PROJECT_ROOT="$(pwd)"
if [ ! -d ".git" ] || [ ! -f "README.md" ]; then
  echo -e "${RED}Error: Not in the android-development project root${NC}"
  exit 1
fi

# Prompt for task details
while true; do
  read -p "Task name (kebab-case, e.g., custom-button-component): " TASK_NAME
  if [ -z "$TASK_NAME" ]; then
    echo -e "${RED}Error: Task name cannot be empty${NC}"
    continue
  fi
  
  # Validate kebab-case: lowercase letters, numbers, hyphens only; start and end with alphanumeric
  if ! echo "$TASK_NAME" | grep -qE '^[a-z0-9]([a-z0-9-]*[a-z0-9])?$'; then
    echo -e "${RED}Error: Task name must be kebab-case (lowercase letters, numbers, hyphens only)${NC}"
    echo -e "${RED}  Examples: custom-button, sqlite-crud, sensor-logger${NC}"
    continue
  fi
  break
done

echo ""
echo "Available topics (from Topic Decision Gate):"
echo "  1. ui       — UI layout, Compose components, theming"
echo "  2. data     — Data persistence, database, file storage"
echo "  3. sensors  — Sensors, location, hardware APIs"
echo "  4. media    — Audio, video, camera, media playback"
echo "  5. network  — Network calls, REST APIs, remote data"
echo "  6. background — Background work, services, scheduling"
echo "  7. [new]    — Create a new topic"
read -p "Select topic (1-7): " TOPIC_CHOICE

case $TOPIC_CHOICE in
  1) TOPIC="ui" ;;
  2) TOPIC="data" ;;
  3) TOPIC="sensors" ;;
  4) TOPIC="media" ;;
  5) TOPIC="network" ;;
  6) TOPIC="background" ;;
  7) read -p "New topic name (kebab-case): " TOPIC
     if [ -z "$TOPIC" ]; then
       echo -e "${RED}Error: Topic name cannot be empty${NC}"
       exit 1
     fi
     ;;
  *) echo -e "${RED}Error: Invalid choice${NC}"
     exit 1
     ;;
esac

read -p "Teacher/Assigned by (optional): " TEACHER
read -p "Primary concept or 'What It Demonstrates' (e.g., 'SQLite with prepared statements'): " CONCEPT
read -p "Brief description of the task: " DESCRIPTION

# Check if task already exists
TASK_DIR="$TOPIC/$TASK_NAME"
if [ -d "$TASK_DIR" ]; then
  echo -e "${RED}Error: Task directory already exists: $TASK_DIR${NC}"
  exit 1
fi

# Create task directory
mkdir -p "$TASK_DIR"
echo -e "${GREEN}✓ Created directory: $TASK_DIR${NC}\n"

# Generate NOTES.md using the established template
NOTES="$TASK_DIR/NOTES.md"
DISPLAY_NAME=$(echo "$TASK_NAME" | sed 's/-/ /g' | sed 's/\b\(.\)/\u\1/g')

cat > "$NOTES" << EOF
# $DISPLAY_NAME

## 1. Task Assigned

- $DESCRIPTION
$([ -n "$TEACHER" ] && echo "- Assigned by: $TEACHER")

## 2. Concept Covered

- $CONCEPT
- [Add what you learned here after completing the task]

## 3. What Was Hard

- [Add friction points and gotchas after implementing]
- [Note any Android APIs or Kotlin patterns that were confusing]

## 4. Key Code Snippet

\`\`\`kotlin
// Paste the most important 5-15 lines of your implementation here
// This should show the core idea you learned
\`\`\`
EOF

echo -e "${GREEN}✓ Created NOTES.md: $NOTES${NC}\n"

# Create .gitkeep for the directory structure
touch "$TASK_DIR/.gitkeep"

# Stage and commit
git add "$TASK_DIR/"
git commit -m "feat($TOPIC): add new task - $TASK_NAME"

echo -e "${GREEN}✓ Committed to git${NC}\n"

# Show next steps
echo -e "${BLUE}Next steps:${NC}"
echo -e "  1. cd $TASK_DIR"
echo -e "  2. Create your Android project structure"
echo -e "  3. Implement the task"
echo -e "  4. Complete all four sections in NOTES.md"
echo -e "     - Section 2: Update with what you learned"
echo -e "     - Section 3: Document any challenges"
echo -e "     - Section 4: Add the key code snippet (5-15 lines)"
echo -e "  5. git add app/ && git commit -m 'feat($TOPIC): implement $TASK_NAME'"
echo -e "  6. Run: ./.planning/update-readmes.sh"
echo -e "     (Updates $TOPIC/README.md and README.md to list the new task)"
echo -e "\n${YELLOW}Task directory ready: $TASK_DIR${NC}\n"
echo -e "${YELLOW}See .planning/phases/02-ongoing-task-accumulation/02-01-PLAN.md for full workflow${NC}"
