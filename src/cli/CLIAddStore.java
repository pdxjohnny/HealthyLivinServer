package cli;

/**
 * Created by John Andersen on 5/29/16.
 */

import net.carpoolme.db.Database;
import net.carpoolme.healthylivin.cli.Store;

/*
For each type of food that the consumer wants to
purchase, what is the most healthiest of that category ordered by a
respective health index (the quantity of sodium, sugar, fat, etc.).
 */
public class CLIAddStore extends CLICommand {
    public CLIAddStore(Database mDatabase, String[] mArgv, String mPreviousCommands) {
        super("store", mDatabase, mArgv, mPreviousCommands);
    }

    @Override
    public void run() {
        System.out.printf("FYI, health index is the amount of healthy items that can be found at a store");
        System.out.printf("Please input a store in the form of name, health index: ");
        Store data = new Store(database);
        data.fromStream(System.in);
        System.out.println("Here's the store you input");
        data.toStream(System.out);
        data.save();
    }
}
