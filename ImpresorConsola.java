import java.util.ArrayList;
import java.util.List;

public class ImpresorConsola implements ImpresorTabla {

    private static final String SEPARADOR = " | ";

    @Override
    public void imprimir(Tabla tabla) {
        if (tabla == null) {
            throw new IllegalArgumentException("La tabla no puede ser null");
        }

        List<Integer> anchos = calcularAnchos(tabla);

        System.out.println(armarEncabezado(tabla, anchos));
        System.out.println(armarSeparador(anchos));

        if (tabla.estaVacia()) {
            System.out.println("(tabla sin filas)");
            return;
        }

        for (int fila = 0; fila < tabla.getCantidadFilas(); fila++) {
            System.out.println(armarFila(tabla, anchos, fila));
        }
    }

    private List<Integer> calcularAnchos(Tabla tabla) {
        List<Integer> anchos = new ArrayList<>();
        for (int columna = 0; columna < tabla.getCantidadColumnas(); columna++) {
            anchos.add(tabla.getEncabezado(columna).length());
        }
        for (int fila = 0; fila < tabla.getCantidadFilas(); fila++) {
            for (int columna = 0; columna < tabla.getCantidadColumnas(); columna++) {
                int largo = tabla.getValor(fila, columna).length();
                if (largo > anchos.get(columna)) {
                    anchos.set(columna, largo);
                }
            }
        }
        return anchos;
    }

    private String armarEncabezado(Tabla tabla, List<Integer> anchos) {
        String linea = "";
        for (int columna = 0; columna < tabla.getCantidadColumnas(); columna++) {
            if (columna > 0) {
                linea += SEPARADOR;
            }
            String encabezado = tabla.getEncabezado(columna).toUpperCase();
            linea += ajustar(encabezado, anchos.get(columna));
        }
        return linea;
    }

    private String armarFila(Tabla tabla, List<Integer> anchos, int fila) {
        String linea = "";
        for (int columna = 0; columna < tabla.getCantidadColumnas(); columna++) {
            if (columna > 0) {
                linea += SEPARADOR;
            }
            linea += ajustar(tabla.getValor(fila, columna), anchos.get(columna));
        }
        return linea;
    }

    private String armarSeparador(List<Integer> anchos) {
        int total = SEPARADOR.length() * (anchos.size() - 1);
        for (int columna = 0; columna < anchos.size(); columna++) {
            total += anchos.get(columna);
        }
        String separador = "";
        for (int i = 0; i < total; i++) {
            separador += "-";
        }
        return separador;
    }

    private String ajustar(String texto, int ancho) {
        return String.format("%-" + ancho + "s", texto);
    }
}