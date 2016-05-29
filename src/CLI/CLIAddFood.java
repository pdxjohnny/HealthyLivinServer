package CLI;

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
public class CLIAddFood extends CLICommand {
    CLIAddFood(Database mDatabase, String[] mArgv, String mPreviousCommands) {
        super(mDatabase, mArgv, mPreviousCommands);
        COMMAND_NAME = "food";
//        database.createTable(COMMAND_NAME, "name", new String[]{"name", "category"});
    }

    @Override
    public void run() {
        System.out.println("Please input a food in the form of:");
        System.out.println("    name, category");
        Food food = new Food(database);
        food.fromStream(System.in);
        System.out.println("Here's the food you input");
        food.toStream(System.out);
    }
}
