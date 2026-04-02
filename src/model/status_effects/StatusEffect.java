package model.status_effects;
import model.combatants.*;

public abstract class StatusEffect {
    private String name;
    private int remainingTurns;

    public StatusEffect(String name, int remainingTurns){
        this.name = name;
        this.remainingTurns = remainingTurns;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void apply(Combatant target){
        target.addStatusEffect(this);
    }

    //public void remove(Combatant target){

    //}

    public void reduceDuration(){
        this.remainingTurns -= 1;
    }

    public boolean isExpired(){
        return this.remainingTurns == 0;
    }
}
