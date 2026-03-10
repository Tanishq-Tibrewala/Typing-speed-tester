import java.util.Scanner;
import java.util.Random;
import java.io.*;

public class TypingSpeedTester {

    static Scanner sc = new Scanner(System.in);
    static Random rand = new Random();

    static int testsCompleted = 0;
    static double totalWpm = 0;

    static String[] easy = {
            "The cat sat on the mat.",
            "Java is a powerful language.",
            "Practice typing every day.",
            "Learning coding takes patience."
    };

    static String[] medium = {
            "Programming requires patience and consistent practice.",
            "Object oriented programming helps organize complex programs.",
            "Software development involves design testing and debugging."
    };

    static String[] hard = {
            "Artificial intelligence is transforming modern software development.",
            "Efficient algorithms and data structures improve program performance.",
            "Computer science combines mathematical reasoning with engineering."
    };

    public static void main(String[] args) {

        int bestScore = loadHighScore();

        System.out.println("=================================");
        System.out.println("        Java Typing Speed Trainer");
        System.out.println("=================================");
        System.out.println("Best Speed So Far: " + bestScore + " WPM");

        while (true) {

            int difficulty = chooseDifficulty();

            String text = getParagraph(difficulty);

            System.out.println("\nType the following text:\n");
            System.out.println(text);

            System.out.println("\nPress ENTER when ready...");
            sc.nextLine();

            countdown();

            long startTime = System.currentTimeMillis();

            String userInput = sc.nextLine();

            long endTime = System.currentTimeMillis();

            double timeTaken = (endTime - startTime) / 1000.0;

            double wpm = ((double) userInput.length() / 5) / timeTaken * 60;

            int correctChars = 0;
            int errors = 0;

            int minLength = Math.min(text.length(), userInput.length());

            for (int i = 0; i < minLength; i++) {

                if (text.charAt(i) == userInput.charAt(i))
                    correctChars++;
                else
                    errors++;
            }

            errors += Math.abs(text.length() - userInput.length());

            double accuracy = ((double) correctChars / text.length()) * 100;

            testsCompleted++;
            totalWpm += wpm;

            System.out.println("\n=================================");
            System.out.println("             RESULTS");
            System.out.println("=================================");

            System.out.printf("Time Taken        : %.2f seconds\n", timeTaken);
            System.out.printf("Typing Speed      : %.0f WPM\n", wpm);
            System.out.printf("Accuracy          : %.2f%%\n", accuracy);
            System.out.println("Errors            : " + errors);

            if ((int) wpm > bestScore) {

                System.out.println("\nNEW HIGH SCORE!");
                bestScore = (int) wpm;
                saveHighScore(bestScore);
            }

            System.out.println("Best Speed        : " + bestScore + " WPM");

            System.out.println("---------------------------------");
            System.out.printf("Tests Completed   : %d\n", testsCompleted);
            System.out.printf("Average Speed     : %.0f WPM\n", totalWpm / testsCompleted);
            System.out.println("=================================");

            System.out.println("\n1. Try Again");
            System.out.println("2. Exit");

            int again = getChoice();

            if (again == 2)
                break;
        }

        System.out.println("\nThank you for using Java Typing Speed Trainer!");
        sc.close();
    }

    public static void countdown() {

        System.out.println("\nGet ready...");

        for (int i = 3; i > 0; i--) {

            System.out.println(i);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("START!");
    }

    public static int chooseDifficulty() {

        while (true) {

            System.out.println("\nSelect Difficulty:");
            System.out.println("1. Easy");
            System.out.println("2. Medium");
            System.out.println("3. Hard");
            System.out.print("Enter choice: ");

            int choice = getChoice();

            if (choice >= 1 && choice <= 3)
                return choice;

            System.out.println("Invalid choice. Try again.");
        }
    }

    public static String getParagraph(int difficulty) {

        if (difficulty == 1)
            return easy[rand.nextInt(easy.length)];

        if (difficulty == 2)
            return medium[rand.nextInt(medium.length)];

        return hard[rand.nextInt(hard.length)];
    }

    public static int getChoice() {

        while (!sc.hasNextInt()) {

            System.out.println("Enter a valid number.");
            sc.next();
        }

        int choice = sc.nextInt();
        sc.nextLine();
        return choice;
    }

    public static int loadHighScore() {

        try {

            File file = new File("highscore.txt");

            if (!file.exists())
                return 0;

            BufferedReader reader = new BufferedReader(new FileReader(file));

            int score = Integer.parseInt(reader.readLine());

            reader.close();

            return score;

        } catch (Exception e) {

            return 0;
        }
    }

    public static void saveHighScore(int score) {

        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter("highscore.txt"));

            writer.write(String.valueOf(score));

            writer.close();

        } catch (Exception e) {

            System.out.println("Error saving high score.");
        }
    }
}