public class QuitarColumna implements TransformacionTabla {

    private final String nombre;

    public QuitarColumna(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre de la columna no puede estar vacío");
        }
        this.nombre = nombre;
    }

    @Override
    public void aplicar(Tabla tabla) {
        if (tabla == null) {
            throw new IllegalArgumentException("La tabla no puede ser null");
        }
        tabla.quitarColumna(nombre);
    }
}
