package com.example.appcorpalivconecta_grupo15.ui.theme

// Componentes para mostrar imágenes
import androidx.compose.foundation.Image

// Componentes para organizar la interfaz
import androidx.compose.foundation.layout.*

// Componentes visuales de Material Design 3
import androidx.compose.material3.*

// Permite crear funciones de interfaz Compose
import androidx.compose.runtime.Composable

// Permite modificar tamaños y espacios
import androidx.compose.ui.Modifier

// Permite ajustar la imagen
import androidx.compose.ui.layout.ContentScale

// Permite utilizar imágenes del proyecto
import androidx.compose.ui.res.painterResource

// Unidades para definir tamaños y espacios
import androidx.compose.ui.unit.dp

// Permite previsualizar la pantalla
import androidx.compose.ui.tooling.preview.Preview

// Acceso a los recursos de nuestra aplicación
import com.example.appcorpalivconecta_grupo15.R

// Necesario para TopAppBar en versiones de Material 3
@OptIn(ExperimentalMaterial3Api::class)

// Indica que esta función construye una interfaz
@Composable
fun HomeScreen() {

    // Estructura principal de nuestra pantalla
    Scaffold(
        topBar = {

            // Barra superior de la aplicación
            TopAppBar(
                title = {
                    Text("CORPALIV Conecta")
                }
            )
        }
    ) { innerPadding ->

        // Organiza verticalmente los componentes
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),

            // Espaciado uniforme entre elementos
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // Texto de bienvenida
            Text(
                text = "¡Bienvenido a CORPALIV!",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge
            )

            // Descripción de la organización
            Text(
                text = "Conoce nuestras iniciativas de inclusión " +
                        "social y laboral, y descubre cómo colaborar.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )





            // Botón de ejemplo
            Button(
                onClick = {
                    // Acción futura del botón
                }
            ) {
                Text("Presióname")
            }

            Image(
                // Cargamos la imagen logo.png desde drawable
                painter = painterResource(
                    id = R.drawable.logo
                ),

                // Descripción para accesibilidad
                contentDescription = "Logo de CORPALIV Conecta",

                // Configuramos el tamaño de la imagen
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),

                // Mantiene las proporciones de la imagen
                contentScale = ContentScale.Fit
            )

        }
    }
}

// Vista previa de HomeScreen
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AppCorpalivConecta_Grupo15Theme {
        HomeScreen()
    }
}
