
package com.example.appcorpalivconecta_grupo15.model

// Representa el estado del formulario de registro
data class UsuarioUiState(

    // Datos ingresados por el usuario
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val direccion: String = "",

    // Aceptación de términos y condiciones
    val aceptaTerminos: Boolean = false,

    // Errores asociados a la validación
    val errores: UsuarioErrores = UsuarioErrores()
)
