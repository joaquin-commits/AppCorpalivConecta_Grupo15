package com.example.appcorpalivconecta_grupo15

import androidx.activity.ComponentActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.appcorpalivconecta_grupo15.ui.screens.SectionCard
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/** Verifica que ambas tarjetas disparan efectivamente su acción de navegación. */
class SectionCardClickTest {
    @get:Rule val rule = createAndroidComposeRule<ComponentActivity>()

    @Test fun tarjetaUfEjecutaNavegacion() {
        var destino = ""
        rule.setContent {
            SectionCard("Calcular aporte en UF", "Consulta UF", Icons.Default.Calculate) {
                destino = "aportes-uf"
            }
        }
        rule.onNodeWithText("Calcular aporte en UF").performClick()
        rule.runOnIdle { assertEquals("aportes-uf", destino) }
    }

    @Test fun tarjetaSedeEjecutaNavegacion() {
        var destino = ""
        rule.setContent {
            SectionCard("Encuentra Alba Lab", "Consulta sede", Icons.Default.Place) {
                destino = "sede-albalab"
            }
        }
        rule.onNodeWithText("Encuentra Alba Lab").performClick()
        rule.runOnIdle { assertEquals("sede-albalab", destino) }
    }
}
