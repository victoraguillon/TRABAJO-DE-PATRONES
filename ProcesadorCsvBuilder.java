import java.util.ArrayList;
import java.util.List;

public class ProcesadorCsvBuilder {

    private LectorCsv lector;
    private ImpresorTabla impresor;
    private final List<TransformacionTabla> transformaciones;

    public ProcesadorCsvBuilder() {
        this.lector = new LectorCsvSimple(',');
        this.impresor = new ImpresorConsola();
        this.transformaciones = new ArrayList<>();
    }

    public ProcesadorCsvBuilder conLector(LectorCsv lector) {
        if (lector == null) {
            throw new IllegalArgumentException("El lector no puede ser null");
        }
        this.lector = lector;
        return this;
    }

    public ProcesadorCsvBuilder conImpresor(ImpresorTabla impresor) {
        if (impresor == null) {
            throw new IllegalArgumentException("El impresor no puede ser null");
        }
        this.impresor = impresor;
        return this;
    }

    public ProcesadorCsvBuilder agregarTransformacion(TransformacionTabla transformacion) {
        if (transformacion == null) {
            throw new IllegalArgumentException("La transformación no puede ser null");
        }
        transformaciones.add(transformacion);
        return this;
    }

    public ProcesadorCsv construir() {
        return new ProcesadorCsv(lector, impresor, transformaciones);
    }
}
