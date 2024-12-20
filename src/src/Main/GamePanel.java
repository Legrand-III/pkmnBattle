package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable{
    public int tileSize = 48; //each 1 tile is 48x48 pixels
    public int rows = 16;
    public int columns = 22;
    public int screenHeight = tileSize * rows; //576 pixels tall // 12 tiles tall, temp size
    public int screenWidth = tileSize * columns; //768 pixels wide // 16 tiles tall, temp size

    int fps = 60;

    BufferedImage backgroundImage;

    Thread gameThread;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new Color(0, 200, 0));
        this.setDoubleBuffered(true);

        this.setFocusable(true);

        try{
            backgroundImage = ImageIO.read(new File("out/res/lhu26US.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){
        double drawInterval= 1000000000.0/fps; //the game's display will be updated 60 times per second
        double delta = 0; //represents difference in time between current time and last checked time
        long lastTime = System.nanoTime(); //returns current time in nanoseconds
        long currentTime = System.nanoTime();

        while(gameThread != null){
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if(delta >= 1) {
                //update game info EX: position

                //display new info
                repaint();
                delta--;
            }

        }

    }

    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D)graphics;

        graphics2D.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight - (5 * tileSize),null);
        //display tiles
        int x;
        int y = 0;
        for(int row = 0; row < rows + 2; row++){
            x = 0;
            for(int column = 0; column < columns + 2; column++){
                graphics.drawRect(x, y, tileSize, tileSize);
                x+= tileSize;
            }
            y += tileSize;
        }

        //textbox
        graphics2D.setColor(new Color(0,0,0,150)); //background = black + low opacity
        graphics2D.fillRoundRect(0, tileSize*11, tileSize*11, tileSize*5, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect( 0, tileSize*11 + 2,
                tileSize*11 - 2, tileSize*5 - 4, 25, 25);


        //options box
        graphics2D.setColor(new Color(255,255,255,150)); //background = black + low opacity
        graphics2D.fillRoundRect(tileSize*11, tileSize*11, tileSize*11, tileSize*5, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect( tileSize*11, tileSize*11 + 2,
                tileSize*11 - 2, tileSize*5 - 4, 25, 25);



        graphics2D.dispose();
    }
}
