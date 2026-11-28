package com.example.appcorpalivconecta_grupo15

import com.example.appcorpalivconecta_grupo15.viewmodel.NavigationViewModel
import com.example.appcorpalivconecta_grupo15.navigation.NavigationEvent
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class NavigationViewModelTest {
    @Before fun iniciar() { Dispatchers.setMain(UnconfinedTestDispatcher()) }
    @After fun terminar() { Dispatchers.resetMain() }
    @Test fun navegarInicio() = runTest { val vm = NavigationViewModel(); vm.navigateTo("inicio"); assertEquals(NavigationEvent.NavigateTo("inicio"), vm.navigationEvents.first()) }
    @Test fun navegarHistorial() = runTest { val vm = NavigationViewModel(); vm.navigateTo("historial"); assertEquals(NavigationEvent.NavigateTo("historial"), vm.navigationEvents.first()) }
    @Test fun rutaVaciaSeEmiteSinValidar() = runTest { val vm = NavigationViewModel(); vm.navigateTo(""); assertEquals(NavigationEvent.NavigateTo(""), vm.navigationEvents.first()) }
    @Test fun regresar() = runTest { val vm = NavigationViewModel(); vm.popBackStack(); assertEquals(NavigationEvent.PopBackStack, vm.navigationEvents.first()) }
    @Test fun eventosEnOrden() = runTest { val vm = NavigationViewModel(); vm.navigateTo("inicio"); vm.navigateTo("historial"); vm.popBackStack(); assertEquals(listOf(NavigationEvent.NavigateTo("inicio"), NavigationEvent.NavigateTo("historial"), NavigationEvent.PopBackStack), vm.navigationEvents.take(3).toList()) }
    @Test fun regresarDosVeces() = runTest { val vm = NavigationViewModel(); vm.popBackStack(); vm.popBackStack(); assertEquals(listOf(NavigationEvent.PopBackStack, NavigationEvent.PopBackStack), vm.navigationEvents.take(2).toList()) }
}
