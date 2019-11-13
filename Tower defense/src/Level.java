public class Level {
    int[][] map;
    SpawnPoint spawnPoint;

    public SpawnPoint findSpawnPoint(){
        int k = 0;
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if(map[i][j] == 2){
                    spawnPoint = new SpawnPoint(i, j);

                }
            }
        }
        return spawnPoint;
    }
}
