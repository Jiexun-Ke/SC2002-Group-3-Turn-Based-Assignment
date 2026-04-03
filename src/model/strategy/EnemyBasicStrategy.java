package model.strategy;
import model.actions.*;
import model.combatants.*;

public class EnemyBasicStrategy implements EnemyActionStrategy {
    @Override
    public Action chooseAction(Enemy user){
        return new BasicAttackAction();
    }
}
