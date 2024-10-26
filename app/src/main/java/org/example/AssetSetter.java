package org.example;

import org.example.object.Sword;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }
    public void setObject(){
        gp.obj[0] = new Sword();
        gp.obj[0].x =  6* gp.tileSize;
        gp.obj[0].y =  6 *gp.tileSize;
    }
}
