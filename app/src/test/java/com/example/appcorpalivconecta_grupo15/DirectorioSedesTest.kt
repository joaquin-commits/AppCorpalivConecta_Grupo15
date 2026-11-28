package com.example.appcorpalivconecta_grupo15

import com.example.appcorpalivconecta_grupo15.data.remote.DirectorioCorpaliv
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DirectorioSedesTest {
    @Test fun `directorio tiene dos sedes institucionales y cuatro recursos externos`() {
        val lugares = DirectorioCorpaliv.lugares
        assertEquals(2, lugares.count { it.esSedeCorpaliv })
        assertEquals(4, lugares.count { !it.esSedeCorpaliv })
    }

    @Test fun `identificadores unicos y direcciones informadas`() {
        val lugares = DirectorioCorpaliv.lugares
        assertEquals(lugares.size, lugares.map { it.id }.distinct().size)
        assertTrue(lugares.all { it.direccion.isNotBlank() && it.referencia.startsWith("https://") })
    }
}
