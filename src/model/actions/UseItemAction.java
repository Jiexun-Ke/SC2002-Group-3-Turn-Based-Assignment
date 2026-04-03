package model.actions;
import model.combatants.*;
import model.items.*;

public class UseItemAction extends Action{
    private Item item;

    public UseItemAction(Item item){
        super("UseItemAction");
        this.item = item;
    }

    @Override
    public String getDescription(){
        return "Uses an item: " + item.getName();
    }

    @Override
    public void execute(Combatant user, Combatant[] targets){
        if(user instanceof Player){
            item.use((Player) user, targets);
        }
    }
}
