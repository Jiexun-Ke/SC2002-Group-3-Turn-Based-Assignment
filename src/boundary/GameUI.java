package boundary;

import java.util.List;

import model.combatants.*;

public class GameUI {
    public void showVictory(int remainingHp, int maxHp, int rounds) {
        System.out.println("Congratulations, you have defeated all your enemies.");
        System.out.println("Remaining HP: " + remainingHp + "/" + maxHp);
        System.out.println("Total Rounds: " + rounds);
    }

    public void showDefeat(int enemiesRemaining, int rounds) {
        System.out.println("Defeated. Don't give up, try again!");
        System.out.println("Enemies remaining: " + enemiesRemaining);
        System.out.println("Total Rounds Survived: " + rounds);
    }

    public void displayRoundInfo(int round, Player player, Enemy[] enemies) {
        System.out.println("Round " + round);
        System.out.println("Player HP: " + player.getCurrentHP() + "/" + player.getMaxHP());
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                System.out.println(enemy.getName() + " HP: " + enemy.getCurrentHP() + "/" + enemy.getMaxHP());
            }
        }
    }

    public void showNewWave(int enemyCount) {
        System.out.println("A new wave of enemies approaches! Enemies remaining: " + enemyCount);
    }

    public void showMessage(String message) {
    System.out.println(message);
    }

    public void showRoundHeader(int roundNumber) {
    System.out.println("========== Round " + roundNumber + " ==========");
}

    public void showBattleStatus(Player player, List<Enemy> enemies) {
    System.out.println("Player HP: " + player.getCurrentHP() + "/" + player.getMaxHP());
    for (Enemy enemy : enemies) {
        System.out.println(enemy.getName() + " HP: " + enemy.getCurrentHP());
    }
}


}
