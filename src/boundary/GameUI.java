package boundary;

import control.ItemOption;
import java.util.List;
import model.actions.Action;
import model.actions.ActionResult;
import model.combatants.*;
import model.items.*;

public class GameUI {
    public static final int EXIT = 2;
    private final InputValidator validator;

    
    public GameUI(){
        this.validator = new InputValidator();
    }

    private static final int CHAR_DELAY = 5;
    private static final int LINE_PAUSE = 120;
    private static final int SECTION_PAUSE = 200;

    public void slowPrint(String text, int delay){
        for (char c: text.toCharArray()){
            System.out.print(c);
            try {
                Thread.sleep(delay); 
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }   
        System.out.println();
        pause(LINE_PAUSE);
    }
    public void print(String text){
        slowPrint(text, CHAR_DELAY);
    }
    public void pause(int ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void divider(){
        System.out.println("══════════════════════════════════════");
    }

    public void section(String title) {
        pause(SECTION_PAUSE);
        divider();
        print(" " + title);
        divider();
    }

    //MENUS & INPUTS (RETURNING DATA TO CONTROL)

    public int showMainMenu(){
        pause(300);
        divider();
        print("       TURN-BASED COMBAT RPG      ");
        divider();
        print("1. Start New Game");
        print("2. Exit");

        return validator.getIntInRange("Select an option: ", 1, 2);
    }

    public int promptPlayerSelection(String warriorSkillDesc, String wizardSkillDesc) {
        print(" ");
        section("WELCOME TO TURN-BASED COMBAT ARENA");

        print("--- SELECT YOUR CHAMPION ---");
        print("1. Warrior");
        print("   STATS: HP: 260  ATK: 40  DEF: 20  SPD: 30");
        print("   ABILITY -> Shield Bash: " + warriorSkillDesc);
        print(" ");
        print("2. Wizard");
        print("   STATS: HP: 200  ATK: 50  DEF: 10  SPD: 20");
        print("   ABILITY ->  Arcane Blast: " + wizardSkillDesc);
        print(" ");
        
        return validator.getIntInRange("Choose your champion (1-2): ", 1, 2);
    }

    
    public int promptStartingItemSelection(int itemNumber, List<ItemOption> itemOptions) {
        print(" ");
        section("         SELECT ITEM " + itemNumber + "/2");

        for (int i = 0; i < itemOptions.size(); i++) {
            ItemOption option = itemOptions.get(i);
            print((i + 1) + ". " + option.getName() + " -> " + option.getDescription());
        }

        print(" ");
        return validator.getIntInRange(
                "Enter choice (1-" + itemOptions.size() + "): ",
                1,
                itemOptions.size()
        );
    }

    public int promptDifficultySelection() {
        pause(200);
        slowPrint(" ", 8);
        section("           ENEMY TYPES");

        print("Goblin");
        print("-> STATS: HP: 55, ATK: 35, DEF: 15, SPD: 25");
        print("Wolf");
        print("-> STATS: HP: 40, ATK: 45, DEF: 5, SPD: 35");

        pause(200);
        print(" ");
        section("        SELECT DIFFICULTY");
        print("1. Easy   -> 3 Goblins");
        print("2. Medium -> 1 Goblin, 1 Wolf (Backup: 2 Wolves)");
        print("3. Hard   -> 2 Goblins (Backup: 1 Goblin, 2 Wolves)");

        print(" ");
        return validator.getIntInRange("Enter choice (1-3): ", 1, 3);
    }

    public int promptPlayerAction(Player player){
        print("");
        section("YOUR TURN");
        slowPrint(" ", 8);
        print("1. Attack");
        print("2. Defend");
        print("3. Use Skill");
        print("4. Use Item");
        slowPrint(" ", 8);

        return validator.getIntInRange("Choose action (1-4): ",1, 4 );
    }

    public int promptEnemyTargetSelection(List<Enemy> enemies){
        print(" ");
        section("SELECT TARGET");

        print("0. Back");

        int displayedIndex = 0;

        for (Enemy e : enemies){
            if (e.isAlive()){
                displayedIndex++;
                print(displayedIndex + ". " + e.getName() + " HP: "
                        + e.getCurrentHP() + "/" + e.getMaxHP());
            }
        }

        if (displayedIndex == 0) {
            print("No valid targets available.");
            return 0;
        }

        divider();
        print("");

        while (true) {
            int choice = validator.getInt("Choose target (0 to go back): ");

            if (choice == 0) {
                return 0;
            }

            if (choice >= 1 && choice <= displayedIndex) {
                int aliveCounter = 0;

                for (int i = 0; i < enemies.size(); i++) {
                    Enemy enemy = enemies.get(i);

                    if (enemy.isAlive()) {
                        aliveCounter++;
                        if (aliveCounter == choice) {
                            return i + 1;
                        }
                    }
                }
            }

            print("Invalid target choice. Please try again.");
        }
}

    public int promptItemSelection(Item[] inventory){
        print(" ");
        section("INVENTORY");

        
        int availItems = 0;
        for (Item item : inventory){
            if (item != null){
                print((availItems + 1) + ". " + item.getName() + " -> " + item.getDescription());
                availItems++;
            }
        }

        if(availItems == 0){
            print("Inventory is empty.");
            return -1;
        }

        print((availItems + 1) + ". Cancel");
        divider();
        print(" ");

        int choice = validator.getIntInRange("Choose item: ", 1, availItems + 1);

        if (choice == availItems + 1){
            return -1; // Cancel
        }
        
        print(" ");
        return choice;
    }
   
   
    // DISPLAY OUTPUTS (DATA COLLECTED FROM CONTROL)

    public void showVictory(int remainingHp, int maxHp, int rounds) {
        print(" ");
        section("        VICTORY");

        print("Congratulations, you have defeated all your enemies.");
        print("HP Remaining -> " + remainingHp + "/" + maxHp);
        print("Rounds Taken -> " + rounds);
        divider();
    }

    public void showDefeat(int enemiesRemaining, int rounds) {
        print(" ");
        section("        DEFEATED");

        print("Don't give up! Learn from this defeat and try again.");
        print("Enemies left standing -> " + enemiesRemaining);
        print("Rounds Survived -> " + rounds);
        divider();
    }

    public void displayRoundInfo(int round, Player player, Enemy[] enemies) {

        print("Player HP -> " + player.getCurrentHP() + "/" + player.getMaxHP());
        print("Enemies");
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                print("- " + enemy.getName() + " HP: " + enemy.getCurrentHP() + "/" + enemy.getMaxHP());
            }
        }
        print(" ");
    }

    public void showNewWave(int enemyCount) {
        pause(300);
        section("NEW WAVE INCOMING");
        print(enemyCount + " enemies approaching...");
        divider();
    }

    public void showMessage(String message) {
        print(message);
        print( " ");
    }

    public void showRoundHeader(int roundNumber) {
        pause(200);
        print(" ");
    section("ROUND " + roundNumber);
}

    public void showActionResult(Combatant user, Action action) {
        ActionResult result = action.getLastResult();

        if (result == null) {
            showMessage(user.getName() + " used " + action.getName() + ".");
            return;
        }

        if (result.isPrevented()) {
            String reason = result.getReason();
            if (reason == null || reason.isEmpty()) {
                reason = "unknown reason";
            }

            showMessage(user.getName() + " could not use " + result.getActionName()
                    + ": " + reason);
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(user.getName()).append(" used ").append(result.getActionName());

        boolean hasExtraInfo = false;

        if (result.getDamageDealt() > 0) {
            sb.append(" and dealt ").append(result.getDamageDealt()).append(" damage");
            hasExtraInfo = true;
        }

        if (result.getHealingDone() > 0) {
            if (hasExtraInfo) {
                sb.append(",");
            } else {
                sb.append(" and");
            }
            sb.append(" healed ").append(result.getHealingDone()).append(" HP");
            hasExtraInfo = true;
        }

        if (!hasExtraInfo) {
            sb.append(".");
        } else {
        sb.append("!");
        }

        if (result.isAppliedStatusEffect() && result.getStatusEffectName() != null) {
            sb.append(" Applied ").append(result.getStatusEffectName()).append(".");
        }

        List<String> targetSummaries = result.getTargetSummaries();
        if (targetSummaries != null && !targetSummaries.isEmpty()) {
            sb.append(" ");
            for (int i = 0; i < targetSummaries.size(); i++) {
                sb.append(targetSummaries.get(i));
                if (i < targetSummaries.size() - 1) {
                    sb.append(" | ");
                }
            }
        }
        
        if (!result.isPrevented()) {
            String reason = result.getReason();
            if (reason != null && !reason.isEmpty()) {
                sb.append(" Note: ").append(reason).append(".");
            }
        }

        showMessage(sb.toString());
    }

    // call when exit game
    public void closeUI(){
        validator.closeScanner();
    }

}
