import java.util.ArrayList;

public class ContainerItem extends Item {
    private ArrayList<Item> items;

    public ContainerItem(String name, String type, String description) {
        super(name, type, description);
        items = new ArrayList<>();
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

    public Item removeItem(String itemName) {
        Item removedItem = null;
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                removedItem = item;
                items.remove(item);
                break;
            }
        }
        return removedItem;
    }

    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder();
        temp.append(super.toString());
        temp.append(" that contains:\n");
        for (Item i : items){
            temp.append("+ ");
            temp.append(i.getName());
            temp.append("\n");
        }

        return temp.toString();
    }
}

