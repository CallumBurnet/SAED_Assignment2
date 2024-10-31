package org.example;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Locale;

public class UtilTool {
    public int[] size;
    public int[] start;
    public int[] goal;
   
    public BufferedImage scaledImage(BufferedImage original, int width, int height){
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height,null);
        g2.dispose();
        return scaledImage;
    }
    public void changeLocale(String languageTag) {
        Locale locale = Locale.forLanguageTag(languageTag);
        Locale.setDefault(locale);
        // Call a method to update all UI text and formats based on the new locale

    }

}
