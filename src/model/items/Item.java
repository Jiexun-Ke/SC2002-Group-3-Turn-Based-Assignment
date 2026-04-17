package model.items;

import control.BattleContext;
import control.TargetSelector;
import model.combatants.Combatant;
import model.combatants.Player;
import model.targeting.TargetingStrategy;

public abstract class Item {
    private final String name;
    private final TargetingStrategy targetingStrategy;

    public Item(String name, TargetingStrategy targetingStrategy){
        this.name = name;
        this.targetingStrategy = targetingStrategy;
    }

    

    public String getName(){
        return this.name;
    }

    

    public Combatant[] selectTargets(
        Player user,
        BattleContext context,
        TargetSelector targetSelector
    ) {
        return targetingStrategy.selectTargets(user, context, targetSelector);
    }

    public abstract void use(Player user, Combatant[] targets);

    public abstract String getDescription();
}
