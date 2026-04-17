package control;

import model.actions.Action;
import model.actions.BasicAttackAction;
import model.actions.DefendAction;
import model.actions.UseItemAction;
import model.combatants.Combatant;
import model.combatants.Player;
import model.items.Item;

public class PlayerActionSelector {
    private final TargetSelector targetSelector;

    public PlayerActionSelector(TargetSelector targetSelector) {
        this.targetSelector = targetSelector;
    }

    public PlayerActionChoice selectAction(Player player, BattleContext context) {
        int choice = context.getUi().promptPlayerAction(player);

        switch (choice) {
            case 1:
                return createBasicAttackChoice(context);

            case 2:
                return createDefendChoice();

            case 3:
                return createSpecialSkillChoice(player, context);

            case 4:
                return createItemChoice(player, context);

            default:
                context.getUi().showMessage("Invalid choice.");
                return null;
        }
    }

    private PlayerActionChoice createBasicAttackChoice(BattleContext context) {
        Combatant[] targets = targetSelector.selectSingleEnemy(context);

        if (targets == null) {
            return null;
        }

        return new PlayerActionChoice(new BasicAttackAction(), targets);
    }

    private PlayerActionChoice createDefendChoice() {
        return new PlayerActionChoice(new DefendAction(), targetSelector.selectNoTargets());
    }

    private PlayerActionChoice createSpecialSkillChoice(Player player, BattleContext context) {
        Combatant[] targets = targetSelector.selectSpecialSkillTargets(player, context);

        if (targets == null) {
            return null;
        }

        return new PlayerActionChoice(new createSpecialSkillAction(), targets);
    }

    private PlayerActionChoice createItemChoice(Player player, BattleContext context) {
        Item[] inventory = player.getInventory();
        int itemChoice = context.getUi().promptItemSelection(inventory);

        if (itemChoice == -1) {
            context.getUi().showMessage("Item use cancelled.");
            return null;
        }

        int itemIndex = itemChoice - 1;

        if (itemIndex < 0 || itemIndex >= inventory.length || inventory[itemIndex] == null) {
            context.getUi().showMessage("Invalid item choice.");
            return null;
        }

        Item chosenItem = inventory[itemIndex];
        Combatant[] targets = chosenItem.selectTargets(player, context, targetSelector);

        if (targets == null) {
            return null;
        }

        return new PlayerActionChoice(new UseItemAction(chosenItem), targets);
    }
}