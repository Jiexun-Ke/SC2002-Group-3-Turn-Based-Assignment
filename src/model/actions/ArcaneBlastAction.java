package model.actions;
import java.util.ArrayList;
import java.util.List;
import model.combat.DamageResult;
import model.combatants.*;

public class ArcaneBlastAction extends Action{
    public ArcaneBlastAction(){
        super("Arcane Blast");
    }

    @Override
    public String getDescription(){
        return "Deal Basic Attack damage to all enemies currently in combat. Each enemy defeated by Arcane Blast adds 10 to the Wizard's Attack, lasting until the end of the level.";
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

        if (!(user instanceof Wizard wizard)) {
            lastResult = new ActionResult(
                getName(),
                0,
                0,
                false,
                null,
                new ArrayList<>(),
                true,
                "Only Wizard can use Arcane Blast"
            );
            return;
        }

        if (targets == null || targets.length == 0) {
            lastResult = new ActionResult(
                getName(),
                0,
                0,
                false,
                null,
                new ArrayList<>(),
                true,
                "No valid targets selected"
            );
            return;
        }

        int totalDamage = 0;
        int killCount = 0;
        List<String> targetSummaries = new ArrayList<>();
        List<String> issues = new ArrayList<>();

        for (Combatant target : targets) {
            if (target == null) {
                issues.add("Skipped a null target");
                continue;
            }

            if (!target.isAlive()) {
                targetSummaries.add(target.getName() + " is already defeated.");
                continue;
            }

            int hpBefore = target.getCurrentHP();
            int rawDamage = Math.max(0, wizard.getAttack() - target.getDefense());

            DamageResult damageResult = target.modifyIncomingDamage(wizard, rawDamage);
            int finalDamage = Math.max(0, damageResult.getDamage());

            target.takeRawDamage(finalDamage);
            totalDamage += finalDamage;

            StringBuilder summary = new StringBuilder();
            summary.append(target.getName())
                   .append(" took ")
                   .append(finalDamage)
                   .append(" damage. HP: ")
                   .append(target.getCurrentHP())
                   .append("/")
                   .append(target.getMaxHP());

            if (damageResult.isPrevented() && damageResult.getReason() != null && !damageResult.getReason().isEmpty()) {
                summary.append(" (").append(damageResult.getReason()).append(")");
            }

            if (hpBefore > 0 && !target.isAlive()) {
                killCount++;
                wizard.increaseAttack(10);
                summary.append(" | Eliminated! Wizard ATK +10");
            }

            targetSummaries.add(summary.toString());
        }

        boolean boosted = killCount > 0;
        String effectName = boosted ? "ATK +" + (killCount * 10) : null;

        String errorMessage = String.join("; ", issues);
        boolean prevented = !issues.isEmpty();

        lastResult = new ActionResult(
            getName(),
            totalDamage,
            0,
            boosted,
            effectName,
            targetSummaries,
            prevented,
            errorMessage
        );
    }
}
