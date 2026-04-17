package model.actions;
import model.combatants.*;

public class ArcaneBlastAction extends Action{
    public ArcaneBlastAction(){
        super("ArcaneBlastAction");
    }

    @Override
    public String getDescription(){
        return "Deal BasicAttack damage to all enemies currently in combat. Each enemy defeated by Arcane Blast adds 10 to the Wizard's Attack, lasting until the end of the level.";
    }

    @Override
    public void execute(Combatant user, Combatant[] targets){
        for (int i=0; i < targets.length; i++){
            targets[i].takeDamage(user.getAttack());
        }
    }
}
