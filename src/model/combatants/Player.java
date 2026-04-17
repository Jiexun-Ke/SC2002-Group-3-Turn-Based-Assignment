package model.combatants;
import model.actions.*;
import model.items.Item;

public abstract class Player extends Combatant{
    private Item[] inventory;
    private int specialSkillCooldown;

    public Player(String name, int maxHP, int attack, int defense, int speed){
        super(name, maxHP, attack, defense, speed);
        this.inventory = new Item[2];
        this.specialSkillCooldown = 0;
    }

    /// GETTERS AND SETTERS ///
    
    public abstract Action createSpecialSkillAction();
    
    public abstract String getSpecialSkillName();
    
    public int getSpecialSkillCooldown(){
        return this.specialSkillCooldown;
    }

    public void setSpecialSkillCooldown(int turns){
        this.specialSkillCooldown = turns;
    }

    public void updateSpecialSkillCooldown(){
        this.specialSkillCooldown = Math.max(0, this.specialSkillCooldown - 1);
    }

    public Item[] getInventory(){
        return inventory;
    }

    public void addItem(Item item){
        for(int i = 0; i < inventory.length; i++){
            if(inventory[i] == null){
                inventory[i] = item;
                return;
            }
        }
    }

    
    /////////////////////

    public Action chooseAction(Action choice){
        return choice;
    }

    public void removeItem(Item item){
        for(int i = 0; i < inventory.length; i++){
            if(inventory[i] != null && inventory[i] == item){
                inventory[i] = null;
                for(int j = i; j < inventory.length - 1; j++){
                    inventory[j] = inventory[j+1];
                }
                inventory[inventory.length-1] = null;
                return;
            }
        }
    }

    // New methods to be used in control
    public abstract boolean useSpecialSkill(Combatant[] targets);

    public abstract boolean specialSkillTargetsAllEnemies();
}

