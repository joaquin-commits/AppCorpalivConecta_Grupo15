
package com.example.appcorpalivconecta_grupo15.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import com.example.appcorpalivconecta_grupo15.viewmodel.EstadoViewModel

@Composable
fun EstadoScreen(
    estadoViewModel: EstadoViewModel
) {

    // Observamos los estados del ViewModel
    val estado by estadoViewModel.activo.collectAsState()
    val mostrarMensaje by estadoViewModel.mostrarMensaje.collectAsState()

    // Estado visual guardable durante recreaciones
    var mostrarDescripcion by rememberSaveable {
        mutableStateOf(true)
    }

    // Texto derivado del estado persistente
    val textoBoton by remember(estado) {
        derivedStateOf {
            if (estado == true) {
                "Desactivar modo especial"
            } else {
                "Activar modo especial"
            }
        }
    }

    // Color animado del botón
    val colorAnimado by animateColorAsState(
        targetValue = if (estado == true) {
            Color(0xFF238A72)
        } else {
            Color(0xFF465F99)
        },
        animationSpec = tween(durationMillis = 500),
        label = "animacionModoEspecial"
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        if (estado == null) {

            // Simulación de carga inicial
            CircularProgressIndicator()

        } else {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                Text(
                    text = "Modo especial",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = if (estado == true) {
                        "Estado actual: Activado"
                    } else {
                        "Estado actual: Desactivado"
                    },
                    style = MaterialTheme.typography.titleMedium
                )

                // Botón con cambio de color animado
                Button(
                    onClick = {
                        estadoViewModel.alternarEstado()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorAnimado
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Text(
                        text = textoBoton,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                // Control de información adicional
                TextButton(
                    onClick = {
                        mostrarDescripcion = !mostrarDescripcion
                    }
                ) {
                    Text(
                        if (mostrarDescripcion) {
                            "Ocultar descripción"
                        } else {
                            "Mostrar descripción"
                        }
                    )
                }

                AnimatedVisibility(
                    visible = mostrarDescripcion
                ) {
                    Text(
                        text = "Este modo conserva su estado usando " +
                                "DataStore Preferences, incluso " +
                                "después de cerrar la aplicación.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // Confirmación visual animada
                AnimatedVisibility(
                    visible = mostrarMensaje
                ) {
                    Text(
                        text = "¡Estado guardado exitosamente!",
                        color = Color(0xFF238A72),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
