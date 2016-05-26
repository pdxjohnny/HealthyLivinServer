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
        DLL test = new DLL<String[]>();
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
    public void testToArrayCorrectType() throws Exception {
        DLL<String> test = new DLL<String>();
        final int size = 10;
        for (int i = 0; i < size; i++) {
            Assert.assertTrue(test.add(String.format("%s%d", TEST_VAR[0], i)));
        }
        Assert.assertEquals(size, test.size());
        String[] array = test.toArray(new String[test.size()]);
        for (int i = 0; i < size; i++) {
            Assert.assertEquals(array[i], String.format("%s%d", TEST_VAR[0], i));
        }
    }

    @Test
    public void testClear() throws Exception {
        DLL test = new DLL();
        final int size = 10;
        for (int i = 0; i < size; i++) {
            Assert.assertTrue(test.add(new String[] {String.format("%s%d", TEST_VAR[0], i)}));
        }
        test.clear();
        Assert.assertEquals(0, test.size());
    }

    @Test
    public void testGet() throws Exception {
        DLL test = new DLL();
        final int size = 10;
        for (int i = 0; i < size; i++) {
            Assert.assertTrue(test.add(new String[] {String.format("%s%d", TEST_VAR[0], i)}));
        }
        Assert.assertEquals(size, test.size());
        for (int i = 0; i < size; i++) {
            Assert.assertEquals(((String[]) test.get(i))[0], String.format("%s%d", TEST_VAR[0], i));
        }
    }

    @Test
    public void testSet() throws Exception {
        DLL test = new DLL();
        final int size = 10;
        for (int i = 0; i < size; i++) {
            Assert.assertTrue(test.add(String.format("%s%d", TEST_VAR[0], i)));
        }
        Assert.assertEquals(size, test.size());
        for (int i = 0; i < size; i++) {
            String newValue = String.format("%s%d", TEST_VAR[0], i*i);
            Assert.assertEquals(test.get(i), String.format("%s%d", TEST_VAR[0], i));
            Assert.assertEquals(test.set(i, newValue), newValue);
            Assert.assertEquals(test.get(i), newValue);
        }
    }

    @Test
    public void testIndexOf() throws Exception {
        DLL test = new DLL();
        final int size = 10;
        for (int i = 0; i < size; i++) {
            Assert.assertTrue(test.add(String.format("%s%d", TEST_VAR[0], i)));
        }
        Assert.assertEquals(size, test.size());
        for (int i = 0; i < size; i++) {
            Assert.assertEquals(i, test.indexOf(String.format("%s%d", TEST_VAR[0], i)));
        }
    }
}