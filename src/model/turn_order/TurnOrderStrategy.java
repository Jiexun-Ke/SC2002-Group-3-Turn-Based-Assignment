package model.turn_order;
import java.util.List;
import model.combatants.*;

public interface TurnOrderStrategy {
     public List<Combatant> determineTurnOrder(List<Combatant> combatants);
}
