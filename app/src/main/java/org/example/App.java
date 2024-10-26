/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example;
import javax.swing.JFrame;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

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
                
                // Pass the file input stream to the parser
                MySimpleParser parser = new MySimpleParser(inputFileStream);
                int size[] = parser.sizeConfig();
                int start[] = parser.startConfig();
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

                
                System.out.println("Parsed successfully.");
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
