import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Location {
    private String name;
    private String description;
    private ArrayList<Item> items;

    //Constructor 
    public Location(String name, String description){
        this.name = name;
        this.description = description;
        this.items = new ArrayList<Item>();
    }

    //getter
    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }


    //setter
    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    //Method named addItem
    public void addItem(Item item){
        items.add(item);

    }

    //Method named hasItem to check Location's ArrayList conatins an item with same name 
    public boolean hasItem(String itemName){
        for(Item item : items){
            if (item.getName().equalsIgnoreCase(itemName)){
                return true;
            }
        }
        return false;
    }

    //Method named getItem to check k to see if an Item with that name is in the ArrayList and if so, it should return the matching Item object, otherwise, it should return null.
    public Item getItem(String itemName){
        for(Item item : items){
            if (item.getName().equalsIgnoreCase(itemName)){
                return item;
            }
        }
        return null;
    }

    //Method named getItem that returns the Item object in Location's ArrayList\
    public Item getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        } else {
            return null; // Invalid index, return null
        }
    }

    // Method named numItems that returns number of items in ArrayList
    public int numItems() {
        return items.size();
    }

    //Method named removeItem to remove an Item by name and return it
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
} 


   