package model.status_effects;
import model.combatants.*;

public class StunEffect extends StatusEffect {
    public StunEffect(){
        super("Stun Effect", 1);
    }

    public void apply(Combatant target){}
    public void remove(Combatant target){}
}
