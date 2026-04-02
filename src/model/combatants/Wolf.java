package model.combatants;

import model.strategy.EnemyBasicStrategy;

public class Wolf extends Enemy{
    public Wolf(){
        super("Wolf", 40, 45, 5, 35, new EnemyBasicStrategy());
    }
}
