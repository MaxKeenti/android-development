package com.example.sensor_management

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.sensor_management.ui.theme.SensormanagementTheme

// Data class to hold our parsed maze components
data class MazeData(
    val walls: List<Rect>,
    val startPos: Offset,
    val goalRect: Rect,
    val recommendedRadius: Float
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SensormanagementTheme {
                Sensors(context = this@MainActivity)
            }
        }
    }
}

@Composable
fun Sensors(context: Context) {
    val sensorManager = remember {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    
    var x by remember { mutableStateOf(0f) }
    var y by remember { mutableStateOf(0f) }

    // Use -1f to indicate the ball hasn't been placed on the start line yet
    var posX by remember { mutableStateOf(-1f) }
    var posY by remember { mutableStateOf(-1f) }

    var speed by remember { mutableStateOf(0.6f) }
    var isWon by remember { mutableStateOf(false) }

    val listener = remember {
        object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

            override fun onSensorChanged(event: SensorEvent) {
                if (!isWon) {
                    x = event.values[0]
                    y = event.values[1]

                    posX -= x * speed
                    posY += y * speed
                }
            }
        }
    }

    val acelerometro = remember {
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    DisposableEffect(Unit) {
        if (acelerometro != null) {
            sensorManager.registerListener(listener, acelerometro, SensorManager.SENSOR_DELAY_GAME)
        }
        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    if (acelerometro == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Accelerometer not available on this device.")
        }
        return
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        val width = constraints.maxWidth.toFloat()
        val height = constraints.maxHeight.toFloat()

        // 1. Draw your maze here! 
        // W = Wall, S = Start, G = Goal, Space = Path
        val mazeTemplate = remember {
            listOf(
                "WWWWWWWWWWWW",
                "WS   W     W",
                "WWWW W W WWW",
                "W    W W   W",
                "W WWWW WWW W",
                "W    W   W W",
                "W WWWWWW W W",
                "W      W   W",
                "W WWWWWWWW W",
                "W          G",
                "WWWWWWWWWWWW"
            )
        }

        // 2. Parse the text map into Rectangles automatically
        val mazeData = remember(width, height) {
            val rows = mazeTemplate.size
            val cols = mazeTemplate[0].length
            val cellW = width / cols
            val cellH = height / rows

            val wallsList = mutableListOf<Rect>()
            var start = Offset(80f, 80f)
            var goal = Rect(0f, 0f, 0f, 0f)

            for (r in 0 until rows) {
                for (c in 0 until cols) {
                    val char = mazeTemplate[r][c]
                    val left = c * cellW
                    val top = r * cellH
                    val right = left + cellW
                    val bottom = top + cellH

                    when (char) {
                        'W' -> wallsList.add(Rect(left, top, right, bottom))
                        'S' -> start = Offset(left + cellW / 2, top + cellH / 2)
                        'G' -> goal = Rect(left, top, right, bottom)
                    }
                }
            }
            
            // Auto-size the ball so it always fits through the corridors comfortably
            val safeRadius = minOf(cellW, cellH) * 0.35f 
            
            MazeData(wallsList, start, goal, safeRadius)
        }

        val radius = mazeData.recommendedRadius

        // Initialize ball position when the maze is generated
        LaunchedEffect(mazeData) {
            if (posX == -1f) {
                posX = mazeData.startPos.x
                posY = mazeData.startPos.y
            }
        }

        // Apply collision detection - per-axis clamping
        for (wall in mazeData.walls) {
            val ballRect = Rect(posX - radius, posY - radius, posX + radius, posY + radius)

            if (ballRect.overlaps(wall)) {
                val wallCenterX = (wall.left + wall.right) / 2f
                val wallCenterY = (wall.top + wall.bottom) / 2f
                val ballCenterX = (ballRect.left + ballRect.right) / 2f
                val ballCenterY = (ballRect.top + ballRect.bottom) / 2f

                val overlapLeft = ballRect.right - wall.left
                val overlapRight = wall.right - ballRect.left
                val overlapTop = ballRect.bottom - wall.top
                val overlapBottom = wall.bottom - ballRect.top

                val minHorizontalOverlap = minOf(overlapLeft, overlapRight)
                val minVerticalOverlap = minOf(overlapTop, overlapBottom)

                if (minHorizontalOverlap <= minVerticalOverlap) {
                    if (ballCenterX < wallCenterX) {
                        posX = wall.left - radius - 1f  
                    } else {
                        posX = wall.right + radius + 1f
                    }
                } else {
                    if (ballCenterY < wallCenterY) {
                        posY = wall.top - radius - 1f
                    } else {
                        posY = wall.bottom + radius + 1f
                    }
                }
            }
        }

        // Check win condition
        val ballRect = Rect(posX - radius, posY - radius, posX + radius, posY + radius)
        if (ballRect.overlaps(mazeData.goalRect)) {
            isWon = true
        }

        // Limit ball inside screen boundaries
        posX = maxOf(radius, minOf(width - radius, posX))
        posY = maxOf(radius, minOf(height - radius, posY))

        // Draw maze and ball
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(color = Color.White, size = Size(width, height))

            for (wall in mazeData.walls) {
                drawRect(
                    color = Color.Black,
                    topLeft = Offset(wall.left, wall.top),
                    size = Size(wall.width, wall.height)
                )
            }

            drawRect(
                color = Color.Green,
                topLeft = Offset(mazeData.goalRect.left, mazeData.goalRect.top),
                size = Size(mazeData.goalRect.width, mazeData.goalRect.height)
            )

            if (posX != -1f) {
                drawCircle(
                    color = Color.Red,
                    radius = radius,
                    center = Offset(posX, posY)
                )
            }
        }

        // Congratulations overlay
        if (isWon) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    modifier = Modifier.padding(32.dp),
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Congratulations!",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Spacer(modifier = Modifier.padding(16.dp))
                        Button(
                            onClick = {
                                // Reset to the dynamic start position!
                                posX = mazeData.startPos.x
                                posY = mazeData.startPos.y
                                isWon = false
                            }
                        ) {
                            Text("Reset")
                        }
                    }
                }
            }
        }
    }
}

