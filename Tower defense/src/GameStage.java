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
    private Frame frame;
    boolean IsRunning;
    private int fps;
    public int scene;

    private int hand = 0;
    private int handX;
    private int handY;

    private int[][] map = new int[26][13];
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
            //g.setColor(Color.ORANGE);
            //g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
            g.drawImage(new ImageIcon("character/terrain/grown.jpg").getImage(), 0, 0, frame.getWidth(), frame.getHeight(), null);
            g.setColor(Color.RED);
            //g.drawRect(40, 40, 40, 40);
            for (int i = 0; i <= 25; i++) {
                for (int j = 0; j <= 12; j++) {
                    //g.drawRect(55 + i * 55, 220 + j * 55, 55, 55);
                }
            }

            //grid
            g.setColor(Color.RED);
            for (int i = 0; i <= 25; i++) {
                for (int j = 0; j <= 12; j++) {
                    if (map[i][j] == 1) {
                        if (levelFile.i == 1) {
                            g.drawImage(new ImageIcon("character/terrain/road.png").getImage(), i * 55 + 55, 220 + j * 55, 55, 55, null);
                        } else {
                            g.drawImage(new ImageIcon("character/terrain/road.png").getImage(), i * 55 + 55, 220 + j * 55, 55, 55, null);
                        }
                    } else if (map[i][j] == 0) {
                        if (levelFile.i == 1) {
                            g.drawImage(new ImageIcon("character/terrain/grass.png").getImage(), i * 55 + 55, 220 + j * 55, 55, 55, null);
                        } else {
                            g.drawImage(new ImageIcon("character/terrain/grass1.png").getImage(), i * 55 + 55, 220 + j * 55, 55, 55, null);
                        }
                    } else if (map[i][j] == 3) {
                        g.drawImage(new ImageIcon("character/terrain/base1.png").getImage(), i * 55 + 55, 220 + j * 55, 55, 55, null);
                    } else if (map[i][j] == 2) {
                        g.drawImage(new ImageIcon("character/terrain/road2.png").getImage(), i * 55 + 55, 220 + j * 55, 55, 55, null);
                    }
                    //g.drawRect(55 + 55 * i, 55 * 4 + 55 * j, 55, 55);
                }
            }


            //Enemy
            for (int i = 0; i < enemyMap.length; i++) {
                if (enemyMap[i] != null) {
                    g.drawImage(enemyMap[i].enemy.texture, (int) enemyMap[i].xPos + 55, (int) enemyMap[i].yPos + 220, 55, 55, null);
                    g.setColor(Color.GRAY);
                    //g.drawRect((int) enemyMap[i].xPos + 55, (int) enemyMap[i].yPos + 200, 55, 10);
                    g.setColor(Color.RED);
                    //g.drawRect((int) enemyMap[i].xPos + 55, (int) enemyMap[i].yPos + 200, 55 * enemyMap[i].health / 100, 10);
                    g.fillRect((int) enemyMap[i].xPos + 55, (int) enemyMap[i].yPos + 200, 55 * enemyMap[i].health / 100, 10);
                }
            }

            //Health and Money
            g.setColor(Color.pink);
            g.drawRect(55, 55, 55 * 5, 55 * 2);
            g.drawRect(55, 55, 55 * 5, 55);
            String health = "Health: " + player.health;
            g.setFont(new Font(health, Font.PLAIN, 20));
            g.drawString(health, 55 + 55 / 2, 55 + 35);

            String money = "Money: " + player.money;
            g.setFont(new Font(money, Font.PLAIN, 20));
            g.drawString(money, 55 + 55 / 2, 110 + 35);

            //skills
            for (int i = 0; i < 4; i++) {
                //g.drawRect(55 * 8 + (55 * 2 + 55 / 2) * i, 55, 55 * 2 + 55 / 2, 110);
            }

            //tower status
            g.drawRect(55 * 30, 55, 55 * 2, 55 * 2);
            if (selectedTower != null) {
                g.drawImage(selectedTower.image, 55 * 30, 55, 55 * 2, 55 * 2, null);
                for (int i = 0; i <= 2; i++) {
                    g.setColor(Color.GRAY);
                    g.drawRect(55 * 24, 55 + 110 * i / 3, 55 * 4, 110 / 3);

                }
                //System.out.println(selectedTower.dam);
                g.setColor(Color.ORANGE);
                String atk = "ATTACK: " + selectedTower.dam;
                g.drawString(atk, 55 * 24 + 20, 55 + 25);
                String atkspd = "ATK SPD: " + Math.round((100 / selectedTower.maxAttackTimeDelay) * 10) / 10;
                g.drawString(atkspd, 55 * 24 + 20, 90 + 25);
                String range = "RANGE: " + selectedTower.range;
                g.drawString(range, 55 * 24 + 20, 125 + 25);
            }

            //Start button
            g.drawImage(new ImageIcon("character/terrain/playbutton.png").getImage(), 55 * 8, 55, 55 * 2 + 55 / 2, 55 * 2, null);

            //Remove button
            g.drawImage(new ImageIcon("character/terrain/remove.png").getImage(), 13 * 55, 55, 55 * 2 + 55 / 2, 55 * 2, null);
            //x >= 13 * 55 && x <= 13 * 55 + 55 * 2 +  55 / 2 && y >= 55 && y <= 55 + 55 * 2

            // tower list
            //g.drawRect(55 * 30, 55 * 4, 55 * 2, 55 * 2);
            g.drawImage(Tower.towerList[0].image, 55 * 30, 55 * 4, 70, 110, null);
            g.drawImage(Tower.towerList[1].image, 55 * 30, 55 * 7, 70, 110, null);
            g.drawImage(Tower.towerList[2].image, 55 * 30, 55 * 10, 70, 110, null);
            g.setColor(new Color(220, 0, 0, 100));
            //System.out.println(Tower.towerList[0].cost);
            if (Tower.towerList[0].cost > player.money) {
                g.fillRect(55 * 30, 55 * 4, 55 * 2, 55 * 2);
            }
            if (Tower.towerList[1].cost > player.money) {
                g.fillRect(55 * 30, 55 * 7, 55 * 2, 55 * 2);
            }
            if (Tower.towerList[2].cost > player.money) {
                g.fillRect(55 * 30, 55 * 10, 55 * 2, 55 * 2);
            }


            //missiles
            Graphics2D g2d = (Graphics2D) g;
            for (int i = 0; i < missiles.length; i++) {
                if (missiles[i] != null) {
                    g2d.rotate(missiles[i].direction + Math.toRadians(90), (int) missiles[i].x, (int) missiles[i].y);
                    g.drawImage(missiles[i].texture, (int) missiles[i].x, (int) missiles[i].y, 20, 20, null);
                    g2d.rotate(-missiles[i].direction + Math.toRadians(-90), (int) missiles[i].x, (int) missiles[i].y);
                }
            }
            //Graphics2D g2d = (Graphics2D) g;
            for (int i = 0; i < normalBullet.length; i++) {
                if (normalBullet[i] != null) {
                    g2d.rotate(normalBullet[i].direction + Math.toRadians(90), (int) normalBullet[i].x, (int) normalBullet[i].y);
                    g.drawImage(normalBullet[i].texture, (int) normalBullet[i].x, (int) normalBullet[i].y, 30, 20, null);
                    g2d.rotate(-normalBullet[i].direction + Math.toRadians(-90), (int) normalBullet[i].x, (int) normalBullet[i].y);
                }
            }
            //hand
            if (hand != 0 && Tower.towerList[hand - 1] != null) {
                g.drawImage(Tower.towerList[hand - 1].image, this.handX - 55 / 2, this.handY - 90 / 2, 55, 55, null);
            }

            //display tower and range
            for (int i = 0; i <= 25; i++) {
                for (int j = 0; j <= 12; j++) {
                    if (towerMap[i][j] != null) {
                        if (selectedTower == towerMap[i][j]) {
                            g.setColor(Color.GRAY);
                            g.drawOval(55 + 55 * i - (towerMap[i][j].range * 2 * 55 + 55) / 2 + 55 / 2, 55 * 4 + 55 * j - (towerMap[i][j].range * 2 * 55 + 55) / 2 + 55 / 2, towerMap[i][j].range * 2 * 55 + 55, towerMap[i][j].range * 2 * 55 + 55);
                            g.setColor(new Color(50, 50, 50, 50));
                            g.fillOval(55 + 55 * i - (towerMap[i][j].range * 2 * 55 + 55) / 2 + 55 / 2, 55 * 4 + 55 * j - (towerMap[i][j].range * 2 * 55 + 55) / 2 + 55 / 2, towerMap[i][j].range * 2 * 55 + 55, towerMap[i][j].range * 2 * 55 + 55);
                        }
                        g.drawImage(Tower.towerList[towerMap[i][j].id].image, 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                        if (towerMap[i][j].target != null) {
                            if (towerMap[i][j] instanceof LaserTower) {
                                g.setColor(Color.RED);
                                g.drawLine(55 + 55 * i + 55 / 2, 55 * 4 + 55 * j + 55 / 2, 55 + (int) towerMap[i][j].target.xPos + 55 / 2, 55 * 4 + (int) towerMap[i][j].target.yPos + 55 / 2);

                            }

                        }
                    }
                }
            }

            //display stage and round
            g.setColor(Color.ORANGE);
            String stage = "Stage " + levelFile.i;
            String round = "Wave " + wave.waveNumber;
            g.setFont(new Font(stage, Font.PLAIN, 30));
            g.setFont(new Font(round, Font.PLAIN, 30));
            g.drawString(stage + " " + round, 10 * 55, 55 * 3 + 40);

            //display thunder
            if(player.money >= 100) {
                g.drawImage(new ImageIcon("character/terrain/thunderbutton.jpg").getImage(), 55 * 10 + 55 / 2, 55, 55 * 2 + 55 / 2, 55 * 2, null);
            }

            if (THUNDERABLE % 2 == 0) {

            } else {
                g.drawImage(new ImageIcon("character/terrain/thunder.png").getImage(), 0, 0, frame.getWidth(), frame.getHeight(), null);
                THUNDERABLE++;
            }

            //music
            if (PAUSE % 2 == 0) {
                g.drawImage(new ImageIcon("character/terrain/batnhac.png").getImage(), 55 * 15 + 55 / 2, 55, 55 * 2 + 55 / 2, 55 * 2, null);
            } else {
                g.drawImage(new ImageIcon("character/terrain/tatnhac.jpg").getImage(), 55 * 15 + 55 / 2, 55, 55 * 2 + 55 / 2, 55 * 2, null);
            }

            //upgrade
            g.drawImage(new ImageIcon("character/terrain/upgrade.jpg").getImage(), 55 * 18, 55, 55 * 2 + 55 / 2, 55 * 2, null);

            for (int i = 0; i <= 25; i++) {
                for (int j = 0; j <= 12; j++) {
                    if (towerMap[i][j] != null){
                        if(towerMap[i][j].Upgrade == 1){
                            if(towerMap[i][j] instanceof LaserTower) {
                                if(levelFile.i == 1) {
                                    g.drawImage(new ImageIcon("character/terrain/grass.png").getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                                }else {
                                    g.drawImage(new ImageIcon("character/terrain/grass1.png").getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                                }
                                towerMap[i][j].image = new ImageIcon("character/tower/Untitled2.png").getImage();
                                g.drawImage(new ImageIcon(towerMap[i][j].image).getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                            }else if(towerMap[i][j] instanceof MissileTower){
                                //g.drawImage(new ImageIcon("character/terrain/grass.png").getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                                if(levelFile.i == 1) {
                                    g.drawImage(new ImageIcon("character/terrain/grass.png").getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                                }else {
                                    g.drawImage(new ImageIcon("character/terrain/grass1.png").getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                                }
                                towerMap[i][j].image = new ImageIcon("character/tower/Untitled3.png").getImage();
                                g.drawImage(new ImageIcon("character/tower/Untitled3.png").getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                            } else {
                                //g.drawImage(new ImageIcon("character/terrain/grass.png").getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                                if(levelFile.i == 1) {
                                    g.drawImage(new ImageIcon("character/terrain/grass.png").getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                                }else {
                                    g.drawImage(new ImageIcon("character/terrain/grass1.png").getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                                }
                                towerMap[i][j].image = new ImageIcon("character/tower/Untitled4.png").getImage();
                                g.drawImage(new ImageIcon("character/tower/Untitled4.png").getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                            }
                        }
                    }
                }
            }

            //Explosion
            for(int i = 0; i < 100; i++){
                if(EnemyMove.EXPLOSION % 2 == 0){

                }else {
                    g.drawImage(new ImageIcon("character/terrain/explosion.jpg").getImage(), (int) EnemyMove.explosionX[i] + 55, (int) EnemyMove.explosionY[i] + 220, 55, 55, null);
                    EnemyMove.EXPLOSION++;
                }
            }

            //SpeedButton
            if(BOOST % 2 == 0){
                g.drawImage(new ImageIcon("character/tower/speed.jpg").getImage(), 55 * 20 + 55 / 2, 55, 55 * 2 + 55 / 2, 55 * 2, null);
            }else{
                g.drawImage(new ImageIcon("character/tower/boost.png").getImage(), 55 * 20 + 55 / 2, 55, 55 * 2 + 55 / 2, 55 * 2, null);
            }
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

                //JOptionPane.showMessageDialog(null, "hit it");
                //long cliptime = clip.getMicrosecondLength();
                //clip.stop();

                //JOptionPane.showMessageDialog(null, "hit");
                //clip.setMicrosecondPosition(cliptime);
                //clip.start();
                //for(int k = 0; k < 1000000; k++){}
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

    public void enemyUpdate(){
        for(int i = 0; i < enemyMap.length; i++){
            if(enemyMap[i] != null){
                //System.out.println(i);
                if(!enemyMap[i].attack){
                    EnemyAuto.moveAI.move(enemyMap[i]);
                }

                enemyMap[i] = enemyMap[i].update();

                if(GameStage.player.health <= 0){
                    DIE = true;
                    //IsRunning = false;
                    //IsRunning = false;
                }
            }
        }
    }

    public void towerUpdate()  {
        for(int i = 0; i < 26; i++){
            for(int j = 0; j < 13; j++){
                if(towerMap[i][j] != null){
                    towerAttack(i, j);

                }
            }
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

    public void missileUpdate(){
        for(int i = 0; i < missiles.length; i++){
            if(missiles[i] != null){
                missiles[i].update();

                if(missiles[i].target == null){
                    missiles[i] = null;
                }
            }
        }
    }

    public void normalBulletUpdate(){
        for(int i = 0; i < normalBullet.length; i++){
            if(normalBullet[i] != null){
                normalBullet[i].update();

                if(normalBullet[i].target == null){
                    normalBullet[i] = null;
                }
            }
        }
    }

    public void waveUpdate(){
        if (wave.spawning) {
            wave.spawnEnemy();
        }
    }

    public void update(){
        update.enemyUpdate();
        //enemyUpdate();
        towerUpdate();
        missileUpdate();
        normalBulletUpdate();
        waveUpdate();
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

    public void selectTower(int x, int y){
        int xPos = (x ) / 55;
        int yPos = (y + 55 / 2 ) / 55;
        yPos -= 4;

        if(xPos > 0 && xPos < 26 && yPos > 0 && yPos < 13) {
            xPos -= 1;
            yPos -= 1;

            selectedTower = towerMap[xPos][yPos];
            selectedTower.posX = xPos;
            selectedTower.posY = yPos;
        }else {
            selectedTower = null;
        }
    }

    public void removeTower(int x, int y){
        //System.out.println(x);
        //System.out.println(y);
        //if(x >= 13 * 55 && x <= 13 * 55 + 55 * 2 +  55 / 2 && y >= 55 && y <= 55 + 55 * 2){
            //System.out.println("a");
        //}
        if(selectedTower != null){
            System.out.println(x);
            System.out.println(y);
            if(x >= 13 * 55 && x <= 13 * 55 + 55 * 2 +  55 / 2 && y >= 55 && y <= 55 + 55 * 3){
                player.money += 20;
                towerMap[selectedTower.posX][selectedTower.posY] = null;

            }
        }
    }

    public void startButton(int x, int y){
        if(x >= 55* 8 && x <= 55 * 10 + 55 / 2){
            if(y >= 55 && y <= 55 * 3 + 55 / 2){
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

                    File Gun = new File("character/terrain/beacon.wav");
                    try {
                        clipstart = AudioSystem.getClip();
                        clipstart.open(AudioSystem.getAudioInputStream(Gun));
                        clipstart.start();

                    } catch (Exception e) {
                    }
            }
        }
    }

    public void saveButton(int x, int y){
        if(x >= 55 * 10 + 55 / 2 && x <= 55 * 12 + 55){
            if(y >= 55 && y <= 55 * 3 + 55 / 2){
                if(player.money >= 100) {
                    player.money -= 100;
                    File Gun = new File("character/terrain/sam.wav");
                    try {
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(Gun));
                        clip.start();

                        //for(int k = 0; k < 1000000; k++){}
                    } catch (Exception e) {
                    }
                    THUNDERABLE++;
                    for (int i = 0; i < enemyMap.length; i++) {
                        if (enemyMap[i] != null) {
                            enemyMap[i].health -= 50;

                        }
                    }
                }else {
                    JOptionPane MOptionPane = new JOptionPane();
                    MOptionPane.showMessageDialog(frame, "You don't have enough money", "Warning", JOptionPane.ERROR_MESSAGE);

                }
            }
        }
    }

    public void Music(int x, int y){
        //System.out.println(x);
        if (x >= 55 * 15 + 55 / 2 && x <= 55 * 18) {
            if (y >= 55 && y <= 55 * 3 + 55 / 2) {
                PAUSE++;
                STOPMUSIC++;
                if(PAUSE % 2 == 0){
                    clip1.start();
                    clip2.start();
                }else {
                    clip1.stop();
                    clip2.stop();
                }
            }
        }
    }

    public void Upgrade(int x, int y){
        if(selectedTower != null) {
            System.out.println("b");
            System.out.println(x);
            System.out.println(y);
            if(x >= 18 * 55 && x <= 18 * 55 + 55 * 2 +  55 / 2 && y >= 55 && y <= 55 + 55 * 3){
                if(STOPMUSIC % 2 == 0) {
                    File Gun = new File("character/terrain/32_12.wav");
                    try {
                        clip2 = AudioSystem.getClip();
                        clip2.open(AudioSystem.getAudioInputStream(Gun));
                        clip2.start();
                        //clip2.loop(Clip.LOOP_CONTINUOUSLY);
                    } catch (Exception e) {
                    }
                }
                selectedTower.Upgrade = 1;
                selectedTower.dam += 2;
                player.money -= 50;
            }
        }
    }

    public void boostSpeed(int x, int y){
        if (x >= 20 * 55 + 55 / 2 && x <= 55 * 23 && y >= 55 && y <= 55 * 3) {
            System.out.println("a");
            BOOST++;
            if(BOOST % 2 == 0){
                speed = 1;
            }else {
                speed = 1.5;
            }
        }
    }


    public class Mouse{
        boolean mouseprs = false;
        public void mousePrs(MouseEvent e){
            mouseprs = true;

            if(hand != 0){
                //System.out.println(e.getX());
                //System.out.println(e.getY());
                placeTower(e.getXOnScreen(), e.getYOnScreen());
                hand = 0;
            }
            else {
                removeTower(e.getX(), e.getY());
                Upgrade(e.getX(), e.getY());
                selectTower(e.getXOnScreen(), e.getYOnScreen());
                startButton(e.getX(), e.getY());
                saveButton(e.getX(), e.getY());
                Music(e.getX(), e.getY());
                boostSpeed(e.getX(), e.getY());
                //Upgrade(e.getX(), e.getY());
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
