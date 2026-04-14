package control;

import boundary.GameUI;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import model.actions.Action;
import model.actions.BasicAttackAction;
import model.actions.DefendAction;
import model.combatants.Combatant;
import model.combatants.Enemy;
import model.combatants.Player;
import model.combatants.Wizard;
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
    private final Player player;
    private final List<Enemy> enemies;
    private final Queue<List<Enemy>> remainingWaves;
    private final TurnOrderStrategy strategy;
    private int rounds;
    private final GameUI ui;
    
    public GameController(Player player, List<Enemy> enemies, Queue<List<Enemy>> remainingWaves, TurnOrderStrategy strategy, GameUI ui) {
        this.player = player;
        this.enemies = enemies;
        this.remainingWaves = remainingWaves;
        this.strategy = strategy;
        this.rounds = 0;
        this.ui = ui;

    }

    private void processTurn(Combatant combatant) { 
        if (combatant instanceof Player playerCombatant) {
            ui.showBattleStatus(playerCombatant, enemies);
            // get player action from UI
            // execute action
            // e.g. attack, use item, defend, etc.
            boolean validActionChosen = false;
            while (!validActionChosen) {

            int choice = ui.promptPlayerAction(playerCombatant);

                switch (choice) {
                    case 1 -> // Attack
                        validActionChosen = handlePlayerAttack(playerCombatant);

                    case 2 -> // Defend
                        validActionChosen = handlePlayerDefend(playerCombatant);

                    case 3 -> // Use Skill
                        validActionChosen = handlePlayerSkill(playerCombatant);

                    case 4 -> // Use Item
                        validActionChosen = handlePlayerItem(playerCombatant);

                    default -> ui.showMessage("Invalid choice.");
                }
            }

        } else if (combatant instanceof Enemy enemy) {
            // determine enemy action based on AI
            // execute action
            ui.showBattleStatus(player, enemies);
            Action enemyAction = enemy.chooseAction();
            enemyAction.execute(enemy, new Combatant[]{player}); 


        }
    }
    

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
    private boolean handlePlayerAttack(Player player) {
        List<Enemy> aliveEnemies = getAliveEnemies();

        if (aliveEnemies.isEmpty()) {
        ui.showMessage("There are no enemies to attack.");
        return false;
    }

        int targetIndex = ui.promptEnemyTargetSelection(aliveEnemies) - 1;

        if (targetIndex < 0 || targetIndex >= aliveEnemies.size()) {
            ui.showMessage("Invalid target choice.");
            return false;
        }
        
        

        

        Enemy target = aliveEnemies.get(targetIndex);
        new BasicAttackAction().execute(player, new Combatant[]{target});
        ui.showMessage(player.getName() + " attacked " + target.getName() + "!");
        return true;
    }

    // Handles player defense -----------------------------------------------------------------------------

    private boolean handlePlayerDefend(Player player) {
        new DefendAction().execute(player, new Combatant[0]);
        ui.showMessage(player.getName() + " takes a defensive stance!");
        return true;
    }

    // Handles player skill ---- -----------------------------------------------------------------------------

    private boolean handlePlayerSkill(Player player) {
        List<Enemy> aliveEnemies = getAliveEnemies();
        
        

        if (aliveEnemies.isEmpty()) {
            ui.showMessage("No enemies to target with a skill.");
            return false;
        }

        boolean skillUsed;

        if (player instanceof Wizard) {
            @SuppressWarnings("CollectionsToArray")
            Combatant[] targets = aliveEnemies.toArray(new Combatant[aliveEnemies.size()]);
            skillUsed = player.useSpecialSkill(targets);

            if (!skillUsed) {
                ui.showMessage("Skill is on cooldown for " + player.getSpecialSkillCooldown() + " more turn(s).");
                return false;
            }

            ui.showMessage(player.getName() + " used Arcane Blast on all enemies!");
            return true;
        }

        int targetIndex = ui.promptEnemyTargetSelection(aliveEnemies) - 1;

        if (targetIndex < 0 || targetIndex >= aliveEnemies.size()) {
            ui.showMessage("Invalid target choice.");
            return false;
        }
       

        

        Enemy target = aliveEnemies.get(targetIndex);

        skillUsed = player.useSpecialSkill(new Combatant[]{target});

        if (!skillUsed) {
            ui.showMessage("Skill is on cooldown for " + player.getSpecialSkillCooldown() + " more turn(s).");
            return false;
        }

        ui.showMessage(player.getName() + " used a skill on " + target.getName() + "!");
        return true;
    }


        

    

    // Handles player item  -------------------------------------------------------------------------------
    private boolean handlePlayerItem(Player player) {
        Item[] inventory = player.getInventory(); 
        int itemChoice = ui.promptItemSelection(inventory);

        if (itemChoice == -1) {
            ui.showMessage("Item use cancelled.");
            return false;
        }

        int itemIndex = itemChoice - 1; // Converts itemIndex to 0-based index

        if (itemIndex < 0 || itemIndex >= inventory.length || inventory[itemIndex] == null) {
            ui.showMessage("Invalid item choice.");
            return false;
        }

        Item chosenItem = inventory[itemIndex];
        
        
        
        Combatant[] targets;

        
        if (chosenItem instanceof PowerStone || chosenItem instanceof SmokeBomb) {
            List<Enemy> aliveEnemies = getAliveEnemies();

            if (aliveEnemies.isEmpty()) {
                ui.showMessage("There are no enemies to attack.");
                return false;
            }
            
            int targetIndex = ui.promptEnemyTargetSelection(aliveEnemies) - 1;

            if (targetIndex < 0 || targetIndex >= aliveEnemies.size()) {
                ui.showMessage("Invalid target choice.");
                return false;
            }

            Enemy target = aliveEnemies.get(targetIndex);
            targets = new Combatant[]{target};
    } else {
        targets = new Combatant[]{player};
    }

    chosenItem.use(player, targets);
    player.removeItem(chosenItem);
    ui.showMessage(player.getName() + " used " + chosenItem.getName() + "!");
    return true;
}

// ------------------------------------------------------------------------------------------------------------

    // Applies start of turn effects
    private void applyStartOfTurnEffects(Combatant combatant) {
        
        combatant.updateStatusEffects();
    }

// ------------------------------------------------------------------------------------------------------------


    // Checks if backup spawn should be triggered and spawns next wave if conditions are met
    private void checkBackupSpawn() {
        boolean allEnemiesDefeated = enemies.stream().noneMatch(Enemy::isAlive);

        if (allEnemiesDefeated && !remainingWaves.isEmpty()) {
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
            ui.displayRoundInfo(rounds, player, enemies.toArray(Enemy[]::new));
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
        if (combatant instanceof Player p) {
            p.updateSpecialSkillCooldown();
        }
    }
}

