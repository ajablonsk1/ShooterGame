package main.Maps;

import main.Graphics.ImageHandler;
import main.Maps.Blocks.Block;
import main.Maps.Blocks.NormBlock;
import main.Util.Vector2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class MapNorm extends Map {

    private  ArrayList<Block> blocks;

    public MapNorm(String data, ImageHandler imageHandler, int width, int height, int tileHeight, int tileWidth, int mapColumns){
        blocks = new ArrayList<>();

        String[] block = data.split(",");


        for(int i = 0; i < (width*height); i++){
            int temp = Integer.parseInt(block[i].replaceAll("\\s+", ""));
            if(temp != 0){
                NormBlock normBlock = new NormBlock(imageHandler.getImage((int) ((temp - 1) % mapColumns), (int) ((temp-1) / mapColumns)), new Vector2D((int) (i % width) * tileWidth, (int) (i / width)*tileHeight), tileWidth, tileHeight);
                blocks.add(normBlock);
            }
        }
    }

    @Override
    public void render(Graphics2D graphics2D) {
        for(int i = 0; i < blocks.size(); i++){
            blocks.get(i).render(graphics2D);
        }
    }
}
