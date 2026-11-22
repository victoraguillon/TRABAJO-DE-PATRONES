public class AgregarColumna implements TransformacionTabla {

    private final String nombre;
    private final String valorPorDefecto;

    public AgregarColumna(String nombre, String valorPorDefecto) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre de la columna no puede estar vacío");
        }
        this.nombre = nombre;
        this.valorPorDefecto = valorPorDefecto;
    }

    @Override
    public void aplicar(Tabla tabla) {
        if (tabla == null) {
            throw new IllegalArgumentException("La tabla no puede ser null");
        }
        tabla.agregarColumna(nombre, valorPorDefecto);
    }
}
