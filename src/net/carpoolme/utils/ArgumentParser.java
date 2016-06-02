package net.carpoolme.utils;

import net.carpoolme.datastructures.Tree23;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.OutputStream;

/**
 * Created by John Andersen on 6/2/16.
 */
public class ArgumentParser extends Tree23 {
    public static final String POSIX_ARG = "--";
    public static final String POSIX_FLAG = "-";

    private final String commandName;
    private final String[] argv;
    private final Tree23 lookingFor = new Tree23();

    public ArgumentParser(final String[] mArgv) {
        commandName = "";
        argv = mArgv;
    }

    public ArgumentParser(final String mCommandName, final String[] mArgv) {
        commandName = mCommandName;
        argv = mArgv;
    }

    public ArgumentParser add(final String arg, final String help) {
        try {
            lookingFor.add(arg, help);
        } catch (InvalidObjectException ignored) {}
        return this;
    }

    public boolean help(OutputStream out) {
        if (getBoolean("help")) {
            try {
                if (commandName.length() > 0) {
                    out.write(String.format("Help for %s%n", commandName).getBytes());
                } else {
                    out.write(String.format("Help%n").getBytes());
                }
                for (int i = 0; i < lookingFor.size(); ++i) {
                    out.write(String.format("    %-20s%s%n", lookingFor.key(i), lookingFor.value(i)).getBytes());
                }
                return true;
            } catch (IOException ignored) {}
        }
        return false;
    }

    public ArgumentParser parseArgs() {
        String string;
        for (int i = 0; i < argv.length; ++i) {
            string = argv[i];
            if (string.startsWith(POSIX_ARG)) {
                string = string.substring(POSIX_ARG.length(), string.length());
                try {
                    ++i;
                    if (i < argv.length && !argv[i].startsWith(POSIX_ARG) && !argv[i].startsWith(POSIX_FLAG)) {
                        super.add(string, argv[i]);
                    } else {
                        super.add(string, true);
                    }
                } catch (InvalidObjectException ignored) {}
            } else if (string.startsWith(POSIX_FLAG)) {
                try {
                    super.add(string.substring(POSIX_FLAG.length(), string.length()), true);
                } catch (InvalidObjectException ignored) {}
            }
        }
        return this;
    }

    public boolean getBoolean(final String string) {
        try {
            return (boolean) get(string);
        } catch (ClassCastException | IndexOutOfBoundsException ignored) {}
        return false;
    }

    public int getInteger(final String string) {
        try {
            return Integer.parseInt((String) get(string));
        } catch (ClassCastException | IndexOutOfBoundsException ignored) {}
        return 0;
    }

    public String getString(final String string) {
        try {
            return (String) get(string);
        } catch (ClassCastException | IndexOutOfBoundsException ignored) {}
        return "";
    }

    public String[] getStringArray(final String string) {
        try {
            return (String[]) getAll(string).toArray();
        } catch (ClassCastException | IndexOutOfBoundsException ignored) {}
        return new String[0];
    }
}
