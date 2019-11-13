public class LaserTower extends Tower {

    public LaserTower(int id, int cost, int range, int dam, int maxAttackTime, int maxAttackTimeDelay){
        super(id, cost, range, dam, maxAttackTime, maxAttackTimeDelay);
    }

    @Override
    public void towerAttack(int x, int y, EnemyMove enemy) {
        enemy.health -= this.dam;
    }
}
