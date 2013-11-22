/**
    Generic operations to perform during a tree traversal.
    @param <T> Type of element in tree.
*/
public abstract class Operation<T> {
    /**
        Operation to perform before processing children.
        @param p Position to perform operation on.
    */
    public void pre(Position<T> p) { /* clients can override */ }
    /**
        Operation to perform between processing two children.
        @param p Position to perform operation on.
    */
    public void in(Position<T> p) { /* clients can override */ }
    /**
        Operation to perform after processing children.
        @param p Position to perform operation on.
    */
    public void post(Position<T> p) { /* clients can override */ }
}
