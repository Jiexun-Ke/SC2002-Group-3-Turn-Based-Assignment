package boundary;

import java.util.List;
import model.items.*;
import model.combatants.*;

public class GameUI {
    private InputValidator validator;

    public void print(String a){
         System.out.println(a);
    }

    public GameUI(){
        this.validator = new InputValidator();
    }


    //MENUS & INPUTS (RETURNING DATA TO CONTROL)

    public int showMainMenu(){
        print("==================================");
        print("       TURN-BASED COMBAT RPG      ");
        print("==================================");
        print("1. Start New Game");
        print("2. Exit");

        return validator.getIntInRange("Select an option: ", 1, 2);
    }

    public int promptPlayerSelection(){
        print("==================================");
        print("WELCOME TO TURN-BASED COMBAT ARENA");
        print("==================================");
        print(" ");
        print("--- SELECT YOUR CHAMPION ---");
        print("1. Warrior");
        print("   STATS: HP: 260, ATK: 40, DEF: 20, SPD: 30");
        print("   ABILITY: Shield Bash: Stun enemy for 2 rounds");
        print("2. Wizard");
        print("   STATS: HP: 200, ATK: 50, DEF: 10, SPD: 20");
        print("   ABILITY: Arcane Blast: Enemy defeated add atk");

        return validator.getIntInRange("Enter choice (1-2): ", 1, 2);
    }

    public int promptPlayerAction(Player player){
        print("--- IT IS YOUR TURN ---");
        print("1. Attack");
        print("2. Defend");
        print("3. Use Skill");
        print("3. Use Item");

        return validator.getIntInRange("Choose your action (1-4): ",1, 4 );
    }

    public int promptEnemyTargetSelection(List<Enemy> enemies){
        print("--- SELECT A TARGET ---");
        for (int i = 0; i < enemies.size(); i++){
            Enemy e = enemies.get(i);
            if (e.isAlive()){
                System.out.println((i + 1) + ". " + e.getName() + e.getCurrentHP() + "/" + e.getMaxHP() + ")");
            }
        }
        return validator.getIntInRange("Choose target: ", 1, enemies.size());
    }

    public int promptItemSelection(Item[] inventory){
        print("--- INVENTORY ---");

        int availItems = 0;
        for (int i = 0; i < inventory.length; i++){
            if (inventory[i] != null){
                System.out.println((i+1) +". " + inventory[i].getName() + " - " + inventory[i].getDescription());
                availItems++;
            }
        }

        if(availItems == 0){
            print("Your inventory is empty.");
            return -1;
        }

        System.out.println((availItems + 1) + ". Cancel");
        return validator.getIntInRange("Choose an item: ", 1, availItems + 1);
    }
   
   
    // DISPLAY OUTPUTS (DATA COLLECTED FROM CONTROL)

    public void showVictory(int remainingHp, int maxHp, int rounds) {
        print("----------- VICTORY ------------");
        print("Congratulations, you have defeated all your enemies.");
        System.out.println("Remaining HP: " + remainingHp + "/" + maxHp);
        System.out.println("Total Rounds: " + rounds);
    }

    public void showDefeat(int enemiesRemaining, int rounds) {
        print("----------- DEFEATED -------------");
        print("Don't give up, try again.");
        System.out.println("Enemies left standing: " + enemiesRemaining);
        System.out.println("Total Rounds Survived: " + rounds);
    }

    public void displayRoundInfo(int round, Player player, Enemy[] enemies) {
        System.out.println("Player HP: " + player.getCurrentHP() + "/" + player.getMaxHP());
        print("--- Enemies ---");
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                System.out.println("- " + enemy.getName() + " HP: " + enemy.getCurrentHP() + "/" + enemy.getMaxHP());
            }
        }
        print("------------------------------");
    }

    public void showNewWave(int enemyCount) {
        System.out.println("WARNING: A new wave of enemies approaches! ("+ enemyCount + "enemies)");
    }

    public void showMessage(String message) {
    System.out.println(message);
    }

    public void showRoundHeader(int roundNumber) {
    System.out.println("========== ROUND " + roundNumber + " ==========");
}

    public void showBattleStatus(Player player, List<Enemy> enemies) {
    System.out.println("Player HP: " + player.getCurrentHP() + "/" + player.getMaxHP());
    for (Enemy enemy : enemies) {
        System.out.println(enemy.getName() + " HP: " + enemy.getCurrentHP());
    }
}

// call when exit game
public void closeUI(){
    validator.closeScanner();
}
}
