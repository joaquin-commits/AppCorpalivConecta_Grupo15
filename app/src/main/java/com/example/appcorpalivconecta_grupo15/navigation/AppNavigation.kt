package com.example.appcorpalivconecta_grupo15.navigation

// Funciones de Jetpack Compose
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState


// Componentes visuales de Material 3
import androidx.compose.material3.*

// Íconos para los menús
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Menu

// Herramientas para organizar la interfaz
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Navegación entre pantallas
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

// ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

// Pantallas de CORPALIV Conecta
import com.example.appcorpalivconecta_grupo15.ui.screens.HomeScreen2
import com.example.appcorpalivconecta_grupo15.ui.screens.SettingsScreen
import com.example.appcorpalivconecta_grupo15.ui.screens.DetailScreen

// ViewModel de navegación
import com.example.appcorpalivconecta_grupo15.viewmodel.NavigationViewModel



import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch






@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    navigationViewModel: NavigationViewModel = viewModel()
) {
    // Controlador principal
    val navController = rememberNavController()

    // Pantalla actualmente seleccionada
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // Estado del menú lateral
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    // Permite abrir y cerrar el menú
    val scope = rememberCoroutineScope()

    // Procesamos los eventos del ViewModel
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

    // Menú lateral de la aplicación
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {

                Text(
                    text = "CORPALIV Conecta",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(24.dp)
                )

                HorizontalDivider()

                NavigationDrawerItem(
                    label = { Text("Inicio") },
                    selected = currentRoute == Screen.Home.route,
                    onClick = {
                        navigationViewModel.navigateTo(Screen.Home.route)
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    icon = {
                        Icon(Icons.Default.Home, contentDescription = null)
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Configuración") },
                    selected = currentRoute == Screen.Settings.route,
                    onClick = {
                        navigationViewModel.navigateTo(Screen.Settings.route)
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    icon = {
                        Icon(Icons.Default.Settings, contentDescription = null)
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Detalle") },
                    selected = currentRoute?.startsWith("detail_page/") == true,
                    onClick = {
                        navigationViewModel.navigateTo(
                            Screen.Detail.createRoute("15")
                        )
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    icon = {
                        Icon(Icons.Default.Menu, contentDescription = null)
                    }
                )
            }
        }
    ) {

        // Estructura principal
        Scaffold(
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
                                contentDescription = "Abrir menú"
                            )
                        }
                    }
                )
            },
            bottomBar = {
                AppBottomBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navigationViewModel.navigateTo(route)
                    }
                )
            }
        ) { innerPadding ->

            // Contenedor de las pantallas
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {

                composable(Screen.Home.route) {
                    HomeScreen2()
                }

                composable(Screen.Settings.route) {
                    SettingsScreen()
                }

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
            }
        }
    }
}























@Composable
fun AppBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    NavigationBar {

        // Opción Inicio
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
            label = { Text("Inicio") }
        )

        // Opción Configuración
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
            label = { Text("Configuración") }
        )

        // Opción Detalle
        NavigationBarItem(
            selected = currentRoute?.startsWith("detail_page/") == true,
            onClick = {
                onNavigate(Screen.Detail.createRoute("15"))
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Detalle"
                )
            },
            label = { Text("Detalle") }
        )
    }
}

