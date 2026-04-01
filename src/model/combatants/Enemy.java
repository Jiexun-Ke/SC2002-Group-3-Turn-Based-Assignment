public abstract class Enemy extends Combatant{
    private EnemyActionStrategy actionStrategy;

    public Enemy(String name, int maxHP, int attack, int defense, int speed, EnemyActionStrategy actionStrategy){
        super(name, maxHP, attack, defense, speed);
        this.actionStrategy = actionStrategy;
    }
    
    public Action chooseAction(){
        return actionStrategy.chooseAction(this);
    }
}
