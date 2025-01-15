package States;

import Main.PlayerKeyInputs;

import java.awt.*;

import static Main.GamePanel.*;

public class TitleScreen extends AbstractState{
    PlayerKeyInputs keyInputs;
    int optionNum = 0;
    public TitleScreen(PlayerKeyInputs keyInputs){
        this.keyInputs = keyInputs;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D)graphics;
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0,0, screenWidth, screenHeight);

        graphics2D.setFont(new Font("times", Font.BOLD, 48));
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString("Pokemon Battle!!", (tileSize * 7), tileSize * 3 -  (tileSize/8));

        graphics2D.drawImage(pokemonData.get("Pikachu").frontSprite, tileSize*7, tileSize*3, tileSize*8, tileSize*8, null);
        if(optionNum == 0){
            graphics2D.setColor(new Color(255,170,180));
            graphics2D.drawString("♡", (tileSize * 9), tileSize * 12 -  (tileSize/8));
        }
        graphics2D.drawString("Play", (tileSize * 10), tileSize * 12 -  (tileSize/8));
        if(optionNum == 1){
            graphics2D.setColor(new Color(255,170,180));
            graphics2D.drawString("♡", (tileSize * 9), tileSize * 14 -  (tileSize/8));
        }
        else{
            graphics2D.setColor(Color.WHITE);
        }
        graphics2D.drawString("Help", (tileSize * 10), tileSize * 14 -  (tileSize/8));
    }
    @Override
    public void update() {

    }

    @Override
    public void upPressed() {
        optionNum = (optionNum+1)%2;
    }

    @Override
    public void downPressed(){
        optionNum = (optionNum+1)%2;
    }

    @Override
    public void spacePressed(){
        if(optionNum == 0){
            keyInputs.state = new PokemonSelectScreen(keyInputs);
        }
        else{
            keyInputs.state = new ControlsScreen(keyInputs);
        }
    }
}
