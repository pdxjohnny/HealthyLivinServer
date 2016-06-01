package CLI;

/**
 * Created by John Andersen on 5/29/16.
 */

import net.carpoolme.db.Database;

public class CLI extends CLICommand {
    CLI(String[] mArgv) {
        super(new Database(), mArgv, "java -jar");
        COMMAND_NAME = "cli.jar";
        subCommands = new Object[][]{
                new Object[]{"add", new CLIAdd(database, downArgLevel(), commandsSoFar())},
                new Object[]{"list", new CLIList(database, downArgLevel(), commandsSoFar())}
        };
    }

    public static void main(String[] argv) {
        CLI cli = new CLI(argv);
        cli.run();
    }
}
