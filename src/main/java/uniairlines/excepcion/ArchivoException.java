package uniairlines.excepcion;

public class ArchivoException extends Exception {

    // Constructor solo con mensaje
    public ArchivoException(String mensaje) {
        super(mensaje);
    }

    // Constructor con mensaje y causa
    public ArchivoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
