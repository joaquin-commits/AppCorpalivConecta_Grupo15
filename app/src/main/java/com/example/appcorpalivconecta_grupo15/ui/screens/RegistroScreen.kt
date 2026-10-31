
package com.example.appcorpalivconecta_grupo15.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

import com.example.appcorpalivconecta_grupo15.viewmodel.UsuarioViewModel
import com.example.appcorpalivconecta_grupo15.ui.theme.AppCorpalivConecta_Grupo15Theme

@Composable
fun RegistroScreen(
    usuarioViewModel: UsuarioViewModel,
    onRegistroExitoso: () -> Unit
) {
    // Observamos el estado del formulario
    val estado by usuarioViewModel.estado.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Registro de usuario",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Completa tus datos para registrarte en CORPALIV Conecta.",
            style = MaterialTheme.typography.bodyMedium
        )

        // Nombre
        OutlinedTextField(
            value = estado.nombre,
            onValueChange = usuarioViewModel::onNombreChange,
            label = { Text("Nombre completo") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = estado.errores.nombre != null,
            supportingText = {
                estado.errores.nombre?.let {
                    Text(it)
                }
            }
        )

        // Correo
        OutlinedTextField(
            value = estado.correo,
            onValueChange = usuarioViewModel::onCorreoChange,
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            isError = estado.errores.correo != null,
            supportingText = {
                estado.errores.correo?.let {
                    Text(it)
                }
            }
        )

        // Contraseña
        OutlinedTextField(
            value = estado.clave,
            onValueChange = usuarioViewModel::onClaveChange,
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            isError = estado.errores.clave != null,
            supportingText = {
                estado.errores.clave?.let {
                    Text(it)
                }
            }
        )

        // Dirección
        OutlinedTextField(
            value = estado.direccion,
            onValueChange = usuarioViewModel::onDireccionChange,
            label = { Text("Dirección") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = estado.errores.direccion != null,
            supportingText = {
                estado.errores.direccion?.let {
                    Text(it)
                }
            }
        )

        // Aceptación de términos
        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Checkbox(
                checked = estado.aceptaTerminos,
                onCheckedChange = usuarioViewModel::onAceptaTerminosChange
            )

            Text(
                text = "Acepto los términos y condiciones",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // Mensaje cuando no acepta los términos
        if (!estado.aceptaTerminos) {
            Text(
                text = "Debes aceptar los términos para continuar.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }

        // Botón de registro
        Button(
            onClick = {
                if (usuarioViewModel.validarFormulario()) {
                    onRegistroExitoso()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar y ver resumen")
        }
    }
}
