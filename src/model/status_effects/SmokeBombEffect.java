package model.status_effects;

import model.combat.DamageResult;
import model.combatants.Combatant;

public class SmokeBombEffect extends StatusEffect {
    private int remainingBlockedAttacks;


    public SmokeBombEffect(){
        super("Smoke Bomb Effect", 999);
        this.remainingBlockedAttacks = 2;
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
        if (remainingBlockedAttacks > 0) {
            remainingBlockedAttacks--;
            return new DamageResult(0, true, "Smoke Bomb");
        }

        return new DamageResult(damage, false, null);
    }

    @Override
    public boolean isExpired() {
        return remainingBlockedAttacks <= 0;
    }
}
