package net.carpoolme.db;

import org.junit.Test;

/**
 * Created by John Andersen on 5/28/16.
 */
public class TableTest {

    @Test
    public void testAll() throws Exception {
//        Storage storage = new FileSystemStorage(Paths.get("tmp", "TableTest"));
//        Table table = new Table(storage, "id", new String[]{"id", "username"});
//        table.add(new Object[][]{
//                new Object[]{"id", 42},
//                new Object[]{"username", "pdxjohnny"},
//                new Object[]{"password", "test"}
//        });
//        Parser parser = new JSONParser();
//        Object[][] result = table.get("id", 42);
//        Assert.assertEquals("{\"id\": 42, \"username\": \"pdxjohnny\", \"password\": \"test\"}", parser.toString(result));
//        result = table.get("username", "pdxjohnny");
//        Assert.assertEquals("{\"id\": 42, \"username\": \"pdxjohnny\", \"password\": \"test\"}", parser.toString(result));
//        result = table.get("password", "test");
//        Assert.assertEquals("{\"id\": 42, \"username\": \"pdxjohnny\", \"password\": \"test\"}", parser.toString(result));
//
//        final int loadTimes = 10;
//        Table tableLoad;
//        for (int i = 0; i < loadTimes; ++i) {
//            tableLoad = new Table(storage, "id", new String[]{"id", "username"});
//            result = tableLoad.get("id", 42);
//            Assert.assertEquals("{\"id\": 42, \"username\": \"pdxjohnny\", \"password\": \"test\"}", parser.toString(result));
//            result = tableLoad.get("username", "pdxjohnny");
//            Assert.assertEquals("{\"id\": 42, \"username\": \"pdxjohnny\", \"password\": \"test\"}", parser.toString(result));
//        }
//
//        storage.destroy();
    }
}