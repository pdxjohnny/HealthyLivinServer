package net.carpoolme.healthylivin.cli;

import net.carpoolme.db.Database;

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
}
