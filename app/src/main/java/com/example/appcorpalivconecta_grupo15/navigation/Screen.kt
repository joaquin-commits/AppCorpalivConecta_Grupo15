package com.example.appcorpalivconecta_grupo15.navigation

// Define las rutas disponibles de la aplicación
sealed class Screen(val route: String) {

    // Guía 10: Pantalla principal
    data object Home : Screen("home_page")

    // Guía 10: Configuración
    data object Settings : Screen("settings_page")

    // Guía 10: Detalle con identificador
    data object Detail : Screen("detail_page/{itemId}") {
        fun createRoute(itemId: String): String {
            return "detail_page/$itemId"
        }
    }

    // Guía 11: Registro de usuario
    data object Registro : Screen("registro")

    // Guía 11: Resumen de los datos
    data object Resumen : Screen("resumen")
}
