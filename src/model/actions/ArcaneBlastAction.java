package model.actions;
import model.combatants.*;

public class ArcaneBlastAction extends Action{
    public ArcaneBlastAction(){
        super("ArcaneBlastAction");
    }

    public void execute(Combatant user, Combatant[] targets){
        for(int i=0; i < targets.length; i++){
            targets[i].takeDamage(user.getAttack());
        }
    }

    public String getDescription(){
        return " ";
    }
}
