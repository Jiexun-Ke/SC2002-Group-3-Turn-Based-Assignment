package model.strategy;
import model.actions.Action;
import model.combatants.Enemy;

public interface EnemyActionStrategy {
    public Action chooseAction(Enemy user);
}
