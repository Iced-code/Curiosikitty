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

public class level3 extends level {

    private HashMap<Integer, tile> levelPlacements;

    public level3(){
        super(3);

        this.levelPlacements = new HashMap<Integer, tile>();
        setLevelPlacements();
        setLevelLayout(stringLevel(), getPlacements());
    }

    public String[] stringLevel() {
        String[] level = {  "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X",
                            "X", "X", "B", "L", "X", "X", "X", "X", "X", "X", "X",
                            "X", "X", "L", "L", "L", "X", "X", "X", "L", "L", "X",
                            "X", "L", "L", "L", "L", "L", "L", "L", "L", "L", "D",
                            "X", "L", "L", "L", "L", "L", "L", "L", "L", "L", "D",
                            "X", "B", "L", "L", "L", "X", "X", "B", "L", "X", "X",
                            "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X" };
        return level;
    }

    public void setLevelPlacements() {
        // ENEMIES
        this.levelPlacements.put(13, new battle_tile(enemy_type.FISH));
        this.levelPlacements.put(56, new battle_tile(enemy_type.FISH));
        this.levelPlacements.put(62, new battle_tile(enemy_type.FISH));

        // TO ROOM 1
        this.levelPlacements.put(43, new door_tile(1, 33));
        this.levelPlacements.put(54, new door_tile(1, 44));
    }
    public HashMap<Integer, tile> getPlacements(){
        return levelPlacements;
    }
}

