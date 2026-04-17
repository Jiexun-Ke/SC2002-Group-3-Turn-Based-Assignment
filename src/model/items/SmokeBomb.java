package model.items;
import model.combatants.*;
import model.status_effects.*;

public class SmokeBomb extends Item {
    public SmokeBomb(){
        super("Smoke Bomb");
    }

    @Override
    public Combatant[] selectTargets(Player user, BattleContext context, TargetSelector targetSelector) {
        return targetSelector.selectAllEnemies(context);
    }

    @Override
    public String getDescription() {
        return "When used, Enemy attacks do 0 damage to the user in the current turn and the next turn";
    }

    @Override
    public boolean targetsPlayer(){
        return true;
    }

    @Override
    public boolean usesSpecialSkillTargets(){
        return false;
    }

    @Override
    public void use(Player user, Combatant[] targets){
        for(Combatant target : targets){
            target.addStatusEffect(new SmokeBombEffect());
        }
    }
}
