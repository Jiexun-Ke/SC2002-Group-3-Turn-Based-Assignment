package model.actions;
import model.combatants.Combatant;

public abstract class Action {
    private final String name;
    protected ActionResult lastResult;

    public ActionResult getLastResult() {
        return lastResult;
    }

    public Action(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
    
    public abstract String getDescription();

    public abstract void execute(Combatant user, Combatant[] targets);
}
