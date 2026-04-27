# SQLite Connection — Tienda Demo

## 1. Task Assigned

Build an Android app that demonstrates SQLite with full CRUD functionality for a fictional shop's products, using:
- Prepared statements via `compileStatement()`
- Separate Activity classes for each CRUD operation
- Input validation before writing to the database

## 2. Concept Covered

- **SQLiteOpenHelper** — manages database creation (`onCreate`) and migration (`onUpgrade`)
- **Prepared statements** — `db.compileStatement()` returns an `SQLiteStatement` that binds parameters safely, avoiding SQL injection and improving performance for repeated queries
- **Multi-Activity architecture** — `Intent` with extras (`putExtra` / `getLongExtra`) passes data between Activity classes
- **Compose + multiple activities** — each Activity calls `setContent {}` independently; `onResume()` override in `MainActivity` increments a `mutableStateOf` counter to trigger product list reload
- **Input validation** — a pure `validateProductForm()` function returns a `ProductFormErrors` data class; errors are shown as `supportingText` on `OutlinedTextField` components; the "live validation" mode only activates after the first submit attempt

## 3. App Structure

```
MainActivity           — product list (Read)
AddProductActivity     — create product form (Create)
EditProductActivity    — edit product form (Update), receives productId via Intent
DeleteProductActivity  — delete confirmation screen (Delete), receives productId via Intent
```

Supporting files:
```
Product.kt             — data class (id, name, price, category, stock)
ProductDatabaseHelper  — SQLiteOpenHelper with insert/getAll/getById/update/delete
ProductValidator.kt    — pure validation logic, returns ProductFormErrors
```

## 4. Prepared Statements Pattern

All write operations use `compileStatement()` with `use { }` (auto-closes the statement):

```kotlin
fun insert(product: Product): Long {
    return writableDatabase.compileStatement(
        "INSERT INTO products (name, price, category, stock) VALUES (?, ?, ?, ?)"
    ).use { stmt ->
        stmt.bindString(1, product.name)
        stmt.bindDouble(2, product.price)
        stmt.bindString(3, product.category)
        stmt.bindLong(4, product.stock.toLong())
        stmt.executeInsert()
    }
}
```

Read operations use `rawQuery()` with a `Cursor`, always wrapped in `.use { }` for automatic resource cleanup.

## 5. Refreshing the List After CRUD

Since each CRUD screen is a separate Activity, returning to `MainActivity` triggers `onResume()`. A `mutableStateOf` counter in the Activity increments there, and `remember(refreshKey)` inside the composable re-runs the database query:

```kotlin
class MainActivity : ComponentActivity() {
    private val refreshKey = mutableStateOf(0)

    override fun onResume() {
        super.onResume()
        refreshKey.value++
    }
}
```

## 6. Validation Design

Validation is deferred until first submit (`submitted` flag), then re-runs on every field change:

```kotlin
var submitted by remember { mutableStateOf(false) }

OutlinedTextField(
    onValueChange = {
        name = it
        if (submitted) errors = validateProductForm(it, price, category, stock)
    },
    isError = errors.name != null,
    supportingText = errors.name?.let { msg -> { Text(msg) } }
)
```

Rules enforced:
- Name: not blank, max 100 chars
- Price: not blank, valid positive decimal
- Category: not blank
- Stock: not blank, valid non-negative integer

## 7. What Was Hard

- Keeping the product list in sync after returning from an Edit/Delete activity without a ViewModel or StateFlow — solved with `onResume()` + `remember(refreshKey)`
- Closing `SQLiteStatement` safely — `use { }` extension handles this even on exception
- Passing Long product IDs through Intents — `putExtra("product_id", product.id)` and `getLongExtra("product_id", -1L)` with a sentinel value for missing extras
