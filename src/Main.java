/**
 * Created by developer on 5/13/16.
 */
public class Main {
    public static void main(String[] agrv) {
        Webserver server = new Webserver();
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}