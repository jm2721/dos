/**
    Generic positions.
    @param <T> Type of elements in positions.
*/
public interface Position<T> {
    /**
        Update the element at this position.
        @param t Value to set element to.
    */
    void put(T t);
    /**
        Return the element at this position.
        @return Value from element.
    */
    T get();
}
