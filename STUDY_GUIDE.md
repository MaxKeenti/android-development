# 📚 Guía de Estudio — Programación Móvil (6NM61)
## Examen: 8 de mayo 2026

> [!TIP]
> Enfoque del examen: **cómo funciona cada función**, **su propósito**, y **qué funcionalidad estándar aporta cada API**.

---

## Índice

1. [UI: News — Card y Composables](#1-ui-news--card-y-composables)
2. [UI: News Improved — LazyColumn y Device ID](#2-ui-news-improved--lazycolumn-y-device-id)
3. [UI: Jukebox — LazyVerticalGrid y MediaPlayer](#3-ui-jukebox--lazyverticalgrid-y-mediaplayer)
4. [Sensors: Acelerómetro y SensorManager](#4-sensors-acelerómetro-y-sensormanager)
5. [Data: DataStore Preferences](#5-data-datastore-preferences)
6. [Data: SQLite con SQLiteOpenHelper](#6-data-sqlite-con-sqliteopenhelper)
7. [Transversales: State, Intent, Ciclo de Vida](#7-transversales-state-intent-ciclo-de-vida)
8. [Preguntas Tipo Examen (Q&A)](#8-preguntas-tipo-examen)
9. [Cheat Sheet](#9-cheat-sheet)

---

## 1. UI: News — Card y Composables

**Propósito:** Aprender a crear componentes de UI reutilizables con parámetros.

### Funciones clave

| Función/API | ¿Qué hace? |
|---|---|
| `@Composable fun News(title, author, date, content)` | Función composable que recibe datos como parámetros y dibuja una tarjeta de noticia |
| `Card(modifier, shape, elevation)` | Contenedor Material 3 con sombra y bordes redondeados |
| `RoundedCornerShape(4.dp)` | Define esquinas redondeadas de 4dp para la Card |
| `CardDefaults.cardElevation(4.dp)` | Configura la sombra/elevación de la Card |
| `Column { }` | Apila hijos **verticalmente** (uno debajo del otro) |
| `Row { }` | Apila hijos **horizontalmente** (uno al lado del otro) |
| `Modifier.fillMaxWidth()` | El elemento ocupa todo el ancho disponible |
| `Modifier.background(Color.Black)` | Pinta el fondo del contenedor de negro |
| `Modifier.padding(16.dp)` | Agrega espacio interno de 16dp |

### Concepto: Composable con parámetros

```kotlin
@Composable
fun News(title: String, author: String, date: String, content: String) { ... }
```

Los composables se llaman como funciones normales pasando datos. **No usan XML** — la UI se construye 100% en Kotlin.

### Versión mejorada: `buildAnnotatedString`

En `news-improved`, el composable usa texto con estilos mezclados:

```kotlin
Text(text = buildAnnotatedString {
    append("Publicado por:\n")
    withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)) {
        append(author)
    }
})
```

| Función | ¿Qué hace? |
|---|---|
| `buildAnnotatedString { }` | Construye un string con **diferentes estilos** por sección |
| `append(text)` | Agrega texto con estilo normal |
| `withStyle(SpanStyle(...)) { append(...) }` | Agrega texto con estilo específico (negrita, cursiva, color, etc.) |

---

## 2. UI: News Improved — LazyColumn y Device ID

**Propósito:** Mostrar listas largas eficientemente y leer identificadores del dispositivo.

### Funciones clave

| Función/API | ¿Qué hace? |
|---|---|
| `LazyColumn { items(list) { } }` | Lista scrolleable que **solo renderiza los elementos visibles** en pantalla — eficiente para listas largas |
| `items(newsList) { (title, author, date) -> }` | Itera sobre la lista; el destructuring `(title, author, date)` extrae los valores del `Triple` |
| `Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)` | Lee el **ID único** del dispositivo Android (persistente entre reinicios) |
| `LocalContext.current` | Obtiene el `Context` de Android desde dentro de un Composable sin pasarlo como parámetro |
| `@Preview` | Anotación que permite ver el composable en el editor sin ejecutar la app |

### LazyColumn vs Column con scroll

| `Column` + `verticalScroll` | `LazyColumn` |
|---|---|
| Renderiza **todos** los elementos de golpe | Renderiza **solo los visibles** |
| OK para listas cortas (<20 items) | Necesario para listas largas |
| Usa `Modifier.verticalScroll(rememberScrollState())` | Usa DSL `items(list) { }` |

### Device ID

```kotlin
val deviceId = Settings.Secure.getString(
    context.contentResolver,
    Settings.Secure.ANDROID_ID
)
```

- `contentResolver` es el intermediario para acceder a datos del sistema
- `ANDROID_ID` es un identificador hexadecimal de 64 bits, único por dispositivo + usuario

---

## 3. UI: Jukebox — LazyVerticalGrid y MediaPlayer

**Propósito:** Crear una cuadrícula interactiva que reproduce sonidos al tocar imágenes.

### Funciones clave

| Función/API | ¿Qué hace? |
|---|---|
| `LazyVerticalGrid(columns = GridCells.Fixed(3))` | Cuadrícula scrolleable con **3 columnas fijas** — solo renderiza celdas visibles |
| `GridCells.Fixed(n)` | Número fijo de columnas |
| `GridCells.Adaptive(minSize)` | Columnas adaptativas según el ancho disponible |
| `painterResource(id = R.drawable.xxx)` | Carga una imagen desde `res/drawable/` |
| `Modifier.clickable { }` | Hace que cualquier composable responda a toques |
| `MediaPlayer.create(context, R.raw.xxx)` | Crea un reproductor de audio desde `res/raw/` |
| `mediaPlayer.start()` | Inicia la reproducción |
| `mediaPlayer.setOnCompletionListener { it.release() }` | Cuando termina de sonar, **libera la memoria** del reproductor |
| `Modifier.size(100.dp)` | Fija el tamaño del elemento a 100×100dp |

### Modelo de datos

```kotlin
data class JukeboxItem(val label: String, val imageRes: Int, val soundRes: Int)
```

Agrupa etiqueta + imagen + sonido en un solo objeto. Los `Int` son IDs de recurso (`R.drawable.*`, `R.raw.*`).

### Flujo de reproducción

1. Usuario toca imagen → se dispara `clickable { }`
2. `MediaPlayer.create(context, soundRes)` → crea instancia lista para sonar
3. `setOnCompletionListener { it.release() }` → al terminar, libera memoria
4. `start()` → reproduce el audio

> [!WARNING]
> **Limitación conocida:** Si tocas rápido varias veces, se crean múltiples instancias que suenan simultáneamente (audio superpuesto).

---

## 4. Sensors: Acelerómetro y SensorManager

**Propósito:** Leer sensores de hardware en tiempo real y usarlos para controlar la UI.

### API de Sensores — Arquitectura

```
SensorManager → getDefaultSensor(TYPE) → Sensor
     ↓
registerListener(listener, sensor, delay)
     ↓
SensorEventListener.onSensorChanged(event) → event.values[0..2]
     ↓
onDispose → unregisterListener(listener)
```

### Funciones clave

| Función/API | ¿Qué hace? |
|---|---|
| `context.getSystemService(Context.SENSOR_SERVICE) as SensorManager` | Obtiene el servicio del sistema que administra todos los sensores |
| `sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)` | Obtiene el acelerómetro. Retorna `null` si el dispositivo no tiene uno |
| `sensorManager.registerListener(listener, sensor, delay)` | **Registra** un listener para recibir eventos del sensor |
| `sensorManager.unregisterListener(listener)` | **Des-registra** el listener — **imprescindible** para evitar fugas de recursos |
| `SensorManager.SENSOR_DELAY_GAME` | Frecuencia de actualización rápida (~20ms), ideal para juegos |
| `SensorManager.SENSOR_DELAY_UI` | Frecuencia más lenta (~60ms), suficiente para actualizar UI |

### SensorEventListener

```kotlin
object : SensorEventListener {
    override fun onSensorChanged(event: SensorEvent) {
        x = event.values[0]  // Inclinación lateral (izq/der)
        y = event.values[1]  // Inclinación adelante/atrás
        // event.values[2] = eje Z (gravedad)
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
```

| Método | ¿Cuándo se llama? |
|---|---|
| `onSensorChanged(event)` | Cada vez que el sensor reporta nuevos valores (según el delay) |
| `onAccuracyChanged(sensor, accuracy)` | Cuando cambia la precisión del sensor (raramente usado) |

### DisposableEffect — Registrar y limpiar

```kotlin
DisposableEffect(Unit) {
    sensorManager.registerListener(listener, acelerometro, SensorManager.SENSOR_DELAY_GAME)
    onDispose {
        sensorManager.unregisterListener(listener)
    }
}
```

| Parte | ¿Qué hace? |
|---|---|
| `DisposableEffect(Unit)` | Ejecuta código al entrar en composición, con un bloque de limpieza |
| Bloque principal | Registra el listener (se ejecuta una vez) |
| `onDispose { }` | Se ejecuta cuando el composable **sale de pantalla** — des-registra el listener |
| `Unit` como key | El efecto solo se ejecuta una vez (key nunca cambia) |

> [!IMPORTANT]
> Si no llamas `unregisterListener` en `onDispose`, el sensor **sigue enviando datos** después de salir de la pantalla, desperdiciando batería y memoria.

### Canvas — Dibujo personalizado

```kotlin
Canvas(modifier = Modifier.fillMaxSize()) {
    drawRect(color = Color.Black, topLeft = Offset(...), size = Size(...))
    drawCircle(color = Color.Red, radius = radius, center = Offset(posX, posY))
}
```

| Función | ¿Qué hace? |
|---|---|
| `Canvas { }` | Composable que permite dibujo libre 2D |
| `drawRect(color, topLeft, size)` | Dibuja un rectángulo |
| `drawCircle(color, radius, center)` | Dibuja un círculo |
| `Offset(x, y)` | Punto 2D para posicionar elementos |
| `Size(width, height)` | Dimensiones de un rectángulo |

### BoxWithConstraints

```kotlin
BoxWithConstraints(modifier = Modifier.weight(1f)) {
    val width = constraints.maxWidth.toFloat()
    val height = constraints.maxHeight.toFloat()
}
```

Igual que `Box` pero expone `constraints.maxWidth` y `constraints.maxHeight` para saber el tamaño disponible en píxeles.

---

## 5. Data: DataStore Preferences

**Propósito:** Almacenar preferencias de usuario de forma asíncrona y segura.

### Funciones clave

| Función/API | ¿Qué hace? |
|---|---|
| `preferencesDataStore("nombre")` | Crea una instancia singleton de DataStore (archivo `nombre.preferences_pb`) |
| `stringPreferencesKey("clave")` | Crea una clave tipada que solo acepta `String` |
| `context.dataStore.data` | Retorna un `Flow<Preferences>` — stream reactivo de datos |
| `.map { prefs -> prefs[clave] ?: "" }` | Transforma el Flow, extrae el valor de la clave con default |
| `context.dataStore.edit { prefs -> prefs[clave] = valor }` | Escritura atómica (transaccional). Es `suspend` |
| `.collectAsState(initial = "")` | Convierte `Flow` → `State` de Compose |
| `rememberCoroutineScope()` | Scope para lanzar coroutines desde un composable |
| `scope.launch { }` | Ejecuta una coroutine sin bloquear la UI |

### Flujo de lectura

```
dataStore.data → Flow<Preferences> → .map { } → Flow<String> → .collectAsState() → State<String> → UI
```

### Flujo de escritura

```
Button onClick → scope.launch { dataStore.edit { prefs[clave] = valor } }
```

> `edit` es `suspend` → necesita coroutine → `scope.launch { }` la ejecuta sin bloquear.

---

## 6. Data: SQLite con SQLiteOpenHelper

**Propósito:** Base de datos relacional local para CRUD completo.

### SQLiteOpenHelper

| Método | ¿Cuándo se llama? | ¿Qué hace? |
|---|---|---|
| `onCreate(db)` | Solo la **1ª vez** (archivo .db no existe) | `CREATE TABLE` |
| `onUpgrade(db, old, new)` | Cuando subes `DATABASE_VERSION` | Migración (aquí: DROP + CREATE) |

**Constructor:** `SQLiteOpenHelper(context, "shop.db", null, 2)`
- `context` → dónde guardar el archivo
- `"shop.db"` → nombre del archivo de BD
- `null` → cursor factory (casi siempre null)
- `2` → versión del esquema

### CRUD completo

#### INSERT

```kotlin
writableDatabase.compileStatement("INSERT INTO products (...) VALUES (?,?,?,?)")
    .use { stmt ->
        stmt.bindString(1, name)    // Los ? son 1-indexed
        stmt.bindDouble(2, price)
        stmt.bindLong(4, stock)
        stmt.executeInsert()        // → retorna el ID del nuevo registro
    }
```

#### SELECT (todos)

```kotlin
readableDatabase.rawQuery("SELECT * FROM products", null).use { cursor ->
    while (cursor.moveToNext()) {
        cursor.getString(cursor.getColumnIndexOrThrow("name"))
    }
}
```

#### SELECT (por ID)

```kotlin
readableDatabase.rawQuery("SELECT * FROM products WHERE id = ?", arrayOf(id.toString()))
    .use { cursor ->
        if (cursor.moveToFirst()) { /* encontrado */ }
    }
```

#### UPDATE

```kotlin
writableDatabase.compileStatement("UPDATE products SET name=? WHERE id=?")
    .use { stmt -> stmt.bindString(1, name); stmt.bindLong(2, id); stmt.executeUpdateDelete() }
```

#### DELETE

```kotlin
writableDatabase.compileStatement("DELETE FROM products WHERE id=?")
    .use { stmt -> stmt.bindLong(1, id); stmt.executeUpdateDelete() }
```

### Tabla de funciones SQLite

| Función | Propósito |
|---|---|
| `writableDatabase` | Abre BD en modo escritura |
| `readableDatabase` | Abre BD en modo lectura (más eficiente) |
| `compileStatement(sql)` | Pre-compila SQL con marcadores `?` (previene SQL injection) |
| `rawQuery(sql, args)` | Ejecuta SELECT, retorna `Cursor` |
| `execSQL(sql)` | Ejecuta DDL sin retorno (CREATE, DROP) |
| `.use { }` | Cierra automáticamente el recurso al terminar |
| `bindString/Double/Long(i, val)` | Enlaza valor al marcador `?` en posición `i` |
| `executeInsert()` | Ejecuta INSERT, retorna **row ID** |
| `executeUpdateDelete()` | Ejecuta UPDATE/DELETE, retorna **# filas afectadas** |
| `cursor.moveToNext()` | Avanza al siguiente registro; `false` = no hay más |
| `cursor.moveToFirst()` | Va al primer registro; `false` = resultado vacío |
| `cursor.getColumnIndexOrThrow(col)` | Índice de columna por nombre (lanza excepción si no existe) |

### Modelo: `data class Product`

```kotlin
data class Product(val id: Long = 0, val name: String, val price: Double, val country: String, val stock: Int)
```

`data class` auto-genera: `equals()`, `hashCode()`, `toString()`, `copy()`, destructuring.
`copy()` se usa en edit: `product.copy(name = "nuevo")` — crea copia con campos modificados.

---

## 7. Transversales: State, Intent, Ciclo de Vida

### `remember` y `mutableStateOf`

```kotlin
val products by remember(refreshKey) { mutableStateOf(db.getAll()) }
```

| Pieza | Propósito |
|---|---|
| `mutableStateOf(value)` | Contenedor observable — cambios provocan **recomposición** |
| `remember { }` | Memoriza el valor entre recomposiciones |
| `remember(key) { }` | Re-ejecuta el bloque si `key` cambia |
| `by` | Delegación — usa `products` directamente sin `.value` |

### Intent — Navegación entre Activities

| Operación | Código |
|---|---|
| Navegar | `startActivity(Intent(context, Target::class.java))` |
| Enviar dato | `.putExtra("product_id", id)` |
| Recibir dato | `intent.getLongExtra("product_id", -1L)` |
| Regresar | `activity.finish()` |

### Ciclo de Vida + Refresh

```kotlin
class MainActivity : ComponentActivity() {
    private val refreshKey = mutableStateOf(0)
    override fun onResume() { super.onResume(); refreshKey.value++ }
}
```

`onResume` se llama cada vez que la Activity vuelve al frente → incrementa key → `remember(key)` re-ejecuta → datos frescos.

### LaunchedEffect

```kotlin
LaunchedEffect(Unit) { activity.finish() }
```

Ejecuta un efecto secundario **una sola vez** al entrar en composición. Con `Unit` como key, no se re-ejecuta.

---

## 8. Preguntas Tipo Examen

### UI

**P: ¿Cuál es la diferencia entre `LazyColumn` y `LazyVerticalGrid`?**
> `LazyColumn` muestra items en una sola columna vertical. `LazyVerticalGrid` los organiza en una cuadrícula de N columnas. Ambos solo renderizan los elementos visibles.

**P: ¿Qué hace `painterResource(id)`?**
> Carga una imagen desde la carpeta `res/drawable/` del proyecto y la convierte en un `Painter` que el composable `Image` puede mostrar.

**P: ¿Por qué se llama `release()` en el `MediaPlayer`?**
> `MediaPlayer` consume recursos nativos (memoria, descriptores de archivo). `release()` libera esos recursos. Sin ella, cada toque acumularía recursos hasta agotar la memoria.

**P: ¿Qué hace `Modifier.clickable { }`?**
> Hace que cualquier composable detecte toques del usuario y ejecute el bloque lambda al ser tocado.

### Sensors

**P: ¿Qué retorna `getDefaultSensor(Sensor.TYPE_ACCELEROMETER)`?**
> Un objeto `Sensor` que representa el acelerómetro, o `null` si el dispositivo no tiene acelerómetro. Siempre hay que verificar null.

**P: ¿Para qué sirve `DisposableEffect` con sensores?**
> Para registrar el listener al entrar en composición y **des-registrarlo** al salir (`onDispose`). Sin esto, el sensor sigue enviando eventos desperdiciando batería.

**P: ¿Qué contiene `event.values` del acelerómetro?**
> Un array de 3 floats: `values[0]` = eje X (inclinación lateral), `values[1]` = eje Y (adelante/atrás), `values[2]` = eje Z (arriba/abajo, gravedad).

**P: ¿Cuál es la diferencia entre `SENSOR_DELAY_GAME` y `SENSOR_DELAY_UI`?**
> `GAME` actualiza ~cada 20ms (50 veces/seg), ideal para juegos fluidos. `UI` actualiza ~cada 60ms (~16 veces/seg), suficiente para mostrar valores en pantalla.

### DataStore

**P: ¿Qué ventaja tiene DataStore sobre SharedPreferences?**
> Es completamente asíncrono (coroutines/Flow), no bloquea el hilo principal, y ofrece seguridad de tipos con claves tipadas.

**P: ¿Por qué `saveGuardar` es `suspend`?**
> La escritura a disco es I/O que no debe bloquear el hilo principal. `suspend` obliga a llamarla desde una coroutine.

**P: ¿Qué hace `collectAsState(initial = "")`?**
> Convierte un `Flow` en un `State` de Compose. `initial` es el valor antes de la primera emisión. Cada emisión recompone la UI.

### SQLite

**P: ¿Cuándo se ejecuta `onCreate` de `SQLiteOpenHelper`?**
> Solo la primera vez que la app accede a la BD y el archivo `.db` no existe. No se vuelve a llamar.

**P: ¿Qué pasa si incrementas `DATABASE_VERSION`?**
> Se llama `onUpgrade`. En este código, borra la tabla y la recrea (migración destructiva).

**P: ¿Diferencia entre `readableDatabase` y `writableDatabase`?**
> `readable` = modo lectura (eficiente). `writable` = lectura + escritura. Usar `readable` para SELECT, `writable` para INSERT/UPDATE/DELETE.

**P: ¿Por qué usar `?` en vez de concatenar valores al SQL?**
> Los marcadores `?` (queries parametrizadas) previenen SQL injection. Nunca concatenes input del usuario al SQL.

**P: Explica `val products by remember(refreshKey) { mutableStateOf(db.getAll()) }`**
> `mutableStateOf` crea estado observable. `remember(refreshKey)` memoriza pero re-ejecuta si `refreshKey` cambia. `by` permite acceder sin `.value`. Cuando `onResume` incrementa `refreshKey`, se re-consulta la BD.

**P: ¿Qué retorna `executeInsert()`? ¿Y `executeUpdateDelete()`?**
> `executeInsert()` → el **row ID** del nuevo registro. `executeUpdateDelete()` → el **número de filas afectadas**.

---

## 9. Cheat Sheet

```
┌──────────── UI COMPOSABLES ──────────────┐
│ Card(shape, elevation)  → Tarjeta M3     │
│ Column { }              → Vertical       │
│ Row { }                 → Horizontal     │
│ LazyColumn { items() }  → Lista eficiente│
│ LazyVerticalGrid(cols)  → Grid eficiente │
│ Canvas { drawRect/Circle }  → Dibujo 2D │
│ BoxWithConstraints → acceso a dimensiones│
│ Image(painterResource)  → Imagen local   │
│ Modifier.clickable { }  → Touch handler  │
│ buildAnnotatedString    → Texto estilizado│
│ @Preview                → Vista previa   │
└──────────────────────────────────────────┘

┌──────────── MEDIA ───────────────────────┐
│ MediaPlayer.create(ctx, R.raw.x) → crear │
│ .start()                       → play    │
│ .setOnCompletionListener { release() }   │
└──────────────────────────────────────────┘

┌──────────── SENSORS ─────────────────────┐
│ getSystemService(SENSOR_SERVICE)→ Manager│
│ getDefaultSensor(TYPE_ACCEL)  → Sensor?  │
│ registerListener(l, s, delay) → escuchar │
│ unregisterListener(l)         → parar    │
│ event.values[0,1,2]         → x, y, z   │
│ SENSOR_DELAY_GAME / _UI      → velocidad│
│ DisposableEffect + onDispose → limpieza  │
└──────────────────────────────────────────┘

┌──────────── DATASTORE ───────────────────┐
│ preferencesDataStore("name") → instancia │
│ stringPreferencesKey("key")  → clave     │
│ .data.map { it[key] ?: "" }  → leer Flow │
│ .edit { it[key] = val }      → escribir  │
│ .collectAsState(initial)   → Flow→State  │
│ scope.launch { }           → coroutine   │
└──────────────────────────────────────────┘

┌──────────── SQLITE ──────────────────────┐
│ SQLiteOpenHelper(ctx,name,null,ver)      │
│ onCreate(db)     → CREATE TABLE (1ª vez) │
│ onUpgrade(db,o,n)→ migración             │
│ readableDatabase → SELECT                │
│ writableDatabase → INSERT/UPDATE/DELETE  │
│ compileStatement → SQL con ?             │
│ rawQuery(sql,args) → Cursor              │
│ executeInsert()  → retorna row ID        │
│ executeUpdateDelete() → retorna # filas  │
│ cursor.moveToNext/First → navegar filas  │
│ .use { }         → auto-close            │
└──────────────────────────────────────────┘

┌──────────── COMPOSE STATE ───────────────┐
│ mutableStateOf(v)      → observable      │
│ remember { }           → memorizar       │
│ remember(key) { }      → re-calc si key∆ │
│ by                     → sin .value      │
│ LaunchedEffect(key)    → efecto 1 vez    │
│ DisposableEffect(key)  → con limpieza    │
│ LocalContext.current   → Context         │
└──────────────────────────────────────────┘

┌──────────── INTENTS ─────────────────────┐
│ Intent(ctx, Target::class.java) → crear  │
│ .putExtra("key", value)     → enviar     │
│ intent.getLongExtra("key",-1) → recibir  │
│ activity.finish()           → regresar   │
└──────────────────────────────────────────┘
```

> [!CAUTION]
> **Errores comunes:**
> - `readableDatabase` para SELECT, `writableDatabase` para escritura — no al revés
> - `remember` sin key **nunca** re-ejecuta el bloque
> - `onCreate` de SQLiteOpenHelper se llama **solo la 1ª vez**, no cada vez que abre la app
> - `executeInsert()` retorna ID, `executeUpdateDelete()` retorna # filas — no confundir
> - `DataStore.edit {}` es `suspend` — necesita `scope.launch { }`
> - `DisposableEffect` sin `onDispose` = fuga de recursos con sensores
> - `MediaPlayer` sin `release()` = fuga de memoria
> - `getDefaultSensor()` puede retornar `null` — siempre verificar
