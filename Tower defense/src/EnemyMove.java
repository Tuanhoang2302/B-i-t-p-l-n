import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class EnemyMove {
    public Enemy enemy;
    public SpawnPoint spawnPoint;

    double xPos;
    double yPos;

    int routePosX;
    int routePosY;

    int health;
    double speed;
    boolean attack;
    public static double[] explosionX = new double[100];
    public static double[] explosionY = new double[100];
    public static int EXPLOSION = 0;

    public EnemyMove(Enemy enemy, SpawnPoint spawnPoint){
        this.enemy = enemy;
        this.routePosX = spawnPoint.x;
        this.routePosY = spawnPoint.y;
        this.xPos = spawnPoint.x * 55;
        this.yPos = spawnPoint.y * 55;
        //System.out.println(spawnPoint.x);

        this.health = enemy.health;
        this.speed = enemy.speed;
        attack = false;
    }

    public EnemyMove update(){
        EnemyMove currentEnemy = this;
        if(currentEnemy.health <= 0){
            GameStage.player.money += 10;
            for(int i = 0; i < 100; i++){
                explosionX[i] = this.xPos;
                explosionY[i] = this.yPos;
            }
            EXPLOSION++;
            File Gun = new File("character/terrain/bomb.wav");
            if(GameStage.STOPMUSIC % 2 == 1){

            }else {
                try {
                    Clip clip2 = AudioSystem.getClip();
                    clip2.open(AudioSystem.getAudioInputStream(Gun));
                    clip2.start();
                    //clip2.loop(Clip.LOOP_CONTINUOUSLY);
                    //for(int k = 0; k < 1000000; k++){}
                } catch (Exception e) {
                }
            }
            return null;
        } else if(this.routePosX == EnemyAuto.baseposX && this.routePosY == EnemyAuto.baseposY){
            GameStage.player.health -= 10;

            return null;
        }

        return currentEnemy;
    }
}
