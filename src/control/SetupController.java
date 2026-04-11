package control;


import boundary.GameUI;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import model.combatants.Enemy;
import model.combatants.Goblin;
import model.combatants.Player;
import model.combatants.Wolf;
import model.turn_order.SpeedBasedTurnStrategy;
import model.turn_order.TurnOrderStrategy;

public class SetupController {
    private GameUI ui;

    public SetupController(GameUI ui) {
        this.ui = ui;
    }

    public GameController createGame() {
        Player player = choosePlayer();
        // choose items
        // choose difficulty
        // create enemies and backup waves

        TurnOrderStrategy strategy = new SpeedBasedTurnStrategy();

        List<Enemy> enemies = createEnemies();
        
        Queue<List<Enemy>> remainingWaves = createBackupWaves();
        return new GameController(player, enemies, remainingWaves, strategy, ui);
    }

    private List<Enemy> createEnemies() {
        
        throw new UnsupportedOperationException("Unimplemented method 'createEnemies'");

    }

    private Player choosePlayer() {
        // ask UI
        // create Warrior or Wizard

        return null;
    }

    private Queue<List<Enemy>> createBackupWaves(int difficulty) {
        Queue<List<Enemy>> remainingWaves = new LinkedList<>();

        switch (difficulty) {
            case 1: // Easy
                break;

            case 2: // Medium
                List<Enemy> mediumBackup = new ArrayList<>();
                mediumBackup.add(new Wolf());
                mediumBackup.add(new Wolf());
                remainingWaves.add(mediumBackup);
                break;

            case 3: // Hard
                List<Enemy> hardBackup = new ArrayList<>();
                hardBackup.add(new Goblin());
                hardBackup.add(new Wolf());
                hardBackup.add(new Wolf());
                remainingWaves.add(hardBackup);
                break;
            
            default:
                throw new IllegalArgumentException("Invalid difficulty");
            }

    return remainingWaves;
    }   



