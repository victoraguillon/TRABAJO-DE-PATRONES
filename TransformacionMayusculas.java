public class TransformacionMayusculas implements TransformacionTabla {

    @Override
    public void aplicar(Tabla tabla) {
        if (tabla == null) {
            throw new IllegalArgumentException("La tabla no puede ser null");
        }
        for (int fila = 0; fila < tabla.getCantidadFilas(); fila++) {
            for (int columna = 0; columna < tabla.getCantidadColumnas(); columna++) {
                String valor = tabla.getValor(fila, columna);
                tabla.setValor(fila, columna, valor.toUpperCase());
            }
        }
    }
}
