package main.Graphics;

import main.Util.Vector2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ImageHandler {

    private  BufferedImage sheet = null;
    private BufferedImage[][] imageArray;
    private final int TILE_SIZE = 64;
    private final int TILE_X = 46;
    private final int TILE_Y = 49;
    public int image_width;
    public int image_height;
    private int numberOfTilesW;
    private int numberOfTilesH;

    public ImageHandler(String file){
        this.image_width = TILE_X;
        this.image_height = TILE_Y;

        System.out.println("Loading: " + file + "...");
        this.sheet = loadFile(file);

        numberOfTilesW = sheet.getWidth() / image_width;
        numberOfTilesH = sheet.getHeight() / image_height;
        loadImageArray();
    }


    public ImageHandler(String file, int width, int height){
        this.image_width = width;
        this.image_height = height;

        System.out.println("Loading: " + file + "...");
        this.sheet = loadFile(file);

        numberOfTilesW = sheet.getWidth() / width;
        numberOfTilesH = sheet.getHeight() / height;
        loadImageArray();
    }

    public void setSize(int width, int height){
        setWidth(width);
        setHeight(height);
    }

    public void setWidth(int width){
        this.image_width = width;
        numberOfTilesW = sheet.getWidth() / width;
    }

    public void setHeight(int height){
        this.image_width = height;
        numberOfTilesH = sheet.getHeight() / height;
    }

    private BufferedImage loadFile(String file){
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
        }
        catch (Exception e){
            System.out.println("Error: Could not load file: " + file);
        }
        return image;
    }

    public void loadImageArray(){
        imageArray = new BufferedImage[numberOfTilesW][numberOfTilesH];

        for(int i = 0; i < numberOfTilesW; i++){
            for (int j = 0; j < numberOfTilesH; j++){
                imageArray[i][j] = getImage(i, j);
            }
        }
    }

    public BufferedImage getSheet(){
        return sheet;
    }

    public BufferedImage getImage(int i, int j){
        return sheet.getSubimage(i*image_width, j *image_height, image_width, image_height);
    }

    public BufferedImage[] getImageArray(int i){
        return imageArray[i];
    }

    public BufferedImage[][] getImageArray2(int i){
        return imageArray;
    }

    public static void drawArray(Graphics2D graphics2D, ArrayList<BufferedImage> images, Vector2D vector2D, int width, int height, int xOffset, int yOffset){
        float x = vector2D.x;
        float y = vector2D.y;

        for(int i = 0; i < images.size(); i++){
            if(images.get(i) != null){
                graphics2D.drawImage(images.get(i), (int) x, (int) y, width, height, null);

                x += xOffset;
                y += yOffset;
            }
        }
    }
}
