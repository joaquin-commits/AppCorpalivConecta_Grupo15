package com.example.appcorpalivconecta_grupo15.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Paleta basada en el logo proporcionado por el usuario: azul, turquesa y naranja.
private val coloresInstitucionales = lightColorScheme(
    primary = Color(0xFF093E78),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDFEEFC),
    onPrimaryContainer = Color(0xFF093E78),
    secondary = Color(0xFF008F8A),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFD9F2EF),
    tertiary = Color(0xFFFF9C0A),
    background = Color(0xFFF2F7FC),
    surface = Color.White,
    surfaceVariant = Color(0xFFE5EEF7),
    onSurface = Color(0xFF14253A),
    onSurfaceVariant = Color(0xFF405569)
)

@Composable
fun AppCorpalivConecta_Grupo15Theme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = coloresInstitucionales, content = content)
}
