package model.actions;
import model.combatants.*;
import model.items.*;

public class UseItemAction extends Action{
    private final Item item;

    public UseItemAction(Item item){
        super("UseItemAction");
        this.item = item;
    }

    @Override
    public String getDescription(){
        if (item == null) {
        return "Uses an item.";
        }
        return "Uses an item: " + item.getName();
    }

    @Override
    public void execute(Combatant user, Combatant[] targets){
        if(item == null){
            return;
        }

        if (user instanceof Player player) {
            item.use(player, targets);
            player.removeItem(item);
        }
    }

    @Override
    public String getName() {
        return "Use Item";
    }
}
