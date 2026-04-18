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
        if (user.hasStatusEffect(DefenseBuffEffect.class)) {
            lastResult = new ActionResult(
                getName(),
                0,
                0,
                false,
                null,
                new ArrayList<>(),
                true,
                "Defense buff is already active"
            );
            return;
        }

        boolean applied = user.addStatusEffect(new DefenseBuffEffect());

        lastResult = new ActionResult(
            getName(),
            0,
            0,
            applied,
            applied ? "Defense +10" : null,
            new ArrayList<>(),
            !applied,
            applied ? "" : "Cannot apply more status effects"
        );
    }
}
