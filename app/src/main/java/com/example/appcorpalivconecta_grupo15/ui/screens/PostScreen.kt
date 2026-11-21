package com.example.appcorpalivconecta_grupo15.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appcorpalivconecta_grupo15.data.model.Post
import com.example.appcorpalivconecta_grupo15.viewmodel.PostViewModel

/** Guía 14: Consume GET, POST, PUT y DELETE con Retrofit y MVVM. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(viewModel: PostViewModel = viewModel()) {
    val estado by viewModel.estado.collectAsState()
    var editor by remember { mutableStateOf(false) }
    var seleccionado by remember { mutableStateOf<Post?>(null) }
    var confirmarBorrado by remember { mutableStateOf<Post?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Publicaciones API", style = MaterialTheme.typography.titleLarge)
                Text("JSONPlaceholder · demostración REST", style = MaterialTheme.typography.labelMedium)
            }
            Row {
                IconButton(onClick = viewModel::cargar, enabled = !estado.cargando && !estado.operando) {
                    Icon(Icons.Default.Refresh, contentDescription = "Recargar publicaciones")
                }
                IconButton(onClick = { seleccionado = null; editor = true }, enabled = !estado.operando) {
                    Icon(Icons.Default.Add, contentDescription = "Crear publicación")
                }
            }
        }
        Text(
            "GET consulta los datos reales. POST, PUT y DELETE se simulan en JSONPlaceholder; " +
                "los cambios se ven temporalmente en la app y desaparecen al recargar.",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            style = MaterialTheme.typography.bodySmall
        )
        if (estado.cargando || estado.operando) LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        estado.error?.let {
            Text(it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(16.dp))
            TextButton(onClick = viewModel::limpiarMensajes, modifier = Modifier.padding(horizontal = 8.dp)) { Text("Cerrar aviso") }
        }
        estado.aviso?.let {
            Text(it, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
        }
        if (!estado.cargando && estado.posts.isEmpty()) {
            Text("No hay publicaciones. Pulsa recargar para intentar de nuevo.", modifier = Modifier.padding(16.dp))
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(estado.posts, key = { "post-${it.id}" }) { post ->
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Publicación #${post.id}", style = MaterialTheme.typography.labelMedium)
                        Spacer(Modifier.height(6.dp))
                        Text(post.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(6.dp))
                        Text(post.body, style = MaterialTheme.typography.bodyMedium)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            TextButton(
                                onClick = { seleccionado = post; editor = true },
                                enabled = !estado.operando && post.id <= 100
                            ) {
                                Icon(Icons.Default.Edit, contentDescription = null)
                                Spacer(Modifier.width(4.dp))
                                Text("Editar")
                            }
                            TextButton(onClick = { confirmarBorrado = post }, enabled = !estado.operando) {
                                Icon(Icons.Default.Delete, contentDescription = null)
                                Spacer(Modifier.width(4.dp))
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }

    if (editor) {
        EditorPostDialog(
            actual = seleccionado,
            bloqueado = estado.operando,
            onDismiss = { editor = false },
            onGuardar = { titulo, contenido ->
                seleccionado?.let { viewModel.editar(it, titulo, contenido) }
                    ?: viewModel.crear(titulo, contenido)
                editor = false
            }
        )
    }
    confirmarBorrado?.let { post ->
        AlertDialog(
            onDismissRequest = { confirmarBorrado = null },
            title = { Text("Eliminar publicación") },
            text = { Text("¿Eliminar la publicación #${post.id} de la lista? JSONPlaceholder no guardará este cambio.") },
            confirmButton = {
                TextButton(onClick = { viewModel.eliminar(post); confirmarBorrado = null }) { Text("Eliminar") }
            },
            dismissButton = {
                TextButton(onClick = { confirmarBorrado = null }) { Text("Cancelar") }
            }
        )
    }
}

@Composable
private fun EditorPostDialog(
    actual: Post?,
    bloqueado: Boolean,
    onDismiss: () -> Unit,
    onGuardar: (String, String) -> Unit
) {
    var titulo by remember(actual) { mutableStateOf(actual?.title.orEmpty()) }
    var cuerpo by remember(actual) { mutableStateOf(actual?.body.orEmpty()) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (actual == null) "Crear publicación (POST)" else "Editar publicación (PUT)") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = titulo, onValueChange = { titulo = it },
                    label = { Text("Título") }, singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = cuerpo, onValueChange = { cuerpo = it },
                    label = { Text("Contenido") }, minLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onGuardar(titulo, cuerpo) },
                enabled = !bloqueado && titulo.isNotBlank() && cuerpo.isNotBlank()
            ) { Text("Guardar") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}
