package model.actions;

import java.util.ArrayList;
import java.util.List;
import model.combat.DamageResult;
import model.combatants.Combatant;
import model.status_effects.StunEffect;

public class ShieldBashAction extends Action{
    public ShieldBashAction(){
        super("Shield Bash");
    }

    @Override
    public String getDescription(){
        return "Deal BasicAttack damage to selected enemy. Selected enemy is unable to take actions for the current turn and the next turn.";
    }


    
    @Override
    public void execute(Combatant user, Combatant[] targets) {
        if (user == null) {
            lastResult = new ActionResult(
                getName(),
                0,
                0,
                false,
                null,
                new ArrayList<>(),
                true,
                "No valid attacker"
            );
            return;
        }

        if (targets == null || targets.length == 0 || targets[0] == null) {
            lastResult = new ActionResult(
                getName(),
                0,
                0,
                false,
                null,
                new ArrayList<>(),
                true,
                "No valid target selected"
            );
            return;
        }

        Combatant target = targets[0];

        int rawDamage = Math.max(0, user.getAttack() - target.getDefense());
        DamageResult damageResult = target.modifyIncomingDamage(user, rawDamage);
        int finalDamage = Math.max(0, damageResult.getDamage());

        target.takeRawDamage(finalDamage);

        boolean stunned = false;
        String reason = damageResult.getReason();

        if (target.isAlive()) {
            if (target.hasStatusEffect(StunEffect.class)) {
                if (reason == null || reason.isEmpty()) {
                    reason = "Target is already stunned";
                }
            } else {
                stunned = target.addStatusEffect(new StunEffect());
                if (!stunned && (reason == null || reason.isEmpty())) {
                    reason = "Cannot apply more status effects";
                }
            }
        }

        List<String> targetSummaries = new ArrayList<>();
        targetSummaries.add(
            target.getName() + " took " + finalDamage + " damage. HP: "
                + target.getCurrentHP() + "/" + target.getMaxHP()
        );

        lastResult = new ActionResult(
            getName(),
            finalDamage,
            0,
            stunned,
            stunned ? "Stun" : null,
            targetSummaries,
            damageResult.isPrevented() || (!stunned && reason != null && !reason.isEmpty()),
            reason == null ? "" : reason
        );
    }



    
}
