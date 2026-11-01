package com.example.appcorpalivconecta_grupo15.model

/** Registros exclusivamente locales del prototipo académico. */
data class Solicitud(
    val nombre: String, val correo: String, val organizacion: String,
    val tipo: String, val mensaje: String, val ubicacion: String, val fecha: String,
    val contexto: String = ""
)
data class FormState(
    val nombre: String = "", val correo: String = "", val organizacion: String = "",
    val tipo: String = "", val mensaje: String = "", val ubicacion: String = "",
    val errores: Map<String, String> = emptyMap(), val enviado: Boolean = false,
    val contexto: String = ""
)
