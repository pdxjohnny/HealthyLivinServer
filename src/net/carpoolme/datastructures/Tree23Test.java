package net.carpoolme.datastructures;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by John Andersen on 5/23/16.
 */
public class Tree23Test {
    private static final String TEST_KEY_1 = "a";
    private static final String TEST_KEY_2 = "b";
    private static final String TEST_KEY_3 = "c";
    private static final String TEST_KEY_4 = "d";

    private String randomString() {
        return (new BigInteger(130, new SecureRandom())).toString(32);
    }

    @Test
    public void testCopy() throws Exception {

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
            subTest = new Tree23();
            for (int j = 0; j < testSize; ++j) {
                String checkSubTestValue = (String) subTest.get(subTestKeys[i][j]);
                Assert.assertEquals(subTestValues[i][j], checkSubTestValue);
            }
            Assert.assertEquals(testSize, subTest.size());
        }
    }

    @Test
    public void testToString() throws Exception {

    }

    @Test
    public void testSize() throws Exception {

    }
}