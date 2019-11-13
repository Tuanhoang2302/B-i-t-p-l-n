import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener {
    private GameStage gameStage;
    private GameStage.Mouse Mouseheld;
    public MouseHandler(GameStage gameStage){
        this.gameStage = gameStage;
        this.Mouseheld = this.gameStage.new Mouse();
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.Mouseheld.mousePrs(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.Mouseheld.mouseMove(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.Mouseheld.mouseMove(e);
    }
}
