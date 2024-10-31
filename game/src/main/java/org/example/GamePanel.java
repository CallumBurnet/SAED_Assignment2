package org.example;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JPanel;

import org.engine.GameAPI;
import org.engine.ScriptHandler;
import org.example.entity.Entity;
import org.example.entity.Player;
import org.example.environment.EnvironmentManager;
import org.example.environment.GameDate;
import org.example.environment.GameLocalization;
import org.example.object.Goal;
import org.example.object.Obstacle;
import org.engine.GamePlugin;
import org.engine.PluginLoader;
import org.example.api.SimpleGameAPI;
import org.example.tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
    // SCREEN settings
    public int originalTileSize = 16; //16x16 characters
    public int scale = 3; // makes it 48 x 48 it scales
    public int tileSize = originalTileSize * scale;
    public int maxScreenCol = 10;
    public int maxScreenRow = 10;
    public int screenWidth = tileSize * maxScreenCol; //768
    public int screenHeight= tileSize * maxScreenRow; //576
    //FPS
    int FPS = 60;
    TileManager tileManager;
    public InputHandler keyH = new InputHandler(this);
    Thread gameThread;
    public CollisionDetector cDetector;// = new CollisionDetector(this);
    public UI ui;// = new UI(this, keyH);
    //PLAYER
    //default player pos
    int playerX;
    int playerY;
    int playerSpeed = 4;
    public Player player;
    public Entity obj[] = new Entity[20]; //10 slots
    public Entity npc[] = new Entity[10];
    public Obstacle obstacles[] = new Obstacle[10];
    public Goal goals[] = new Goal[10];
    public ArrayList<Entity> entityList = new ArrayList<>();
    public List<GamePlugin> plugins;
    //GameState implementation
    public int gameState;
    public static int titleState = 0;
    public static int playState = 1;
    public static int pauseState =2;
    public static int dialogueState = 3;
    public static int deathState = 4;
    public static int languageState = 5;
    private int obstacleTimer = 0;
    public AssetSetter assetSetter;
    public Boolean visible;
    public SimpleGameAPI gameAPI;
    private ResourceBundle bundle;
    public GameLocalization gameLocal;
    public GameDate gameDate;
    private int days = 0;
    private int secondTimer = 0;
    EnvironmentManager environmentManager = new EnvironmentManager(this);

    public GamePanel(GameConfig config){
        //--------/
        this.gameLocal = new GameLocalization(this);


        // Print out localized strings to check for correct display
        System.out.println(gameLocal.getText("game_name"));
        //----Getting the config----//
        int[] size = config.getSize();
        int[] start = config.getStart();
        this.maxScreenCol = size[0];
        this.maxScreenRow = size[1];
        this.screenHeight = tileSize * maxScreenRow;
        this.screenWidth = tileSize * maxScreenCol; //768
        this.playerX = start[0] * tileSize;
        this.playerY = start[1] * tileSize;
        this.visible = false;
        tileManager = new TileManager(this);
        cDetector = new CollisionDetector(this);
        this.assetSetter = new AssetSetter(this, config);
        ui = new UI(this, keyH);

        this.player = new Player(this, keyH, this.playerX, this.playerY);
        System.out.println(player.x);

        PluginLoader pluginLoader = new PluginLoader();
        this.gameAPI = new SimpleGameAPI(this);
        ArrayList<String> pluginsToLoad = config.getPlugins();
        ArrayList<String> scriptsToLoad = config.getScripts();
        for (String pluginName : pluginsToLoad) {
            pluginLoader.loadPlugin(pluginName, gameAPI);
            System.out.println("Loaded plugin: " + pluginName);
        }
        ScriptHandler scriptHandler = new ScriptHandler(gameAPI);

        for (String script : scriptsToLoad) {
            // Assuming you have a method to process or execute the script, e.g., executeScript
            scriptHandler.runScript(script);
            System.out.println("Executed script:\n" + script);
        }

        plugins =  pluginLoader.getPlugins();
        for(GamePlugin plugin : plugins){
            plugin.execute();
        }
        gameAPI.onMenuOptionSelected();

       // Executes the teleport action

        List<Item> items = config.getItems();
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setUpGame(){

        assetSetter.setObject();
        assetSetter.setNPC();
        assetSetter.setObstacles();
        assetSetter.setGoal();
        //game state
        gameState = titleState;


        // Load the script (you could read it from a file instead)
        //String pythonScript = "api.createTimedObstacle('death', 5)"; // Replace with actual script loading

        //scriptHandler.runScript(pythonScript);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        environmentManager.setup();
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
                System.out.println("Game State " + this.gameState);
                drawCount =0;
                timer = 0;
                secondTimer ++;
                if(secondTimer % 10 == 0){
                    secondTimer = 0;
                    System.out.println("Seconds" + drawCount);
                    gameDate.advanceDay();
                    days++;
                }
            }
            
        }
    }
    public void update(){
        if(gameState == playState){
            player.update();
            for(int i = 0; i < npc.length; i++){
                if(npc[i] != null){
                    npc[i].update();
                }
            }

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
            for(int i = 0; i < goals.length; i++){
                if(goals[i]!= null){
                    System.out.println("Goal");
                    entityList.add(goals[i]);

                }
            }
            obstacleTimer ++;
            for(int i = 0; i < obstacles.length; i++){
                if(obstacles[i]!= null){
                    
                    if(obstacleTimer % 60 == 0 && obstacles[i].timed == true){
                        obstacles[i].decreaseTimer();
                        if(obstacles[i].getTimer()<=0){
                            gameState = deathState;
                            System.out.println("REMOVING");
                            obstacles[i] = null;
                            obstacleTimer = 0;

                        }else{
                            entityList.add(obstacles[i]);

                        }
                    }else{
                        entityList.add(obstacles[i]);

                    }
    
                }
            }
            for(int i = 0; i < npc.length; i++){
                if(npc[i]!= null){
                    entityList.add(npc[i]);
    
                }
            }        


            for(int i = 0; i < entityList.size(); i++){
                entityList.get(i).draw(g2);
            }
            for(int i = 0; i < entityList.size(); i++){
                entityList.remove(i);
            }
            //The light
            if(visible == true){
                environmentManager.draw(g2);

            }

            ui.draw(g2);
            g2.dispose(); //mem saver
        }

        
        
    }
}
