package enemy;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import javax.imageio.ImageIO;

public class saucey_enemy {

    private int worldSizeX, worldSizeY;
    private int position;
    private int x, y;

    private int maxHealth = 0;
    private int health = 0;
    private int expDrop = 0;
    private enemy_type enemy;

    private String[] textures = {"assets/sprites/saucey.png", "assets/sprites/knija.png", "assets/sprites/fish.png"};
    private String expShard = "assets/sprites/gem.bmp";
    private BufferedImage image;

    // ENEMY
    public saucey_enemy(int pos, int worldSizeX, int worldSizeY, enemy_type enemyType){
        this.worldSizeX = worldSizeX;
        this.worldSizeY = worldSizeY;
        this.position = pos;
        this.enemy = enemyType;

        File file = new File(setEnemy(enemy));
        
        try {
            this.image = ImageIO.read(file);
        } catch (Exception e){}
    }

    // ENEMY TYPE SETTING/GETTING
    public String setEnemy(enemy_type enemyType){
        String fpath;

        switch (enemyType) {
            case SAUCEY:
                this.maxHealth = 3;
                this.health = 3;
                this.expDrop = 5;
                fpath = textures[0];
                break;
            case FISH:
                this.maxHealth = 5;
                this.health = 5;
                this.expDrop = 8;
                fpath = textures[2];
                break;
            default:
                fpath = textures[0];
                break;
        }

        return fpath;
    }
    public enemy_type getType(){
        return enemy;
    }

    // ENEMY POSITION
    public int getPosition(){
        return position;
    }
    public void setPosition(int aPos){
        position = aPos;
    }

    // ENEMY MOVEMENT
    public void moveRight(){
        position++;
    }
    public void moveLeft(){
        position--;
    }
    public void moveUp(){
        position -= worldSizeY;
    }
    public void moveDown(){
        position += worldSizeY;
    }
    public void move(ArrayList<Integer> positions, int playerPos){
        int weight = 4;

        switch (enemy) {
            case SAUCEY:
                weight = 4;
                break;
            case FISH:
                weight = 6;
                break;
            default:
                weight = 4;
                break;
        }
        
        if((int)(Math.random() * weight) == 2){
            /* if(enemy.equals(enemy_type.FISH)){
                positions.sort(null);
                if(playerPos < position){
                    position = positions.get(0);
                }
                else if(playerPos > position){
                    position = positions.get(positions.size()-1);
                }
                else {
                    position = positions.get((int)(Math.random() * positions.size()));
                }
            }
            else {
                position = positions.get((int)(Math.random() * positions.size()));
            } */
            position = positions.get((int)(Math.random() * positions.size()));
        }
    }

    // ENEMY HEALTH
    public int getHealth(){
        return health;
    }
    public void changeHealth(int amount){
        if(health > 0){
            health += amount;
        }
        
        if(health <= 0){
            File file = new File(expShard);
        
            try {
                this.image = ImageIO.read(file);
            } catch (Exception e){}
        }
    }
    
    // ENEMY EXP DROPS
    public int getEXP(){
        return expDrop;
    }
    public void setEXP(int amount){
        expDrop = amount;
    }

    // PAINTS ENEMY HEALTH BAR
    public void paintHealth(Graphics g, int x, int y){
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x, y-20, 90, 20);

        //g.setColor(Color.GREEN);
        g.setColor(new Color(Math.max(255, 255-(health*75)), Math.min(255, health*75), 0));
        g.fillRect(x, y-20, Math.min(90, 30*health), 20);
    }

    // PAINTS ENEMY
    public void paint(Graphics g, int x, int y){
        Graphics2D g2 = (Graphics2D) g;

        this.x = x;
        this.y = y;

        if(image != null){
            g2.drawImage(image, x-5, y-5, null);
        }
        else {
            g2.setColor(Color.RED);
            g2.fillOval(x+5, y+5, (worldSizeX*10), (worldSizeY*10));
        }

        if(health < maxHealth) paintHealth(g2, x, y);
    }
}
