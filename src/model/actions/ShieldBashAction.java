package model.actions;
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
        for (Combatant target : targets) {
            if (target == null || !target.isAlive()) {
                continue;
            }

            int damage = Math.max(0, user.getAttack() - target.getDefense());

            damage = target.modifyIncomingDamage(user, damage);

            target.takeRawDamage(damage);
            target.addStatusEffect(new StunEffect());
        }
    }
}
