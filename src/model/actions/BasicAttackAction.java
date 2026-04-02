package model.actions;

import model.combatants.Combatant;

public class BasicAttackAction extends Action{
    public BasicAttackAction(){
        super("BasicAttackAction");
    }

    public void execute(Combatant user, Combatant[] targets){
        for(int i=0; i < targets.length; i++){
            targets[i].takeDamage(user.getAttack());
        }
    }
}
