import javax.swing.*;
import java.awt.*;

public class Enemy {
    public static Enemy[] enemyList = new Enemy[100];

    public static Enemy normalEnemy = new SmallerEnemy(0, 30, 70, 16, 1.7, 10, 1).getTextureFile("gun2");
    public static Enemy normalEnemy1 = new TankerEnemy(1, 50, 100, 13, 1.7, 10, 2).getTextureFile("enemy");
    public static Enemy Boss = new BossEnemy(2, 80, 150, 16, 1.7, 10, 2).getTextureFile("enemy2");

    public int dam;
    public int id;
    public int priceReward;
    public int health;
    public double speed;
    public double armor;
    public int point;
    public String textureFile = "";
    public Image texture = null;

    public Enemy(int id, int priceReward, int health, double speed, double armor, int dam, int point){
        if(enemyList[id] == null){
            enemyList[id] = this;
            this.id = id;
            this.health = health;
            this.priceReward = priceReward;
            this.speed = speed;
            this.armor = armor;
            this.dam = dam;
            this.point = point;
        }
    }

    public Enemy getTextureFile(String str){
        this.textureFile = str;
        this.texture = new ImageIcon("character/enemy/" + textureFile + ".png").getImage();
        return this;
    }
}
