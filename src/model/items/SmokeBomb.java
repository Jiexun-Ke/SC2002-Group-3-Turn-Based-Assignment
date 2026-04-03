package model.items;
import model.combatants.*;
import model.status_effects.*;

public class SmokeBomb extends Item {
    public SmokeBomb(){
        super("Smoke Bomb");
    }

    @Override
    public String getDescription() {
        return "When used, Enemy attacks do 0 damage in the current turn and the next turn";
    }

    public void use(Player user, Combatant[] targets){
        for(int i=0; i < targets.length; i++){
            targets[i].addStatusEffect(new SmokeBombEffect());
        }
    }
}
