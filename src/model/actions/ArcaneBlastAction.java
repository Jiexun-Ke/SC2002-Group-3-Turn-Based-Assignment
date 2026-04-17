package model.actions;
import model.combat.DamageResult;
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
    public void execute(Combatant user, Combatant[] targets) {
        lastResult = null;

        if (targets == null || targets.length == 0) {
            return;
        }

        int totalDamage = 0;
        boolean prevented = false;
        String reason = null;

        for (Combatant target : targets) {
            if (target == null || !target.isAlive()) {
                continue;
            }

            int baseDamage = Math.max(0, user.getAttack() - target.getDefense());

            DamageResult damageResult = target.modifyIncomingDamage(user, baseDamage);

            int finalDamage = damageResult.getDamage();

            target.takeRawDamage(finalDamage);

            totalDamage += finalDamage;

            if (damageResult.isPrevented()) {
                prevented = true;
                reason = damageResult.getReason();
            }
        }

        lastResult = new ActionResult(totalDamage, prevented, reason);
    }
}
