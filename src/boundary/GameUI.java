package boundary;

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
}
