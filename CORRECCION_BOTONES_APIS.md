# Corrección de tarjetas UF y ubicación

Se corrigió `SectionCard` en `CorpalivScreens.kt` para que la tarjeta completa use `Modifier.clickable` con la acción de navegación que recibe desde `CorpalivHome`.

- **Calcular aporte en UF** → ruta `aportes-uf` → `AportesUfScreen` → botón `Consultar UF`.
- **Encuentra Alba Lab** → ruta `sede-albalab` → `SedeAlbaLabScreen` → botón de consulta de ubicación.
- Se añadieron dos pruebas instrumentadas `SectionCardClickTest` para validar que ambas tarjetas ejecuten sus callbacks.

**Pruebas pendientes en el equipo local:** ejecutar `:app:connectedDebugAndroidTest`, reinstalar la APK desde Android Studio, probar la navegación. No se verificó ejecución contra servicios externos en este entorno.
