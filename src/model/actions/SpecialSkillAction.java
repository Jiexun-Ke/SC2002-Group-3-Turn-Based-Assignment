package model.actions;

import model.combatants.Combatant;
import model.combatants.Player;

public class SpecialSkillAction extends Action {
    public SpecialSkillAction() {
        super("SpecialSkillAction");
    }

    @Override
    public String getDescription() {
        return "Uses the player's class-specific special skill.";
    }

    @Override
    public void execute(Combatant user, Combatant[] targets) {
        if (user instanceof Player player) {
            boolean used = player.useSpecialSkill(targets);

            if (!used) {
                System.out.println("Skill is on cooldown for "
                        + player.getSpecialSkillCooldown()
                        + " more turn(s).");
            }
        }
    }
}