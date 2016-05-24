package Web;

import net.carpoolme.healthylivin.server.Webserver;

/**
 * Created by John Andersen on 5/13/16.
 */

public class Web {
    public static void main(String[] agrv) {
        Webserver server = new Webserver();

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
