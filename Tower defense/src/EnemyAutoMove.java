public class EnemyAutoMove extends EnemyAuto {
    public EnemyAutoMove(int id){
        super(id);
    }

    public void move(EnemyMove enemy){
        //System.out.println(enemy.xPos % 55);
        //System.out.println(enemy.yPos);
        if((int)enemy.xPos % 55 == 0 && (int)enemy.yPos % 55 == 0 && enemy.routePosX == (int)enemy.xPos / 55 && enemy.routePosY == (int)enemy.yPos / 55){
            if(enemy.routePosX == baseposX && enemy.routePosY == baseposY){
                enemy.attack = true;
            } else {
                if(route.route[enemy.routePosX][enemy.routePosY] == route.UP){
                    enemy.routePosY--;
                }else if(route.route[enemy.routePosX][enemy.routePosY] == route.DOWN){
                    enemy.routePosY++;
                }else if(route.route[enemy.routePosX][enemy.routePosY] == route.RIGHT){
                    enemy.routePosX ++;
                }else if(route.route[enemy.routePosX][enemy.routePosY] == route.LEFT){
                    enemy.routePosX--;
                }else {
                }
            }
        }else {
            double xPos = (int)enemy.xPos / 55 ;
            double yPos = (int)enemy.yPos / 55;

            if(xPos > enemy.routePosX){
                enemy.xPos -= enemy.enemy.speed / 24 * GameStage.speed;
                if(xPos < enemy.routePosX * 55){
                    enemy.xPos = enemy.routePosX * 55;
                }
            }
            if(xPos < enemy.routePosX){
                enemy.xPos += enemy.enemy.speed / 24 * GameStage.speed;
                if(xPos > enemy.routePosX * 55){
                    enemy.xPos = enemy.routePosX * 55;
                }
            }
            if(yPos > enemy.routePosY){
                enemy.yPos -= enemy.enemy.speed / 24 * GameStage.speed;
                if(yPos < enemy.routePosY * 55){
                    enemy.yPos = enemy.routePosY * 55;
                }
            }
            if(yPos < enemy.routePosY){
                enemy.yPos += enemy.enemy.speed / 24 * GameStage.speed;
                if(yPos > enemy.routePosY * 55){
                    enemy.yPos = enemy.routePosY * 55;
                }
            }
        }
    }

    public void canFindRoute(){
        System.out.println("can't find move");
    }
}
