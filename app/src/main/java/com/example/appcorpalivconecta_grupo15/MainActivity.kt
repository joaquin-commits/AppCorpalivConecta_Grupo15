
package com.example.appcorpalivconecta_grupo15

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

// Importamos nuestra pantalla HomeScreen
import com.example.appcorpalivconecta_grupo15.ui.theme.HomeScreen

// Importamos el tema visual de la aplicación
import com.example.appcorpalivconecta_grupo15.ui.theme.AppCorpalivConecta_Grupo15Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Establecemos la interfaz de la aplicación
        setContent {

            // Aplicamos el tema visual
            AppCorpalivConecta_Grupo15Theme {

                // Mostramos nuestra pantalla principal
                HomeScreen()
            }
        }
    }
}
