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
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0,0, screenWidth, screenHeight);

        graphics2D.setFont(new Font("times", Font.BOLD, 48));
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString("Controls and such", (tileSize * 7), tileSize * 3 -  (tileSize/8));

    }

    @Override
    public void update() {

    }

    @Override
    public void escapePressed(){
        keyInputs.state = new TitleScreen(keyInputs);
    }
}
