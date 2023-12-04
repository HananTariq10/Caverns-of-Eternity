import java.util.HashMap;
import java.util.ArrayList;
import java.util.ListIterator;


public class Location {
    private String name;
    private String description;
    private ArrayList<Item> items;
    private HashMap<String, Location> connections;

    public Location(String name, String description) {
        this.name = name;
        this.description = description;
        this.items = new ArrayList<Item>();
        this.connections = new HashMap<String, Location>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public boolean hasItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }

    public Item getItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public Item getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        } else {
            return null;
        }
    }

    public int numItems() {
        return items.size();
    }

    public Item removeItem(String itemName) {
        ListIterator<Item> iterator = items.listIterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getName().equalsIgnoreCase(itemName)) {
                iterator.remove();
                return item;
            }
        }
        return null;
    }

    public void connect(String direction, Location otherLocation) {
        connections.put(direction, otherLocation);
    }

    public boolean canMove(String direction) {
        return connections.containsKey(direction);
    }

    public Location getLocation(String direction) {
        return connections.get(direction);
    }
}
