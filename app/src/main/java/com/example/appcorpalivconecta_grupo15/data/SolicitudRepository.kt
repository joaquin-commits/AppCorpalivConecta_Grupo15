package com.example.appcorpalivconecta_grupo15.data
import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.appcorpalivconecta_grupo15.model.Solicitud
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject

private val Context.solicitudesStore by preferencesDataStore(name="corpaliv_solicitudes")
class SolicitudRepository(private val context:Context){
 private val clave=stringPreferencesKey("solicitudes")
 val solicitudes:Flow<List<Solicitud>> = context.solicitudesStore.data.map { prefs ->
   val arr=JSONArray(prefs[clave] ?: "[]")
   (0 until arr.length()).map { i -> val o=arr.getJSONObject(i); Solicitud(o.optString("nombre"),o.optString("correo"),o.optString("organizacion"),o.optString("tipo"),o.optString("mensaje"),o.optString("ubicacion"),o.optString("fecha"),o.optString("contexto")) }.reversed()
 }
 suspend fun guardar(s:Solicitud){context.solicitudesStore.edit { prefs ->
   val a=JSONArray(prefs[clave] ?: "[]")
   a.put(JSONObject().put("nombre",s.nombre).put("correo",s.correo).put("organizacion",s.organizacion).put("tipo",s.tipo).put("mensaje",s.mensaje).put("ubicacion",s.ubicacion).put("fecha",s.fecha).put("contexto",s.contexto))
   prefs[clave]=a.toString()
 }}
 suspend fun eliminar(index:Int){context.solicitudesStore.edit { prefs ->
   val original=JSONArray(prefs[clave] ?: "[]")
   val dest=JSONArray();val originalIndex=original.length()-1-index
   for (i in 0 until original.length()) if(i!=originalIndex)dest.put(original.getJSONObject(i))
   prefs[clave]=dest.toString()
 }}

}
