import java.util.Scanner;

public class Driver {
    private static Location currLocation;
    private static ContainerItem myInventory;

    public static void main(String[] args) {
        // Create a Scanner object for user input
        Scanner scanner = new Scanner(System.in);

        // Welcome Output Statement 
        System.out.println("Welcome to Caverns of Eternity");

        // Create the world and initializing the starting location
        createWorld();

        while (true) {
            // user for a command
            System.out.print("Enter command: ");
            String command = scanner.nextLine();

            // Spliting the command into individual words
            String[] words = command.split(" ");

            // Handling different commands using the switch-case structure
            switch (words[0].toLowerCase()) {
                case "quit":
                    System.out.println("Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;

                case "look":
                    System.out.println(currLocation.getName() + " - " + currLocation.getDescription() + " currently has the following items:");
                    for (int i = 0; i < currLocation.numItems(); i++) {
                        System.out.println("+ " + currLocation.getItem(i).getName());
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

                case "inventory":
                    System.out.println(myInventory.toString());
                    break;

                case "take":
                    if (words.length > 1) {
                        String itemName = words[1];
                        Item item = currLocation.getItem(itemName);
                        if (item != null) {
                            // Adding item to the inventory
                            myInventory.addItem(currLocation.removeItem(itemName));
                            System.out.println("You have taken the " + itemName + ".");
                        } else {
                            System.out.println("Cannot find that item at this location.");
                        }
                    } else {
                        System.out.println("Please specify an item to take.");
                    }
                    break;

                case "drop":
                    if (words.length > 1) {
                        String itemName = words[1];
                        Item item = myInventory.removeItem(itemName);
                        if (item != null) {
                            // Removing item from the inventory and adding it to the current location
                            currLocation.addItem(item);
                            System.out.println("You have dropped the " + itemName + ".");
                        } else {
                            System.out.println("Cannot find that item in your inventory.");
                        }
                    } else {
                        System.out.println("Please specify an item to drop.");
                    }
                    break;

                case "help":
                    printHelp();
                    break;

                case "go":
                    if (words.length > 1) {
                        String direction = words[1].toLowerCase();
                        if (!direction.equals("north") && !direction.equals("south") && !direction.equals("east") && !direction.equals("west")){
                            System.out.println("That's not a valid direction. Valid directions are north, south, east and west");
                        }
                        if (currLocation.canMove(words[1])){
                            currLocation = currLocation.getLocation(words[1]);
                            System.out.println("Moved " + words[1]);
                        }
                        else{
                            System.out.println("Cannot move in that direction.");
                        }
                    } else {
                        System.out.println("Please specify a direction to go.");
                    }
                    break;

                default:
                    System.out.println("I don't know how to do that.");
            }
        }
    }

    public static void createWorld() {
        // Creating Location objects and adding items
        Location kitchen = new Location("Kitchen", "A dark kitchen whose lights are flickering");
        Location hallway = new Location("Hallway", "A dimly lit hallway");
        Location bedroom = new Location("Bedroom", "A cozy bedroom with a bed");
        Location livingRoom = new Location("Living Room", "A spacious living room");

        kitchen.connect("north", hallway);
        hallway.connect("south", kitchen);
        hallway.connect("east", livingRoom);
        livingRoom.connect("west", hallway);
        bedroom.connect("west", hallway);
        hallway.connect("east", bedroom);

        kitchen.addItem(new Item("Knife", "Weapon", "A sharp kitchen knife"));
        kitchen.addItem(new Item("Turkey", "Food", "A roasted turkey"));
        kitchen.addItem(new Item("Plate", "Dinnerware", "A clean white plate"));
        bedroom.addItem(new Item("Lamp", "Furniture", "A table lamp"));

        currLocation = kitchen; // Setting the starting location
        myInventory = new ContainerItem("Inventory", "Container", "Your character's inventory");

    }

    public static void printHelp(){
        System.out.println("Supported commands:");
        System.out.println("quit - Quit the game");
        System.out.println("look - Examine the current location's items");
        System.out.println("examine [item] - Examine an item in the location");
        System.out.println("inventory - View your character's inventory");
        System.out.println("take [item] - Take an item from the current location");
        System.out.println("drop [item] - Drop an item from your inventory");
        System.out.println("go [direction] - Move to the direction specified (north, south, east and west)");
    }
}
