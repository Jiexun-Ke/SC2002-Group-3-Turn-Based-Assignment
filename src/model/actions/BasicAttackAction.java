package model.actions;
import model.combat.DamageResult;
import model.combatants.Combatant;

public class BasicAttackAction extends Action{
    public BasicAttackAction(){
        super("Basic Attack");
    }

    @Override
    public String getDescription(){
        return "Performs a basic attack on the selected target.";
    }
    
    



    @Override
    public void execute(Combatant user, Combatant[] targets) {
        Combatant target = targets[0];
        int rawDamage = Math.max(0, user.getAttack() - target.getDefense());
        

        DamageResult damageResult = target.modifyIncomingDamage(user, rawDamage);
        int finalDamage = damageResult.getDamage();

        target.takeRawDamage(damageResult.getDamage());

        java.util.List<String> targetSummaries = new java.util.ArrayList<>();
        targetSummaries.add(target.getName() + " took " + finalDamage + " damage. HP: " + target.getCurrentHP() + "/" + target.getMaxHP());

        lastResult = new ActionResult(
            getName(),
            finalDamage,
            0,
            false,
            null,
            targetSummaries,
            damageResult.isPrevented(),
            damageResult.getReason()
        );
    }
}




