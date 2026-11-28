# APIs externas — CORPALIV Conecta

## 1. UF: integración para aportes orientativos
- Fuente principal: https://indicadores.cl/api/v1/uf.json (GET; estructura `valor`, `fecha`)
- Respaldo 1: https://mindicador.cl/api (estructura `uf.valor`, `uf.fecha`)
- Respaldo 2: https://mindicador.cl/api/uf (estructura `serie`)
- Atribución de indicadores.cl: https://indicadores.cl/api/
- Si falla la conexión, se muestra el detalle de error y **no se inventan valores**.
- Cálculo: cantidad de UF × valor de UF en pesos.
- La app **no procesa donaciones ni pagos**.

## 2. Espacios de CORPALIV
- Escuela Especial Jan Van Dijk — Lo Encalada 207, Ñuñoa, Santiago, Chile. Fuentes: proyecto educativo del Mineduc y páginas de CORPALIV: https://wwwfs.mineduc.cl/Archivos/infoescuelas/documentos/25828/ProyectoEducativo25828.pdf y https://corpaliv.cl/nuestra-escuela/
- Alba Lab — General Salvo 20, Providencia, Santiago, Chile. Fuente: https://corpaliv.cl/red-socio-laboral/
- En la app se distinguen dos sedes vinculadas a CORPALIV y cuatro instituciones externas INDEPENDIENTES (no se afirma colaboración formal):
  - Biblioteca Central para Ciegos — Rafael Cañas 165, Providencia. https://www.bibliociegos.cl/post/c%C3%B3mo-imprimir-en-braille-en-la-biblioteca-central-para-ciegos
  - SENADIS RM — Mac Iver 440, piso 12, oficina 1201, Santiago. https://www.senadis.gob.cl/pag/66/1317/direccion_regional
  - Instituto Teletón Santiago — Av. Libertador Bernardo O'Higgins 4620, Estación Central. https://www.teleton.cl/instituto/instituto-teleton-santiago/
  - Biblioteca Nacional — Av. Libertador Bernardo O'Higgins 651, Santiago. https://www.bibliotecanacional.gob.cl/personas-con-discapacidad-visual
- No se inventan direcciones separadas para talleres, cursos u oficinas sin confirmación.
- API Nominatim de OpenStreetMap: búsqueda manual por dirección, caché por sede durante la sesión, sin consultas automáticas.
- El mapa funciona también como búsqueda por dirección cuando no responde Nominatim.
- Política pública: https://operations.osmfoundation.org/policies/nominatim/

## Pruebas manuales
1. Abrir Participar > Calcular aporte en UF > Consultar UF. Confirmar valor/fecha reales y cálculo en pesos.
2. Desactivar internet y actualizar UF. Debe aparecer un mensaje de error, no un valor ficticio.
3. Abrir Participar > Encuentra Alba Lab. Ver las dos sedes de CORPALIV y cuatro instituciones externas independientes, sus descripciones y direcciones.
4. Consultar coordenadas de una sede y abrir mapa. Probar también «Ver en mapa» sin consulta previa.
5. Confirmar que el resto de la app y las pruebas de Guía 15 siguen funcionando.
