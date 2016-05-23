package net.carpoolme.datastructures;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by John Andersen on 5/23/16.
 */
public class DLLTest {
    public static final String[] TEST_VAR = {"hello"};

    @Test
    public void testSize() throws Exception {
        DLL test = new DLL();
        Assert.assertEquals(0, test.size());
        Assert.assertTrue(test.add(TEST_VAR));
        Assert.assertEquals(1, test.size());
        Assert.assertTrue(test.remove(TEST_VAR));
        Assert.assertEquals(0, test.size());
    }

    @Test
    public void testIsEmpty() throws Exception {
        DLL test = new DLL();
        Assert.assertTrue(test.isEmpty());
        Assert.assertTrue(test.add(TEST_VAR));
        Assert.assertFalse(test.isEmpty());
        Assert.assertTrue(test.remove(TEST_VAR));
        Assert.assertTrue(test.isEmpty());
    }

    @Test
    public void testContains() throws Exception {
        DLL test = new DLL();
        Assert.assertTrue(test.add(TEST_VAR));
        Assert.assertTrue(test.contains(TEST_VAR));
    }

    @Test
    public void testToArray() throws Exception {
        DLL test = new DLL();
        final int size = 10;
        for (int i = 0; i < size; i++) {
            Assert.assertTrue(test.add(new String[] {String.format("%s%d", TEST_VAR[0], i)}));
        }
        Assert.assertEquals(size, test.size());
        Object[] array = test.toArray();
        for (int i = 0; i < size; i++) {
            Assert.assertEquals(((String[]) array[i])[0], String.format("%s%d", TEST_VAR[0], i));
        }
    }

    @Test
    public void testClear() throws Exception {

    }

    @Test
    public void testGet() throws Exception {

    }

    @Test
    public void testSet() throws Exception {

    }

    @Test
    public void testAdd2() throws Exception {

    }

    @Test
    public void testRemove2() throws Exception {

    }

    @Test
    public void testIndexOf() throws Exception {

    }
}