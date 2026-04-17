package model.actions;
import model.combatants.*;
import model.status_effects.StunEffect;

public class ShieldBashAction extends Action{
    public ShieldBashAction(){
        super("ShieldBashAction");
    }

    @Override
    public String getDescription(){
        return "Deal BasicAttack damage to selected enemy. Selected enemy is unable to take actions for the current turn and the next turn.";
    }

    @Override
    public void execute(Combatant user, Combatant[] targets){
        for(int i=0; i < targets.length; i++){
            targets[i].takeDamage(user.getAttack());
            targets[i].addStatusEffect(new StunEffect());
        }
    }
}
