package boundary;

public class EachRound {

    public void warrior(){
        System.out.println("Warrior: " + HP());
    }

    public String HP(){
        return "260/260 HP";
    }

    public void wizard(){
        System.out.println("Wizard: " + HP());
    }

    public void ItemsAvail(){
        System.out.println("Items: [" + "] | Skill Cooldown: ");
    }

    public String items(){
        return "Potion, Smoke Bomb";
    }

    
}
