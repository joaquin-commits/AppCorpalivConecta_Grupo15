# CORPALIV Conecta — Grupo 15 | Integración Guía 14

Esta copia conserva las pantallas y funcionalidades del ZIP recibido y agrega **API REST** a la barra inferior.

## Archivos añadidos
- `app/src/main/java/com/example/appcorpalivconecta_grupo15/data/model/Post.kt`
- `app/src/main/java/com/example/appcorpalivconecta_grupo15/data/remote/ApiService.kt`
- `app/src/main/java/com/example/appcorpalivconecta_grupo15/data/remote/RetrofitInstance.kt`
- `app/src/main/java/com/example/appcorpalivconecta_grupo15/repository/PostRepository.kt`
- `app/src/main/java/com/example/appcorpalivconecta_grupo15/viewmodel/PostViewModel.kt`
- `app/src/main/java/com/example/appcorpalivconecta_grupo15/ui/screens/PostScreen.kt`
- `app/src/test/java/com/example/appcorpalivconecta_grupo15/PostRepositoryTest.kt`

## Archivos modificados
- `app/build.gradle.kts`: Retrofit 2.11.0, Gson y corrutinas.
- `app/src/main/AndroidManifest.xml`: permiso INTERNET.
- `app/src/main/java/com/example/appcorpalivconecta_grupo15/navigation/AppNavigation.kt`: pestaña "API REST".

## Uso
1. Abre **AndroidStudioProjects** en Android Studio.
2. Sincroniza Gradle con Internet (Sync Project with Gradle Files).
3. Ejecuta el proyecto y abre la pestaña **API REST**.
4. Prueba **GET** (carga y refresca publicaciones), **POST** (+), **PUT** (Editar un post original) y **DELETE** (Eliminar con confirmación).
5. Ejecuta `./gradlew testDebugUnitTest` (Windows: `gradlew.bat testDebugUnitTest`) para las cuatro pruebas unitarias del repositorio.

**Atención:** JSONPlaceholder es un servicio de demostración: POST, PUT y DELETE responden como si las modificaciones fueran exitosas, pero **no modifican la base de datos del servidor**. Los cambios se muestran en la lista local hasta que se vuelve a cargar. La API solo admite PUT remoto de posts existentes; un post simulado 101 se puede quitar de la lista local pero no editar remotamente.

## Git / Trello
Rama sugerida: `feature/guia14-api-rest`.
Commit sugerido: `Guia 14 - Retrofit MVVM y operaciones REST GET POST PUT DELETE`.
Tarjeta Trello: **Guía 14 — Consumo de API REST**.
Descripción: "Se integró JSONPlaceholder mediante Retrofit con arquitectura MVVM y una nueva pantalla Compose para consultar, crear, actualizar y eliminar publicaciones. Se incorporaron manejo de errores, progreso y pruebas unitarias del repositorio.".

## Validación
No se pudo ejecutar una compilación Android completa en este entorno porque no está disponible el SDK de Android ni el acceso a descargas de dependencias de Gradle. Realiza la sincronización, compilación y ejecución en Android Studio antes de marcar la actividad como validada.

### Corrección de edición de publicaciones creadas
Las publicaciones nuevas con IDs locales superiores a 100 pueden editarse dentro de la aplicación. Es una edición local, porque JSONPlaceholder no persiste POST ni admite PUT de estos registros simulados. La edición de publicaciones originales (IDs hasta 100) sigue enviando PUT a la API.
