package com.example.sqlite_connection

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sqlite_connection.ui.theme.SqliteconnectionTheme

class MainActivity : ComponentActivity() {
    private val refreshKey = mutableStateOf(0)

    override fun onResume() {
        super.onResume()
        refreshKey.value++
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SqliteconnectionTheme {
                val key by refreshKey
                UIMainActivity(refreshKey = key)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UIMainActivity(refreshKey: Int) {
    val context = LocalContext.current
    val db = remember { ProductDatabaseHelper(context) }
    val products by remember(refreshKey) { mutableStateOf(db.getAll()) }
    val deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Column {
                    Text("Tienda Demo — Productos")
                    Text(
                        text = "Device ID: $deviceId",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                context.startActivity(Intent(context, AddProductActivity::class.java))
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar producto")
            }
        }
    ) { padding ->
        if (products.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay productos. Toca + para agregar uno.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(products) { product ->
                    ProductListItem(
                        product = product,
                        onEdit = {
                            context.startActivity(
                                Intent(context, EditProductActivity::class.java)
                                    .putExtra("product_id", product.id)
                            )
                        },
                        onDelete = {
                            context.startActivity(
                                Intent(context, DeleteProductActivity::class.java)
                                    .putExtra("product_id", product.id)
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ProductListItem(product: Product, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEdit() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.name,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "País: ${product.country}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Precio: $${String.format("%.2f", product.price)}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Stock: ${product.stock} unidades",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = "Eliminar ${product.name}",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
