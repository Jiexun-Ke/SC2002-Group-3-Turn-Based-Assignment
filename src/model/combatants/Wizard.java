package model.combatants;
import model.actions.*;
public class Wizard extends Player {
    private int bonusAttack;

    public Wizard(){
        super("Wizard", 200, 50, 10, 20);
        this.bonusAttack = 0;
    }

    public void increaseAttack(){
        this.setAttack(this.getAttack() + bonusAttack);
    }

    public void resetAttackBonus(){
        this.bonusAttack = 0;
        this.setAttack(50);
    }

    @Override
    public void useSpecialSkill(Combatant[] targets){

        if(this.getSpecialSkillCooldown() != 0){
            System.err.println("Arcane blast is on cooldown! Turns remaining: " + this.getSpecialSkillCooldown());
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
                    this.bonusAttack += 10;
                }
            }

            this.increaseAttack();
        }
    }

}
