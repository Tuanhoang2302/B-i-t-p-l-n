public class Update {
    private GameField gameStage;
    public static int m = 0;

    public Update(GameField gameStage){
        this.gameStage = gameStage;
    }

    public void enemyUpdate(){
        for(int i = 0; i < gameStage.enemyMap.length; i++){
            if(gameStage.enemyMap[i] != null){
                //System.out.println(i);
                if(!gameStage.enemyMap[i].attack){
                    EnemyAuto.moveAI.move(gameStage.enemyMap[i]);
                }

                gameStage.enemyMap[i] = gameStage.enemyMap[i].update();

                if(GameField.player.health <= 0){
                    gameStage.DIE = true;
                    //IsRunning = false;
                    //IsRunning = false;
                }
            }
        }
    }

    public void towerUpdate()  {
        for(int i = 0; i < 26; i++){
            for(int j = 0; j < 13; j++){
                if(gameStage.towerMap[i][j] != null){
                    gameStage.towerAttack(i, j);

                }
            }
        }
    }

    public void missileUpdate(){
        for(int i = 0; i < GameField.missiles.length; i++){
            if(GameField.missiles[i] != null){
                GameField.missiles[i].update();

                if(GameField.missiles[i].target == null){
                    GameField.missiles[i] = null;

                }
            }
        }
    }

    public void normalBulletUpdate(){
        for(int i = 0; i < GameField.normalBullet.length; i++){
            if(GameField.normalBullet[i] != null){
                GameField.normalBullet[i].update();

                if(GameField.normalBullet[i].target == null){
                    GameField.normalBullet[i] = null;
                }
            }
        }
    }

    public void waveUpdate(){
        if (gameStage.wave.spawning) {
            gameStage.wave.spawnEnemy();
        }
    }
}
