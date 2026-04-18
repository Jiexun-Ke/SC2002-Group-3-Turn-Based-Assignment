package model.actions;
import java.util.ArrayList;
import model.combatants.*;
import model.items.*;

public class UseItemAction extends Action{
    private final Item item;

    public UseItemAction(Item item){
        super("Use Item");
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
            lastResult = new ActionResult(
            getName(),
            0,
            0,
            false,
            null,
            new ArrayList<>(),
            true,
            "No item selected"
            );
            return;
        }

        if (user instanceof Player player) {
            int oldHp = player.getCurrentHP();

            boolean success = item.use(player, targets);

            if (success) {
                player.removeItem(item);
            }

            lastResult = item.createActionResult(player, targets, oldHp, success);
        }
    }

    @Override
    public String getName() {
        return "Use Item";
    }
}
