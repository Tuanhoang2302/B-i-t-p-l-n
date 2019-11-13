import com.sun.org.apache.bcel.internal.generic.ARETURN;

import java.io.*;
import java.util.Scanner;

public class LevelFile {
    FileInputStream fileInputStream;
    InputStreamReader reader;

    Scanner scanner;

    Level level = new Level();
    public int i = 1;
    public int MAXLEVEL = 2;

    public Level getLevel(){
        try{
            //File file;
            fileInputStream = new FileInputStream("LevelFile/Level" + i + ".txt");
            reader = new InputStreamReader(fileInputStream);

            level.map = new int[26][13];
            int x = 0;
            int y = 0;
            scanner = new Scanner(reader);
            while (scanner.hasNext()){
                level.map[x][y] = scanner.nextInt();
                if(x < 25){
                    x++;
                } else {
                    x = 0;
                    y++;
                }
            }
            return level;

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        return null;
    }


}
