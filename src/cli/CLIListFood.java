package cli;

/**
 * Created by John Andersen on 5/29/16.
 */

import net.carpoolme.db.Database;
import net.carpoolme.healthylivin.BasicData;
import net.carpoolme.healthylivin.cli.Food;

/*
For each type of food that the consumer wants to
purchase, what is the most healthiest of that category ordered by a
respective health index (the quantity of sodium, sugar, fat, etc.).
 */
public class CLIListFood extends CLICommand {
    public CLIListFood(Database mDatabase, String[] mArgv, String mPreviousCommands) {
        super("food", mDatabase, mArgv, mPreviousCommands);
        argumentParser
                .add("--order", "What to order by, name, category, sodium, sugar, fat")
                .add("--category", "Category to match");
    }

    @Override
    public void run() {
        if (argumentParser.help(System.out)) {
            return;
        }
        BasicData data = new Food(database);
        String category = argumentParser.getString("category");
        if (category.length() > 0) {
            data = data.select("category", category);
        }
        String orderBy = argumentParser.getString("order");
        if (orderBy.length() < 1) {
            orderBy = "name";
        }
        data.orderBy(System.out, orderBy);
    }
}
