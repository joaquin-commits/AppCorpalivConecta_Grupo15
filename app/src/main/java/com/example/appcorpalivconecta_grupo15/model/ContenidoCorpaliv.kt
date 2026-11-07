package com.example.appcorpalivconecta_grupo15.model

/** Contenido público verificado en corpaliv.cl y albalab.corpaliv.cl (01-11-2026).
 * Las fotos de personas no se distribuyen sin confirmación de autorización de uso.
 * Los precios publicados son referenciales y pueden cambiar en el sitio oficial.
 */
enum class TipoAccion { FORMULARIO, ENLACE }
data class Ficha(
    val id: String,
    val titulo: String,
    val subtitulo: String,
    val descripcion: String,
    val categoria: String,
    val textoAccion: String,
    val tipoAccion: TipoAccion,
    val tipoFormulario: String = "Solicitar información",
    val enlace: String,
    val precioReferencia: String = ""
)
data class Noticia(val titulo: String, val fecha: String, val resumen: String, val enlace: String)

object ContenidoCorpaliv {
    const val sitio = "https://corpaliv.cl/"
    const val alba = "https://albalab.corpaliv.cl/"
    const val donaciones = "https://corpaliv.donando.cl/"
    const val contacto = "mailto:comunicaciones@corpaliv.cl"

    val programas = listOf(
        Ficha("escuela", "Escuela Especial Jan Van Dijk", "Educación y acompañamiento especializado",
            "La Escuela Especial Jan Van Dijk fue creada en 2003 y ofrece educación especializada a niños, niñas y jóvenes con discapacidad múltiple. CORPALIV también promueve el trabajo conjunto con sus familias.",
            "Educación", "Solicitar información", TipoAccion.FORMULARIO, "Información de programa", "https://corpaliv.cl/nuestra-escuela/"),
        Ficha("taller", "Programa Taller Sociolaboral", "Formación para la autonomía y el empleo",
            "Alba Lab desarrolla habilidades sociales, emocionales y laborales y promueve la elaboración de productos con sentido, con experiencias de aprendizaje y participación.",
            "Alba Lab", "Postular en sitio oficial", TipoAccion.ENLACE, enlace="https://albalab.corpaliv.cl/programa-taller-sociolaboral/"),
        Ficha("intermediacion", "Programa de Intermediación Laboral", "Acompañamiento hacia oportunidades laborales",
            "Programa con mecanismos de apoyo para facilitar la inclusión laboral de personas con discapacidad múltiple, desarrollando habilidades en contextos de trabajo.",
            "Alba Lab", "Postular en sitio oficial", TipoAccion.ENLACE, enlace="https://albalab.corpaliv.cl/programa-intermediacion-laboral/"),
        Ficha("servicios", "Servicios para empresas", "Formación, análisis y asesoría inclusiva",
            "Alba Lab presenta capacitaciones en inclusión, experiencias de concientización, análisis de puestos de trabajo, apoyo a la inserción laboral y evaluación de accesibilidad universal.",
            "Empresas", "Solicitar información", TipoAccion.FORMULARIO, "Consulta de servicio", "https://albalab.corpaliv.cl/servicios/"),
        Ficha("alianzas", "Alianzas y colaboración", "Empresas, organizaciones y personas",
            "CORPALIV vincula a personas y organizaciones que desean aportar a la educación, al desarrollo de capacidades y a la inclusión sociolaboral.",
            "Participación", "Colaborar", TipoAccion.FORMULARIO, "Alianza empresarial", "https://corpaliv.cl/para-empresas/")
    )

    val catalogo = listOf(
        Ficha("bolsa", "Bolsa Alba Lab", "Bolsa ecológica reutilizable",
            "Bolsa de algodón reutilizable y lavable, con identidad Alba Lab. Consulta disponibilidad y condiciones en la tienda oficial.",
            "Producto · Otros", "Consultar producto", TipoAccion.FORMULARIO, "Consulta de producto", "https://albalab.corpaliv.cl/producto/bolsa-alba-lab/", "$4.000"),
        Ficha("difusor100", "Difusor de Aroma 100 ml", "Bienestar · Aromas",
            "Producto de la categoría Bienestar de la tienda de Alba Lab. La compra no se procesa en esta aplicación académica.",
            "Producto · Bienestar", "Consultar producto", TipoAccion.FORMULARIO, "Consulta de producto", "https://albalab.corpaliv.cl/producto/difusor-de-aroma-100-ml/", "$7.500"),
        Ficha("difusor150", "Difusor de Aroma 150 ml", "Bienestar · Aromas",
            "Difusor publicado en el catálogo de Alba Lab. Para confirmar precio y disponibilidad, consulta el sitio oficial.",
            "Producto · Bienestar", "Consultar producto", TipoAccion.FORMULARIO, "Consulta de producto", "https://albalab.corpaliv.cl/producto/difusor-de-aroma-150-ml/", "$10.000"),
        Ficha("difusor250", "Difusor de Aroma 250 ml", "Bienestar · Aromas",
            "Difusor publicado en el catálogo de Alba Lab. Información referencial sujeta a confirmación.",
            "Producto · Bienestar", "Consultar producto", TipoAccion.FORMULARIO, "Consulta de producto", "https://albalab.corpaliv.cl/producto/difusor-de-aroma-250-ml/", "$12.000"),
        Ficha("difusorvarilla", "Difusor de varilla", "Bienestar · Aromas",
            "Producto de bienestar anunciado en la tienda Alba Lab. Consulta opciones vigentes.",
            "Producto · Bienestar", "Consultar producto", TipoAccion.FORMULARIO, "Consulta de producto", "https://albalab.corpaliv.cl/producto/difusor-de-varilla/", "$17.000"),
        Ficha("farol", "Farol de Luz", "Artículo Alba Lab",
            "Artículo del catálogo de productos de Alba Lab. Consulta detalles y disponibilidad con la organización.",
            "Producto · Otros", "Consultar producto", TipoAccion.FORMULARIO, "Consulta de producto", "https://albalab.corpaliv.cl/producto/farol-de-luz/", "$11.900"),
        Ficha("guatero", "Guatero de Semillas Grande", "Bienestar · Artículos de confort",
            "Artículo de la línea de bienestar disponible en la tienda Alba Lab según su catálogo público.",
            "Producto · Bienestar", "Consultar producto", TipoAccion.FORMULARIO, "Consulta de producto", "https://albalab.corpaliv.cl/producto/guatero-de-semillas-grande/", "$11.990"),
        Ficha("capacitaciones", "Capacitaciones en inclusión", "Servicio para empresas y organizaciones",
            "Alba Lab ofrece formación en diversidad e inclusión para promover herramientas prácticas y un trato respetuoso.",
            "Servicio", "Solicitar información", TipoAccion.FORMULARIO, "Consulta de servicio", "https://albalab.corpaliv.cl/servicios/"),
        Ficha("accesibilidad", "Evaluación de accesibilidad universal", "Servicio institucional",
            "Evaluación de condiciones arquitectónicas con el propósito de ayudar a eliminar barreras de accesibilidad.",
            "Servicio", "Solicitar información", TipoAccion.FORMULARIO, "Consulta de servicio", "https://albalab.corpaliv.cl/servicios/")
    )

    val noticias = listOf(
        Noticia("Impulso Laboral: nuevo proyecto aprobado", "25/11/2025",
            "CORPALIV informó la aprobación de una iniciativa de formación laboral para personas con discapacidad múltiple.",
            "https://corpaliv.cl/corpaliv-obtiene-aprobacion-para-nuevo-proyecto-impulso-laboral-formacion-laboral-para-personas-con-discapacidad-multiple/"),
        Noticia("Canal 13C destaca el trabajo de CORPALIV", "10/11/2025",
            "Reportaje sobre educación, terapias y apoyo especializado desde la infancia hacia la vida laboral.",
            "https://corpaliv.cl/canal-13c-destaca-a-corpaliv-por-apoyar-a-personas-con-discapacidad-multiple/"),
        Noticia("Capacitaciones de Alba Lab con Duoc UC", "11/07/2025",
            "Beneficiarios finalizaron talleres sobre logística, atención al cliente y merchandising.",
            "https://corpaliv.cl/jovenes-beneficiarios-de-albalab-reciben-diplomas-tras-ciclo-de-capacitaciones-en-duoc-uc/")
    )

    fun buscar(grupo: String, id: String): Ficha? =
        (if (grupo == "programa") programas else if (grupo == "catalogo") catalogo else emptyList()).firstOrNull { it.id == id }
}
