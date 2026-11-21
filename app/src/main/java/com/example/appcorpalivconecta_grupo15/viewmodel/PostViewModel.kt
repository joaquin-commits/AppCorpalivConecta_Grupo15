package com.example.appcorpalivconecta_grupo15.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcorpalivconecta_grupo15.data.model.Post
import com.example.appcorpalivconecta_grupo15.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** Estado de la pantalla: datos, progreso, error y confirmaciones. */
data class PostUiState(
    val posts: List<Post> = emptyList(),
    val cargando: Boolean = false,
    val operando: Boolean = false,
    val error: String? = null,
    val aviso: String? = null
)

class PostViewModel(
    private val repositorio: PostRepository = PostRepository()
) : ViewModel() {
    private val _estado = MutableStateFlow(PostUiState())
    val estado: StateFlow<PostUiState> = _estado

    init { cargar() }

    fun cargar() {
        viewModelScope.launch {
            _estado.update { it.copy(cargando = true, error = null, aviso = null) }
            try {
                val posts = repositorio.obtenerPosts()
                _estado.update { it.copy(posts = posts, cargando = false) }
            } catch (e: Exception) {
                _estado.update { it.copy(cargando = false, error = mensajeError(e)) }
            }
        }
    }

    fun crear(titulo: String, contenido: String) {
        if (!validar(titulo, contenido)) return
        viewModelScope.launch {
            _estado.update { it.copy(operando = true, error = null, aviso = null) }
            try {
                val creado = repositorio.crearPost(titulo.trim(), contenido.trim())
                // JSONPlaceholder no persiste escrituras; se representa localmente.
                _estado.update {
                    // JSONPlaceholder devuelve 101 para cada POST; IDs locales únicos
                    // evitan colisiones al crear varias publicaciones consecutivas.
                    val idLocal = maxOf(100, it.posts.maxOfOrNull { post -> post.id } ?: 100) + 1
                    val nuevo = creado.copy(id = idLocal)
                    it.copy(
                        operando = false,
                        posts = listOf(nuevo) + it.posts,
                        aviso = "POST correcto: publicación simulada (ID local ${nuevo.id})."
                    )
                }
            } catch (e: Exception) {
                _estado.update { it.copy(operando = false, error = mensajeError(e)) }
            }
        }
    }

    fun editar(post: Post, titulo: String, contenido: String) {
        if (!validar(titulo, contenido)) return
        viewModelScope.launch {
            _estado.update { it.copy(operando = true, error = null, aviso = null) }
            try {
                // La API pública admite PUT de IDs reales 1..100, no de posts simulados (101).
                if (post.id > 100) {
                    _estado.update { it.copy(operando = false, error = "Este post simulado no admite PUT remoto. Edita uno de los posts originales.") }
                    return@launch
                }
                val modificado = repositorio.actualizarPost(post.id, titulo.trim(), contenido.trim())
                _estado.update { it.copy(
                    operando = false,
                    posts = it.posts.map { existente -> if (existente.id == post.id) modificado else existente },
                    aviso = "PUT correcto: cambio visible temporalmente en esta pantalla."
                ) }
            } catch (e: Exception) {
                _estado.update { it.copy(operando = false, error = mensajeError(e)) }
            }
        }
    }

    fun eliminar(post: Post) {
        viewModelScope.launch {
            _estado.update { it.copy(operando = true, error = null, aviso = null) }
            try {
                if (post.id <= 100) repositorio.eliminarPost(post.id)
                // Los posts creados solo existen en memoria local.
                _estado.update { it.copy(
                    operando = false,
                    posts = it.posts.filterNot { actual -> actual.id == post.id },
                    aviso = if (post.id <= 100)
                        "DELETE correcto: eliminación simulada (no persiste en servidor)."
                    else "Publicación simulada retirada de la lista local."
                ) }
            } catch (e: Exception) {
                _estado.update { it.copy(operando = false, error = mensajeError(e)) }
            }
        }
    }

    fun limpiarMensajes() { _estado.update { it.copy(error = null, aviso = null) } }

    private fun validar(titulo: String, contenido: String): Boolean {
        if (titulo.isBlank() || contenido.isBlank()) {
            _estado.update { it.copy(error = "Completa título y contenido.", aviso = null) }
            return false
        }
        return true
    }

    private fun mensajeError(e: Exception): String =
        "No se pudo conectar con la API: ${e.localizedMessage ?: "Error desconocido"}. Revisa Internet e inténtalo otra vez."
}
