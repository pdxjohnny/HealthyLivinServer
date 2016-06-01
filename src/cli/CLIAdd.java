package CLI;

/**
 * Created by John Andersen on 5/29/16.
 */

import net.carpoolme.db.Database;

public class CLIAdd extends CLICommand {
    CLIAdd(Database mDatabase, String[] mArgv, String mPreviousCommands) {
        super(mDatabase, mArgv, mPreviousCommands);
        COMMAND_NAME = "add";
        subCommands = new Object[][]{
                new Object[]{"food", new CLIAddFood(database, downArgLevel(), commandsSoFar())},
//                new Object[]{"store", new CLIAddStore(database, downArgLevel(), commandsSoFar())},
//                new Object[]{"restaurant", new CLIAddRestaurant(database, downArgLevel(), commandsSoFar())},
        };
    }
}
