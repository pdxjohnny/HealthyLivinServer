package net.carpoolme.utils;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Created by John Andersen on 6/2/16.
 */
public class Logging extends PrintStream {
    public static final String DEBUG = "DEBUG";
    public static final String INFO = "INFO";
    public static final String WARN = "WARN";
    public static final String ERROR = "ERROR";
    public static final String FATAL = "FATAL";

    // For finding in env vars
    public static final String LOG_LEVEL = "LOG_LEVEL";

    public static final int DEBUG_LEVEL = 1;
    public static final int INFO_LEVEL = 2;
    public static final int WARN_LEVEL = 3;
    public static final int ERROR_LEVEL = 4;
    public static final int FATAL_LEVEL = 5;

    private int LEVEL = checkLevel();

    public static Logging start() {
        Logging logging = new Logging(System.out);
        System.setOut(logging);
        return logging;
    }

    public static int mapLevel(final String level) {
        if (level ==  null) {
            return FATAL_LEVEL;
        }
        switch (level) {
            case DEBUG:
                return DEBUG_LEVEL;
            case INFO:
                return INFO_LEVEL;
            case WARN:
                return WARN_LEVEL;
            case ERROR:
                return ERROR_LEVEL;
            case FATAL:
            default:
                return FATAL_LEVEL;
        }
    }

    /**
     * Creates a new print stream.  This stream will not flush automatically.
     *
     * @param out The output stream to which values and objects will be
     *            printed
     * @see PrintWriter#PrintWriter(OutputStream)
     */
    public Logging(OutputStream out) {
        super(out);
    }

    private int checkLevel() {
        return mapLevel(System.getenv(LOG_LEVEL));
    }

    private boolean shouldPrint(final String string) {
        if (string.startsWith(DEBUG) && DEBUG_LEVEL < LEVEL) return false;
        if (string.startsWith(INFO) && INFO_LEVEL < LEVEL) return false;
        if (string.startsWith(WARN) && WARN_LEVEL < LEVEL) return false;
        if (string.startsWith(ERROR) && ERROR_LEVEL < LEVEL) return false;
        if (string.startsWith(FATAL) && FATAL_LEVEL < LEVEL) return false;
        return true;
    }

    @Override
    public void print(String string) {
        if (shouldPrint(string)) {
            super.print(string);
        }
    }

    @Override
    public void println(String string) {
        if (shouldPrint(string)) {
            super.println(string);
        }
    }
}
