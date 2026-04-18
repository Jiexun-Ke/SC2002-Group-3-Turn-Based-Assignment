package control;

import model.items.Item;

public class ItemOption {
    private final Item item;

    public ItemOption(Item item) {
        this.item = item;
    }

    public String getName() {
        return item.getName();
    }

    public String getDescription() {
        return item.getDescription();
    }

    public Item getItem() {
        return item;
    }
}
