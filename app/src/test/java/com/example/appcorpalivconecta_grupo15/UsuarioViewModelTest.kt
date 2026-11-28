package com.example.appcorpalivconecta_grupo15

import com.example.appcorpalivconecta_grupo15.viewmodel.UsuarioViewModel
import com.example.appcorpalivconecta_grupo15.model.UsuarioUiState
import org.junit.Test
import org.junit.Assert.*

class UsuarioViewModelTest {
    private val vm = UsuarioViewModel()
    @Test fun estadoInicial() { assertEquals(UsuarioUiState(), vm.estado.value) }
    @Test fun nombreActualizado() { vm.onNombreChange("Ana"); assertEquals("Ana", vm.estado.value.nombre) }
    @Test fun nombreVacio() { vm.onNombreChange("Ana"); vm.onNombreChange(""); assertEquals("", vm.estado.value.nombre) }
    @Test fun nombreConEspacios() { vm.onNombreChange(" Ana "); assertEquals(" Ana ", vm.estado.value.nombre) }
    @Test fun correoActualizado() { vm.onCorreoChange("ana@ejemplo.cl"); assertEquals("ana@ejemplo.cl", vm.estado.value.correo) }
    @Test fun correoVacio() { vm.onCorreoChange("ana@ejemplo.cl"); vm.onCorreoChange(""); assertEquals("", vm.estado.value.correo) }
    @Test fun correoInvalidoSeConservaParaValidar() { vm.onCorreoChange("incorrecto"); assertEquals("incorrecto", vm.estado.value.correo) }
    @Test fun claveActualizada() { vm.onClaveChange("abc123"); assertEquals("abc123", vm.estado.value.clave) }
    @Test fun claveCortaSeConservaParaValidar() { vm.onClaveChange("123"); assertEquals("123", vm.estado.value.clave) }
    @Test fun claveVacia() { vm.onClaveChange("abc123"); vm.onClaveChange(""); assertEquals("", vm.estado.value.clave) }
    @Test fun direccionActualizada() { vm.onDireccionChange("Santiago"); assertEquals("Santiago", vm.estado.value.direccion) }
    @Test fun direccionVacia() { vm.onDireccionChange("Santiago"); vm.onDireccionChange(""); assertEquals("", vm.estado.value.direccion) }
    @Test fun direccionConEspacios() { vm.onDireccionChange(" "); assertEquals(" ", vm.estado.value.direccion) }
    @Test fun aceptaTerminos() { vm.onAceptaTerminosChange(true); assertTrue(vm.estado.value.aceptaTerminos) }
    @Test fun revocaTerminos() { vm.onAceptaTerminosChange(true); vm.onAceptaTerminosChange(false); assertFalse(vm.estado.value.aceptaTerminos) }
    @Test fun camposIndependientes() { vm.onNombreChange("Ana"); vm.onCorreoChange("ana@ejemplo.cl"); vm.onClaveChange("abc123"); vm.onDireccionChange("Santiago"); assertEquals(UsuarioUiState("Ana", "ana@ejemplo.cl", "abc123", "Santiago"), vm.estado.value) }
}
