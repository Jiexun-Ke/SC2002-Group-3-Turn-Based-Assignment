package model.turn_order;


import model.combatants.*;

public class SpeedBasedTurnStrategy implements TurnOrderStrategy{ //Bubble sort
    
    @Override
    public Combatant[] determineTurnOrder(Combatant[] combatants){
        for(int passnum = 0; passnum < combatants.length - 1; passnum++){
            for(int i = 0; i < combatants.length - passnum - 1; i++){
                if (combatants[i].getSpeed() < combatants[i+1].getSpeed()){
                    Combatant temp = combatants[i];
                    combatants[i] = combatants[i+1];
                    combatants[i+1] = temp;
                }
            }
        }

        return combatants;
    }
}
