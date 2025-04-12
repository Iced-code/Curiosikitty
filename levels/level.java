package levels;
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

import tiles.*;
import enemy.*;

import java.util.*;

public abstract class level {

    private String[] level;             
    private ArrayList<tile> levelLayout;

    private int levelID;
    int length, width;
    private int player_spawn;

    public level(int id){
        this.levelID = id;
        this.length = 11;
        this.width = 7;
        this.levelLayout = new ArrayList<tile>();
    }

    public abstract String[] stringLevel();

    public ArrayList<tile> setLevelLayout(String[] level, HashMap<Integer, tile> placements){
        for(String tile : level){
            switch (tile) {
                case "L":
                    levelLayout.add(new floor_tile());
                    break;
                case "X":
                    levelLayout.add(new wall_tile());
                    break;
                case "D":
                    levelLayout.add(new door_tile(-1,-1));
                    break;
                case "S":
                    levelLayout.add(new shop_tile());
                    break;
                case "B":
                    levelLayout.add(new battle_tile());
                    break;
                default:
                    levelLayout.add(new floor_tile());
                    break;
            }
        }
        
        for(Integer i : placements.keySet()){
                levelLayout.set(i, placements.get(i));
        }

       return levelLayout;
    }

    public ArrayList<tile> getLevelLayout(){
        return levelLayout;
    }

    public abstract void setLevelPlacements();
    public abstract HashMap<Integer, tile> getPlacements();

    public int getLevelID(){
        return levelID;
    }

    public String[] getLevel(){
        return level;
    }

    public void setPlayerSpawn(int playerSpawn){
        this.player_spawn = playerSpawn;
    }
    public int getPlayerSpawn(){
        return player_spawn;
    }

    @Override
    public String toString(){
        String result = "";

        for(int i = 0; i < length*width; i++){
            if(i > 0 && i % length == 0){
                result = result + "\n";
            }

            result = result + stringLevel()[i] + " ";
            //result = result + currWorld[i] + " ";
        }

        return result;
    }
}

