package model.combatants;
import model.actions.*;

public class Warrior extends Player {
    public Warrior(){
        super("Warrior", 260, 40, 20, 30);
    }

    @Override
    public boolean useSpecialSkill(Combatant[] target){
        if(this.getSpecialSkillCooldown() != 0){
            System.out.println("Shield Bash is on cooldown! Turns remaining: " + this.getSpecialSkillCooldown());
            return false;
        }

        else{
            this.setSpecialSkillCooldown(3);
            new ShieldBashAction().execute(this, target);
            return true;

        }
    }
}
