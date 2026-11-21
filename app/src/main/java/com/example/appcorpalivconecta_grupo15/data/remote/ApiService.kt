package com.example.appcorpalivconecta_grupo15.data.remote

import com.example.appcorpalivconecta_grupo15.data.model.Post
import com.example.appcorpalivconecta_grupo15.data.model.PostRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

/** Endpoints de la API REST pública utilizada en la Guía 14. */
interface ApiService {
    @GET("posts")
    suspend fun getPosts(@Query("_limit") limite: Int = 20): List<Post>

    @POST("posts")
    suspend fun createPost(@Body post: PostRequest): Post

    @PUT("posts/{id}")
    suspend fun updatePost(@Path("id") id: Int, @Body post: PostRequest): Post

    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id") id: Int): Response<Unit>
}
