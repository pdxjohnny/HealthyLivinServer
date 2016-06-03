package net.carpoolme.healthylivin;

import net.carpoolme.db.Database;
import net.carpoolme.db.Table;

/**
 * Created by John Andersen on 5/29/16.
 */
public abstract class Activity extends Question {
    public static final String DEFAULT_ACTIVITY = "Not sure what to do";

    protected String activity = DEFAULT_ACTIVITY;

    public Activity(final Database mDatabase, final Table mTable) {
        super(mDatabase, mTable, false);
        database.createTable(getClass().getSimpleName(),
                "activity",
                new String[]{"activity", "walking", "running", "cycling", "gym", "hiking"}
        );
        load();
    }

    public Object[][] Marshal() {
        return new Object[][] {
                new Object[] {"activity", activity},
                new Object[] {"walking", walking},
                new Object[] {"running", running},
                new Object[] {"cycling", cycling},
                new Object[] {"gym", gym},
                new Object[] {"hiking", hiking}
        };
    }

    public boolean Unmarshal(Object[][] data) {
        super.Unmarshal(data);
        activity = (String) parser.getKey(data, "activity", DEFAULT_ACTIVITY);
        return true;
    }
}
