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
        Action skillAction = user.createSpecialSkillAction();
        skillAction.execute(user, targets);
        triggeredSkillResult = skillAction.getLastResult();
        return true;
    }

    @Override
    public ActionResult createActionResult(Player user, Combatant[] targets, int oldHp, boolean success) {
        if (triggeredSkillResult == null) {
            return new ActionResult(
                getName(),
                0,
                0,
                true,
                "Triggered Free Special Skill",
                new ArrayList<>(),
                false,
                ""
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
