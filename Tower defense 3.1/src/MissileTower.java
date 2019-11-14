public class MissileTower extends Tower {
    public MissileTower(int id, int cost, int range, int dam, int maxAttackTime, int maxAttackTimeDelay) {
        super(id, cost, range, dam, maxAttackTime, maxAttackTimeDelay);
    }

    @Override
    public void towerAttack(int x, int y, EnemyState enemy) {
        for(int i = 0; i < GameField.missiles.length; i++) {
            if(GameField.missiles[i] == null) {
                GameField.missiles[i] = new Missile( 55 + 55 * x, 55 * 4 + 55 * y, 2, dam, enemy );
                break;
            }
        }
    }
}
