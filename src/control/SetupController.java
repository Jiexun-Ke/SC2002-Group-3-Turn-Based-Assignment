package control;


import boundary.GameUI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import model.combatants.Enemy;
import model.combatants.Goblin;
import model.combatants.Player;
import model.combatants.Warrior;
import model.combatants.Wizard;
import model.combatants.Wolf;
import model.items.Potion;
import model.items.PowerStone;
import model.items.SmokeBomb;
import model.turn_order.SpeedBasedTurnStrategy;
import model.turn_order.TurnOrderStrategy;

public class SetupController {
    private final GameUI ui;

    public SetupController(GameUI ui) {
        this.ui = ui;
    }

    public GameController createGame() {
        Player player = choosePlayer();
        chooseStartingItems(player);

        int difficulty = ui.promptDifficultySelection();

        String difficultyName;
            switch (difficulty) {
                case 1:
                    difficultyName = "Easy";
                    break;
                case 2:
                    difficultyName = "Medium";
                    break;
                case 3:
                    difficultyName = "Hard";
                    break;
                default:
                    difficultyName = "Unknown";
            }

        TurnOrderStrategy strategy = new SpeedBasedTurnStrategy();
        List<Enemy> enemies = createEnemies(difficulty);
        Queue<List<Enemy>> remainingWaves = createBackupWaves(difficulty);

        ui.showMessage("You selected: " + player.getName());
        ui.showMessage("Difficulty selected: " + difficultyName);
        ui.showMessage("Battle setup complete. Prepare for combat!");
        ui.showMessage(" ");
        ui.showBattleStatus(player, enemies);

        return new GameController(player, enemies, remainingWaves, strategy, ui);
    }


    private List<Enemy> createEnemies(int difficulty) {
        List<Enemy> enemies = new ArrayList<>();

        switch (difficulty) {
            case 1:
                enemies.add(new Goblin());
                enemies.add(new Goblin());
                enemies.add(new Goblin());
                break;

            case 2:
                enemies.add(new Goblin());
                enemies.add(new Wolf());
                break;

            case 3:
                enemies.add(new Goblin());
                enemies.add(new Goblin());
                break;

            default:
                throw new IllegalArgumentException("Invalid difficulty");
        }

        return enemies;

    }

    private Player choosePlayer() {
        int choice = ui.promptPlayerSelection();

        switch (choice) {
            case 1:
                return new Warrior();
            case 2:
                return new Wizard();
            default:
                ui.showMessage("Invalid choice. Defaulting to Warrior.");
                return new Warrior();
        }  
    }

    private void chooseStartingItems(Player player) {
        for (int i = 1; i <= 2; i++) {
            int choice = ui.promptStartingItemSelection(i);

            switch (choice) {
                case 1:
                    player.addItem(new Potion());
                    break;
                case 2:
                    player.addItem(new PowerStone());
                    break;
                case 3:
                    player.addItem(new SmokeBomb());
                    break;
                default:
                    ui.showMessage("Invalid choice. Defaulting to Potion.");
                    player.addItem(new Potion());
            }
        }
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

}



