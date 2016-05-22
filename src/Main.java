import net.carpoolme.healthylivin.server.Webserver;
import net.carpoolme.utils.Strings;

/**
 * Created by John Andersen on 5/13/16.
 */

public class Main {
    public static String ENV_PORT = "PORT";

    public static void main(String[] agrv) {
        Webserver server = new Webserver();

        // Determine which port to serve on
        String portString = null;
        try {
            portString = System.getenv(ENV_PORT);
        } catch (NullPointerException | SecurityException ignored) {}
        if (portString == null) {
            portString = String.format("%d", Webserver.DEFAULT_PORT);
            System.out.println("INFO: serving on port " + portString);
        }
        int port = Strings.toInt(portString);

        try {
            server.start(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
