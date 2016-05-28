package net.carpoolme.db;

import net.carpoolme.utils.JSONParser;
import net.carpoolme.utils.Parser;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by John Andersen on 5/28/16.
 */
public class TableTest {

    @Test
    public void testAll() throws Exception {
        Table table = new Table();
        table.addIndexes(new String[]{"id", "username"});
        table.add(new Object[][]{
                new Object[]{"id", 42},
                new Object[]{"username", "pdxjohnny"},
                new Object[]{"password", "test"}
        });
        Parser parser = new JSONParser();
        Object[][] result = table.get("id", 42);
        Assert.assertEquals("{\"id\": 42, \"username\": \"pdxjohnny\", \"password\": \"test\"}", parser.toString(result));
        result = table.get("username", "pdxjohnny");
        Assert.assertEquals("{\"id\": 42, \"username\": \"pdxjohnny\", \"password\": \"test\"}", parser.toString(result));
        result = table.get("password", "test");
        Assert.assertEquals("{\"id\": 42, \"username\": \"pdxjohnny\", \"password\": \"test\"}", parser.toString(result));
    }
}