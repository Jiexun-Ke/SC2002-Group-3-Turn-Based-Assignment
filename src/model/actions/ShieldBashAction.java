package model.actions;
import java.util.ArrayList;
import java.util.List;
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
        Combatant target = targets[0];
        int damage = Math.max(0, user.getAttack() - target.getDefense());
        target.takeDamage(damage);

        boolean stunned = false;
        if (target.isAlive()) {
            target.addStatusEffect(new StunEffect());
            stunned = true;
        }

        List<String> targetSummaries = new ArrayList<>();
        targetSummaries.add(target.getName() + " HP: " + target.getCurrentHP() + "/" + target.getMaxHP());

        lastResult = new ActionResult(
            getName(),
            damage,
            0,
            stunned,
            stunned ? "Stun" : null,
            targetSummaries,
            false,
            ""
        );
    }


    
}
