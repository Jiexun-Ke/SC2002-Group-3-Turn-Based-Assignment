package model.status_effects;

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
    public int modifyIncomingDamage(Combatant attacker, Combatant target, int damage) {
            return 0;
    }

    @Override
    public boolean isExpired() {
        return remainingBlockedAttacks <= 0;
    }
}
