public class QuitarFila implements TransformacionTabla {

    private final int indice;

    public QuitarFila(int indice) {
        if (indice < 0) {
            throw new IllegalArgumentException("El índice no puede ser negativo: " + indice);
        }
        this.indice = indice;
    }

    @Override
    public void aplicar(Tabla tabla) {
        if (tabla == null) {
            throw new IllegalArgumentException("La tabla no puede ser null");
        }
        tabla.quitarFila(indice);
    }
}
