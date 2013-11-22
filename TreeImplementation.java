import java.util.Iterator;
import java.util.ArrayList;

/** Implementation of a tree.
    @param <T> the type of data in the tree
*/
public class TreeImplementation<T> implements Tree<T> {

    private static class Op<T> extends Operation<T> {
        String tostring = " ";
        public void pre(Position<T> p) {
            this.tostring += p.get() + " ";
        }
    }
    private static class Node<T> implements Position<T> {
        ArrayList<Node<T>> children;
        Node<T> parent;
        T data;
        Tree<T> color;

        Node(T t, Tree<T> newcolor) {
            this.data = t;
            this.color = newcolor;
            this.children = new ArrayList<Node<T>>();
        }

        public T get() {
            return this.data;
        }

        public void put(T t) {
            this.data = t;
        }

        public ArrayList<Node<T>> getChildren() {
            return this.children;
        }
    }

    private Node<T> root;
    private ArrayList<Node<T>> nodes = new ArrayList<Node<T>>();

    @Override
    public boolean empty() {
        return this.root == null;
    }

    @Override
    public int size() {
        return this.nodes.size();
    }

    @Override
    public boolean valid(Position<T> p) {
        try {
            this.convert(p);
        } catch (InvalidPositionException i) {
            return false;
        }
        return true;
    }

    @Override
    public boolean hasParent(Position<T> p)
        throws InvalidPositionException {
        // isRoot can throw the InvalidPositionException
        if (this.isRoot(p)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean hasChildren(Position<T> p)
        throws InvalidPositionException {
        Node<T> n = this.convert(p);
        if (n.children.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isRoot(Position<T> p)
        throws InvalidPositionException {
        if (this.convert(p) == this.root) {
            return true;
        }
        return false;
    }
    @Override
    public Position<T> insertRoot(T t)
        throws InsertionException {
        if (this.root == null) {
            this.root = new Node<T>(t, this);
            this.nodes.add(this.root);
            return this.root;
        }
        throw new InsertionException();
    }

    @Override
    public Position<T> insertChild(Position<T> p, T t)
        throws InvalidPositionException {
        Node<T> n = this.convert(p);
        Node<T> child = new Node<T>(t, this);
        this.nodes.add(child);
        n.children.add(child);
        child.parent = n;
        return child;
    }

    @Override
    public T removeAt(Position<T> p)
        throws InvalidPositionException, RemovalException {
        Node<T> n = this.convert(p);
        T d = n.data;
        if (this.hasChildren(p)) {
            throw new RemovalException();
        }
        this.nodes.remove(n);

        n.parent.children.remove(n);
        return d;
    }

    @Override
    public Position<T> root()
        throws EmptyTreeException {
        if (this.root == null) {
            throw new EmptyTreeException();
        }
        return this.root;
    }

    @Override
    public Position<T> parent(Position<T> p)
        throws InvalidPositionException {
        return this.convert(p).parent;
    }

    private Node<T> convert(Position<T> p) {
        Node<T> n;
        if (p == null || !(p instanceof Node<?>)) {
            throw new InvalidPositionException();
        }
        n = (Node<T>) p;
        if (n.color != this) {
            throw new InvalidPositionException();
        }

        return n;
    }

    @Override
    public Iterable<Position<T>> children(Position<T> p)
        throws InvalidPositionException {
        ArrayList<Node<T>> children = this.convert(p).children;
        ArrayList<Position<T>> childpositions = new ArrayList<Position<T>>();
        for (Node<T> n : children) {
            childpositions.add(n);
        }
        return childpositions;
    }

    @Override
    public Iterable<Position<T>> positions() {
        ArrayList<Position<T>> positions = new ArrayList<Position<T>>();
        for (Node<T> n : this.nodes) {
            Position<T> p = n;
            positions.add(p);
        }
        return positions;
    }

    @Override
    public Iterator<T> iterator() {
        ArrayList<T> data = new ArrayList<T>();
        for (Node<T> n : this.nodes) {
            data.add(n.data);
        }
        Iterator<T> iter = data.iterator();
        return iter;
    }

    @Override
    public void traverse(Operation<T> o) {
        this.recurse(this.root, o);
    }

    private void recurse(Node<T> n, Operation<T> op) {
        op.pre(n);
        if (this.hasChildren(n)) {
            for (int i = 0; i < n.children.size() - 1; i++) {
                this.recurse(n.children.get(i), op);
                op.in(n);
            }
            if (n.children.size() == 1) {
                op.in(n);
            }
            this.recurse(n.children.get(n.children.size() - 1), op);
        } else {
            op.in(n);
        }
        op.post(n);
    }

    /** Give a string representation of the tree.
        @return t the string representation of the tree
    */
    public String toString() {
        String t = "[";

        Op<T> o = new Op<T>();
        this.traverse(o);
        t += o.tostring;

        t += "]";
        return t;
    }
}
