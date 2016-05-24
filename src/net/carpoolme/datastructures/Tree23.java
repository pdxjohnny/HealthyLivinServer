package net.carpoolme.datastructures;

import java.io.InvalidObjectException;

/**
 * Created by John Andersen on 5/23/16.
 */
public class Tree23 {

    private static final int TREE23_NUM_NODES = 3;

    private static final int TREE23_LEFT = 0;
    private static final int TREE23_RIGHT = 1;
    private static final int TREE23_MIDDLE = 2;

    private Tree23[] nodes = new Tree23[TREE23_NUM_NODES];
    private Object[] data = new Object[TREE23_NUM_NODES - 1];
    private String[] keys = new String[TREE23_NUM_NODES - 1];
    private int contains = 0;

    public Tree23() {
        int i;
        for (i = (TREE23_NUM_NODES - 1); i >= 0; --i) {
            nodes[i] = null;
        }
        setLeft(null, null);
        setRight(null, null);
    }

    public Tree23(final Tree23 copyNode) {
        this();
        copySingle(copyNode);
    }

    public Tree23(final String copyKey, final Object copyData, final int mContains) {
        this();
        contains = mContains;
        setLeft(copyKey, copyData);
    }

    // Sets the left to the data and marks the right as inactive
    private Tree23 set(final String setKey, final Object setData) {
        setLeft(setKey, setData);
        setRight(null, null);
        return this;
    }

    // Sets the left to the data and marks the right as inactive
    private Tree23 setLeft(final String setKey, final Object setData) {
        data[TREE23_LEFT] = setData;
        keys[TREE23_LEFT] = setKey;
        return this;
    }

    // Sets the left to the data and marks the right as inactive
    private Tree23 setRight(final String setKey, final Object setData) {
        data[TREE23_RIGHT] = setData;
        keys[TREE23_RIGHT] = setKey;
        return this;
    }

    private void copySingle(final Tree23 copyNode) {
        if (copyNode == null || copyNode == this) {
            return;
        }
        contains = copyNode.contains;
        setLeft(copyNode.keys[TREE23_LEFT], copyNode.data[TREE23_LEFT]);
        setRight(copyNode.keys[TREE23_RIGHT], copyNode.data[TREE23_RIGHT]);
    }

    /**
     * Copies the contents of copyTree into the tree is was called on
     * @param copyTree The tree to copy from
     */
    public void copy(final Tree23 copyTree) {
        int i;
        if (copyTree == null || copyTree == this) {
            return;
        }
        copySingle(copyTree);
        for (i = (TREE23_NUM_NODES - 1); i >= 0; --i) {
            if (copyTree.nodes[i] != null) {
                nodes[i] = new Tree23();
                nodes[i].copy(copyTree.nodes[i]);
            }
        }
    }

    public Tree23 add(final String addKey, final Object to_add) throws InvalidObjectException {
        return addToTree(addKey, to_add);
    }

    private Tree23 addToTree(final String addKey, final Object addData) throws InvalidObjectException {
        if (addData == null) {
            throw new InvalidObjectException("Cannot add a null object");
        }
        ++contains;
        Tree23 send_up = null;
        // Neither are active, setLeft the left
        if (data[TREE23_LEFT] == null && data[TREE23_RIGHT] == null) {
            setLeft(addKey, addData);
        } else if (nodes[TREE23_LEFT] == null && nodes[TREE23_MIDDLE] == null &&
                nodes[TREE23_RIGHT] == null) {
            if (data[TREE23_LEFT] != null && data[TREE23_RIGHT] == null) {
                if (0 > addKey.compareTo(keys[TREE23_LEFT])) {
                    // Left is active and less than left
                    setRight(keys[TREE23_LEFT], data[TREE23_RIGHT]);
                    setLeft(addKey, addData);
                } else {
                    // Left is active and greater than or equal to left
                    setRight(addKey, addData);
                }
            } else if (data[TREE23_LEFT] != null && data[TREE23_RIGHT] != null) {
                send_up = this;
                // Both active choose who to send up
                if (0 > addKey.compareTo(keys[TREE23_LEFT])) {
                    // Less than left, left is middle send up left
                    nodes[TREE23_LEFT] = new Tree23(addKey, addData, contains);
                    nodes[TREE23_RIGHT] = new Tree23(keys[TREE23_RIGHT], data[TREE23_RIGHT], contains);
                    setRight(null, null);
                } else if (0 <= addKey.compareTo(keys[TREE23_RIGHT])) {
                    // Greater than or equal to the right, right is middle send up right
                    nodes[TREE23_LEFT] = new Tree23(keys[TREE23_LEFT], data[TREE23_LEFT], contains);
                    nodes[TREE23_RIGHT] = new Tree23(addKey, addData, contains);
                    set(keys[TREE23_RIGHT], data[TREE23_RIGHT]);
                } else {
                    // Data to add is in between left and right it is the middle so
                    // send it up
                    nodes[TREE23_LEFT] = new Tree23(keys[TREE23_LEFT], data[TREE23_LEFT], contains);
                    nodes[TREE23_RIGHT] = new Tree23(keys[TREE23_RIGHT], data[TREE23_RIGHT], contains);
                    set(addKey, addData);
                }
            }
        } else {
            if (data[TREE23_LEFT] != null && data[TREE23_RIGHT] == null &&
                    nodes[TREE23_LEFT] != null && nodes[TREE23_RIGHT] != null) {
                // Left is active right is not
                if (0 > addKey.compareTo(keys[TREE23_LEFT])) {
                    // Left is active and less than left
                    send_up = push_up(nodes[TREE23_LEFT].add(addKey, addData));
                } else {
                    // Left is active and greater than or equal to left
                    send_up = push_up(nodes[TREE23_RIGHT].add(addKey, addData));
                }
            } else if (data[TREE23_LEFT] != null && data[TREE23_RIGHT] != null &&
                    nodes[TREE23_LEFT] != null && nodes[TREE23_MIDDLE] != null &&
                    nodes[TREE23_RIGHT] != null) {
                // Both active so we have a middle because we are not a leaf
                if (0 > addKey.compareTo(keys[TREE23_LEFT])) {
                    // Both active and less than left
                    send_up = push_up(nodes[TREE23_LEFT].add(addKey, addData));
                } else if (0 <= addKey.compareTo(keys[TREE23_RIGHT])) {
                    // Both active and greater than or equal to right
                    send_up = push_up(nodes[TREE23_RIGHT].add(addKey, addData));
                } else {
                    // Both active and in between left and right
                    send_up = push_up(nodes[TREE23_MIDDLE].add(addKey, addData));
                }
            }
        }
        return send_up;
    }

    // If add returns a node then it is pushing that node up. This handles the
    // pushing up of a node
    private Tree23 push_up(Tree23 pushed_up) {
        Tree23 send_up = null;
        // Nothing pushed up
        if (pushed_up == null) {
            return null;
        }
        if (this == pushed_up) {
            return null;
        }
        // If the child was full and I am full
        if (data[TREE23_LEFT] != null && data[TREE23_RIGHT] != null) {
            send_up = this;
            // Both active choose who to send up
            if (0 > pushed_up.keys[TREE23_LEFT].compareTo(keys[TREE23_LEFT])) {
                // Less than left, left is middle send up left
                Tree23 new_right = new Tree23(keys[TREE23_RIGHT], data[TREE23_RIGHT], contains);
                new_right.nodes[TREE23_RIGHT] = nodes[TREE23_RIGHT];
                new_right.nodes[TREE23_LEFT] = nodes[TREE23_MIDDLE];
                setRight(null, null);
                nodes[TREE23_LEFT] = pushed_up;
                nodes[TREE23_RIGHT] = new_right;
                nodes[TREE23_MIDDLE] = null;
            } else if (0 <= pushed_up.keys[TREE23_LEFT].compareTo(keys[TREE23_RIGHT])) {
                // Greater than or equal to the right, right is middle send up right
                Tree23 new_left = new Tree23(keys[TREE23_LEFT], data[TREE23_LEFT], contains);
                new_left.nodes[TREE23_LEFT] = nodes[TREE23_LEFT];
                new_left.nodes[TREE23_RIGHT] = nodes[TREE23_MIDDLE];
                set(keys[TREE23_RIGHT], data[TREE23_RIGHT]);
                nodes[TREE23_LEFT] = new_left;
                nodes[TREE23_RIGHT] = pushed_up;
                nodes[TREE23_MIDDLE] = null;
            } else {
                // Data to add is in between left and right it is the middle so
                // send it up
                Tree23 new_left = new Tree23(keys[TREE23_LEFT], data[TREE23_LEFT], contains);
                Tree23 new_right = new Tree23(keys[TREE23_RIGHT], data[TREE23_RIGHT], contains);
                new_left.nodes[TREE23_LEFT] = nodes[TREE23_LEFT];
                new_left.nodes[TREE23_RIGHT] = pushed_up.nodes[TREE23_LEFT];
                new_right.nodes[TREE23_RIGHT] = nodes[TREE23_RIGHT];
                new_right.nodes[TREE23_LEFT] = pushed_up.nodes[TREE23_RIGHT];
                set(pushed_up.keys[TREE23_LEFT], pushed_up.data[TREE23_LEFT]);
                nodes[TREE23_LEFT] = new_left;
                nodes[TREE23_RIGHT] = new_right;
                nodes[TREE23_MIDDLE] = null;
            }
            // Data pushed up goes into the left spot
        } else if (0 > pushed_up.keys[TREE23_LEFT].compareTo(keys[TREE23_LEFT])) {
            setRight(keys[TREE23_LEFT], data[TREE23_LEFT]);
            setLeft(pushed_up.keys[TREE23_LEFT], pushed_up.data[TREE23_LEFT]);
            nodes[TREE23_LEFT] = pushed_up.nodes[TREE23_LEFT];
            nodes[TREE23_MIDDLE] = pushed_up.nodes[TREE23_RIGHT];
            // Data pushed up goes in the right spot
        } else {
            setRight(pushed_up.keys[TREE23_LEFT], pushed_up.data[TREE23_LEFT]);
            nodes[TREE23_RIGHT] = pushed_up.nodes[TREE23_RIGHT];
            nodes[TREE23_MIDDLE] = pushed_up.nodes[TREE23_LEFT];
        }
        return send_up;
    }

    public Object get(String searchKey) throws IndexOutOfBoundsException {
        if (searchKey == null) {
            return null;
        }
        // Neither are active, setLeft the left
        if (keys[TREE23_LEFT] != null && keys[TREE23_LEFT].equals(searchKey)) {
            return data[TREE23_LEFT];
        } else if (keys[TREE23_RIGHT] != null && keys[TREE23_RIGHT].equals(searchKey)) {
            return data[TREE23_RIGHT];
        }
        if (nodes[TREE23_LEFT] != null && nodes[TREE23_MIDDLE] != null
                && nodes[TREE23_RIGHT] != null && keys[TREE23_LEFT] != null &&
                keys[TREE23_RIGHT] != null) {
            // Both active so we have a middle because we are not a leaf
            if (0 > searchKey.compareTo(keys[TREE23_LEFT])) {
                // Both active and less than left
                return nodes[TREE23_LEFT].get(searchKey);
            } else if (0 <= searchKey.compareTo(keys[TREE23_RIGHT])) {
                // Both active and greater than or equal to right
                return nodes[TREE23_RIGHT].get(searchKey);
            } else {
                // Both active and in between left and right
                return nodes[TREE23_MIDDLE].get(searchKey);
            }
        }
        if (nodes[TREE23_LEFT] != null && nodes[TREE23_RIGHT] != null &&
                keys[TREE23_LEFT] != null) {
            // Left is active right is not
            if (0 > searchKey.compareTo(keys[TREE23_LEFT])) {
                // Left is active and less than left
                return nodes[TREE23_LEFT].get(searchKey);
            } else {
                // Left is active and greater than or equal to left
                return nodes[TREE23_RIGHT].get(searchKey);
            }
        }
        throw new IndexOutOfBoundsException(String.format("Key: \'%s\' not found", searchKey));
    }

    public String key(int index) throws IndexOutOfBoundsException {
        int curr = 0;
        return getCount(index, new int[] {0}).keys[TREE23_LEFT];
    }

    public Object value(int index) throws IndexOutOfBoundsException {
        int curr = 0;
        return getCount(index, new int[] {0}).data[TREE23_LEFT];
    }

    /*
     * Attempts to get a node at the given index and counts along the way
     */
    private Tree23 getCount(int index, int [] curr) throws IndexOutOfBoundsException {
        // To see if one of the sides found it
        Tree23 found;
        // Look of the index on the left, that will be index 0 if its the leftmost node
        if (nodes[TREE23_LEFT] != null) {
            try {
                found = nodes[TREE23_LEFT].getCount(index, curr);
                return found;
            } catch (IndexOutOfBoundsException ignored) {}
        }
        // Check if this node is the index
        if (data[TREE23_LEFT] != null) {
            if (index == curr[0]) {
                return new Tree23(keys[TREE23_LEFT], data[TREE23_LEFT], 1);
            }
            // increment the index
            ++curr[0];
        }
        // Look of the index down the middle
        if (nodes[TREE23_MIDDLE] != null) {
            try {
                found = nodes[TREE23_MIDDLE].getCount(index, curr);
                return found;
            } catch (IndexOutOfBoundsException ignored) {}
        }
        // Check if this node is the index
        if (data[TREE23_RIGHT] != null) {
            if (index == curr[0]) {
                return new Tree23(keys[TREE23_RIGHT], data[TREE23_RIGHT], 1);
            }
            // increment the index
            ++curr[0];
        }
        // Look of the index on the right
        if (nodes[TREE23_RIGHT] != null) {
            try {
                found = nodes[TREE23_RIGHT].getCount(index, curr);
                return found;
            } catch (IndexOutOfBoundsException ignored) {}
        }
        throw new IndexOutOfBoundsException();
    }

    // Everything in tree to string
    @Override
    public String toString() {
        String asString = "";
        int i;
        for (i = 0; i < contains; ++i) {
            asString += value(i).toString();
        }
        return asString;
    }

    // Returns contains
    public int size(){
        return contains;
    }
}
