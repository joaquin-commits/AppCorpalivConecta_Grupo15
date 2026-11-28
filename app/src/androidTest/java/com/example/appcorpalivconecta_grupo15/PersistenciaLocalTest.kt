package com.example.appcorpalivconecta_grupo15

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.appcorpalivconecta_grupo15.data.EstadoDataStore
import com.example.appcorpalivconecta_grupo15.data.SolicitudRepository
import com.example.appcorpalivconecta_grupo15.model.Solicitud
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class PersistenciaLocalTest {
    private val context get() = InstrumentationRegistry.getInstrumentation().targetContext
    private fun nueva() = Solicitud("Prueba ${UUID.randomUUID()}", "prueba@ejemplo.cl", "Organizacion", "Donacion", "Mensaje de prueba", "Santiago", "26/11/2026", "Prueba local")

    private suspend fun verificarEstado(valor: Boolean) {
        val store = EstadoDataStore(context)
        val anterior = store.obtenerEstado().first()
        try {
            store.guardarEstado(valor)
            assertEquals(valor, EstadoDataStore(context).obtenerEstado().first())
        } finally { store.guardarEstado(anterior) }
    }
    @Test fun estadoActivado() = runBlocking { withTimeout(15000) { verificarEstado(true) } }
    @Test fun estadoDesactivado() = runBlocking { withTimeout(15000) { verificarEstado(false) } }
    @Test fun estadoAlternado() = runBlocking { withTimeout(15000) {
        val store = EstadoDataStore(context)
        val anterior = store.obtenerEstado().first()
        try {
            store.guardarEstado(!anterior)
            assertEquals(!anterior, store.obtenerEstado().first())
            store.guardarEstado(anterior)
            assertEquals(anterior, store.obtenerEstado().first())
        } finally { store.guardarEstado(anterior) }
    } }
    private suspend fun limpiar(repo: SolicitudRepository, s: Solicitud) {
        val indice = repo.solicitudes.first().indexOf(s)
        if (indice >= 0) repo.eliminar(indice)
    }
    @Test fun guardarYRecuperar() = runBlocking { withTimeout(15000) {
        val repo = SolicitudRepository(context)
        val s = nueva()
        try {
            repo.guardar(s)
            assertEquals(s, SolicitudRepository(context).solicitudes.first().first())
        } finally { limpiar(repo, s) }
    } }
    @Test fun ordenReciente() = runBlocking { withTimeout(15000) {
        val repo = SolicitudRepository(context)
        val a = nueva(); val b = nueva()
        try {
            repo.guardar(a); repo.guardar(b)
            assertEquals(listOf(b, a), repo.solicitudes.first().take(2))
        } finally { limpiar(repo, b); limpiar(repo, a) }
    } }
    @Test fun eliminarConservaOtros() = runBlocking { withTimeout(15000) {
        val repo = SolicitudRepository(context)
        val anterior = repo.solicitudes.first()
        val a = nueva(); val b = nueva()
        try {
            repo.guardar(a); repo.guardar(b)
            repo.eliminar(1)
            assertEquals(listOf(b) + anterior, repo.solicitudes.first())
        } finally { limpiar(repo, b); limpiar(repo, a) }
    } }
    @Test fun eliminarIndiceInexistente() = runBlocking { withTimeout(15000) {
        val repo = SolicitudRepository(context)
        val anterior = repo.solicitudes.first()
        repo.eliminar(anterior.size)
        assertEquals(anterior, repo.solicitudes.first())
    } }
}
