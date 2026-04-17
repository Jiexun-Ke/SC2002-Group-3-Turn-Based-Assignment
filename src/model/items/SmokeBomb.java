package model.items;

import model.combatants.*;
import model.status_effects.*;
import model.targeting.SelfTargetingStrategy;

public class SmokeBomb extends Item {
    public SmokeBomb(){
        super("Smoke Bomb", new SelfTargetingStrategy());
    }

    @Override
    public String getDescription() {
        return "When used, Enemy attacks do 0 damage to the user in the current turn and the next turn";
    }

    @Override
    public void use(Player user, Combatant[] targets){
        user.addStatusEffect(new SmokeBombEffect());
    }
}
