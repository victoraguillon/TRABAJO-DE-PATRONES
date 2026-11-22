public class ImpresorConAsteriscos extends ImpresorDecorador {

    private static final String LINEA = "********************************************";

    public ImpresorConAsteriscos(ImpresorTabla impresor) {
        super(impresor);
    }

    @Override
    public void imprimir(Tabla tabla) {
        System.out.println(LINEA);
        super.imprimir(tabla);
        System.out.println(LINEA);
    }
}