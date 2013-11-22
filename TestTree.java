import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.Test;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class TestTree {

    private interface Fixture {
        Tree<String> init();
    }

    @DataPoint
    public static final Fixture tree = new Fixture() {
        public Tree<String> init() {
            return new TreeImplementation<>();
        }
    };

    @Theory
    public void newTreeIsEmpty(Fixture fix) {
        Tree<String> tree = fix.init();
        assertTrue(tree.empty());
        assertEquals(0, tree.size());
    }

    @Theory @Test(expected=EmptyTreeException.class)
    public void rootThrowsEmptyTreeException(Fixture fix) {
        Tree<String> tree = fix.init();
        tree.root();
    }
    @Theory
    public void insertRootWorks(Fixture fix) {
        Tree<String> tree = fix.init();
        Position<String> p = tree.insertRoot("root");
        assertEquals(tree.root(), p);
        // Check the isRoot method
        assertTrue(tree.isRoot(p));

        // Check it is a valid position
        assertTrue(tree.valid(p));

        // Check is has no parent
        assertFalse(tree.hasParent(p));

        // Check it has no children
        assertFalse(tree.hasChildren(p));
    }
    
    @Theory
    public void insertChildrenWorks(Fixture fix) {
        Tree<String> tree = fix.init();
        Position<String> p = tree.insertRoot("root");
        assertEquals(tree.root(), p);
        Position<String> c = tree.insertChild(p, "child");
        ArrayList<Position<String>> children = (ArrayList<Position<String>>) tree.children(p);
        assertTrue(children.contains(c));
    }
    
    @Theory @Test(expected=InsertionException.class)
    public void rootThrowsExceptionIfAlreadyExists(Fixture fix) {
        Tree<String> tree = fix.init();
        Position<String> p = tree.insertRoot("root");
        Position<String> otherp = tree.insertRoot("otherroot");
    }
   
    public void validMethodWorks(Fixture fix) {
        Tree<String> tree = fix.init();
        Tree<String> othertree = new TreeImplementation<String>();
        Position<String> othertreeroot = othertree.insertRoot("root");
        assertFalse(tree.valid(null));

        // Check that position from other tree is invalid
        assertFalse(tree.valid(othertreeroot));
    }
    
    @Theory
    public void testRemovalWorks(Fixture fix) {
        Tree<String> tree = fix.init();
        Position<String> p = tree.insertRoot("root");
        Position<String> a = tree.insertChild(p, "a");
        Position<String> b = tree.insertChild(p, "b");
        tree.removeAt(a);
        ArrayList<Position<String>> children = (ArrayList<Position<String>>) tree.children(p);
        assertFalse(children.contains(a));
    }
    @Theory
    public void testToString(Fixture fix) {
        //This in turn checks traverse, since
        // toString calls a traverse with pre
        // order operation to get the string.
        Tree<String> tree = fix.init();
        Position<String> r = tree.insertRoot("root");
        Position<String> c1 = tree.insertChild(r, "child1");
        Position<String> c2 = tree.insertChild(r, "child2");
        tree.insertChild(c2, "childofchild2");
        // The second is the preorder traversal of the tree.
        assertEquals(tree.toString(), "[ root child1 child2 childofchild2 ]");
    }
}
