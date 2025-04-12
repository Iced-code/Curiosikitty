package tiles;
import java.awt.*;
import java.util.*;


public class floor_tile extends tile {
        
    tile_type tileType;
    //Color color;

    public floor_tile(){
        super(tile_type.FLOOR);
        //this.color = new Color(235, 215,210);
    }

    /* public Color getColor() {
        return color;
    } */

    /* public void paint(Graphics g, int x, int y, int side_length){
        g.drawRect(x, y, (side_length*2) + 13, (side_length*2) + 13);
        //g.drawRect(x, y, (side_length*10) + 10, (side_length*10) + 10);
    } */

    @Override
    public String toString(){
        return "L";
    }
}
