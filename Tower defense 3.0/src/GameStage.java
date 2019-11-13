import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;


public class GameStage extends JPanel implements Runnable {
    private Thread thread;
    public Frame frame;
    boolean IsRunning;
    private int fps;
    public int scene;

    private int hand = 0;
    private int handX;
    private int handY;

    public int[][] map = new int[26][13];
    public Tower[][] towerMap = new Tower[26][13];
    private Image[] terrain = new Image[100];

    public Tower selectedTower;

    SpawnPoint spawnPoint;

    public EnemyMove[] enemyMap = new EnemyMove[100];
    public static Missile[] missiles = new Missile[10];
    public static SpecialBullet[] normalBullet = new SpecialBullet[10];
    public int enemy = 0;
    Wave wave;

    LevelFile levelFile;
    Level level;

    public boolean DIE = false;
    public int PAUSE = 0;
    public int THUNDERABLE = 0;
    public int UPGRADE = 0;
    public static int STOPMUSIC = 0;

    public static EnemyAuto enemyAI;
    public static NguoiChoi player;
    public static double speed = 1;
    public static int BOOST = 0;

    public Clip clip1, clip2, clipstart;
    public Update update = new Update(this);
    public OptionSet optionSet = new OptionSet(this);
    public Paint paint;

    public GameStage(Frame frame) {
        this.frame = frame;

        //this.frame.addKeyListener(new KeyHandler(this));
        fps = 0;
        scene = 0;
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            IsRunning = true;
            thread.start();
        }
    }

    public void paintComponent(Graphics g) {
        g.clearRect(0, 0, frame.getWidth(), frame.getHeight());
        if(scene == 0){
            g.setColor(Color.GREEN);
            g.fillRect(0,0, frame.getWidth(), frame.getHeight());

        } else if (scene == 1) {
            //background
            paint = new Paint(g, this);
            paint.paintBackGround();
            //grid
            paint.paintGrid();
            //enemy
            paint.paintEnemy();
            //Health and Money
            paint.paintHealthAndMoney();
            //tower status
            paint.paintTowerStatus();
            //Start button
            g.drawImage(new ImageIcon("character/terrain/playbutton.png").getImage(), 55 * 8, 55, 55 * 2 + 55 / 2, 55 * 2, null);
            //Remove button
            g.drawImage(new ImageIcon("character/terrain/remove.png").getImage(), 13 * 55, 55, 55 * 2 + 55 / 2, 55 * 2, null);
            // tower list
            paint.paintTowerList();
            //missiles
            paint.paintMissiles();
            //normal bullet
            paint.paintNormalBullet();
            //hand
            if (hand != 0 && Tower.towerList[hand - 1] != null) {
                g.drawImage(Tower.towerList[hand - 1].image, this.handX - 55 / 2, this.handY - 90 / 2, 55, 55, null);
            }

            //display tower and range
            paint.paintTowerAndRange();

            //display stage and round
            paint.paintStageAndRound();

            //display thunder
            paint.paintThunder();

            //music
            if (PAUSE % 2 == 0) {
                g.drawImage(new ImageIcon("character/terrain/batnhac.png").getImage(), 55 * 15 + 55 / 2, 55, 55 * 2 + 55 / 2, 55 * 2, null);
            } else {
                g.drawImage(new ImageIcon("character/terrain/tatnhac.jpg").getImage(), 55 * 15 + 55 / 2, 55, 55 * 2 + 55 / 2, 55 * 2, null);
            }

            //upgrade
            paint.paintUpgrade();

            //Explosion
            paint.Explosion();

            //SpeedButton
            paint.paintSpeedButton();
        }
    }



    public void loadGame(){
        IsRunning = true;
        levelFile = new LevelFile();
        wave = new Wave(this);

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++) {
                terrain[j + (10 * i)] = new ImageIcon("character/" + "terrain.png").getImage();
                terrain[j + 10 * i] = createImage(new FilteredImageSource(terrain[j + 10 * i].getSource(), new CropImageFilter(j * 25, i * 25 , 25, 25)));
            }
        }

    }


    public void startGame(){
        scene = 1;
        this.speed = 1;
        this.level = levelFile.getLevel();
        //this.level.findSpawnPoint();
        this.map = this.level.map;

        player = new NguoiChoi(this);
        this.wave.waveNumber = 0;
        this.enemyAI = new EnemyAuto(this.level);
        if(levelFile.i == 1) {
            File Gun = new File("character/terrain/0267.wav");
            try {
                clip1 = AudioSystem.getClip();
                clip1.open(AudioSystem.getAudioInputStream(Gun));
                clip1.start();
                clip1.loop(Clip.LOOP_CONTINUOUSLY);

            } catch (Exception e) { }

        }else {
            File Gun = new File("character/terrain/0999.wav");
            try {
                clip2 = AudioSystem.getClip();
                clip2.open(AudioSystem.getAudioInputStream(Gun));
                clip2.start();
                clip2.loop(Clip.LOOP_CONTINUOUSLY);
                //for(int k = 0; k < 1000000; k++){}
            } catch (Exception e) { }
        }
    }

    public void run() {
        loadGame();

        fps = 60;
        long period = 1000 * 1000000 / fps;
        long beginTime;
        long sleepTime;
        beginTime = System.nanoTime();

        while (IsRunning) {
            repaint();
            long deltaTime = System.nanoTime() - beginTime;
            sleepTime = period - deltaTime;

            update();
            try {
                if (sleepTime > 0)
                    Thread.sleep(sleepTime / 1000000);
                else {
                    Thread.sleep(period / 2000000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            beginTime = System.nanoTime();

            if(DIE) {
                //IsRunning = false;
                try {
                    JOptionPane jOptionPane = new JOptionPane();
                    jOptionPane.showMessageDialog(frame, "YOU LOSE", "ENDGAME", JOptionPane.ERROR_MESSAGE);
                    Thread.sleep(1000);
                    int n = JOptionPane.showConfirmDialog(
                            frame,
                            "Are you sure you want to quit?",
                            "Alert",
                            JOptionPane.YES_NO_OPTION);
                    if(n == JOptionPane.YES_OPTION)
                        System.exit(0);
                    else {
                        DIE = false;
                        int m = JOptionPane.showConfirmDialog(
                                frame,
                                "Do you want to continue this round with full health but your current gold will decrease by 500?",
                                "Alert",
                                JOptionPane.YES_NO_OPTION);
                        if(m == JOptionPane.YES_OPTION) {
                            if(player.money >= 500) {
                                int currentMoney = player.money - 500;
                                startGame();
                                player.health = 10;
                                player.money = currentMoney;
                            }else{
                                JOptionPane OptionPane = new JOptionPane();
                                OptionPane.showMessageDialog(frame, "You don't have enough gold", "Warning", JOptionPane.ERROR_MESSAGE);
                                IsRunning = false;
                            }
                        } else {
                            IsRunning = false;
                        }
                    }

                }catch (InterruptedException e){}

            }

        }

        JOptionPane MOptionPane = new JOptionPane();
        if(levelFile.i < 2 || levelFile.i == 2 && wave.waveNumber < 2) {
            MOptionPane.showMessageDialog(frame, "YOU LOSE", "ENDGAME", JOptionPane.ERROR_MESSAGE);
        } else {
            MOptionPane.showMessageDialog(frame, "YOU WIN", "ENDGAME", JOptionPane.CLOSED_OPTION);
        }


        //System.exit(0);
    }

    public class keyInput{
        public void ESC(){
            IsRunning = false;
        }
        public void Enter(){
            wave.nextWave();
            if(levelFile.i <= levelFile.MAXLEVEL) {
                if (wave.waveNumber == 3) {
                    if(levelFile.i == levelFile.MAXLEVEL) {
                        IsRunning = false;
                    }else {
                        levelFile.i++;
                        startGame();
                    }
                }
            }else {
                IsRunning = false;
            }
        }
        public void Space(){
            startGame();
        }
    }

    public void towerAttack(int x, int y){
        if(this.towerMap[x][y].target == null){
            //find target
            if(this.towerMap[x][y].attackTimeDelay > this.towerMap[x][y].maxAttackTimeDelay){
                EnemyMove currentEnemy = this.towerMap[x][y].calculateEnemy(enemyMap, x, y);

                if(currentEnemy != null){
                    this.towerMap[x][y].towerAttack(x, y, currentEnemy);

                    this.towerMap[x][y].target = currentEnemy;
                    this.towerMap[x][y].attackTime = 0;
                    this.towerMap[x][y].attackTimeDelay = 0;

                    //System.out.println("enemy attacked: health:" + currentEnemy.health + " x:" + x + " y:" + y);
                }
            }else {
                this.towerMap[x][y].attackTimeDelay += 1;
            }

        }else {
            if(this.towerMap[x][y].attackTime < this.towerMap[x][y].maxAttackTime){
                this.towerMap[x][y].attackTime += 1;
            }else {
                this.towerMap[x][y].target = null;
            }
        }
    }


    public void update(){
        update.enemyUpdate();
        update.towerUpdate();
        update.missileUpdate();
        update.normalBulletUpdate();
        update.waveUpdate();

    }

    public void spawnEnemy(int enemyID){
        spawnPoint = level.findSpawnPoint();
        int i = 0;
        //for(int i = 0; i < enemyMap.length; i++){
        while (i < enemyMap.length) {
            if (enemyMap[i] == null) {
                enemyMap[i] = new EnemyMove(Enemy.enemyList[enemyID], spawnPoint);
                break;
            }
            i++;
        }
        //}
    }

    public void placeTower(int x, int y){
        int xPos = (x ) / 55;
        int yPos = (y + 55 / 2) / 55;
        yPos -= 4;
        //System.out.println(x);
        //System.out.println(y);
        if(xPos < 26 && yPos < 13) {

            xPos -= 1;
            yPos -= 1;
            if (towerMap[xPos][yPos] == null && map[xPos][yPos] == 0) {
                if(player.money >= Tower.towerList[hand - 1].cost) {
                    player.money -= Tower.towerList[hand - 1].cost;
                    towerMap[xPos][yPos] = Tower.towerList[hand - 1];

                    towerMap[xPos][yPos] = (Tower) Tower.towerList[hand - 1].clone();
                    selectedTower = towerMap[xPos][yPos];
                }
            }
        }
    }


    public class Mouse{
        boolean mouseprs = false;
        public void mousePrs(MouseEvent e){
            mouseprs = true;

            if(hand != 0){
                placeTower(e.getXOnScreen(), e.getYOnScreen());
                hand = 0;
            }
            else {
                optionSet.removeTower(e.getX(), e.getY());
                optionSet.Upgrade(e.getX(), e.getY());
                optionSet.selectTower(e.getX(), e.getY());
                optionSet.startButton(e.getX(), e.getY());
                optionSet.saveButton(e.getX(), e.getY());
                optionSet.Music(e.getX(), e.getY());
                optionSet.boostSpeed(e.getX(), e.getY());
            }
            updateMouse(e);
        }

        public void mouseMove(MouseEvent e) {
            handX = e.getXOnScreen();
            handY = e.getYOnScreen();
        }

        public void updateMouse(MouseEvent e){
            if(scene == 1){
                if(hand == 0 && mouseprs) {
                    if (e.getXOnScreen() >= 55 * 30 && e.getXOnScreen() <= 55 * 32) {
                        if (e.getYOnScreen() >= 55 * 4 && e.getYOnScreen() <= 55 * 6 + 55 / 2) {
                            if (player.money > Tower.towerList[0].cost) {
                                hand = 1;
                            }
                        }
                    }

                    if (e.getXOnScreen() >= 55 * 30 && e.getXOnScreen() <= 55 * 32) {
                        if (e.getYOnScreen() >= 55 * 7 && e.getYOnScreen() <= 55 * 9 + 55 / 2) {
                            if (player.money > Tower.towerList[0].cost) {
                                hand = 2;
                            }
                        }
                    }

                    if (e.getXOnScreen() >= 55 * 30 && e.getXOnScreen() <= 55 * 32) {
                        if (e.getYOnScreen() >= 55 * 10 && e.getYOnScreen() <= 55 * 12+ 55 /2) {
                            if (player.money > Tower.towerList[0].cost) {
                                hand = 3;
                            }
                        }
                    }
                }
            }
        }
    }
}
