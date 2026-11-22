public abstract class ImpresorDecorador implements ImpresorTabla {

    protected final ImpresorTabla impresor;

    public ImpresorDecorador(ImpresorTabla impresor) {
        if (impresor == null) {
            throw new IllegalArgumentException("El impresor no puede ser null");
        }
        this.impresor = impresor;
    }

    @Override
    public void imprimir(Tabla tabla) {
        impresor.imprimir(tabla);
    }

    protected String armarLinea(int largo, char caracter) {
        String linea = "";
        for (int i = 0; i < largo; i++) {
            linea += caracter;
        }
        return linea;
    }
}