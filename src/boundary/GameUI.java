package boundary;

import java.util.List;

import model.actions.ArcaneBlastAction;
import model.actions.ShieldBashAction;
import model.combatants.*;
import model.items.*;

public class GameUI {

    //Initiation
    public void ShowPlayerStats() {
        System.out.println("\n=================================");
        System.out.println("          CHARACTER LIST");
        System.out.println("=================================");

        System.out.println("Warrior");
        System.out.println("HP            : 260");
        System.out.println("Attack        : 40");
        System.out.println("Defense       : 20");
        System.out.println("Speed         : 30");
        System.out.println("Special Skill : Shield Bash");
        System.out.println("Description   : " + new ShieldBashAction().getDescription());
        System.out.println("---------------------------------");

        System.out.println("Wizard");
        System.out.println("HP            : 200");
        System.out.println("Attack        : 50");
        System.out.println("Defense       : 10");
        System.out.println("Speed         : 20");
        System.out.println("Special Skill : Arcane Blast");
        System.out.println("Description   : " + new ArcaneBlastAction().getDescription());
        System.out.println("---------------------------------");
    }

    public void ShowItemStats() {
        System.out.println("\n=================================");
        System.out.println("             ITEM LIST");
        System.out.println("=================================");

        System.out.println("Potion");
        System.out.println("Description : " + new Potion().getDescription());
        System.out.println("---------------------------------");

        System.out.println("Power Stone");
        System.out.println("Description : " + new PowerStone().getDescription());
        System.out.println("---------------------------------");

        System.out.println("Smoke Bomb");
        System.out.println("Description : " + new SmokeBomb().getDescription());
        System.out.println("---------------------------------");
    }

    public void ShowDifficultyStats() {
        System.out.println("\n=================================");
        System.out.println("              LEVELS");
        System.out.println("=================================");

        System.out.println("Level 1");
        System.out.println("Difficulty   : Easy");
        System.out.println("Enemy Pool   : Initial Spawn - 3 Goblins");
        System.out.println("---------------------------------");

        System.out.println("Level 2");
        System.out.println("Difficulty   : Medium");
        System.out.println("Enemy Pool   : Initial Spawn - 1 Goblin, 1 Wolf");
        System.out.println("               Backup Spawn  - 2 Wolves");
        System.out.println("---------------------------------");

        System.out.println("Level 3");
        System.out.println("Difficulty   : Hard");
        System.out.println("Enemy Pool   : Initial Spawn - 2 Goblins");
        System.out.println("               Backup Spawn  - 1 Goblin, 2 Wolves");
        System.out.println("---------------------------------");
    }

    //Gameplay
    public void displayRoundInfo(Player player, List<Enemy> enemies) {
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

    public void showPlayerActions(Player player)
    {
        int skillCD = player.getSpecialSkillCooldown();
        System.out.println("Choose your attack:");
        System.out.println("(1)Basic Attack\n(2)Defend\n(3)Item");
        if (skillCD > 0)
        {
            System.out.println("(4)Special Skill: (Cooldown: " + skillCD + ")");
        }
        else
        {
            System.out.println("(4)Special Skill");
        }
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
