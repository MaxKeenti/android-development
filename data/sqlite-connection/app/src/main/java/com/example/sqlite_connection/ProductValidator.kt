package com.example.sqlite_connection

data class ProductFormErrors(
    val name: String? = null,
    val price: String? = null,
    val category: String? = null,
    val stock: String? = null
) {
    val hasErrors get() = name != null || price != null || category != null || stock != null
}

fun validateProductForm(name: String, price: String, category: String, stock: String): ProductFormErrors {
    val nameError = when {
        name.isBlank() -> "El nombre no puede estar vacío"
        name.length > 100 -> "El nombre no puede tener más de 100 caracteres"
        else -> null
    }
    val priceError = when {
        price.isBlank() -> "El precio no puede estar vacío"
        price.toDoubleOrNull() == null -> "El precio debe ser un número válido"
        price.toDouble() <= 0 -> "El precio debe ser mayor a 0"
        else -> null
    }
    val categoryError = when {
        category.isBlank() -> "La categoría no puede estar vacía"
        else -> null
    }
    val stockError = when {
        stock.isBlank() -> "El stock no puede estar vacío"
        stock.toIntOrNull() == null -> "El stock debe ser un número entero"
        stock.toInt() < 0 -> "El stock no puede ser negativo"
        else -> null
    }
    return ProductFormErrors(nameError, priceError, categoryError, stockError)
}
