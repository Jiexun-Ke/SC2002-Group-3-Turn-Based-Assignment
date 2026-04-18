package model.items;

import java.util.ArrayList;
import model.actions.Action;
import model.actions.ActionResult;
import model.combatants.*;
import model.targeting.SpecialSkillTargetingStrategy;

public class PowerStone extends Item{

    private ActionResult triggeredSkillResult;

    public PowerStone(){
        super("Power Stone", new SpecialSkillTargetingStrategy());
    }

    @Override
    public String getDescription(){
        return "Trigger the special skill effect once when used, but does not start or change the skill's cooldown timer. In short, free extra use of the skill";
    }

    

    @Override
    public boolean use(Player user, Combatant[] targets){
        triggeredSkillResult = null;
        
        if (user == null) {
        return false;
    }

        Action skillAction = user.createSpecialSkillAction();
        if (skillAction == null) {
            return false;
        }

        skillAction.execute(user, targets);
        triggeredSkillResult = skillAction.getLastResult();

        return triggeredSkillResult != null && !triggeredSkillResult.isPrevented();
    }

    @Override
    public ActionResult createActionResult(Player user, Combatant[] targets, int oldHp, boolean success) {
        if (triggeredSkillResult == null) {
            return new ActionResult(
                getName(),
                0,
                0,
                false,
                null,
                new ArrayList<>(),
                true,
                "Power Stone failed to trigger the special skill!"
            );
        }

        return new ActionResult(
            getName(),
            triggeredSkillResult.getDamageDealt(),
            triggeredSkillResult.getHealingDone(),
            triggeredSkillResult.isAppliedStatusEffect(),
            triggeredSkillResult.getStatusEffectName(),
            triggeredSkillResult.getTargetSummaries(),
            triggeredSkillResult.isPrevented(),
            triggeredSkillResult.getReason()
        );
    }
}
