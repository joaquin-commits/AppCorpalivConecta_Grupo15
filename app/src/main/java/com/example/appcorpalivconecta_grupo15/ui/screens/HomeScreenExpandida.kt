
package com.example.appcorpalivconecta_grupo15.ui.screens

// Componentes para organizar la interfaz
import androidx.compose.foundation.layout.*

// Permite mostrar imágenes
import androidx.compose.foundation.Image

// Componentes de Material Design 3
import androidx.compose.material3.*

// Funciones de Jetpack Compose
import androidx.compose.runtime.Composable

// Modificadores, alineación y recursos
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

// Vista previa
import androidx.compose.ui.tooling.preview.Preview

// Acceso al logo del proyecto
import com.example.appcorpalivconecta_grupo15.R

// Tema visual de CORPALIV Conecta
import com.example.appcorpalivconecta_grupo15.ui.theme.AppCorpalivConecta_Grupo15Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenExpandida() {

    // Estructura principal de la pantalla
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("CORPALIV Conecta")
                }
            )
        }
    ) { innerPadding ->

        // Contenedor que ocupa el espacio disponible
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {

            // Fila central para la distribución expandida
            Row(
                modifier = Modifier
                    .widthIn(max = 1100.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(48.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Información institucional a la izquierda
                Column(
                    modifier = Modifier.weight(1.2f),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {

                    Text(
                        text = "¡Bienvenido a CORPALIV!",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineLarge
                    )

                    Text(
                        text = "Promovemos la inclusión social y laboral " +
                                "de personas con discapacidad múltiple. " +
                                "Conoce nuestros programas y descubre " +
                                "cómo puedes colaborar.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Button(
                        onClick = {
                            // Acción futura del botón
                        }
                    ) {
                        Text("Conoce nuestras iniciativas")
                    }
                }

                // Logo institucional a la derecha
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo de CORPALIV Conecta",
                    modifier = Modifier
                        .weight(1f)
                        .height(300.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

// Vista previa de pantalla expandida
@Preview(
    name = "Expanded",
    widthDp = 1000,
    heightDp = 800,
    showBackground = true
)
@Composable
fun PreviewExpandida() {
    AppCorpalivConecta_Grupo15Theme {
        HomeScreenExpandida()
    }
}
