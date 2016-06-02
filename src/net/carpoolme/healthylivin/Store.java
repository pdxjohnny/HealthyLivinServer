package net.carpoolme.healthylivin;

import net.carpoolme.db.Database;
import net.carpoolme.db.Table;
import net.carpoolme.utils.BasicParser;
import net.carpoolme.utils.JSONParser;

/**
 * Created by John Andersen on 5/29/16.
 */
public abstract class Store extends BasicData {
    public static final String DEFAULT_NAME = "Unknown store";
    public static final int DEFAULT_HEALTH = 0;

    // All protected so derived classes can change to what they need
    protected BasicParser parser = new BasicParser();

    protected String name = DEFAULT_NAME;
    protected int health = DEFAULT_HEALTH;

    public Store(final Database mDatabase, final Table mTable) {
        super(mDatabase, mTable);
        database.createTable(getClass().getSimpleName(), "name", new String[]{"name", "health"});
        load();
    }

    public Object[][] Marshal() {
        return new Object[][] {
                new Object[] {"name", name},
                new Object[] {"health", health}
        };
    }

    public boolean Unmarshal(Object[][] data) {
        System.out.println("DEBUG: Store parsing " + new JSONParser().toString(data));
        name = (String) parser.getKey(data, "name", DEFAULT_NAME);
        health = (int) parser.getKey(data, "health", DEFAULT_HEALTH);
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
