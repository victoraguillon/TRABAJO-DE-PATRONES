import java.util.ArrayList;
import java.util.List;

public class ProcesadorCsv extends ProcesadorCsvBase {

    private final LectorCsv lector;
    private final ImpresorTabla impresor;
    private final List<TransformacionTabla> transformaciones;

    public ProcesadorCsv(LectorCsv lector, ImpresorTabla impresor,
            List<TransformacionTabla> transformaciones) {
        if (lector == null) {
            throw new IllegalArgumentException("El lector no puede ser null");
        }
        if (impresor == null) {
            throw new IllegalArgumentException("El impresor no puede ser null");
        }
        this.lector = lector;
        this.impresor = impresor;
        this.transformaciones = new ArrayList<>();
        if (transformaciones != null) {
            for (TransformacionTabla transformacion : transformaciones) {
                if (transformacion == null) {
                    throw new IllegalArgumentException("La transformación no puede ser null");
                }
                this.transformaciones.add(transformacion);
            }
        }
    }

    @Override
    protected LectorCsv crearLector() {
        return lector;
    }

    @Override
    protected ImpresorTabla crearImpresor() {
        return impresor;
    }

    @Override
    protected List<TransformacionTabla> getTransformaciones() {
        return new ArrayList<>(transformaciones);
    }
}
