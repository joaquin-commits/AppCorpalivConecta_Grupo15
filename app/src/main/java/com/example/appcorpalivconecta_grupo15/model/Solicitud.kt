package com.example.appcorpalivconecta_grupo15.model

/** Registro de solicitud del prototipo académico. */
data class Solicitud(
    val nombre: String, val correo: String, val organizacion: String,
    val tipo: String, val mensaje: String, val ubicacion: String, val fecha: String,
    val contexto: String = "", val id: Long = 0
)
// Estado reactivo del formulario: valores, errores, contexto y confirmación.
data class FormState(
    val nombre: String = "", val correo: String = "", val organizacion: String = "",
    val tipo: String = "", val mensaje: String = "", val ubicacion: String = "",
    val errores: Map<String, String> = emptyMap(), val enviado: Boolean = false,
    val contexto: String = "", val id: Long = 0, val fecha: String = ""
)
