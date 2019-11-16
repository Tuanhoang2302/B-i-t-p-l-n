import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;


public class GameField extends JPanel implements Runnable {
    private Thread thread;
    public Frame frame;
    boolean IsRunning;
    private int fps;
    public int scene;

    private int hand = 0;
    private int handX;
    private int handY;

    public GameTile gameTile = new GameTile(this);

    Spawner spawnPoint;
    public EnemyState[] enemyMap = new EnemyState[100];
    public int enemy = 0;
    Wave wave;

    LevelFile levelFile;
    public Level level;

    public boolean DIE = false;
    public int PAUSE = 0;
    public int THUNDERABLE = 0;
    public int UPGRADE = 0;
    public static int STOPMUSIC = 0;

    public static EnemyAuto enemyAI;
    public static Player player;
    public static double speed = 1;
    public static int BOOST = 0;

    public Clip clip1, clip2, clipstart;
    public Update update = new Update(this);
    public OptionSet optionSet = new OptionSet(this);
    public Paint paint;

    public GameStage gameST = new GameStage(this);

    public GameField(Frame frame) {
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
            //g.fillRect(0,0, frame.getWidth(), frame.getHeight());
            g.drawImage(new ImageIcon("character/terrain/scene2.png").getImage(), 0, 0, frame.getWidth(), frame.getHeight(), null);
            String a = "Press to play";
            g.setFont(new Font(a, Font.BOLD,50) );
            g.drawString(a, 800, 800);


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


    public void run() {
        //loadGame();
        gameST.loadGame();

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
                                gameST.startGame();
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
                        gameST.startGame();
                    }
                }
            }else {
                IsRunning = false;
            }
        }
        public void Space(){
            //startGame();
            gameST.startGame();

        }
    }

    public void towerAttack(int x, int y){
        if(gameTile.towerMap[x][y].target == null){
            //find target
            if(gameTile.towerMap[x][y].attackTimeDelay > gameTile.towerMap[x][y].maxAttackTimeDelay){
                EnemyState currentEnemy = gameTile.towerMap[x][y].calculateEnemy(enemyMap, x, y);

                if(currentEnemy != null){
                    gameTile.towerMap[x][y].towerAttack(x, y, currentEnemy);

                    gameTile.towerMap[x][y].target = currentEnemy;
                    gameTile.towerMap[x][y].attackTime = 0;
                    gameTile.towerMap[x][y].attackTimeDelay = 0;

                    //System.out.println("enemy attacked: health:" + currentEnemy.health + " x:" + x + " y:" + y);
                }
            }else {
                gameTile.towerMap[x][y].attackTimeDelay += 1;
            }

        }else {
            if(gameTile.towerMap[x][y].attackTime < gameTile.towerMap[x][y].maxAttackTime){
                gameTile.towerMap[x][y].attackTime += 1;
            }else {
                gameTile.towerMap[x][y].target = null;
            }
        }
    }


    public void update(){
        update.waveUpdate();
        update.enemyUpdate();
        update.towerUpdate();
        update.missileUpdate();
        update.normalBulletUpdate();
        //update.waveUpdate();

    }

    public void spawnEnemy(int enemyID){
        spawnPoint = level.findSpawnPoint();
        int i = 0;
        //for(int i = 0; i < enemyMap.length; i++){
        while (i < enemyMap.length) {
            if (enemyMap[i] == null) {
                enemyMap[i] = new EnemyState(Enemy.enemyList[enemyID], spawnPoint);
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
            if (gameTile.towerMap[xPos][yPos] == null && gameTile.map[xPos][yPos] == 0) {
                if(player.money >= Tower.towerList[hand - 1].cost) {
                    player.money -= Tower.towerList[hand - 1].cost;
                    gameTile.towerMap[xPos][yPos] = Tower.towerList[hand - 1];

                    gameTile.towerMap[xPos][yPos] = (Tower) Tower.towerList[hand - 1].clone();
                    gameTile.selectedTower = gameTile.towerMap[xPos][yPos];
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
