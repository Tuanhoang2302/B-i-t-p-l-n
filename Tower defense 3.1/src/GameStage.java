import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class GameStage {
    GameField gameStage;

    public GameStage(GameField gameStage){
        this.gameStage = gameStage;
    }

    public void loadGame(){
        gameStage.IsRunning = true;
        gameStage.levelFile = new LevelFile();
        gameStage.wave = new Wave(gameStage);

    }

    public void startGame(){
        gameStage.scene = 1;
        gameStage.speed = 1;
        gameStage.level = gameStage.levelFile.getLevel();
        //this.level.findSpawnPoint();
        gameStage.map = gameStage.level.map;

        GameField.player = new Player(gameStage);
        gameStage.wave.waveNumber = 0;
        GameField.enemyAI = new EnemyAuto(gameStage.level);
        if(gameStage.levelFile.i == 1) {
            File Gun = new File("character/terrain/0267.wav");
            try {
                gameStage.clip1 = AudioSystem.getClip();
                gameStage.clip1.open(AudioSystem.getAudioInputStream(Gun));
                gameStage.clip1.start();
                gameStage.clip1.loop(Clip.LOOP_CONTINUOUSLY);

            } catch (Exception e) { }

        }else {
            File Gun = new File("character/terrain/0999.wav");
            try {
                gameStage.clip2 = AudioSystem.getClip();
                gameStage.clip2.open(AudioSystem.getAudioInputStream(Gun));
                gameStage.clip2.start();
                gameStage.clip2.loop(Clip.LOOP_CONTINUOUSLY);
                //for(int k = 0; k < 1000000; k++){}
            } catch (Exception e) { }
        }
    }
}
