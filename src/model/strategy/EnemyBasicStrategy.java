package model.strategy;
import model.actions.*;
import model.combatants.*;

public class EnemyBasicStrategy implements EnemyActionStrategy {
    public Action chooseAction(Enemy user){
        return BasicAttackAction();
    }
}
