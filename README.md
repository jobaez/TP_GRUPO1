Documentación Breve del Proyecto Aplicación Android para gestionar el registro de productos destinados a la venta.
Repositorio: https://github.com/jobaez/UAA-PAPDM-Grupo-1-TP-1
Estructura del Proyecto
Este proyecto utiliza Jetpack Compose para la interfaz de usuario y Coil para cargar imágenes desde URLs.
Estructura de carpetas:
•	app/src/main/java/com/josebaez/trabajopractico/
a)	MainActivity.kt: Actividad principal que carga la interfaz con Compose.
b)	Producto.kt: Modelo de datos donde se define la estructura de un producto.
c)	RegistroProductosScreen.kt: Pantalla principal donde se muestra el formulario de registro de productos y la lista de productos registrados.
•	app/src/main/res/drawable/
a)	placeholder_image.png: Imagen mostrada mientras se carga una imagen desde una URL.
b)	error_image.png: Imagen mostrada en caso de error al cargar una imagen.
•	app/build.gradle.kts: Archivo de configuración con las dependencias para Jetpack Compose, Coil, y otros componentes.
Componentes Clave:
1.	MainActivity.kt:
•	MainActivity: clase que extiende ComponentActivity y usa setContent {} para cargar la interfaz.
•	setContent: Carga el Composable principal RegistroProductosScreen, que contiene toda la lógica y la interfaz de usuario del registro de productos.
2. RegistroProductosScreen.kt
•	RegistroProductosScreen: Pantalla principal que incluye: formulario de registro, validación de URL, lista de productos, manejo de errores, estado del formulario, botón registrar producto y manejo de imágenes con Coil.
3. Producto.kt
•	Producto: clase donde se define el modelo de los productos registrados: nombre, descripción, marca, precio, existencia, imagenUrl.

Ejecución de la Aplicación
Requisitos Previos
1.	Android Studio: Asegúrate de tener la versión más reciente.
2.	SDK de Android: El proyecto requiere compileSdk = 34.
3.	Gradle: utilizamos  Kotlin DSL para build.gradle.kts. Instalar la versión actualizada de Gradle.
Pasos para Ejecutar el Proyecto
1.	Descargar el Repositorio: Baja el proyecto en tu máquina local.
2.	Abrir Android Studio: seleccionar "Open an existing project" e ir a la carpeta del proyecto.
3.	Sincronizar dependencias: pedirá sincronizar con Gradle
4.	Ejecutar la Aplicación:4
Comportamiento de la Aplicación
1.	Pantalla Inicial: aparece la pantalla "Registro de Productos".
2.	Formulario: se ingresan los datos del producto, junto al URL de la imagen.
3.	Registro: el producto se agrega a la lista con su imagen si la URL es válida.
4.	Errores: en caso de que la URL es inválida, se muestra un Snackbar con un mensaje de error.
