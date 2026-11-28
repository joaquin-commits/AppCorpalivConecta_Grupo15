package com.example.appcorpalivconecta_grupo15
import com.example.appcorpalivconecta_grupo15.data.remote.*
import com.example.appcorpalivconecta_grupo15.viewmodel.*
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.*
@OptIn(ExperimentalCoroutinesApi::class)
class HerramientasExternasViewModelTest {
 private val p=mockk<UfIndicadoresClApi>(); private val r=mockk<IndicadoresApi>(); private val u=mockk<SedesApi>()
 private lateinit var vm:HerramientasExternasViewModel
 private val dato=UfDato("2026-11-26",40000.0)
 private val id=DirectorioCorpaliv.lugares.first().id
 private val sede=ResultadoSede("-33.4","-70.6","Santiago")
 @Before fun inicio(){Dispatchers.setMain(UnconfinedTestDispatcher());vm=HerramientasExternasViewModel(p,r,u)}
 @After fun fin(){Dispatchers.resetMain()}
 @Test fun ufPrincipal(){coEvery{p.obtenerUfDelDia()} returns UfIndicadoresCl(dato.fecha,dato.valor);vm.consultarUf();assertEquals(EstadoConsulta.Exito(UfActual(dato.valor,dato.fecha,"indicadores.cl")),vm.uf.value)}
 @Test fun ufRespaldo(){coEvery{p.obtenerUfDelDia()} throws java.io.IOException();coEvery{r.obtenerResumen()} returns IndicadoresResumen(dato);vm.consultarUf();assertEquals(EstadoConsulta.Exito(UfActual(dato.valor,dato.fecha,"mindicador.cl")),vm.uf.value)}
 @Test fun ufHistorica(){coEvery{p.obtenerUfDelDia()} returns UfIndicadoresCl(dato.fecha,0.0);coEvery{r.obtenerResumen()} returns IndicadoresResumen(null);coEvery{r.obtenerUfHistorica()} returns UfRespuesta(listOf(dato));vm.consultarUf();assertTrue(vm.uf.value is EstadoConsulta.Exito)}
 @Test fun ufSinDatos(){coEvery{p.obtenerUfDelDia()} throws java.io.IOException();coEvery{r.obtenerResumen()} returns IndicadoresResumen(null);coEvery{r.obtenerUfHistorica()} returns UfRespuesta();vm.consultarUf();assertTrue(vm.uf.value is EstadoConsulta.Error)}
 @Test fun ufInvalida(){coEvery{p.obtenerUfDelDia()} returns UfIndicadoresCl(dato.fecha,-1.0);coEvery{r.obtenerResumen()} returns IndicadoresResumen(null);coEvery{r.obtenerUfHistorica()} returns UfRespuesta(listOf(dato.copy(valor=Double.NaN)));vm.consultarUf();assertTrue(vm.uf.value is EstadoConsulta.Error)}
 @Test fun sedeEncontrada(){coEvery{u.buscarSede(any(),any(),any(),any())} returns listOf(sede);vm.consultarSede(id);assertEquals(EstadoConsulta.Exito(sede),vm.sedes.value[id])}
 @Test fun sedeVacia(){coEvery{u.buscarSede(any(),any(),any(),any())} returns emptyList();vm.consultarSede(id);assertTrue(vm.sedes.value[id] is EstadoConsulta.Error)}
 @Test fun sedeRedCaida(){coEvery{u.buscarSede(any(),any(),any(),any())} throws java.io.IOException();vm.consultarSede(id);assertTrue(vm.sedes.value[id] is EstadoConsulta.Error)}
 @Test fun sedeCoordenadaInvalida(){coEvery{u.buscarSede(any(),any(),any(),any())} returns listOf(sede.copy(lat="999"));vm.consultarSede(id);assertTrue(vm.sedes.value[id] is EstadoConsulta.Error)}
 @Test fun sedeCache(){coEvery{u.buscarSede(any(),any(),any(),any())} returns listOf(sede);vm.consultarSede(id);vm.consultarSede(id);coVerify(exactly=1){u.buscarSede(any(),any(),any(),any())}}
 @Test fun sedeDesconocida(){vm.consultarSede("no-existe");assertTrue(vm.sedes.value.isEmpty());coVerify(exactly=0){u.buscarSede(any(),any(),any(),any())}}
}
