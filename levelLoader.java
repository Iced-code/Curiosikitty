
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

import java.net.MalformedURLException;
import java.net.URL;
import java.awt.event.*;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import enemy.enemy_type;
import levels.*;
import tiles.*;

import java.util.*;

public class levelLoader {

    private int length;
    private int width;

    private ArrayList<level> levels;
    private level currLevel;
    private ArrayList<tile> currLevelLayout;

    private int worldID;
    private int player_spawn;

    private ArrayList<enemy> enemies;


    // HOLDS AND LOADS GAME LEVELS
    public levelLoader(int length, int width){
        this.length = length;
        this.width = width;

        this.levels = new ArrayList<level>();
        addLevels();

        this.enemies = new ArrayList<enemy>();

        setWorld(0);
    }
    public levelLoader(int length, int width, int worldID){
        this.length = length;
        this.width = width;

        this.levels = new ArrayList<level>();
        addLevels();

        this.enemies = new ArrayList<enemy>();

        setWorld(worldID);   
    }

    // LOADS ALL GAME LEVELS
    private void addLevels(){
        levels.add(new level0());
        levels.add(new level1());
        levels.add(new level2());
    }

    // CURRENT LEVEL LOADING
    public void setWorld(int worldID){
        this.worldID = worldID;
        this.currLevel = levels.get(worldID);
        this.currLevelLayout = currLevel.getLevelLayout();
        enemies.clear();
        setEnemies();
    }
    public ArrayList<tile> getWorldLayout(){
        return currLevelLayout;
    }
    public int getWorldID(){
        return worldID;
    }
    public void setLevel(int ID){
        currLevel = levels.get(ID);
    }
    public level getLevel(){
        return currLevel;
    }

    public int getPlayerSpawn(){
        return player_spawn;
    }

    // COMPARES TYPE OF TILE AT SPECIFIED INDEX WITH SPECIFIED TILE TYPE
    public boolean tileValueEqual(int tileIndex, tile_type tileType){
        return currLevelLayout.get(tileIndex).getTileType().equals(tileType);
    }

    // LEVEL'S ENEMIES
    public void setEnemies(){
        for(int i = 0; i < currLevelLayout.size(); i++){
            if(tileValueEqual(i, tile_type.BATTLE)){
                battle_tile curr = (battle_tile)currLevelLayout.get(i);
                enemies.add(new enemy(i, length, width, curr.getEnemyType()));
            }
        }
    }
    public ArrayList<enemy> getEnemies(){
        return enemies;
    }

    public ArrayList<Integer> getEnemyPosition(){
        ArrayList<Integer> enemyPositions = new ArrayList<Integer>();
        for(enemy curr : enemies){
            enemyPositions.add(curr.getPosition());
        }
        return enemyPositions;
    }

    @Override
    public String toString(){
        String result = "";

        for(int i = 0; i < length*width; i++){
            if(i > 0 && i % length == 0){
                result = result + "\n";
            }

            result = result + currLevel.stringLevel()[i] + " ";
        }

        return result;
    }
}

