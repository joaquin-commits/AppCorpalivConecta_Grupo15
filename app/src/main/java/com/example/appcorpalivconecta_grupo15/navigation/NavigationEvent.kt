package com.example.appcorpalivconecta_grupo15.navigation

// Representa las acciones posibles de navegación
sealed class NavigationEvent {

    // Evento para navegar hacia una ruta
    data class NavigateTo(val route: String) : NavigationEvent()

    // Evento para regresar a la pantalla anterior
    data object PopBackStack : NavigationEvent()
}
