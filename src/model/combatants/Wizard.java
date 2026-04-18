package model.combatants;
import model.actions.*;
public class Wizard extends Player {

    public Wizard(){
        super("Wizard", 200, 50, 10, 20);
    }

    public void increaseAttack(){
        this.setAttack(this.getAttack() + 10);
    }

    public void resetAttack(){ //probably redundant but just in case we want to reset the attack back to base value after a battle
        this.setAttack(50);
    }

    @Override
    public boolean useSpecialSkillWithoutCooldown(Combatant[] targets) {
        new ArcaneBlastAction().execute(this, targets);
        return true;
    }


    @Override
    public String getSpecialSkillName() {
        return "Arcane Blast";
    }

    @Override
    public boolean useSpecialSkill(Combatant[] targets) {
        if (this.getSpecialSkillCooldown() != 0) {
            return false;
        }

        this.setSpecialSkillCooldown(3);
        new ArcaneBlastAction().execute(this, targets);
        return true;
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
