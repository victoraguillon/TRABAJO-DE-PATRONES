# Librería CSV extensible — PROYECTO-1

Librería en Java para leer archivos CSV y operar sobre ellos como objetos, pensada para
ampliarse con código propio sin modificar la librería.

**Materia:** Programación Orientada a Objetos
**Integrantes:** Victor Aguillon · Rubén Adarme · Rafael Bula
**Repositorio:** https://github.com/victoraguillon/TRABAJO-DE-PATRONES

---

## 1. Qué hace

| Función | Cómo se usa |
|---|---|
| Leer un CSV | `new LectorCsvSimple().leer("datos/ejemplo.csv")` |
| Obtener y modificar celdas | `tabla.getValor(fila, columna)` / `tabla.setValor(fila, columna, valor)` |
| Agregar y quitar filas | `tabla.agregarFila(List<String>)` / `tabla.quitarFila(indice)` |
| Agregar y quitar columnas | `tabla.agregarColumna(nombre, valorPorDefecto)` / `tabla.quitarColumna(nombre)` |
| Imprimir la tabla | `new ImpresorConsola().imprimir(tabla)` |
| Decorar la salida | `new ImpresorConTitulo(impresor, "Título")` |
| Encadenar transformaciones | `new ProcesadorCsvBuilder()...procesar(ruta)` |

Todos los datos se guardan como `String`. La librería no interpreta tipos: si se necesitan
números o fechas, se convierten en una transformación propia.

## 2. Requisitos

- JDK 21 o superior.
- No hay dependencias externas, ni Maven, ni Gradle.

## 3. Compilar y ejecutar

Desde la raíz del proyecto:

```cmd
javac *.java
java "-Dstdout.encoding=UTF-8" Main
```

La opción `-Dstdout.encoding=UTF-8` es necesaria en Windows para que los acentos se vean
correctamente en la consola. En Linux o macOS alcanza con `java Main`.

La demo muestra cuatro bloques:

1. La tabla leída del archivo.
2. Uso directo del objeto `Tabla` (quitar columna, agregar columna, quitar fila, modificar celda).
3. Un procesador armado con el constructor encadenado y un impresor decorado.
4. Dos clases escritas fuera de la librería que se integran sin tocarla.

## 4. Reglas del formato CSV

| Situación | Resultado |
|---|---|
| `a,b,c` | Tres campos: `a`, `b`, `c` |
| `"a,b",c` | Dos campos: `a,b` y `c` |
| `"dijo ""hola""",x` | Dos campos: `dijo "hola"` y `x` |
| Línea en blanco | Se ignora |
| Coma final (`a,b,`) | Tercer campo vacío |
| Primera línea | Siempre se toma como encabezado |
| Fila con menos campos que el encabezado | Se completa con cadenas vacías |
| Fila con más campos que el encabezado | Se descartan los sobrantes |
| BOM UTF-8 al inicio | Se descarta |
| Archivo inexistente o ilegible | `IllegalStateException` |
| Archivo vacío | `IllegalStateException` |

**Limitaciones declaradas:** no se admiten saltos de línea dentro de un campo entrecomillado
y no se interpretan tipos.

El separador es configurable: `new LectorCsvSimple(';')`.

## 5. Estructura

```
PROYECTO-1/
├── Tabla.java                  modelo: encabezados y filas
├── LectorCsv.java              interfaz de lectura
├── LectorCsvSimple.java        parser de CSV
├── ImpresorTabla.java          interfaz de salida
├── ImpresorConsola.java        salida por consola
├── ImpresorDecorador.java      base de los decoradores
├── ImpresorConTitulo.java      título antes de la tabla
├── ImpresorConPie.java         pie después de la tabla
├── TransformacionTabla.java    interfaz de transformación
├── QuitarColumna.java          transformación: quita una columna
├── AgregarColumna.java         transformación: agrega una columna
├── QuitarFila.java             transformación: quita una fila
├── RecortarEspacios.java       transformación: aplica trim
├── ProcesadorCsvBase.java      esqueleto del flujo
├── ProcesadorCsv.java          procesador configurable
├── ProcesadorCsvBuilder.java   constructor encadenado
├── Main.java                   demo
├── TransformacionMayusculas.java  ejemplo de extensión
├── ImpresorConAsteriscos.java     ejemplo de extensión
├── datos/ejemplo.csv
└── docs/diagrama-clases.mmd
```

Los dieciséis primeros archivos forman la librería. Los tres últimos son ejemplos: no son
necesarios para usar la librería y no son referenciados por ella.

## 6. Cómo extenderla

### 6.1 Una transformación propia

1. Crear el archivo `MiTransformacion.java` en la misma carpeta:

```java
public class MiTransformacion implements TransformacionTabla {

    @Override
    public void aplicar(Tabla tabla) {
        for (int fila = 0; fila < tabla.getCantidadFilas(); fila++) {
            for (int columna = 0; columna < tabla.getCantidadColumnas(); columna++) {
                String valor = tabla.getValor(fila, columna);
                tabla.setValor(fila, columna, valor + "!");
            }
        }
    }
}
```

2. Pasarla al procesador:

```java
ProcesadorCsv procesador = new ProcesadorCsvBuilder()
        .agregarTransformacion(new MiTransformacion())
        .construir();

procesador.procesar("datos/ejemplo.csv");
```

3. Compilar y ejecutar con `javac *.java` y `java Main`.

No hace falta editar ningún archivo de la librería: el constructor acepta cualquier objeto
que implemente `TransformacionTabla`.

### 6.2 Un impresor propio

Para un formato de salida nuevo, implementar la interfaz:

```java
public class MiImpresor implements ImpresorTabla {

    @Override
    public void imprimir(Tabla tabla) {
        System.out.println("Tabla de " + tabla.getCantidadFilas() + " filas");
    }
}
```

Y usarlo con `new ProcesadorCsvBuilder().conImpresor(new MiImpresor())`, o directamente:

```java
new MiImpresor().imprimir(tabla);
```

Si solo se quiere agregar algo alrededor de una salida existente, conviene **decorar** en
lugar de implementar desde cero:

```java
public class MiDecorador extends ImpresorDecorador {

    public MiDecorador(ImpresorTabla impresor) {
        super(impresor);
    }

    @Override
    public void imprimir(Tabla tabla) {
        System.out.println("--- inicio ---");
        super.imprimir(tabla);
        System.out.println("--- fin ---");
    }
}
```

### 6.3 Una fuente de datos distinta

`LectorCsv` también es una interfaz. Una implementación propia puede leer de otro origen
(texto en memoria, otro separador, otra codificación) y devolver una `Tabla`:

```java
public class LectorDesdeTexto implements LectorCsv {

    private final String contenido;

    public LectorDesdeTexto(String contenido) {
        this.contenido = contenido;
    }

    @Override
    public Tabla leer(String ruta) {
        Tabla tabla = new Tabla(List.of("columna"));
        tabla.agregarFila(List.of(contenido));
        return tabla;
    }
}
```

## 7. API de `Tabla`

| Método | Qué hace |
|---|---|
| `getCantidadFilas()` | Cantidad de filas |
| `getCantidadColumnas()` | Cantidad de columnas |
| `getEncabezado(columna)` | Nombre de una columna |
| `setEncabezado(columna, valor)` | Cambia el nombre de una columna |
| `getValor(fila, columna)` | Valor de una celda |
| `setValor(fila, columna, valor)` | Cambia el valor de una celda |
| `getFila(fila)` | Copia de una fila |
| `agregarFila(valores)` | Agrega una fila al final |
| `quitarFila(indice)` | Quita una fila |
| `buscarColumna(nombre)` | Índice de la columna, o `-1` |
| `agregarColumna(nombre, valorPorDefecto)` | Agrega una columna al final |
| `quitarColumna(nombre)` | Quita una columna |
| `estaVacia()` | Indica si no hay filas |
| `toString()` | Resumen tipo `Tabla(5 filas x 5 columnas)` |

Los índices fuera de rango lanzan `IllegalArgumentException`. Una tabla nunca se queda sin
columnas y nunca contiene celdas `null`.

## 8. Documentación

| Archivo | Contenido |
|---|---|
| `INFORME.md` | Informe académico del proyecto |
| `docs/diagrama-clases.mmd` | Diagrama de clases en Mermaid |
| `docs/PLAN-EQUIPO.md` | Reparto de trabajo y reglas del equipo |
| `docs/DIVISION-INFORME.md` | Reparto del informe sección por sección |
| `docs/INDICE.md` | Índice de la documentación del proyecto |
