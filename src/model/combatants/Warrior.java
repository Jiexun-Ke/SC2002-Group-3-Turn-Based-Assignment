public class Warrior extends Player {
    public Warrior(){
        super("Warrior", 260, 40, 20, 30);
    }

    public void useSpecialSkill(Combatant[] target){
        new ShieldBashAction.execute(this, target);
    }
}
