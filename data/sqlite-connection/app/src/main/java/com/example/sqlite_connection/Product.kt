package com.example.sqlite_connection

data class Product(
    val id: Long = 0,
    val name: String,
    val price: Double,
    val category: String,
    val stock: Int
)
