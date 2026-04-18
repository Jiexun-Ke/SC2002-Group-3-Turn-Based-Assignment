package model.actions;
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
        int damage = Math.max(0, user.getAttack() - target.getDefense());
        target.takeDamage(damage);

        java.util.List<String> targetSummaries = new java.util.ArrayList<>();
        targetSummaries.add(target.getName() + " HP: " + target.getCurrentHP() + "/" + target.getMaxHP());

        lastResult = new ActionResult(
            getName(),
            damage,
            0,
            false,
            null,
            targetSummaries,
            false,
            ""
        );
}
}




