package model.items;
import model.combatants.*;

public abstract class Item {
    private String name;

    public Item(String name){
        this.name = name;
    }

    public abstract void use(Player user, Combatant[] targets);

    public String getName(){
        return this.name;
    }

    public abstract String getDescription();

    public abstract boolean targetsPlayer();

    public abstract boolean usesSpecialSkillTargets();
}
