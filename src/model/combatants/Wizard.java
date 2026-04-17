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
    public String getSpecialSkillName() {
        return "Arcane Blast";
    }

    @Override
    public boolean useSpecialSkill(Combatant[] targets){
        boolean skillused = true;

        if(this.getSpecialSkillCooldown() != 0){
            return !skillused;
        }
        
        else{
            this.setSpecialSkillCooldown(3);
            boolean[] wasAlive = new boolean[targets.length];

            for(int i = 0; i < targets.length; i++){
                wasAlive[i] = targets[i].isAlive(); //Register Enemies that were alive before ArcaneBlast
            }

            new ArcaneBlastAction().execute(this, targets);

            for(int i = 0; i < targets.length; i++){
                if(wasAlive[i] && !targets[i].isAlive()){ // if was killed by ArcaneBlast then increase ATK
                    this.increaseAttack();
                }
            }
            return skillused;
        }
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
