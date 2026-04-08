package model.turn_order;
import java.util.List;
import model.combatants.*;

public class SpeedBasedTurnStrategy implements TurnOrderStrategy{ //Bubble sort
    
    @Override
    public List<Combatant> determineTurnOrder(List<Combatant> combatants){
        for (int passnum = 0; passnum < combatants.size() - 1; passnum++) {
            for (int i = 0; i < combatants.size() - passnum - 1; i++) {
                if (combatants.get(i).getSpeed() < combatants.get(i + 1).getSpeed()) {
                    Combatant temp = combatants.get(i);
                    combatants.set(i, combatants.get(i + 1));
                    combatants.set(i + 1, temp);
                }
            }
        }

        return combatants;
    }
}
