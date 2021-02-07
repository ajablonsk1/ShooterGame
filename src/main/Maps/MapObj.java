package main.Maps;

import main.Graphics.ImageHandler;
import main.Maps.Blocks.Block;
import main.Maps.Blocks.BlockObj;
import main.Util.Collisions;
import main.Util.Vector2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MapObj extends Map {

    public static HashMap<String, Block> map_blocks;

    public MapObj(String data, ImageHandler imageHandler, int width, int height, int tileHeight, int tileWidth, int mapColumns){
        Block tempBlock;
        map_blocks = new HashMap<String, Block>();

        String[] block = data.split(",");

        for(int i = 0; i < (width*height); i++){
            int temp = Integer.parseInt(block[i].replaceAll("\\s+", ""));
            if(temp != 0){
                tempBlock = new BlockObj(imageHandler.getImage((int)((temp - 1) % mapColumns), (int)((temp - 1) / mapColumns)), new Vector2D((int) ((i % width) * tileWidth), (int) ((i / width)*tileHeight)), tileWidth, tileHeight);
                map_blocks.put((int) (i % 20) + "," + ((int) (i / 20)), tempBlock);
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        for(Block block: map_blocks.values()){
            block.render(g);
        }
    }
}
