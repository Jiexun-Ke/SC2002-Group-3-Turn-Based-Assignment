package model.actions;
import model.combatants.*;
import model.items.*;

public class UseItemAction implements Action{
    private Item item;

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
        if(item != null){
            item.use(user, targets);
        }
    }

    @Override
    public String getName() {
        return "Use Item";
    }
}
