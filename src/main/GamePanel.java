package main;

import main.Util.KeyHandler;
import main.States.GameStateManager;
import main.Util.MouseHandler;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GamePanel extends JPanel implements Runnable{

    public int width;
    public int height;
    public static int oldFrameCount;

    private Thread thread;
    private boolean running;

    private BufferedImage image;
    private Graphics2D graphics2D;

    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;

    public GameStateManager gameStateManager;

    public GamePanel(int width, int height){
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();
    }

    @Override
    public void addNotify(){
        super.addNotify();

        if(thread == null){
            thread = new Thread(this, "GameThread");
            thread.start();
        }
    }

    public void init(){
        running = true;

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        graphics2D =  (Graphics2D) image.getGraphics();
        graphics2D.setColor(Color.BLACK);
        graphics2D.setFont(this.getFont());
        keyHandler = new KeyHandler(this);
        mouseHandler = new MouseHandler(this);

        gameStateManager = new GameStateManager(this);
    }

    @Override
    public void run() {
        init();

        final double game_update = 60.0;
        final double TBU = 1000000000 / game_update;  // Time Before Update

        final int MUBR = 5; // Must Update Before Render

        double lastUpdateTime = System.nanoTime();
        double lastRenderTime;

        final double TARGET_FPS = 60.0;
        final double TTBR = 1000000000 / TARGET_FPS; // Total Time Before Render

        int frameCount = 0;
        int lastSecondTime = (int) (lastUpdateTime / 1000000000);
        oldFrameCount = 0;

        while(running){

            double now = System.nanoTime();
            int updateCount = 0;
            while((now - lastUpdateTime > TBU) && (updateCount < MUBR)){
                update();
                input(keyHandler, mouseHandler);
                lastUpdateTime += TBU;
                updateCount++;
            };
            if(now - lastUpdateTime > TBU){
                lastUpdateTime = now - TBU;
            }

            input(keyHandler, mouseHandler);
            try {
                render();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (FontFormatException e) {
                e.printStackTrace();
            }
            draw();
            lastRenderTime = now;
            frameCount++;

            int thisSecond = (int) (lastUpdateTime / 1000000000);
            if(thisSecond > lastSecondTime){
                if(frameCount != oldFrameCount){
                    System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
                    oldFrameCount = frameCount;
                }
                frameCount = 0;
                lastSecondTime = thisSecond;
            }
            while((now - lastRenderTime) < TTBR && (now - lastUpdateTime) < TBU){
                Thread.yield();

                try{
                    Thread.sleep(1);
                }
                catch (Exception e){
                    System.out.println("ERROR: yielding thread");
                }
                now = System.nanoTime();
            }
        }
    }

    public void update(){
        gameStateManager.update();
    }

    public void input(KeyHandler keyHandler, MouseHandler mouseHandler){
        gameStateManager.input(keyHandler, mouseHandler);
    }

    public void render() throws IOException, FontFormatException {
        if(graphics2D != null){
            graphics2D.setColor(new Color(51, 51, 51));
            graphics2D.fillRect(0,0,width,height);
            gameStateManager.render(graphics2D);
        }
    }

    public void draw(){
        Graphics graphic2 = (Graphics) this.getGraphics();
        graphic2.drawImage(image,0,0,width, height, null);
        graphic2.dispose();
    }

    public void addFont() throws IOException, FontFormatException {
        InputStream font_file = Window.class.getResourceAsStream("Graphics/commando.ttf");
        Font font = Font.createFont(Font.TRUETYPE_FONT, font_file);
        Font sizedFont = font.deriveFont(100f);
        this.setFont(sizedFont);
    }

    public Font getFont(){
        InputStream font_file = Window.class.getResourceAsStream("Graphics/commando.ttf");
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, font_file);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Font sizedFont = font.deriveFont(100f);
        return sizedFont;
    }
}
