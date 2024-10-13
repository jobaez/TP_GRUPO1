package com.josebaez.trabajopractico
import com.josebaez.trabajopractico.ui.theme.TrabajoPracticoTheme

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrabajoPracticoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RegistroProductosScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun RegistroProductosScreen(modifier: Modifier = Modifier) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var marca by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var existencia by remember { mutableStateOf("") }
    var imagenUrl by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    val productos = remember { mutableStateListOf<Producto>() }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(showError) {
        if (showError) {
            snackbarHostState.showSnackbar("URL inválida o no se puede cargar la imagen")
            showError = false
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Registro de Productos",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Blue
                ),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.DarkGray) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFEEEEEE),
                    unfocusedContainerColor = Color(0xFFE0E0E0),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.DarkGray
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.DarkGray) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFEEEEEE),
                    unfocusedContainerColor = Color(0xFFE0E0E0),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.DarkGray
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = marca,
                onValueChange = { marca = it },
                label = { Text("Marca", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.DarkGray) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFEEEEEE),
                    unfocusedContainerColor = Color(0xFFE0E0E0),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.DarkGray
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = precio,
                onValueChange = {
                    if (isValidNumber(it) || it.isEmpty()) {
                        precio = it
                    }
                },
                label = { Text("Precio", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.DarkGray) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFEEEEEE),
                    unfocusedContainerColor = Color(0xFFE0E0E0),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.DarkGray
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = existencia,
                onValueChange = {
                    if (isValidNumber(it) || it.isEmpty()) {
                        existencia = it
                    }
                },
                label = { Text("Existencia", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.DarkGray) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFEEEEEE),
                    unfocusedContainerColor = Color(0xFFE0E0E0),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.DarkGray
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = imagenUrl,
                onValueChange = { imagenUrl = it },
                label = { Text("Imagen (URL)", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.DarkGray) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFEEEEEE),
                    unfocusedContainerColor = Color(0xFFE0E0E0),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.DarkGray
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (nombre.isNotEmpty() && precio.isNotEmpty() && existencia.isNotEmpty()) {
                        if (isValidUrl(imagenUrl)) {
                            productos.add(
                                Producto(nombre, descripcion, marca, precio.toDouble(), existencia.toInt(), imagenUrl)
                            )
                            nombre = ""
                            descripcion = ""
                            marca = ""
                            precio = ""
                            existencia = ""
                            imagenUrl = ""
                        } else {
                            showError = true
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(8.dp)
            ) {
                Text("Registrar Producto", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(productos) { producto ->
                    ProductoItem(producto = producto) {
                        productos.remove(producto)
                    }
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

fun isValidUrl(url: String): Boolean {
    return url.startsWith("http://") || url.startsWith("https://")
}

fun isValidNumber(input: String): Boolean {
    return input.all { it.isDigit() }
}

@Composable
fun ProductoItem(producto: Producto, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )

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
                        painter = rememberAsyncImagePainter(
                            model = producto.imagenUrl,
                            placeholder = painterResource(R.drawable.placeholder_image_background),
                            error = painterResource(R.drawable.error_image_background)
                        ),
                        contentDescription = producto.nombre,
                        modifier = Modifier.size(64.dp)
                    )
                }
            }

            Button(
                onClick = onDelete,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.align(Alignment.End),
                elevation = ButtonDefaults.buttonElevation(8.dp)
            ) {
                Text("Eliminar", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistroProductosScreenPreview() {
    TrabajoPracticoTheme {
        RegistroProductosScreen()
    }
}

data class Producto(
    val nombre: String,
    val descripcion: String,
    val marca: String,
    val precio: Double,
    val existencia: Int,
    val imagenUrl: String
)
