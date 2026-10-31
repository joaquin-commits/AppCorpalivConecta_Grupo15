
package com.example.appcorpalivconecta_grupo15.model

// Almacena los posibles errores del formulario
data class UsuarioErrores(
    val nombre: String? = null,
    val correo: String? = null,
    val clave: String? = null,
    val direccion: String? = null
)
