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

import enemy.*;
import tiles.*;

import java.util.*;

public class level1 extends level {

    private HashMap<Integer, tile> levelPlacements;

    public level1(){
        super(1);

        this.levelPlacements = new HashMap<Integer, tile>();
        setLevelPlacements();
        setLevelLayout(stringLevel(), getPlacements());
    }

    public String[] stringLevel() {
        String[] level = {  "X", "X", "X", "X", "X", "X", "X", "X", "L", "L", "X",
                            "X", "X", "L", "L", "L", "B", "L", "X", "L", "B", "X",
                            "X", "X", "L", "L", "L", "L", "B", "X", "L", "L", "D",
                            "D", "L", "L", "L", "L", "L", "L", "D", "L", "L", "D",
                            "D", "L", "L", "L", "L", "L", "L", "D", "L", "L", "X",
                            "X", "L", "B", "L", "L", "L", "X", "X", "L", "L", "X",
                            "X", "X", "X", "X", "D", "D", "X", "X", "L", "B", "X" };
        return level;
    }

    public void setLevelPlacements() {
        // TO ROOM 0
        this.levelPlacements.put(70, new door_tile(0, 2));
        this.levelPlacements.put(71, new door_tile(0, 2));

        // TO ROOM 1
        this.levelPlacements.put(32, new door_tile(2, 33));
        this.levelPlacements.put(43, new door_tile(2, 44));
    }
    public HashMap<Integer, tile> getPlacements(){
        return levelPlacements;
    }
}

