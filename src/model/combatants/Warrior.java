package model.combatants;
import model.actions.*;

public class Warrior extends Player {
    public Warrior(){
        super("Warrior", 260, 40, 20, 30);
    }

    @Override
    public String getSpecialSkillName() {
        return "Shield Bash";
    }

    @Override
    public boolean useSpecialSkill(Combatant[] target){
        boolean skillused = true;

        if(this.getSpecialSkillCooldown() != 0){
            return !skillused;
        }

        else{
            this.setSpecialSkillCooldown(3);
            new ShieldBashAction().execute(this, target);
            return skillused;

        }
    }

    //override new method
    @Override
    public boolean specialSkillTargetsAllEnemies(){
        return false;
    }
}
