package model.actions;
import model.combatants.Combatant;
import model.status_effects.SmokeBombEffect;
import model.status_effects.StatusEffect;

public class BasicAttackAction extends Action{
    public BasicAttackAction(){
        super("BasicAttackAction");
    }

    @Override
    public String getDescription(){
        return "Performs a basic attack on the selected target.";
    }
    
    @Override
    public void execute(Combatant user, Combatant[] targets){
        for (Combatant target : targets){
            for (StatusEffect effect : target.getStatusEffects()) {
                if (effect instanceof SmokeBombEffect) {
                    System.out.println("Smoke Bomb is active on " + target.getName() + "! No damage will be dealt to this target.");
                }

                else{
                    target.takeDamage(user.getAttack());
                    System.out.println(user.getName() + " attacks " + target.getName() + " for " + (user.getAttack() - target.getDefense()) + " damage!");
                }
            }
        }            
    }


}



