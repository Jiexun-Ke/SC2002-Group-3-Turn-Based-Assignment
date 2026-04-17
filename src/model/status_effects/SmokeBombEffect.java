package model.status_effects;

import model.combat.DamageResult;
import model.combatants.Combatant;

public class SmokeBombEffect extends StatusEffect {
    


    public SmokeBombEffect() {
        super("Smoke Bomb Effect",  2);
        
    }

    @Override
    public void apply(Combatant target){
       // target.setDefense(target.getDefense() + 1000); // Effectively makes all attacks do 0 damage
    }

    @Override
    public void remove(Combatant target){
        // target.setDefense(target.getDefense() - 1000); // Revert the defense buff
    }

    @Override
    public DamageResult modifyIncomingDamage(Combatant attacker, Combatant target, int damage) {
        return new DamageResult(0, true, "Smoke Bomb");
    }

    
}
