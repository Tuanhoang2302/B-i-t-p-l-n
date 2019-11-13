import javax.swing.*;
import java.awt.*;

public class SpecialBullet extends Bullet {
    Image texture = new ImageIcon("character/tower/rocket2.png").getImage();
    public SpecialBullet(double x, double y, int speed, int dam, EnemyMove target) {
        super(x, y, speed, dam, target);
    }
}
