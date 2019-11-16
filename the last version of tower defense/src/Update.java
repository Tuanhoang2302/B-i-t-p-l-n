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
                if(gameStage.gameTile.towerMap[i][j] != null){
                    gameStage.towerAttack(i, j);

                }
            }
        }
    }

    public void missileUpdate(){
        for(int i = 0; i < GameTile.missiles.length; i++){
            if(GameTile.missiles[i] != null){
                GameTile.missiles[i].update();

                if(GameTile.missiles[i].target == null){
                    GameTile.missiles[i] = null;

                }
            }
        }
    }

    public void normalBulletUpdate(){
        for(int i = 0; i < GameTile.normalBullet.length; i++){
            if(GameTile.normalBullet[i] != null){
                GameTile.normalBullet[i].update();

                if(GameTile.normalBullet[i].target == null){
                    GameTile.normalBullet[i] = null;
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
