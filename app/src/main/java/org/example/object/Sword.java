package org.example.object;
import java.io.IOException;

import javax.imageio.ImageIO;
public class Sword extends SuperObject{
    public Sword(){
        name = "Sword";
        type = "Sword";
        try{
        image  = ImageIO.read(getClass().getResourceAsStream("/objects/diamondSword.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
