package com.example.appcorpalivconecta_grupo15
import com.example.appcorpalivconecta_grupo15.model.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
class SolicitudValidacionCasosTest {
 private val base=FormState(nombre="Ana",correo="ana@ejemplo.cl",tipo="Donación",mensaje="Mensaje válido")
 @ParameterizedTest @ValueSource(strings=["", " ", "A", "AB", " A ", "  AB  ", "\t", "\n"])
 fun nombreInvalido(v:String){assertEquals(setOf("nombre"),ValidacionSolicitud.validar(base.copy(nombre=v)).keys)}
 @ParameterizedTest @ValueSource(strings=["Ana","Juan Pérez","  Ana  ","José","María Paz","ABC"])
 fun nombreValido(v:String){assertTrue(ValidacionSolicitud.validar(base.copy(nombre=v)).isEmpty())}
 @ParameterizedTest @ValueSource(strings=["", "a", "a@", "@cl.cl", "a@cl", "a@cl.c", "a b@cl.cl", "a@cl .cl", "a@@cl.cl", "a@cl.", "a@.cl", "a@cl.cl x"])
 fun correoInvalido(v:String){assertEquals(setOf("correo"),ValidacionSolicitud.validar(base.copy(correo=v)).keys)}
 @ParameterizedTest @ValueSource(strings=["a@b.cl","ana+1@ejemplo.cl","  a@b.cl  ","a.b@sub.ejemplo.cl","A@B.COM","a_b@ejemplo.org"])
 fun correoValido(v:String){assertTrue(ValidacionSolicitud.validar(base.copy(correo=v)).isEmpty())}
 @ParameterizedTest @ValueSource(strings=[""," ","\t","\n"])
 fun tipoInvalido(v:String){assertEquals(setOf("tipo"),ValidacionSolicitud.validar(base.copy(tipo=v)).keys)}
 @ParameterizedTest @ValueSource(strings=["Solicitar información","Información de programa","Consulta de producto","Consulta de servicio","Donación","Voluntariado","Alianza empresarial","Capacitación"])
 fun tipoValido(v:String){assertTrue(ValidacionSolicitud.validar(base.copy(tipo=v)).isEmpty())}
 @ParameterizedTest @ValueSource(strings=[""," ","1","12","123456789"," 123456789 ","\t12345", "hola"])
 fun mensajeInvalido(v:String){assertEquals(setOf("mensaje"),ValidacionSolicitud.validar(base.copy(mensaje=v)).keys)}
 @ParameterizedTest @ValueSource(strings=["1234567890"," 1234567890 ","Deseo colaborar","Información del programa","Consulta de producto"])
 fun mensajeValido(v:String){assertTrue(ValidacionSolicitud.validar(base.copy(mensaje=v)).isEmpty())}
}
