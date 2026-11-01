package com.example.appcorpalivconecta_grupo15.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcorpalivconecta_grupo15.data.SolicitudRepository
import com.example.appcorpalivconecta_grupo15.model.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
class ColaboracionViewModel(application:Application):AndroidViewModel(application){
 private val repository=SolicitudRepository(application)
 private val _form=MutableStateFlow(FormState())
 val form:StateFlow<FormState> = _form.asStateFlow()
 val solicitudes=repository.solicitudes.stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000),emptyList())
 fun cambiar(campo:String,valor:String){_form.update { s ->
  val e=s.errores-campo
  when(campo){"nombre"->s.copy(nombre=valor,errores=e);"correo"->s.copy(correo=valor,errores=e);"organizacion"->s.copy(organizacion=valor,errores=e);"tipo"->s.copy(tipo=valor,errores=e);"mensaje"->s.copy(mensaje=valor,errores=e);"ubicacion"->s.copy(ubicacion=valor);else->s}
 }}
 fun prepararConsulta(titulo: String, tipo: String) {
  _form.value = FormState(tipo = tipo, contexto = titulo)
 }
 fun validar():Boolean {
  val errores = ValidacionSolicitud.validar(_form.value)
  _form.update { it.copy(errores = errores) }
  return errores.isEmpty()
 }
 fun enviar(){if(!validar())return;val s=_form.value
  viewModelScope.launch {repository.guardar(Solicitud(s.nombre.trim(),s.correo.trim(),s.organizacion.trim(),s.tipo,s.mensaje.trim(),s.ubicacion,SimpleDateFormat("dd/MM/yyyy HH:mm",Locale("es","CL")).format(Date()),s.contexto))
  _form.update { it.copy(enviado=true) }
  }
 }
 fun eliminarSolicitud(index:Int){viewModelScope.launch{repository.eliminar(index)}}
 fun nuevo(){_form.value=FormState()}
}
