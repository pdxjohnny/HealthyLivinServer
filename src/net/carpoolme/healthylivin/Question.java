package net.carpoolme.healthylivin;

import net.carpoolme.db.Database;
import net.carpoolme.db.Table;
import net.carpoolme.utils.BasicParser;
import net.carpoolme.utils.JSONParser;

/**
 * Created by John Andersen on 5/29/16.
 */
public abstract class Question extends BasicData {
    public static final String DEFAULT_QUESTION = "Blank question";
    public static final int DEFAULT_VALUE = 0;

    // All protected so derived classes can change to what they need
    protected BasicParser parser = new BasicParser();

    protected String question = DEFAULT_QUESTION;
    // These points are only awarded if the answer to the question is yes
    protected int walking = DEFAULT_VALUE;
    protected int running = DEFAULT_VALUE;
    protected int cycling = DEFAULT_VALUE;
    protected int gym = DEFAULT_VALUE;
    protected int hiking = DEFAULT_VALUE;
    protected boolean stop = false;

    public Question(final Database mDatabase, final Table mTable) {
        this(mDatabase, mTable, true);
    }

    public Question(final Database mDatabase, final Table mTable, final boolean createTable) {
        super(mDatabase, mTable);
        if (createTable) {
            database.createTable(getClass().getSimpleName(), "question", new String[]{"question"});
        }
        load();
    }

    public Object[][] Marshal() {
        return new Object[][] {
                new Object[] {"question", question},
                new Object[] {"walking", walking},
                new Object[] {"running", running},
                new Object[] {"cycling", cycling},
                new Object[] {"gym", gym},
                new Object[] {"hiking", hiking},
                new Object[] {"stop", stop}
        };
    }

    public boolean Unmarshal(Object[][] data) {
        System.out.println("DEBUG: Question parsing " + new JSONParser().toString(data));
        question = (String) parser.getKey(data, "question", DEFAULT_QUESTION);
        walking = (int) parser.getKey(data, "walking", DEFAULT_VALUE);
        running = (int) parser.getKey(data, "running", DEFAULT_VALUE);
        cycling = (int) parser.getKey(data, "cycling", DEFAULT_VALUE);
        gym = (int) parser.getKey(data, "gym", DEFAULT_VALUE);
        hiking = (int) parser.getKey(data, "hiking", DEFAULT_VALUE);
        stop = (boolean) parser.getKey(data, "stop", false);
        return true;
    }

    public Table load() {
        if (table == null) {
            try {
                table = (Table) database.get(getClass().getSimpleName());
            } catch (IndexOutOfBoundsException ignored) {}
        }
        return table;
    }

    public boolean save() {
        return database.insert(getClass().getSimpleName(), Marshal());
    }
}
