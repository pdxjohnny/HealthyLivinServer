package net.carpoolme.healthylivin.cli;

import net.carpoolme.db.Database;
import net.carpoolme.db.Table;
import net.carpoolme.healthylivin.BasicData;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

/**
 * Created by John Andersen on 5/29/16.
 */
public class Food extends net.carpoolme.healthylivin.Food {

    public Food(Database mDatabase) {
        super(mDatabase, null);
    }

    public Food(final Database mDatabase, final Table mTable) {
        super(mDatabase, mTable);
    }

    public BasicData createSelf() {
        return new Food(database, table);
    }

    public void toStream(OutputStream out) {
        try {
            out.write(String.format("Name: %s%nCategory: %s%n(Grams)%nSodium: %d%nSugar: %d%nFat: %d%n", name, category, sodium, sugar, fat).getBytes());
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
        sodium = Integer.parseInt(scanner.next().trim());
        if (!scanner.hasNext()) {
            return false;
        }
        sugar = Integer.parseInt(scanner.next().trim());
        if (!scanner.hasNext()) {
            return false;
        }
        fat = Integer.parseInt(scanner.next().trim());
        return true;
    }
}
