---
quick_id: 260426-lbr
slug: maze-labyrinth-game
description: Implement labyrinth game in sensor-management app with maze collision detection, win state, and reset button
created: "2026-04-26"
status: ready
context_locked: false
---

# Quick Task 260426-lbr: Labyrinth Game Implementation

**Goal:** Replace the free-roaming ball screen with a single-screen maze game that uses accelerometer tilt to roll a ball through hardcoded maze walls, with collision detection and a win state.

## Tasks

### T01: Design and Hardcode Maze Layout

**What:** Define 8-12 wall segments as hardcoded `Rect` values that form a navigable maze path from start (top-left area) to goal (bottom-right area).

**How:**
- Open sensor-management MainActivity to see current ball canvas layout
- Design maze on paper: clear start, winding path, ~8-12 walls
- Create a `walls` list in MainActivity: `listOf(Rect(...), Rect(...), ...)`
- Example: corridor-style maze with tight turns to ensure player skill requirement

**Files touched:** sensor-management/app/src/main/java/com/maxkeenti/sensormanagement/MainActivity.kt

**Verify:**
- 8-12 wall Rect segments defined
- Maze is navigable (can trace a path from start to goal visually)
- Walls don't block the entire canvas

---

### T02: Implement Per-Axis Collision Clamping

**What:** Add collision detection that clamps ball position per-axis when hitting walls (slide along, don't stop dead).

**How:**
- Read current ball position from accelerometer input (x, y coordinates on canvas)
- For each wall rect, check if ball overlaps
- If overlap on X-axis only → clamp X position to wall boundary, allow Y to continue
- If overlap on Y-axis only → clamp Y position to wall boundary, allow X to continue
- If both axes overlap (corner hit) → clamp both

**Files touched:** sensor-management/app/src/main/java/com/maxkeenti/sensormanagement/MainActivity.kt (or extracted to a Collisions.kt helper)

**Verify:**
- Ball bounces/slides along walls (doesn't pass through)
- Ball can slide diagonally past corner (per-axis independence)
- No dead stops at corners

---

### T03: Add Win State Detection and Congratulations UI

**What:** Detect when ball reaches goal area, pause movement, show congratulations message.

**How:**
- Define a goal `Rect` in bottom-right or corner area
- After collision clamping each frame, check if ball center is within goal rect
- Set `isWon` flag to true
- Render a congratulations overlay/dialog when `isWon == true`
- Overlay: "Congratulations!" + reset button + maybe ball position/time

**Files touched:** sensor-management/app/src/main/java/com/maxkeenti/sensormanagement/MainActivity.kt

**Verify:**
- Ball reaches goal → congratulations appears
- No crashes, state persists until reset

---

### T04: Add Reset Button

**What:** Button in congratulations UI that clears maze state and restarts game.

**How:**
- Add "Reset" button to congratulations overlay
- On click: reset ball position to start, clear `isWon` flag, resume sensor input
- Make button prominent and tappable

**Files touched:** sensor-management/app/src/main/java/com/maxkeenti/sensormanagement/MainActivity.kt

**Verify:**
- Click reset → ball returns to start position
- Game resets and can be played again
- No leftover UI artifacts

---

### T05: Replace Free-Roaming Ball Screen

**What:** Swap out the current ball-on-canvas screen with the maze game.

**How:**
- Remove (or comment out) the free-roaming ball rendering code
- Render maze walls + ball + goal instead
- Ensure sensor input still works
- Test on device or emulator: tilt to roll ball through maze

**Files touched:** sensor-management/app/src/main/java/com/maxkeenti/sensormanagement/MainActivity.kt

**Verify:**
- Maze game is the default screen
- Sensor input (accelerometer tilt) moves ball
- Ball slides along walls and reaches goal

---

## Must-Haves

- Maze is hardcoded (not procedural)
- Collision detection uses per-axis clamping (not rigid stop)
- Win state triggers congratulations + reset button
- No old free-roaming screen left in the rendered output
- Portrait lock maintained (existing behavior)

## Reference

- Current sensor-management app: sensor-management/app/src/main/java/com/maxkeenti/sensormanagement/MainActivity.kt
- Existing canvas ball code: accelerometer → ball movement pipeline
- CLAUDE.md: All Android work uses Kotlin + Jetpack Compose, no changes to tech stack
