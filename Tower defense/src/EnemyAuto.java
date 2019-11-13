public class EnemyAuto {
    public static EnemyRoute route;
    public static EnemyAutoMove moveAI;
    public static int baseposX;
    public static int baseposY;
    public int id;

    public EnemyAuto(Level level){
        route = new EnemyRoute(level);
        baseposX = route.base.xPos;
        baseposY = route.base.yPos;

        moveAI = new EnemyAutoMove(0);
    }

    public EnemyAuto(int id){
        this.id = id;
    }


}
