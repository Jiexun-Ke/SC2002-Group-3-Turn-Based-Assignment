package model.status_effects;
import model.combatants.*;

public class StunEffect extends StatusEffect {
    public StunEffect(){
        super("Stun Effect", 2);
    }

    @Override
    public void apply(Combatant target){
        
    }

    @Override
    public void remove(Combatant target){
        
    }

    @Override
    public boolean preventsAction() {
        return true;
    }
}
