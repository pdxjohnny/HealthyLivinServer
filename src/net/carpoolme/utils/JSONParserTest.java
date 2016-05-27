package net.carpoolme.utils;

import org.junit.Assert;

/**
 * Created by John Andersen on 5/22/16.
 */
public class JSONParserTest {

    @org.junit.Test
    public void testToString() throws Exception {
        Parser parser = new JSONParser();
        Object[] id = new Object[] {"uid", 42};
        Object[] time = new Object[] {"time", -239798123.123490};
        Object[] username = new Object[] {"username", "pdxjohnny"};
        Object[] password = new Object[] {"password", "testpass"};
        Object[][] data = new Object[][] {id, time, username, password};
        String output = parser.toString(data);
        Assert.assertEquals("{\"uid\": 42, \"time\": -239798123.123490, \"username\": \"pdxjohnny\", \"password\": \"testpass\"}", output);
    }

    @org.junit.Test
    public void testToStringWithArray() throws Exception {
        Parser parser = new JSONParser();
        Object[] id = new Object[] {"uid", 42};
        Object[] array = new Object[] {"array", new Object[] {1, 2, 3, 4, 5}};
        Object[] username = new Object[] {"username", "pdxjohnny"};
        Object[][] data = new Object[][] {id, array, username};
        String output = parser.toString(data);
        Assert.assertEquals("{\"uid\": 42, \"array\": [1,2,3,4,5], \"username\": \"pdxjohnny\"}", output);
    }

    @org.junit.Test
    public void testToStringNested() throws Exception {
        Parser parser = new JSONParser();
        Object[] id = new Object[] {"uid", 42};
        Object[] array = new Object[] {"array", new Object[] {
                new Object[][] {
                        new Object[] {"small", "key"},
                        new Object[] {"large", 12345},
                }, 2, 3, 4, 5}};
        Object[] username = new Object[] {"username", "pdxjohnny"};
        Object[][] data = new Object[][] {id, array, username};
        String output = parser.toString(data);
        Assert.assertEquals("{\"uid\": 42, \"array\": [{\"small\": \"key\", \"large\": 12345},2,3,4,5], \"username\": \"pdxjohnny\"}", output);
    }

    @org.junit.Test
    public void testParse() throws Exception {
        Parser parser = new JSONParser();
        String input = "{\"uid\": 42, \"time\": -239798123.123490, \"username\": \"pdxjohnny\", \"password\": \"testpass\"}";
        Object[][] output = parser.parse(input);
        System.out.println(parser.toString(output));
        // Check all the data
        Assert.assertNotEquals(null, output);
        Assert.assertEquals(42, parser.getKey(output, "uid"));
        Assert.assertEquals(-239798123.123490, parser.getKey(output, "time"));
        Assert.assertEquals("pdxjohnny", parser.getKey(output, "username"));
        Assert.assertEquals("testpass", parser.getKey(output, "password"));
    }
}