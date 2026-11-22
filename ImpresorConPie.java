public class ImpresorConPie extends ImpresorDecorador {

    private final String pie;

    public ImpresorConPie(ImpresorTabla impresor, String pie) {
        super(impresor);
        if (pie == null || pie.isEmpty()) {
            throw new IllegalArgumentException("El pie no puede estar vacío");
        }
        this.pie = pie;
    }

    @Override
    public void imprimir(Tabla tabla) {
        super.imprimir(tabla);
        System.out.println(armarLinea(pie.length(), '-'));
        System.out.println(pie);
    }
}
