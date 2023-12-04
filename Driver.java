import java.util.Scanner;

public class Driver {
    private static Location currLocation;
    private static ContainerItem myInventory;

    public static void main(String[] args) {

        
        createWorld();
        

        

        try (Scanner scanner = new Scanner(System.in)) {
            while (true){
                System.out.println("Enter command: ");
                String command = scanner.nextLine();
                String[] commandWords = command.split(" ");

                switch (commandWords[0].toLowerCase()) {
                    case "quit":
                        System.exit(0);
                        break;

                        
                    case "look":
                        System.out.println(currLocation.getName() + " - " + currLocation.getDescription() + " currently has the following items:");
                        for (int i = 0; i < currLocation.numItems(); i++) {
                            System.out.println("+ " + currLocation.getItem(i).getName());
                        }
                        break;
                        
                    case "go":
                        if (commandWords.length < 2) {
                            System.out.println("Please specify a direction");
                        } else {
                            String direction = commandWords[1].toLowerCase(); 
                            if (!direction.equals("north") && !direction.equals("south") && 
                                !direction.equals("east") && !direction.equals("west")) {
                                System.out.println("That's not a valid direction. Valid directions are north, south, east, and west.");
                            } else if (currLocation.canMove(direction)) {
                                currLocation = currLocation.getLocation(direction);
                                System.out.println("Moved " + direction);
                            } else {
                                System.out.println("Cannot move in that direction");
                            }
                        }
                        break;
                            
    
                        
                    case "examine":
                        if (commandWords.length < 2) {
                            System.out.println("Please specify an item or container to examine.");
                        } else {
                            Item item = currLocation.getItem(commandWords[1]);
                            if (item instanceof ContainerItem) {
                                ContainerItem container = (ContainerItem) item;
                                System.out.println(container.toString());
                            } else if (item != null) {
                                System.out.println(item.toString());
                            } else {
                                System.out.println("Cannot find that item or container.");
                            }
                        }
                        
                        break;
                    

                    case "inventory":
                        System.out.println(myInventory.toString());
                        break;

                    case "take":
                    if (commandWords.length == 2) {
                        String itemName = commandWords[1];
                        if (currLocation.hasItem(itemName)) {
                            Item item = currLocation.removeItem(itemName);
                            myInventory.addItem(item);
                            System.out.println("You have taken the " + itemName + ".");
                        } else {
                            System.out.println("Cannot find that item here.");
                        }
                    }
                    else if (commandWords.length == 4 && commandWords[2].equalsIgnoreCase("from")){
                        String itemName = commandWords[1];
                        String cItem = commandWords[3];
                        Item thisContainer = currLocation.getItem(cItem);
                        if (thisContainer instanceof ContainerItem){
                            ContainerItem containerItem = (ContainerItem) thisContainer;
                            Item item = containerItem.removeItem(itemName);
                            myInventory.addItem(item);
                            System.out.println("You have taken the " + itemName + " from " + cItem + ".");
                        } else {
                            System.out.println("Cannot find that item here.");
                        }
                    }
                    else{
                        System.out.println("Please specify an item and/or a container (e.g., 'take key from chest' or 'take knife' .");
                    }

                        break;

                    case "drop":
                    if (commandWords.length < 2) {
                        System.out.println("Please specify an item to drop.");
                    }
                    else{
                        if (myInventory.hasItem(commandWords[1])) {
                            Item item = myInventory.removeItem(commandWords[1]);
                            currLocation.addItem(item);
                            System.out.println("You have dropped the " + commandWords[1] + ".");
                        } else {
                            System.out.println("Cannot find that item in your inventory.");
                        }
                    }
                    break;
                    
                    case "put":
                    if (commandWords.length < 3 || !commandWords[2].equals("in")) {
                        System.out.println("Please specify an item and a container (e.g., 'put book in desk').");
                    } else {
                        if (myInventory.hasItem(commandWords[1])) {
                            Item item = myInventory.removeItem(commandWords[1]);
                            Item container = currLocation.getItem(commandWords[3]);
                            if (container instanceof ContainerItem) {
                                ContainerItem containerItem = (ContainerItem) container;
                                containerItem.addItem(item);
                                System.out.println("You have put the " + commandWords[1] + " in " + commandWords[3] + ".");
                            } else {
                                System.out.println("Cannot find that container.");
                            }
                        } else {
                            System.out.println("Cannot find that item in your inventory.");
                        }
                    }
                    break;

                    case "help":
                        printHelp();
                        break;

                    default:
                        System.out.println("I don’t know how to do that");
                        break;
                }
            }
        }


    }


    public static void createWorld(){
        Location kitchen = new Location("Kitchen", "This place has stuff");
        Location hallway = new Location("Hallway", "This is a passageway");
        Location bedroom = new Location("Bedroom", "This is a place to sleep");
        Location livingroom = new Location ("Living Room", "This is a place to rest");


        kitchen.connect("north", hallway);
        hallway.connect("south", kitchen);
        hallway.connect("east", bedroom);
        bedroom.connect("west", hallway);
        bedroom.connect("south", livingroom);
        livingroom.connect("north", bedroom);
        livingroom.connect("west", kitchen);
        kitchen.connect("east", livingroom);

        //Items for kitchen
        Item knife = new Item("Knife", "Weapon", "This is sharp");
        Item turkey = new Item("Turkey", "Food", "This is delicious");
        Item plate = new Item("Plate", "Utensil", "This is fragile");
        
        kitchen.addItem(knife);
        kitchen.addItem(turkey);
        kitchen.addItem(plate);


        //Items for hallway
        Item painting = new Item("Painting", "Decoration", "This is expensive");
        Item flower = new Item("Flower", "Plant", "This is red");
        Item dog = new Item("Dog", "Animal", "This is cute");
        
        hallway.addItem(painting);
        hallway.addItem(flower);
        hallway.addItem(dog);


        //Items for bedroom
        Item bed = new Item("Bed", "Furniture", "This is comfortable");
        Item lamp = new Item("Lamp", "Light", "This is bright");
        Item book = new Item("Book", "Education", "This is informative");
        
        bedroom.addItem(bed);
        bedroom.addItem(lamp);
        bedroom.addItem(book);


        //Items for livingroom
        Item couch = new Item("Couch", "Furniture", "This is white");
        Item tv = new Item("TV", "Entertainment", "This is 5K");
        Item fireplace = new Item("Fireplace", "Comfort", "This is warm");
        
        livingroom.addItem(couch);
        livingroom.addItem(tv);
        livingroom.addItem(fireplace);


        currLocation = kitchen;
        myInventory = new ContainerItem("Backpack", "Container", "Stores items you acquire during the game.");


        //Container Items
        ContainerItem chest = new ContainerItem("Chest", "Storage", "An old, wooden chest");
        chest.addItem(new Item("Key", "Utility", "A small brass key"));
        kitchen.addItem(chest);

        ContainerItem desk = new ContainerItem("Desk", "Furniture", "A standing desk");
        desk.addItem(new Item("Laptop", "Electronic", "A worn-out 2010s Toshiba Laptop"));
        livingroom.addItem(desk);

        ContainerItem vault = new ContainerItem("Vault", "Security", "A very strong vault");
        vault.addItem(new Item("Gold Necklace", "Jewelery", "A beautiful gold necklace"));
        bedroom.addItem(vault);

        ContainerItem closet = new ContainerItem("Closet", "Furniture", "An old rusty closet");
        closet.addItem(new Item("Cloth", "Cloth", "A silky red table cloth"));
        hallway.addItem(closet);












    }



    public static void printHelp(){

        System.out.println("Available commands:");
        System.out.println("'look' - Look around in the current location.");
        System.out.println("'go (direction)' - Move to the direction specified (north, south, east, west).");
        System.out.println("'take (item)' - Take an item from the current location.");
        System.out.println("'drop (item)' - Drop an item from your inventory.");
        System.out.println("'inventory' - Check the items in your inventory.");
        System.out.println("'examine (item)' - Get a description of the item.");
        System.out.println("'quit' - Quit the game.");
    }
}

