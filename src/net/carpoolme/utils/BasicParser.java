package net.carpoolme.utils;

/**
 * Created by John Andersen on 5/22/16.
 */
public class BasicParser extends Object {
    public static final String DEFAULT_CHARSET = "UTF-8";
    public String CHARSET;

    public BasicParser() {
        this(DEFAULT_CHARSET);
    }

    public BasicParser(String mCHARSET) {
        CHARSET = mCHARSET;
    }

    public Object getKey(Object[][] obj, String key, Object defaultValue) {
        if (obj != null) {
            for (Object[] aObj : obj) {
                if (aObj.length > 1 && aObj[0] != null && aObj[0].toString().equals(key)) {
                    return aObj[1];
                }
            }
        }
        return defaultValue;
    }

    public Object getKey(Object[][] obj, String key) {
        return getKey(obj, key, null);
    }

    public Object[][] setKey(Object[][] obj, String key, Object value) {
        if (obj != null) {
            for (Object[] aObj : obj) {
                if (aObj.length > 1 && aObj[0] != null && aObj[0].toString().equals(key)) {
                    aObj[1] = value;
                    return obj;
                }
            }
        }
        return addKey(obj, key, value);
    }

    public String toString(Object[][] data) {
        return data.toString();
    }

    public String arrayToString(Object[] data) {
        String result = "";
        for (int i = 0; i < data.length; ++i) {
            result += correctFormat(data[i]);
            if (i < data.length - 1) {
                result += ',';
            }
        }
        return result;
    }

    public String correctFormat(Object obj) {
        if (obj instanceof Object[][]) {
            return toString((Object[][]) obj);
        } else if (obj instanceof Object[]) {
            return arrayToString((Object[]) obj);
        } else if (obj instanceof Boolean) {
            return String.format("%b", obj);
        } else if (obj instanceof Integer) {
            return String.format("%d", obj);
        } else if (obj instanceof Double) {
            return String.format("%f", obj);
        }
        return String.format("\"%s\"", obj);
    }

    public Object correctType(Object obj) {
        String string = obj.toString();
        switch (Strings.Type(string)) {
            case Strings.TYPE_BOOL:
                return Strings.toBoolean(string);
            case Strings.TYPE_INT:
                return Strings.toInt(string);
            case Strings.TYPE_DOUBLE:
                return Strings.toDouble(string);
            case Strings.TYPE_STRING:
            default:
                return string;
        }
    }

    private Object[][] addKey(Object[][] obj, String key, Object value) {
        int length = obj == null ? 0 : obj.length;
        Object[][] newJSON = new Object[length + 1][2];
        // Add the new key
        newJSON[length][0] = key;
        newJSON[length][1] = correctType(value);
        // Expand the array
        if (obj != null) {
            System.arraycopy(obj, 0, newJSON, 0, length);
        }
        return newJSON;
    }
}
