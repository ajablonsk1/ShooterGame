package main.Util;

import main.Enemy.Enemy;
import main.Enemy.Solider;
import main.Enemy.Zombie;
import main.Maps.Blocks.Block;
import main.Maps.MapObj;
import main.Players.Player;
import main.PowerUps.PowerUp;
import main.PowerUps.PowerUpsHandler;

import java.util.HashMap;

public class Collisions {

    private Vector2D vector2D;
    private float width;
    private float height;

    private HashMap<String, Block> blocks;

    public Collisions(Vector2D vector2D, float width, float height){
        this.vector2D = vector2D;
        this.width = width;
        this.height = height;
    }

    public void setPosition(Vector2D vector2D){
        this.vector2D = vector2D;
    }

    public void setWidth(float x){
        this.width = x;
    }

    public void setHeight(float x){
        this.height = x;
    }

    public Vector2D getPosition(){
        return this.vector2D;
    }

    public float getWidth(){
        return this.width;
    }

    public float getHeight(){
        return this.height;
    }

    public boolean collision(Collisions box){
        float firstObjX = ((vector2D.x + (width / 2)));
        float firstObjY = ((vector2D.y + (height / 2)));
        float secondObjX = ((box.getPosition().x + (box.getWidth() / 2)));
        float secondObjY = ((box.getPosition().y + (box.getHeight() / 2)));

        return Math.abs(firstObjX - secondObjX) < (this.width / 2) + (box.getWidth() / 2) &&
                Math.abs(firstObjY - secondObjY) < (this.height / 2) + (box.getWidth() / 2);
    }

    public boolean CollisionTile(float x, float y){
        for(int i = 0; i < 4; i ++){

            int TileX = (int) ((vector2D.x + x) + ((i % 2) * width)) / 64;
            int TileY = (int) ((vector2D.y + y) + ((i / 2) * height)) / 64;

            if(MapObj.map_blocks.containsKey(TileX + "," + TileY)){
                return MapObj.map_blocks.get(TileX + "," + TileY).update(this);
            }
        }
        return false;
    }

    public boolean CollisionPlayer(float x, float y, Bullet bullet, Player player){
        Vector2D bulletPos = new Vector2D(bullet.getPosition().x + 2, bullet.getPosition().y + 2);
        if(bulletPos.x > vector2D.x && bulletPos.x < vector2D.x + player.getSizeX() &&
        bulletPos.y > vector2D.y && bulletPos.y < vector2D.y + player.getSizeY()){
            return true;
        }
        return false;
    }

    public boolean CollisionEnemyShot(float x, float y, Bullet bullet, Enemy enemy){
        Vector2D bulletPos = new Vector2D(bullet.getPosition().x + 2, bullet.getPosition().y + 2);
        if(bulletPos.x > vector2D.x && bulletPos.x < vector2D.x + 43 &&
                bulletPos.y > vector2D.y && bulletPos.y < vector2D.y + 43){
            return true;
        }
        return false;
    }

    public PowerUp CollisionPowerUp(float x, float y, Player player){
        for(int i = 0; i < 4; i++){
            Vector2D PlayerPosition = new Vector2D((vector2D.x+x) + ((i % 2) * width), (vector2D.y+y) + ((int) (i / 2) * height));
            int TilePlayerX = (int) PlayerPosition.x / 64;
            int TilePLayerY = (int) PlayerPosition.y / 64;
            if(PowerUpsHandler.powerUps != null) {
                for (PowerUp powerup : PowerUpsHandler.powerUps) {
                    int TileX = (int) ((powerup.getPosition().x) / 64);
                    int TileY = (int) ((powerup.getPosition().y) / 64);
                    if (TilePlayerX == TileX && TilePLayerY == TileY) {
                        PowerUpsHandler.spawnTimes.remove(PowerUpsHandler.powerUps.indexOf(powerup));
                        PowerUpsHandler.powerUps.remove(powerup);
                        return powerup;
                    }
                }
            }
        }
        return null;
    }

    public boolean CollisionEnemyVision(Player player){
        Vector2D PlayerPositionPlus = new Vector2D(player.getPosition().x - 256, player.getPosition().y - 256);
        if(vector2D.x > PlayerPositionPlus.x && vector2D.x < PlayerPositionPlus.x + 512 + player.getSizeX() &&
        vector2D.y > PlayerPositionPlus.y && vector2D.y < PlayerPositionPlus.y + 512 + player.getSizeY()){
            return true;
        }
        return false;
    }

    public boolean CollisionEnemy(Player player, int x, int y){
        Vector2D vector2D1 = new Vector2D(vector2D.x + (width)/2, vector2D.y + (height) / 2);
        if(vector2D1.x > player.getPosition().x && vector2D1.x < player.getPosition().x + player.getSizeX() &&
                vector2D1.y > player.getPosition().y && vector2D1.y < player.getPosition().y + player.getSizeY()) {
            return true;
        }
        return false;
    }

    public boolean CollisionBlockSoliderVision(int TileX, int TileY){
        if(MapObj.map_blocks.containsKey(TileX + "," + TileY)){
            return MapObj.map_blocks.get(TileX + "," + TileY).update(this);
        }
        return false;
    }

    public boolean CollisionSolider(Player player, Solider solider) {
        for (int i = 0; i < 4; i++) {
            Vector2D PlayerPosition = new Vector2D((player.getPosition().x) + ((i % 2) * player.getSizeX()), (player.getPosition().y) + (float) (i / 2) * player.getSizeY());
            int TileX = (int) solider.getPosition().x / 64;
            int TileY = (int) solider.getPosition().y / 64;
            int PlayerTileX = (int) PlayerPosition.x / 64;
            int PlayerTileY = (int) PlayerPosition.y / 64;
            if (solider.currentDirection == 0) {
                for (int j = TileX; j < 20; j++) {
                    if (!CollisionBlockSoliderVision(j, TileY)) {
                        if (j == PlayerTileX && PlayerTileY == TileY) {
                            return true;
                        }
                    } else {
                        break;
                    }
                }
            } else if (solider.currentDirection == 1) {
                for (int j = TileY; j < 12; j++) {
                    if (!CollisionBlockSoliderVision(TileX, j)) {
                        if (j == PlayerTileY && PlayerTileX == TileX) {
                            return true;
                        }
                    } else {
                        break;
                    }
                }
            } else if (solider.currentDirection == 2) {
                for (int j = TileX; j >= 0; j--) {
                    if (!CollisionBlockSoliderVision(j, TileY)) {
                        if (j == PlayerTileX && PlayerTileY == TileY) {
                            return true;
                        }
                    } else {
                        break;
                    }
                }
            } else if (solider.currentDirection == 3) {
                for (int j = TileY; j >= 0; j--) {
                    if (!CollisionBlockSoliderVision(TileX, j)) {
                        if (TileX == PlayerTileX && PlayerTileY == j) {
                            return true;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        return false;
    }

    public boolean CollisionBetweenPlayers(Player player, int x, int y){
        float fx;
        float fy;
        float sx = (player.getPosition().x);
        float sy =  (player.getPosition().y);
        for(int i = 0; i < 4; i ++){
            fx =  (vector2D.x + x) + ((i % 2) * width);
            fy =  (vector2D.y + y) + ((float) (i / 2) * height);
            if(fx >= sx && fx <= sx + player.getSizeX() && fy >= sy && fy <= sy + player.getSizeY()){
                return true;
            }
        }
        return false;
    }
}
