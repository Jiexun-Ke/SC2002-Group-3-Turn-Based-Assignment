package model.combatants;
import model.status_effects.*;

public abstract class Combatant{
    private String name;
    private int maxHP;
    private int currentHP;
    private int attack;
    private int defense;
    private int speed;
    private StatusEffect[] activeEffects;

    public Combatant(String name, int maxHP, int attack, int defense, int speed){
        this.name = name;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.activeEffects = new StatusEffect[5];
    }

    /////////////GETTERS AND SETTERS//////////////
    public String getName(){
        return this.name;
    }
    
    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSpeed(){
        return this.speed;
    }

    public int getMaxHP() {
        return maxHP;
    }
    ////////////////////
    
    public boolean isAlive(){
        return (this.currentHP>0);
    }

    public boolean canAct(){
        for(int i = 0; i < activeEffects.length; i++){
            if(activeEffects[i] != null && activeEffects[i] instanceof StunEffect){
                return false;
            }
        }
        return true;
    }

    public void addStatusEffect(StatusEffect effect){
        for(int i = 0; i < activeEffects.length; i++){
            if(activeEffects[i] == null){
                activeEffects[i] = effect;
                effect.apply(this);
                return;
            }
        }
    }

    public void updateStatusEffects() {
    for (int i = 0; i < activeEffects.length; i++) {
        if (activeEffects[i] != null) {
            activeEffects[i].reduceDuration();
        }
    }
    removeExpiredEffects();
    }

    public void removeExpiredEffects(){
        for(int i = 0; i < activeEffects.length; i++){
            if (activeEffects[i] != null && activeEffects[i].isExpired()){
                activeEffects[i].remove(this);
                activeEffects[i] = null;
                for(int j = i; j < activeEffects.length - 1; j++){
                    activeEffects[j] = activeEffects[j+1];
                }
                activeEffects[activeEffects.length - 1] = null; //[n-1] will take on [n], but since we do activeEffects.length -1 as our range, nth index is untouched, so to fully shift left need remove last index.
                i--; //everything shift left, so need check the current index again
            }
        }
    }

    public void takeDamage(int amount){
        int finalDamage = Math.max(0, (amount - this.defense));
        this.currentHP = Math.max(0, currentHP - finalDamage);
    }
        
    public void heal(int amount){
        this.currentHP = Math.min(this.maxHP, currentHP + amount);
    }
    
}       
