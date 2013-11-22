/**
    Exception for a failed insertion.
*/
public class InsertionException extends RuntimeException {
    /** Serialization stuff, please ignore. */
    static final long serialVersionUID = 1L;
    /** Create a default exception. */
    InsertionException() { /* nothing to do */ }
    /**
        Create an exception with the specified message.
        @param msg The message.
    */
    InsertionException(String msg) { super(msg); }
}
