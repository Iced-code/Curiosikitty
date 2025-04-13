package tiles;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

import javax.imageio.ImageIO;

// TILES ABSTRACT CLASS
public abstract class tile {
        
    private tile_type tileType;
    private Color color = new Color(235, 215,210);;
    private BufferedImage image;

    public tile(tile_type tileType){
        this.tileType = tileType;
    }
    public tile(tile_type tileType, String fPath){
        this.tileType = tileType;

        File file = new File(fPath);
        try {
            this.image = ImageIO.read(file);
        } catch (Exception e){}
    }

    public tile(String tileTypeChar, String fPath){
        switch (tileTypeChar) {
            case "X":
                this.tileType = tile_type.WALL;
                break;
            case "L":
                this.tileType = tile_type.FLOOR;
                break;
            case "S":
                this.tileType = tile_type.SHOP;
                break;
            case "B":
                this.tileType = tile_type.BATTLE;
                break;
            default:
                break;
        }

        File file = new File(fPath);
        try {
            this.image = ImageIO.read(file);
        } catch (Exception e){}
    }
    
    public tile_type getTileType(){
        return tileType;
    }

    public void setTileType(tile_type newType){
        tileType = newType;
    }

    public Color getColor(){
        return color;
    }

    public void paint(Graphics g, int x, int y, int length){
         if(image != null){
            g.drawImage(image, x, y, null);
        }
        else {
            //g.drawRect(x, y, (length*10)+10, (length*10)+10);
            //g.fillRect(x, y, length+10, length+5);
            g.fillRect(x, y, length+10, length+5);
        }
    }

    @Override
    public abstract String toString();
}
