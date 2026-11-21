package com.example.appcorpalivconecta_grupo15.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/** Instancia única del cliente REST. */
object RetrofitInstance {
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
