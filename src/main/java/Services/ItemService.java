package Services;

import Modelo.Item;
import Services.Exceptions.ItemException;

import java.util.Collection;

public interface ItemService {
    public int addItem(String id, Item item);
    public Collection<Item> getItems();
    public  Item getItem(String id);
    public Item editItem(String id, Item item) throws ItemException;
    public void deleteItem(String id);
}
