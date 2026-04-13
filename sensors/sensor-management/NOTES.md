# Sensor Management

## 1. Task Assigned

- Build an app that detects and reacts to sensor value changes in real time
- Request runtime permissions required for sensor access (e.g. `BODY_SENSORS`)
- Display live sensor readings on screen as values change
- Handle permission denial gracefully with a user-visible message

## 2. Concept Covered

- `SensorManager` and `SensorEventListener` — the Android API for registering and receiving sensor events
- Runtime permission requests using `ActivityResultContracts.RequestPermission` in Compose
- `remember` + `mutableStateOf` to hold live sensor values and update the UI reactively
- Unregistering the sensor listener in `DisposableEffect` to prevent resource leaks

## 3. What Was Hard

- Understanding the difference between `Sensor.TYPE_*` constants and which ones require permissions vs. which are freely accessible
- Wiring the `SensorEventListener` callback into Compose state — the listener runs on a background thread, so updates must go through `mutableStateOf` which is thread-safe
- Handling the case where a sensor type is not available on the device (`SensorManager.getDefaultSensor()` returns null)
- Getting `DisposableEffect` right: the listener must be unregistered in `onDispose` or it keeps firing after the composable leaves the screen

## 4. Key Code Snippet

Registering a sensor listener inside a Compose composable and unregistering it on disposal:

```kotlin
val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
var sensorValue by remember { mutableStateOf("Waiting…") }

DisposableEffect(Unit) {
    val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    val listener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            sensorValue = "x=%.2f y=%.2f z=%.2f".format(
                event.values[0], event.values[1], event.values[2]
            )
        }
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }
    sensor?.let { sensorManager.registerListener(listener, it, SensorManager.SENSOR_DELAY_UI) }
    onDispose { sensorManager.unregisterListener(listener) }
}
```
