public class Main {

    private static final String RUTA_CSV = "datos/ejemplo.csv";

    public static void main(String[] args) {
        try {
            mostrarTablaOriginal();
            mostrarUsoDelObjeto();
            mostrarProcesadorConTransformaciones();
            mostrarExtensionPropia();
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void mostrarTablaOriginal() {
        System.out.println("========== 1. TABLA ORIGINAL ==========");

        LectorCsv lector = new LectorCsvSimple(',');
        Tabla tabla = lector.leer(RUTA_CSV);

        ImpresorTabla impresor = new ImpresorConsola();
        impresor.imprimir(tabla);

        System.out.println();
    }

    private static void mostrarUsoDelObjeto() {
        System.out.println("========== 2. USO DIRECTO DEL OBJETO ==========");

        Tabla tabla = new LectorCsvSimple().leer(RUTA_CSV);

        System.out.println("Se quita la columna id.");
        tabla.quitarColumna("id");

        System.out.println("Se agrega la columna activo con el valor \"si\".");
        tabla.agregarColumna("activo", "si");

        System.out.println("Se quita la primera fila.");
        tabla.quitarFila(0);

        System.out.println("Se modifica la celda de la fila 0, columna 0.");
        tabla.setValor(0, 0, "Ana María");

        System.out.println();
        new ImpresorConsola().imprimir(tabla);
        System.out.println();
    }

    private static void mostrarProcesadorConTransformaciones() {
        System.out.println("========== 3. PROCESADOR CON TRANSFORMACIONES ==========");

        ImpresorTabla impresor = new ImpresorConPie(
                new ImpresorConTitulo(new ImpresorConsola(), "EMPLEADOS"),
                "Fin del reporte");

        ProcesadorCsv procesador = new ProcesadorCsvBuilder()
                .conLector(new LectorCsvSimple(','))
                .conImpresor(impresor)
                .agregarTransformacion(new RecortarEspacios())
                .agregarTransformacion(new QuitarColumna("id"))
                .agregarTransformacion(new AgregarColumna("pais", "Uruguay"))
                .agregarTransformacion(new QuitarFila(0))
                .construir();

        procesador.procesar(RUTA_CSV);
        System.out.println();
    }

    private static void mostrarExtensionPropia() {
        System.out.println("========== 4. EXTENSIÓN SIN TOCAR LA LIBRERÍA ==========");
        System.out.println("TransformacionMayusculas e ImpresorConAsteriscos son de ejemplo:");
        System.out.println("no pertenecen a la librería y no se modificó ningún archivo.");
        System.out.println();

        ImpresorTabla impresor = new ImpresorConAsteriscos(new ImpresorConsola());

        ProcesadorCsv procesador = new ProcesadorCsvBuilder()
                .conImpresor(impresor)
                .agregarTransformacion(new TransformacionMayusculas())
                .construir();

        procesador.procesar(RUTA_CSV);
        System.out.println();
    }
}
