
package com.example.appcorpalivconecta_grupo15.ui.screens

// Permite crear funciones de interfaz
import androidx.compose.runtime.Composable

// Clases para identificar el ancho de ventana
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

// Importamos nuestra función reutilizable
import com.example.appcorpalivconecta_grupo15.ui.utils.obtenerWindowSizeClass

// Pantalla principal que selecciona el diseño
@Composable
fun HomeScreen2() {

    // Obtenemos la clasificación de la ventana
    val windowSizeClass = obtenerWindowSizeClass()

    // Seleccionamos según el ancho disponible
    when (windowSizeClass.widthSizeClass) {

        // Pantallas pequeñas
        WindowWidthSizeClass.Compact -> {
            HomeScreenCompacta()
        }

        // Pantallas medianas
        WindowWidthSizeClass.Medium -> {
            HomeScreenMediana()
        }

        // Pantallas amplias
        WindowWidthSizeClass.Expanded -> {
            HomeScreenExpandida()
        }

        // Alternativa para otras clasificaciones
        else -> {
            HomeScreenExpandida()
        }
    }
}
