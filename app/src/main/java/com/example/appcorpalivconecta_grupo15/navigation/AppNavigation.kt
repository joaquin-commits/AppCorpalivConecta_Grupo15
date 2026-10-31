
package com.example.appcorpalivconecta_grupo15.navigation

// Funciones de Jetpack Compose
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope

// Componentes visuales de Material Design 3
import androidx.compose.material3.*

// Iconos para los menus
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Menu

// Organizacion de la interfaz
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Navegacion entre pantallas
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument

// ViewModel de Jetpack
import androidx.lifecycle.viewmodel.compose.viewModel

// Corrutinas para abrir y cerrar el menu
import kotlinx.coroutines.launch

// Pantallas de las Guias 9 y 10
import com.example.appcorpalivconecta_grupo15.ui.screens.HomeScreen2
import com.example.appcorpalivconecta_grupo15.ui.screens.SettingsScreen
import com.example.appcorpalivconecta_grupo15.ui.screens.DetailScreen

// Pantallas de la Guia 11
import com.example.appcorpalivconecta_grupo15.ui.screens.RegistroScreen
import com.example.appcorpalivconecta_grupo15.ui.screens.ResumenScreen

// ViewModels de la aplicacion
import com.example.appcorpalivconecta_grupo15.viewmodel.NavigationViewModel
import com.example.appcorpalivconecta_grupo15.viewmodel.UsuarioViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    navigationViewModel: NavigationViewModel = viewModel()
) {

    // Controlador principal de navegacion
    val navController = rememberNavController()

    // Guia 11: ViewModel compartido entre Registro y Resumen
    val usuarioViewModel: UsuarioViewModel = viewModel()

    // Observamos la pantalla actual
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // Estado del menu lateral
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    // Corrutinas para controlar el menu
    val scope = rememberCoroutineScope()

    // Escuchamos los eventos de navegacion
    LaunchedEffect(navController, navigationViewModel) {

        navigationViewModel.navigationEvents.collect { event ->

            when (event) {

                is NavigationEvent.NavigateTo -> {
                    navController.navigate(event.route) {
                        launchSingleTop = true
                    }
                }

                NavigationEvent.PopBackStack -> {
                    navController.popBackStack()
                }
            }
        }
    }

    // Menu lateral principal
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {

            ModalDrawerSheet {

                // Encabezado del menu
                Text(
                    text = "CORPALIV Conecta",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(24.dp)
                )

                HorizontalDivider()

                // Opcion Inicio
                NavigationDrawerItem(
                    label = {
                        Text("Inicio")
                    },
                    selected = currentRoute == Screen.Home.route,
                    onClick = {
                        navigationViewModel.navigateTo(
                            Screen.Home.route
                        )

                        scope.launch {
                            drawerState.close()
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = null
                        )
                    }
                )

                // Opcion Configuracion
                NavigationDrawerItem(
                    label = {
                        Text("Configuración")
                    },
                    selected = currentRoute == Screen.Settings.route,
                    onClick = {
                        navigationViewModel.navigateTo(
                            Screen.Settings.route
                        )

                        scope.launch {
                            drawerState.close()
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null
                        )
                    }
                )

                // Opcion Detalle
                NavigationDrawerItem(
                    label = {
                        Text("Detalle")
                    },
                    selected = currentRoute == Screen.Detail.route,
                    onClick = {
                        navigationViewModel.navigateTo(
                            Screen.Detail.createRoute("15")
                        )

                        scope.launch {
                            drawerState.close()
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = null
                        )
                    }
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // Guia 11: Acceso al formulario de registro
                NavigationDrawerItem(
                    label = {
                        Text("Registro de usuario")
                    },
                    selected = currentRoute == Screen.Registro.route,
                    onClick = {
                        navigationViewModel.navigateTo(
                            Screen.Registro.route
                        )

                        scope.launch {
                            drawerState.close()
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = null
                        )
                    }
                )
            }
        }
    ) {

        // Estructura principal de CORPALIV Conecta
        Scaffold(

            // Barra superior global
            topBar = {
                TopAppBar(
                    title = {
                        Text("CORPALIV Conecta")
                    },

                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menú lateral"
                            )
                        }
                    }
                )
            },

            // Barra inferior de la Guia 10
            bottomBar = {
                AppBottomBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navigationViewModel.navigateTo(route)
                    }
                )
            }

        ) { innerPadding ->

            // Contenedor de todas las pantallas
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {

                // Pantalla principal adaptable
                composable(Screen.Home.route) {
                    HomeScreen2()
                }

                // Pantalla de configuracion
                composable(Screen.Settings.route) {
                    SettingsScreen()
                }

                // Pantalla de detalle con identificador
                composable(
                    route = Screen.Detail.route,
                    arguments = listOf(
                        navArgument("itemId") {
                            type = NavType.StringType
                        }
                    )
                ) { entry ->

                    val itemId =
                        entry.arguments?.getString("itemId") ?: ""

                    DetailScreen(
                        itemId = itemId,
                        onBack = {
                            navigationViewModel.popBackStack()
                        }
                    )
                }

                // Guia 11: Formulario de registro
                composable(Screen.Registro.route) {

                    RegistroScreen(
                        usuarioViewModel = usuarioViewModel,
                        onRegistroExitoso = {
                            navigationViewModel.navigateTo(
                                Screen.Resumen.route
                            )
                        }
                    )
                }

                // Guia 11: Pantalla de resumen
                composable(Screen.Resumen.route) {

                    ResumenScreen(
                        usuarioViewModel = usuarioViewModel,
                        onVolver = {
                            navigationViewModel.popBackStack()
                        }
                    )
                }
            }
        }
    }
}


// Barra inferior de navegacion
@Composable
fun AppBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {

    NavigationBar {

        // Inicio
        NavigationBarItem(
            selected = currentRoute == Screen.Home.route,
            onClick = {
                onNavigate(Screen.Home.route)
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Inicio"
                )
            },
            label = {
                Text("Inicio")
            }
        )

        // Configuracion
        NavigationBarItem(
            selected = currentRoute == Screen.Settings.route,
            onClick = {
                onNavigate(Screen.Settings.route)
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Configuración"
                )
            },
            label = {
                Text("Configuración")
            }
        )

        // Detalle
        NavigationBarItem(
            selected = currentRoute == Screen.Detail.route,
            onClick = {
                onNavigate(
                    Screen.Detail.createRoute("15")
                )
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Detalle"
                )
            },
            label = {
                Text("Detalle")
            }
        )
    }
}
