package net.carpoolme.healthylivin;

import net.carpoolme.db.Database;
import net.carpoolme.db.Table;
import net.carpoolme.utils.BasicParser;
import net.carpoolme.utils.JSONParser;

/**
 * Created by John Andersen on 5/29/16.
 */
public class Food {
    public static final String DEFAULT_NAME = "Unknown food";
    public static final String DEFAULT_CATEGORY = "No category for this food";

    protected Database database;

    // All protected so derived classes can change to what they need
    protected BasicParser parser = new BasicParser();

    protected String name = DEFAULT_NAME;
    protected String category = DEFAULT_CATEGORY;

    public Food(Database mDatabase) {
        database = mDatabase;
        database.createTable(getClass().getSimpleName(), "name", new String[]{"name", "category"});
    }

    public Object[][] Marshal() {
        return new Object[][] {
                new Object[] {"name", name},
                new Object[] {"category", category}
        };
    }

    public boolean Unmarshal(Object[][] data) {
        System.out.println("DEBUG: Food parsing " + new JSONParser().toString(data));
        name = (String) parser.getKey(data, "name", DEFAULT_NAME);
        category = (String) parser.getKey(data, "category", DEFAULT_CATEGORY);
        return true;
    }

    public Table load() {
        return (Table) database.get(getClass().getSimpleName());
    }

    public boolean save() {
        return database.insert(getClass().getSimpleName(), Marshal());
    }
}
