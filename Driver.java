import java.util.Scanner;

public class Driver {
    public static Location currLocation;

    public static void main(String[] args) {
        // Create a new Location object and add items
        currLocation = new Location("Kitchen", "A dark kitchen whose lights are flickering");
        currLocation.addItem(new Item("Knife", "A sharp kitchen knife"));
        currLocation.addItem(new Item("Turkey", "A roasted turkey"));
        currLocation.addItem(new Item("Plate", "A clean white plate"));

        // Create a Scanner object to read user input
        Scanner scanner = new Scanner(System.in);

        // Enter an "infinite" loop
        while (true) {
            // Prompt the user for a command
            System.out.print("Enter command: ");
            String command = scanner.nextLine();

            // Split the command into individual words
            String[] words = command.split(" ");

            // Handle different commands using a switch-case structure
            switch (words[0].toLowerCase()) {
                case "quit":
                    System.out.println("Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;

                case "look":
                    System.out.println(currLocation.getName() + " - " + currLocation.getDescription() + " currently has the following items:");
                    for (Item item : currLocation.items) {
                        System.out.println("+ " + item.getName());
                    }
                    break;

                case "examine":
                    if (words.length > 1) {
                        String itemName = words[1];
                        Item item = currLocation.getItem(itemName);
                        if (item != null) {
                            System.out.println(item);
                        } else {
                            System.out.println("Cannot find that item.");
                        }
                    } else {
                        System.out.println("Please specify an item to examine.");
                    }
                    break;

                default:
                    System.out.println("I don't know how to do that.");
            }
        }
    }
}
