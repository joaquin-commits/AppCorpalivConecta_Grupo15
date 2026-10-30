package com.example.appcorpalivconecta_grupo15.navigation

// Definimos las rutas disponibles de la aplicación
sealed class Screen(val route: String) {

    // Ruta de la pantalla principal
    data object Home : Screen("home_page")

    // Ruta de configuración
    data object Settings : Screen("settings_page")

    // Ruta de detalle que recibe un identificador
    data object Detail : Screen("detail_page/{itemId}") {

        // Construye una ruta con el identificador real
        fun createRoute(itemId: String): String {
            return "detail_page/$itemId"
        }
    }
}
