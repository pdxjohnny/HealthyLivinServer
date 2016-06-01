package net.carpoolme.healthylivin.cli;

import net.carpoolme.db.Database;
import net.carpoolme.db.Table;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

/**
 * Created by John Andersen on 5/29/16.
 */
public class Food extends net.carpoolme.healthylivin.Food {

    public Food(Database mDatabase) {
        super(mDatabase);
    }

    public void toStream(OutputStream out) {
        try {
            out.write(String.format("Name: %s\nCategory: %s\n", name, category).getBytes());
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
        return true;
    }

    public void orderBy(OutputStream out, final String toOrderBy) {
        Table all;
        try {
            all = load().orderBy(toOrderBy);
        } catch (IndexOutOfBoundsException e) {
            try {
                out.write(String.format("%s%n", e.getMessage()).getBytes());
            } catch (IOException ignored) {}
            return;
        }
        for (int i = 0; i < all.size(); i++) {
            Food food = new Food(database);
            if (food.Unmarshal(all.get(i))) {
                food.toStream(out);
            }
        }
    }
}
