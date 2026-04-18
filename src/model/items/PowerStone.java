package model.items;

import java.util.ArrayList;
import java.util.List;
import model.actions.ActionResult;
import model.combatants.*;
import model.targeting.SpecialSkillTargetingStrategy;

public class PowerStone extends Item{
    public PowerStone(){
        super("Power Stone", new SpecialSkillTargetingStrategy());
    }

    @Override
    public String getDescription(){
        return "Trigger the special skill effect once when used, but does not start or change the skill's cooldown timer. In short, free extra use of the skill";
    }

    

    @Override
    public void use(Player user, Combatant[] targets){
        user.useSpecialSkillWithoutCooldown(targets);
    }

    @Override
    public ActionResult createActionResult(Player user, Combatant[] targets, int oldHp) {
        List<String> targetSummaries = new ArrayList<>();

        if (targets != null) {
            for (Combatant target : targets) {
                if (target != null) {
                    targetSummaries.add(target.getName() + " HP: " + target.getCurrentHP() + "/" + target.getMaxHP());
                }
            }
        }

        return new ActionResult(
            getName(),
            0,
            0,
            false,
            null,
            targetSummaries,
            false,
            ""
        );
    }
}
