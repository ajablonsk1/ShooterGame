package main.Maps.Blocks;

import main.Util.Collisions;
import main.Util.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BlockObj extends Block {


    public BlockObj(BufferedImage image, Vector2D position, int w, int h) {
        super(image, position, w, h);
        collision = new Collisions(position, (float) w, (float) h);
    }

    @Override
    public boolean update(Collisions collisions) {
        return true;
    }

    public void render(Graphics2D graphics2D){
        super.render(graphics2D);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawRect((int) pos.x, (int) pos.y, width, height);
    }

}
