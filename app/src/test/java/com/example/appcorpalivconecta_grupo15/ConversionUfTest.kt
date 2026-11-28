package com.example.appcorpalivconecta_grupo15

import com.example.appcorpalivconecta_grupo15.viewmodel.convertirUfAPesos
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ConversionUfTest {
    @Test fun conversionReferencial() { assertEquals(80000.0, convertirUfAPesos(2.0, 40000.0), 0.001) }
    @Test fun cantidadCero() { assertEquals(0.0, convertirUfAPesos(0.0, 40000.0), 0.001) }
    @Test fun cantidadNegativaRechazada() { assertThrows(IllegalArgumentException::class.java) { convertirUfAPesos(-1.0, 40000.0) } }
    @Test fun valorInvalidoRechazado() { assertThrows(IllegalArgumentException::class.java) { convertirUfAPesos(1.0, 0.0) } }
}
