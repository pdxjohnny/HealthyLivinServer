package net.carpoolme.healthylivin.server;

import org.junit.Assert;

/**
 * Created by John Andersen on 5/22/16.
 */
public class JSONTest {

    @org.junit.Test
    public void testToString() throws Exception {
        String[] id = new String[] {"uid", "42"};
        String[] time = new String[] {"time", "-239798123.123490"};
        String[] username = new String[] {"username", "pdxjohnny"};
        String[][] data = new String[][] {id, time, username};
        String output = JSON.toString(data);
        Assert.assertEquals("{\"uid\": 42, \"time\": -239798123.123490, \"username\": \"pdxjohnny\"}", output);
    }

    @org.junit.Test
    public void testParse() throws Exception {
        String input = "{\"uid\": 42, \"time\": -239798123.123490, \"username\": \"pdxjohnny\"}";
        Object[][] output = JSON.parse(input);
        // Check all the data
        Assert.assertNotEquals(null, output);
        Assert.assertEquals(42, JSON.getKey(output, "uid"));
        Assert.assertEquals(-239798123.123490, JSON.getKey(output, "time"));
        Assert.assertEquals("pdxjohnny", JSON.getKey(output, "username"));
    }
}