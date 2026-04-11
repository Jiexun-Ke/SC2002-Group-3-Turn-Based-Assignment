package control;


import boundary.GameUI;
import java.util.List;
import model.combatants.Enemy;
import model.combatants.Player;
import model.turn_order.SpeedBasedTurnStrategy;
import model.turn_order.TurnOrderStrategy;
import model.items.Item;

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
        return new GameController(player, enemies, strategy, ui);
    }

    private List<Enemy> createEnemies() {
        // TODO Auto-generated method stub
        
        throw new UnsupportedOperationException("Unimplemented method 'createEnemies'");
    }

    private Player choosePlayer() {
        // ask UI
        // create Warrior or Wizard
        return null;
    }
}   private Item[] chooseItems() {
    Item[] items = new Item[2];

    for (int i = 0; i < 2; i++) {
        int choice = ui.promptItemChoice(i + 1);

        switch (choice) {
            case 1:
                items[i] = new Potion();
                break;
            case 2:
                items[i] = new PowerStone();
                break;
            case 3:
                items[i] = new SmokeBomb();
                break;
            default:
                throw new IllegalArgumentException("Invalid item choice.");
        }
    }

    return items;
}


