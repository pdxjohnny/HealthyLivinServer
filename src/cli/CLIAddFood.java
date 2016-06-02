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
public class CLIAddFood extends CLICommand {
    CLIAddFood(Database mDatabase, String[] mArgv, String mPreviousCommands) {
        super(mDatabase, mArgv, mPreviousCommands);
        COMMAND_NAME = "food";
    }

    @Override
    public void run() {
        System.out.printf("Please input a food in the form of name, category: ");
        Food food = new Food(database);
        food.fromStream(System.in);
        System.out.println("Here's the food you input");
        food.toStream(System.out);
        food.save();
    }
}
