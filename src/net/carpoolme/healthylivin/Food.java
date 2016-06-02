package net.carpoolme.healthylivin;

import net.carpoolme.db.Database;
import net.carpoolme.db.Table;
import net.carpoolme.utils.BasicParser;
import net.carpoolme.utils.JSONParser;

/**
 * Created by John Andersen on 5/29/16.
 */
public abstract class Food extends BasicData {
    public static final String DEFAULT_NAME = "Unknown food";
    public static final String DEFAULT_CATEGORY = "No category for this food";
    public static final int DEFAULT_NUTRITION = 0;

    // All protected so derived classes can change to what they need
    protected BasicParser parser = new BasicParser();

    protected String name = DEFAULT_NAME;
    protected String category = DEFAULT_CATEGORY;
    protected int sodium = DEFAULT_NUTRITION;
    protected int sugar = DEFAULT_NUTRITION;
    protected int fat = DEFAULT_NUTRITION;

    public Food(final Database mDatabase, final Table mTable) {
        super(mDatabase, mTable);
        database.createTable(getClass().getSimpleName(), "name", new String[]{"name", "category", "sodium", "sugar", "fat"});
        load();
    }

    public Object[][] Marshal() {
        return new Object[][] {
                new Object[] {"name", name},
                new Object[] {"category", category},
                new Object[] {"sodium", sodium},
                new Object[] {"sugar", sugar},
                new Object[] {"fat", fat}
        };
    }

    public boolean Unmarshal(Object[][] data) {
        System.out.println("DEBUG: Food parsing " + new JSONParser().toString(data));
        name = (String) parser.getKey(data, "name", DEFAULT_NAME);
        category = (String) parser.getKey(data, "category", DEFAULT_CATEGORY);
        sodium = (int) parser.getKey(data, "sodium", DEFAULT_CATEGORY);
        sugar = (int) parser.getKey(data, "sugar", DEFAULT_CATEGORY);
        fat = (int) parser.getKey(data, "fat", DEFAULT_CATEGORY);
        return true;
    }

    public Table load() {
        if (table == null) {
            table = (Table) database.get(getClass().getSimpleName());
        }
        return table;
    }

    public boolean save() {
        return database.insert(getClass().getSimpleName(), Marshal());
    }
}
