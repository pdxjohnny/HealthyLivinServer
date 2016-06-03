package cli;

/**
 * Created by John Andersen on 5/29/16.
 */

import net.carpoolme.db.Database;
import net.carpoolme.healthylivin.cli.Activity;

public class CLIAddActivity extends CLICommand {
    public CLIAddActivity(Database mDatabase, String[] mArgv, String mPreviousCommands) {
        super("activity", mDatabase, mArgv, mPreviousCommands);
    }

    @Override
    public void run() {
        System.out.printf("Please input an activity in the form of activity (no commas), walking," +
                " running, cycling, gym, hiking (minimum points at which to do this activity," +
                " must be a whole number): ");
        Activity data = new Activity(database);
        data.fromStream(System.in);
        System.out.println("Here's the activity you input");
        data.toStream(System.out);
        data.save();
    }
}
