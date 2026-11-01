
package com.example.appcorpalivconecta_grupo15.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope

import com.example.appcorpalivconecta_grupo15.data.EstadoDataStore

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// Gestiona el estado persistente del modo especial
class EstadoViewModel(
    application: Application
) : AndroidViewModel(application) {

    // Acceso al almacenamiento local
    private val estadoDataStore = EstadoDataStore(application)

    // null indica que todavía estamos cargando
    private val _activo = MutableStateFlow<Boolean?>(null)
    val activo: StateFlow<Boolean?> = _activo

    // Controla la visibilidad del mensaje animado
    private val _mostrarMensaje = MutableStateFlow(false)
    val mostrarMensaje: StateFlow<Boolean> = _mostrarMensaje

    // Cargar los datos al crear el ViewModel
    init {
        cargarEstado()
    }

    // Recuperar el estado guardado
    private fun cargarEstado() {
        viewModelScope.launch {

            // Simula una carga inicial para mostrar el loader
            delay(1500)

            _activo.value = estadoDataStore
                .obtenerEstado()
                .first()
        }
    }

    // Cambiar entre activado y desactivado
    fun alternarEstado() {
        viewModelScope.launch {

            // Evita cambiar el estado mientras carga
            val estadoActual = _activo.value ?: return@launch

            val nuevoValor = !estadoActual

            // Guardar el nuevo estado en DataStore
            estadoDataStore.guardarEstado(nuevoValor)

            // Actualizar el estado de la interfaz
            _activo.value = nuevoValor

            // Mostrar mensaje de confirmación
            _mostrarMensaje.value = true

            // Ocultar mensaje después de 2 segundos
            delay(2000)
            _mostrarMensaje.value = false
        }
    }
}
