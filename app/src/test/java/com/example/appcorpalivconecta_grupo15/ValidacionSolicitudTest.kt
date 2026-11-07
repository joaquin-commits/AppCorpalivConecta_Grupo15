package com.example.appcorpalivconecta_grupo15

import com.example.appcorpalivconecta_grupo15.model.*
import org.junit.Assert.*
import org.junit.Test

class ValidacionSolicitudTest {
    private fun valido() = FormState(
        nombre = "Persona de prueba", correo = "prueba@ejemplo.cl",
        tipo = "Consulta de producto", mensaje = "Deseo información de este producto"
    )

    @Test fun formularioCompletoNoTieneErrores() {
        assertTrue(ValidacionSolicitud.validar(valido()).isEmpty())
    }

    @Test fun nombreCortoInformaError() {
        assertTrue(ValidacionSolicitud.validar(valido().copy(nombre = "A")) .containsKey("nombre"))
    }

    @Test fun correoIncorrectoInformaError() {
        assertTrue(ValidacionSolicitud.validar(valido().copy(correo = "no-es-correo")).containsKey("correo"))
    }

    @Test fun tipoVacioInformaError() {
        assertTrue(ValidacionSolicitud.validar(valido().copy(tipo = "")).containsKey("tipo"))
    }

    @Test fun mensajeCortoInformaError() {
        assertTrue(ValidacionSolicitud.validar(valido().copy(mensaje = "hola")).containsKey("mensaje"))
    }

    @Test fun todosLosItemsDelCatalogoTienenAccion() {
        assertTrue(ContenidoCorpaliv.catalogo.all { it.textoAccion.isNotBlank() && it.enlace.startsWith("https://") })
        assertEquals(ContenidoCorpaliv.catalogo.size, ContenidoCorpaliv.catalogo.map { it.id }.distinct().size)
    }
}
