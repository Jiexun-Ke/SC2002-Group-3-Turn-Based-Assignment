package model.items;
import model.combatants.*;

public class PowerStone extends Item{
    public PowerStone(){
        super("Power Stone");
    }

    @Override
    public Combatant[] selectTargets(Player user, BattleContext context, TargetSelector targetSelector) {
        return targetSelector.selectSpecialSkillTargets(user, context);
    }
    
    @Override
    public String getDescription(){
        return "Trigger the special skill effect once when used, but does not start or change the skill's cooldown timer. In short, free extra use of the skill";
    }

    @Override
    public boolean targetsPlayer() {
        return false;
    }

    @Override
    public boolean usesSpecialSkillTargets() {
        return true;
    }

    @Override
    public void use(Player user, Combatant[] targets){
        int initial_cooldown = user.getSpecialSkillCooldown();

        user.setSpecialSkillCooldown(0);
        user.useSpecialSkill(targets);
        user.setSpecialSkillCooldown(initial_cooldown);
    }
}
