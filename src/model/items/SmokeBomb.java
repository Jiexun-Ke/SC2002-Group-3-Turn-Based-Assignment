package model.items;

import model.combatants.*;
import model.status_effects.*;
import model.targeting.AllEnemiesTargetingStrategy;

public class SmokeBomb extends Item {
    public SmokeBomb(){
        super("Smoke Bomb", new AllEnemiesTargetingStrategy());
    }

    @Override
    public String getDescription() {
        return "When used, Enemy attacks do 0 damage to the user in the current turn and the next turn";
    }

    @Override
    public void use(Player user, Combatant[] targets){
        for(Combatant target : targets){
            if (target instanceof Enemy enemy) {
            enemy.addStatusEffect(new SmokeBombEffect());
            }
        }
    }
}
