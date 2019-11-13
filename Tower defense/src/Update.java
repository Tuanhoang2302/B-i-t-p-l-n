public class Update {
    private GameStage gameStage;

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


}
