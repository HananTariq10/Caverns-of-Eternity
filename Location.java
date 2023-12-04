import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Location {
    private String lName;
    private String lDescription;
    private ArrayList<Item> items;
    private HashMap<String, Location> connections;

    public Location(String pNameL, String pDescriptionL){
        lName = pNameL;
        lDescription = pDescriptionL;
        items = new ArrayList<Item>();
        connections = new HashMap<String, Location>();
    }

    public String getName(){
        return lName;
    }

    public String getDescription(){
        return lDescription;
    }

    public void setName (String pName){
        lName = pName;
    }

    public void setDescription(String pDescription){
        lDescription = pDescription;
    }

    public void addItem(Item pItem){
        items.add(pItem);
    }

    public boolean hasItem(String sItem){
        for (Item i : items){
            if (i.getName().equalsIgnoreCase(sItem)){
                return true;
            }
        }
        return false;
    }

    public Item getItem(String sItem){
        for (Item i : items){
            if (i.getName().equalsIgnoreCase(sItem)){
                return i;
            }
        }
        return null;
        }

    public Item getItem(int index){
        if (index>=0 && index<items.size()){
            return items.get(index);
        }
        else{
            return null;
        }
    }

    public int numItems(){
        return items.size();
    }

    public Item removeItem(String sItem){
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getName().equalsIgnoreCase(sItem)) {
                iterator.remove();
                return item;
            }
        }
        return null;
    }

    public void connect (String dName, Location lName){
        connections.put(dName, lName);
    }

    public boolean canMove(String dName){
        if (connections.containsKey(dName)) return true;
        return false;
    }

    public Location getLocation(String dName){
        return connections.get(dName);
    }





}
