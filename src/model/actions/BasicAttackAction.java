package model.actions;
import model.combat.DamageResult;
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
    public void execute(Combatant user, Combatant[] targets) {
        lastResult = null;

        if (targets == null || targets.length == 0) {
            return;
        }

        Combatant target = targets[0];

        if (target == null || !target.isAlive()) {
            return;
        }

        int baseDamage = Math.max(0, user.getAttack() - target.getDefense());

        DamageResult damageResult = target.modifyIncomingDamage(user, baseDamage);

        target.takeRawDamage(damageResult.getDamage());

        lastResult = new ActionResult(
                damageResult.getDamage(),
                damageResult.isPrevented(),
                damageResult.getReason()
        );
    }
}




