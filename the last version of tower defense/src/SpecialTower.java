public class SpecialTower extends Tower {

    public SpecialTower(int id, int cost, int range, int dam, int maxAttackTime, int maxAttackTimeDelay) {
        super(id, cost, range, dam, maxAttackTime, maxAttackTimeDelay);
    }

    @Override
    public void towerAttack(int x, int y, EnemyState enemy) {
        for(int i = 0; i < GameTile.normalBullet.length; i++) {
            if(GameTile.normalBullet[i] == null) {
                GameTile.normalBullet[i] = new SpecialBullet( 55 + 55 * x, 55 * 4 + 55 * y, 2, dam, enemy );
                break;
            }
        }
    }
}