package net.carpoolme.utils;

import net.carpoolme.datastructures.Tree23;

import java.io.InvalidObjectException;

/**
 * Created by John Andersen on 6/2/16.
 */
public class ArgumentParser extends Tree23 {
    public static final String POSIX_ARG = "--";
    public static final String POSIX_FLAG = "-";

    private final String[] argv;

    public ArgumentParser(final String[] mArgv) {
        argv = mArgv;
    }

    public ArgumentParser parseArgs() {
        String string;
        for (int i = 0; i < argv.length; ++i) {
            string = argv[i];
            if (string.startsWith(POSIX_ARG)) {
                string = string.substring(POSIX_ARG.length(), string.length());
                try {
                    ++i;
                    if (i < argv.length) {
                        add(string, argv[i]);
                    }
                } catch (InvalidObjectException ignored) {}
            } else if (string.startsWith(POSIX_FLAG)) {
                try {
                    add(string.substring(POSIX_FLAG.length(), string.length()), true);
                } catch (InvalidObjectException ignored) {}
            }
        }
        return this;
    }

    public boolean getBoolean(final String string) {
        try {
            return (boolean) get(string);
        } catch (IndexOutOfBoundsException ignored) {}
        return false;
    }

    public int getInteger(final String string) {
        try {
            return Integer.parseInt((String) get(string));
        } catch (IndexOutOfBoundsException ignored) {}
        return 0;
    }

    public String getString(final String string) {
        try {
            return (String) get(string);
        } catch (IndexOutOfBoundsException ignored) {}
        return "";
    }

    public String[] getStringArray(final String string) {
        try {
            return (String[]) getAll(string).toArray();
        } catch (IndexOutOfBoundsException ignored) {}
        return new String[0];
    }
}
