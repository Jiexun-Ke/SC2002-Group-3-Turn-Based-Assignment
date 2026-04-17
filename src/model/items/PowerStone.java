package model.items;

import model.combatants.*;
import model.targeting.SpecialSkillTargetingStrategy;

public class PowerStone extends Item{
    public PowerStone(){
        super("Power Stone", new SpecialSkillTargetingStrategy());
    }

    @Override
    public String getDescription(){
        return "Trigger the special skill effect once when used, but does not start or change the skill's cooldown timer. In short, free extra use of the skill";
    }

    

    @Override
    public void use(Player user, Combatant[] targets){
        user.useSpecialSkillWithoutCooldown(targets);
    }
}
