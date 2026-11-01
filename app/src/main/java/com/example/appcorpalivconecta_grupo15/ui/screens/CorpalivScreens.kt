package com.example.appcorpalivconecta_grupo15.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import com.example.appcorpalivconecta_grupo15.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.appcorpalivconecta_grupo15.viewmodel.ColaboracionViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

private val opciones=listOf("Solicitar información", "Información de programa", "Consulta de producto", "Consulta de servicio", "Donación", "Voluntariado", "Alianza empresarial", "Capacitación")
private val programas=listOf(Triple("Inclusión laboral","Preparación y acompañamiento para el empleo",Icons.Default.Work),Triple("Formación","Talleres y desarrollo de habilidades",Icons.Default.School),Triple("Alianzas y RSE","Vinculación entre organizaciones y empresas",Icons.Default.Handshake))
private val catalogo=listOf("Productos de elaboración colaborativa" to "Consultar disponibilidad y precios", "Servicios de Alba Lab" to "Solicitar información sobre servicios", "Iniciativas con impacto social" to "Explorar oportunidades de colaboración")

@Composable fun SectionCard(title:String,subtitle:String,icon:androidx.compose.ui.graphics.vector.ImageVector, onClick:(()->Unit)?=null){
 ElevatedCard(onClick={onClick?:{}},enabled=onClick!=null,modifier=Modifier.fillMaxWidth().padding(vertical=5.dp),shape=RoundedCornerShape(18.dp)) {
 Row(Modifier.padding(18.dp),verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(14.dp)){
 Icon(icon,contentDescription=null,tint=MaterialTheme.colorScheme.secondary,modifier=Modifier.size(32.dp));Column(Modifier.weight(1f)){Text(title,fontWeight=FontWeight.SemiBold);Text(subtitle,style=MaterialTheme.typography.bodySmall,color=Color.DarkGray)}
 if(onClick!=null) Icon(Icons.Default.ChevronRight,contentDescription=null)
 }
 }}
@Composable
fun CorpalivHome(go: (String) -> Unit, abrirWeb: (String) -> Unit) {
    BoxWithConstraints(Modifier.fillMaxSize()) {
    val margen = if (maxWidth >= 600.dp) 28.dp else 16.dp
    LazyColumn(modifier = Modifier.fillMaxWidth().widthIn(max = 780.dp).align(Alignment.TopCenter),
        contentPadding = PaddingValues(margen), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Image(painterResource(R.drawable.corpaliv_logo), contentDescription = "Logo CORPALIV Conecta",
                modifier = Modifier.fillMaxWidth().height(142.dp),
                contentScale = androidx.compose.ui.layout.ContentScale.Fit)
        }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(26.dp)) {
                Column(Modifier.fillMaxWidth().padding(22.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("CONSTRUYAMOS JUNTOS", style = MaterialTheme.typography.labelLarge,
                        color = Color(0xFFFFBC4B), fontWeight = FontWeight.Bold)
                    Text("Un país sin barreras", style = MaterialTheme.typography.headlineMedium,
                        color = Color.White, fontWeight = FontWeight.Bold)
                    Text("CORPALIV acompaña a personas con discapacidad múltiple y a sus familias a lo largo de la vida. Conoce su escuela, Alba Lab y las oportunidades para sumarte.",
                        style = MaterialTheme.typography.bodyLarge, color = Color.White)
                    Button(onClick = { go("participar") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.heightIn(min = 48.dp)) {
                        Icon(Icons.Default.VolunteerActivism, contentDescription = null)
                        Spacer(Modifier.width(8.dp)); Text("Quiero participar")
                    }
                }
            }
        }
        item {
            Text("Conoce CORPALIV", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(5.dp))
            Text("Una corporación sin fines de lucro fundada en 1993, con más de 30 años de historia.",
                style = MaterialTheme.typography.bodyMedium)
        }
        item { SectionCard("Nuestra misión", "Acompañar a niños, niñas, jóvenes y adultos, respetando sus necesidades y diferencias.", Icons.Default.Favorite) {
            abrirWeb("https://corpaliv.cl/quienes-somos/")
        } }
        item { Text("¿Qué hacemos?", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold) }
        item { PresentacionArea("Escuela Jan Van Dijk", "Educación especializada y acompañamiento a las familias.",
            Icons.Default.School, Color(0xFF0865AC)) { go("detalle/programa/escuela") } }
        item { PresentacionArea("Alba Lab", "Inclusión sociolaboral, autonomía y experiencias de formación para jóvenes y adultos.",
            Icons.Default.Work, Color(0xFF079A91)) { go("detalle/programa/taller") } }
        item { PresentacionArea("Programas de inclusión", "Descubre educación, formación, vinculación laboral y alianzas con empresas.",
            Icons.Default.Groups, Color(0xFFE58D00)) { go("programas") } }
        item { Text("Nuestro impacto", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold) }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                CajaImpacto("1993", "Año de fundación", Modifier.weight(1f))
                CajaImpacto("2003", "Inicio de la escuela", Modifier.weight(1f))
            }
        }
        item {
            Surface(color = MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(18.dp)) {
                Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Inclusión en cada etapa", fontWeight = FontWeight.Bold)
                    Text("Educación especializada · Formación para el empleo · Apoyo a familias · Alianzas inclusivas")
                    Text("Datos históricos publicados por CORPALIV; no son indicadores de resultados actuales.",
                        style = MaterialTheme.typography.labelSmall)
                }
            }
        }
        item { Text("¿Cómo puedes participar?", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold) }
        item { SectionCard("Explorar programas", "Conoce iniciativas de la escuela y Alba Lab.", Icons.Default.Groups) { go("programas") } }
        item { SectionCard("Apoyar a Alba Lab", "Conoce productos elaborados y servicios inclusivos.", Icons.Default.Storefront) { go("catalogo") } }
        item { SectionCard("Colaborar o crear una alianza", "Deja una solicitud de interés guardada en el teléfono.", Icons.Default.Handshake) { go("colaborar") } }
        item { SectionCard("Donar en el sitio oficial", "Abre la plataforma oficial de donaciones; no cobramos en la app.", Icons.Default.Favorite) {
            abrirWeb(com.example.appcorpalivconecta_grupo15.model.ContenidoCorpaliv.donaciones)
        } }
        item { SectionCard("Contactar a CORPALIV", "Escribe un correo utilizando la aplicación de correo del teléfono.", Icons.Default.Email) {
            abrirWeb(com.example.appcorpalivconecta_grupo15.model.ContenidoCorpaliv.contacto)
        } }
        item { Text("Noticias y actividades", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold) }
        item { Text("Publicaciones verificadas del sitio oficial. Selecciona una para leer la noticia completa.",
            style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant) }
        items(com.example.appcorpalivconecta_grupo15.model.ContenidoCorpaliv.noticias) { noticia ->
            ElevatedCard(onClick = { abrirWeb(noticia.enlace) }, shape = RoundedCornerShape(18.dp)) {
                Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
                    Text(noticia.fecha, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary)
                    Text(noticia.titulo, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    Text(noticia.resumen, style = MaterialTheme.typography.bodyMedium)
                    Text("Leer noticia en CORPALIV  ↗", style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary)
                }
            }
        }
        item { SectionCard("Mis solicitudes", "Revisa y elimina solicitudes de demostración guardadas localmente.",
            Icons.Default.History) { go("historial") } }
        item { Text("Proyecto académico. Información pública consultada el 01/11/2026. La app no gestiona pagos, postulaciones ni envía solicitudes a CORPALIV.",
            style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant) }
      }
    }
}
@Composable
private fun PresentacionArea(titulo: String, descripcion: String, icono: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color, abrir: () -> Unit) {
    ElevatedCard(onClick = abrir, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp)) {
        Row(Modifier.fillMaxWidth().padding(15.dp), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(13.dp)) {
            Surface(color = color.copy(alpha = 0.14f), shape = RoundedCornerShape(16.dp)) {
                Icon(icono, contentDescription = null, tint = color, modifier = Modifier.padding(15.dp).size(32.dp))
            }
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(titulo, fontWeight = FontWeight.Bold)
                Text(descripcion, style = MaterialTheme.typography.bodySmall)
                Text("Conocer más", style = MaterialTheme.typography.labelLarge, color = color)
            }
            Icon(Icons.Default.ChevronRight, contentDescription = "Abrir $titulo")
        }
    }
}

@Composable
private fun CajaImpacto(numero: String, descripcion: String, modifier: Modifier = Modifier) {
    Surface(modifier = modifier, shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.secondaryContainer) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
            Text(numero, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary)
            Text(descripcion, style = MaterialTheme.typography.bodySmall)
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable fun FormularioScreen(vm:ColaboracionViewModel,onSuccess:()->Unit){
 val state by vm.form.collectAsStateWithLifecycle()
 val context=LocalContext.current
 var imagen by remember { mutableStateOf<android.graphics.Bitmap?>(null) }
 val camera=rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()){bitmap->imagen=bitmap}
 fun obtenerUbicacion(){
  val locationManager=context.getSystemService(android.content.Context.LOCATION_SERVICE) as LocationManager
  try { val l=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
   vm.cambiar("ubicacion",if(l==null) "Ubicación no disponible (activa el GPS)" else "${"%.5f".format(java.util.Locale.US,l.latitude)}, ${"%.5f".format(java.util.Locale.US,l.longitude)}")
  }catch(_:SecurityException){vm.cambiar("ubicacion","Permiso de ubicación denegado")}
 }
 val permission=rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){grants->if(grants.values.any{it})obtenerUbicacion()else vm.cambiar("ubicacion","Permiso de ubicación denegado")}
 LaunchedEffect(state.enviado){if(state.enviado)onSuccess()}
 LazyColumn(contentPadding=PaddingValues(18.dp),verticalArrangement=Arrangement.spacedBy(12.dp)){
 item{Text(if(state.contexto.isBlank()) "Quiero colaborar" else "Solicitud de información",style=MaterialTheme.typography.headlineSmall,fontWeight=FontWeight.Bold)
 Text("Formulario de demostración. Tus datos se guardan solamente en este teléfono y NO se envían a CORPALIV.")}
 if(state.contexto.isNotBlank()) item {
  Surface(color=MaterialTheme.colorScheme.primaryContainer,shape=RoundedCornerShape(16.dp)) {
   Column(Modifier.fillMaxWidth().padding(14.dp)) {
    Text("Estás consultando",style=MaterialTheme.typography.labelMedium)
    Text(state.contexto,fontWeight=FontWeight.Bold)
    Text("Conservaremos esta referencia en tu solicitud local.",style=MaterialTheme.typography.bodySmall)
   }
  }
 }
 item{Campo("Nombre completo",state.nombre,state.errores["nombre"],Icons.Default.Person){vm.cambiar("nombre",it)}}
 item{Campo("Correo electrónico",state.correo,state.errores["correo"],Icons.Default.Email,KeyboardType.Email){vm.cambiar("correo",it)}}
 item{Campo("Organización (opcional)",state.organizacion,null,Icons.Default.Business){vm.cambiar("organizacion",it)}}
 item{var expanded by remember { mutableStateOf(false) }; ExposedDropdownMenuBox(expanded=expanded,onExpandedChange={expanded=it}){
 OutlinedTextField(state.tipo,{ },label={Text("Tipo de colaboración")},readOnly=true,isError=state.errores["tipo"]!=null,modifier=Modifier.menuAnchor().fillMaxWidth(),trailingIcon={ExposedDropdownMenuDefaults.TrailingIcon(expanded)})
 ExposedDropdownMenu(expanded=expanded,onDismissRequest={expanded=false}){opciones.forEach {op->DropdownMenuItem(text={Text(op)},onClick={vm.cambiar("tipo",op);expanded=false})}}
 };state.errores["tipo"]?.let{Text(it,color=MaterialTheme.colorScheme.error,style=MaterialTheme.typography.labelSmall)} }
 item{Campo("Mensaje",state.mensaje,state.errores["mensaje"],Icons.Default.Edit){vm.cambiar("mensaje",it)}}
 item{OutlinedButton(onClick={camera.launch(null)},modifier=Modifier.fillMaxWidth()){Icon(Icons.Default.CameraAlt,null);Spacer(Modifier.width(8.dp));Text(if(imagen==null)"Tomar foto (cámara)" else "Repetir fotografía")}}
 item{AnimatedVisibility(imagen!=null){imagen?.let{Image(it.asImageBitmap(),contentDescription="Foto de prueba",modifier=Modifier.fillMaxWidth().height(170.dp))}}}
 item{OutlinedButton(onClick={if(ContextCompat.checkSelfPermission(context,Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED) obtenerUbicacion() else permission.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION))},modifier=Modifier.fillMaxWidth()){Icon(Icons.Default.LocationOn,null);Spacer(Modifier.width(8.dp));Text("Compartir ubicación (opcional)")}}
 item{if(state.ubicacion.isNotBlank())Text(state.ubicacion,style=MaterialTheme.typography.bodySmall)}
 item{Button(onClick={vm.enviar()},modifier=Modifier.fillMaxWidth()){Icon(Icons.Default.Send,null);Spacer(Modifier.width(8.dp));Text("Guardar solicitud")}}
 item{Text("Usa datos ficticios para esta evaluación. La foto se muestra sin guardarse. La ubicación, si la compartes, se guarda con la solicitud local.",style=MaterialTheme.typography.labelSmall,color=Color.Gray)}
 }
}
@Composable private fun Campo(titulo:String,valor:String,error:String?,icon:androidx.compose.ui.graphics.vector.ImageVector,keyboard:KeyboardType=KeyboardType.Text,cambio:(String)->Unit){
 OutlinedTextField(value=valor,onValueChange=cambio,label={Text(titulo)},leadingIcon={Icon(icon,contentDescription=titulo)},isError=error!=null,supportingText={if(error!=null)Text(error) else Text(" ")},keyboardOptions=KeyboardOptions(keyboardType=keyboard),modifier=Modifier.fillMaxWidth(),shape=RoundedCornerShape(14.dp),maxLines=if(titulo=="Mensaje")4 else 1)
}
@Composable fun ConfirmacionScreen(vm:ColaboracionViewModel,go:(String)->Unit){
 var visible by remember { mutableStateOf(false) };LaunchedEffect(Unit){visible=true}
 Box(Modifier.fillMaxSize().padding(24.dp),contentAlignment=Alignment.Center){AnimatedVisibility(visible){Column(horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.spacedBy(16.dp)){
 Icon(Icons.Default.CheckCircle,contentDescription="Registro exitoso",tint=Color(0xFF159E77),modifier=Modifier.size(90.dp));Text("¡Gracias por tu interés!",style=MaterialTheme.typography.headlineSmall);Text("Tu solicitud quedó guardada en este dispositivo.");Button(onClick={vm.nuevo();go("historial")}){Text("Ver mis solicitudes")};TextButton(onClick={vm.nuevo();go("inicio")}){Text("Volver al inicio")}
 }}}
}
@Composable fun HistorialScreen(vm:ColaboracionViewModel,go:()->Unit){
 val registros by vm.solicitudes.collectAsStateWithLifecycle()
 var borrar by remember { mutableStateOf<Int?>(null) }
 if(borrar!=null) AlertDialog(onDismissRequest={borrar=null},title={Text("Eliminar solicitud")},text={Text("¿Deseas borrar este registro local? Esta acción no se puede deshacer.")},confirmButton={TextButton(onClick={borrar?.let(vm::eliminarSolicitud);borrar=null}){Text("Eliminar")}},dismissButton={TextButton(onClick={borrar=null}){Text("Cancelar")}})
 LazyColumn(contentPadding=PaddingValues(18.dp),verticalArrangement=Arrangement.spacedBy(12.dp)){
  item{Text("Mis solicitudes",style=MaterialTheme.typography.headlineSmall,fontWeight=FontWeight.Bold);Text("Historial guardado solo en este dispositivo")}
  if(registros.isEmpty())item{Column(verticalArrangement=Arrangement.spacedBy(12.dp),horizontalAlignment=Alignment.CenterHorizontally,modifier=Modifier.fillMaxWidth().padding(30.dp)){Icon(Icons.Default.Inventory2,contentDescription=null,modifier=Modifier.size(70.dp),tint=MaterialTheme.colorScheme.primary);Text("Todavía no tienes solicitudes");Button(onClick=go){Text("Crear solicitud")}}}
  items(registros.size){idx->val r=registros[idx]; ElevatedCard(Modifier.fillMaxWidth(),shape=RoundedCornerShape(18.dp)){Column(Modifier.padding(18.dp),verticalArrangement=Arrangement.spacedBy(6.dp)){
    Row(verticalAlignment=Alignment.CenterVertically){Text(r.tipo,fontWeight=FontWeight.Bold,modifier=Modifier.weight(1f));Icon(Icons.Default.CheckCircle,null,tint=Color(0xFF159E77))}
    Text("${r.nombre} · ${r.fecha}");if(r.contexto.isNotBlank()) Text("Consulta: ${r.contexto}",fontWeight=FontWeight.SemiBold);Text(r.correo,style=MaterialTheme.typography.bodySmall);Text(r.mensaje);if(r.organizacion.isNotBlank())Text("Organización: ${r.organizacion}",style=MaterialTheme.typography.bodySmall);if(r.ubicacion.isNotBlank())Text("Ubicación compartida: ${r.ubicacion}",style=MaterialTheme.typography.bodySmall)
    TextButton(onClick={borrar=idx},modifier=Modifier.align(Alignment.End)){Icon(Icons.Default.DeleteOutline,null);Spacer(Modifier.width(6.dp));Text("Eliminar registro")}
  }}}
  if(registros.isNotEmpty()) item {OutlinedButton(onClick=go,modifier=Modifier.fillMaxWidth()){Icon(Icons.Default.Add,null);Spacer(Modifier.width(8.dp));Text("Crear solicitud")}}
 }
}
