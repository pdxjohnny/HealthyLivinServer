package net.carpoolme.healthylivin;

import net.carpoolme.db.Database;
import net.carpoolme.db.Table;
import net.carpoolme.utils.Parseable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by John Andersen on 5/29/16.
 */
public abstract class BasicData implements Parseable {
    protected Database database;
    protected Table table;

    public BasicData(final Database mDatabase, final Table mTable) {
        database = mDatabase;
        table = mTable;
    }

    public abstract BasicData createSelf();

    public abstract void toStream(OutputStream out);

    public abstract boolean fromStream(InputStream in);

    public BasicData select(final String matchField, final String matchString) {
        try {
            table = table.select(matchField, matchString);
        } catch (IndexOutOfBoundsException e) {
            try {
                System.out.write(String.format("ERROR: %s%n", e.getMessage()).getBytes());
            } catch (IOException ignored) {}
        }
        return this;
    }

    public BasicData orderBy(OutputStream out, final String toOrderBy) {
        try {
            table = table.orderBy(toOrderBy);
            for (int i = 0; i < table.size(); ++i) {
                BasicData object = createSelf();
                if (object.Unmarshal(table.get(i))) {
                    object.toStream(out);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            try {
                out.write(String.format("ERROR: %s%n", e.getMessage()).getBytes());
            } catch (IOException ignored) {}
        }
        return this;
    }
}
