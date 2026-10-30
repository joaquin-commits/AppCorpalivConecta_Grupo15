
package com.example.appcorpalivconecta_grupo15.ui.screens

// Componentes para organizar la pantalla
import androidx.compose.foundation.layout.*

// Componentes visuales de Material 3
import androidx.compose.material3.*

// Funciones de Jetpack Compose
import androidx.compose.runtime.Composable

// Modificadores y medidas
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Vista previa
import androidx.compose.ui.tooling.preview.Preview

// Tema de CORPALIV Conecta
import com.example.appcorpalivconecta_grupo15.ui.theme.AppCorpalivConecta_Grupo15Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    itemId: String,
    onBack: () -> Unit
) {

    Scaffold{ innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),

            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Detalle de CORPALIV Conecta",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Identificador recibido: $itemId",
                style = MaterialTheme.typography.bodyLarge
            )

            HorizontalDivider()

            Text(
                text = "Esta pantalla muestra información de un elemento seleccionado.",
                style = MaterialTheme.typography.bodyMedium
            )

            Button(
                onClick = onBack
            ) {
                Text("Volver")
            }
        }
    }
}

// Vista previa para comprobar el diseño
@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    AppCorpalivConecta_Grupo15Theme {
        DetailScreen(
            itemId = "15",
            onBack = {}
        )
    }
}
