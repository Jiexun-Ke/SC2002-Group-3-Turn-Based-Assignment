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
            player.useSpecialSkill(targets);
        }
    }
}