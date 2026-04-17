package control;

import boundary.GameUI;
import java.util.ArrayList;
import java.util.List;
import model.combatants.Enemy;
import model.combatants.Player;

public class BattleContext {
    private final Player player;
    private final List<Enemy> enemies;
    private final GameUI ui;

    public BattleContext(Player player, List<Enemy> enemies, GameUI ui) {
        this.player = player;
        this.enemies = enemies;
        this.ui = ui;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public GameUI getUi() {
        return ui;
    }

    public List<Enemy> getAliveEnemies() {
        List<Enemy> aliveEnemies = new ArrayList<>();

        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                aliveEnemies.add(enemy);
            }
        }

        return aliveEnemies;
    }
}