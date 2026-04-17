package control;

import java.util.List;
import model.combatants.Combatant;
import model.combatants.Enemy;
import model.combatants.Player;

public class TargetSelector {

    public Combatant[] selectSingleEnemy(BattleContext context) {
        List<Enemy> aliveEnemies = context.getAliveEnemies();

        if (aliveEnemies.isEmpty()) {
            context.getUi().showMessage("There are no enemies to target.");
            return null;
        }

        int targetChoice = context.getUi().promptEnemyTargetSelection(aliveEnemies) - 1;

        if (targetChoice == 0) {
            context.getUi().showMessage("Action cancelled.");
            return null;
        }

        int targetIndex = targetChoice - 1;

        if (targetIndex < 0 || targetIndex >= aliveEnemies.size()) {
            context.getUi().showMessage("Invalid target choice.");
            return null;
        }

        return new Combatant[] { aliveEnemies.get(targetIndex) };
    }

    public Combatant[] selectAllEnemies(BattleContext context) {
        List<Enemy> aliveEnemies = context.getAliveEnemies();

        if (aliveEnemies.isEmpty()) {
            context.getUi().showMessage("There are no enemies to target.");
            return null;
        }

        return aliveEnemies.toArray(new Combatant[0]);
    }

    public Combatant[] selectPlayer(BattleContext context) {
        return new Combatant[] { context.getPlayer() };
    }

    public Combatant[] selectNoTargets() {
        return new Combatant[0];
    }

    public Combatant[] selectSpecialSkillTargets(Player player, BattleContext context) {
        if (player.specialSkillTargetsAllEnemies()) {
            return selectAllEnemies(context);
        }

        return selectSingleEnemy(context);
    }
}