import java.util.Iterator;

/**
    A general n-ary position-based tree.

    Note that this is <strong>one</strong> possible interface, there
    are <strong>lots</strong> of other design options.

    @param <T> Type of elements stored in tree.
*/
public interface Tree<T> extends Iterable<T> {
    /**
        No elements?
        @return True if tree is empty, false otherwise.
    */
    boolean empty();

    /**
        How many elements?
        @return Number of elements in tree.
    */
    int size();

    /**
        Is the given position valid for this tree?
        @param p Position to check.
        @return True if p is valid, false otherwise.
    */
    boolean valid(Position<T> p);

    /**
        Does given position have a parent?
        @param p Position to check.
        @return True if p has parent, false otherwise.
        @throws InvalidPositionException If p not valid.
    */
    boolean hasParent(Position<T> p)
        throws InvalidPositionException;

    /**
        Does given position have children?
        @param p Position to check.
        @return True if p has children, false otherwise.
        @throws InvalidPositionException If p not valid.
    */
    boolean hasChildren(Position<T> p)
        throws InvalidPositionException;

    /**
        Is given position the root of the tree?
        @param p Position to check.
        @return True if p is root of tree, false otherwise.
        @throws InvalidPositionException If p not valid.
    */
    boolean isRoot(Position<T> p)
        throws InvalidPositionException;

    /**
        Insert at the root of the tree.
        @param t Element to store.
        @return Position of new child.
        @throws InsertionException If root exists already.
    */
    Position<T> insertRoot(T t)
        throws InsertionException;

    /**
        Insert new child of given position.
        @param p Position to insert a child for.
        @param t Element to store.
        @return Position of new child.
        @throws InvalidPositionException If p not valid.
    */
    Position<T> insertChild(Position<T> p, T t)
        throws InvalidPositionException;

    /**
        Remove given position.
        @param p Position to remove.
        @return Element stored at p.
        @throws InvalidPositionException If p not valid.
        @throws RemovalException If node has children.
    */
    T removeAt(Position<T> p)
        throws InvalidPositionException, RemovalException;

    /**
        Get the root position of the tree.
        @return Position of tree's root.
        @throws EmptyTreeException If tree was empty.
    */
    Position<T> root()
        throws EmptyTreeException;

    /**
        Return the parent of the given position.
        @param p Position to return parent for.
        @return Position of p's parent.
        @throws InvalidPositionException If p not valid or the root.
    */
    Position<T> parent(Position<T> p)
        throws InvalidPositionException;

    /**
        Return iterable collection of children of the given position.
        @param p Position to return children for.
        @return Iterable with all children.
        @throws InvalidPositionException If p not valid or a leaf.
    */
    Iterable<Position<T>> children(Position<T> p)
        throws InvalidPositionException;

    /**
        Return iterable collection of all positions in the tree.
        @return Iterable with all positions.
    */
    Iterable<Position<T>> positions();

    /**
        Return iterator of all elements stored in the tree.
        @return Iterator of elements.
    */
    Iterator<T> iterator();

    /**
        Traverse the tree performing the given operations as required.

        Note that nothing is called for an empty tree; for a tree with
        several children, in() is called between *every* two children.

        @param o Operation object to run over the tree with.
    */
    void traverse(Operation<T> o);
}
