package model.combatants;
import model.combat.DamageResult;
import model.status_effects.StatusEffect;

public abstract class Combatant{
    private final String name;
    private final int maxHP;
    private int currentHP;
    private int attack;
    private int defense;
    private final int speed;
    private final StatusEffect[] activeEffects;

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

    public StatusEffect[] getStatusEffects() {
    return activeEffects;
    }
    ////////////////////
    
    public boolean isAlive(){
        return (this.currentHP>0);
    }

    public boolean canAct() {
        for (StatusEffect effect : activeEffects) {
            if (effect != null && effect.preventsAction()) {
                return false;
            }
        }
        return true;
    }

    public boolean addStatusEffect(StatusEffect effect){
        for (int i = 0; i < activeEffects.length; i++){
            if(activeEffects[i] == null){
                activeEffects[i] = effect;
                effect.apply(this);
                return true;
            }
        }

        return false;
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

    public DamageResult modifyIncomingDamage(Combatant attacker, int damage) {
        boolean prevented = false;
        String reason = null;

        for (int i = 0; i < activeEffects.length; i++) {
            StatusEffect effect = activeEffects[i];

            if (effect != null) {
                DamageResult result = effect.modifyIncomingDamage(attacker, this, damage);

                damage = result.getDamage();

                if (result.isPrevented()) {
                    prevented = true;
                    reason = result.getReason();
                }

                if (effect.isExpired()) {
                    effect.remove(this);
                    activeEffects[i] = null;
                }
            }
        }

        removeExpiredEffects();

        return new DamageResult(damage, prevented, reason);
    }


    public void takeRawDamage(int damage) {
        this.currentHP = Math.max(0, this.currentHP - damage);
    }
    
    public void takeDamage(int amount){
        int finalDamage = Math.max(0, (amount - this.defense));
        this.currentHP = Math.max(0, currentHP - finalDamage);
    }
        
    public void heal(int amount){
        this.currentHP = Math.min(this.maxHP, currentHP + amount);
    }
    
}       
