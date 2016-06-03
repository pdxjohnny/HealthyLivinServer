package cli;

/**
 * Created by John Andersen on 5/29/16.
 */

import net.carpoolme.db.Database;
import net.carpoolme.db.Table;
import net.carpoolme.healthylivin.cli.Activity;
import net.carpoolme.healthylivin.cli.Question;
import net.carpoolme.utils.BasicParser;
import net.carpoolme.utils.Prompt;

public class CLIExercise extends CLICommand {
    public static final int DEFAULT_NUMBER_OF_QUESTIONS = 5;

    public CLIExercise(Database mDatabase, String[] mArgv, String mPreviousCommands) {
        super("exercise", mDatabase, mArgv, mPreviousCommands);
        argumentParser
                .add("--ask", "Number of questions to ask");
    }

    @Override
    public void run() {
        Prompt prompt = new Prompt(System.in, System.out);
        System.out.printf("You will be asked a number of questions on your" +
                " physical ability. Answer these as bew you can to get a list of activities" +
                " that suit you best. Y to begin ");
        if (!prompt.confirm()) {
            return;
        }
        int questionsToAsk = argumentParser.getInteger("ask");
        if (questionsToAsk < 1) {
            questionsToAsk = DEFAULT_NUMBER_OF_QUESTIONS;
        }
        BasicParser parser = new BasicParser();
        Object[][] points = setupPoints();
        Table questions;
        Table activities;
        Question question;
        // Loads the Question table
        new Question(database);
        // Loads the Activity table
        new Activity(database);
        try {
            questions = (Table) database.get("Question");
        } catch (IndexOutOfBoundsException e) {
            System.out.print(String.format("FATAL: %s%n", e.getMessage()));
            System.out.print(String.format("Try adding some questions first%n"));
            return;
        }
        try {
            activities = (Table) database.get("Activity");
        } catch (IndexOutOfBoundsException e) {
            System.out.print(String.format("FATAL: %s%n", e.getMessage()));
            System.out.print(String.format("Try adding some activities first%n"));
            return;
        }
        for (int i = 0; i < questions.size(); ++i) {
            question = new Question(database);
            if (!question.Unmarshal(questions.get(i))) {
                continue;
            }
            // Ask the question and if it is that last question or it is a question
            // that is marked as wanting to end on it then we stop asking questions
            if (!question.ask(prompt, points) || (int) parser.getKey(points, "yes", 0) >= questionsToAsk) {
                break;
            }
        }
    }

    private Object[][] setupPoints() {
        return new Object[][] {
                new Object[] {"walking", 0},
                new Object[] {"running", 0},
                new Object[] {"cycling", 0},
                new Object[] {"gym", 0},
                new Object[] {"hiking", 0},
                new Object[] {"yes", 0}
        };
    }

    private String highestPointCategory() {
        return "walking";
    }
}
