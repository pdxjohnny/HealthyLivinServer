/**
 * Created by John Andersen on 5/13/16.
 */

import java.io.InputStream;

public class Employee extends Object {
    private String name;
    private int age;
    private float rate;

    public boolean fromStream(InputStream in) {
        java.util.Scanner input = new java.util.Scanner(in);
        input.useDelimiter(", ");
        // Parse in the values

        if (input.hasNext()) {
            name = input.next();
        } else {
            return false;
        }

        if (input.hasNext()) {
            age = input.nextInt();
        } else {
            return false;
        }

        if (input.hasNext()) {
            rate = input.nextFloat();
        } else {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        java.text.NumberFormat formatter = new java.text.DecimalFormat("#0.00");
        return String.format("%s, %d, %s", name, age, formatter.format(rate));
    }
}
