package control;

import boundary.GameUI;
import java.util.List;
import java.util.Queue;
import model.actions.Action;
import model.actions.ActionResult;
import model.combatants.Combatant;
import model.combatants.Enemy;
import model.combatants.Player;
import model.turn_order.TurnOrderStrategy;


public class GameController {
    private final Player player;
    private final List<Enemy> enemies;
    private final Queue<List<Enemy>> remainingWaves;
    private final TurnOrderStrategy strategy;
    private final GameUI ui;
    private int rounds;
    private final BattleContext context;
    private final PlayerActionSelector playerActionSelector;
    
    public GameController(
        Player player,
        List<Enemy> enemies,
        Queue<List<Enemy>> remainingWaves,
        TurnOrderStrategy strategy,
        GameUI ui
    ) {
        this.player = player;
        this.enemies = enemies;
        this.remainingWaves = remainingWaves;
        this.strategy = strategy;
        this.rounds = 0;
        this.ui = ui;

        TargetSelector targetSelector = new TargetSelector();
        this.context = new BattleContext(player, enemies, ui);
        this.playerActionSelector = new PlayerActionSelector(targetSelector);

    }   

    

    private void processTurn(Combatant combatant) { 
        if (combatant instanceof Player playerCombatant) {
            boolean validActionChosen = false;

            while (!validActionChosen) {
                PlayerActionChoice choice =
                    playerActionSelector.selectAction(playerCombatant, context);

                if (choice == null) {
                    continue;
                }

                choice.getAction().execute(playerCombatant, choice.getTargets());
                validActionChosen = true;
            }

        } else if (combatant instanceof Enemy enemy) {
            processEnemyTurn(enemy);
        } 
    }

    private void processEnemyTurn(Enemy enemy) {
        Action enemyAction = enemy.chooseAction();
        enemyAction.execute(enemy, new Combatant[] { player });

        ActionResult result = enemyAction.getLastResult();

        if (result != null && result.isPrevented()) {
            ui.showMessage(enemy.getName() + " could not damage "
                    + player.getName() + " because of " + result.getReason() + "!");
            return;
        }

        if (result != null && result.getDamageDealt() > 0) {
            ui.showMessage(enemy.getName() + " attacked " + player.getName()
                    + " for " + result.getDamageDealt() + " damage! "
                    + player.getName() + " HP: "
                    + player.getCurrentHP() + "/" + player.getMaxHP());
        } else {
            ui.showMessage(enemy.getName() + " attacked " + player.getName()
                    + ", but dealt 0 damage!");
        }
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

    return enemies.stream().noneMatch(Enemy::isAlive) && remainingWaves.isEmpty();
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
            
        } else {
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

    // runs through a full round of combat. -----------------------------------------------------------
    private void runRound() {
        Combatant[] combatants = buildCombatantsArray();
        Combatant[] turnOrder = strategy.determineTurnOrder(combatants);

        for (Combatant combatant : turnOrder) {
            if (!combatant.isAlive()) {
                continue;
            }

            if (!combatant.canAct()) {
                ui.showMessage(combatant.getName() + " is stunned and cannot act!");
                updatePerTurnState(combatant);
                
                if (isBattleOver()) {
                    break;
                }

                continue; 
            
            }

            applyStartOfTurnEffects(combatant);

            if (!combatant.isAlive()) {
                updatePerTurnState(combatant);
                continue;
            }

            processTurn(combatant);
            updatePerTurnState(combatant);

            if (!player.isAlive()) {
            break;
        }

            if (enemies.stream().noneMatch(Enemy::isAlive)) {
                checkBackupSpawn();
                break;
            }
        }

        if (player.isAlive()) {
            player.updateStatusEffects();
        }
    }
    //---------------------------------------------------------------
    private void applyStartOfTurnEffects(Combatant combatant) {
        
    }

    //------------------------------------------------------------------

    private void updatePerTurnState(Combatant combatant) {
        if (combatant instanceof Player p) {
            p.updateSpecialSkillCooldown();
        } else {
            combatant.updateStatusEffects();
        }
    }
}

