
package com.example.appcorpalivconecta_grupo15.ui.utils

// Permite acceder a la actividad actual
import androidx.activity.compose.LocalActivity

// Permite crear funciones de Jetpack Compose
import androidx.compose.runtime.Composable

// Clase que representa el tamaño de ventana
import androidx.compose.material3.windowsizeclass.WindowSizeClass

// Habilita el uso de esta API experimental
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi

// Función que calcula el tamaño de ventana
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass

// Función reutilizable para obtener el tamaño
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun obtenerWindowSizeClass(): WindowSizeClass {

    // Obtenemos la actividad actual
    val activity = LocalActivity.current
        ?: error("No se encontró la actividad actual")

    // Calculamos y devolvemos la clasificación
    return calculateWindowSizeClass(activity)
}
