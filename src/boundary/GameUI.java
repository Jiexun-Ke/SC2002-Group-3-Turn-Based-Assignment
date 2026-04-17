package boundary;

import java.util.List;
import model.actions.ArcaneBlastAction;
import model.actions.ShieldBashAction;
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

        return validator.getIntInRange("Select an option: ", 1, 2);
    }

    public int promptPlayerSelection(){
        print(" ");
        section("WELCOME TO TURN-BASED COMBAT ARENA");

        print("--- SELECT YOUR CHAMPION ---");
        print("1. Warrior");
        print("   STATS: HP: 260  ATK: 40  DEF: 20  SPD: 30");
        print("   ABILITY -> Shield Bash: " + new ShieldBashAction().getDescription());
        print(" ");
        print("2. Wizard");
        print("   STATS: HP: 200  ATK: 50  DEF: 10  SPD: 20");
        print("   ABILITY ->  Arcane Blast: " + new ArcaneBlastAction().getDescription());
        print(" ");
        
        return validator.getIntInRange("Choose your champion (1-2): ", 1, 2);
    }

    
    public int promptStartingItemSelection(int itemNumber) {
        slowPrint(" ", 8);
        section("         SELECT ITEM " + itemNumber + "/2");
        print("1. Potion      -> " + new Potion().getDescription());
        print("2. Power Stone -> " + new PowerStone().getDescription());
        print("3. Smoke Bomb  -> " + new SmokeBomb().getDescription());
        slowPrint(" ", 8);
        return validator.getIntInRange("Enter choice (1-3): ", 1, 3);
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
        slowPrint(" ", 8);
        section("SELECT TARGET");

        System.out.println("0. Back");

        for (int i = 0; i < enemies.size(); i++){
            Enemy e = enemies.get(i);

            if (e.isAlive()){
                System.out.println((i + 1) + ". " + e.getName() + " HP: " 
                + e.getCurrentHP() + "/" + e.getMaxHP());
            }
        }

        divider();
        print("");

        while (true) {

            int choice = validator.getInt("Choose target (0 to go back): ");

            if (choice == 0) {
                return 0;
            }

            if (choice >= 1 && choice <= enemies.size()) {
                Enemy selectedEnemy = enemies.get(choice - 1);

                if (selectedEnemy.isAlive()) {
                    return choice;
                }
            }

            System.out.println("Invalid target choice. Please try again.");
        }

        
    }

    public int promptItemSelection(Item[] inventory){
        slowPrint(" ", 8);
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

        print("");
        int choice = validator.getIntInRange("Choose item: ", 1, availItems + 1);

        if (choice == availItems + 1){
            return -1; // Cancel
        }
        
        return choice;
    }
   
   
    // DISPLAY OUTPUTS (DATA COLLECTED FROM CONTROL)

    public void showVictory(int remainingHp, int maxHp, int rounds) {
        slowPrint(" ", 8);
        section("        VICTORY");

        print("Congratulations, you have defeated all your enemies.");
        System.out.println("HP Remaining -> " + remainingHp + "/" + maxHp);
        System.out.println("Rounds Taken -> " + rounds);
        divider();
    }

    public void showDefeat(int enemiesRemaining, int rounds) {
        slowPrint(" ", 8);
        section("        DEFEATED");

        print("Don't give up! Learn from this defeat and try again.");
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
        slowPrint(" ", 8);
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
