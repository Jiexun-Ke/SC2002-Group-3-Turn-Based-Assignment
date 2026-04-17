package model.status_effects;
import model.combatants.*;

public class DefenseBuffEffect extends StatusEffect {
    private final int buffAmount = 10;

    public DefenseBuffEffect(){
        super("Defense Buff Effect", 2);
    }

    @Override
    public void apply(Combatant target){
        target.setDefense(target.getDefense() + buffAmount);
    }

    @Override
    public void remove(Combatant target){
        target.setDefense(target.getDefense() - buffAmount);
    }
}
