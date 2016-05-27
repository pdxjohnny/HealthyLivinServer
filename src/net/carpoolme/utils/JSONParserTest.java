package net.carpoolme.utils;

import org.junit.Assert;

/**
 * Created by John Andersen on 5/22/16.
 */
public class JSONParserTest {

    @org.junit.Test
    public void testToString() throws Exception {
        Parser parser = new JSONParser();
        String[] id = new String[] {"uid", "42"};
        String[] time = new String[] {"time", "-239798123.123490"};
        String[] username = new String[] {"username", "pdxjohnny"};
        String[][] data = new String[][] {id, time, username};
        String output = parser.toString(data);
        Assert.assertEquals("{\"uid\": 42, \"time\": -239798123.123490, \"username\": \"pdxjohnny\"}", output);
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