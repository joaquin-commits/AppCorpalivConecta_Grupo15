# Guía 15 — Detectando errores antes de que ocurran

Proyecto: CORPALIV Conecta — Grupo 15. Se preservó la aplicación de la Guía 14 y se añadieron pruebas aisladas de servicios, ViewModel y UI.

## Qué se incorporó

- `src/test/.../Guia15PostRepositoryKotest.kt`: 5 casos, GET, POST, PUT, DELETE y error HTTP.
- `src/test/.../Guia15PostViewModelKotest.kt`: 5 casos, carga exitosa, error, validación, creación y edición de publicación local.
- `src/androidTest/.../Guia15PostScreenTest.kt`: 3 casos instrumentados Compose, listado, formulario y edición.
- Dependencias Kotest, JUnit5, MockK, `kotlinx-coroutines-test` y Compose UI Test.
- JUnit5 se utiliza para los tests locales JVM; en Android `androidTest` utiliza JUnit4 con las reglas oficiales de Compose. El motor Vintage conserva los tests JUnit4 previos.

**Estos son casos de prueba incorporados, no resultados de ejecución verificados.**

## Cómo ejecutarlos en Android Studio

1. Abrir la carpeta `AndroidStudioProjects` como proyecto y sincronizar Gradle.
2. Ejecutar `./gradlew.bat :app:testDebugUnitTest` desde la terminal (PowerShell: ` .\gradlew.bat :app:testDebugUnitTest`).
3. Iniciar un emulador Android desde Device Manager y ejecutar ` .\gradlew.bat :app:connectedDebugAndroidTest`.
4. Tomar capturas de `BUILD SUCCESSFUL` y de la lista de pruebas con sus resultados.
5. Si alguna prueba falla, guardar el error y corregirlo antes de marcar la tarjeta como Finalizada.

## Evidencias y trabajo colaborativo

- Trello: tarjeta «Guía 15 — Testing híbrido de CORPALIV Conecta».
- Checklist: configuración, Kotest/MockK, corrutinas, pruebas UI, ejecución, evidencias, GitHub.
- Commit sugerido: `Creación de unit test`.
- Rama sugerida: `feature/guia15-testing` basada en `feature/guia14-api-rest`.

## Observación

Este ZIP se construyó desde la versión **Corregida** de la Guía 14. Si tu copia publicada de GitHub es la versión «Integrada», verifica las diferencias antes de fusionar, especialmente el botón «Editar» de publicaciones locales. No sobrescribas archivos ajenos a la guía sin comparar.
