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
            boolean smokeBombActive = false;

            for (StatusEffect effect : target.getStatusEffects()) { //Check if target has smoke bomb effect, if have just exit loop
                if (effect instanceof SmokeBombEffect) {
                    smokeBombActive = true;
                    break;
                }
            }

            if (smokeBombActive) {
                continue; //smoke bomb active, go straight to next combatant (if have)
            }

            target.takeDamage(user.getAttack());
        }
    }
}




