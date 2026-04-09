package boundary;

import java.util.List;

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
        print("2. Use Skill");
        print("3. Use Item");

        return validator.getIntInRange("Choose your action (1-3): ",1, 3 );
    }

    public int promptEnemyTargetSelection(List<Enemy> enemies){
        print("--- SELECT A TARGET ---");
        for (int i = 0; i < enemies.size(); i++){
            Enemy e = enemies.get(i);
            if (e.isAlive()){
                print((i + 1) + ". " + e.getName() + e.getCurrentHP() + "/" + e.getMaxHP() + ")");
            }
        }
        return validator.getIntInRange("Choose target: ", 1, enemies.size());
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
        print("Player HP: " + player.getCurrentHP() + "/" + player.getMaxHP());
        print("--- Enemies ---");
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                print("- " + enemy.getName() + " HP: " + enemy.getCurrentHP() + "/" + enemy.getMaxHP());
            }
        }
        print("------------------------------")
    }

    public void showNewWave(int enemyCount) {
        print("WARNING: A new wave of enemies approaches! ("+ enemyCount + "enemies)");
    }

    public void showMessage(String message) {
    print(message);
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
