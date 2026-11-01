package com.example.appcorpalivconecta_grupo15.navigation

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.appcorpalivconecta_grupo15.model.Ficha
import com.example.appcorpalivconecta_grupo15.ui.screens.*
import com.example.appcorpalivconecta_grupo15.viewmodel.ColaboracionViewModel

/** Abre páginas verificadas o el cliente de correo. Las postulaciones y donaciones
 * se realizan únicamente en plataformas institucionales externas, nunca en la app. */
private fun abrirEnSistema(context: android.content.Context, destino: String) {
    try {
        val uri = Uri.parse(destino)
        val intent = if (uri.scheme == "mailto") Intent(Intent.ACTION_SENDTO, uri)
                     else Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    } catch (_: ActivityNotFoundException) {
        Toast.makeText(context, "No se encontró una aplicación para abrir el enlace", Toast.LENGTH_LONG).show()
    } catch (_: SecurityException) {
        Toast.makeText(context, "El dispositivo bloqueó el acceso al enlace", Toast.LENGTH_LONG).show()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(vm: ColaboracionViewModel = viewModel()) {
    val nav = rememberNavController()
    val context = LocalContext.current
    val entry by nav.currentBackStackEntryAsState()
    val route = entry?.destination?.route ?: "inicio"
    val pestañas = listOf("inicio", "programas", "catalogo", "historial")
    val mostrarBarra = route in pestañas

    fun ir(ruta: String) {
        // Una colaboración genérica no debe arrastrar el contexto de otra consulta.
        if (ruta == "colaborar" && route != "detalle/{grupo}/{id}") vm.nuevo()
        if (ruta in pestañas) {
            nav.navigate(ruta) {
                launchSingleTop = true
                popUpTo("inicio") { saveState = true }
                restoreState = true
            }
        } else nav.navigate(ruta) { launchSingleTop = true }
    }
    fun volver() { if (!nav.popBackStack()) ir("inicio") }
    fun consulta(f: Ficha) {
        vm.prepararConsulta(f.titulo, f.tipoFormulario)
        ir("colaborar")
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("CORPALIV Conecta", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    if (!mostrarBarra) IconButton(onClick = ::volver) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver a la pantalla anterior")
                    }
                }
            )
        },
        bottomBar = {
            if (mostrarBarra) NavigationBar {
                listOf(
                    Triple("inicio", "Inicio", Icons.Default.Home),
                    Triple("programas", "Programas", Icons.Default.Groups),
                    Triple("catalogo", "Catálogo", Icons.Default.Store),
                    Triple("historial", "Solicitudes", Icons.Default.History)
                ).forEach { (r, label, icono) ->
                    NavigationBarItem(
                        selected = route == r,
                        onClick = { ir(r) },
                        icon = { Icon(icono, contentDescription = label) },
                        label = { Text(label) }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(navController = nav, startDestination = "inicio", modifier = Modifier.padding(padding)) {
            composable("inicio") { CorpalivHome(::ir) { url -> abrirEnSistema(context, url) } }
            composable("programas") { ProgramasScreen { id -> ir("detalle/programa/$id") } }
            composable("catalogo") { CatalogoScreen { id -> ir("detalle/catalogo/$id") } }
            composable("detalle/{grupo}/{id}") { back ->
                DetalleSocial(
                    grupo = back.arguments?.getString("grupo") ?: "programa",
                    id = back.arguments?.getString("id") ?: "",
                    volver = ::volver,
                    accionFormulario = ::consulta,
                    abrirEnlace = { abrirEnSistema(context, it) }
                )
            }
            composable("participar") {
                ParticiparScreen(
                    abrir = ::ir,
                    web = { abrirEnSistema(context, it) }
                )
            }
            composable("colaborar") { FormularioScreen(vm) { ir("confirmacion") } }
            composable("confirmacion") { ConfirmacionScreen(vm, ::ir) }
            composable("historial") { HistorialScreen(vm) {
                vm.nuevo()
                ir("colaborar")
            } }
        }
    }
}
