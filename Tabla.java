import java.util.ArrayList;
import java.util.List;

public class Tabla {

    private final List<String> encabezados;
    private final List<List<String>> filas;

    public Tabla(List<String> encabezados) {
        if (encabezados == null || encabezados.isEmpty()) {
            throw new IllegalArgumentException("La tabla necesita al menos una columna");
        }
        this.encabezados = new ArrayList<>();
        for (String encabezado : encabezados) {
            this.encabezados.add(normalizar(encabezado));
        }
        this.filas = new ArrayList<>();
    }

    public int getCantidadFilas() {
        return filas.size();
    }

    public int getCantidadColumnas() {
        return encabezados.size();
    }

    public String getEncabezado(int columna) {
        validarColumna(columna);
        return encabezados.get(columna);
    }

    public void setEncabezado(int columna, String valor) {
        validarColumna(columna);
        encabezados.set(columna, normalizar(valor));
    }

    public String getValor(int fila, int columna) {
        validarFila(fila);
        validarColumna(columna);
        return filas.get(fila).get(columna);
    }

    public void setValor(int fila, int columna, String valor) {
        validarFila(fila);
        validarColumna(columna);
        filas.get(fila).set(columna, normalizar(valor));
    }

    public List<String> getFila(int fila) {
        validarFila(fila);
        return new ArrayList<>(filas.get(fila));
    }

    public void agregarFila(List<String> valores) {
        if (valores == null) {
            throw new IllegalArgumentException("La fila no puede ser null");
        }
        if (valores.size() != getCantidadColumnas()) {
            throw new IllegalArgumentException("La fila debe tener " + getCantidadColumnas()
                    + " valores y tiene " + valores.size());
        }
        List<String> copia = new ArrayList<>();
        for (String valor : valores) {
            copia.add(normalizar(valor));
        }
        filas.add(copia);
    }

    public void quitarFila(int indice) {
        validarFila(indice);
        filas.remove(indice);
    }

    public int buscarColumna(String nombre) {
        if (nombre == null) {
            return -1;
        }
        for (int columna = 0; columna < encabezados.size(); columna++) {
            if (encabezados.get(columna).equals(nombre)) {
                return columna;
            }
        }
        return -1;
    }

    public void agregarColumna(String nombre, String valorPorDefecto) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre de la columna no puede estar vacío");
        }
        if (buscarColumna(nombre) != -1) {
            throw new IllegalArgumentException("Ya existe la columna: " + nombre);
        }
        String valor = normalizar(valorPorDefecto);
        encabezados.add(nombre);
        for (List<String> fila : filas) {
            fila.add(valor);
        }
    }

    public void quitarColumna(String nombre) {
        int columna = buscarColumna(nombre);
        if (columna == -1) {
            throw new IllegalArgumentException("No existe la columna: " + nombre);
        }
        if (getCantidadColumnas() == 1) {
            throw new IllegalArgumentException("No se puede quitar la última columna");
        }
        encabezados.remove(columna);
        for (List<String> fila : filas) {
            fila.remove(columna);
        }
    }

    public boolean estaVacia() {
        return filas.isEmpty();
    }

    @Override
    public String toString() {
        return "Tabla(" + getCantidadFilas() + " filas x " + getCantidadColumnas() + " columnas)";
    }

    private void validarFila(int fila) {
        if (fila < 0 || fila >= filas.size()) {
            throw new IllegalArgumentException("Fila fuera de rango: " + fila);
        }
    }

    private void validarColumna(int columna) {
        if (columna < 0 || columna >= encabezados.size()) {
            throw new IllegalArgumentException("Columna fuera de rango: " + columna);
        }
    }

    private String normalizar(String valor) {
        if (valor == null) {
            return "";
        }
        return valor;
    }
}
