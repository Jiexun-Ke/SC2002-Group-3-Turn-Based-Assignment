package model.items;
import model.combatants.*;

public abstract class Item {
    private String name;

    public Item(String name){
        this.name = name;
    }

    public void use(Combatant user, Combatant[] targets){}

    public String getName(){
        return this.name;
    }

    public abstract String getDescription();

}
