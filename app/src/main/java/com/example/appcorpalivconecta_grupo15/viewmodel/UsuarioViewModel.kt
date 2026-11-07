
package com.example.appcorpalivconecta_grupo15.viewmodel

import androidx.lifecycle.ViewModel
import com.example.appcorpalivconecta_grupo15.model.UsuarioUiState
import com.example.appcorpalivconecta_grupo15.model.UsuarioErrores
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

// Gestiona el estado y las validaciones del formulario
class UsuarioViewModel : ViewModel() {

    // Estado interno del formulario
    private val _estado = MutableStateFlow(UsuarioUiState())

    // Estado que observarán las pantallas
    val estado: StateFlow<UsuarioUiState> = _estado

    // Actualizar nombre
    fun onNombreChange(valor: String) {
        _estado.update {
            it.copy(
                nombre = valor,
                errores = it.errores.copy(nombre = null)
            )
        }
    }

    // Actualizar correo
    fun onCorreoChange(valor: String) {
        _estado.update {
            it.copy(
                correo = valor,
                errores = it.errores.copy(correo = null)
            )
        }
    }

    // Actualizar contraseña
    fun onClaveChange(valor: String) {
        _estado.update {
            it.copy(
                clave = valor,
                errores = it.errores.copy(clave = null)
            )
        }
    }

    // Actualizar dirección
    fun onDireccionChange(valor: String) {
        _estado.update {
            it.copy(
                direccion = valor,
                errores = it.errores.copy(direccion = null)
            )
        }
    }

    // Actualizar aceptación de términos
    fun onAceptaTerminosChange(valor: Boolean) {
        _estado.update {
            it.copy(aceptaTerminos = valor)
        }
    }

    // Validar los datos antes de continuar
    fun validarFormulario(): Boolean {

        val actual = _estado.value

        val errores = UsuarioErrores(
            nombre = if (actual.nombre.isBlank())
                "El nombre es obligatorio" else null,

            correo = if (!android.util.Patterns.EMAIL_ADDRESS
                    .matcher(actual.correo).matches())
                "Ingresa un correo válido" else null,

            clave = if (actual.clave.length < 6)
                "La contraseña debe tener al menos 6 caracteres"
            else null,

            direccion = if (actual.direccion.isBlank())
                "La dirección es obligatoria" else null
        )

        val hayErrores =
            errores.nombre != null ||
                    errores.correo != null ||
                    errores.clave != null ||
                    errores.direccion != null ||
                    !actual.aceptaTerminos

        _estado.update {
            it.copy(errores = errores)
        }

        return !hayErrores
    }
}
