package control;

import boundary.GameUI;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import model.combatants.Enemy;
import model.combatants.Player;
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
}


