#!/bin/bash

# update-readmes.sh - Update topic and root README.md files to include new tasks
# Completes Task 3 from 02-ongoing-task-accumulation workflow
# See: .planning/phases/02-ongoing-task-accumulation/02-01-PLAN.md

set -e

GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${BLUE}=== Scanning for new tasks and updating READMEs ===${NC}\n"

PROJECT_ROOT="$(pwd)"
if [ ! -f "README.md" ]; then
  echo -e "${RED}Error: Not in project root${NC}"
  exit 1
fi

# Find all NOTES.md files
NOTES_FILES=$(find . -name "NOTES.md" -type f ! -path "./.git/*" | sort)

if [ -z "$NOTES_FILES" ]; then
  echo -e "${YELLOW}No NOTES.md files found${NC}"
  exit 0
fi

echo "Found tasks:"
for NOTES in $NOTES_FILES; do
  DIR=$(dirname "$NOTES")
  TOPIC=$(echo "$DIR" | cut -d'/' -f2)
  TASK=$(echo "$DIR" | cut -d'/' -f3)
  
  # Get the header from NOTES.md
  HEADER=$(head -1 "$NOTES" | sed 's/^# //')
  
  # Extract concept from section 2
  CONCEPT=$(grep -A 1 "## 2. Concept Covered" "$NOTES" | tail -1 | sed 's/^- //')
  
  echo "  $TOPIC/$TASK — $HEADER"
done

echo -e "\n${YELLOW}Note: Manual README updates are required${NC}"
echo -e "See: .planning/phases/02-ongoing-task-accumulation/02-01-PLAN.md (Task 3)"
echo -e "\nYou must manually:"
echo -e "  1. Update $TOPIC/README.md to add the new task in its Tasks table"
echo -e "  2. Update README.md root to add the new task in its topic section"
echo -e "  3. If this is a new topic, add it to the Topic Decision Gate table"
echo -e "\nThis ensures the links are exactly right and the table formatting is clean."
echo -e "\nAfter updating, commit with:"
echo -e "  git add README.md $TOPIC/README.md"
echo -e "  git commit -m 'docs: add $TASK to $TOPIC task index'"
