package com.example.sqlite_connection

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ProductDatabaseHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_NAME = "shop.db"
        private const val DATABASE_VERSION = 2
        private const val TABLE = "products"
        private const val COL_ID = "id"
        private const val COL_NAME = "name"
        private const val COL_PRICE = "price"
        private const val COL_COUNTRY = "country"
        private const val COL_STOCK = "stock"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $TABLE (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NAME TEXT NOT NULL,
                $COL_PRICE REAL NOT NULL,
                $COL_COUNTRY TEXT NOT NULL,
                $COL_STOCK INTEGER NOT NULL
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE")
        onCreate(db)
    }

    fun insert(product: Product): Long {
        return writableDatabase.compileStatement(
            "INSERT INTO $TABLE ($COL_NAME, $COL_PRICE, $COL_COUNTRY, $COL_STOCK) VALUES (?, ?, ?, ?)"
        ).use { stmt ->
            stmt.bindString(1, product.name)
            stmt.bindDouble(2, product.price)
            stmt.bindString(3, product.country)
            stmt.bindLong(4, product.stock.toLong())
            stmt.executeInsert()
        }
    }

    fun getAll(): List<Product> {
        val list = mutableListOf<Product>()
        readableDatabase.rawQuery("SELECT * FROM $TABLE ORDER BY $COL_NAME ASC", null).use { cursor ->
            while (cursor.moveToNext()) {
                list.add(
                    Product(
                        id = cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID)),
                        name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
                        price = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_PRICE)),
                        country = cursor.getString(cursor.getColumnIndexOrThrow(COL_COUNTRY)),
                        stock = cursor.getInt(cursor.getColumnIndexOrThrow(COL_STOCK))
                    )
                )
            }
        }
        return list
    }

    fun getById(id: Long): Product? {
        readableDatabase.rawQuery(
            "SELECT * FROM $TABLE WHERE $COL_ID = ?", arrayOf(id.toString())
        ).use { cursor ->
            if (cursor.moveToFirst()) {
                return Product(
                    id = cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID)),
                    name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
                    price = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_PRICE)),
                    country = cursor.getString(cursor.getColumnIndexOrThrow(COL_COUNTRY)),
                    stock = cursor.getInt(cursor.getColumnIndexOrThrow(COL_STOCK))
                )
            }
        }
        return null
    }

    fun update(product: Product): Int {
        return writableDatabase.compileStatement(
            "UPDATE $TABLE SET $COL_NAME = ?, $COL_PRICE = ?, $COL_COUNTRY = ?, $COL_STOCK = ? WHERE $COL_ID = ?"
        ).use { stmt ->
            stmt.bindString(1, product.name)
            stmt.bindDouble(2, product.price)
            stmt.bindString(3, product.country)
            stmt.bindLong(4, product.stock.toLong())
            stmt.bindLong(5, product.id)
            stmt.executeUpdateDelete()
        }
    }

    fun delete(id: Long): Int {
        return writableDatabase.compileStatement(
            "DELETE FROM $TABLE WHERE $COL_ID = ?"
        ).use { stmt ->
            stmt.bindLong(1, id)
            stmt.executeUpdateDelete()
        }
    }
}
