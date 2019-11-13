import javax.swing.*;
import java.awt.*;

public class Missile extends Bullet {
    Image texture = new ImageIcon("character/tower/rocket.png").getImage();
    public Missile(double x, double y, int speed, int dam, EnemyMove target) {
        super(x, y, speed, dam, target);
    }
}
