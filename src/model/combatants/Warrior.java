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
    public Action createSpecialSkillAction() {
        return new ShieldBashAction();
    }

    //override new method
    @Override
    public boolean specialSkillTargetsAllEnemies(){
        return false;
    }
}
