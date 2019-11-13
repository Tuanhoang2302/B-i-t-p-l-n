public class MissileTower extends Tower {
    public MissileTower(int id, int cost, int range, int dam, int maxAttackTime, int maxAttackTimeDelay) {
        super(id, cost, range, dam, maxAttackTime, maxAttackTimeDelay);
    }

    @Override
    public void towerAttack(int x, int y, EnemyMove enemy) {
        for(int i = 0; i < GameStage.missiles.length; i++) {
            if(GameStage.missiles[i] == null) {
                GameStage.missiles[i] = new Missile( 55 + 55 * x, 55 * 4 + 55 * y, 2, dam, enemy );
                break;
            }
        }
    }
}
