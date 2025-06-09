package uniairlines.excepcion;

public class VueloException extends RuntimeException {
    public VueloException(String message) {
        super(message);
    }

    public VueloException(String message, Throwable cause) {
        super(message, cause);
    }
}
