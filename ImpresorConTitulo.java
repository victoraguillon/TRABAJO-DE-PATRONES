public class ImpresorConTitulo extends ImpresorDecorador {

    private final String titulo;

    public ImpresorConTitulo(ImpresorTabla impresor, String titulo) {
        super(impresor);
        if (titulo == null || titulo.isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        this.titulo = titulo;
    }

    @Override
    public void imprimir(Tabla tabla) {
        System.out.println(titulo);
        System.out.println(armarLinea(titulo.length(), '='));
        super.imprimir(tabla);
    }
}