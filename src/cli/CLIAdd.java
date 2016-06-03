package cli;

/**
 * Created by John Andersen on 5/29/16.
 */

import net.carpoolme.db.Database;

public class CLIAdd extends CLICommand {
    public CLIAdd(Database mDatabase, String[] mArgv, String mPreviousCommands) {
        super("add", mDatabase, mArgv, mPreviousCommands);
        subCommands = new Object[][]{
                new Object[]{"food", new CLIAddFood(database, downArgLevel(), commandsSoFar())},
                new Object[]{"store", new CLIAddStore(database, downArgLevel(), commandsSoFar())},
                new Object[]{"question", new CLIAddQuestion(database, downArgLevel(), commandsSoFar())},
        };
    }
}
