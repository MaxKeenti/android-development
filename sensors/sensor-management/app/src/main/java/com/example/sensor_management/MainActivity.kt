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
import androidx.compose.ui.unit.dp
import com.example.sensor_management.ui.theme.SensormanagementTheme

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

    //For the ball

    var posX by remember {
        mutableStateOf(0f)
    }
    var posY by remember {
        mutableStateOf(0f)
    }

    var speed by remember {
        mutableStateOf(0.5f)
    }

    val listener = remember {
        object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            }

            override fun onSensorChanged(event: SensorEvent) {
                x = event.values[0]
                y = event.values[1]
                z = event.values[2]

                posX -= x * speed
                posY += y * speed

            }
        }
    }
    //Registro y liberacion del sensor
    val acelerometro = remember {
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    DisposableEffect(Unit) {
        sensorManager.registerListener(
            listener, acelerometro,
            SensorManager.SENSOR_DELAY_GAME
        )
        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    /*Column(){
        Text(text= "X: %.2f".format(x))
        Text(text= "Y: %.2f".format(y))
        Text(text= "Z: %.2f".format(z))
    }*/



    BoxWithConstraints(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        val width = constraints.maxWidth.toFloat()
        val height = constraints.maxHeight.toFloat()
        val radius = 60f

        //Limit ball inside screen
        posX = maxOf(radius, minOf(width - radius, posX))
        posY = maxOf(radius, minOf(height - radius, posY))

        //Draw ball inside canvas
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = Color.Red,
                radius = radius,
                center = Offset(posX, posY)
            )
        }
    }
}