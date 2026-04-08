package control;

import boundary.GameUI;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import model.combatants.*;
import model.items.*;
import model.turn_order.SpeedBasedTurnStrategy;
import model.turn_order.TurnOrderStrategy;

public class SetupController {
    private GameUI ui;
    enum DIFFICULTY {
        EASY,
        MEDIUM,
        HARD
    }

    public SetupController(GameUI ui) {
        this.ui = ui;
    }

    private void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public GameController createGame() {
        //For initialization
        TurnOrderStrategy strategy = new SpeedBasedTurnStrategy();
        
        //Player Stuff
        ui.ShowPlayerStats();
        delay(1);
        Player player = choosePlayer();
        delay(1);

        //Item Stuff
        ui.ShowItemStats();
        player.addItem(chooseItem(1));
        player.addItem(chooseItem(2));
        delay(1);

        //Difficulty Stuff
        ui.ShowDifficultyStats();
        DIFFICULTY difficulty = chooseDifficulty();
        delay(1);

        //Creating Enemies
        List<Enemy> enemies = createEnemies(difficulty);

        //Stats confirmation
        System.out.println("\n========================================");
        System.out.println("      ALL STATS FOR INITIALIZATION");
        System.out.println("========================================");

        System.out.println("\nPlayer Stats:");
        System.out.printf("Name : %s%n", player.getName());
        System.out.printf("HP   : %d%n", player.getMaxHP());
        System.out.printf("ATK  : %d%n", player.getAttack());
        System.out.printf("DEF  : %d%n", player.getDefense());
        System.out.printf("SPD  : %d%n", player.getSpeed());

        System.out.println("\nPlayer Items:");
        Item[] inventory = player.getInventory();
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                System.out.printf("%d. %s%n", i + 1, inventory[i].getName());
            }
        }

        System.out.println("\nSelected Difficulty:");
        System.out.println(difficulty);

        System.out.println("\nEnemies:");
        for (int i = 0; i < enemies.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, enemies.get(i).getName());
        }

        System.out.println("========================================\n");
        
        return new GameController(player, enemies, strategy, ui);
    }

    private List<Enemy> createEnemies(DIFFICULTY difficulty) {
        List<Enemy> tempList = new ArrayList<>();

        switch (difficulty) {
            case EASY:
                tempList.add(new Goblin());
                tempList.add(new Goblin());
                tempList.add(new Goblin());
                break;

            case MEDIUM:
                tempList.add(new Goblin());
                tempList.add(new Wolf());
                tempList.add(new Wolf());
                tempList.add(new Wolf());
                break;

            case HARD:
                tempList.add(new Goblin());
                tempList.add(new Goblin());
                tempList.add(new Goblin());
                tempList.add(new Wolf());
                break;
        }

        return tempList;
    }

    private Player choosePlayer() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=================================");
            System.out.println("         CHOOSE CHARACTER");
            System.out.println("=================================");
            System.out.println("1) Warrior");
            System.out.println("2) Wizard");
            System.out.print("Enter choice (1-2): ");

            int classChoice = scanner.nextInt();

            if (classChoice == 1) {
                System.out.println("\n---------------------------------");
                System.out.println("You selected: Warrior");
                System.out.println("---------------------------------");
                return new Warrior();
            } else if (classChoice == 2) {
                System.out.println("\n---------------------------------");
                System.out.println("You selected: Wizard");
                System.out.println("---------------------------------");
                return new Wizard();
            } else {
                System.out.println("\nInvalid choice. Please enter 1 or 2.");
            }
        }
    }

    private Item chooseItem(int num) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=================================");
            System.out.println("          CHOOSE ITEM " + num);
            System.out.println("=================================");
            System.out.println("1) Potion");
            System.out.println("2) Power Stone");
            System.out.println("3) Smoke Bomb");
            System.out.print("Enter choice (1-3): ");

            int itemChoice = scanner.nextInt();

            if (itemChoice == 1) {
                System.out.println("\n---------------------------------");
                System.out.println("You selected: Potion");
                System.out.println("---------------------------------");
                return new Potion();
            } else if (itemChoice == 2) {
                System.out.println("\n---------------------------------");
                System.out.println("You selected: Power Stone");
                System.out.println("---------------------------------");
                return new PowerStone();
            } else if (itemChoice == 3) {
                System.out.println("\n---------------------------------");
                System.out.println("You selected: Smoke Bomb");
                System.out.println("---------------------------------");
                return new SmokeBomb();
            } else {
                System.out.println("\nInvalid choice. Please enter 1, 2, or 3.");
            }
        }
    }

    private DIFFICULTY chooseDifficulty() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=================================");
            System.out.println("        CHOOSE DIFFICULTY");
            System.out.println("=================================");
            System.out.println("1) Easy");
            System.out.println("2) Medium");
            System.out.println("3) Hard");
            System.out.print("Enter choice (1-3): ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\n---------------------------------");
                    System.out.println("You selected: Easy");
                    System.out.println("---------------------------------");
                    return DIFFICULTY.EASY;
                case 2:
                    System.out.println("\n---------------------------------");
                    System.out.println("You selected: Medium");
                    System.out.println("---------------------------------");
                    return DIFFICULTY.MEDIUM;
                case 3:
                    System.out.println("\n---------------------------------");
                    System.out.println("You selected: Hard");
                    System.out.println("---------------------------------");
                    return DIFFICULTY.HARD;
                default:
                    System.out.println("\nInvalid choice. Please enter 1, 2, or 3.");
            }
        }
    }
}