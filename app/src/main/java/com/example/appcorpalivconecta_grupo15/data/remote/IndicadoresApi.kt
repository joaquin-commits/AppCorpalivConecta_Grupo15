package com.example.appcorpalivconecta_grupo15.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET

/** JSON de mindicador.cl. La ruta principal /api entrega el valor de UF en el campo uf. */
data class UfDato(val fecha: String, val valor: Double)
data class UfRespuesta(@SerializedName("serie") val serie: List<UfDato> = emptyList())
data class IndicadoresResumen(val uf: UfDato?)

/** Datos diarios de indicadores.cl; GET /api/v1/uf.json. */
data class UfIndicadoresCl(val fecha: String, val valor: Double)

interface UfIndicadoresClApi {
    @GET("api/v1/uf.json")
    suspend fun obtenerUfDelDia(): UfIndicadoresCl
}

interface IndicadoresApi {
    @GET("api")
    suspend fun obtenerResumen(): IndicadoresResumen

    // Ruta alternativa si el resumen no está disponible.
    @GET("api/uf")
    suspend fun obtenerUfHistorica(): UfRespuesta
}
