package rockpaperscissors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

class User {
    String name;
    int points = 0;

    public void setRating(String name) {
//        System.out.println(new File(".").getAbsoluteFile());
        File f = new File("rating.txt");
        try {
            Scanner reader = new Scanner(f);
            while(reader.hasNextLine()) {
                String data = reader.nextLine();
                this.name = data.split(" ")[0];
                if (this.name.equals(name)) {
                    this.points = Integer.parseInt(data.split(" ")[1]);
                }
            }
        } catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            this.name = name;
//            this.points = 0;
        }
    }
}
public class Main {

    static ArrayList<String> choices = new ArrayList<>(3);
    public static void main(String[] args) {
        // write your code here
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = scan.nextLine();
        System.out.printf("Hello, %s\n", name);
        User user = new User();
        user.setRating(name);
        String options = scan.nextLine();
        if (options.isBlank()) {
            choices.add("rock");
            choices.add("paper");
            choices.add("scissors");
        } else {
            choices.addAll(Arrays.asList(options.split(","))); // shortcut to for loop.
        }
        System.out.println("Okay, let's start");

        while (true) {
            String choice = scan.nextLine();
            if (!validateChoice(choice)) {
                System.out.println("Invalid input");
            }
            String compChoice = choiceGenerator(choices.size());
            if (choice.equals("!exit")) {
                System.out.println("Bye!");
                break;
            } else if (choice.equals("!rating")) {
                System.out.printf("Your rating: %d\n", user.points);
            } else if (compChoice.equals(choice)) {
                user.points += 50;
                System.out.printf("There is a draw (%s)\n",compChoice);
            } else {
                if (play(choice, compChoice, user)) {
                    user.points += 100;
                    System.out.printf("Well done. The computer chose %s and failed\n", compChoice);
                } else {
                    System.out.printf("Sorry, but the computer chose %s\n", compChoice);
                }
            }
        }
    }

// rock,gun,lightning,devil,dragon,water,air,paper,sponge,wolf,tree,human,snake,scissors,fire

    private static boolean validateChoice(String choice) {
        if (choice.equals("!exit") || choice.equals("!rating")) {
            return true;
        }
        for (String c : choices) {
            if (c.equals(choice)) {
                return true;
            }
        }
        return false;
    }

    private static String choiceGenerator(int bound) {
        Random random = new Random();
        return choices.get(random.nextInt(bound));
    }

    private static boolean play(String choice, String compChoice, User user) {
        int half = (choices.size() - 1) / 2;
        int indexOfChoice = choices.indexOf(choice);
        int counter = 0;
        boolean flag = false;
        for (int i = indexOfChoice + 1; counter < choices.size(); i++, counter++) {
            if (i >= choices.size()) {
                i = 0;
            }
            if (counter >= half) {
                flag = true;
            }
            if (choices.get(i).equals(compChoice)) {
                break;
            }
        }
        return flag;
    }
//        if (choice.equals("rock")) {
//            if (compChoice.equals("paper")) {
//                return false;
//            } else {
//                user.points += 100;
//                return true;
//            }
//        } else if (choice.equals("paper")) {
//            if (compChoice.equals("scissors")) {
//                return false;
//            } else {
//                user.points += 100;
//                return true;
//            }
//        } else {
//            if (compChoice.equals("rock")) {
//                return false;
//            } else {
//                user.points += 100;
//                return true;
//            }
//        }
}
