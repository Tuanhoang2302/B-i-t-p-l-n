import javafx.stage.Screen;

import java.util.Random;

public class Wave {
    public GameStage gameStage;
    public int waveNumber = 0;
    public int allEnemiesInOneRound = 0;
    public int pointsThisRound;
    boolean spawning;
    public int countDelay;
    public int timeDelay = 150;

    public int currentPoint;

    public Wave(GameStage gameStage){
        this.gameStage = gameStage;

    }

    public void nextWave(){
        this.waveNumber++;
        this.pointsThisRound = this.waveNumber * 10;
        this.currentPoint = 0;
        this.allEnemiesInOneRound = 0;
        this.spawning = true;

        //System.out.println("coming");
        System.out.println(waveNumber);
        for(int i = 0; i < this.gameStage.enemyMap.length; i++){
            this.gameStage.enemyMap[i] = null;
        }
    }

    public void spawnEnemy(){
        if(this.currentPoint < this.pointsThisRound){
            if(countDelay < timeDelay){
                countDelay++;
            } else {
                countDelay = 0;

                //System.out.println("enemy has spawned");
                //System.out.println(Enemy.enemyList.length);
                int[] enemiesSpawnableID = new int[Enemy.enemyList.length];
                int enemiesSpawnable = 0;

                for(int i = 0; i < Enemy.enemyList.length; i++){
                    if(Enemy.enemyList[i] != null) {
                        if (Enemy.enemyList[i].point + currentPoint <= this.pointsThisRound && Enemy.enemyList[i].point <= this.waveNumber) {
                            enemiesSpawnableID[enemiesSpawnable] = Enemy.enemyList[i].id;
                            enemiesSpawnable++;
                        }
                    }
                }
                //System.out.println(enemiesSpawnable);
                int enemyID = new Random().nextInt(3);
                this.currentPoint += Enemy.enemyList[enemyID].point;
                //this.gameStage.spawnEnemy(enemiesSpawnableID[enemyID]);
                this.gameStage.spawnEnemy(enemyID);
            }
        } else {
            this.spawning = false;
        }
    }
}
