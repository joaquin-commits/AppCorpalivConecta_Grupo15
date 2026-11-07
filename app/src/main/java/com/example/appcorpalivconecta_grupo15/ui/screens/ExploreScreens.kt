package com.example.appcorpalivconecta_grupo15.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.appcorpalivconecta_grupo15.model.ContenidoCorpaliv
import com.example.appcorpalivconecta_grupo15.model.Ficha
import com.example.appcorpalivconecta_grupo15.model.TipoAccion

private val azul = Color(0xFF0865AC)
private val turquesa = Color(0xFF079A91)
private val naranja = Color(0xFFFFA614)

private fun icono(f: Ficha): ImageVector = when (f.id) {
    "escuela" -> Icons.Default.School
    "taller" -> Icons.Default.Construction
    "intermediacion" -> Icons.Default.Work
    "servicios", "capacitaciones" -> Icons.Default.Groups
    "alianzas" -> Icons.Default.Handshake
    "bolsa" -> Icons.Default.ShoppingBag
    "accesibilidad" -> Icons.Default.AccessibilityNew
    "guatero" -> Icons.Default.Spa
    else -> Icons.Default.CardGiftcard
}

/** Marcador honesto: no se insertan fotos de terceros sin autorización verificable. */
@Composable
fun RecursoVisual(ficha: Ficha, modifier: Modifier = Modifier, compacto: Boolean = false) {
    val c = when {
        ficha.id == "escuela" -> azul
        ficha.categoria.contains("Producto") -> turquesa
        ficha.categoria.contains("Empresas") || ficha.id == "alianzas" -> naranja
        else -> MaterialTheme.colorScheme.primary
    }
    Surface(modifier = modifier, color = c.copy(alpha = 0.11f), shape = RoundedCornerShape(18.dp)) {
        Column(Modifier.fillMaxWidth().padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Icon(icono(ficha), contentDescription = null, tint = c,
                modifier = Modifier.size(if (compacto) 38.dp else 65.dp))
            if (!compacto) {
                Spacer(Modifier.height(7.dp))
                Text("Imagen institucional pendiente de autorización", style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun Encabezado(titulo: String, texto: String) {
    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(titulo, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary)
        Text(texto, style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun FichaCard(ficha: Ficha, abrir: () -> Unit) {
    ElevatedCard(onClick = abrir, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp)) {
        Row(Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            RecursoVisual(ficha, Modifier.size(width = 91.dp, height = 93.dp), compacto = true)
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(ficha.categoria, style = MaterialTheme.typography.labelSmall, color = turquesa)
                Text(ficha.titulo, fontWeight = FontWeight.Bold, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Text(ficha.subtitulo, style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 2)
                if (ficha.precioReferencia.isNotBlank()) {
                    Text("Referencia web: ${ficha.precioReferencia}", style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary)
                }
                Text("Conocer más", style = MaterialTheme.typography.labelLarge, color = azul)
            }
            Icon(Icons.Default.ChevronRight, contentDescription = "Abrir detalle de ${ficha.titulo}")
        }
    }
}

@Composable
fun ProgramasScreen(abrir: (String) -> Unit) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item { Encabezado("Programas e iniciativas", "Conoce las áreas de educación, formación e inclusión de CORPALIV y Alba Lab.") }
        items(ContenidoCorpaliv.programas, key = { it.id }) { ficha -> FichaCard(ficha) { abrir(ficha.id) } }
        item { AvisoReferencia() }
    }
}

@Composable
fun CatalogoScreen(abrir: (String) -> Unit) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item { Encabezado("Catálogo Alba Lab", "Productos y servicios publicados por Alba Lab. Los precios son referencias de su sitio web.") }
        item {
            Surface(color = MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(16.dp)) {
                Row(Modifier.padding(14.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Icon(Icons.Default.Info, contentDescription = null)
                    Text("Las consultas se guardan solo en tu dispositivo. Para comprar, utiliza la tienda oficial de Alba Lab.",
                        style = MaterialTheme.typography.bodySmall)
                }
            }
        }
        items(ContenidoCorpaliv.catalogo, key = { it.id }) { ficha -> FichaCard(ficha) { abrir(ficha.id) } }
    }
}

@Composable
fun AvisoReferencia() {
    Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(14.dp)) {
        Row(Modifier.fillMaxWidth().padding(14.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Icon(Icons.Default.Verified, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Text("Información basada en los sitios oficiales. Servicios, precios y cupos pueden cambiar.",
                style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun DetalleSocial(
    grupo: String,
    id: String,
    volver: () -> Unit,
    accionFormulario: (Ficha) -> Unit,
    abrirEnlace: (String) -> Unit
) {
    val ficha = ContenidoCorpaliv.buscar(grupo, id)
    if (ficha == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("No se encontró este contenido")
                TextButton(onClick = volver) { Text("Volver") }
            }
        }
        return
    }
    var visible by remember(ficha.id) { mutableStateOf(false) }
    LaunchedEffect(ficha.id) { visible = true }
    LazyColumn(contentPadding = PaddingValues(18.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            TextButton(onClick = volver, modifier = Modifier.heightIn(min = 48.dp)) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
                Spacer(Modifier.width(7.dp))
                Text("Volver a ${if (grupo == "programa") "programas" else "catálogo"}")
            }
        }
        item { RecursoVisual(ficha, Modifier.fillMaxWidth().height(175.dp)) }
        item {
            AnimatedVisibility(visible, enter = fadeIn() + slideInVertically(initialOffsetY = { it / 5 })) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(ficha.categoria.uppercase(), color = turquesa, style = MaterialTheme.typography.labelLarge)
                    Text(ficha.titulo, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineMedium)
                    Text(ficha.subtitulo, style = MaterialTheme.typography.titleMedium)
                    Text(ficha.descripcion, style = MaterialTheme.typography.bodyLarge)
                    if (ficha.precioReferencia.isNotBlank()) {
                        Text("Precio de referencia publicado: ${ficha.precioReferencia}", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
        item {
            Button(onClick = {
                if (ficha.tipoAccion == TipoAccion.ENLACE) abrirEnlace(ficha.enlace)
                else accionFormulario(ficha)
            }, modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp)) {
                Icon(if (ficha.tipoAccion == TipoAccion.ENLACE) Icons.Default.OpenInNew else Icons.Default.Email,
                    contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(ficha.textoAccion)
            }
        }
        item {
            TextButton(onClick = { abrirEnlace(ficha.enlace) }, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.Language, contentDescription = null)
                Spacer(Modifier.width(6.dp))
                Text("Consultar fuente oficial")
            }
        }
        item { AvisoReferencia() }
    }
}
