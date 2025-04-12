package tiles;
import java.awt.*;
import java.util.*;


// DOOR TILE
public class door_tile extends tile {
        
    tile_type tileType;
    int toRoom;
    int playerSpawn;

    public door_tile(int toRoom, int playerSpawn){
        super(tile_type.DOOR);
        this.toRoom = toRoom;
        this.playerSpawn = playerSpawn;
    }

    public void setNextRoom(int room) {
        toRoom = room;
    }
    public int getNextRoom() {
        return toRoom;
    }

    public void setPlayerRoomSpawn(int spawn) {
        playerSpawn = spawn;
    }
    public int getPlayerRoomSpawn() {
        return playerSpawn;
    }

    @Override
    public String toString(){
        return "D";
    }
}
