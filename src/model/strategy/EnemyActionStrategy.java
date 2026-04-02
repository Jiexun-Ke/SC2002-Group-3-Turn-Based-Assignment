package model.strategy;
import model.actions.*;
import model.combatants.Enemy;

public interface EnemyActionStrategy {
    public Action chooseAction(Enemy user);
}
