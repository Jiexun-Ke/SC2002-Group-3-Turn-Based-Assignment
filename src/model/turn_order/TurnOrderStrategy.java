package model.turn_order;


import model.combatants.*;

public interface TurnOrderStrategy {
     public Combatant[] determineTurnOrder(Combatant [] combatants);
}
