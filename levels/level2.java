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

import java.util.*;

public class level2 extends level {

    private HashMap<Integer, tile> levelPlacements;

    public level2(){
        super(2);

        this.levelPlacements = new HashMap<Integer, tile>();
        setLevelPlacements();
        setLevelLayout(stringLevel(), getPlacements());
    }

    public String[] stringLevel() {
        String[] level = {  "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X",
                            "X", "X", "L", "L", "L", "L", "L", "L", "L", "X", "X",
                            "X", "X", "L", "L", "L", "L", "L", "X", "L", "L", "X",
                            "D", "L", "L", "L", "L", "L", "L", "L", "L", "L", "X",
                            "D", "L", "L", "L", "L", "L", "L", "B", "L", "L", "X",
                            "X", "L", "B", "L", "L", "L", "X", "L", "L", "X", "X",
                            "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X" };
        return level;
    }

    public void setLevelPlacements() {
        this.levelPlacements.put(33, new door_tile(1, 32));
        this.levelPlacements.put(44, new door_tile(1, 43));
    }
    public HashMap<Integer, tile> getPlacements(){
        return levelPlacements;
    }
}

