package cli;

/**
 * Created by John Andersen on 5/29/16.
 */

import net.carpoolme.db.Database;
import net.carpoolme.healthylivin.cli.Question;

public class CLIAddQuestion extends CLICommand {
    public CLIAddQuestion(Database mDatabase, String[] mArgv, String mPreviousCommands) {
        super("question", mDatabase, mArgv, mPreviousCommands);
    }

    @Override
    public void run() {
        System.out.printf("Please input a question in the form of question (no commas), walking," +
                " running, cycling, gym, hiking (points to be awarded towards" +
                " these categories on a yes answer, must be a whole number), " +
                " need to stop after this question (yes or no): ");
        Question data = new Question(database);
        data.fromStream(System.in);
        System.out.println("Here's the question you input");
        data.toStream(System.out);
        data.save();
    }
}
