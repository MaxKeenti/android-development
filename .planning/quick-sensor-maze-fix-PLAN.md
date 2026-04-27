---
phase: quick
plan: sensor-maze-fix
type: auto
title: Fix goal zone corridor - add right wall to complete passage
---

# Quick Task: Sensor-Management Maze Goal Corridor Fix

## Problem
Ball cannot reach goal zone because walls are completely closed. The maze has Row 5 and a corridor left wall defined, but no corresponding right wall to complete the corridor structure from Row 5 to goal zone.

## Root Cause Analysis
Looking at MainActivity.kt maze generation (lines 81-119):
- Row 5 horizontal bar: from `t` to `width * 0.62f` at `height * 0.86f`
- Corridor left wall: `width * 0.62f` from `height * 0.86f` to `height - t`
- Missing: Right wall of corridor to create bounded passage
- Goal location: `(width - 100f, height - 100f, width - 30f, height - 30f)`

The corridor needs a right boundary to guide the ball correctly and ensure the goal zone is accessible.

## Fix Approach
Add a right wall of the corridor starting from Row 5 gap edge down to just above the goal zone. This completes the corridor structure and creates a clear, accessible path.

**Add after the corridor left wall definition (line 117):**
- Right wall of corridor at position `width * 0.85f` extending from `height * 0.86f` down to `height - t`
- This creates a bounded corridor from Row 5 (0.62w to 0.85w) down to goal level
- Goal zone at (w-100 to w-30) remains accessible within this corridor

## Tasks
1. Edit MainActivity.kt - add right corridor wall after line 117
2. Verify maze allows ball passage from Row 5 through corridor to goal
3. Commit fix

## Success Criteria
- Ball can navigate from top-left through maze
- Ball can pass through Row 5 gap at width * 0.62f
- Corridor (bounded by left and right walls) guides ball downward
- Ball can enter goal zone in bottom-right area
- No collision errors on downward path

## Files Modified
- `/Users/moonstone/Source/UPIICSA/Plan 2021/2026 2/6NM61 Programación móvil/android-development/sensors/sensor-management/app/src/main/java/com/example/sensor_management/MainActivity.kt`
