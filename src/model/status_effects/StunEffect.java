package model.status_effects;
import model.combatants.*;

public class StunEffect extends StatusEffect {
    public StunEffect(){
        super("Stun Effect", 2);
    }

    @Override
    public void apply(Combatant target){
        // No direct stat change is needed
    }

    @Override
    public void remove(Combatant target){
        // No removal action needed for stun
    }

    @Override
    public boolean preventsAction() {
        return true;
    }
}
