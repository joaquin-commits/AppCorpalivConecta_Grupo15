package com.example.appcorpalivconecta_grupo15.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/** Dos servicios externos separados de JSONPlaceholder y del futuro backend propio. */
object ApisInstitucionales {
    private val cliente = OkHttpClient.Builder()
        .connectTimeout(12, TimeUnit.SECONDS)
        .readTimeout(12, TimeUnit.SECONDS)
        .build()

    // Proveedor principal de UF, JSON público sin token.
    val ufPrincipal: UfIndicadoresClApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://indicadores.cl/")
            .client(cliente)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UfIndicadoresClApi::class.java)
    }

    // Respaldo, por si el proveedor principal no responde.
    val uf: IndicadoresApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://mindicador.cl/")
            .client(cliente)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IndicadoresApi::class.java)
    }

    val sedes: SedesApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://nominatim.openstreetmap.org/")
            .client(cliente)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SedesApi::class.java)
    }
}
