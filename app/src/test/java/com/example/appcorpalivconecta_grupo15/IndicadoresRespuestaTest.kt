package com.example.appcorpalivconecta_grupo15

import com.example.appcorpalivconecta_grupo15.data.remote.IndicadoresResumen
import com.example.appcorpalivconecta_grupo15.data.remote.UfRespuesta
import com.example.appcorpalivconecta_grupo15.data.remote.UfIndicadoresCl
import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Test

class IndicadoresRespuestaTest {
    @Test fun interpretaApiPrincipalIndicadoresCl() {
        val json = """{"indicador":"uf","fecha":"2026-11-24","valor":41398.57}"""
        val respuesta = Gson().fromJson(json, UfIndicadoresCl::class.java)
        assertEquals(41398.57, respuesta.valor, 0.001)
        assertEquals("2026-11-24", respuesta.fecha)
    }

    @Test fun obtieneUfDelResumenApi() {
        val json = """{"uf":{"valor":41398.57,"fecha":"2026-11-24T03:00:00.000Z"}}"""
        val respuesta = Gson().fromJson(json, IndicadoresResumen::class.java)
        assertEquals(41398.57, respuesta.uf!!.valor, 0.001)
    }

    @Test fun interpretaSerieAlternativa() {
        val json = """{"serie":[{"valor":41398.57,"fecha":"2026-11-24T03:00:00.000Z"}]}"""
        val respuesta = Gson().fromJson(json, UfRespuesta::class.java)
        assertEquals(1, respuesta.serie.size)
    }
}
