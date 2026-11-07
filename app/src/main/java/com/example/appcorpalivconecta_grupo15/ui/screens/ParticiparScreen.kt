package com.example.appcorpalivconecta_grupo15.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.appcorpalivconecta_grupo15.model.ContenidoCorpaliv

/** Opciones claras: la colaboración local no se confunde con donación ni postulación real. */
@Composable
fun ParticiparScreen(abrir: (String) -> Unit, web: (String) -> Unit) {
    LazyColumn(contentPadding = PaddingValues(18.dp), verticalArrangement = Arrangement.spacedBy(13.dp)) {
        item { Text("Sé parte del cambio", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold) }
        item { Text("Tu interés puede transformarse en oportunidades para la inclusión. Elige una forma de participar.") }
        item { SectionCard("Conocer programas", "Educación, formación y empleo inclusivo.", Icons.Default.School) { abrir("programas") } }
        item { SectionCard("Productos y servicios Alba Lab", "Consulta el catálogo de Alba Lab.", Icons.Default.Storefront) { abrir("catalogo") } }
        item { SectionCard("Enviar interés de colaboración", "Formulario local de demostración académica.", Icons.Default.Handshake) { abrir("colaborar") } }
        item { SectionCard("Donar en plataforma oficial", "Continuarás en una página web externa segura de la organización.", Icons.Default.Favorite) { web(ContenidoCorpaliv.donaciones) } }
        item { SectionCard("Contactar con CORPALIV", "Escribir al correo institucional publicado en su sitio.", Icons.Default.Email) { web(ContenidoCorpaliv.contacto) } }
        item { Text("Importante: esta aplicación académica no recibe dinero, no formaliza inscripciones y no transmite tus formularios a CORPALIV.",
            style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant) }
    }
}
