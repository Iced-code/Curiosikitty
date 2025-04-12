package levels;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.LDAPCertStoreParameters;
import java.awt.event.*;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import tiles.*;
import enemy.*;

import java.util.*;

public class level0 extends level {

    private HashMap<Integer, tile> levelPlacements;

    public level0(){
        super(0);

        this.levelPlacements = new HashMap<Integer, tile>();
        setLevelPlacements();
        setLevelLayout(stringLevel(), getPlacements());
        //setPlayerSpawn(52);
    }

    public String[] stringLevel() {
        String[] level = {  "L", "L", "D", "L", "L", "L", "L", "L", "L", "L", "L",
                            "L", "L", "L", "L", "L", "L", "L", "L", "L", "L", "L",
                            "L", "L", "L", "L", "L", "L", "L", "B", "X", "L", "L",
                            "L", "L", "L", "L", "L", "L", "L", "X", "L", "L", "X",
                            "X", "L", "B", "L", "L", "L", "L", "L", "L", "L", "X",
                            "X", "L", "L", "L", "L", "L", "L", "L", "L", "L", "L",
                            "X", "S", "L", "L", "L", "L", "L", "L", "L", "L", "L" };
        return level;
    }

    public void setLevelPlacements() {
        // ENEMIES
        this.levelPlacements.put(29, new battle_tile(enemy_type.SAUCEY));
        this.levelPlacements.put(46, new battle_tile(enemy_type.FISH));

        // TO ROOM 1
        this.levelPlacements.put(2, new door_tile(1, 70));
    }
    public HashMap<Integer, tile> getPlacements(){
        return levelPlacements;
    }
}

