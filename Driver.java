import java.util.Scanner;

public class Driver {
    private static Location currLocation;
    private static ContainerItem myInventory;

    public static void main(String[] args) { 
        createWorld();
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Welcome to caverna-of-eternity");
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
                            if (containerItem.hasItem(itemName)){
                                Item item = containerItem.removeItem(itemName);
                                myInventory.addItem(item);
                                System.out.println("You have taken the " + itemName + " from " + cItem + ".");
                            }
                            else {
                                System.out.println("The container doesn't contain the item requested");
                            }
                        } else {
                            System.out.println("That is not a container item.");
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
                            Item container = currLocation.getItem(commandWords[3]);
                            if (container instanceof ContainerItem) {
                                Item item = myInventory.removeItem(commandWords[1]);
                                ContainerItem containerItem = (ContainerItem) container;
                                containerItem.addItem(item);
                                System.out.println("You have put the " + commandWords[1] + " in " + commandWords[3] + ".");
                            } else {
                                System.out.println("Cannot find that container or maybe the item is not a container type.");
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
        Item photo = new Item("Photo", "Decoration", "This is a picture of the last Ottomon empror");
        Item flower = new Item("Flower", "Plant", "It's is dead");
        Item skeleton = new Item("Skeleton", "Human", "This is scary");
        
        hallway.addItem(photo);
        hallway.addItem(flower);
        hallway.addItem(skeleton);


        //Items for bedroom
        Item bed = new Item("Bed", "Furniture", "This is't mattress on it");
        Item candle = new Item("Candle", "Light", "It's not lit");
        Item book = new Item("Book", "Education", "This is an old religious scripture");
        
        bedroom.addItem(bed);
        bedroom.addItem(candle);
        bedroom.addItem(book);


        //Items for livingroom
        Item sofa = new Item("Sofa", "Furniture", "This is a black leather sofa");
        Item mirror = new Item("Mirror", "Furniture", "This is half broken blur mirror");
        Item fireplace = new Item("Fireplace", "Comfort", "This is warm");
        
        livingroom.addItem(sofa);
        livingroom.addItem(mirror);
        livingroom.addItem(fireplace);


        currLocation = kitchen;
        myInventory = new ContainerItem("Backpack", "Container", "Stores items you acquire during the game.");


        //Container Items
        ContainerItem Bottle = new ContainerItem("Bottle", "Storage", "An old glass bottle.");
        Bottle.addItem(new Item("Wine", "Drink", "Century old red wine."));
        kitchen.addItem(Bottle);

        ContainerItem desk = new ContainerItem("Charit", "Furniture", "A broken desk");
        desk.addItem(new Item("Letter", "Paper", "An old letter written in Arabic"));
        livingroom.addItem(desk);

        ContainerItem chest = new ContainerItem("Chset", "Security", "A strong chest made of gold and iron");
        chest.addItem(new Item("Diamonds", "Jewelery", "A priceless diamond that belongs to the Khan Dinesty"));
        bedroom.addItem(chest);

        ContainerItem closet = new ContainerItem("Closet", "Furniture", "An old dusty wooden closet");
        closet.addItem(new Item("Shirt", "Cloth", "A century old shirt that belong to the Arabs"));
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

