package cli;

/**
 * Created by John Andersen on 5/29/16.
 */

import net.carpoolme.db.Database;
import net.carpoolme.utils.Logging;

public class CLI extends CLICommand {
    public CLI(String[] mArgv) {
        super("cli.jar", new Database(), mArgv, "java -jar");
        subCommands = new Object[][]{
                new Object[]{"add", new CLIAdd(database, downArgLevel(), commandsSoFar())},
                new Object[]{"list", new CLIList(database, downArgLevel(), commandsSoFar())},
                new Object[]{"exercise", new CLIExercise(database, downArgLevel(), commandsSoFar())}
        };
    }

    public static void main(String[] argv) {
        Logging.start();
        CLI cli = new CLI(argv);
        cli.run();
    }
}
