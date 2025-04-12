import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

import javax.imageio.ImageIO;

// PLAYER CHARACTER (KITTY)
public class player {

    private int worldSizeX, worldSizeY;
    private int position;
    private int x, y;
    private boolean facingRight = true;

    private int maxHealth,health;
    private int exp = 0;
    private boolean attack = false;
    private boolean onEnemy = false;

    private ArrayList<String> inventory;
    private int steps = 0;

    private String[] cat_textures = {"assets/sprites/cat_r.png", "assets/sprites/cat_l.png"};
    private String[] scratch_textures = {"assets/sprites/scratch_r.png", "assets/sprites/scratch_l.png"}; 
    private BufferedImage cat_rightImage, cat_leftImage, image_scratch;

    public player(int pos, int worldSizeX, int worldSizeY){
        this.worldSizeX = worldSizeX;
        this.worldSizeY = worldSizeY;
        this.position = pos;
        this.maxHealth = 5;
        this.health = 5;
        this.inventory = new ArrayList<String>();
        
        try {
            this.cat_rightImage = ImageIO.read(new File(cat_textures[0]));
            this.cat_leftImage = ImageIO.read(new File(cat_textures[1]));
            this.image_scratch = ImageIO.read(new File(scratch_textures[0]));
        } catch (Exception e){}
    }

    // POSITION
    public int getPosition(){
        return position;
    }
    public void setPosition(int aPos){
        position = aPos;
    }

    // MISCELLANEOUS
    public int getSteps(){
        return steps;
    }
    public ArrayList<String> getInventory(){
        return inventory;
    }

    // PLAYER DIRECTION
    public boolean isFacingRight(){
        return facingRight;
    }
    public void faceRight(){
        if(!facingRight){
            facingRight = true;
            try {
                this.image_scratch = ImageIO.read(new File(scratch_textures[0]));
            } catch (Exception e){}
        }
    }
    public void faceLeft(){
        if(facingRight){
            facingRight = false;
            try {
                this.image_scratch = ImageIO.read(new File(scratch_textures[1]));
            } catch (Exception e){}
        }
    }

    // MOVEMENT
    public void moveRight(){
        if(!facingRight){
            facingRight = true;
            try {
                this.image_scratch = ImageIO.read(new File(scratch_textures[0]));
            } catch (Exception e){}
        }
        else {
            position++;
            steps++;
        }
    }
    public void moveLeft(){
        if(facingRight){
            facingRight = false;
            try {
                this.image_scratch = ImageIO.read(new File(scratch_textures[1]));
            } catch (Exception e){}
        }
        else {
            position--;
            steps++;
        }
    }
    public void moveUp(){
        position -= worldSizeX;
        steps++;
    }
    public void moveDown(){
        position += worldSizeX;
        steps++;
    }

    // PLAYER HEALTH
    public int getHealth(){
        return health;
    }
    public void setHealth(int amount){
        health = amount;
    }
    public void changeHealth(int amount){
        if(health <= maxHealth) {
            health += amount;
        }
        else {
            health = maxHealth;
        }
    }

    // EXP 
    public int getEXP(){
        return exp;
    }
    public void setEXP(int EXP){
        exp = EXP;
    }
    public void changeEXP(int amount){
        exp += amount;
    }

    // ON ENEMY 
    public boolean getOnEnemy(){
        return onEnemy;
    }
    public void setOnEnemy(boolean state){
        onEnemy = state;
    }

    // ATTACK LOGIC
    public boolean istAttacking(){
        return attack;
    }
    public void attack(boolean val){
        attack = val;
    }
    public void attack(){
        attack = !attack;
    }

    //PAINTS KITTY
    public void paint(Graphics g, int x, int y){
        Graphics2D g2 = (Graphics2D) g;

        this.x = x;
        this.y = y;
        
        if(cat_rightImage != null){
            if(facingRight){
                g2.drawImage(cat_rightImage, x-5, y-5, null);
            }
            else if(!facingRight){
                g2.drawImage(cat_leftImage, x-5, y-5, null);
            }
        }
        else {
            g2.setColor(g2.getColor().darker());
            g2.fillOval(x+5, y+5, (worldSizeX*10), (worldSizeY*10));
        }
        //g.fillRect(x, y, (length*10)+10, (length*10)+10);
    }

    // PAINTS HEALTH BAR
    public void paintHealth(Graphics g){
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x, y+95, 90, 20);

        //g.setColor(Color.GREEN);
        g.setColor(new Color(Math.min(225, 255-(health*10)), Math.min(255, health*51), 0));
        g.fillRect(x, y+95, Math.min(90, 18*health), 20);
    }

    // PAINTS SCRATCH ATTACK
    public void paint_scratch(Graphics g){
        Graphics2D g2 = (Graphics2D) g;

        if(attack && facingRight){  // ATTACK UP & RIGHT
            g2.drawImage(image_scratch, x+95, y, null);
            g2.drawImage(image_scratch, x, y-95, null);
        }
        else if(attack && !facingRight){    // ATTACK DOWN & LEFT
            g2.drawImage(image_scratch, x-105, y, null);
            g2.drawImage(image_scratch, x, y+95, null);
        }
    }
}
