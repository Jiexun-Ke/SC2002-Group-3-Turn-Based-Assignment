package model.items;
import model.combatants.*;

public class Potion extends Item {
    public Potion(){
        super("Potion");
    }

    @Override
    public String getDescription(){
        return "A potion that heals 100 HP when used.";
    }

    @Override
    public boolean targetsPlayer(){
        return true;
    }
    
    @Override
    public boolean usesSpecialSkillTargets(){
        return false;
    }

    @Override
    public void use(Player user, Combatant[] targets){
        for(int i=0; i < targets.length; i++){
            targets[i].heal(100);
        }
    }

}
