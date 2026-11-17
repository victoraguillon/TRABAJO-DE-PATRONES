import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LectorCsvSimple implements LectorCsv {

    private static final char SEPARADOR_POR_DEFECTO = ',';
    private static final char COMILLA = '"';
    private static final char BOM = '\uFEFF';

    private final char separador;

    public LectorCsvSimple() {
        this(SEPARADOR_POR_DEFECTO);
    }

    public LectorCsvSimple(char separador) {
        this.separador = separador;
    }

    @Override
    public Tabla leer(String ruta) {
        if (ruta == null || ruta.isEmpty()) {
            throw new IllegalArgumentException("La ruta no puede estar vacía");
        }

        List<String> lineas;
        try {
            lineas = Files.readAllLines(Paths.get(ruta), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo leer el archivo: " + ruta, e);
        }

        if (lineas.isEmpty() || lineas.get(0).trim().isEmpty()) {
            throw new IllegalStateException("El archivo está vacío: " + ruta);
        }

        Tabla tabla = new Tabla(separarCampos(quitarBom(lineas.get(0))));

        for (int i = 1; i < lineas.size(); i++) {
            String linea = lineas.get(i);
            if (linea.trim().isEmpty()) {
                continue;
            }
            List<String> campos = separarCampos(linea);
            tabla.agregarFila(ajustarCampos(campos, tabla.getCantidadColumnas()));
        }

        return tabla;
    }

    private List<String> separarCampos(String linea) {
        List<String> campos = new ArrayList<>();
        StringBuilder campo = new StringBuilder();
        boolean entreComillas = false;

        for (int i = 0; i < linea.length(); i++) {
            char caracter = linea.charAt(i);

            if (caracter == COMILLA) {
                // Una comilla doble dentro de un campo entrecomillado se escapa duplicándola.
                if (entreComillas && i + 1 < linea.length() && linea.charAt(i + 1) == COMILLA) {
                    campo.append(COMILLA);
                    i++;
                } else {
                    entreComillas = !entreComillas;
                }
            } else if (caracter == separador && !entreComillas) {
                campos.add(campo.toString());
                campo.setLength(0);
            } else {
                campo.append(caracter);
            }
        }

        campos.add(campo.toString());
        return campos;
    }

    private List<String> ajustarCampos(List<String> campos, int cantidadColumnas) {
        List<String> ajustados = new ArrayList<>();
        for (int columna = 0; columna < cantidadColumnas; columna++) {
            if (columna < campos.size()) {
                ajustados.add(campos.get(columna));
            } else {
                ajustados.add("");
            }
        }
        return ajustados;
    }

    private String quitarBom(String linea) {
        if (linea.isEmpty() || linea.charAt(0) != BOM) {
            return linea;
        }
        return linea.substring(1);
    }
}
