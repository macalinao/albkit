package pw.ian.albkit.reflect;

/**
 *
 * @author ian
 */
public class ReflectException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ReflectException() {
        super();
    }

    public ReflectException(String message) {
        super(message);
    }

    public ReflectException(Throwable cause) {
        super(cause);
    }

    public ReflectException(String message, Throwable cause) {
        super(message, cause);
    }
}
