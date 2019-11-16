public class Level {
    int[][] map;
    Spawner spawnPoint;

    public Spawner findSpawnPoint(){
        int k = 0;
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if(map[i][j] == 2){
                    spawnPoint = new Spawner(i, j);

                }
            }
        }
        return spawnPoint;
    }
}
