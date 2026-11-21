package com.example.appcorpalivconecta_grupo15.repository

import com.example.appcorpalivconecta_grupo15.data.model.Post
import com.example.appcorpalivconecta_grupo15.data.model.PostRequest
import com.example.appcorpalivconecta_grupo15.data.remote.ApiService
import com.example.appcorpalivconecta_grupo15.data.remote.RetrofitInstance

/** Permite sustituir la API por un servicio falso en pruebas unitarias. */
class PostRepository(private val api: ApiService = RetrofitInstance.api) {
    suspend fun obtenerPosts(): List<Post> = api.getPosts()

    suspend fun crearPost(title: String, body: String): Post =
        api.createPost(PostRequest(title = title, body = body))

    suspend fun actualizarPost(id: Int, title: String, body: String): Post =
        api.updatePost(id, PostRequest(title = title, body = body))

    suspend fun eliminarPost(id: Int) {
        val respuesta = api.deletePost(id)
        if (!respuesta.isSuccessful) {
            throw IllegalStateException("Error HTTP ${respuesta.code()} al eliminar")
        }
    }
}
