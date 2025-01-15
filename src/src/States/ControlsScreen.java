package States;

import Main.PlayerKeyInputs;

import java.awt.*;

import static Main.GamePanel.*;
import static Main.GamePanel.tileSize;

public class ControlsScreen extends AbstractState{

    public PlayerKeyInputs keyInputs;
    public ControlsScreen(PlayerKeyInputs keyInputs){
        this.keyInputs = keyInputs;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D)graphics;
        graphics2D.setColor(new Color(0,0,0,200));
        graphics2D.fillRect(0,0, screenWidth, screenHeight);
        drawTextBox(graphics2D, columns, 5);

        graphics2D.setFont(new Font("times", Font.BOLD, 48));
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString("Things to know!", (tileSize * 7), tileSize * 2 -  (tileSize/8));


        graphics2D.setColor(new Color(255,255,255,150)); //background = white + low opacity
        graphics2D.fillRoundRect(0, tileSize*3, tileSize*11, tileSize*8, 35, 35);
        graphics2D.setColor(Color.white);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect( 0,tileSize * 3 + 2,
                tileSize*11 - 2, tileSize*8 - 4, 25, 25);

        graphics2D.setColor(new Color(255,255,255,150)); //background = white + low opacity
        graphics2D.fillRoundRect(tileSize*11, tileSize*3, tileSize*11, tileSize*8, 35, 35);
        graphics2D.setColor(Color.white);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect( tileSize*11,tileSize * 3 + 2,
                tileSize*11 - 2, tileSize*8 - 4, 25, 25);


        graphics2D.setFont(new Font("times", Font.BOLD | Font.ITALIC, 40));

        graphics2D.drawString("~Information~", (tileSize), tileSize * 4 -  (tileSize/8));
        graphics2D.drawString("~More Information~", (tileSize*12), tileSize * 4 -  (tileSize/8));
        graphics2D.drawString("~Controls~", (tileSize), tileSize * 12 -  (tileSize/8));

        graphics2D.setFont(new Font("times", Font.BOLD | Font.ITALIC, 30));
        graphics2D.drawString("~Information~", (tileSize), tileSize * 5 -  (tileSize/8));
        graphics2D.drawString("~More Information~", (tileSize*12), tileSize * 5 -  (tileSize/8));





        graphics2D.drawString("W,A,S,D // ARROW KEYS- Navigate menus", (tileSize), tileSize * 13 -  (tileSize/8));
        graphics2D.drawString("SPACE // ENTER- Confirm Selection", (tileSize), tileSize * 14 -  (tileSize/8));
        graphics2D.drawString("ESCAPE // X - Cancel Selection", (tileSize), tileSize * 15 -  (tileSize/8));




    }

    @Override
    public void update() {

    }

    @Override
    public void escapePressed(){
        keyInputs.state = new TitleScreen(keyInputs);
    }
}
