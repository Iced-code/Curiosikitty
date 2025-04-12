import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.MalformedURLException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.util.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;


// MAIN PROGRAM EXECUTER
public class world extends game {
    public static void main(String[] args) {

        //makes the game window
        JFrame frame = new JFrame("World");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(1210, 805);
        frame.setLocation(250, 50);
        frame.setLayout(null);

        //makes the game
        game myWorld = new game(11,7);
        myWorld.makeWorld();
        
        myWorld.setSize(1210, 805);
        myWorld.setLocation(0, 0);
        frame.getContentPane().add(myWorld);

        frame.setVisible(true);

        frame.addKeyListener(myWorld);

        System.out.println(myWorld);
    }
}
