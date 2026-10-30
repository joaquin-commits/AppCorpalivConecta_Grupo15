
package com.example.appcorpalivconecta_grupo15

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

// Sistema de navegación de la Guía 10
import com.example.appcorpalivconecta_grupo15.navigation.AppNavigation

// Tema visual de CORPALIV Conecta
import com.example.appcorpalivconecta_grupo15.ui.theme.AppCorpalivConecta_Grupo15Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            AppCorpalivConecta_Grupo15Theme {

                // Navegación principal de la aplicación
                AppNavigation()

            }
        }
    }
}
