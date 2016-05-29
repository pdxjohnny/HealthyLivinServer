package CLI;

/**
 * Created by John Andersen on 5/29/16.
 */

import net.carpoolme.db.Database;

public class CLIAddFood extends CLICommand {
    CLIAddFood(Database mDatabase, String[] mArgv, String mPreviousCommands) {
        super(mDatabase, mArgv, mPreviousCommands);
        COMMAND_NAME = "food";
    }

    @Override
    public void run() {
        System.out.println("Will add a food");
    }
}
