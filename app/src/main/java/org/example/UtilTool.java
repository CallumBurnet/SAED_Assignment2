package org.example;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
public class UtilTool {
    public int[] size;
    public int[] start;
    public int[] goal;
    public List<Obstacle> obstacles = new ArrayList<>();
    public List<Item> items = new ArrayList<>();
    public List<String> plugins = new ArrayList<>();
    public List<String> scripts = new ArrayList<>();

    // Nested classes for obstacles and items
    public static class Obstacle {
        public List<int[]> locations = new ArrayList<>();
        public String requiredItem;
    }

    public static class Item {
        public String name;
        public List<int[]> locations = new ArrayList<>();
        public String message;
    }
    public BufferedImage scaledImage(BufferedImage original, int width, int height){
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height,null);
        g2.dispose();
        return scaledImage;
    }
}
