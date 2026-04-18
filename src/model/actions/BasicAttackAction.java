package model.actions;

import java.util.ArrayList;
import java.util.List;
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

        List<String> targetSummaries = new ArrayList<>();
        targetSummaries.add(
            target.getName() + " took " + finalDamage + " damage. HP: "
            + target.getCurrentHP() + "/" + target.getMaxHP()
        );

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




