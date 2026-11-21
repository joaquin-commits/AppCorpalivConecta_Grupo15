package com.example.appcorpalivconecta_grupo15

import com.example.appcorpalivconecta_grupo15.data.model.Post
import com.example.appcorpalivconecta_grupo15.data.model.PostRequest
import com.example.appcorpalivconecta_grupo15.data.remote.ApiService
import com.example.appcorpalivconecta_grupo15.repository.PostRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Response

/** Pruebas sin Internet: el repositorio utiliza una API falsa. */
class PostRepositoryTest {
    private class FakeApi : ApiService {
        var posts = listOf(Post(1, 1, "Inicial", "Contenido"))
        var eliminados = mutableListOf<Int>()
        var limiteUsado = 0
        override suspend fun getPosts(limite: Int): List<Post> {
            limiteUsado = limite
            return posts
        }
        override suspend fun createPost(post: PostRequest): Post = Post(post.userId, 101, post.title, post.body)
        override suspend fun updatePost(id: Int, post: PostRequest): Post = Post(post.userId, id, post.title, post.body)
        override suspend fun deletePost(id: Int): Response<Unit> {
            eliminados.add(id)
            return Response.success(Unit)
        }
    }

    @Test fun getObtieneLista() = runBlocking {
        val fake = FakeApi()
        val resultado = PostRepository(fake).obtenerPosts()
        assertEquals(1, resultado.size)
        assertEquals("Inicial", resultado.first().title)
        assertEquals(20, fake.limiteUsado)
    }

    @Test fun postCreaPublicacion() = runBlocking {
        val creado = PostRepository(FakeApi()).crearPost("Nuevo", "Texto")
        assertEquals(101, creado.id)
        assertEquals("Nuevo", creado.title)
    }

    @Test fun putActualizaPublicacion() = runBlocking {
        val actualizado = PostRepository(FakeApi()).actualizarPost(1, "Editado", "Contenido nuevo")
        assertEquals(1, actualizado.id)
        assertEquals("Editado", actualizado.title)
        assertEquals("Contenido nuevo", actualizado.body)
    }

    @Test fun deleteLlamaEndpoint() = runBlocking {
        val fake = FakeApi()
        PostRepository(fake).eliminarPost(1)
        assertEquals(listOf(1), fake.eliminados)
    }

}
