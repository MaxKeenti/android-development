# SQLite Connection

## 1. Task Assigned

- Create an SQLite connection object in an Android application
- Initialize the database schema for the first time
- Manage database versioning smoothly

## 2. Concept Covered

- SQLite database functionality
- Usage of the `SQLiteOpenHelper` class
- Application context requirement for local database operations

## 3. What Was Hard

- Ensuring the single instance connection across different components
- Passing the correct Application context to the connection object without memory leaks
- Managing version increments properly during `onUpgrade` execution

## 4. Key Code Snippet

Basic `SQLiteOpenHelper` initialization and database setup:

```kotlin
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE users (id INTEGER PRIMARY KEY, name TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }
    
    companion object {
        const val DATABASE_NAME = "sqlite-connection.db"
        const val DATABASE_VERSION = 1
    }
}
```
