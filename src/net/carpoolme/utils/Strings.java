package net.carpoolme.utils;

/**
 * Created by John Andersen on 5/22/16.
 */
public class Strings {
    public static final int TYPE_INT = 1;
    public static final int TYPE_DOUBLE = 2;
    public static final int TYPE_STRING = 3;
    public static final int TYPE_BOOL = 4;

    // Checks if a string is not a number
    public static boolean isNaN (String string) {
        try {
            Double.parseDouble(string);
        } catch(NumberFormatException nfe) {
            return true;
        }
        return false;
    }

    public static int toInt(String string) throws NumberFormatException {
        return Integer.parseInt(string);
    }

    public static double toDouble(String string) throws NumberFormatException {
        return Double.parseDouble(string);
    }

    public static boolean isBoolean(String string) {
        return string.toUpperCase().equals("TRUE") ||
                string.toUpperCase().equals("FALSE");
    }

    public static boolean toBoolean(String string) {
        return string.toUpperCase().equals("TRUE") ||
                string.toUpperCase().equals("ON") ||
                string.toUpperCase().equals("YES");
    }

    // Checks what data type the string could be
    public static int Type(String string) {
        if (isBoolean(string)) {
            return TYPE_BOOL;
        }
        try {
            toInt(string);
            return TYPE_INT;
        } catch (NumberFormatException ignored) {}
        try {
            toDouble(string);
            return TYPE_DOUBLE;
        } catch (NumberFormatException ignored) {}
        return TYPE_STRING;
    }
}
