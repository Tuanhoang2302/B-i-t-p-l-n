public class Player {
    private GameField gameStage;
    int health;
    int money;

    public Player(GameField gameStage){
        this.gameStage = gameStage;
        health = 10;
        money = 1000;
    }

}
