# Fotografías oficiales: pendientes de autorización

Se identificaron secciones con fotografías institucionales en las fuentes oficiales:

| Sección de la aplicación | Sitio de origen para solicitar autorización | Estado |
|---|---|---|
| Escuela Jan Van Dijk | https://corpaliv.cl/nuestra-escuela/ | Fotografía no incluida: derechos y consentimiento de retratados no verificados |
| Programas Alba Lab | https://albalab.corpaliv.cl/programa-taller-sociolaboral/ | No incluida |
| Intermediación laboral | https://albalab.corpaliv.cl/programa-intermediacion-laboral/ | No incluida |
| Noticias CORPALIV | https://corpaliv.cl/blog/ | No incluida |
| Productos Alba Lab | https://albalab.corpaliv.cl/ | Imágenes comerciales no incluidas: licencia no confirmada |

El logo de **CORPALIV Conecta** fue provisto directamente por el usuario y sí está integrado.

## Cómo incorporarlas cuando estén autorizadas

1. Obtener imágenes autorizadas y, si contienen personas, confirmar consentimiento de uso, especialmente en caso de menores de edad.
2. Reducir resolución a aproximadamente 900–1200 px de ancho y guardar en `app/src/main/res/drawable/` como WebP/JPEG optimizados.
3. Asignar una imagen distinta a cada `Ficha`, añadir un campo `imagen: Int?` o equivalente, y mostrarla con `Image(painterResource(...), contentDescription=..., contentScale=ContentScale.Crop)` dentro de `RecursoVisual`.
4. No publicar fotos sin autorización institucional y de privacidad.

La aplicación ofrece enlaces externos oficiales para acceder a contenido real sin redistribuir las fotografías dentro del APK.
