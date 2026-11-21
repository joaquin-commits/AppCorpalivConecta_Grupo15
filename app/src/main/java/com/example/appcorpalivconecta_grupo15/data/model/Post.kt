package com.example.appcorpalivconecta_grupo15.data.model

/** Publicación recibida desde JSONPlaceholder. */
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

/** Datos enviados al crear o actualizar publicaciones. */
data class PostRequest(
    val userId: Int = 1,
    val title: String,
    val body: String
)
