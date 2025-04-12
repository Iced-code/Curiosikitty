package tiles;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

import java.util.*;

import enemy.*;

public class battle_tile extends tile {
        
    private tile_type tileType;
    private enemy_type enemyType;
    
    public battle_tile(){
        super(tile_type.BATTLE);
        this.enemyType = enemy_type.SAUCEY;
    }
    public battle_tile(enemy_type eType){
        super(tile_type.BATTLE);
        this.enemyType = eType;
    }

    public void setEnemyType(enemy_type eType){
        enemyType = eType;
    }
    public enemy_type getEnemyType(){
        return enemyType;
    }

    /* public void paint(Graphics g, int x, int y, int side_length){
        g.drawRect(x, y, (side_length*10) + 10, (side_length*10) + 10);
    } */

    @Override
    public String toString(){
        return "B";
    }
}
