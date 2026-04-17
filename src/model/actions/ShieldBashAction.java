package model.actions;
import model.combat.DamageResult;
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

        int finalDamage = damageResult.getDamage();

        target.takeRawDamage(finalDamage);

        if (target.isAlive()) {
            target.addStatusEffect(new StunEffect());
        }

        lastResult = new ActionResult(
                finalDamage,
                damageResult.isPrevented(),
                damageResult.getReason()
        );
    }
}
