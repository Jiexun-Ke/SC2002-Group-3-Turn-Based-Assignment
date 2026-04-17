package model.actions;
import model.combatants.Combatant;

public class BasicAttackAction extends Action{
    public BasicAttackAction(){
        super("BasicAttackAction");
    }

    @Override
    public String getDescription(){
        return "Performs a basic attack on the selected target.";
    }
    
    @Override
    public void execute(Combatant user, Combatant[] targets){
        for (Combatant target : targets) {
        if (target == null || !target.isAlive()) {
            continue;
        }

        int damage = Math.max(0, user.getAttack() - target.getDefense());
        damage = target.modifyIncomingDamage(user, damage);
        target.takeDamage(damage);
    }
    }
}




