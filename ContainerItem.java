import java.util.ArrayList;

public class ContainerItem extends Item{

    private ArrayList<Item> items;



    public ContainerItem(String name, String type, String description) {
        super(name, type, description);
        items = new ArrayList<>();
    }

    public void addItem(Item lItem){
        items.add(lItem);
    }

    
    public boolean hasItem(String iName){
        for (Item i : items){
            if (i.getName().equalsIgnoreCase(iName)){
                return true;
            }
        }
        return false;
    }

    public Item removeItem(String iName){
        for (int i = 0; i < items.size(); i++){
            if (items.get(i).getName().equalsIgnoreCase(iName)) {
                return items.remove(i);
            }
        }
        return null;
    }
    
    @Override
    public String toString(){
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
