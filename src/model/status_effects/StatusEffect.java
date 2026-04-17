package model.status_effects;
import model.combatants.*;

public abstract class StatusEffect {
    private final String name;
    private int remainingTurns;

    public StatusEffect(String name, int remainingTurns){
        this.name = name;
        this.remainingTurns = remainingTurns;
    }

    public int modifyIncomingDamage(Combatant attacker, Combatant target, int damage) {
        return damage;
    }
    
    public String getName(){
        return this.name;
    }

    public void reduceDuration(){
        this.remainingTurns -= 1;
    }

    public boolean isExpired(){
        return this.remainingTurns <= 0;
    }

    public abstract void apply(Combatant target);

    public abstract void remove(Combatant target);
}
