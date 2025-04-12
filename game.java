import tiles.*;
import levels.*;
import enemy.*;


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

import java.util.*;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import java.util.concurrent.TimeUnit;


// MAIN GAME LOGIC
public class game extends JPanel implements KeyListener
{
    private int dimensionX, dimensionY;
    private ArrayList<tile> world_layout = new ArrayList<>();
    private levelLoader Levels;

    private player Player;
    private ArrayList<enemy> enemies;
    private ArrayList<Integer> adjacentTiles;
    private int points = 0;


    public game(){
        this.dimensionX = 11;
        this.dimensionY = 7;
        this.Levels = new levelLoader(dimensionX, dimensionY);
        this.Player = new player(25, dimensionX, dimensionY);
    }
    public game(int dX, int dY){
        this.dimensionX = dX;
        this.dimensionY = dY;
        this.Levels = new levelLoader(dimensionX, dimensionY);
        this.Player = new player(25, dX, dY);
    }

    // GETS LEVEL, PLAYER ADJACENT TILES, AND ENEMIES
    public ArrayList<tile> makeWorld(){
        world_layout = Levels.getWorldLayout();
        
        this.adjacentTiles = getAdjacentTiles(Player.getPosition());

        this.enemies = Levels.getEnemies();

        return world_layout;
    }

    // HANDLES SWITCHING BETWEEN LEVELS
    public void changeRoom(){
        if(Levels.tileValueEqual(Player.getPosition(), tile_type.DOOR)){
            door_tile D = (door_tile)world_layout.get(Player.getPosition());
            Levels.setWorld(D.getNextRoom());
            Player.setPosition(D.getPlayerRoomSpawn());
            makeWorld();
        }
    }

    // GETS OPEN (NON-WALL) TILES ADJACENT TO THE TILE AT THE INPUTTED INDEX
    public ArrayList<Integer> getAdjacentTiles(int index){
        ArrayList<Integer> tiles = new ArrayList<Integer>();

        // CHECK IF LEFT OPEN
        if((index - 1) % dimensionX >= 0 && (index - 1) % dimensionX != dimensionX-1){
            if(Levels.tileValueEqual(index - 1, tile_type.WALL) == false)
                tiles.add(index - 1); 
        }
        // CHECK IF RIGHT OPEN
        if((index + 1) % dimensionX <= dimensionX-1 && (index + 1) % dimensionX != 0){
            if(Levels.tileValueEqual(index + 1, tile_type.WALL) == false)
                tiles.add(index + 1); 
        }

        // CHECK IF UP OPEN
        if((index - dimensionX) >= 0 && (index - dimensionX) % dimensionX >= 0){
            if(Levels.tileValueEqual(index - dimensionX, tile_type.WALL) == false)
                tiles.add(index - dimensionX); 
        }
        // CHECK IF DOWN OPEN
        if((index + dimensionX) < (dimensionX*dimensionY)){
            if(Levels.tileValueEqual(index + dimensionX, tile_type.WALL) == false)
                tiles.add(index + dimensionX); 
        }
        
        return tiles;
    }

    /* public ArrayList<Integer> getFullAdjacentTiles(int index){
        ArrayList<Integer> tiles = new ArrayList<Integer>();

        // CHECK TOP LEFT OPEN
        if((index - dimensionY - 1) >= 0 && (index - dimensionY - 1) % dimensionY >= 0 && (index - dimensionY - 1) % dimensionY >= 0 && (index - dimensionY - 1) % dimensionX != dimensionX-1){
            tiles.add(index - dimension - 1); 
        } else {
            tiles.add(-1);
        }

        // CHECK IF TOP MIDDLE OPEN
        if((index - dimension) >= 0 && (index - dimension) % dimension >= 0){
            tiles.add(index - dimension); 
        } else {
            tiles.add(-1);
        }

        // CHECK IF TOP RIGHT OPEN
        if((index - dimension + 1) >= 0 && (index - dimension + 1) % dimension >= 0 && (index - dimension + 1) % dimension <= dimension-1 && (index - dimension + 1) % dimension != 0){
            tiles.add(index - dimension + 1); 
        } else {
            tiles.add(-1);
        }

        // CHECK IF MIDDLE LEFT OPEN
        if((index - 1) % dimension >= 0 && (index - 1) % dimension != dimension-1){
            tiles.add(index - 1); 
        } else {
            tiles.add(-1);
        }

        tiles.add(Player.getPosition()); 

        // CHECK IF MIDDLE RIGHT OPEN
        if((index + 1) % dimension <= dimension-1 && (index + 1) % dimension != 0){
            tiles.add(index + 1); 
        } else {
            tiles.add(-1);
        }

        // CHECK IF BOTTOM LEFT OPEN
        if((index + dimension - 1) < (dimension*dimension) && (index + dimension - 1) % dimension >= 0 && (index + dimension - 1) % dimension != dimension-1){
            tiles.add(index + dimension - 1); 
        } else {
            tiles.add(-1);
        }

        // CHECK IF BOTTOM MIDDLE OPEN
        if((index + dimension) < (dimension*dimension)){
            tiles.add(index + dimension); 
        } else {
            tiles.add(-1);
        }

        // CHECK IF BOTTOM RIGHT OPEN
        if((index + dimension + 1) < (dimension*dimension) && (index + dimension + 1) % dimension >= 0 && (index + dimension + 1) % dimension != 0){
            tiles.add(index + dimension + 1); 
        } else {
            tiles.add(-1);
        }
        
        if(tiles.get(0) == -1 && tiles.get(2) == -1){       // UPPER BORDER

            for(int i = 0; i < 3; i++) {
                tiles.remove(tiles.get(0));
            }

            tiles.add(tiles.get(3) + dimension);
            tiles.add(tiles.get(4) + dimension);
            tiles.add(tiles.get(5) + dimension);
        }
        else if(tiles.get(6) == -1 && tiles.get(8) == -1){      // BOTTOM BORDER

            for(int i = 0; i < 3; i++) {
                tiles.remove(tiles.get(6));
            }

            tiles.add(tiles.get(0) - dimension);
            tiles.add(tiles.get(1) - dimension);
            tiles.add(tiles.get(2) - dimension);
        }
        else if(tiles.get(0) == -1 && tiles.get(6) == -1){      // LEFT BORDER
            tiles.remove(tiles.get(0));
            tiles.remove(tiles.get(2));
            tiles.remove(tiles.get(4));
            
            tiles.add(tiles.get(1) + 1);
            tiles.add(tiles.get(3) + 1);
            tiles.add(tiles.get(5) + 1);
        }
        else if(tiles.get(2) == -1 && tiles.get(8) == -1){      // RIGHT BORDER
            tiles.remove(tiles.get(2));
            tiles.remove(tiles.get(4));
            tiles.remove(tiles.get(6));

            tiles.add(tiles.get(0) - 1);
            tiles.add(tiles.get(2) - 1);
            tiles.add(tiles.get(4) - 1);
        }

        return tiles;
    } */

    /*
    public boolean pointsAvailable(){
        return pointsAvailable && tileValueEqual(player_position, tile_type.) ; //world_layout.get(curr_index).equals("W");
    } */

    
    /* private void task() {

        for(enemy curr : enemies){
            ArrayList<Integer> enemypos = getAdjacentTiles(curr.getPosition());
            curr.move(enemypos.get((int)(Math.random() * enemypos.size())));
        }
    }
    public void incPoints(){
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = () -> task();
        if(pointsAvailable == false)
            scheduler.scheduleAtFixedRate(task, 0, 2, TimeUnit.SECONDS);
        scheduler.schedule(() -> {
            System.out.println("Timer finished! 5 seconds elapsed.");
            repaint();
        }, 5, TimeUnit.SECONDS);
    } */
    
    // BATTLING ENEMY LOGIC
    public boolean attackEnemy(){
        int enemyCount = enemies.size();

        if(Player.istAttacking() && enemies.size() > 0){
            Iterator<enemy> eIterator = enemies.iterator();

            while(eIterator.hasNext()){
                enemy currEnemy = eIterator.next();
                
                if(adjacentTiles.contains(currEnemy.getPosition())){
                    if( Player.isFacingRight() && (currEnemy.getPosition() == Player.getPosition()+1 || currEnemy.getPosition() == Player.getPosition()-dimensionX) ){
                        currEnemy.changeHealth(-1);
                        if(currEnemy.getHealth() <= 0){
                            //world_layout.set(currEnemy.getPosition(), new floor_tile());
                            Player.changeEXP(currEnemy.getEXP());
                            eIterator.remove();
                        }
                    }
                    else if( !Player.isFacingRight() && (currEnemy.getPosition() == Player.getPosition()-1 || currEnemy.getPosition() == Player.getPosition()+dimensionX) ){
                        currEnemy.changeHealth(-1);
                        if(currEnemy.getHealth() <= 0){
                            //world_layout.set(currEnemy.getPosition(), new floor_tile());
                            Player.changeEXP(currEnemy.getEXP());
                            eIterator.remove();
                        }
                    }
                }
            }
        }

        return enemyCount > enemies.size();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();

        if(c == 'r' || c == 'R'){
            Player.setEXP(0);
            Player.setHealth(5);
            enemies = (ArrayList<enemy>) Levels.getEnemies().clone();
        }

        if(c == 'x' || c == 'X'){
            System.out.println(Levels.getLevel().getPlacements());
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char c = e.getKeyChar();

        // SCRATCH ATTACK
        if(c == ' '){
            Player.attack();
            attackEnemy();
        }


        // MOVE RIGHT
        if(c == 'd' || c == 'D'){
            if(Levels.tileValueEqual(Player.getPosition(), tile_type.DOOR) && !adjacentTiles.contains(Player.getPosition() + 1) && (Player.getPosition() % dimensionX == dimensionX-1) && Player.isFacingRight()){
                changeRoom();
            }
            else if(adjacentTiles.contains(Player.getPosition() + 1)) {
                Player.moveRight();
            }
            Player.faceRight();
        }
        // MOVE LEFT
        if(c == 'a' || c == 'A'){
            if(Levels.tileValueEqual(Player.getPosition(), tile_type.DOOR) && !adjacentTiles.contains(Player.getPosition() - 1) && (Player.getPosition() % dimensionX == 0) && !Player.isFacingRight()/*  || (adjacentTiles.size() == 1 && adjacentTiles.get(0).equals(Player.getPosition()+8)) */){
                changeRoom();
            }
            else if(adjacentTiles.contains(Player.getPosition() - 1)) {
                Player.moveLeft();
            }
            Player.faceLeft();
        }
        // MOVE UP
        if(c == 'w' || c == 'W'){
            if(Levels.tileValueEqual(Player.getPosition(), tile_type.DOOR) && !adjacentTiles.contains(Player.getPosition() - dimensionX) && (Player.getPosition() >= 0 && Player.getPosition() < dimensionY) || (adjacentTiles.size() == 1 && adjacentTiles.contains(Player.getPosition()+dimensionX))){
                changeRoom();
            }
            else if(adjacentTiles.contains(Player.getPosition() - dimensionX)) {
                Player.moveUp();
            }
        }
        // MOVE DOWN
        if(c == 's' || c == 'S'){
            if(Levels.tileValueEqual(Player.getPosition(), tile_type.DOOR) && !adjacentTiles.contains(Player.getPosition() + dimensionX) && (Player.getPosition() >= dimensionX*(dimensionY-1) && Player.getPosition() < (dimensionX*dimensionY)) || (adjacentTiles.size() == 1 && adjacentTiles.contains(Player.getPosition()-dimensionX)) ){
                changeRoom();
            }
            else if(adjacentTiles.contains(Player.getPosition() + dimensionX)) {
                Player.moveDown();
            }
        }

        // HEALTH STATION
        if(Levels.tileValueEqual(Player.getPosition(), tile_type.SHOP)){
            Player.changeHealth(1);
        }

        repaint();
    }
    @Override
    public void keyReleased(KeyEvent e) {
        char c = e.getKeyChar();

        // RESETS ATTACK STATE
        if(Player.istAttacking()){
            Player.attack(false);   
        }

        repaint();
    }

    // PAINTS GAME (KITTY, LEVEL, ENEMIES...)
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;

        //paints background
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, 1200, 805);

        int x = 50;
        int y = 75;
        int count = 0;
        tile curr;

        for(enemy currEnemy : enemies){
            currEnemy.move(getAdjacentTiles(currEnemy.getPosition()), Player.getPosition());
        }

        adjacentTiles = getAdjacentTiles(Player.getPosition());

        //MAP DISPLAY
        for(int i = 0; i < dimensionY; i++){
            for(int j = 0; j < dimensionX; j++){
                curr = world_layout.get(count);;
                g2.setColor(curr.getColor());
                
                //g.drawRect(x+5, y+5, (dimension*10), (dimension*10)); // for 8x8  
                //g.fillRect(x, y, (dimension*10) + 10, (dimension*10) + 10);   // for 8x8 full tile
                //g.fillRect(x, y, (dimension*2) + 13, (dimension*2) + 13);   // for 16x6

                curr.paint(g2, x, y, 90);
                for(enemy e : enemies){
                    if(e.getPosition() == count){
                        e.paint(g2, x, y);
                    }
                }

                if(Player.getPosition() == count && Player.getHealth() > 0){
                    if(Levels.getEnemyPosition().contains(Player.getPosition())){
                        Player.changeHealth(-1);
                    }
                    Player.paint(g2, x, y);
                    
                }

                x += 100; // for 8x8
                //x += 50;    // for 16x6
                count++;
            }
            //x = 150;
            x = 50;
            y += 95;    // for 8x8
            //y += 47;   // for 16x16
        }

        if(Player.getHealth() > 0){
            Player.paintHealth(g2);
            Player.paint_scratch(g2);
        }

        g2.setColor(Color.DARK_GRAY);
        g2.fill3DRect(0, 0, 1200, 50, true);
        g2.setColor(Color.CYAN);
        g2.fillRect(0, 0, Math.min(Player.getEXP()*50, 1200), 50);
        g2.setColor(Color.BLUE);
        g2.fillRect(0, 47, Math.min(Player.getEXP()*50, 1200), 3);


        /* g2.setColor(Color.WHITE);
        g2.setFont(new Font("Verdana", Font.BOLD, 20));
        g2.drawString("Points: " + points, 950, 100);

        if(markTile < 0 && mapDisplay_level > 0){
            g2.drawString("X", 950, 150);
        } */
    }

    @Override
    public String toString(){
        String result = "";

        for(int i = 0; i < dimensionX*dimensionY; i++){
            if(i > 0 && i % dimensionX == 0){
                result = result + "\n";
            }

            result = result + world_layout.get(i).toString() + " ";
        }

        return result;
    }
}

