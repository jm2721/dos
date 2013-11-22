/**
    Exception for empty tree.
*/
public class EmptyTreeException extends RuntimeException {
    /** Serialization stuff, please ignore. */
    static final long serialVersionUID = 1L;
    /** Create a default exception. */
    EmptyTreeException() { /* nothing to do */ }
    /**
        Create an exception with the specified message.
        @param msg The message.
    */
    EmptyTreeException(String msg) { super(msg); }
}
