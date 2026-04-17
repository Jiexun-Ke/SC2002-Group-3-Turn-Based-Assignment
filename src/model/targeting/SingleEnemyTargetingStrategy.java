package model.targeting;

import control.BattleContext;
import control.TargetSelector;
import model.combatants.Combatant;
import model.combatants.Player;

public class SingleEnemyTargetingStrategy implements TargetingStrategy {
    @Override
    public Combatant[] selectTargets(
            Player user,
            BattleContext context,
            TargetSelector targetSelector
    ) {
        return targetSelector.selectSingleEnemy(context);
    }
}