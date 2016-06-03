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
public class Activity extends net.carpoolme.healthylivin.Activity {

    public Activity(Database mDatabase) {
        super(mDatabase, null);
    }

    public Activity(final Database mDatabase, final Table mTable) {
        super(mDatabase, mTable);
    }

    public BasicData createSelf() {
        return new Activity(database, table);
    }

    public void toStream(OutputStream out) {
        try {
            out.write(String.format("activity: %s%nwalking: %d%nrunning: %d%ncycling: %d%ngym: %d%nhiking: %d%n", activity, walking, running, cycling, gym, hiking).getBytes());
        } catch (IOException ignored) {}
    }

    public boolean fromStream(InputStream in) {
        Scanner scanner = new Scanner(in).useDelimiter(",");
        if (!scanner.hasNext()) {
            return false;
        }
        activity = scanner.next().trim();
        if (!scanner.hasNext()) {
            return false;
        }
        walking = Strings.toIntSafe(scanner.next().trim());
        if (!scanner.hasNext()) {
            return false;
        }
        running = Strings.toIntSafe(scanner.next().trim());
        if (!scanner.hasNext()) {
            return false;
        }
        cycling = Strings.toIntSafe(scanner.next().trim());
        if (!scanner.hasNext()) {
            return false;
        }
        gym = Strings.toIntSafe(scanner.next().trim());
        if (!scanner.hasNext()) {
            return false;
        }
        hiking = Strings.toIntSafe(scanner.next().trim());
        return true;
    }

    @Override
    public String toString() {
        return activity;
    }
}
