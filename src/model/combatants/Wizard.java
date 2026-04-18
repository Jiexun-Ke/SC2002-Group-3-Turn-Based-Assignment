package model.combatants;
import model.actions.*;
public class Wizard extends Player {

    public Wizard(){
        super("Wizard", 200, 50, 10, 20);
    }

    public void increaseAttack(int amount){
        this.setAttack(this.getAttack() + amount);
    }

    public void resetAttack(){ //probably redundant but just in case we want to reset the attack back to base value after a battle
        this.setAttack(50);
    }

    


    @Override
    public String getSpecialSkillName() {
        return "Arcane Blast";
    }

    

    @Override
    public Action createSpecialSkillAction() {
        return new ArcaneBlastAction();
    }

    @Override
    public boolean specialSkillTargetsAllEnemies() {
        return true;
    }
}
