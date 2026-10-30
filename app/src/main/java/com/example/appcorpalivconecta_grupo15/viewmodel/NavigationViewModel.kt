
package com.example.appcorpalivconecta_grupo15.viewmodel

// Clase base para implementar un ViewModel
import androidx.lifecycle.ViewModel

// Permite ejecutar tareas asociadas al ViewModel
import androidx.lifecycle.viewModelScope

// Eventos de navegación definidos anteriormente
import com.example.appcorpalivconecta_grupo15.navigation.NavigationEvent

// Herramientas de Kotlin Coroutines
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

// ViewModel encargado de emitir eventos de navegación
class NavigationViewModel : ViewModel() {

    // Canal interno de eventos de navegación
    private val _navigationEvents =
        Channel<NavigationEvent>(Channel.BUFFERED)

    // Flujo que podrá observar la interfaz
    val navigationEvents = _navigationEvents.receiveAsFlow()

    // Solicita navegar hacia una pantalla
    fun navigateTo(route: String) {
        viewModelScope.launch {
            _navigationEvents.send(
                NavigationEvent.NavigateTo(route)
            )
        }
    }

    // Solicita regresar a la pantalla anterior
    fun popBackStack() {
        viewModelScope.launch {
            _navigationEvents.send(
                NavigationEvent.PopBackStack
            )
        }
    }
}
