# CORPALIV Conecta — Evaluación Parcial 3

## Ejecutar en Windows
1. Descomprimir el ZIP. Abrir AndroidStudioProjects en Android Studio (se conserva la configuración Gradle y SDK del proyecto original).
2. En PowerShell, desde la raíz: `node api/server.js`. Mantener la ventana abierta. Requiere Node.js 18 o superior; no requiere paquetes npm.
3. Abrir http://localhost:8080/solicitudes en navegador: devuelve una lista JSON. El archivo api/solicitudes.json se crea automáticamente.
4. Ejecutar la aplicación en el emulador Android Studio. Retrofit apunta a http://10.0.2.2:8080/; no usar localhost en el emulador.
5. Inicio > Colaborar: completar con datos ficticios, guardar (POST). Solicitudes: actualizar historial (GET), editar y guardar (PUT), eliminar y confirmar (DELETE). Cada cambio se conserva en un archivo JSON local y se refleja en el navegador al recargar.

Para un teléfono físico en la misma red de confianza: iniciar en PowerShell con `$env:CORPALIV_HOST='0.0.0.0'; node api/server.js`, cambiar BASE_URL en SolicitudApi.kt a la IP LAN del PC con puerto 8080, permitir el puerto en firewall solo para la red privada, recompilar. No exponer a Internet. API sin autenticación/TLS para demostración académica; usar exclusivamente datos ficticios. No es una API oficial de CORPALIV.

## Qué mostrar al profesor
- `data/remote/SolicitudApi.kt`: URL, instancia Retrofit, GET, POST, PUT y DELETE.
- `repository/SolicitudRemotaRepository.kt`: consumo de endpoints y errores HTTP.
- `viewmodel/ColaboracionViewModel.kt`: estado, validaciones, operaciones y mensajes.
- `ui/screens/CorpalivScreens.kt`: formulario e historial.
- La API propia es una sola API CRUD. Se mantienen las funcionalidades institucionales existentes de UF y ubicación. Se retiran los archivos y pruebas de la demostración JSONPlaceholder; las guías anteriores quedan como antecedentes. Los registros DataStore anteriores no se borran ni migran automáticamente; el nuevo historial consulta la API.

## Pruebas
- `gradlew.bat :app:testDebugUnitTest`: lógica (JUnit, MockK, coroutines-test, MockWebServer).
- `gradlew.bat :app:connectedDebugAndroidTest`: interfaz Compose en emulador iniciado.
- `gradlew.bat :app:createDebugUnitTestCoverageReport`: cobertura (si la versión AGP expone esta tarea; comprobar con `gradlew.bat :app:tasks --all`). Abrir informe de cobertura en app/build/reports o usar Run with Coverage en Android Studio.
- Nuevos casos JVM: 45 API/repository, 57 validación parametrizada, 13 ViewModel (115 casos previstos), más 11 casos de UF y ubicaciones y 6 UI. No contar los tests anteriores como cobertura de la integración nueva. Los casos HTTP alternativos simulan respuestas, no implican que el servidor implemente autenticación.
- Backend: `node --test api/server.test.js` (11 subpruebas HTTP y su prueba agrupadora, todas aprobadas en el entorno de preparación).
- El entorno de preparación no cuenta con Android SDK y no pudo descargar Gradle. No se ha compilado ni ejecutado la suite Android; el porcentaje 80% no está certificado. Verificar en Android Studio antes de entregar.

## APK firmada (requisito pendiente de ejecutar)
Android Studio > Build > Generate Signed App Bundle or APK > APK > Next > Create new (si no hay keystore) > guardar .jks fuera del repositorio > completar alias, contraseñas y certificado del equipo > Next > release > Create/Finish. Usar los datos reales del equipo; no publicar las contraseñas ni la llave. Seleccionar Locate para conocer ubicación exacta, normalmente app/release o app/build/outputs/apk/release. No se incluye APK porque no se pudo compilar ni firmar aquí. Verificar instalación y CRUD con servidor activo.

## GitHub
Subir el proyecto al repositorio del equipo, sin .jks, contraseñas, api/solicitudes.json, local.properties ni directorios build. Mostrar repositorio al profesor. No se ha publicado el proyecto desde este entorno.

Los README/guías anteriores se conservan como antecedentes de las entregas previas; para EP3 prevalece este archivo. No se prepara PPT del examen transversal.
