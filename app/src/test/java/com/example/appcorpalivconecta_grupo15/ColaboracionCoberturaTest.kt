package com.example.appcorpalivconecta_grupo15

import com.example.appcorpalivconecta_grupo15.model.Solicitud
import com.example.appcorpalivconecta_grupo15.repository.SolicitudRemotaRepository
import com.example.appcorpalivconecta_grupo15.viewmodel.ColaboracionViewModel
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class ColaboracionCoberturaTest {
    private val repo = mockk<SolicitudRemotaRepository>()
    private lateinit var vm: ColaboracionViewModel
    @Before fun iniciar() { Dispatchers.setMain(UnconfinedTestDispatcher()); vm = ColaboracionViewModel(repo) }
    @After fun terminar() { Dispatchers.resetMain() }
    @Test fun cambiarNombre() { vm.cambiar("nombre", "Ana"); assertEquals("Ana", vm.form.value.nombre) }
    @Test fun cambiarCorreo() { vm.cambiar("correo", "ana@ejemplo.cl"); assertEquals("ana@ejemplo.cl", vm.form.value.correo) }
    @Test fun cambiarOrganizacion() { vm.cambiar("organizacion", "Empresa"); assertEquals("Empresa", vm.form.value.organizacion) }
    @Test fun cambiarTipo() { vm.cambiar("tipo", "Donación"); assertEquals("Donación", vm.form.value.tipo) }
    @Test fun cambiarMensaje() { vm.cambiar("mensaje", "Mensaje de prueba"); assertEquals("Mensaje de prueba", vm.form.value.mensaje) }
    @Test fun cambiarUbicacion() { vm.cambiar("ubicacion", "Santiago"); assertEquals("Santiago", vm.form.value.ubicacion) }
    @Test fun campoDesconocido() { val inicial = vm.form.value; vm.cambiar("desconocido", "x"); assertEquals(inicial, vm.form.value) }
    @Test fun corregirNombreEliminaSoloSuError() { assertFalse(vm.validar()); vm.cambiar("nombre", "Ana"); assertFalse(vm.form.value.errores.containsKey("nombre")); assertTrue(vm.form.value.errores.containsKey("correo")) }
    @Test fun guardarGeneraFechaYRecortaCampos() { val capturada = slot<Solicitud>(); coEvery { repo.guardar(capture(capturada)) } answers { capturada.captured.copy(id = 1) }; vm.cambiar("nombre", " Ana "); vm.cambiar("correo", "ana@ejemplo.cl"); vm.cambiar("organizacion", " Empresa "); vm.cambiar("tipo", "Donación"); vm.cambiar("mensaje", " Mensaje válido "); vm.enviar(); assertTrue(vm.form.value.enviado); assertEquals("Ana", capturada.captured.nombre); assertEquals("Empresa", capturada.captured.organizacion); assertEquals("Mensaje válido", capturada.captured.mensaje); assertTrue(capturada.captured.fecha.matches(Regex("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}"))) }
    @Test fun evitaOperacionesSimultaneas() = runTest { val pendiente = CompletableDeferred<List<Solicitud>>(); coEvery { repo.listar() } coAnswers { pendiente.await() }; vm.cargar(); assertTrue(vm.ocupado.value); vm.cargar(); coVerify(exactly = 1) { repo.listar() }; pendiente.complete(emptyList()); advanceUntilIdle(); assertFalse(vm.ocupado.value) }
    @Test fun eliminarConservaOtrosRegistros() { val a = Solicitud("Ana", "ana@ejemplo.cl", "", "Donación", "Mensaje válido", "", "26/11/2026", id = 1); val b = a.copy(id = 2); coEvery { repo.listar() } returns listOf(a,b); coEvery { repo.eliminar(1) } just Runs; vm.cargar(); vm.eliminarSolicitud(1); assertEquals(listOf(b), vm.solicitudes.value) }
}
