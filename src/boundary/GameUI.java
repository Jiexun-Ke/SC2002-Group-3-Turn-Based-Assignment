package boundary;

import java.util.List;
import model.combatants.*;
import model.items.*;

public class GameUI {
    private final InputValidator validator;

    public GameUI(){
        this.validator = new InputValidator();
    }

    public void slowPrint(String text, int delay){
        for (char c: text.toCharArray()){
            System.out.print(c);
            try {
                Thread.sleep(delay); 
            }
            catch (InterruptedException e) {}
        }
        System.out.println();
    }
    public void print(String text){
        slowPrint(text, 5);
    }
    public void pause(int ms){
        try {
            Thread.sleep(ms);
        }
        catch (InterruptedException e) {}
    }

public void divider(){
    System.out.println("══════════════════════════════════════");
}

public void section(String title) {
    divider();
    System.out.println(" " + title);
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

        return validator.getIntInRange("-> Select an option: ", 1, 2);
    }

    public int promptDifficultySelection() {
        pause(200);
        print(" ");
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

        return validator.getIntInRange("-> Enter choice (1-3): ", 1, 3);
    }

    public int promptStartingItemSelection(int itemNumber) {
        print(" ");
        section("         SELECT ITEM " + itemNumber + "/2");

        print("1. Potion      -> Heal");
        print("2. Power Stone -> Boost Attack");
        print("3. Smoke Bomb  -> Escape utility");
        return validator.getIntInRange("-> Enter choice (1-3): ", 1, 3);
    }

    public int promptPlayerSelection(){
        print(" ");
        section("WELCOME TO TURN-BASED COMBAT ARENA");

        print("--- SELECT YOUR CHAMPION ---");
        print("1. Warrior");
        print("   STATS: HP: 260  ATK: 40  DEF: 20  SPD: 30");
        print("   ABILITY -> Shield Bash: Stun enemy chosen. Last for 2 rounds.");
        print("2. Wizard");
        print("   STATS: HP: 200  ATK: 50  DEF: 10  SPD: 20");
        print("   ABILITY ->  Arcane Blast: AOE. Scaling ATK with enemies defeated.");

        return validator.getIntInRange("-> Choose your champion (1-2): ", 1, 2);
    }

    public int promptPlayerAction(Player player){
        print("");
        section("YOUR TURN");
        print("1. Attack");
        print("2. Defend");
        print("3. Use Skill");
        print("4. Use Item");

        return validator.getIntInRange("-> Choose action (1-4): ",1, 4 );
    }

    public int promptEnemyTargetSelection(List<Enemy> enemies){
        print(" ");
        section("SELECT TARGET");

        for (int i = 0; i < enemies.size(); i++){
            Enemy e = enemies.get(i);
            if (e.isAlive()){
                System.out.println((i + 1) + ". " + e.getName() + " HP: " 
                + e.getCurrentHP() + "/" + e.getMaxHP());
            }
        }

        divider();
        return validator.getIntInRange("Choose target: ", 1, enemies.size());
    }

    public int promptItemSelection(Item[] inventory){
        print(" ");
        section("INVENTORY");

        
        int availItems = 0;
        for (int i = 0; i < inventory.length; i++){
            if (inventory[i] != null){
                System.out.println((availItems +1) +". " + inventory[i].getName() + " -> " + inventory[i].getDescription());
                availItems++;
            }
        }

        if(availItems == 0){
            print("Inventory is empty.");
            return -1;
        }

        System.out.println((availItems + 1) + ". Cancel");
        divider();

        int choice = validator.getIntInRange("-> Choose item: ", 1, availItems + 1);

        if (choice == availItems + 1){
            return -1; // Cancel
        }
        
        return choice;
    }
   
   
    // DISPLAY OUTPUTS (DATA COLLECTED FROM CONTROL)

    public void showVictory(int remainingHp, int maxHp, int rounds) {
        print(" ");
        section("        VICTORY");

        print("Congratulations, you have defeated all your enemies.");
        System.out.println("HP Remaining -> " + remainingHp + "/" + maxHp);
        System.out.println("Rounds Taken -> " + rounds);
        divider();
    }

    public void showDefeat(int enemiesRemaining, int rounds) {
        print(" ");
        section("        DEFEATED");

        print("You fought bravely..");
        System.out.println("Enemies left standing -> " + enemiesRemaining);
        System.out.println("Rounds Survived -> " + rounds);
        divider();
    }

    public void displayRoundInfo(int round, Player player, Enemy[] enemies) {

        System.out.println("Player HP -> " + player.getCurrentHP() + "/" + player.getMaxHP());
        print("Enemies");
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                System.out.println("- " + enemy.getName() + " HP: " + enemy.getCurrentHP() + "/" + enemy.getMaxHP());
            }
        }
    }

    public void showNewWave(int enemyCount) {
        pause(300);
        section("NEW WAVE INCOMING");
        print(enemyCount + " enemies approaching...");
        divider();
    }

    public void showMessage(String message) {
    slowPrint(message, 8);
    }

    public void showRoundHeader(int roundNumber) {
        pause(200);
        print(" ");
    section("ROUND " + roundNumber);
}

// call when exit game
public void closeUI(){
    validator.closeScanner();
}
}
