import javax.swing.*;
import java.awt.*;

public class Bullet {
    public double x;
    public double y;

    public double direction;
    public int dam;

    public EnemyMove target;
    public int speed;

    //Image texture = new ImageIcon("character/terrain/missile.png").getImage();

    public Bullet(double x, double y, int speed, int dam, EnemyMove target){
        this.x = x;
        this.y = y;
        this.target = target;
        this.speed = speed;
        this.dam = dam;

        updateDirection();

    }

    public void update(){
        updateDirection();

        this.x += speed * Math.cos(direction) ;
        this.y += speed * Math.sin(direction) ;

        checkTarget();
    }

    public void checkTarget(){
        int deltaX = (int)(55 + this.target.xPos - this.x + 55 / 2 - 7);
        int deltaY = (int) (55 * 3 + this.target.yPos - this.y - 55 / 2 - 15 + 55 * 2);

        int deltaRadius = 4;

        if(deltaX * deltaX + deltaY * deltaY < deltaRadius * deltaRadius){
            this.target.health -= this.dam;
            this.target = null;
        }
    }

    public void updateDirection(){
        this.direction = Math.atan2(55 * 3 + this.target.yPos - this.y - 55 / 2 - 15 + 55 * 2, 55 + this.target.xPos - this.x + 55 / 2 - 7);
    }
}
