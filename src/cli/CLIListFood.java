package cli;

/**
 * Created by John Andersen on 5/29/16.
 */

import net.carpoolme.db.Database;
import net.carpoolme.healthylivin.cli.Food;

/*
For each type of food that the consumer wants to
purchase, what is the most healthiest of that category ordered by a
respective health index (the quantity of sodium, sugar, fat, etc.).
 */
public class CLIListFood extends CLICommand {
    CLIListFood(Database mDatabase, String[] mArgv, String mPreviousCommands) {
        super(mDatabase, mArgv, mPreviousCommands);
        COMMAND_NAME = "food";
    }

    @Override
    public void run() {
        Food food = new Food(database);
        String orderBy = "name";
        if (argv.length > 0) {
            orderBy = argv[0];
        }
        food.orderBy(System.out, orderBy);
    }
}
