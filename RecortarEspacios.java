public class RecortarEspacios implements TransformacionTabla {

    @Override
    public void aplicar(Tabla tabla) {
        if (tabla == null) {
            throw new IllegalArgumentException("La tabla no puede ser null");
        }
        for (int columna = 0; columna < tabla.getCantidadColumnas(); columna++) {
            tabla.setEncabezado(columna, tabla.getEncabezado(columna).trim());
        }
        for (int fila = 0; fila < tabla.getCantidadFilas(); fila++) {
            for (int columna = 0; columna < tabla.getCantidadColumnas(); columna++) {
                tabla.setValor(fila, columna, tabla.getValor(fila, columna).trim());
            }
        }
    }
}
