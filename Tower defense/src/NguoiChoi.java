import java.io.InputStream;

public class NguoiChoi {
    private GameStage gameStage;
    int health;
    int money;

    public NguoiChoi(GameStage gameStage){
        this.gameStage = gameStage;
        health = 10;
        money = 1000;
    }

}
