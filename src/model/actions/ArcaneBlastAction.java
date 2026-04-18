package model.actions;
import java.util.ArrayList;
import java.util.List;
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
        int totalDamage = 0;
        List<String> targetSummaries = new ArrayList<>();

        for (Combatant target : targets) {
            if (target == null || !target.isAlive()) {
                continue;
            }

            int damage = Math.max(0, user.getAttack() - target.getDefense());
            target.takeDamage(damage);
            totalDamage += damage;

            targetSummaries.add(
                target.getName() + " HP: " + target.getCurrentHP() + "/" + target.getMaxHP()
            );
    }
        

        lastResult = new ActionResult(
            getName(),
            totalDamage,
            0,
            false,
            null,
            targetSummaries,
            false,
            ""
        );

    }
}
