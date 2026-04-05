package control;

import boundary.GameUI;
import java.util.List;
import java.util.Queue;
import model.combatants.Combatant;
import model.combatants.Enemy;
import model.combatants.Player;
import model.turn_order.TurnOrderStrategy;

public class GameController {
    // overall game flow
    // should track rounds, turns and win/lose checking for both player and enemies
    // apply start-of-turn and end-of-turn effects 
    // invoke selected action
    // remove dead combatants
    // trigger backup spawn
    // check battle end conditions
    private Player player;
    private List<Enemy> enemies;
    private Queue<List<Enemy>> remainingWaves;
    private TurnOrderStrategy strategy;
    private int rounds;
    private int roundNumber;
    private boolean battleEnded;

    
    
    private void processTurn(Combatant combatant) { }
    private void applyStartOfTurnEffects(Combatant combatant) { }
    private void checkBackupSpawn() { }
    private boolean isBattleOver() { return false; }
    private void showBattleResult() { }

    private GameUI ui;

    public GameController(Player player, List<Enemy> enemies, TurnOrderStrategy strategy, GameUI ui) {
        this.player = player;
        this.enemies = enemies;
        this.strategy = strategy;
        this.rounds = 0;
        this.ui = ui;

    }


    private void displayResult() {
    int enemiesRemaining = 0;
    for (Enemy enemy : enemies) {
        if (enemy.isAlive()) {
            enemiesRemaining++;
        }
    }

    if (player.isAlive()) {
        ui.showVictory(player.getCurrentHP(), player.getMaxHP(), rounds);
    } else {
        ui.showDefeat(enemiesRemaining, rounds);
    }
}
    public void startBattle() {
        while (!isBattleOver()) {
            rounds++;
            runRound();
        }
        displayResult();
    }

    private Combatant[] buildCombatantsArray() {
    int aliveEnemies = 0;
    for (Enemy enemy : enemies) {
        if (enemy.isAlive()) {
            aliveEnemies++;
        }
    }

    Combatant[] combatants = new Combatant[1 + aliveEnemies];
    combatants[0] = player;

    int index = 1;
    for (Enemy enemy : enemies) {
        if (enemy.isAlive()) {
            combatants[index] = enemy;
            index++;
            }
        }

    return combatants;
    }

    private void runRound() {
    Combatant[] combatants = buildCombatantsArray();
    Combatant[] turnOrder = strategy.determineTurnOrder(combatants);

    for (Combatant combatant : turnOrder) {
        if (!combatant.isAlive()) {
            continue;
        }

        applyStartOfTurnEffects(combatant);

        if (!combatant.isAlive() || !combatant.canAct()) {
            continue;
        }

        processTurn(combatant);

        if (isBattleOver()) {
            break;
        }
    }

    checkBackupSpawn();
}
}
