package com.example.appcorpalivconecta_grupo15.repository
import com.example.appcorpalivconecta_grupo15.model.Solicitud
import com.example.appcorpalivconecta_grupo15.data.remote.SolicitudApi
import com.example.appcorpalivconecta_grupo15.data.remote.SolicitudRetrofit
import retrofit2.HttpException
class SolicitudRemotaRepository(private val api: SolicitudApi = SolicitudRetrofit.api) {
 suspend fun listar() = api.listar()
 suspend fun guardar(s: Solicitud) = if (s.id == 0L) api.crear(s) else api.editar(s.id,s)
 suspend fun eliminar(id: Long) { val r = api.eliminar(id); if (!r.isSuccessful) throw HttpException(r) }
}
