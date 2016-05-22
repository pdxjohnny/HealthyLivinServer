import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by John Andersen on 5/22/16.
 * This cannot handle layered data, so no nested arrays or objects
 */
public class JSON {
    public static final String JSON_CHARSET = "UTF-8";

    public static String toString(String[][] data) {
        String asJSON = "{";
        for (int i = 0; i < data.length; i++) {
            asJSON += String.format("\"%s\": %s", data[i][0], data[i][1]);
            if (i < data.length - 1) {
                asJSON += ",";
            }
        }
        asJSON += "}";
        return asJSON;
    }

    public static String[][] parse(InputStream in) {
        Scanner scanner = new Scanner(in, JSON_CHARSET).useDelimiter("\"");

//        if (scanner.hasNext()) {
//            name = scanner.next();
//        } else {
//            return false;
//        }
//
//        if (scanner.hasNext()) {
//            age = scanner.nextInt();
//        } else {
//            return false;
//        }
//
//        if (scanner.hasNext()) {
//            rate = scanner.nextFloat();
//        } else {
//            return false;
//        }

        return null;
    }
}
