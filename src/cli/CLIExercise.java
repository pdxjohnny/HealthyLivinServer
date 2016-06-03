package cli;

/**
 * Created by John Andersen on 5/29/16.
 */

import net.carpoolme.db.Database;
import net.carpoolme.db.Table;
import net.carpoolme.healthylivin.cli.Activity;
import net.carpoolme.healthylivin.cli.Question;
import net.carpoolme.storage.MockStorage;
import net.carpoolme.utils.BasicParser;
import net.carpoolme.utils.JSONParser;
import net.carpoolme.utils.Logging;
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
        if (argumentParser.help(System.out)) {
            return;
        }
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
        Activity activity;
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
            System.out.print(String.format("INFO: Activity size %d%n", activities.size()));
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
            if (question.ask(prompt, points) || (int) parser.getKey(points, "yes", 0) >= questionsToAsk) {
                break;
            }
        }
        // Find all the activities which have point values less than or equal to the
        // values the user has. Points are a measure of how active you are in a
        // category so activities with points less than the number we have should be
        // easy for the user
        String currentCategory;
        Table allApplicable = new Table(new MockStorage(), "activity", new String[]{"activity"});
        allApplicable.disableDuplicates();
        Table currentApplicable;
        for (int i = 0; i < points.length - 1; ++i) {
            currentCategory = (String) points[i][0];
            try {
                currentApplicable = activities.selectLessThanOrEqual(currentCategory, (int) points[i][1]);
                System.out.print(String.format("INFO: currentApplicable size %d for %s as %d%n", currentApplicable.size(), currentCategory, (int) points[i][1]));
            } catch (IndexOutOfBoundsException ignored) {
                continue;
            }
            for (int j = 0; j < currentApplicable.size(); ++j) {
                System.out.print(String.format(Logging.INFO + ": Adding %s to list of activities %n", new JSONParser().toString(currentApplicable.get(j))));
                allApplicable.add(currentApplicable.get(j));
            }
        }
        for (int i = 0; i < allApplicable.size(); ++i) {
            activity = new Activity(database);
            if (!activity.Unmarshal(allApplicable.get(i))) {
                continue;
            }
            System.out.printf(String.format("%-4d - %s%n", i, activity));
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
}
