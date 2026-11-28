package com.example.appcorpalivconecta_grupo15.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcorpalivconecta_grupo15.data.remote.ApisInstitucionales
import com.example.appcorpalivconecta_grupo15.data.remote.DirectorioCorpaliv
import com.example.appcorpalivconecta_grupo15.data.remote.ResultadoSede
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface EstadoConsulta<out T> {
    data object Inicial : EstadoConsulta<Nothing>
    data object Cargando : EstadoConsulta<Nothing>
    data class Exito<T>(val dato: T) : EstadoConsulta<T>
    data class Error(val mensaje: String) : EstadoConsulta<Nothing>
}

data class UfActual(val valor: Double, val fecha: String, val fuente: String = "indicadores.cl")

class HerramientasExternasViewModel(
    private val principal: com.example.appcorpalivconecta_grupo15.data.remote.UfIndicadoresClApi = ApisInstitucionales.ufPrincipal,
    private val respaldo: com.example.appcorpalivconecta_grupo15.data.remote.IndicadoresApi = ApisInstitucionales.uf,
    private val ubicaciones: com.example.appcorpalivconecta_grupo15.data.remote.SedesApi = ApisInstitucionales.sedes
) : ViewModel() {
    private val _uf = MutableStateFlow<EstadoConsulta<UfActual>>(EstadoConsulta.Inicial)
    val uf = _uf.asStateFlow()

    // Cache por sede: cada resultado puede reutilizarse durante la sesión sin generar consultas.
    private val _sedes = MutableStateFlow<Map<String, EstadoConsulta<ResultadoSede>>>(emptyMap())
    val sedes = _sedes.asStateFlow()

    fun consultarUf() {
        if (_uf.value is EstadoConsulta.Cargando) return
        viewModelScope.launch {
            _uf.value = EstadoConsulta.Cargando
            try {
                // Primero intentamos indicadores.cl. Si falla, intentamos mindicador.cl.
                // Nunca sustituimos una UF real por un valor inventado.
                val resultado: UfActual = try {
                    val dato = principal.obtenerUfDelDia()
                    require(dato.valor.isFinite() && dato.valor > 0) { "UF inválida" }
                    UfActual(dato.valor, dato.fecha, "indicadores.cl")
                } catch (e: kotlinx.coroutines.CancellationException) { throw e } catch (_: Exception) {
                    val dato = try {
                        respaldo.obtenerResumen().uf
                            ?.takeIf { it.valor.isFinite() && it.valor > 0 }
                            ?: error("UF no disponible en el resumen")
                    } catch (e: kotlinx.coroutines.CancellationException) { throw e } catch (_: Exception) {
                        respaldo.obtenerUfHistorica().serie.firstOrNull()
                            ?: error("No hay valor de UF disponible")
                    }
                    require(dato.valor.isFinite() && dato.valor > 0)
                    UfActual(dato.valor, dato.fecha, "mindicador.cl")
                }
                _uf.value = EstadoConsulta.Exito(resultado)
            } catch (e: kotlinx.coroutines.CancellationException) { throw e } catch (ex: Exception) {
                // Mensaje útil para diagnóstico: no convertir un error de red en un valor inventado.
                val detalle = when (ex) {
                    is retrofit2.HttpException -> "HTTP ${ex.code()}"
                    is java.net.UnknownHostException -> "sin acceso al servidor"
                    is java.net.SocketTimeoutException -> "tiempo de espera agotado"
                    is javax.net.ssl.SSLException -> "problema de conexión segura"
                    else -> ex.localizedMessage?.take(90) ?: "error de conexión"
                }
                _uf.value = EstadoConsulta.Error("No fue posible consultar la UF ($detalle). Comprueba internet y vuelve a intentar.")
            }
        }
    }

    fun consultarSede(id: String) {
        val sede = DirectorioCorpaliv.lugares.firstOrNull { it.id == id } ?: return
        val actual = _sedes.value[id]
        if (actual is EstadoConsulta.Cargando || actual is EstadoConsulta.Exito) return
        viewModelScope.launch {
            _sedes.value = _sedes.value + (id to EstadoConsulta.Cargando)
            try {
                val dato = ubicaciones.buscarSede(sede.direccion).firstOrNull()
                    ?: error("Dirección no encontrada")
                require(dato.lat.toDoubleOrNull()?.let { it.isFinite() && it in -90.0..90.0 } == true && dato.lon.toDoubleOrNull()?.let { it.isFinite() && it in -180.0..180.0 } == true)
                _sedes.value = _sedes.value + (id to EstadoConsulta.Exito(dato))
            } catch (e: kotlinx.coroutines.CancellationException) { throw e } catch (_: Exception) {
                _sedes.value = _sedes.value + (id to EstadoConsulta.Error(
                    "No se pudieron obtener coordenadas. Puedes abrir la dirección registrada en tu aplicación de mapas."
                ))
            }
        }
    }
}

/** Cálculo orientativo, no representa un cobro ni una donación procesada. */
fun convertirUfAPesos(cantidadUf: Double, valorUf: Double): Double {
    require(cantidadUf >= 0 && cantidadUf.isFinite())
    require(valorUf > 0 && valorUf.isFinite())
    return cantidadUf * valorUf
}
