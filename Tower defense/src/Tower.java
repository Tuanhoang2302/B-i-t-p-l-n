import javax.swing.*;
import java.awt.*;
import java.util.Random;

public abstract class Tower implements Cloneable{
    public static Tower[] towerList = new Tower[100];
    public static Tower warrior = new LaserTower(0, 30, 2, 1, 1, 5).getTexturefile("warrior");
    public static Tower missileTower = new MissileTower(1, 30, 2, 10, 1, 30).getTexturefile("Untitled");
    public static Tower specialTower = new SpecialTower(2, 30, 2, 4, 1, 20).getTexturefile("pp");

    public Image image;
    public String textureFile = "";

    public int id;
    public int cost;
    public int range;
    public int dam;
    public int attackTime;
    public int attackTimeDelay;
    public int maxAttackTime;
    public int maxAttackTimeDelay;
    public int Upgrade = 0;

    public int posX;
    public int posY;

    public boolean selected;

    public int AttackFirst_EnemyNearBase = 1;
    public int Random = 1;
    public int First = 2;
    public int Last = 3;
    public int Strong = 4;

    public int attackStrategy = First;

    public EnemyMove target;

    public Tower(int id, int cost, int range, int dam, int maxAttackTime, int maxAttackTimeDelay){
        if(towerList[id] != null){

        }else {
            this.id = id;
            towerList[id] = this;
            this.cost = cost;
            this.range = range;
            this.dam = dam;

            this.maxAttackTime = maxAttackTime;
            this.maxAttackTimeDelay = maxAttackTimeDelay;

            this.attackTime = 0;
            this.attackTimeDelay = 0;
        }
    }

    public EnemyMove calculateEnemy(EnemyMove[] enemies, int x, int y){
        EnemyMove[] enemiesInRange = new EnemyMove[enemies.length];

        int towerX = x;
        int towerY = y;

        int towerRadius = this.range;
        int enemyRadius =  1;

        int enemyX;
        int enemyY;

        for(int i = 0; i < enemies.length; i++){
            if(enemies[i] != null){
                enemyX = (int) enemies[i].xPos / 55;
                enemyY = (int) enemies[i].yPos / 55;

                int dx = enemyX - towerX;
                int dy = enemyY - towerY;

                int dradius = towerRadius + enemyRadius;

                if(dx * dx + dy * dy < dradius * dradius){
                    enemiesInRange[i] = enemies[i];
                }
            }
        }

        int totalEnemies = 0;

        for(int i = 0; i < enemiesInRange.length; i++){
            if(enemiesInRange[i] != null) {
                totalEnemies++;
            }
        }

        if(this.attackStrategy == Random){
            if(totalEnemies > 0){
                int enemy = new Random().nextInt(totalEnemies);
                int enemiesTaken = 0;
                int i = 0;

                while (true){
                    if(enemiesTaken == enemy && enemiesInRange != null){
                        return enemiesInRange[i];
                    }

                    if(enemiesInRange != null){
                        enemiesTaken++;
                    }

                    i++;
                }
            }
        }
        if(this.attackStrategy == First){
            EnemyMove bestTarget = null;
            for(int i = 0; i < enemiesInRange.length; i++){
                if(enemiesInRange[i] != null) {
                    if(bestTarget == null){
                        bestTarget = enemiesInRange[i];
                    }else {
                        int b_x= bestTarget.routePosX;
                        int b_y = bestTarget.routePosY;

                        int b_points_worth = GameStage.enemyAI.route.getPointsWorth(b_x, b_y);
                        if(GameStage.enemyAI.route.getPointsWorth(enemiesInRange[i].routePosX, enemiesInRange[i].routePosY) > b_points_worth){
                            bestTarget = enemiesInRange[i];
                        }else if (GameStage.enemyAI.route.getPointsWorth(enemiesInRange[i].routePosX, enemiesInRange[i].routePosY) == b_points_worth){

                        }
                    }
                }
            }
            return bestTarget;
        }
        return null;
    }

    public abstract void towerAttack(int x, int y, EnemyMove enemies);

    public Tower getTexturefile(String str){
        this.textureFile = str;
        this.image = new ImageIcon("character/tower/" + this.textureFile + ".png").getImage();
        return null;
    }

    protected Object clone(){
        try{
            return super.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return null;
    }
}
