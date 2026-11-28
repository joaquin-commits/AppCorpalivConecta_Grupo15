package com.example.appcorpalivconecta_grupo15

import com.example.appcorpalivconecta_grupo15.data.remote.*
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.HttpException
import java.util.concurrent.TimeUnit

class ApisConfiguracionTest {
    private fun retrofit(server: MockWebServer) = Retrofit.Builder()
        .baseUrl(server.url("/"))
        .addConverterFactory(GsonConverterFactory.create()).build()

    @Test fun ufPrincipalConfigurada() {
        val api = ApisInstitucionales.ufPrincipal
        assertTrue(UfIndicadoresClApi::class.java.isInstance(api))
        assertSame(api, ApisInstitucionales.ufPrincipal)
    }
    @Test fun ufRespaldoConfigurada() {
        val api = ApisInstitucionales.uf
        assertTrue(IndicadoresApi::class.java.isInstance(api))
        assertSame(api, ApisInstitucionales.uf)
    }
    @Test fun sedesConfiguradas() {
        val api = ApisInstitucionales.sedes
        assertTrue(SedesApi::class.java.isInstance(api))
        assertSame(api, ApisInstitucionales.sedes)
    }
    @Test fun solicitudesConfiguradas() {
        val api = SolicitudRetrofit.api
        assertTrue(SolicitudApi::class.java.isInstance(api))
        assertSame(api, SolicitudRetrofit.api)
        assertEquals("http://127.0.0.1:8080/", SolicitudRetrofit.BASE_URL)
    }
    @Test fun ufPrincipalRespuestaYRuta() = runTest {
        MockWebServer().use { server ->
            server.enqueue(MockResponse().setBody("""{"fecha":"2026-11-26","valor":40000.25}"""))
            val dato = retrofit(server).create(UfIndicadoresClApi::class.java).obtenerUfDelDia()
            assertEquals(40000.25, dato.valor, 0.001)
            assertEquals("2026-11-26", dato.fecha)
            assertEquals("/api/v1/uf.json", server.takeRequest(2, TimeUnit.SECONDS)?.path)
        }
    }
    @Test fun ufPrincipalErrorHttp() = runTest {
        MockWebServer().use { server ->
            server.enqueue(MockResponse().setResponseCode(503))
            try {
                retrofit(server).create(UfIndicadoresClApi::class.java).obtenerUfDelDia()
                fail<Unit>("Se esperaba HTTP 503")
            } catch (e: HttpException) { assertEquals(503, e.code()) }
        }
    }
    @Test fun ufRespaldoResumen() = runTest {
        MockWebServer().use { server ->
            server.enqueue(MockResponse().setBody("""{"uf":{"fecha":"2026-11-26","valor":40000.25}}"""))
            val dato = retrofit(server).create(IndicadoresApi::class.java).obtenerResumen()
            assertEquals(40000.25, dato.uf!!.valor, 0.001)
            assertEquals("/api", server.takeRequest(2, TimeUnit.SECONDS)?.path)
        }
    }
    @Test fun ufHistoricaVacia() = runTest {
        MockWebServer().use { server ->
            server.enqueue(MockResponse().setBody("""{"serie":[]}"""))
            assertTrue(retrofit(server).create(IndicadoresApi::class.java).obtenerUfHistorica().serie.isEmpty())
            assertEquals("/api/uf", server.takeRequest(2, TimeUnit.SECONDS)?.path)
        }
    }
    @Test fun sedeConParametrosPredeterminados() = runTest {
        MockWebServer().use { server ->
            server.enqueue(MockResponse().setBody("""[{"lat":"-33.4","lon":"-70.6","display_name":"Sede de prueba"}]"""))
            val dato = retrofit(server).create(SedesApi::class.java).buscarSede("Santiago").single()
            assertEquals("-33.4", dato.lat)
            assertEquals("-70.6", dato.lon)
            assertEquals("Sede de prueba", dato.direccionEncontrada)
            val url = server.takeRequest(2, TimeUnit.SECONDS)!!.requestUrl!!
            assertEquals("/search", url.encodedPath)
            assertEquals("Santiago", url.queryParameter("q"))
            assertEquals("jsonv2", url.queryParameter("format"))
            assertEquals("1", url.queryParameter("limit"))
            assertEquals("cl", url.queryParameter("countrycodes"))
        }
    }
    @Test fun sedeSinResultados() = runTest {
        MockWebServer().use { server ->
            server.enqueue(MockResponse().setBody("[]"))
            assertTrue(retrofit(server).create(SedesApi::class.java).buscarSede("Sin resultados").isEmpty())
        }
    }
    @Test fun sedeErrorHttp() = runTest {
        MockWebServer().use { server ->
            server.enqueue(MockResponse().setResponseCode(429))
            try {
                retrofit(server).create(SedesApi::class.java).buscarSede("Santiago")
                fail<Unit>("Se esperaba HTTP 429")
            } catch (e: HttpException) { assertEquals(429, e.code()) }
        }
    }
}
