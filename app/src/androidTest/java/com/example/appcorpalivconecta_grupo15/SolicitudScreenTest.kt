package com.example.appcorpalivconecta_grupo15
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.appcorpalivconecta_grupo15.model.Solicitud
import com.example.appcorpalivconecta_grupo15.repository.SolicitudRemotaRepository
import com.example.appcorpalivconecta_grupo15.ui.screens.HistorialScreen
import com.example.appcorpalivconecta_grupo15.viewmodel.ColaboracionViewModel
import io.mockk.*
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*
class SolicitudScreenTest {
 @get:Rule val rule=createAndroidComposeRule<ComponentActivity>()
 private val s=Solicitud("Ana","ana@ejemplo.cl","","Donación","Mensaje válido","","26/11/2026",id=1)
 @Test fun historialVacio(){val r=mockk<SolicitudRemotaRepository>();coEvery{r.listar()} returns emptyList();rule.setContent{HistorialScreen(androidx.compose.runtime.remember { ColaboracionViewModel(r) }) {}};rule.onNodeWithText("Todavía no tienes solicitudes").assertIsDisplayed()}
 @Test fun historialError(){val r=mockk<SolicitudRemotaRepository>();coEvery{r.listar()} throws java.io.IOException();rule.setContent{HistorialScreen(androidx.compose.runtime.remember { ColaboracionViewModel(r) }) {}};rule.onNodeWithText("No se pudo completar la operación. Revisa la conexión con la API e inténtalo nuevamente.").assertIsDisplayed()}
 @Test fun historialMuestraSolicitud(){val r=mockk<SolicitudRemotaRepository>();coEvery{r.listar()} returns listOf(s);rule.setContent{HistorialScreen(androidx.compose.runtime.remember { ColaboracionViewModel(r) }) {}};rule.onNodeWithText("Mensaje válido").assertIsDisplayed()}
 @Test fun editarLlamaAccion(){val r=mockk<SolicitudRemotaRepository>();coEvery{r.listar()} returns listOf(s);var id=0L;rule.setContent{HistorialScreen(androidx.compose.runtime.remember { ColaboracionViewModel(r) },editar={id=it.id}){}};rule.onNodeWithText("Editar solicitud").performClick();rule.runOnIdle{assertEquals(1L,id)}}
 @Test fun borrarCancelado(){val r=mockk<SolicitudRemotaRepository>();coEvery{r.listar()} returns listOf(s);rule.setContent{HistorialScreen(androidx.compose.runtime.remember { ColaboracionViewModel(r) }) {}};rule.onNodeWithText("Eliminar registro").performClick();rule.onNodeWithText("Cancelar").performClick();rule.onNodeWithText("Mensaje válido").assertIsDisplayed();coVerify(exactly=0){r.eliminar(any())}}
 @Test fun borrarConfirmado(){val r=mockk<SolicitudRemotaRepository>();coEvery{r.listar()} returns listOf(s);coEvery{r.eliminar(1)} just Runs;rule.setContent{HistorialScreen(androidx.compose.runtime.remember { ColaboracionViewModel(r) }) {}};rule.onNodeWithText("Eliminar registro").performClick();rule.onNodeWithText("Eliminar",useUnmergedTree=false).performClick();rule.onNodeWithText("Todavía no tienes solicitudes").assertIsDisplayed()}
}
