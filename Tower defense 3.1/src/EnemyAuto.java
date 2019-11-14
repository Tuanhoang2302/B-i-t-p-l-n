public class EnemyAuto {
    public static Road route;
    public static EnemyAutoMove moveAI;
    public static int baseposX;
    public static int baseposY;
    public int id;

    public EnemyAuto(Level level){
        route = new Road(level);
        baseposX = route.base.xPos;
        baseposY = route.base.yPos;

        moveAI = new EnemyAutoMove(0);
    }

    public EnemyAuto(int id){
        this.id = id;
    }


}
