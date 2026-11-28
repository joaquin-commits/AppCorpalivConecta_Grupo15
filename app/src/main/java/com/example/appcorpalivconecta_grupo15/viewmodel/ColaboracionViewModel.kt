
package com.example.appcorpalivconecta_grupo15.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcorpalivconecta_grupo15.repository.SolicitudRemotaRepository
import com.example.appcorpalivconecta_grupo15.model.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ColaboracionViewModel(
 private val repository: SolicitudRemotaRepository = SolicitudRemotaRepository()
) : ViewModel() {

 private val _form = MutableStateFlow(FormState())
 val form = _form.asStateFlow()

 private val _solicitudes = MutableStateFlow<List<Solicitud>>(emptyList())
 val solicitudes = _solicitudes.asStateFlow()

 private val _ocupado = MutableStateFlow(false)
 val ocupado = _ocupado.asStateFlow()

 private val _error = MutableStateFlow<String?>(null)
 val error = _error.asStateFlow()

 fun cambiar(campo: String, valor: String) {
  _form.update { s ->
   val e = s.errores - campo

   when (campo) {
    "nombre" -> s.copy(nombre = valor, errores = e)
    "correo" -> s.copy(correo = valor, errores = e)
    "organizacion" -> s.copy(organizacion = valor, errores = e)
    "tipo" -> s.copy(tipo = valor, errores = e)
    "mensaje" -> s.copy(mensaje = valor, errores = e)
    "ubicacion" -> s.copy(ubicacion = valor)
    else -> s
   }
  }
 }

 fun prepararConsulta(titulo: String, tipo: String) {
  _form.value = FormState(
   tipo = tipo,
   contexto = titulo
  )

  _error.value = null
 }

 fun prepararEdicion(s: Solicitud) {
  _form.value = FormState(
   nombre = s.nombre,
   correo = s.correo,
   organizacion = s.organizacion,
   tipo = s.tipo,
   mensaje = s.mensaje,
   ubicacion = s.ubicacion,
   contexto = s.contexto,
   id = s.id,
   fecha = s.fecha
  )

  _error.value = null
 }

 fun validar(): Boolean {
  val e = ValidacionSolicitud.validar(_form.value)

  _form.update {
   it.copy(errores = e)
  }

  return e.isEmpty()
 }

 // Control de operaciones y manejo de errores
 private fun operar(accion: suspend () -> Unit) {
  if (_ocupado.value) return

  _ocupado.value = true
  _error.value = null

  viewModelScope.launch {
   try {
    accion()

   } catch (e: CancellationException) {
    throw e

   } catch (e: Exception) {
    _error.value =
     "No se pudo completar la operación. Revisa la conexión con la API e inténtalo nuevamente."

   } finally {
    _ocupado.value = false
   }
  }
 }

 // GET: consultar solicitudes
 fun cargar() = operar {
  _solicitudes.value = repository.listar()
 }

 // POST y PUT: registrar o modificar solicitudes
 fun enviar() {
  if (!validar()) return

  val s = _form.value

  operar {
   val guardada = repository.guardar(
    Solicitud(
     s.nombre.trim(),
     s.correo.trim(),
     s.organizacion.trim(),
     s.tipo,
     s.mensaje.trim(),
     s.ubicacion,
     s.fecha.ifBlank {
      SimpleDateFormat(
       "dd/MM/yyyy HH:mm",
       Locale("es", "CL")
      ).format(Date())
     },
     s.contexto,
     s.id
    )
   )

   _solicitudes.update {
    listOf(guardada) + it.filterNot { r ->
     r.id == guardada.id
    }
   }

   _form.update {
    it.copy(enviado = true)
   }
  }
 }

 // DELETE: eliminar solicitud
 fun eliminarSolicitud(id: Long) = operar {
  repository.eliminar(id)

  _solicitudes.update {
   it.filterNot { s ->
    s.id == id
   }
  }
 }

 // Restablecer formulario
 fun nuevo() {
  _form.value = FormState()
  _error.value = null
 }
}
