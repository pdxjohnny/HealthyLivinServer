package net.carpoolme.db;

import net.carpoolme.storage.FileSystemStorage;
import net.carpoolme.storage.MockStorage;
import net.carpoolme.storage.Storage;
import net.carpoolme.utils.JSONParser;
import net.carpoolme.utils.Parser;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;

/**
 * Created by John Andersen on 5/28/16.
 */
public class TableTest {

    @Test
    public void testStorage() throws Exception {
        Storage storage = new FileSystemStorage(Paths.get("tmp", "TableTest"));
        Table table = new Table(storage, "id", new String[]{"id", "username"});
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

        final int loadTimes = 10;
        Table tableLoad;
        for (int i = 0; i < loadTimes; ++i) {
            tableLoad = new Table(storage, "id", new String[]{"id", "username"});
            result = tableLoad.get("id", 42);
            Assert.assertEquals("{\"id\": 42, \"username\": \"pdxjohnny\", \"password\": \"test\"}", parser.toString(result));
            result = tableLoad.get("username", "pdxjohnny");
            Assert.assertEquals("{\"id\": 42, \"username\": \"pdxjohnny\", \"password\": \"test\"}", parser.toString(result));
        }

        storage.destroy();
    }

    @Test
    public void testSelect() throws Exception {
        Storage storage = new MockStorage();
        Table table = new Table(storage, "id", new String[]{"id"});
        final int tableSize = 100;
        int userNumber = 0;
        for (int i = 0; i < tableSize; ++i) {
            if (i % 5 == 0) {
                userNumber = 42;
            } else {
                userNumber = i;
            }
            table.add(new Object[][]{
                    new Object[]{"id", i},
                    new Object[]{"username", String.format("pdxjohnny-%d", userNumber)}
            });
        }
        Parser parser = new JSONParser();
        Table result = table.select("username", "pdxjohnny-42");
        Object[][] current;
        int j = 0;
        for (int i = 0; i < tableSize; ++i) {
            if (i % 5 == 0 || i == 42) {
                current = result.get(j);
                Assert.assertEquals(String.format("{\"id\": %d, \"username\": \"pdxjohnny-%d\"}", i, 42), parser.toString(current));
                ++j;
            }
        }
        storage.destroy();
    }

    @Test
    public void testSelect2() throws Exception {
        Storage storage = new MockStorage();
        Table table = new Table(storage, "id", new String[]{"id"});
        final int tableSize = 100;
        int userNumber = 0;
        for (int i = 0; i < tableSize; ++i) {
            if (i % 5 == 0) {
                userNumber = 42;
            } else {
                userNumber = i;
            }
            table.add(new Object[][]{
                    new Object[]{"id", userNumber},
                    new Object[]{"username", String.format("pdxjohnny-%d", userNumber)}
            });
        }
        Parser parser = new JSONParser();
        Table result = table.select("id", 42);
        Object[][] current;
        int j = 0;
        for (int i = 0; i < tableSize; ++i) {
            if (i % 5 == 0 || i == 42) {
                current = result.get(j);
                Assert.assertEquals("{\"id\": 42, \"username\": \"pdxjohnny-42\"}", parser.toString(current));
                ++j;
            }
        }
        storage.destroy();
    }
}