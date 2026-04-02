package model.combatants;
import model.strategy.*;

public class Goblin extends Enemy {
    public Goblin(){
        super("Goblin", 55, 35, 15, 25, new EnemyBasicStrategy());
    }
}
