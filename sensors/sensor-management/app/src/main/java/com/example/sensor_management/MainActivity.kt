package com.example.sensor_management

import android.content.Context
import androidx.compose.ui.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.dp
import com.example.sensor_management.ui.theme.SensormanagementTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Sensors(context = this)
        }
    }
}

@Composable
fun Sensors(context: Context) {
    val sensorManager = remember {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    var x by remember {
        mutableStateOf(0f)
    }
    var y by remember {
        mutableStateOf(0f)
    }
    var z by remember {
        mutableStateOf(0f)
    }

    // Ball position - starts in top-left area
    var posX by remember {
        mutableStateOf(80f)
    }
    var posY by remember {
        mutableStateOf(80f)
    }

    var speed by remember {
        mutableStateOf(0.5f)
    }

    var isWon by remember {
        mutableStateOf(false)
    }

    // Maze definition: proportional walls so the maze fills the screen at any size.
    // Snake path: start top-left → right → down → left → down → right → goal bottom-right
    val createMaze: (Float, Float) -> List<Rect> = { width, height ->
        val t = height * 0.025f  // wall thickness scales with screen height
        listOf(
            // Outer boundaries
            Rect(0f, 0f, width, t),
            Rect(0f, 0f, t, height),
            Rect(width - t, 0f, width, height),
            Rect(0f, height - t, width, height),

            // Row 1 – horizontal bar from left, gap on right (30% of width)
            Rect(t, height * 0.18f, width * 0.70f, height * 0.18f + t),
            // Row 1 right connector – vertical down from gap edge
            Rect(width * 0.70f, height * 0.18f, width * 0.70f + t, height * 0.34f),

            // Row 2 – horizontal bar from right, gap on left (30% of width)
            Rect(width * 0.30f, height * 0.34f, width - t, height * 0.34f + t),
            // Row 2 left connector – vertical down from gap edge
            Rect(width * 0.30f, height * 0.34f, width * 0.30f + t, height * 0.52f),

            // Row 3 – horizontal bar from left, gap on right (28% of width)
            Rect(t, height * 0.52f, width * 0.72f, height * 0.52f + t),
            // Row 3 right connector – vertical down from gap edge
            Rect(width * 0.72f, height * 0.52f, width * 0.72f + t, height * 0.70f),

            // Row 4 – horizontal bar from right, gap on left (32% of width)
            Rect(width * 0.32f, height * 0.70f, width - t, height * 0.70f + t),
            // Row 4 left connector – vertical down to create final corridor
            Rect(width * 0.32f, height * 0.70f, width * 0.32f + t, height * 0.86f),

            // Row 5 – horizontal bar from left, gap on right toward goal
            Rect(t, height * 0.86f, width * 0.58f, height * 0.86f + t),
        )
    }

    val listener = remember {
        object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            }

            override fun onSensorChanged(event: SensorEvent) {
                if (!isWon) {
                    x = event.values[0]
                    y = event.values[1]
                    z = event.values[2]

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
        val radius = 30f
        val walls = createMaze(width, height)

        // Goal rect in bottom-right area
        val goalRect = Rect(width - 100f, height - 100f, width - 30f, height - 30f)

        // Apply collision detection - per-axis clamping
        for (wall in walls) {
            val ballRect = Rect(posX - radius, posY - radius, posX + radius, posY + radius)

            if (ballRect.overlaps(wall)) {
                // Calculate wall center
                val wallCenterX = (wall.left + wall.right) / 2f
                val wallCenterY = (wall.top + wall.bottom) / 2f
                val ballCenterX = (ballRect.left + ballRect.right) / 2f
                val ballCenterY = (ballRect.top + ballRect.bottom) / 2f

                // Clamp X-axis if overlapping horizontally
                if (ballRect.right > wall.left && ballRect.left < wall.right) {
                    if (ballCenterX < wallCenterX) {
                        posX = wall.left - radius
                    } else {
                        posX = wall.right + radius
                    }
                }

                // Clamp Y-axis if overlapping vertically
                if (ballRect.bottom > wall.top && ballRect.top < wall.bottom) {
                    if (ballCenterY < wallCenterY) {
                        posY = wall.top - radius
                    } else {
                        posY = wall.bottom + radius
                    }
                }
            }
        }

        // Check win condition
        val ballRect = Rect(posX - radius, posY - radius, posX + radius, posY + radius)
        if (ballRect.overlaps(goalRect)) {
            isWon = true
        }

        // Limit ball inside screen boundaries
        posX = maxOf(radius, minOf(width - radius, posX))
        posY = maxOf(radius, minOf(height - radius, posY))

        // Draw maze and ball
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Draw walls (black rectangles)
            for (wall in walls) {
                drawRect(
                    color = Color.Black,
                    topLeft = Offset(wall.left, wall.top),
                    size = androidx.compose.ui.geometry.Size(wall.width, wall.height)
                )
            }

            // Draw goal area (green rectangle)
            drawRect(
                color = Color.Green,
                topLeft = Offset(goalRect.left, goalRect.top),
                size = androidx.compose.ui.geometry.Size(goalRect.width, goalRect.height)
            )

            // Draw ball (red circle)
            drawCircle(
                color = Color.Red,
                radius = radius,
                center = Offset(posX, posY)
            )
        }

        // Congratulations overlay with reset button
        if (isWon) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    modifier = Modifier
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(32.dp),
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
                                posX = 80f
                                posY = 80f
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
