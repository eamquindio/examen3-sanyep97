<!-- Copilot / AI assistant instructions specific to the "parqueadero" project -->
# Instrucciones rápidas para agentes de IA

Propósito breve
- Este repo es un ejercicio/mini-proyecto Maven (Java 17) que implementa un sistema de parqueadero "EstacionaFácil".
- La fuente principal está en `src/main/java/edu/eam/ingesoft/fundamentos/parqueadero/logica` y el comportamiento esperado está cubierto por pruebas unitarias en `src/test/java/.../parqueadero` y por la especificación en `EXAMEN_PARQUEADERO.md`.

Arquitectura / Big picture
- Clases principales:
  - `Parqueadero` — orquesta el sistema, mantiene listas de `Propietario`, `Vehiculo`, `Servicio`.
  - `Propietario` — datos del cliente, horas acumuladas, categoría y descuento.
  - `Vehiculo` — datos del vehículo y tarifa por tipo (SEDAN, SUV, CAMION).
  - `Servicio` — registra ingreso/salida, calcula horas y costo aplicando descuentos.
- Relaciones: `Parqueadero` contiene colecciones, `Vehiculo` tiene un `Propietario`, `Servicio` referencia a un `Vehiculo`.

Qué buscar primero (prioridad)
1. `EXAMEN_PARQUEADERO.md` — definición de reglas de negocio y ejemplos (fuente de verdad para tests).
2. `src/test/java/.../ParqueaderoTest.java` y demás tests — casos y validaciones precisas (usar como checklist cuando hagas cambios).
3. `pom.xml` — Maven 17, tests con JUnit5, plugin `exec-maven-plugin` apuntando a `edu.eam.ingesoft.fundamentos.parqueadero.Main`.

Concretos: cómo ser productivo rápidamente
- Compilar: mvn clean compile
- Ejecutar tests: mvn test
- Ejecutar un test específico: mvn test -Dtest=ParqueaderoTest#testRegistrarPropietarioExitoso
- Ejecutar la demo/main: mvn -q exec:java (usa el `mainClass` definido en `pom.xml`)

Patrones y convenciones en este repo
- Atributos privados + getters; evita setters no solicitados por los tests.
- Los tests esperan valores exactos (enteros/doubles) y validaciones estrictas — sigue el formato y los límites en `EXAMEN_PARQUEADERO.md`.
- Se usan listas Java (`ArrayList`) y simples bucles foreach, if/switch para lógica: las pruebas asumen implementaciones explícitas (no streams ni reflejos extraños).

Dónde tocar y ejemplos de cambios esperados
- Para implementar una funcionalidad, abre la clase correspondiente en `src/main/java/edu/eam/ingesoft/fundamentos/parqueadero/logica`.
- Ejemplo: implementar `registrarPropietario` en `Parqueadero.java`:
  - Validar duplicado con `buscarPropietario` → si existe retorna false.
  - Si no existe: crear `new Propietario(cedula, nombre)` y agregar a `propietarios`.
  - Tests relevantes: `ParqueaderoTest#testRegistrarPropietarioExitoso`, `ParqueaderoTest#testRegistrarPropietarioDuplicado`.

Validaciones importantes (no improvisar)
- Horas en `registrarServicio`: ingreso 1-22, salida 2-23 y salida > ingreso.
- Tarifas fijas: SEDAN=1500, SUV=2300, CAMION=3000.
- Categorías por horas: ESTANDAR 0-100, ESPECIAL 101-500 (10%), VIP >500 (15%).

Tests y autograding
- Cada método tiene tests unitarios; cambia código y ejecuta subset de tests localmente antes de push.
- Para validar rápidamente un grupo de métodos, usa `mvn test -Dtest=ServicioTest` o `-Dtest=ParqueaderoTest#testRegistrarServicio*`.

¿Qué NO hacer?
- No cambies firmas públicas ni constantes de comportamiento esperadas por tests (por ejemplo nombres de métodos ni visibilidad).
- No modifiques los tests salvo que haya un bug en los tests; en ese caso documenta y abre una PR separada.

Si necesitas más contexto
- Revisa `EXAMEN_PARQUEADERO.md` para entradas de ejemplo y tablas con tarifas y reglas; utilízalo como la fuente de verdad.

Preguntas para el autor humano (si surgen)
- ¿Deseas que usemos Streams/JDK moderno o preferimos mantener implementaciones explícitas (foreach/if/switch) para claridad didáctica?  

-- Fin (si quieres, puedo ajustar esto con ejemplos más concretos o tests de muestra).
