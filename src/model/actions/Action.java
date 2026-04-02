package model.actions;
import model.combatants.*;

public abstract class Action {
    private String name;

    public Action(String name){
        this.name = name;
    }

    public abstract void execute(Combatant user, Combatant[] targets);

    public String getName(){
        return this.name;
    }
}
