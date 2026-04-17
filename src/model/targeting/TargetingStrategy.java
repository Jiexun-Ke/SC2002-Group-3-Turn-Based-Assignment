package model.targeting;

import control.BattleContext;
import control.TargetSelector;
import model.combatants.Combatant;
import model.combatants.Player;

public interface TargetingStrategy {
    Combatant[] selectTargets(
            Player user,
            BattleContext context,
            TargetSelector targetSelector
    );
}