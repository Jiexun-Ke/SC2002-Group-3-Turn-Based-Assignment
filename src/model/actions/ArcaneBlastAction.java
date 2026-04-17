package model.actions;
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
        if (targets == null || targets.length == 0) {
            return;
        }

        for (Combatant target : targets) {
            if (target == null || !target.isAlive()) {
                continue;
            }

            int damage = Math.max(0, user.getAttack() - target.getDefense());

            damage = target.modifyIncomingDamage(user, damage);

            target.takeRawDamage(damage);
        }
    }
}
