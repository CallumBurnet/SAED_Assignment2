package org.example.tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import org.example.GamePanel;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNumber[][];
    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[10];
        mapTileNumber = new int[gp.maxScreenCol][gp.maxScreenRow];
        getTileImage();
        loadMap();
    }
    public void getTileImage(){
        try{
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass01.png"));
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            tile[1].collision = true; //call collision to true

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void loadMap(){
        try{
            //Get the map
            InputStream is = getClass().getResourceAsStream("/maps/map01.txt");
            //Read the map line by line
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
            int col = 0;
            int row = 0;
            while(col < gp.maxScreenCol && row < gp.maxScreenRow){
                String line = br.readLine();
                while(col<gp.maxScreenCol){
                    String numbers[] = line.split(" "); //Split with a space
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNumber[col][row] = num;
                    col++;
                }
                if(col == gp.maxScreenCol){
                    col = 0;
                    row ++;
                }
            }
            br.close();
        }catch(Exception e){
            
        }
    }
    //Tile reading in
    public void draw(Graphics2D g2){
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;
        while(col < gp.maxScreenCol && row < gp.maxScreenRow){
            int tileNum = mapTileNumber[col][row];
            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x += gp.tileSize;
            //GO to next row
            if(col == gp.maxScreenCol){
                col = 0;
                x = 0;
                row ++;
                y+= gp.tileSize;
            }
        }
    }
}
