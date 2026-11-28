package com.example.appcorpalivconecta_grupo15

import android.app.Application
import com.example.appcorpalivconecta_grupo15.data.EstadoDataStore
import com.example.appcorpalivconecta_grupo15.viewmodel.EstadoViewModel
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class EstadoViewModelTest {
    private lateinit var dispatcher: TestDispatcher
    @Before fun iniciar() {
        dispatcher = StandardTestDispatcher()
        Dispatchers.setMain(dispatcher)
        mockkConstructor(EstadoDataStore::class)
        every { anyConstructed<EstadoDataStore>().obtenerEstado() } returns flowOf(false)
        coEvery { anyConstructed<EstadoDataStore>().guardarEstado(any()) } just Runs
    }
    @After fun terminar() { unmockkConstructor(EstadoDataStore::class); Dispatchers.resetMain() }
    private fun crear() = EstadoViewModel(mockk<Application>())
    @Test fun estadoInicialEnCarga() = runTest { val vm = crear(); assertNull(vm.activo.value); assertFalse(vm.mostrarMensaje.value); advanceUntilIdle() }
    @Test fun recuperarDesactivado() = runTest { val vm = crear(); advanceUntilIdle(); assertEquals(false, vm.activo.value) }
    @Test fun recuperarActivado() = runTest { every { anyConstructed<EstadoDataStore>().obtenerEstado() } returns flowOf(true); val vm = crear(); advanceUntilIdle(); assertEquals(true, vm.activo.value) }
    @Test fun ignorarCambioDuranteCarga() = runTest { val vm = crear(); vm.alternarEstado(); runCurrent(); coVerify(exactly = 0) { anyConstructed<EstadoDataStore>().guardarEstado(any()) }; advanceUntilIdle(); assertEquals(false, vm.activo.value) }
    @Test fun activarYGuardar() = runTest { val vm = crear(); advanceUntilIdle(); vm.alternarEstado(); runCurrent(); assertEquals(true, vm.activo.value); coVerify(exactly = 1) { anyConstructed<EstadoDataStore>().guardarEstado(true) }; advanceUntilIdle() }
    @Test fun desactivarYGuardar() = runTest { every { anyConstructed<EstadoDataStore>().obtenerEstado() } returns flowOf(true); val vm = crear(); advanceUntilIdle(); vm.alternarEstado(); runCurrent(); assertEquals(false, vm.activo.value); coVerify(exactly = 1) { anyConstructed<EstadoDataStore>().guardarEstado(false) }; advanceUntilIdle() }
    @Test fun mensajeTemporal() = runTest { val vm = crear(); advanceUntilIdle(); vm.alternarEstado(); runCurrent(); assertTrue(vm.mostrarMensaje.value); advanceTimeBy(1999); assertTrue(vm.mostrarMensaje.value); advanceTimeBy(1); runCurrent(); assertFalse(vm.mostrarMensaje.value) }
    @Test fun alternarDosVeces() = runTest { val vm = crear(); advanceUntilIdle(); vm.alternarEstado(); advanceUntilIdle(); vm.alternarEstado(); advanceUntilIdle(); assertEquals(false, vm.activo.value); coVerifyOrder { anyConstructed<EstadoDataStore>().guardarEstado(true); anyConstructed<EstadoDataStore>().guardarEstado(false) } }
}
