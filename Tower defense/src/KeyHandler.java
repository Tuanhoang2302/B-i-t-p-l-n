import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private GameStage gameStage;
    private GameStage.keyInput keyInput;

    public KeyHandler(GameStage gameStage){
        this.gameStage = gameStage;
        this.keyInput = this.gameStage.new keyInput();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        //System.out.println(keyCode);
        if(e.getKeyCode() == 27){
            this.keyInput.ESC();
        }
        if(e.getKeyCode() == 32){
            this.keyInput.Space();
        }
        if(e.getKeyCode() == 10){
            this.keyInput.Enter();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
