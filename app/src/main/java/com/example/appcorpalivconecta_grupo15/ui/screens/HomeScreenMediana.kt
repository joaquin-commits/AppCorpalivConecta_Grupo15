
package com.example.appcorpalivconecta_grupo15.ui.screens

// Componentes de organización visual
import androidx.compose.foundation.layout.*

// Permite mostrar imágenes
import androidx.compose.foundation.Image

// Componentes de Material Design 3
import androidx.compose.material3.*

// Funciones de Jetpack Compose
import androidx.compose.runtime.Composable

// Modificadores y recursos
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

// Vista previa de Android Studio
import androidx.compose.ui.tooling.preview.Preview

// Acceso al logo de CORPALIV
import com.example.appcorpalivconecta_grupo15.R

// Tema de la aplicación
import com.example.appcorpalivconecta_grupo15.ui.theme.AppCorpalivConecta_Grupo15Theme

@Composable
fun HomeScreenMediana() {

    // Estructura principal sin barra superior duplicada
    Scaffold { innerPadding ->

        // Fila: contenido a la izquierda e imagen a la derecha
        Row(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),

            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Columna de textos y botón
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                // Título de bienvenida
                Text(
                    text = "¡Bienvenido a CORPALIV!",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineSmall
                )

                // Descripción institucional
                Text(
                    text = "Conoce nuestras iniciativas de inclusión " +
                            "social y laboral, y descubre cómo colaborar.",
                    style = MaterialTheme.typography.bodyLarge,
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
            }

            // Logo a la derecha
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo de CORPALIV Conecta",
                modifier = Modifier
                    .weight(1f)
                    .height(220.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

// Vista previa de pantalla mediana
@Preview(
    name = "Medium",
    widthDp = 700,
    heightDp = 900,
    showBackground = true
)
@Composable
fun PreviewMediana() {
    AppCorpalivConecta_Grupo15Theme {
        HomeScreenMediana()
    }
}
