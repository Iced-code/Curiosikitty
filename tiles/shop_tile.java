package tiles;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

import java.util.*;

public class shop_tile extends tile {
        
    tile_type tileType;
    Color color;

    public shop_tile(){
        super(tile_type.SHOP);
        this.color = Color.ORANGE;
    }

    public Color getColor() {
        return color;
    }

    /* public void paint(Graphics g, int x, int y, int side_length){
        g.drawRect(x, y, (side_length*10) + 10, (side_length*10) + 10);
    } */

    @Override
    public String toString(){
        return "S";
    }
}
