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
import javax.swing.text.PlainDocument;

import java.util.*;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;


// MAIN GAME LOGIC
public class game extends JPanel implements KeyListener, MouseListener
{
    private int dimensionX, dimensionY;
    private ArrayList<tile> world_layout = new ArrayList<>();
    private levelLoader Levels;

    private player Player;
    private ArrayList<enemy> enemies;
    private ArrayList<Integer> adjacentTiles;
    private int points = 0;

    private Point mouse;
    private int mouseX, mouseY, mousePosition;

    public game(){
        this.dimensionX = 11;
        this.dimensionY = 7;
        this.Levels = new levelLoader(dimensionX, dimensionY);
        this.Player = new player(35, dimensionX, dimensionY);

        this.mousePosition = mouseIndex();
    }
    public game(int dX, int dY){
        this.dimensionX = dX;
        this.dimensionY = dY;
        this.Levels = new levelLoader(dimensionX, dimensionY);
        this.Player = new player(35, dX, dY);
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
        /* if(Levels.tileValueEqual(Player.getPosition(), tile_type.DOOR)){
            door_tile D = (door_tile)world_layout.get(Player.getPosition()); */

        if(Levels.tileValueEqual(mousePosition, tile_type.DOOR)){
                door_tile D = (door_tile)world_layout.get(mousePosition);
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

    public ArrayList<Integer> getFullAdjacentTiles(int index){
        ArrayList<Integer> tiles = new ArrayList<Integer>();

        // CHECK TOP LEFT OPEN
        if((index - dimensionY - 1) >= 0 && (index - dimensionY - 1) % dimensionY >= 0 && (index - dimensionY - 1) % dimensionY >= 0 && (index - dimensionY - 1) % dimensionX != dimensionX-1){
            tiles.add(index - dimensionX - 1); 
        }/*  else {
            tiles.add(-1);
        } */

        // CHECK IF TOP MIDDLE OPEN
        if((index - dimensionX) >= 0 && (index - dimensionX) % dimensionX >= 0){
            tiles.add(index - dimensionX); 
        }/*  else {
            tiles.add(-1);
        } */

        // CHECK IF TOP RIGHT OPEN
        if((index - dimensionX + 1) >= 0 && (index - dimensionX + 1) % dimensionX >= 0 && (index - dimensionX + 1) % dimensionX <= dimensionX-1 && (index - dimensionX + 1) % dimensionX != 0){
            tiles.add(index - dimensionX + 1); 
        }/*  else {
            tiles.add(-1);
        } */

        // CHECK IF MIDDLE LEFT OPEN
        if((index - 1) % dimensionX >= 0 && (index - 1) % dimensionX != dimensionX-1){
            tiles.add(index - 1); 
        }/*  else {
            tiles.add(-1);
        } */

        //tiles.add(Player.getPosition()); 

        // CHECK IF MIDDLE RIGHT OPEN
        if((index + 1) % dimensionX <= dimensionX-1 && (index + 1) % dimensionX != 0){
            tiles.add(index + 1); 
        }/*  else {
            tiles.add(-1);
        } */

        // CHECK IF BOTTOM LEFT OPEN
        if((index + dimensionX - 1) < (dimensionX*dimensionY) && (index + dimensionX - 1) % dimensionX >= 0 && (index + dimensionX - 1) % dimensionX != dimensionX-1){
            tiles.add(index + dimensionX - 1); 
        }/*  else {
            tiles.add(-1);
        } */

        // CHECK IF BOTTOM MIDDLE OPEN
        if((index + dimensionX) < (dimensionX*dimensionY)){
            tiles.add(index + dimensionX); 
        }/*  else {
            tiles.add(-1);
        } */

        // CHECK IF BOTTOM RIGHT OPEN
        if((index + dimensionX + 1) < (dimensionX*dimensionY) && (index + dimensionX + 1) % dimensionX >= 0 && (index + dimensionX + 1) % dimensionX != 0){
            tiles.add(index + dimensionX + 1); 
        }/*  else {
            tiles.add(-1);
        } */
        
        /* if(tiles.get(0) == -1 && tiles.get(2) == -1){       // UPPER BORDER

            for(int i = 0; i < 3; i++) {
                tiles.remove(tiles.get(0));
            }

            tiles.add(tiles.get(3) + dimensionX);
            tiles.add(tiles.get(4) + dimensionX);
            tiles.add(tiles.get(5) + dimensionX);
        }
        else if(tiles.get(6) == -1 && tiles.get(8) == -1){      // BOTTOM BORDER

            for(int i = 0; i < 3; i++) {
                tiles.remove(tiles.get(6));
            }

            tiles.add(tiles.get(0) - dimensionX);
            tiles.add(tiles.get(1) - dimensionX);
            tiles.add(tiles.get(2) - dimensionX);
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
        } */

        return tiles;
    }

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

        if(Levels.getEnemyPosition().contains(Player.getPosition())){
            Player.changeHealth(-1);
        }
            /* if(!Player.getOnEnemy()){
                Player.changeHealth(-1);
                Player.setOnEnemy(true);
            }
            else {
                Player.setOnEnemy(false);
            }
        }
        else {
            Player.setOnEnemy(false);
        } */

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

        // ENEMY MOVEMENT
        for(enemy currEnemy : enemies){
            currEnemy.move(getAdjacentTiles(currEnemy.getPosition()), Player.getPosition());
            //currEnemy.move(getAdjacentTiles(currEnemy.getPosition()), mousePosition);
        }

        adjacentTiles = getAdjacentTiles(Player.getPosition());

        // PAINTS LEVEL, ENEMIES, AND PLAYER
        for(int i = 0; i < dimensionY; i++){
            for(int j = 0; j < dimensionX; j++){
                curr = world_layout.get(count);;
                g2.setColor(curr.getColor());

                /* if(count == mouseIndex()) {
                    g2.setColor(g2.getColor().darker());
                } */
                if(selected.size() > 0) {
                    if(selected.get(0) == count && !Levels.tileValueEqual(count, tile_type.WALL)) g2.setColor(Color.CYAN);
                    else if(count == mouseIndex() && !Levels.tileValueEqual(count, tile_type.WALL)) { g2.setColor(Color.ORANGE); }
                }
                else if(count == mouseIndex()) {
                    g2.setColor(g2.getColor().darker());
                }
                /* if(selected.contains(count) && count == Player.getPosition()) {
                    g2.setColor(Color.CYAN);
                } */

                curr.paint(g2, x, y, 90);

                for(enemy e : enemies){
                    if(e.getPosition() == count){
                        e.paint(g2, x, y);
                    }
                }

                if(Player.getPosition() == count && Player.getHealth() > 0){
                    /* if(Levels.getEnemyPosition().contains(Player.getPosition())){
                        Player.changeHealth(-1);
                    } */
                    
                    Player.paint(g2, x, y);
                }

                x += 100;
                count++;
            }
            x = 50;
            y += 95;
        }

        if(Levels.getEnemyPosition().contains(Player.getPosition())){
            if(Player.getOnEnemy() == 3) Player.changeHealth(-1);
            Player.setOnEnemy(-1);
        }
        else {
            Player.setOnEnemy();
        }

        if(Levels.tileValueEqual(Player.getPosition(), tile_type.SHOP)){
            Player.changeHealth(1);
        }

        // PAINTS PLAYER HEALTH BAR AND SCRATCH ATTACKS
        if(Player.getHealth() > 0){
            Player.paintHealth(g2);
            Player.paint_scratch(g2);
        }

        // PAINTS EXP BAR
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

    public int mouseIndex(){
        this.mouse = MouseInfo.getPointerInfo().getLocation();
        this.mouseX = (int)mouse.getX();
        this.mouseY = (int)mouse.getY();
        int index = (int) (Math.min(Math.max(0,Math.ceil((mouseX - 410)/100.0)), 10) + Math.min(Math.max(0,Math.ceil((mouseY - 240)/100.0))*11, 66));

        if(index % 11 < Player.getPosition() % 11){
            Player.faceLeft();
        }
        else if(index % 11 > Player.getPosition() % 11){
            Player.faceRight();
        }

        return index;
    }

    private ArrayList<Integer> selected = new ArrayList<Integer>();

    @Override
    public void mouseClicked(MouseEvent e) {}   
    @Override
    public void mousePressed(MouseEvent e) {
        mousePosition = mouseIndex();
        ArrayList<Integer> spaces;

        selected.add(mouseIndex());

        //System.out.println("mouse: " + mousePosition);
        
        if(Player.getHealth() > 0){
            if(Levels.getEnemyPosition().contains(mousePosition)){
                Iterator<enemy> eIterator = enemies.iterator();
                Player.attack(true);

                while(eIterator.hasNext()){
                    enemy currEnemy = eIterator.next();
                    //System.out.println("enemy: " + currEnemy.getPosition());

                    if(currEnemy.getPosition() == mousePosition){
                        spaces = getAdjacentTiles(currEnemy.getPosition());
                        Player.setPosition(spaces.get((int)Math.random() * spaces.size()));

                        currEnemy.changeHealth(-1);
                        if(currEnemy.getHealth() <= 0){
                            Player.changeEXP(currEnemy.getEXP());
                            eIterator.remove();    
                        }
                    }
                }
                repaint();
            }
            /* else if(Levels.tileValueEqual(mousePosition, tile_type.DOOR)){
                changeRoom();
            }
            else if(!Levels.tileValueEqual(mousePosition, tile_type.WALL)){
                Player.setPosition(mousePosition);
            } */
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        Player.attack(false);
        
        selected.add(mouseIndex());
        if(Levels.tileValueEqual(mousePosition, tile_type.DOOR)){
            changeRoom();
        }
        else if(!Levels.tileValueEqual(selected.get(selected.size()-1), tile_type.WALL) && !Levels.getEnemyPosition().contains(selected.get(selected.size()-1))){
            Player.setPosition(selected.get(selected.size()-1));
        }
        else if(!Levels.tileValueEqual(mousePosition, tile_type.WALL)){
            Player.setPosition(mousePosition);
        }
        
        repaint();
        selected.clear();
    }
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    
}

