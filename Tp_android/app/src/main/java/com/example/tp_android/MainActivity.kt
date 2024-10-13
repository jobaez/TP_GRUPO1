package com.example.tp_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.Alignment


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Tp_androidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RegistroProductosScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun RegistroProductosScreen(modifier: Modifier = Modifier) {
    // Estados para los campos de entrada
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var marca by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var existencia by remember { mutableStateOf("") }
    var imagenUrl by remember { mutableStateOf("") }

    // Lista de productos
    val productos = remember { mutableStateListOf<Producto>() }

    Column(modifier = modifier.padding(16.dp)) {
        // Formulario de Ingreso de productos
        TextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = marca, onValueChange = { marca = it }, label = { Text("Marca") })
        Spacer(modifier = Modifier.height(8.dp))

        // Campo de Precio con validación
        TextField(
            value = precio,
            onValueChange = {
                if (isValidNumber(it) || it.isEmpty()) { // Validar que sea número
                    precio = it
                }
            },
            label = { Text("Precio") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Campo de Existencia con validación
        TextField(
            value = existencia,
            onValueChange = {
                if (isValidNumber(it) || it.isEmpty()) { // Validar que sea número
                    existencia = it
                }
            },
            label = { Text("Existencia") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(value = imagenUrl, onValueChange = { imagenUrl = it }, label = { Text("Imagen (URL)") })
        Spacer(modifier = Modifier.height(16.dp))

        // Botón para agregar producto a la lista
        Button(onClick = {
            if (nombre.isNotEmpty() && precio.isNotEmpty() && existencia.isNotEmpty()) {
                productos.add(
                    Producto(nombre, descripcion, marca, precio.toDouble(), existencia.toInt(), imagenUrl)
                )
                nombre = ""
                descripcion = ""
                marca = ""
                precio = ""
                existencia = ""
                imagenUrl = ""
            }
        }) {
            Text("Registrar Producto")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de productos registrada
        LazyColumn {
            items(productos) { producto ->
                ProductoItem(producto = producto) {
                    productos.remove(producto) // Funcionalidad de eliminación
                }
            }
        }
    }
}

// Función para validar que el string sea un número
fun isValidNumber(input: String): Boolean {
    return input.all { it.isDigit() }
}

// Composable que representa un ítem de la lista de productos
@Composable
fun ProductoItem(producto: Producto, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = producto.nombre, style = MaterialTheme.typography.titleLarge)

            LazyRow(modifier = Modifier.padding(top = 4.dp)) {
                item { Text("Descripción: ${producto.descripcion}") }
                item { Spacer(modifier = Modifier.width(16.dp)) }
                item { Text("Marca: ${producto.marca}") }
                item { Spacer(modifier = Modifier.width(16.dp)) }
                item { Text("Precio: ${producto.precio}") }
                item { Spacer(modifier = Modifier.width(16.dp)) }
                item { Text("Existencia: ${producto.existencia}") }
                item {
                    Spacer(modifier = Modifier.width(16.dp))
                    Image(
                        painter = rememberAsyncImagePainter(producto.imagenUrl),
                        contentDescription = producto.nombre,
                        modifier = Modifier.size(64.dp)
                    )
                }
            }

            // Botón para eliminar producto
            Button(
                onClick = onDelete,
                modifier = Modifier.align(Alignment.End) // Corregido
            ) {
                Text("Eliminar")
            }
        }
    }
}

// Vista previa en el editor
@Preview(showBackground = true)
@Composable
fun RegistroProductosScreenPreview() {
    Tp_androidTheme {
        RegistroProductosScreen()
    }
}

// Modelo de datos del Producto
data class Producto(
    val nombre: String,
    val descripcion: String,
    val marca: String,
    val precio: Double,
    val existencia: Int,
    val imagenUrl: String
)
