package com.example.appcorpalivconecta_grupo15
import com.example.appcorpalivconecta_grupo15.model.Solicitud
import com.example.appcorpalivconecta_grupo15.repository.SolicitudRemotaRepository
import com.example.appcorpalivconecta_grupo15.viewmodel.ColaboracionViewModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.*
@OptIn(ExperimentalCoroutinesApi::class)
class SolicitudViewModelTest {
 private val repo=mockk<SolicitudRemotaRepository>()
 private val s=Solicitud("Ana","ana@ejemplo.cl","","Donación","Mensaje válido","","26/11/2026",id=1)
 private lateinit var vm:ColaboracionViewModel
 @Before fun iniciar(){Dispatchers.setMain(UnconfinedTestDispatcher());vm=ColaboracionViewModel(repo)}
 @After fun terminar(){Dispatchers.resetMain()}
 @Test fun cargarLista(){coEvery{repo.listar()} returns listOf(s);vm.cargar();assertEquals(listOf(s),vm.solicitudes.value);assertFalse(vm.ocupado.value)}
 @Test fun cargarVacio(){coEvery{repo.listar()} returns emptyList();vm.cargar();assertTrue(vm.solicitudes.value.isEmpty());assertNull(vm.error.value)}
 @Test fun cargarError(){coEvery{repo.listar()} throws java.io.IOException();vm.cargar();assertNotNull(vm.error.value);assertFalse(vm.ocupado.value)}
 @Test fun formularioInvalido(){vm.enviar();assertEquals(4,vm.form.value.errores.size);coVerify(exactly=0){repo.guardar(any())}}
 @Test fun prepararConsulta(){vm.prepararConsulta("Escuela","Información de programa");assertEquals("Escuela",vm.form.value.contexto);assertEquals("Información de programa",vm.form.value.tipo)}
 @Test fun guardarNuevo(){vm.prepararEdicion(s.copy(id=0));coEvery{repo.guardar(match{it.id==0L})} returns s;vm.enviar();assertTrue(vm.form.value.enviado);assertEquals(listOf(s),vm.solicitudes.value)}
 @Test fun guardarFallo(){vm.prepararEdicion(s.copy(id=0));coEvery{repo.guardar(any())} throws java.io.IOException();vm.enviar();assertFalse(vm.form.value.enviado);assertNotNull(vm.error.value)}
 @Test fun editar(){vm.prepararEdicion(s);vm.cambiar("mensaje","Mensaje editado");val edit=s.copy(mensaje="Mensaje editado");coEvery{repo.guardar(edit)} returns edit;vm.enviar();assertEquals(edit,vm.solicitudes.value.single());assertTrue(vm.form.value.enviado)}
 @Test fun editarInvalido(){vm.prepararEdicion(s);vm.cambiar("mensaje","x");vm.enviar();assertFalse(vm.form.value.enviado);coVerify(exactly=0){repo.guardar(any())}}
 @Test fun editarFallo(){vm.prepararEdicion(s);coEvery{repo.guardar(any())} throws java.io.IOException();vm.enviar();assertFalse(vm.form.value.enviado);assertNotNull(vm.error.value)}
 @Test fun eliminar(){coEvery{repo.listar()} returns listOf(s);coEvery{repo.eliminar(1)} just Runs;vm.cargar();vm.eliminarSolicitud(1);assertTrue(vm.solicitudes.value.isEmpty())}
 @Test fun eliminarFalloConservaLista(){coEvery{repo.listar()} returns listOf(s);coEvery{repo.eliminar(1)} throws java.io.IOException();vm.cargar();vm.eliminarSolicitud(1);assertEquals(listOf(s),vm.solicitudes.value);assertNotNull(vm.error.value)}
 @Test fun limpiarFormulario(){vm.prepararEdicion(s);vm.nuevo();assertEquals(0L,vm.form.value.id);assertTrue(vm.form.value.nombre.isEmpty())}
}
