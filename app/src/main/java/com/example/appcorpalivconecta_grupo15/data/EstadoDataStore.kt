
package com.example.appcorpalivconecta_grupo15.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Creamos una instancia de DataStore asociada al contexto
private val Context.dataStore by preferencesDataStore(
    name = "preferencias_usuario"
)

// Gestiona el almacenamiento persistente del modo especial
class EstadoDataStore(private val context: Context) {

    companion object {
        // Clave con la que guardaremos el estado
        private val ESTADO_ACTIVADO =
            booleanPreferencesKey("modo_activado")
    }

    // Guarda el nuevo estado en DataStore
    suspend fun guardarEstado(valor: Boolean) {
        context.dataStore.edit { preferencias ->
            preferencias[ESTADO_ACTIVADO] = valor
        }
    }

    // Recupera el estado de manera reactiva
    fun obtenerEstado(): Flow<Boolean> {
        return context.dataStore.data.map { preferencias ->
            preferencias[ESTADO_ACTIVADO] ?: false
        }
    }
}
