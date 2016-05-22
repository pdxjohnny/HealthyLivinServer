package net.carpoolme.utils;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by John Andersen on 5/22/16.
 * This cannot handle layered data, so no nested arrays or objects
 */
public class JSON extends Parser {
    // Constants for parsing
    public static final String JSON_TOKEN_OBJ_OPEN = "\\{";
    public static final String JSON_TOKEN_OBJ_CLOSE = "\\}";
    public static final String JSON_TOKEN_KEY = "\"";
    public static final Character JSON_TOKEN_DEF = ':';
    public static final Character JSON_TOKEN_MORE = ',';
    public static final int JSON_ON_KEY = 1;
    public static final int JSON_ON_VALUE = 2;

    public static String toString(Object[][] data) {
        String asJSON = "{";
        for (int i = 0; i < data.length; i++) {
            asJSON += String.format("\"%s\": %s", data[i][0], correctFormat(data[i][1].toString()));
            if (i < data.length - 1) {
                asJSON += ", ";
            }
        }
        asJSON += "}";
        return asJSON;
    }

    public static Object[][] parse(InputStream in) {
        Object[][] json = null;
        Scanner scanner = new Scanner(in, CHARSET).useDelimiter(JSON_TOKEN_OBJ_OPEN);

        // Make sure there's an opening token
        if (!scanner.hasNext()) {
            return null;
        }
        scanner = scanner.skip(JSON_TOKEN_OBJ_OPEN);

        scanner = scanner.useDelimiter(JSON_TOKEN_KEY);

        String key = "";
        String value;
        int on = JSON_ON_KEY;
        while (!scanner.hasNext(JSON_TOKEN_OBJ_CLOSE)) {
            switch (on) {
                case JSON_ON_KEY:
                    // Get the key
                    key = scanner.next();
                    on = JSON_ON_VALUE;
                    break;
                case JSON_ON_VALUE:
                default:
                    // Get the value
                    value = scanner.next();
                    // Fix the value (in case it has the colon in it)
                    value = fixValue(value);
                    // Store the value
                    json = setKey(json, key, value);
                    on = JSON_ON_KEY;
                    if (value.length() > 0 && value.charAt(0) == JSON_TOKEN_DEF) {
                        on = JSON_ON_VALUE;
                    }
                    break;
            }
        }

        return json;
    }

    private static String fixValue(String value) {
        while (value.length() > 2 && value.charAt(0) == JSON_TOKEN_DEF || value.charAt(0) == ' ') {
            value = value.substring(1, value.length());
        }
        while (value.length() > 2 && value.charAt(value.length() - 1) == JSON_TOKEN_MORE || value.charAt(value.length() - 1) == ' ') {
            value = value.substring(0, value.length() - 1);
        }
        return value;
    }
}
