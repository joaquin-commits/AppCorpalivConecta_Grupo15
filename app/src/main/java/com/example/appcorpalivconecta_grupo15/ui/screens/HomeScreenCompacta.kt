
package com.example.appcorpalivconecta_grupo15.ui.screens

// Componentes para organizar la pantalla
import androidx.compose.foundation.layout.*

// Permite mostrar imágenes
import androidx.compose.foundation.Image

// Componentes de Material Design 3
import androidx.compose.material3.*

// Funciones de Jetpack Compose
import androidx.compose.runtime.Composable

// Modificadores de tamaño y espacio
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

// Vista previa
import androidx.compose.ui.tooling.preview.Preview

// Imagen guardada en res/drawable
import com.example.appcorpalivconecta_grupo15.R

// Tema visual de CORPALIV Conecta
import com.example.appcorpalivconecta_grupo15.ui.theme.AppCorpalivConecta_Grupo15Theme

@Composable
fun HomeScreenCompacta() {

    // Estructura principal sin barra superior duplicada
    Scaffold { innerPadding ->

        // Organización vertical para pantallas pequeñas
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),

            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // Título de bienvenida
            Text(
                text = "¡Bienvenido a CORPALIV!",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge
            )

            // Descripción institucional
            Text(
                text = "Conoce nuestras iniciativas de inclusión " +
                        "social y laboral, y descubre cómo colaborar.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Botón de ejemplo
            Button(
                onClick = {
                    // Acción futura
                }
            ) {
                Text("Presióname")
            }

            // Logo de CORPALIV Conecta
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo de CORPALIV Conecta",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

// Vista previa para pantalla compacta
@Preview(
    name = "Compact",
    widthDp = 360,
    heightDp = 800,
    showBackground = true
)
@Composable
fun PreviewCompacta() {
    AppCorpalivConecta_Grupo15Theme {
        HomeScreenCompacta()
    }
}
