package model.actions;
import java.util.ArrayList;
import model.combatants.*;
import model.status_effects.DefenseBuffEffect;

public class DefendAction extends Action{
    public DefendAction(){
        super("Defend");
    }

    @Override
    public String getDescription(){
        return "Increases defense by 10 for the current round and the next round";
    }

    

    @Override
    public void execute(Combatant user, Combatant[] targets){
        user.addStatusEffect(new DefenseBuffEffect());


        lastResult = new ActionResult(
            getName(),
            0,
            0,
            true,
            "Defense +10",
            new ArrayList<>(),
            false,
            ""
        );
    }
}
