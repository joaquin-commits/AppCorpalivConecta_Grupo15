
package com.example.appcorpalivconecta_grupo15.ui.screens

// Componentes de organización visual
import androidx.compose.foundation.layout.*

// Componentes de Material Design 3
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
fun SettingsScreen() {

    Scaffold { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Configuración de CORPALIV Conecta",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Aquí podrás consultar las opciones generales de la aplicación.",
                style = MaterialTheme.typography.bodyMedium
            )

            HorizontalDivider()

            Text(
                text = "Información de la aplicación",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "CORPALIV Conecta - Grupo 15",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

// Vista previa de Configuración
@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    AppCorpalivConecta_Grupo15Theme {
        SettingsScreen()
    }
}
