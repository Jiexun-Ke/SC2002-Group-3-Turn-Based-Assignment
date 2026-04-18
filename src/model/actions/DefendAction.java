package model.actions;
import model.combatants.*;
import model.status_effects.DefenseBuffEffect;

public class DefendAction extends Action{
    public DefendAction(){
        super("DefendAction");
    }

    @Override
    public String getDescription(){
        return "Increases defense by 10 for the current round and the next round";
    }

    

    @Override
    public void execute(Combatant user, Combatant[] targets){
        user.addStatusEffect(new DefenseBuffEffect());
    }
}
