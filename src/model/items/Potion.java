package model.items;
import java.util.ArrayList;
import java.util.List;
import model.actions.ActionResult;
import model.combatants.*;
import model.targeting.SelfTargetingStrategy;

public class Potion extends Item {
    public Potion(){
        super("Potion", new SelfTargetingStrategy());
    }


    @Override
    public String getDescription(){
        return "A potion that heals 100 HP when used.";
    }

    @Override
    public boolean use(Player user, Combatant[] targets){
            user.heal(100);
            return true;
        }

    @Override
    public ActionResult createActionResult(Player user, Combatant[] targets, int oldHp, boolean success) {
        int healed = user.getCurrentHP() - oldHp;
        List<String> targetSummaries = new ArrayList<>();
        targetSummaries.add(user.getName() + " HP: " + user.getCurrentHP() + "/" + user.getMaxHP());

        return new ActionResult(
            getName(),
            0,
            healed,
            false,
            null,
            targetSummaries,
            false,
            ""
        );
    }
}


