import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;

public class OptionSet {
    GameStage gameStage;

    public OptionSet(GameStage gameStage){
        this.gameStage = gameStage;
    }

    public void selectTower(int x, int y){
        int xPos = (x ) / 55;
        int yPos = (y + 55 / 2 ) / 55;
        yPos -= 4;

        if(xPos > 0 && xPos < 26 && yPos > 0 && yPos < 13) {
            xPos -= 1;
            yPos -= 1;

            gameStage.selectedTower = gameStage.towerMap[xPos][yPos];
            gameStage.selectedTower.posX = xPos;
            gameStage.selectedTower.posY = yPos;
        }else {
            gameStage.selectedTower = null;
        }
    }

    public void removeTower(int x, int y){
        //System.out.println(x);
        //System.out.println(y);
        //if(x >= 13 * 55 && x <= 13 * 55 + 55 * 2 +  55 / 2 && y >= 55 && y <= 55 + 55 * 2){
        //System.out.println("a");
        //}
        if(gameStage.selectedTower != null){
            System.out.println(x);
            System.out.println(y);
            if(x >= 13 * 55 && x <= 13 * 55 + 55 * 2 +  55 / 2 && y >= 55 && y <= 55 + 55 * 3){
                GameStage.player.money += 20;
                gameStage.towerMap[gameStage.selectedTower.posX][gameStage.selectedTower.posY] = null;

            }
        }
    }

    public void startButton(int x, int y){
        if(x >= 55* 8 && x <= 55 * 10 + 55 / 2){
            if(y >= 55 && y <= 55 * 3 + 55 / 2){
                gameStage.wave.nextWave();
                if(gameStage.levelFile.i <= gameStage.levelFile.MAXLEVEL) {
                    if (gameStage.wave.waveNumber == 3) {
                        if(gameStage.levelFile.i == gameStage.levelFile.MAXLEVEL) {
                            gameStage.IsRunning = false;
                        }else {
                            gameStage.levelFile.i++;
                            gameStage.startGame();
                        }
                    }
                }else {
                    gameStage.IsRunning = false;
                }

                File Gun = new File("character/terrain/beacon.wav");
                try {
                    gameStage.clipstart = AudioSystem.getClip();
                    gameStage.clipstart.open(AudioSystem.getAudioInputStream(Gun));
                    gameStage.clipstart.start();

                } catch (Exception e) {
                }
            }
        }
    }

    public void Upgrade(int x, int y){
        if(gameStage.selectedTower != null) {
            System.out.println("b");
            System.out.println(x);
            System.out.println(y);
            if(x >= 18 * 55 && x <= 18 * 55 + 55 * 2 +  55 / 2 && y >= 55 && y <= 55 + 55 * 3){
                if(gameStage.STOPMUSIC % 2 == 0) {
                    File Gun = new File("character/terrain/32_12.wav");
                    try {
                        gameStage.clip2 = AudioSystem.getClip();
                        gameStage.clip2.open(AudioSystem.getAudioInputStream(Gun));
                        gameStage.clip2.start();
                        //clip2.loop(Clip.LOOP_CONTINUOUSLY);
                    } catch (Exception e) {
                    }
                }
                gameStage.selectedTower.Upgrade = 1;
                gameStage.selectedTower.dam += 2;
                GameStage.player.money -= 50;
            }
        }
    }

    public void Music(int x, int y){
        //System.out.println(x);
        if (x >= 55 * 15 + 55 / 2 && x <= 55 * 18) {
            if (y >= 55 && y <= 55 * 3 + 55 / 2) {
                gameStage.PAUSE++;
                GameStage.STOPMUSIC++;
                if(gameStage.PAUSE % 2 == 0){
                    gameStage.clip1.start();
                    gameStage.clip2.start();
                }else {
                    gameStage.clip1.stop();
                    gameStage.clip2.stop();
                }
            }
        }
    }

    public void saveButton(int x, int y){
        if(x >= 55 * 10 + 55 / 2 && x <= 55 * 12 + 55){
            if(y >= 55 && y <= 55 * 3 + 55 / 2){
                if(GameStage.player.money >= 100) {
                    GameStage.player.money -= 100;
                    File Gun = new File("character/terrain/sam.wav");
                    try {
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(Gun));
                        clip.start();

                        //for(int k = 0; k < 1000000; k++){}
                    } catch (Exception e) {
                    }
                    gameStage.THUNDERABLE++;
                    for (int i = 0; i < gameStage.enemyMap.length; i++) {
                        if (gameStage.enemyMap[i] != null) {
                            gameStage.enemyMap[i].health -= 50;

                        }
                    }
                }else {
                    JOptionPane MOptionPane = new JOptionPane();
                    MOptionPane.showMessageDialog(gameStage.frame, "You don't have enough money", "Warning", JOptionPane.ERROR_MESSAGE);

                }
            }
        }
    }

    public void boostSpeed(int x, int y){
        if (x >= 20 * 55 + 55 / 2 && x <= 55 * 23 && y >= 55 && y <= 55 * 3) {
            System.out.println("a");
            GameStage.BOOST++;
            if(GameStage.BOOST % 2 == 0){
                GameStage.speed = 1;
            }else {
                GameStage.speed = 1.5;
            }
        }
    }
}
