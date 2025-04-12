package tiles;
import java.awt.*;
import java.util.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;



public class wall_tile extends tile {
        
    tile_type tileType;
    Color color;
    private BufferedImage image;

    public wall_tile(){
        super(tile_type.WALL);
        this.color = new Color(8, 25, 15);   
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString(){
        return "X";
    }
}
