package net.carpoolme.datastructures;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by John Andersen on 5/23/16.
 */
public class Tree23Test {
    private SecureRandom random = new SecureRandom();
    private String randomString() {
        return (new BigInteger(130, random)).toString(32);
    }

    @Test
    public void testCopy() throws Exception {
        Tree23 test = new Tree23();
        final int testSize = 100;
        String[] testValues = new String[testSize];
        String checkValue;
        for (int i = 0; i < testSize; ++i) {
            testValues[i] = randomString();
            test.add(String.format("%010d", i), testValues[i]);
        }
        Tree23 copy = new Tree23(test);
        Assert.assertEquals(testSize, copy.size());
        for (int i = 0; i < testSize; ++i) {
            checkValue = (String) copy.get(String.format("%010d", i));
            Assert.assertEquals(testValues[i], checkValue);
        }
    }

    @Test
    public void testAdd() throws Exception {
        Tree23 test = new Tree23();
        Tree23 subTest;
        final int testSize = 100;
        for (int i = 0; i < testSize; ++i) {
            subTest = new Tree23();
            for (int j = 0; j < testSize; ++j) {
                subTest.add(randomString(), randomString());
            }
            test.add(randomString(), subTest);
            Assert.assertEquals(testSize, subTest.size());
        }
        Assert.assertEquals(testSize, test.size());
    }

    @Test
    public void testKey() throws Exception {

    }

    @Test
    public void testValue() throws Exception {

    }

    @Test
    public void testGet() throws Exception {
        Tree23 test = new Tree23();
        final int testSize = 100;
        String[] testValues = new String[testSize];
        String checkValue;
        for (int i = 0; i < testSize; ++i) {
            testValues[i] = randomString();
            test.add(String.format("%010d", i), testValues[i]);
        }
        Assert.assertEquals(testSize, test.size());
        for (int i = 0; i < testSize; ++i) {
            checkValue = (String) test.get(String.format("%010d", i));
            Assert.assertEquals(testValues[i], checkValue);
        }
    }

    @Test
    public void testGetLarge() throws Exception {
        Tree23 test = new Tree23();
        Tree23 subTest;
        final int testSize = 100;
        String[] testKeys = new String[testSize];
        String[][] subTestKeys = new String[testSize][testSize];
        String[][] subTestValues = new String[testSize][testSize];
        for (int i = 0; i < testSize; ++i) {
            subTest = new Tree23();
            for (int j = 0; j < testSize; ++j) {
                subTestKeys[i][j] = randomString();
                subTestValues[i][j] = randomString();
                subTest.add(subTestKeys[i][j], subTestValues[i][j]);
            }
            testKeys[i] = randomString();
            test.add(testKeys[i], subTest);
            Assert.assertEquals(testSize, subTest.size());
        }
        Assert.assertEquals(testSize, test.size());
        for (int i = 0; i < testSize; ++i) {
            subTest = (Tree23) test.get(testKeys[i]);
            Assert.assertNotEquals(subTest, null);
            for (int j = 0; j < testSize; ++j) {
                String checkSubTestValue = (String) subTest.get(subTestKeys[i][j]);
                Assert.assertNotEquals(subTest, null);
                Assert.assertEquals(subTestValues[i][j], checkSubTestValue);
            }
        }
    }

    @Test
    public void testGetAll() throws Exception {
        Tree23 test = new Tree23();
        final int testSize = 100;
        int numAdded = 0;
        String[] testValues = new String[testSize];
        String checkValue;
        for (int i = 0; i < testSize; ++i) {
            if (i % 5 == 0) {
                testValues[i] = "8086";
                test.add(testValues[i], String.format("%010d", i));
                ++numAdded;
            } else {
                testValues[i] = randomString();
                test.add(String.format("%010d", i), testValues[i]);
            }
        }
        Assert.assertEquals(testSize, test.size());
        for (int i = 0; i < testSize; ++i) {
            if (i % 5 != 0) {
                checkValue = (String) test.get(String.format("%010d", i));
                Assert.assertEquals(testValues[i], checkValue);
            }
        }
        Tree23 testAllMatching = test.getAll("8086");
        Assert.assertEquals(numAdded, testAllMatching.size());
        int j = 0;
        for (int i = 0; i < testSize; ++i) {
            if (i % 5 == 0) {
                checkValue = (String) testAllMatching.value(j);
                Assert.assertEquals(String.format("%010d", i), checkValue);
                ++j;
            }
        }
    }

    @Test
    public void testToString() throws Exception {

    }

    @Test
    public void testSize() throws Exception {

    }
}