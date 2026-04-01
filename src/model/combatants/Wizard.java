public class Wizard extends Player {
    private int bonusAttack;

    public Wizard(){
        super("Wizard", 200, 50, 10, 20);
        this.bonusAttack = 0;
    }

    public void increaseAttack(int bonus){
        this.bonusAttack += bonus;
        this.setAttack(this.getAttack() + bonus);
    }

    public void resetAttackBonus(){
        this.bonusAttack = 0;
        this.setAttack(50);
    }

    public void useSpecialSkill(Combatant[] targets){
        new ArcaneBlastAction.execute(this, targets);
    }
}
