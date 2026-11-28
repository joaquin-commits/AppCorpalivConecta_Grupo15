package com.example.appcorpalivconecta_grupo15

import com.example.appcorpalivconecta_grupo15.data.remote.SolicitudApi
import com.example.appcorpalivconecta_grupo15.model.Solicitud
import com.example.appcorpalivconecta_grupo15.repository.SolicitudRemotaRepository
import com.google.gson.Gson
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SolicitudApiTest {
    private val solicitud = Solicitud("Persona ficticia", "prueba@ejemplo.cl", "", "Donación", "Mensaje de prueba", "", "26/11/2026", id=0)
    private fun repo(server: MockWebServer) = SolicitudRemotaRepository(Retrofit.Builder().baseUrl(server.url("/"))
        .addConverterFactory(GsonConverterFactory.create()).build().create(SolicitudApi::class.java))
    private fun json(body: Any, status: Int = 200) = MockResponse().setResponseCode(status).setHeader("Content-Type","application/json").setBody(Gson().toJson(body))
    @Test fun getLista() = runTest { MockWebServer().use { s ->
        s.enqueue(json(listOf(solicitud.copy(id=1)))); assertEquals(1L,repo(s).listar().single().id)
        val r=s.takeRequest();assertEquals("GET",r.method);assertEquals("/solicitudes",r.path)
    } }
    @Test fun getVacio() = runTest { MockWebServer().use { s -> s.enqueue(json(emptyList<Solicitud>()));assertTrue(repo(s).listar().isEmpty()) } }
    @Test fun postReal() = runTest { MockWebServer().use { s ->
        s.enqueue(json(solicitud.copy(id=2),201));assertEquals(2L,repo(s).guardar(solicitud).id)
        val r=s.takeRequest();assertEquals("POST",r.method);assertEquals("/solicitudes",r.path);assertEquals(solicitud,Gson().fromJson(r.body.readUtf8(),Solicitud::class.java))
    } }
    @Test fun putReal() = runTest { MockWebServer().use { s ->
        val edit=solicitud.copy(id=3,mensaje="Mensaje modificado");s.enqueue(json(edit));assertEquals(edit,repo(s).guardar(edit))
        val r=s.takeRequest();assertEquals("PUT",r.method);assertEquals("/solicitudes/3",r.path);assertEquals(edit,Gson().fromJson(r.body.readUtf8(),Solicitud::class.java))
    } }
    @Test fun deleteReal() = runTest { MockWebServer().use { s ->
        s.enqueue(MockResponse().setResponseCode(204));repo(s).eliminar(3);val r=s.takeRequest();assertEquals("DELETE",r.method);assertEquals("/solicitudes/3",r.path)
    } }
    @ParameterizedTest @ValueSource(ints=[400,401,403,404,409,429,500,502,503,504])
    fun getError(code:Int) = runTest { MockWebServer().use { s ->
        s.enqueue(MockResponse().setResponseCode(code));try { repo(s).listar(); fail<Unit>("Se esperaba HTTP $code") } catch(e:HttpException){assertEquals(code,e.code())}
    } }
    @ParameterizedTest @ValueSource(ints=[400,401,403,404,409,429,500,502,503,504])
    fun postError(code:Int) = runTest { MockWebServer().use { s ->
        s.enqueue(MockResponse().setResponseCode(code));try { repo(s).guardar(solicitud); fail<Unit>("Se esperaba HTTP $code") } catch(e:HttpException){assertEquals(code,e.code())}
    } }
    @ParameterizedTest @ValueSource(ints=[400,401,403,404,409,429,500,502,503,504])
    fun putError(code:Int) = runTest { MockWebServer().use { s ->
        s.enqueue(MockResponse().setResponseCode(code));try { repo(s).guardar(solicitud.copy(id=3)); fail<Unit>("Se esperaba HTTP $code") } catch(e:HttpException){assertEquals(code,e.code())}
    } }
    @ParameterizedTest @ValueSource(ints=[400,401,403,404,409,429,500,502,503,504])
    fun deleteError(code:Int) = runTest { MockWebServer().use { s ->
        s.enqueue(MockResponse().setResponseCode(code));try { repo(s).eliminar(3); fail<Unit>("Se esperaba HTTP $code") } catch(e:HttpException){assertEquals(code,e.code())}
    } }
}
