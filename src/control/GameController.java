package control;

import boundary.GameUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import model.status_effects.*;
import model.actions.*;
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
    private TurnOrderStrategy turnOrderStrat;
    private int rounds;
    private GameUI UI;

    public GameController(Player player, List<Enemy> enemies, TurnOrderStrategy turnOrderStrat, GameUI UI) {
        this.player = player;
        this.enemies = enemies;
        this.turnOrderStrat = turnOrderStrat;
        this.rounds = 0;
        this.UI = UI;
    }

    public void startBattle() {
        while (!isBattleOver()) {
            rounds++;
            UI.showRoundHeader(rounds);
            UI.displayRoundInfo(player, enemies);
            runRound();
        }
        displayResult();
    }

    private void runRound() {
        List<Combatant> combatants = buildCombatantsArray();
        List<Combatant> turnOrder = turnOrderStrat.determineTurnOrder(combatants);

        for (Combatant combatant : turnOrder) {
            //Update status effect
            combatant.updateStatusEffects();

            if (combatant instanceof Player)
            {
                UI.showPlayerActions(player);
                Action action = chooseAction();
            }

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
        UI.showBattleStatus(player, enemies);
    }

    private Action chooseAction()
    {
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.println("Basic Attack Executed");
                return new BasicAttackAction();
            case 2:
                System.out.println("Defend Executed");
                return new DefendAction();
            case 3:
                System.out.println("Item Executed");
                return new UseItemAction(null);
        }

        return null;
    }

    private List<Combatant> buildCombatantsArray() {
        List<Combatant> tempList = new ArrayList<>();
        tempList.add(player);

        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                tempList.add(enemy);
            }
        }

        return tempList;
    }

    private void processTurn(Combatant combatant) {
        if (combatant instanceof Player) {
            Player player = (Player) combatant;
            UI.showBattleStatus(player, enemies);
            // get player action from UI
            // execute action
            // e.g. attack, use item, defend, etc.

        } else if (combatant instanceof Enemy) {
            // determine enemy action based on AI
            // execute action
            UI.showBattleStatus(player, enemies);
            Enemy enemy = (Enemy) combatant;
            Action enemyAction = enemy.chooseAction();
            enemyAction.execute(enemy, new Combatant[]{player}); // assuming actions target the player for simplicity, can be expanded to support multi-target actions
        }
    }

    private void applyStartOfTurnEffects(Combatant combatant) {
        // apply status effects that trigger at the start of the turn
        // also handle any effects that might prevent action (e.g. stun)
        combatant.updateStatusEffects();
    }

    private void checkBackupSpawn() {
        boolean AllEnemiesDefeated = enemies.stream().noneMatch(Enemy::isAlive);

        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                AllEnemiesDefeated = false;
                break;
            }
        }

        if (enemies.stream().noneMatch(Enemy::isAlive) && !remainingWaves.isEmpty()) {
            List<Enemy> nextWave = remainingWaves.poll();
            enemies.addAll(nextWave);
            UI.showNewWave(nextWave.size());
            UI.showMessage("Backup spawn has triggered!");
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
            UI.showVictory(player.getCurrentHP(), player.getMaxHP(), rounds);
        } else {
            UI.showDefeat(enemiesRemaining, rounds);
        }
    }
}