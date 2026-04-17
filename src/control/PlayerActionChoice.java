package control;

import model.actions.Action;
import model.combatants.Combatant;

public class PlayerActionChoice {
    private final Action action;
    private final Combatant[] targets;

    public PlayerActionChoice(Action action, Combatant[] targets) {
        this.action = action;
        this.targets = targets;
    }

    public Action getAction() {
        return action;
    }

    public Combatant[] getTargets() {
        return targets;
    }
}