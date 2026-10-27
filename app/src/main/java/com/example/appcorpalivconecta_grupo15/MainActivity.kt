
package com.example.appcorpalivconecta_grupo15

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

// Importamos la pantalla adaptable
import com.example.appcorpalivconecta_grupo15.ui.screens.HomeScreen2

// Importamos el tema visual de CORPALIV Conecta
import com.example.appcorpalivconecta_grupo15.ui.theme.AppCorpalivConecta_Grupo15Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Establecemos la interfaz con Jetpack Compose
        setContent {

            // Aplicamos el tema visual
            AppCorpalivConecta_Grupo15Theme {

                // Selecciona la pantalla según el tamaño
                HomeScreen2()
            }
        }
    }
}
