package cli;

/**
 * Created by John Andersen on 5/29/16.
 */

import net.carpoolme.db.Database;
import net.carpoolme.healthylivin.BasicData;
import net.carpoolme.healthylivin.cli.Store;

/*
For each type of food that the consumer wants to
purchase, what is the most healthiest of that category ordered by a
respective health index (the quantity of sodium, sugar, fat, etc.).
 */
public class CLIListStore extends CLICommand {
    public CLIListStore(Database mDatabase, String[] mArgv, String mPreviousCommands) {
        super("store", mDatabase, mArgv, mPreviousCommands);
        argumentParser
                .add("-count", "Display a count of how many stores were matched")
                .add("--order", "What to order by, name, health")
                .add("--category", "Category to match, grocery, restaurant");
    }

    @Override
    public void run() {
        if (argumentParser.help(System.out)) {
            return;
        }
        BasicData data = new Store(database);
        String category = argumentParser.getString("category");
        if (category.length() > 0) {
            data = data.select("category", category);
        }
        String orderBy = argumentParser.getString("order");
        if (orderBy.length() < 1) {
            orderBy = "name";
        }
        data.orderBy(System.out, orderBy);
        if (argumentParser.getBoolean("count")) {
            System.out.print(String.format("Number matching query : %d%n", data.size()));
        }
    }
}
