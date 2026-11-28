
package com.example.appcorpalivconecta_grupo15.data.remote

import com.example.appcorpalivconecta_grupo15.model.Solicitud
import retrofit2.http.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface SolicitudApi {

 @GET("solicitudes")
 suspend fun listar(): List<Solicitud>

 @POST("solicitudes")
 suspend fun crear(@Body solicitud: Solicitud): Solicitud

 @PUT("solicitudes/{id}")
 suspend fun editar(
  @Path("id") id: Long,
  @Body solicitud: Solicitud
 ): Solicitud

 @DELETE("solicitudes/{id}")
 suspend fun eliminar(
  @Path("id") id: Long
 ): Response<Unit>
}

object SolicitudRetrofit {

 // Conexion local mediante ADB Reverse
 const val BASE_URL = "http://127.0.0.1:8080/"

 val api: SolicitudApi by lazy {
  Retrofit.Builder()
   .baseUrl(BASE_URL)
   .addConverterFactory(GsonConverterFactory.create())
   .build()
   .create(SolicitudApi::class.java)
 }
}
