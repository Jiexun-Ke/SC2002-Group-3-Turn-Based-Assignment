package model.targeting;

import control.BattleContext;
import control.TargetSelector;
import model.combatants.Combatant;
import model.combatants.Player;

public class SpecialSkillTargetingStrategy implements TargetingStrategy {
    @Override
    public Combatant[] selectTargets(
            Player user,
            BattleContext context,
            TargetSelector targetSelector
    ) {
        return targetSelector.selectSpecialSkillTargets(user, context);
    }
}