package CLI;

import net.carpoolme.db.Database;

import java.util.Arrays;

/**
 * Created by John Andersen on 5/29/16.
 */
public class CLICommand {
    protected Database database;
    protected String[] argv;
    protected String previousCommands;

    protected String COMMAND_NAME = "COMMAND";
    protected Object[][] subCommands = null;

    CLICommand(Database mDatabase, String[] mArgv, String mPreviousCommands) {
        database = mDatabase;
        argv = mArgv;
        previousCommands = mPreviousCommands;
    }

    public void run() {
        if (argv.length < 1) {
            displayUsage();
            return;
        }
        if (subCommands == null) {
            return;
        }
        for (int i = 0; i < subCommands.length; ++i) {
            if (subCommands[i].length > 1 && subCommands[i][0].equals(argv[0])) {
                ((CLICommand) subCommands[i][1]).run();
                return;
            }
        }
        displayUsage();
    }

    protected String[] downArgLevel() {
        if (argv.length > 0) {
            return Arrays.copyOfRange(argv, 1, argv.length);
        }
        return new String[0];
    }

    protected String commandsSoFar() {
        return String.format("%s %s", previousCommands, COMMAND_NAME);
    }

    private void displayUsage() {
        System.out.printf("Usage %s [COMMAND] [FLAGS]\n", commandsSoFar());
        if (subCommands.length > 0) {
            System.out.println("Commands");
            for (int i = 0; i < subCommands.length; ++i) {
                if (subCommands[i].length > 1) {
                    System.out.printf("    %s\n", subCommands[i][0]);
                }
            }
        }
    }
}
