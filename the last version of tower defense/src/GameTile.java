public class GameTile {
    public int[][] map = new int[26][13];
    public Tower[][] towerMap = new Tower[26][13];
    public Tower selectedTower;

    public static Missile[] missiles = new Missile[10];
    public static SpecialBullet[] normalBullet = new SpecialBullet[10];

    public Road road;
    GameField gameField;

    public GameTile(GameField gameField){
        this.gameField = gameField;
        //road = new Road(gameField.level);
    }

}
