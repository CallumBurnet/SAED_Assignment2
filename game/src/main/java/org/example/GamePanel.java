package org.example;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

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
    public Entity obj[] = new Entity[30]; //10 slots
    public Entity npc[] = new Entity[10];
    public Obstacle obstacles[] = new Obstacle[20];
    public Goal goals[] = new Goal[10];
    public ArrayList<Entity> entityList = new ArrayList<>();
    public List<GamePlugin> plugins;
    //GameState implementation
    public int gameState;
    public int titleState = 0;
    public int playState = 1;
    public int pauseState =2;
    public int dialogueState = 3;
    public int deathState = 4;
    public int languageState = 5;
    public int victoryState = 6;
    public int restartState = 7;
    private int obstacleTimer = 0;
    public AssetSetter assetSetter;
    public Boolean visible;
    public SimpleGameAPI gameAPI;
    public GameLocalization gameLocal;
    public GameDate gameDate;
    public int days = 0;
    private int secondTimer = 0;
    private GameConfig config;
    EnvironmentManager environmentManager = new EnvironmentManager(this);

    public GamePanel(GameConfig config){
        //--------/
        this.gameLocal = new GameLocalization(this);


        // Print out localized strings to check for correct display
        System.out.println(gameLocal.getText("game_name"));
        //----Getting the config----//
        this.config = config;
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
        List<String> pluginsToLoad = config.getPlugins();
        for (String pluginName : pluginsToLoad) {
            pluginLoader.loadPlugin(pluginName, gameAPI);
            System.out.println("Loaded plugin: " + pluginName);
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
        assetSetter.setObstacleToEntity();
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
        ScriptHandler scriptHandler = new ScriptHandler(gameAPI);
        List<String> scriptsToLoad = config.getScripts();
        //scriptHandler.runScript("api.createIdleObstacleAfterDelay('death', 0)");

        for (String script : scriptsToLoad) {
            String parsedScript = script.trim();
            if (parsedScript.startsWith("\"") && parsedScript.endsWith("\"")) {
                parsedScript = parsedScript.substring(1, parsedScript.length() - 1);
            }
            // Assuming you have a method to process or execute the script, e.g., executeScript
            System.out.println("SCRIPT :" + parsedScript);
            scriptHandler.runScript(parsedScript);
            System.out.println("Executed script:\n" + parsedScript);
        }
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
                gameAPI.onSecondPassed();
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

        }else if(gameState == restartState){
            resetGame(config); // Pass the initial GameConfig for restart
            gameState = playState;
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

            for (Entity element : obj) {
                            if(element!= null){
                                entityList.add(element);
            
                            }
                        }
            for (Goal goal : goals) {
                            if(goal!= null){
                                entityList.add(goal);
            
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
            for (Entity element : npc) {
                            if(element!= null){
                                entityList.add(element);
                
                            }
                        }        


            for (Entity element : entityList) {
                            element.draw(g2);
                        }
            for(int i = 0; i < entityList.size(); i++){
                entityList.remove(i);
            }
            //The light
            
            //environmentManager.draw(g2,visible );

            

            ui.draw(g2);
            g2.dispose(); //mem saver
        }

        
        
    }
    public void resetGame(GameConfig config) {
        // Reinitialize all game properties
        this.gameState = titleState;
        this.playerX = config.getStart()[0] * tileSize;
        this.playerY = config.getStart()[1] * tileSize;
        
        // Reinitialize components that were instantiated in the constructor
        this.tileManager = new TileManager(this);
        this.cDetector = new CollisionDetector(this);
        this.assetSetter = new AssetSetter(this, config);
        this.player = new Player(this, keyH, this.playerX, this.playerY);
        this.ui = new UI(this, keyH);
        this.entityList.clear(); // Clear entities to avoid duplicates
    
        // Reset environment manager or any other instance that holds state
        this.environmentManager = new EnvironmentManager(this);
        this.plugins.clear(); // Clear and reload plugins if necessary
    
        // Reset obstacle timer, day counter, and any other counters
        this.obstacleTimer = 0;
        this.days = 0;
        this.secondTimer = 0;
    
        // Reinitialize any specific objects or NPCs
        this.assetSetter.setObject();
        this.assetSetter.setNPC();
        this.assetSetter.setObstacleToEntity();
        this.assetSetter.setGoal();
        
        // Optionally re-run plugins or scripts if needed
        for(GamePlugin plugin : plugins) {
            plugin.execute();
        }
    
        System.out.println("Game has been reset.");
    }
    
}
