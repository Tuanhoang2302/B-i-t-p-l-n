import javax.swing.*;
import java.awt.*;

public class Paint {
    Graphics g;
    GameField gameField;

    public Paint(Graphics g, GameField gameStage){
        this.g = g;
        this.gameField = gameStage;
    }

    public void paintBackGround() {
        g.drawImage(new ImageIcon("character/terrain/grown.jpg").getImage(), 0, 0, gameField.frame.getWidth(), gameField.frame.getHeight(), null);
        g.setColor(Color.RED);
    }

    public void paintGrid(){
        //grid
        g.setColor(Color.RED);
        for (int i = 0; i <= 25; i++) {
            for (int j = 0; j <= 12; j++) {
                if (gameField.gameTile.map[i][j] == 1) {
                    if (gameField.levelFile.i == 1) {
                        g.drawImage(new ImageIcon("character/terrain/road.png").getImage(), i * 55 + 55, 220 + j * 55, 55, 55, null);
                    } else {
                        g.drawImage(new ImageIcon("character/terrain/road.png").getImage(), i * 55 + 55, 220 + j * 55, 55, 55, null);
                    }
                } else if (gameField.gameTile.map[i][j] == 0) {
                    if (gameField.levelFile.i == 1) {
                        g.drawImage(new ImageIcon("character/terrain/grass.png").getImage(), i * 55 + 55, 220 + j * 55, 55, 55, null);
                    } else {
                        g.drawImage(new ImageIcon("character/terrain/grass1.png").getImage(), i * 55 + 55, 220 + j * 55, 55, 55, null);
                    }
                } else if (gameField.gameTile.map[i][j] == 3) {
                    g.drawImage(new ImageIcon("character/terrain/base1.png").getImage(), i * 55 + 55, 220 + j * 55, 55, 55, null);
                } else if (gameField.gameTile.map[i][j] == 2) {
                    g.drawImage(new ImageIcon("character/terrain/road2.png").getImage(), i * 55 + 55, 220 + j * 55, 55, 55, null);
                }
                //g.drawRect(55 + 55 * i, 55 * 4 + 55 * j, 55, 55);
            }
        }
    }

    public void paintEnemy(){
        //Enemy
        for (int i = 0; i < gameField.enemyMap.length; i++) {
            if (gameField.enemyMap[i] != null) {
                g.drawImage(gameField.enemyMap[i].enemy.texture, (int) gameField.enemyMap[i].xPos + 55, (int) gameField.enemyMap[i].yPos + 220, 55, 55, null);
                g.setColor(Color.GRAY);
                //g.drawRect((int) enemyMap[i].xPos + 55, (int) enemyMap[i].yPos + 200, 55, 10);
                g.setColor(Color.RED);
                //g.drawRect((int) enemyMap[i].xPos + 55, (int) enemyMap[i].yPos + 200, 55 * enemyMap[i].health / 100, 10);
                g.fillRect((int) gameField.enemyMap[i].xPos + 55, (int) gameField.enemyMap[i].yPos + 200, 55 * gameField.enemyMap[i].health / 100, 10);
            }
        }
    }

    public void paintHealthAndMoney(){
        g.setColor(Color.pink);
        g.drawRect(55, 55, 55 * 5, 55 * 2);
        g.drawRect(55, 55, 55 * 5, 55);
        String health = "Health: " + GameField.player.health;
        g.setFont(new Font(health, Font.PLAIN, 20));
        g.drawString(health, 55 + 55 / 2, 55 + 35);

        String money = "Money: " + GameField.player.money;
        g.setFont(new Font(money, Font.PLAIN, 20));
        g.drawString(money, 55 + 55 / 2, 110 + 35);
    }

    public void paintTowerStatus(){
        g.drawRect(55 * 30, 55, 55 * 2, 55 * 2);
        if (gameField.gameTile.selectedTower != null) {
            g.drawImage(gameField.gameTile.selectedTower.image, 55 * 30, 55, 55 * 2, 55 * 2, null);
            for (int i = 0; i <= 2; i++) {
                g.setColor(Color.GRAY);
                g.drawRect(55 * 24, 55 + 110 * i / 3, 55 * 4, 110 / 3);

            }
            //System.out.println(selectedTower.dam);
            g.setColor(Color.ORANGE);
            String atk = "ATTACK: " + gameField.gameTile.selectedTower.dam;
            g.drawString(atk, 55 * 24 + 20, 55 + 25);
            String atkspd = "ATK SPD: " + Math.round((100 / gameField.gameTile.selectedTower.maxAttackTimeDelay) * 10) / 10;
            g.drawString(atkspd, 55 * 24 + 20, 90 + 25);
            String range = "RANGE: " + gameField.gameTile.selectedTower.range;
            g.drawString(range, 55 * 24 + 20, 125 + 25);
        }
    }

    public void paintTowerList(){
        //g.drawRect(55 * 30, 55 * 4, 55 * 2, 55 * 2);
        g.drawImage(Tower.towerList[0].image, 55 * 30, 55 * 4, 70, 110, null);
        g.drawImage(Tower.towerList[1].image, 55 * 30, 55 * 7, 70, 110, null);
        g.drawImage(Tower.towerList[2].image, 55 * 30, 55 * 10, 70, 110, null);
        g.setColor(new Color(220, 0, 0, 100));
        if (Tower.towerList[0].cost > GameField.player.money) {
            g.fillRect(55 * 30, 55 * 4, 55 * 2, 55 * 2);
        }
        if (Tower.towerList[1].cost > GameField.player.money) {
            g.fillRect(55 * 30, 55 * 7, 55 * 2, 55 * 2);
        }
        if (Tower.towerList[2].cost > GameField.player.money) {
            g.fillRect(55 * 30, 55 * 10, 55 * 2, 55 * 2);
        }
    }

    public void paintMissiles(){
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < GameTile.missiles.length; i++) {
            if (GameTile.missiles[i] != null) {
                g2d.rotate(GameTile.missiles[i].direction + Math.toRadians(90), (int) GameTile.missiles[i].x, (int) GameTile.missiles[i].y);
                g.drawImage(GameTile.missiles[i].texture, (int) GameTile.missiles[i].x, (int) GameTile.missiles[i].y, 20, 20, null);
                g2d.rotate(-GameTile.missiles[i].direction + Math.toRadians(-90), (int) GameTile.missiles[i].x, (int) GameTile.missiles[i].y);
            }
        }
    }

    public void paintNormalBullet() {
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < GameTile.normalBullet.length; i++) {
            if (GameTile.normalBullet[i] != null) {
                g2d.rotate(GameTile.normalBullet[i].direction + Math.toRadians(90), (int) GameTile.normalBullet[i].x, (int) GameTile.normalBullet[i].y);
                g.drawImage(GameTile.normalBullet[i].texture, (int) GameTile.normalBullet[i].x, (int) GameTile.normalBullet[i].y, 30, 20, null);
                g2d.rotate(-GameTile.normalBullet[i].direction + Math.toRadians(-90), (int) GameTile.normalBullet[i].x, (int) GameTile.normalBullet[i].y);
            }
        }
    }

    public void paintTowerAndRange(){
        for (int i = 0; i <= 25; i++) {
            for (int j = 0; j <= 12; j++) {
                if (gameField.gameTile.towerMap[i][j] != null) {
                    if (gameField.gameTile.selectedTower == gameField.gameTile.towerMap[i][j]) {
                        g.setColor(Color.GRAY);
                        g.drawOval(55 + 55 * i - (gameField.gameTile.towerMap[i][j].range * 2 * 55 + 55) / 2 + 55 / 2, 55 * 4 + 55 * j - (gameField.gameTile.towerMap[i][j].range * 2 * 55 + 55) / 2 + 55 / 2, gameField.gameTile.towerMap[i][j].range * 2 * 55 + 55, gameField.gameTile.towerMap[i][j].range * 2 * 55 + 55);
                        g.setColor(new Color(50, 50, 50, 50));
                        g.fillOval(55 + 55 * i - (gameField.gameTile.towerMap[i][j].range * 2 * 55 + 55) / 2 + 55 / 2, 55 * 4 + 55 * j - (gameField.gameTile.towerMap[i][j].range * 2 * 55 + 55) / 2 + 55 / 2, gameField.gameTile.towerMap[i][j].range * 2 * 55 + 55, gameField.gameTile.towerMap[i][j].range * 2 * 55 + 55);
                    }
                    g.drawImage(Tower.towerList[gameField.gameTile.towerMap[i][j].id].image, 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                    if (gameField.gameTile.towerMap[i][j].target != null) {
                        if (gameField.gameTile.towerMap[i][j] instanceof LaserTower) {
                            g.setColor(Color.RED);
                            g.drawLine(55 + 55 * i + 55 / 2, 55 * 4 + 55 * j + 55 / 2, 55 + (int) gameField.gameTile.towerMap[i][j].target.xPos + 55 / 2, 55 * 4 + (int) gameField.gameTile.towerMap[i][j].target.yPos + 55 / 2);
                        }
                    }
                }
            }
        }
    }

    public void paintStageAndRound(){
        g.setColor(Color.ORANGE);
        String stage = "Stage " + gameField.levelFile.i;
        String round = "Wave " + gameField.wave.waveNumber;
        g.setFont(new Font(stage, Font.PLAIN, 30));
        g.setFont(new Font(round, Font.PLAIN, 30));
        g.drawString(stage + " " + round, 10 * 55, 55 * 3 + 40);
    }

    public void paintThunder(){
        if(GameField.player.money >= 100) {
            g.drawImage(new ImageIcon("character/terrain/thunderbutton.jpg").getImage(), 55 * 10 + 55 / 2, 55, 55 * 2 + 55 / 2, 55 * 2, null);
        }
        if (gameField.THUNDERABLE % 2 == 0) {
        } else {
            g.drawImage(new ImageIcon("character/terrain/thunder.png").getImage(), 0, 0, gameField.frame.getWidth(), gameField.frame.getHeight(), null);
            gameField.THUNDERABLE++;
        }
    }

    public void paintUpgrade(){
        g.drawImage(new ImageIcon("character/terrain/upgrade.jpg").getImage(), 55 * 18, 55, 55 * 2 + 55 / 2, 55 * 2, null);

        for (int i = 0; i <= 25; i++) {
            for (int j = 0; j <= 12; j++) {
                if (gameField.gameTile.towerMap[i][j] != null){
                    if(gameField.gameTile.towerMap[i][j].Upgrade == 1){
                        if(gameField.gameTile.towerMap[i][j] instanceof LaserTower) {
                            if(gameField.levelFile.i == 1) {
                                g.drawImage(new ImageIcon("character/terrain/grass.png").getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                            }else {
                                g.drawImage(new ImageIcon("character/terrain/grass1.png").getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                            }
                            gameField.gameTile.towerMap[i][j].image = new ImageIcon("character/tower/Untitled2.png").getImage();
                            g.drawImage(new ImageIcon(gameField.gameTile.towerMap[i][j].image).getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                        }else if(gameField.gameTile.towerMap[i][j] instanceof MissileTower){
                            //g.drawImage(new ImageIcon("character/terrain/grass.png").getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                            if(gameField.levelFile.i == 1) {
                                g.drawImage(new ImageIcon("character/terrain/grass.png").getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                            }else {
                                g.drawImage(new ImageIcon("character/terrain/grass1.png").getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                            }
                            gameField.gameTile.towerMap[i][j].image = new ImageIcon("character/tower/Untitled3.png").getImage();
                            g.drawImage(new ImageIcon("character/tower/Untitled3.png").getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                        } else {
                            //g.drawImage(new ImageIcon("character/terrain/grass.png").getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                            if(gameField.levelFile.i == 1) {
                                g.drawImage(new ImageIcon("character/terrain/grass.png").getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                            }else {
                                g.drawImage(new ImageIcon("character/terrain/grass1.png").getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                            }
                            gameField.gameTile.towerMap[i][j].image = new ImageIcon("character/tower/Untitled4.png").getImage();
                            g.drawImage(new ImageIcon("character/tower/Untitled4.png").getImage(), 55 + 55 * i, 55 * 4 + 55 * j, 55, 55, null);
                        }
                    }
                }
            }
        }
    }

    public void Explosion(){
        for(int i = 0; i < 100; i++){
            if(EnemyState.EXPLOSION % 2 == 0){

            }else {
                g.drawImage(new ImageIcon("character/terrain/explosion.jpg").getImage(), (int) EnemyState.explosionX[i] + 55, (int) EnemyState.explosionY[i] + 220, 55, 55, null);
                EnemyState.EXPLOSION++;
            }
        }
    }

    public void paintSpeedButton(){
        if(gameField.BOOST % 2 == 0){
            g.drawImage(new ImageIcon("character/tower/speed.jpg").getImage(), 55 * 20 + 55 / 2, 55, 55 * 2 + 55 / 2, 55 * 2, null);
        }else{
            g.drawImage(new ImageIcon("character/tower/boost.png").getImage(), 55 * 20 + 55 / 2, 55, 55 * 2 + 55 / 2, 55 * 2, null);
        }
    }
}
