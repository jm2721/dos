/**
    Exception for invalid positions.
*/
public class InvalidPositionException extends RuntimeException {
    /** Serialization stuff, please ignore. */
    static final long serialVersionUID = 1L;
    /** Create a default exception. */
    InvalidPositionException() { /* nothing to do */ }
    /**
        Create an exception with the specified message.
        @param msg The message.
    */
    InvalidPositionException(String msg) { super(msg); }
}
