/**
    Exception for a failed removal.

    For example, this would be raised if we try to remove a position
    that has children.
*/
public class RemovalException extends RuntimeException {
    /** Serialization stuff, please ignore. */
    static final long serialVersionUID = 1L;
    /** Create a default exception. */
    RemovalException() { /* nothing to do */ }
    /**
        Create an exception with the specified message.
        @param msg The message.
    */
    RemovalException(String msg) { super(msg); }
}
