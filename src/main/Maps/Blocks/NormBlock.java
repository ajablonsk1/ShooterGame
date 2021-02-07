package main.Maps.Blocks;

import main.Util.Collisions;
import main.Util.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NormBlock extends Block {


    public NormBlock(BufferedImage image, Vector2D position, int w, int h) {
        super(image, position, w, h);
    }

    @Override
    public boolean update(Collisions collisions) {
        return false;
    }

    public void render(Graphics2D graphics2D){
        super.render(graphics2D);
    }

    public Vector2D getPosition(){
        return this.pos;
    }

    public BufferedImage getImage(){
        return this.image;
    }
}
