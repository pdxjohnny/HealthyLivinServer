
import net.carpoolme.db.Webserver;

/**
 * Created by John Andersen on 5/13/16.
 */

public class Database {
    public static void main(String[] agrv) {
        Webserver server = new Webserver();

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
