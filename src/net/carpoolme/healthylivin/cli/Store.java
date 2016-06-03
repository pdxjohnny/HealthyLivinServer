package net.carpoolme.healthylivin.cli;

import net.carpoolme.db.Database;
import net.carpoolme.db.Table;
import net.carpoolme.healthylivin.BasicData;
import net.carpoolme.utils.Strings;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

/**
 * Created by John Andersen on 5/29/16.
 */
public class Store extends net.carpoolme.healthylivin.Store {

    public Store(Database mDatabase) {
        super(mDatabase, null);
    }

    public Store(final Database mDatabase, final Table mTable) {
        super(mDatabase, mTable);
    }

    public BasicData createSelf() {
        return new Store(database, table);
    }

    public void toStream(OutputStream out) {
        try {
            out.write(String.format("Name: %s%nCategory: %s%nHealth Index: %s%n", name, category, health).getBytes());
        } catch (IOException ignored) {}
    }

    public boolean fromStream(InputStream in) {
        Scanner scanner = new Scanner(in).useDelimiter(",");
        if (!scanner.hasNext()) {
            return false;
        }
        name = scanner.next().trim();
        if (!scanner.hasNext()) {
            return false;
        }
        category = scanner.next().trim();
        if (!scanner.hasNext()) {
            return false;
        }
        health = Strings.toInt(scanner.next().trim());
        return true;
    }
}
