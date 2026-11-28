package com.example.appcorpalivconecta_grupo15.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appcorpalivconecta_grupo15.viewmodel.*
import com.example.appcorpalivconecta_grupo15.model.ContenidoCorpaliv
import com.example.appcorpalivconecta_grupo15.data.remote.DirectorioCorpaliv
import com.example.appcorpalivconecta_grupo15.data.remote.ResultadoSede
import java.net.URLEncoder
import java.text.NumberFormat
import java.util.Locale

private fun pesos(monto: Double): String =
    NumberFormat.getCurrencyInstance(Locale("es", "CL")).format(monto)

@Composable
fun AportesUfScreen(
    vm: HerramientasExternasViewModel = viewModel(),
    abrirEnlace: (String) -> Unit
) {
    val estado by vm.uf.collectAsState()
    var cantidadTexto by remember { mutableStateOf("1") }
    val cantidad = cantidadTexto.replace(',', '.').toDoubleOrNull()
    val dato = (estado as? EstadoConsulta.Exito<UfActual>)?.dato
    val total = if (dato != null && cantidad != null) {
        runCatching { convertirUfAPesos(cantidad, dato.valor) }.getOrNull()
    } else null
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Aportes y valor de la UF", style = MaterialTheme.typography.headlineSmall)
        Text("Consulta la UF desde un servicio externo y calcula montos referenciales en pesos chilenos. La aplicación no procesa donaciones ni cobros.")
        Button(onClick = vm::consultarUf, enabled = estado !is EstadoConsulta.Cargando) {
            Text(if (estado is EstadoConsulta.Inicial) "Consultar UF" else "Actualizar valor de UF")
        }
        when (val e = estado) {
            EstadoConsulta.Inicial -> Text("Presiona Consultar UF para consultar el indicador económico.")
            EstadoConsulta.Cargando -> CircularProgressIndicator()
            is EstadoConsulta.Error -> Text(e.mensaje, color = MaterialTheme.colorScheme.error)
            is EstadoConsulta.Exito -> ElevatedCard(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
                    Text("Valor informado: ${pesos(e.dato.valor)}", style = MaterialTheme.typography.titleMedium)
                    Text("Fecha publicada: ${e.dato.fecha.take(10)}", style = MaterialTheme.typography.bodySmall)
                    Text("Fuente: ${e.dato.fuente}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
        OutlinedTextField(
            value = cantidadTexto, onValueChange = { cantidadTexto = it },
            label = { Text("Cantidad en UF (referencial)") },
            singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            isError = cantidadTexto.isNotBlank() && (cantidad == null || cantidad < 0),
            modifier = Modifier.fillMaxWidth()
        )
        if (total != null) Text("Equivalente estimado: ${pesos(total)}", style = MaterialTheme.typography.titleLarge)
        Text("Valor orientativo: no constituye una donación, compromiso de pago ni monto exigido por CORPALIV.", style = MaterialTheme.typography.bodySmall)
        Button(onClick = { abrirEnlace(ContenidoCorpaliv.donaciones) }, modifier = Modifier.fillMaxWidth()) {
            Text("Ir al sitio oficial para donar")
        }
        Text("API principal: indicadores.cl/api/v1/uf.json · Respaldo: mindicador.cl/api", style = MaterialTheme.typography.labelSmall)
    }
}

private fun abrirMapa(context: android.content.Context, lat: String?, lon: String?, direccion: String) {
    // La dirección está verificada; si falla la geocodificación, igualmente se puede buscar en mapas.
    val consulta = if (lat != null && lon != null) "$lat,$lon" else direccion
    val url = "https://www.google.com/maps/search/?api=1&query=" +
        URLEncoder.encode(consulta, "UTF-8")
    runCatching { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url))) }
}

@Composable
fun SedeAlbaLabScreen(vm: HerramientasExternasViewModel = viewModel()) {
    val estados by vm.sedes.collectAsState()
    val context = LocalContext.current
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Ubicaciones y red de apoyo", style = MaterialTheme.typography.headlineSmall)
        Text("Encuentra las sedes relacionadas directamente con CORPALIV y otros recursos independientes de inclusión, rehabilitación y accesibilidad. Los lugares externos NO son sucursales ni convenios oficiales de CORPALIV.")
        val grupos = listOf(
            "Sedes vinculadas a CORPALIV" to DirectorioCorpaliv.lugares.filter { it.esSedeCorpaliv },
            "Otras instituciones y recursos de apoyo" to DirectorioCorpaliv.lugares.filter { !it.esSedeCorpaliv }
        )
        grupos.forEach { (titulo, lugares) ->
            Text(titulo, style = MaterialTheme.typography.titleLarge)
            lugares.forEach { lugar ->
            val estado = estados[lugar.id] ?: EstadoConsulta.Inicial
            ElevatedCard(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(lugar.nombre, style = MaterialTheme.typography.titleMedium)
                    Text(lugar.tipo, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelLarge)
                    if (!lugar.esSedeCorpaliv) {
                        Text("Organización independiente de CORPALIV", style = MaterialTheme.typography.labelSmall)
                    }
                    Text(lugar.descripcion)
                    Text("Dirección: ${lugar.direccion}", style = MaterialTheme.typography.bodyMedium)
                    Button(
                        onClick = { vm.consultarSede(lugar.id) },
                        enabled = estado !is EstadoConsulta.Cargando && estado !is EstadoConsulta.Exito,
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("Consultar coordenadas (OpenStreetMap)") }
                    when (estado) {
                        EstadoConsulta.Inicial -> Text("Consulta opcional: puedes abrir el mapa sin obtener coordenadas.", style = MaterialTheme.typography.bodySmall)
                        EstadoConsulta.Cargando -> CircularProgressIndicator()
                        is EstadoConsulta.Error -> Text(estado.mensaje, color = MaterialTheme.colorScheme.error)
                        is EstadoConsulta.Exito -> {
                            Text("Dirección localizada: ${estado.dato.direccionEncontrada}", style = MaterialTheme.typography.bodySmall)
                            Text("Coordenadas: ${estado.dato.lat}, ${estado.dato.lon}")
                        }
                    }
                    val posicion = (estado as? EstadoConsulta.Exito<ResultadoSede>)?.dato
                    OutlinedButton(onClick = {
                        abrirMapa(context, posicion?.lat, posicion?.lon, lugar.direccion)
                    }, modifier = Modifier.fillMaxWidth()) { Text("Ver en mapa / Cómo llegar") }
                    TextButton(onClick = {
                        runCatching {
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(lugar.referencia)))
                        }
                    }) { Text("Información oficial") }
                }
            }
            }
        }
        Text("Coordenadas aproximadas obtenidas mediante Nominatim de OpenStreetMap. Antes de visitar, confirma con la institución respectiva. No se realiza seguimiento de ubicación del usuario.", style = MaterialTheme.typography.bodySmall)
        Text("API externa: Nominatim OpenStreetMap · GET /search", style = MaterialTheme.typography.labelSmall)
    }
}
