
package com.example.appcorpalivconecta_grupo15.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.example.appcorpalivconecta_grupo15.viewmodel.UsuarioViewModel

@Composable
fun ResumenScreen(
    usuarioViewModel: UsuarioViewModel,
    onVolver: () -> Unit
) {
    // Observamos los datos del ViewModel compartido
    val estado by usuarioViewModel.estado.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Resumen del registro",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Estos son los datos ingresados en CORPALIV Conecta:",
            style = MaterialTheme.typography.bodyMedium
        )

        HorizontalDivider()

        Text("Nombre: ${estado.nombre}")

        Text("Correo: ${estado.correo}")

        Text("Dirección: ${estado.direccion}")

        Text(
            text = "Términos aceptados: " +
                    if (estado.aceptaTerminos) "Sí" else "No"
        )

        // No mostramos la contraseña por seguridad

        Button(
            onClick = onVolver
        ) {
            Text("Volver al formulario")
        }
    }
}
