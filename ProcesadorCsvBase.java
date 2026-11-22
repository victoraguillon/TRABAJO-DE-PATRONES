import java.util.List;

public abstract class ProcesadorCsvBase {

    public final void procesar(String ruta) {
        Tabla tabla = crearLector().leer(ruta);
        antesDeTransformar(tabla);
        for (TransformacionTabla transformacion : getTransformaciones()) {
            transformacion.aplicar(tabla);
        }
        despuesDeTransformar(tabla);
        crearImpresor().imprimir(tabla);
    }

    protected abstract LectorCsv crearLector();

    protected abstract ImpresorTabla crearImpresor();

    protected abstract List<TransformacionTabla> getTransformaciones();

    protected void antesDeTransformar(Tabla tabla) {
    }

    protected void despuesDeTransformar(Tabla tabla) {
    }
}
