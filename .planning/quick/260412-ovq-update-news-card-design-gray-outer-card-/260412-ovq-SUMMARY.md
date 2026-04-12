---
phase: quick
plan: 260412-ovq
subsystem: ui/news-improved
tags: [compose, styling, material3, card]
key-files:
  modified:
    - ui/news-improved/app/src/main/java/com/maxkeenti/news_improved/News.kt
decisions:
  - Used buildAnnotatedString to bold/italic author and date values inline without splitting into multiple Text composables
  - weight(0.4f) / weight(0.6f) split gives the metadata card 40% width and content card 60%
metrics:
  duration: ~5m
  completed: 2026-04-12
---

# Quick Task Summary: Restyle News Card — Gray Outer, Black Title, White Inner Cards

**One-liner:** Restyled the News composable with a light gray outer Card, a black italic-bold title header, and two white inner Cards side-by-side showing metadata (author, date) and body content.

## What Changed

**File:** `ui/news-improved/app/src/main/java/com/maxkeenti/news_improved/News.kt`

Previous state:
- Single Card with default elevation, no explicit background color
- Title row: black background, plain white text
- Body: two bare Columns side by side with raw Text composables

New state:
- Outer Card: `Color(0xFFD0D0D0)` (light gray) background, `RoundedCornerShape(4.dp)`, `elevation = 2.dp`, horizontal padding 8.dp / vertical 4.dp
- Title Row: black background, white **bold italic** text, 12.dp horizontal + 10.dp vertical padding
- Body Row: 8.dp padding, contains two white inner Cards with no elevation
  - Left Card (`weight(0.4f)`): displays "Publicado por:" then bold-italic author, and "El:" then bold-italic date using `buildAnnotatedString`
  - Right Card (`weight(0.6f)`): displays body content text

## New Imports Added

- `androidx.compose.foundation.layout.weight`
- `androidx.compose.ui.text.SpanStyle`
- `androidx.compose.ui.text.buildAnnotatedString`
- `androidx.compose.ui.text.font.FontStyle`
- `androidx.compose.ui.text.font.FontWeight`
- `androidx.compose.ui.text.withStyle`

## Commit

`2a7bb04` — style(ui/news-improved): restyle News card — gray outer, black title, white inner cards

## Deviations

None — implemented exactly as specified.
