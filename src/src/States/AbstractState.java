package States;

import java.awt.*;

import static Main.GamePanel.*;
import static Main.GamePanel.tileSize;

public abstract class AbstractState {
    public abstract void update();
    public void paintComponent(Graphics graphics){
        //player health box
        Graphics2D graphics2D = (Graphics2D)graphics;
        graphics2D.setFont(new Font("times", Font.BOLD, 48));
        graphics2D.setColor(new Color(255,255,255,150)); //background = white + low opacity
        graphics2D.fillRoundRect(tileSize*12, tileSize*8, tileSize*9, tileSize*3, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.drawRoundRect(tileSize*12, tileSize*8 + 2,
                tileSize*9 - 2, tileSize*3 - 4, 25, 25);

        //enemy health box
        graphics2D.setColor(new Color(255,255,255,150)); //background = white + low opacity
        graphics2D.fillRoundRect(tileSize, tileSize, tileSize*9, tileSize*3, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.drawRoundRect(tileSize, tileSize + 2,
                tileSize*9 - 2, tileSize*3 - 4, 25, 25);

        graphics2D.setColor(Color.black);
        graphics2D.drawString(activePokemon.Name, tileSize*13, tileSize * 9 - (tileSize/8));
        graphics2D.drawString("HP: " + activePokemon.CurrentHealth + " / "
                + activePokemon.MaxHealth, tileSize*14, tileSize * 11 - (tileSize/8));

        graphics2D.drawString(opposingPokemon.Name, tileSize*2, tileSize * 2 - (tileSize/8));
        graphics2D.drawString("HP: " + opposingPokemon.CurrentHealth + " / "
                + opposingPokemon.MaxHealth, tileSize*3, tileSize * 4 - (tileSize/8));

        graphics2D.drawImage(activePokemon.backSprite, tileSize*4, tileSize*7 - 2, tileSize*4, tileSize*4, null);
        graphics2D.drawImage(opposingPokemon.frontSprite, tileSize*14, tileSize*3, tileSize*4, tileSize*4, null);
    }
    public void upPressed(){}
    public void downPressed(){}
    public void leftPressed(){}
    public void rightPressed(){}
    public void escapePressed(){}
    public void spacePressed(){}

    public void drawTextBox(Graphics2D graphics2D, int width, int height){
        graphics2D.setColor(new Color(0,0,0,150)); //background = black + low opacity
        graphics2D.fillRoundRect(0, tileSize*11, tileSize*width, tileSize*height, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect( 0, tileSize*11 + 2,
                tileSize*width - 2, tileSize*height - 4, 25, 25);
    }

    public void drawOptionsBox(Graphics2D graphics2D, int xPos, int width, int height){
        graphics2D.setColor(new Color(255,255,255,150)); //background = white + low opacity
        graphics2D.fillRoundRect(tileSize*xPos, tileSize*11, tileSize*width, tileSize*height, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.drawRoundRect(tileSize*xPos, tileSize*11 + 2,
                tileSize*width - 2, tileSize*height - 4, 25, 25);
    }
}
