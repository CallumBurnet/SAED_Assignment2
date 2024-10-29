/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example;
import javax.swing.JFrame;

import org.example.plugin.GamePlugin;
import org.example.plugin.PluginLoader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    public static final int baseMulti = 1;
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            String inputFilePath = args[0];
            try {
                // Open the input file
                InputStream inputFileStream = new FileInputStream(inputFilePath);
                Map<String, ArrayList<int[]>> obstacleMap = new HashMap<>();
                Map<String, ArrayList<int[]>> tempObMap = new HashMap<>();

                Map<String, ArrayList<Item>> itemMap = new HashMap<>();
                Map<String, ArrayList<Item>> tempItemMap = new HashMap<>();
                ArrayList<String> plugins = new ArrayList<>();
                ArrayList<String> scripts = new ArrayList<>();


                // Pass the file input stream to the parser
                MySimpleParser parser = new MySimpleParser(inputFileStream);
                GameConfig config = parser.parseGameConfig();
                int[] size= config.getSize();
                int[] start = config.getStart();
                int[] goal = config.getGoal();

                // String tempPlugins = parser.pluginConfig();
                // System.out.println("PLF" + tempPlugins);
                // String tempScripts = parser.scriptConfig();

             
                // // Assuming 'itemMap' is already populated by parser.itemConfig()
                // for (Map.Entry<String, ArrayList<Item>> entry : itemMap.entrySet()) {
                //     String message = entry.getKey();
                //     ArrayList<Item> items = entry.getValue();

                //     System.out.println("Message: " + message);
                //     for (Item item : items) {
                //         System.out.println("Item: " + item); // You might want to customize the toString() method in Item
                //     }
                // }

                

               
                int x = size[0]*baseMulti;
                int y = size[1]*baseMulti;
              
                int playerX = roundToNearestFive(start[0]);
                int playerY = roundToNearestFive(start[1]);
                System.out.println("Player x: " + playerX + " Player Y" + playerY);

                System.out.println(new App().getGreeting());
                JFrame window = new JFrame(); 
                GamePanel gamePanel = new GamePanel(x,y,start[0] *2, start[1]  *2);
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setResizable(false);   
                window.setTitle("2d Maze");
                window.add(gamePanel);
                window.pack();
                window.setLocationRelativeTo(null);
                window.setVisible(true);
                gamePanel.setUpGame();
                gamePanel.startGameThread();

                // Uncomment the line below to call the parse method
                //parser.parseGameConfig();  // Call your parse method

                } catch (FileNotFoundException e) {
                System.out.println("File not found: " + inputFilePath);
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No input file provided.");
        }
        

        
    }
    public static int roundToNearestFive(int value) {
        return Math.round(value / 5.0f) * 5;
    }
}
