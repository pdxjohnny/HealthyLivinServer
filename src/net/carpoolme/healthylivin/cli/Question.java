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
public class Question extends net.carpoolme.healthylivin.Question {

    public Question(Database mDatabase) {
        super(mDatabase, null);
    }

    public Question(final Database mDatabase, final Table mTable) {
        super(mDatabase, mTable);
    }

    public BasicData createSelf() {
        return new Question(database, table);
    }

    public void toStream(OutputStream out) {
        try {
            out.write(String.format("question: %s%nwalking: %d%nrunning: %d%ncycling: %d%ngym: %d%nhiking: %d%nstop: %b%n", question, walking, running, cycling, gym, hiking, stop).getBytes());
        } catch (IOException ignored) {}
    }

    public boolean fromStream(InputStream in) {
        Scanner scanner = new Scanner(in).useDelimiter(",");
        if (!scanner.hasNext()) {
            return false;
        }
        question = scanner.next().trim();
        if (!scanner.hasNext()) {
            return false;
        }
        walking = Strings.toInt(scanner.next().trim());
        if (!scanner.hasNext()) {
            return false;
        }
        running = Strings.toInt(scanner.next().trim());
        if (!scanner.hasNext()) {
            return false;
        }
        cycling = Strings.toInt(scanner.next().trim());
        if (!scanner.hasNext()) {
            return false;
        }
        gym = Strings.toInt(scanner.next().trim());
        if (!scanner.hasNext()) {
            return false;
        }
        hiking = Strings.toInt(scanner.next().trim());
        if (!scanner.hasNext()) {
            return false;
        }
        stop = Strings.toBoolean(scanner.next().trim());
        return true;
    }
}
