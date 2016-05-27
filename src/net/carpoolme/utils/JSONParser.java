package net.carpoolme.utils;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by John Andersen on 5/22/16.
 * This cannot handle layered data, so no nested arrays or objects
 */
public class JSONParser extends Parser {
    // Constants for parsing
    public static final Character JSON_TOKEN_OBJ_OPEN = '{';
    public static final Character JSON_TOKEN_OBJ_CLOSE = '}';
    public static final Character JSON_TOKEN_KEY = '"';
    public static final Character JSON_TOKEN_DEF = ':';
    public static final Character JSON_TOKEN_MORE = ',';
    public static final int JSON_ON_KEY = 1;
    public static final int JSON_ON_VALUE = 2;

    private Object[][] main;
    private String[] descend = null;

    private String string;
    private String parsedString;
    private int lastIndex = 0;

    public String toString(Object[][] data) {
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

    public Object[][] parse(InputStream in) {
        main = null;
        descend = null;
        lastIndex = 0;

        Scanner scanner = new Scanner(in).useDelimiter("\\A");
        string = scanner.hasNext() ? scanner.next() : "";
        System.out.println(string);

        String key = "";
        int on = JSON_ON_KEY;
        while (nextData()) {
            if (parsedString.length() > 0) {
                switch (on) {
                    case JSON_ON_KEY:
                        // Get the key
                        key = parsedString;
                        on = JSON_ON_VALUE;
                        break;
                    case JSON_ON_VALUE:
                    default:
                        System.out.println("Adding: " + key + "    Value: " + fixValue(parsedString));
                        // Store the value
                        setJSONKey(key, fixValue(parsedString));
                        on = JSON_ON_KEY;
                        break;
                }
            }
        }

        return main;
    }

    private void setJSONKey(final String key, final Object value) {
        // We are top level
        if (descend == null) {
            main = setKey(main, key, value);
            return;
        }
        // We need to descend into the structure
        Object[][] current = main;
        for (int i = 0; i < descend.length; ++i) {
            current = (Object[][]) getKey(current, descend[i]);
        }
    }

    private boolean nextData() {
        boolean started = false;
        String result = "";
        parsedString = "";
        // Nothing left to parse
        if (lastIndex >= string.length()) {
            return false;
        }
        Character lastToken = string.charAt(lastIndex);
        switch (lastToken) {
            // Looking for a key because we just started an object or finished the last key
            case '{':
            case ',':
                for (; lastIndex < string.length(); ++lastIndex) {
                    lastToken = string.charAt(lastIndex);
                    if (lastToken == JSON_TOKEN_KEY) {
                        if (!started) {
                            started = true;
                            continue;
                        } else {
                            parsedString = result;
                            return true;
                        }
                    }
                    if (started) {
                        result += lastToken;
//                        System.out.println(result);
                    }
                }
                break;
            case ':':
                ++lastIndex;
                boolean withinString = false;
                for (; lastIndex < string.length(); ++lastIndex) {
                    lastToken = string.charAt(lastIndex);
//                    System.out.println("lastToken: " + lastToken);
                    if (lastToken == '"') {
                        withinString = !withinString;
                    }
                    if (!withinString && (lastToken == JSON_TOKEN_MORE || lastToken == JSON_TOKEN_OBJ_CLOSE)) {
                        parsedString = result;
                        return true;
                    }
                    result += lastToken;
//                    System.out.println(result);
                }
                break;
            default:
                ++lastIndex;
                return true;
        }
        return false;
    }

    private static String fixValue(String value) {
        while (value.length() > 1 && value.charAt(0) == JSON_TOKEN_DEF || value.charAt(0) == ' ') {
            value = value.substring(1, value.length());
        }
        while (value.length() > 1 && value.charAt(value.length() - 1) == JSON_TOKEN_MORE || value.charAt(value.length() - 1) == ' ') {
            value = value.substring(0, value.length() - 1);
        }
        // Fix stings
        if (value.length() > 1) {
            if (value.charAt(0) == '"') {
                value = value.substring(1, value.length());
            }
        }
        if (value.length() > 1) {
            if (value.charAt(value.length() - 1) == '"') {
                value = value.substring(0, value.length() - 1);
            }
        }
        return value;
    }
}
