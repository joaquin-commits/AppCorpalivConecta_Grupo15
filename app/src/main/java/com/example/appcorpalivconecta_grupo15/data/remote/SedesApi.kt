package com.example.appcorpalivconecta_grupo15.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/** Lugares confirmados; no se permite una búsqueda arbitraria sin controles. */
data class ResultadoSede(
    val lat: String,
    val lon: String,
    @SerializedName("display_name") val direccionEncontrada: String
)

data class SedeCorpaliv(
    val id: String,
    val nombre: String,
    val tipo: String,
    val direccion: String,
    val descripcion: String,
    val referencia: String,
    val esSedeCorpaliv: Boolean = true
)

object DirectorioCorpaliv {
    val lugares = listOf(
        SedeCorpaliv(
            id = "escuela",
            nombre = "Escuela Especial Jan Van Dijk",
            tipo = "Educación especial",
            direccion = "Lo Encalada 207, Ñuñoa, Santiago, Chile",
            descripcion = "Centro educativo de CORPALIV para niños, niñas y jóvenes con discapacidad múltiple. Incluye actividades educativas y apoyos interdisciplinarios.",
            referencia = "https://corpaliv.cl/nuestra-escuela/"
        ),
        SedeCorpaliv(
            id = "albalab",
            nombre = "Alba Lab — Red sociolaboral",
            tipo = "Talleres e inclusión sociolaboral",
            direccion = "General Salvo 20, Providencia, Santiago, Chile",
            descripcion = "Espacio vinculado a talleres y formación sociolaboral de jóvenes y adultos. Consulta antes de visitar, pues no todos los programas funcionan como atención abierta.",
            referencia = "https://albalab.corpaliv.cl/"
        ),
        // Recursos externos relevantes para educación y accesibilidad.
        // Son organizaciones independientes; NO se presentan como sedes ni aliados formales de CORPALIV.
        SedeCorpaliv(
            id = "biblioteca_ciegos",
            nombre = "Biblioteca Central para Ciegos",
            tipo = "Lectura accesible y talleres",
            direccion = "Rafael Cañas 165, Providencia, Santiago, Chile",
            descripcion = "Biblioteca especializada en acceso a lectura, material Braille y actividades para personas con discapacidad visual. Institución independiente de CORPALIV.",
            referencia = "https://www.bibliociegos.cl/",
            esSedeCorpaliv = false
        ),
        SedeCorpaliv(
            id = "senadis_rm",
            nombre = "SENADIS — Dirección Regional Metropolitana",
            tipo = "Orientación sobre discapacidad y derechos",
            direccion = "Mac Iver 440, piso 12, oficina 1201, Santiago, Chile",
            descripcion = "Servicio público de apoyo a la inclusión y orientación sobre discapacidad. No es sede de CORPALIV. Verifica sus modalidades de atención antes de acudir.",
            referencia = "https://www.senadis.gob.cl/pag/66/1317/direccion_regional",
            esSedeCorpaliv = false
        ),
        SedeCorpaliv(
            id = "teleton_santiago",
            nombre = "Instituto Teletón Santiago",
            tipo = "Rehabilitación e inclusión",
            direccion = "Avenida Libertador Bernardo O'Higgins 4620, Estación Central, Santiago, Chile",
            descripcion = "Centro de rehabilitación de Teletón en Santiago. Institución externa e independiente; no implica convenio con CORPALIV.",
            referencia = "https://www.teleton.cl/instituto/instituto-teleton-santiago/",
            esSedeCorpaliv = false
        ),
        SedeCorpaliv(
            id = "biblioteca_nacional",
            nombre = "Biblioteca Nacional — Servicios accesibles",
            tipo = "Acceso a cultura y lectura",
            direccion = "Avenida Libertador Bernardo O'Higgins 651, Santiago, Chile",
            descripcion = "Institución cultural con información sobre servicios para personas con discapacidad visual. No es una sede de CORPALIV.",
            referencia = "https://www.bibliotecanacional.gob.cl/personas-con-discapacidad-visual",
            esSedeCorpaliv = false
        )
    )
}

interface SedesApi {
    @Headers("User-Agent: CorpalivConectaGrupo15/1.1 (https://corpaliv.cl)")
    @GET("search")
    suspend fun buscarSede(
        @Query("q") direccion: String,
        @Query("format") formato: String = "jsonv2",
        @Query("limit") limite: Int = 1,
        @Query("countrycodes") pais: String = "cl"
    ): List<ResultadoSede>
}
