package model.actions;
import model.combatants.*;

public abstract class Action {
    private String name;

    public Action(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
    
    public abstract String getDescription();

    public abstract void execute(Combatant user, Combatant[] targets);
}
