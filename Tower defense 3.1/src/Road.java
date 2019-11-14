public class Road {
    Level level;
    int[][] route = new int[26][13];
    int[][] route1 = new int[26][13];
    int[][] route_pointsWorth = new int[26][13];
    int baseBlock = 3;
    public Target base;
    int xpos2, ypos2;

    int lastPos = - 1;

    int RIGHT = 1;
    int DOWN = 2;
    int LEFT = 3;
    int UP = 4;
    int INDEX = 0;


    public Road(Level level){
        this.level = level;

        this.xpos2 = this.level.findSpawnPoint().x;
        this.ypos2 = this.level.findSpawnPoint().y;

        calculateRoute();
    }

    public void calculateRoute(){
        while (base == null){
            calculateNextPos();
        }

    }

    public void calculateNextPos(){

        for(int i = 1; i < 5; i++){
            if(i != lastPos){
                if(ypos2 > 0 && i == UP){
                    if(level.map[xpos2][ypos2 - 1] == 1){
                        setPointWorth();
                        lastPos = DOWN;
                        route[xpos2][ypos2] = UP;

                        ypos2--;
                        break;
                    }else if(level.map[xpos2][ypos2 - 1] == baseBlock){
                        base = new Target(xpos2, ypos2 - 1);
                        lastPos = DOWN;
                        route[xpos2][ypos2] = UP;
                        break;
                    }
                }


                if(i == DOWN && ypos2 < 12){
                    if(level.map[xpos2][ypos2 + 1] == 1){
                        setPointWorth();
                        lastPos = UP;
                        route[xpos2][ypos2] = DOWN;

                        ypos2++;
                        break;
                    }else if(level.map[xpos2][ypos2 + 1] == baseBlock){
                        base = new Target(xpos2, ypos2 + 1);
                        lastPos = UP;
                        route[xpos2][ypos2] = DOWN;
                        break;
                    }
                }

                if(i == RIGHT && xpos2 < 25){
                    if(level.map[xpos2 + 1][ypos2] == 1){
                        setPointWorth();
                        lastPos = LEFT;
                        route[xpos2][ypos2] = RIGHT;

                        xpos2++;
                        break;
                    }else if(level.map[xpos2 + 1][ypos2] == baseBlock){
                        base = new Target(xpos2 + 1, ypos2);
                        lastPos = LEFT;
                        route[xpos2][ypos2] = RIGHT;
                        break;
                    }
                }

                if(xpos2 > 0 && i == LEFT){
                    if(level.map[xpos2 - 1][ypos2] == 1){
                        setPointWorth();
                        lastPos = RIGHT;
                        route[xpos2][ypos2] = LEFT;

                        xpos2--;
                        break;
                    }else if(level.map[xpos2 - 1][ypos2] == baseBlock){
                        base = new Target(xpos2 - 1, ypos2);
                        lastPos = RIGHT;
                        route[xpos2][ypos2] = LEFT;
                        break;
                    }
                }
            }
        }
    }


    public void setPointWorth(){
        if(lastPos == UP){
            route_pointsWorth[xpos2][ypos2] = route_pointsWorth[xpos2][ypos2 - 1] + 1;
        }
        if(lastPos == DOWN){
            route_pointsWorth[xpos2][ypos2] = route_pointsWorth[xpos2][ypos2 + 1] + 1;
        }
        if(lastPos == RIGHT){
            route_pointsWorth[xpos2][ypos2] = route_pointsWorth[xpos2 + 1][ypos2] + 1;
        }
        if(lastPos == LEFT){
            route_pointsWorth[xpos2][ypos2] = route_pointsWorth[xpos2 - 1][ypos2] + 1;
        }
        if(lastPos == -1){
            route_pointsWorth[xpos2][ypos2] = 1;
        }
    }

    public int getPointsWorth(int x, int y){
        return route_pointsWorth[x][y];
    }
}

