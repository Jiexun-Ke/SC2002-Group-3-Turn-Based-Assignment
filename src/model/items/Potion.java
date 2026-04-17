package model.items;
import model.combatants.*;
import model.targeting.SelfTargetingStrategy;

public class Potion extends Item {
    public Potion(){
        super("Potion", new SelfTargetingStrategy());
    }


    @Override
    public String getDescription(){
        return "A potion that heals 100 HP when used.";
    }

    @Override
    public void use(Player user, Combatant[] targets){
            user.heal(100);
        }
}


