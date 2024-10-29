package org.example;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.example.entity.Entity;
import org.example.entity.Player;
import org.example.gameplugins.GamePlugin;
import org.example.gameplugins.PluginLoader;
import org.example.gameplugins.SimpleGameAPI;
import org.example.tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
    // SCREEN settings
    public int originalTileSize = 16; //16x16 characters
    public int scale = 3; // makes it 48 x 48 it scales
    public int tileSize = originalTileSize * scale;
    public int maxScreenCol = 10;
    public int maxScreenRow = 20;
    public int screenWidth = tileSize * maxScreenCol; //768
    public int screenHeight= tileSize * maxScreenRow; //576
    //FPS
    int FPS = 60;
    TileManager tileManager;
    InputHandler keyH = new InputHandler(this);
    Thread gameThread;
    public CollisionDetector cDetector;// = new CollisionDetector(this);
    public AssetSetter assetSetter;// = new AssetSetter(this);
    public UI ui;// = new UI(this, keyH);
    //PLAYER
    //default player pos
    int playerX;
    int playerY;
    int playerSpeed = 4;
    public Player player;
    public Entity obj[];
    public ArrayList<Entity> entityList = new ArrayList<>();
    public List<GamePlugin> plugins;
    //GameState implementation
    public int gameState;
    public static int playState = 1;
    public static int pauseState =2;
    public static int titleState = 0;
    public GamePanel(GameConfig config){
        //----Getting the config----//
        int[] size = config.getSize();
        int[] start = config.getStart();

        this.maxScreenCol = size[0];
        this.maxScreenRow = size[1];
        this.screenHeight = tileSize * maxScreenRow;
        this.screenWidth = tileSize * maxScreenCol; //768
        this.playerX = start[0] * tileSize;
        this.playerY = start[1] * tileSize;
        obj = new Entity[10]; //10 slots

        tileManager = new TileManager(this);
        cDetector = new CollisionDetector(this);
        assetSetter = new AssetSetter(this, config);
        ui = new UI(this, keyH);

        player = new Player(this, keyH, this.playerX, this.playerY);
        SimpleGameAPI gameAPI = new SimpleGameAPI(player);
        PluginLoader pluginLoader = new PluginLoader();

        pluginLoader.loadPlugin("org.example.plugin.PrintPlugin", gameAPI);
        plugins =  pluginLoader.getPlugins();
        for(GamePlugin plugin : plugins){
            plugin.printRetard();
        }

        List<Item> items = config.getItems();
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        
    }
    public void setUpGame(){
        
        assetSetter.setObject();
        gameState = titleState;
    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override //THE GAME LOOP IS HERE
    public void run(){
        //DELTA ACCUMULATOR METHOD OF GAME LOOP
        double drawInterval = 1000000000/FPS;
        double delta =0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        while(gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if(delta >=1){
                update();
                repaint();
                delta --;
                drawCount ++;
            }
            if(timer >= 1000000000){
                System.out.println("FPS:" + drawCount);
                drawCount =0;
                timer = 0;
            }
            
        }
    }
    public void update(){
        if(gameState == playState){
            player.update();

        }if(gameState == pauseState){

        }
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g; //change graphics to 2d more functions
        if(gameState == titleState){
            ui.draw(g2);
        }else{
            //tile
            tileManager.draw(g2);
            //object
            entityList.add(player);

            for(int i = 0; i < obj.length; i++){
                if(obj[i]!= null){
                    entityList.add(obj[i]);

                }
            }

            for(int i = 0; i < entityList.size(); i++){
                entityList.get(i).draw(g2);
            }
            for(int i = 0; i < entityList.size(); i++){
                entityList.remove(i);
            }
            ui.draw(g2);
            g2.dispose(); //mem saver
        }

        
        
    }
}
