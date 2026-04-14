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
         boolean smokeBombActive = false;

        for (StatusEffect effect : user.getStatusEffects()) {
            if (effect instanceof SmokeBombEffect) {
            smokeBombActive = true;
            break;
            }
        }

        int attackValue = smokeBombActive ? 0 : user.getAttack();

        for (Combatant target : targets) {
            target.takeDamage(attackValue);
            

            //for (int i = 0; i < targets.length; i++) {
            //    targets[i].takeDamage(attackValue);
            //     if (attackValue > 0) {
            //        System.out.println(user.getName() + " attacked " + targets[i].getName() + " for " + attackValue + " damage! "
            //        + targets[i].getName() + " HP: " + targets[i].getCurrentHP() + "/" + targets[i].getMaxHP());
            //    } else {
            //        System.out.println(user.getName() + "'s attack was evaded due to Smoke Bomb! No damage dealt to " + targets[i].getName() + ".");
            //    }
            
        }
    }
}


