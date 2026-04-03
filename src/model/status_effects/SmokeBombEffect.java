package model.status_effects;

import model.combatants.Combatant;

public class SmokeBombEffect extends StatusEffect {
    public SmokeBombEffect(){
        super("Smoke Bomb Effect", 2);
    }

    @Override
    public void apply(Combatant target){
        target.setDefense(target.getDefense() + 1000); // Effectively makes all attacks do 0 damage
    }

    @Override
    public void remove(Combatant target){
        target.setDefense(target.getDefense() - 1000); // Revert the defense buff
    }
}
