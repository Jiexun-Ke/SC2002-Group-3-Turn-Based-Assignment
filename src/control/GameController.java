package control;

import boundary.GameUI;
import java.util.List;
import java.util.Queue;

import model.status_effects.*;
import model.actions.Action;
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
    private GameUI ui;
    
    public GameController(Player player, List<Enemy> enemies, TurnOrderStrategy strategy, GameUI ui) {
        this.player = player;
        this.enemies = enemies;
        this.strategy = strategy;
        this.rounds = 0;
        this.ui = ui;

    }

    private void processTurn(Combatant combatant) { 
        if (combatant instanceof Player) {
            Player player = (Player) combatant;
            ui.showBattleStatus(player, enemies);
            // get player action from UI
            // execute action
            // e.g. attack, use item, defend, etc.

        } else if (combatant instanceof Enemy) {
            // determine enemy action based on AI
            // execute action
            ui.showBattleStatus(player, enemies);
            Enemy enemy = (Enemy) combatant;
            Action enemyAction = enemy.chooseAction();
            enemyAction.execute(enemy, new Combatant[]{player}); // assuming actions target the player for simplicity, can be expanded to support multi-target actions


        }
    }

// Applies start of turn effects
    private void applyStartOfTurnEffects(Combatant combatant) {
        // apply status effects that trigger at the start of the turn
        // also handle any effects that might prevent action (e.g. stun)
        combatant.updateStatusEffects();
    }

// -----------------------------------------------

    private void checkBackupSpawn() {
        boolean AllEnemiesDefeated = enemies.stream().noneMatch(Enemy::isAlive);

        for (Enemy enemy : enemies) {
        if (enemy.isAlive()) {
            AllEnemiesDefeated = false;
            break;
        }
    }

        if (AllEnemiesDefeated && !remainingWaves.isEmpty()) {
            List<Enemy> nextWave = remainingWaves.poll();
            enemies.addAll(nextWave);
            ui.showNewWave(nextWave.size());
            ui.showMessage("Backup spawn has triggered!"); 
        }
     }

    private boolean isBattleOver() { 
        if (!player.isAlive()) {
        return true;
    }

        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                return false;
            }
        }

        return true; 
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
        } 
        
        else {
        ui.showDefeat(enemiesRemaining, rounds);
        }
    }

    public void startBattle() {
        while (!isBattleOver()) {
            rounds++;
            ui.showRoundHeader(rounds);
            ui.displayRoundInfo(rounds, player, enemies.toArray(new Enemy[0]));
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
        ui.showBattleStatus(player, enemies);
    }
}
