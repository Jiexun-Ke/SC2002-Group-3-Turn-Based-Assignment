package control;

import boundary.GameUI;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import model.actions.Action;
import model.actions.DefendAction;
import model.combatants.Combatant;
import model.combatants.Enemy;
import model.combatants.Player;
import model.items.*;
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
    
    public GameController(Player player, List<Enemy> enemies, Queue<List<Enemy>> remainingWaves, TurnOrderStrategy strategy, GameUI ui) {
        this.player = player;
        this.enemies = enemies;
        this.remainingWaves = remainingWaves;
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
            boolean validActionChosen = false;
            while (!validActionChosen) {

            int choice = ui.promptPlayerAction(player);

                switch (choice) {
                    case 1: // Attack
                        handlePlayerAttack(player);
                        break;

                    case 2: // Defend
                        handlePlayerDefend(player);
                        break;

                    case 3: // Use Skill
                        handlePlayerSkill(player);
                        break;

                    case 4: // Use Item
                        handlePlayerItem(player);
                        break;

                    default:
                        ui.showMessage("Invalid choice.");
                }
            }

        } else if (combatant instanceof Enemy) {
            // determine enemy action based on AI
            // execute action
            ui.showBattleStatus(player, enemies);
            Enemy enemy = (Enemy) combatant;
            Action enemyAction = enemy.chooseAction();
            enemyAction.execute(enemy, new Combatant[]{player}); // assuming actions target the player for simplicity, can be expanded to support multi-target actions


        }
    }
    // All case methods used by processTurn() to handle player actions.

    // Handles player attack --------------------------------------------------------------------------------
    private List<Enemy> getAliveEnemies() {
    List<Enemy> aliveEnemies = new ArrayList<>();
    for (Enemy enemy : enemies) {
        if (enemy.isAlive()) {
            aliveEnemies.add(enemy);
        }
    }
    return aliveEnemies;
}
    private void handlePlayerAttack(Player player) {
        List<Enemy> aliveEnemies = getAliveEnemies();
        int targetIndex = ui.promptEnemyTargetSelection(aliveEnemies) - 1;

        if (targetIndex < 0 || targetIndex >= aliveEnemies.size()) {
        ui.showMessage("Invalid target choice.");
        return;
        }
        
        Enemy target = aliveEnemies.get(targetIndex);

        if (!target.isAlive()) {
            ui.showMessage("That enemy is already defeated.");
            return;
        }

        target.takeDamage(player.getAttack());

        ui.showMessage(player.getName() + " attacked " + target.getName() + "!");
    }

    // Handles player defense -----------------------------------------------------------------------------

    private void handlePlayerDefend(Player player) {
        new DefendAction().execute(player, new Combatant[0]);
        ui.showMessage(player.getName() + " takes a defensive stance!");
}

    // Handles player skill ---- -----------------------------------------------------------------------------

    private void handlePlayerSkill(Player player) {
        int targetIndex = ui.promptEnemyTargetSelection(enemies) - 1;
        Enemy target = enemies.get(targetIndex);

        if (!target.isAlive()) {
            ui.showMessage("That enemy is already defeated.");
            return;
        }

        player.useSpecialSkill(new Combatant[]{target});
        ui.showMessage(player.getName() + " used a skill on " + target.getName() + "!");
    }

    // Handles player item  -------------------------------------------------------------------------------
    private void handlePlayerItem(Player player) {
        Item[] inventory = player.getInventory(); // assuming this exists
        int itemChoice = ui.promptItemSelection(inventory);

        if (itemChoice == -1) {
            ui.showMessage("Item use cancelled.");
            return;
        }

        int itemIndex = itemChoice - 1; // Converts itemIndex to 0-based index

        if (itemIndex < 0 || itemIndex >= inventory.length || inventory[itemIndex] == null) {
            ui.showMessage("Invalid item choice.");
            return;
        }

        Item chosenItem = inventory[itemIndex];
        Combatant[] targets;

        // this is because offensive items like PowerStone and SmokeBomb should target enemies, while defensive items like HealthPotion should target the player
        if (chosenItem instanceof PowerStone || chosenItem instanceof SmokeBomb) {
            int targetIndex = ui.promptEnemyTargetSelection(enemies) - 1;
            Enemy target = enemies.get(targetIndex);

            if (!target.isAlive()) {
                ui.showMessage("That enemy is already defeated.");
                return;
            }

            targets = new Combatant[]{target};
        } else {
            targets = new Combatant[]{player};
        }

        chosenItem.use(player, targets);
        player.removeItem(chosenItem);
        ui.showMessage(player.getName() + " used " + chosenItem.getName() + "!");
    }

// ------------------------------------------------------------------------------------------------------------

    // Applies start of turn effects
    private void applyStartOfTurnEffects(Combatant combatant) {
        // apply status effects that trigger at the start of the turn
        // also handle any effects that might prevent action (e.g. stun)
        combatant.updateStatusEffects();
    }

// ------------------------------------------------------------------------------------------------------------


    // Checks if backup spawn should be triggered and spawns next wave if conditions are met
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

    // returns true or false if battle is over based on if player is dead/enemies are all dead
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

    // just displays victory or defeat screen based on ui.showDefeat() or ui.showVictory() in GameUI.
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

    // main game loop that runs until battle is over, tracks rounds and displays round info at the start of each round.
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

    // runs through a full round of combat. 
    private void runRound() {
        Combatant[] combatants = buildCombatantsArray();
        Combatant[] turnOrder = strategy.determineTurnOrder(combatants);

        for (Combatant combatant : turnOrder) {
            if (!combatant.isAlive()) {
                continue;
            }

            applyStartOfTurnEffects(combatant);

            if (!combatant.isAlive() || !combatant.canAct()) {
                updatePerTurnState(combatant);
                continue;
            }

            processTurn(combatant);
            updatePerTurnState(combatant);

            if (isBattleOver()) {
                break;
            }
        }

        checkBackupSpawn();
        ui.showBattleStatus(player, enemies);
    }

    private void updatePerTurnState(Combatant combatant) {
        if (combatant instanceof Player) {
            Player p = (Player) combatant;
            p.updateSpecialSkillCooldown();
        }
    }
}

