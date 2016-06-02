package cli;

import net.carpoolme.db.Database;
import net.carpoolme.utils.ArgumentParser;

import java.util.Arrays;

/**
 * Created by John Andersen on 5/29/16.
 */
public class CLICommand {
    protected Database database;
    protected String[] argv;
    protected String previousCommands;
    protected ArgumentParser argumentParser;

    protected String commandName = "COMMAND";
    protected Object[][] subCommands = null;

    public CLICommand(final String mCommandName, Database mDatabase, String[] mArgv, String mPreviousCommands) {
        commandName = mCommandName;
        database = mDatabase;
        argv = mArgv;
        previousCommands = mPreviousCommands;
        argumentParser = new ArgumentParser(commandName, argv).parseArgs();
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
        return String.format("%s %s", previousCommands, commandName);
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
