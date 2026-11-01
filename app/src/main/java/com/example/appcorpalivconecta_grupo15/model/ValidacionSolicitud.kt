package com.example.appcorpalivconecta_grupo15.model

/** Validación pura, separada de Android y de la interfaz (guía 11). */
object ValidacionSolicitud {
    private val correoBasico = Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]{2,}$")
    fun validar(form: FormState): Map<String, String> = buildMap {
        if (form.nombre.trim().length < 3) put("nombre", "Escribe tu nombre (al menos 3 caracteres)")
        if (!correoBasico.matches(form.correo.trim())) put("correo", "Ingresa un correo válido, por ejemplo ejemplo@correo.cl")
        if (form.tipo.isBlank()) put("tipo", "Selecciona un tipo de solicitud")
        if (form.mensaje.trim().length < 10) put("mensaje", "Cuéntanos tu interés en al menos 10 caracteres")
    }
}
