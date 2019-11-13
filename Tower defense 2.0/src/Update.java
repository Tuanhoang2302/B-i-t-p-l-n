public class Update {
    private GameStage gameStage;
    int m = 0;

    public Update(GameStage gameStage){
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

                if(GameStage.player.health <= 0){
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
        for(int i = 0; i < GameStage.missiles.length; i++){
            if(GameStage.missiles[i] != null){
                GameStage.missiles[i].update();

                if(GameStage.missiles[i].target == null){
                    GameStage.missiles[i] = null;
                }
            }
        }
    }

    public void normalBulletUpdate(){
        for(int i = 0; i < GameStage.normalBullet.length; i++){
            if(GameStage.normalBullet[i] != null){
                GameStage.normalBullet[i].update();

                if(GameStage.normalBullet[i].target == null){
                    GameStage.normalBullet[i] = null;
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
