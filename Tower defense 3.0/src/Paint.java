import javax.swing.*;
import java.awt.*;

public class Paint {
    Graphics g;
    GameStage gameStage;

    public Paint(Graphics g, GameStage gameStage){
        this.g = g;
        this.gameStage = gameStage;
    }

    public void paintBackGround() {
        g.drawImage(new ImageIcon("character/terrain/grown.jpg").getImage(), 0, 0, gameStage.frame.getWidth(), gameStage.frame.getHeight(), null);
        g.setColor(Color.RED);
    }

    public void paintGrid(){
        //grid
        g.setColor(Color.RED);
        for (int i = 0; i <= 25; i++) {
            for (int j = 0; j <= 12; j++) {
                if (gameStage.map[i][j] == 1) {
                    if (gameStage.levelFile.i == 1) {
                        g.drawImage(new ImageIcon("character/terrain/road.png").getImage(), i * 55 + 55, 220 + j * 55, 55, 55, null);
                    } else {
                        g.drawImage(new ImageIcon("character/terrain/road.png").getImage(), i * 55 + 55, 220 + j * 55, 55, 55, null);
                    }
                } else if (gameStage.map[i][j] == 0) {
                    if (gameStage.levelFile.i == 1) {
                        g.drawImage(new ImageIcon("character/terrain/grass.png").getImage(), i * 55 + 55, 220 + j * 55, 55, 55, null);
                    } else {
                        g.drawImage(new ImageIcon("character/terrain/grass1.png").getImage(), i * 55 + 55, 220 + j * 55, 55, 55, null);
                    }
                } else if (gameStage.map[i][j] == 3) {
                    g.drawImage(new ImageIcon("character/terrain/base1.png").getImage(), i * 55 + 55, 220 + j * 55, 55, 55, null);
                } else if (gameStage.map[i][j] == 2) {
                    g.drawImage(new ImageIcon("character/terrain/road2.png").getImage(), i * 55 + 55, 220 + j * 55, 55, 55, null);
                }
                //g.drawRect(55 + 55 * i, 55 * 4 + 55 * j, 55, 55);
            }
        }
    }

    public void paintEnemy(){
        //Enemy
        for (int i = 0; i < gameStage.enemyMap.length; i++) {
            if (gameStage.enemyMap[i] != null) {
                g.drawImage(gameStage.enemyMap[i].enemy.texture, (int) gameStage.enemyMap[i].xPos + 55, (int) gameStage.enemyMap[i].yPos + 220, 55, 55, null);
                g.setColor(Color.GRAY);
                //g.drawRect((int) enemyMap[i].xPos + 55, (int) enemyMap[i].yPos + 200, 55, 10);
                g.setColor(Color.RED);
                //g.drawRect((int) enemyMap[i].xPos + 55, (int) enemyMap[i].yPos + 200, 55 * enemyMap[i].health / 100, 10);
                g.fillRect((int) gameStage.enemyMap[i].xPos + 55, (int) gameStage.enemyMap[i].yPos + 200, 55 * gameStage.enemyMap[i].health / 100, 10);
            }
        }
    }

    public void paintHealthAndMoney(){
        g.setColor(Color.pink);
        g.drawRect(55, 55, 55 * 5, 55 * 2);
        g.drawRect(55, 55, 55 * 5, 55);
        String health = "Health: " + GameStage.player.health;
        g.setFont(new Font(health, Font.PLAIN, 20));
        g.drawString(health, 55 + 55 / 2, 55 + 35);

        String money = "Money: " + GameStage.player.money;
        g.setFont(new Font(money, Font.PLAIN, 20));
        g.drawString(money, 55 + 55 / 2, 110 + 35);
    }

    public void paintTowerStatus(){
        g.drawRect(55 * 30, 55, 55 * 2, 55 * 2);
        if (gameStage.selectedTower != null) {
            g.drawImage(gameStage.selectedTower.image, 55 * 30, 55, 55 * 2, 55 * 2, null);
            for (int i = 0; i <= 2; i++) {
                g.setColor(Color.GRAY);
                g.drawRect(55 * 24, 55 + 110 * i / 3, 55 * 4, 110 / 3);

            }
            //System.out.println(selectedTower.dam);
            g.setColor(Color.ORANGE);
            String atk = "ATTACK: " + gameStage.selectedTower.dam;
            g.drawString(atk, 55 * 24 + 20, 55 + 25);
            String atkspd = "ATK SPD: " + Math.round((100 / gameStage.selectedTower.maxAttackTimeDelay) * 10) / 10;
            g.drawString(atkspd, 55 * 24 + 20, 90 + 25);
            String range = "RANGE: " + gameStage.selectedTower.range;
            g.drawString(range, 55 * 24 + 20, 125 + 25);
        }
    }

    public void paintTowerList(){
        //g.drawRect(55 * 30, 55 * 4, 55 * 2, 55 * 2);
        g.drawImage(Tower.towerList[0].image, 55 * 30, 55 * 4, 70, 110, null);
        g.drawImage(Tower.towerList[1].image, 55 * 30, 55 * 7, 70, 110, null);
        g.drawImage(Tower.towerList[2].image, 55 * 30, 55 * 10, 70, 110, null);
        g.setColor(new Color(220, 0, 0, 100));
        if (Tower.towerList[0].cost > GameStage.player.money) {
            g.fillRect(55 * 30, 55 * 4, 55 * 2, 55 * 2);
        }
        if (Tower.towerList[1].cost > GameStage.player.money) {
            g.fillRect(55 * 30, 55 * 7, 55 * 2, 55 * 2);
        }
        if (Tower.towerList[2].cost > GameStage.player.money) {
            g.fillRect(55 * 30, 55 * 10, 55 * 2, 55 * 2);
        }
    }

    public void paintMissiles(){
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < GameStage.missiles.length; i++) {
            if (GameStage.missiles[i] != null) {
                g2d.rotate(GameStage.missiles[i].direction + Math.toRadians(90), (int) GameStage.missiles[i].x, (int) GameStage.missiles[i].y);
                g.drawImage(GameStage.missiles[i].texture, (int) GameStage.missiles[i].x, (int) GameStage.missiles[i].y, 20, 20, null);
                g2d.rotate(-GameStage.missiles[i].direction + Math.toRadians(-90), (int) GameStage.missiles[i].x, (int) GameStage.missiles[i].y);
            }
        }
    }

    public void paintNormalBullet() {
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < GameStage.normalBullet.length; i++) {
            if (GameStage.normalBullet[i] != null) {
                g2d.rotate(GameStage.normalBullet[i].direction + Math.toRadians(90), (int) GameStage.normalBullet[i].x, (int) GameStage.normalBullet[i].y);
                g.drawImage(GameStage.normalBullet[i].texture, (int) GameStage.normalBullet[i].x, (int) GameStage.normalBullet[i].y, 30, 20, null);
                g2d.rotate(-GameStage.normalBullet[i].direction + Math.toRadians(-90), (int) GameStage.normalBullet[i].x, (int) GameStage.normalBullet[i].y);
            }
        }
    }

    public void paintTowerAndRange(){
        for (int i = 0; i <= 25; i++) {
            for (int j = 0; j <= 12; j++) {
                if (gameStage.towerMap[i][j] != null) {
                    if (gameStage.selectedTower == gameStage.towerMap[i][j]) {
                        g.setColor(Color.GRAY);
                        g.drawOval(55 + 55 * i - (gameStage.towerMap[i][j].range * 2 * 55 + 55) / 2 + 55 / 2, 55 * 4 + 55 * j - (gameStage.towerMap[i][j].range * 2 * 55 + 55) / 2 + 55 / 2, gameStage.towerMap[i][j].range * 2 * 55 + 55, gameStage.towerMap[i][j].range * 2 * 55 + 55);
                        g.setColor(new Color(50, 50, 50, 50));
                        g.fillOval(55 + 55 * i - (gameStage.towerMap[i][j].range * 2 * 55 + 55) / 2 + 55 / 2, 55 * 4 + 55 * j - (gameStage.towerMap[i][j].range * 2 * 55 + 55) / 2 + 55 / 2, gameStage.towerMap[i][j].range * 2 * 55 + 55, gameStage.towerMap[i][j].range * 2 * 55 + 55);
                    }
                    g.drawImage(Tower.towerList[gameStage.towerMap[i][j].id].image, 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                    if (gameStage.towerMap[i][j].target != null) {
                        if (gameStage.towerMap[i][j] instanceof LaserTower) {
                            g.setColor(Color.RED);
                            g.drawLine(55 + 55 * i + 55 / 2, 55 * 4 + 55 * j + 55 / 2, 55 + (int) gameStage.towerMap[i][j].target.xPos + 55 / 2, 55 * 4 + (int) gameStage.towerMap[i][j].target.yPos + 55 / 2);
                        }
                    }
                }
            }
        }
    }

    public void paintStageAndRound(){
        g.setColor(Color.ORANGE);
        String stage = "Stage " + gameStage.levelFile.i;
        String round = "Wave " + gameStage.wave.waveNumber;
        g.setFont(new Font(stage, Font.PLAIN, 30));
        g.setFont(new Font(round, Font.PLAIN, 30));
        g.drawString(stage + " " + round, 10 * 55, 55 * 3 + 40);
    }

    public void paintThunder(){
        if(GameStage.player.money >= 100) {
            g.drawImage(new ImageIcon("character/terrain/thunderbutton.jpg").getImage(), 55 * 10 + 55 / 2, 55, 55 * 2 + 55 / 2, 55 * 2, null);
        }
        if (gameStage.THUNDERABLE % 2 == 0) {
        } else {
            g.drawImage(new ImageIcon("character/terrain/thunder.png").getImage(), 0, 0, gameStage.frame.getWidth(), gameStage.frame.getHeight(), null);
            gameStage.THUNDERABLE++;
        }
    }

    public void paintUpgrade(){
        g.drawImage(new ImageIcon("character/terrain/upgrade.jpg").getImage(), 55 * 18, 55, 55 * 2 + 55 / 2, 55 * 2, null);

        for (int i = 0; i <= 25; i++) {
            for (int j = 0; j <= 12; j++) {
                if (gameStage.towerMap[i][j] != null){
                    if(gameStage.towerMap[i][j].Upgrade == 1){
                        if(gameStage.towerMap[i][j] instanceof LaserTower) {
                            if(gameStage.levelFile.i == 1) {
                                g.drawImage(new ImageIcon("character/terrain/grass.png").getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                            }else {
                                g.drawImage(new ImageIcon("character/terrain/grass1.png").getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                            }
                            gameStage.towerMap[i][j].image = new ImageIcon("character/tower/Untitled2.png").getImage();
                            g.drawImage(new ImageIcon(gameStage.towerMap[i][j].image).getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                        }else if(gameStage.towerMap[i][j] instanceof MissileTower){
                            //g.drawImage(new ImageIcon("character/terrain/grass.png").getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                            if(gameStage.levelFile.i == 1) {
                                g.drawImage(new ImageIcon("character/terrain/grass.png").getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                            }else {
                                g.drawImage(new ImageIcon("character/terrain/grass1.png").getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                            }
                            gameStage.towerMap[i][j].image = new ImageIcon("character/tower/Untitled3.png").getImage();
                            g.drawImage(new ImageIcon("character/tower/Untitled3.png").getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                        } else {
                            //g.drawImage(new ImageIcon("character/terrain/grass.png").getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                            if(gameStage.levelFile.i == 1) {
                                g.drawImage(new ImageIcon("character/terrain/grass.png").getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                            }else {
                                g.drawImage(new ImageIcon("character/terrain/grass1.png").getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                            }
                            gameStage.towerMap[i][j].image = new ImageIcon("character/tower/Untitled4.png").getImage();
                            g.drawImage(new ImageIcon("character/tower/Untitled4.png").getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                        }
                    }
                }
            }
        }
    }

    public void Explosion(){
        for(int i = 0; i < 100; i++){
            if(EnemyMove.EXPLOSION % 2 == 0){

            }else {
                g.drawImage(new ImageIcon("character/terrain/explosion.jpg").getImage(), (int) EnemyMove.explosionX[i] + 55, (int) EnemyMove.explosionY[i] + 220, 55, 55, null);
                EnemyMove.EXPLOSION++;
            }
        }
    }

    public void paintSpeedButton(){
        if(gameStage.BOOST % 2 == 0){
            g.drawImage(new ImageIcon("character/tower/speed.jpg").getImage(), 55 * 20 + 55 / 2, 55, 55 * 2 + 55 / 2, 55 * 2, null);
        }else{
            g.drawImage(new ImageIcon("character/tower/boost.png").getImage(), 55 * 20 + 55 / 2, 55, 55 * 2 + 55 / 2, 55 * 2, null);
        }
    }
}
