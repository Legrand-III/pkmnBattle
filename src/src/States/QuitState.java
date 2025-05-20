package States;

import Main.PlayerKeyInputs;

import java.awt.*;

import static Main.GamePanel.*;

public class QuitState extends AbstractState{
    protected PlayerKeyInputs keyInputs;
    int selectedOption = 0;
    public QuitState(PlayerKeyInputs keyInputs){
        this.keyInputs = keyInputs;
    }
    @Override
    public void update() {

    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D)graphics;

        drawTextBox(graphics2D, 11, 5);

        drawOptionsBox(graphics2D, 11, 11, 5);

        graphics2D.setFont(new Font("times", Font.BOLD, 48));
        graphics2D.drawString("Are you sure", tileSize, tileSize *13 - (tileSize/8));
        graphics2D.drawString("you want to quit",
                tileSize, tileSize *15 - (tileSize/8));
        graphics2D.drawString("Yes", tileSize*12, tileSize *13 - (tileSize/8));
        graphics2D.drawString("No", tileSize*12, tileSize *15 - (tileSize/8));

        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.setColor(Color.red);
        switch (selectedOption) {
            case (0):
                graphics2D.drawRoundRect(tileSize * 12 - 5, tileSize * 12,
                        tileSize * 3, tileSize + 5, 25, 25);
                break;
            case (1):
                graphics2D.drawRoundRect(tileSize * 12 - 5, tileSize * 14,
                        tileSize * 3, tileSize + 5, 25, 25);
                break;
        }
    }



    public void upPressed(){
        if(selectedOption == 1){
            selectedOption = 0;
        }
    }
    public void downPressed(){
        if(selectedOption == 0){
            selectedOption = 1;
        }
    }

    public void spacePressed() {
        switch(selectedOption){
            case(0)://quit - reset for now
                ActivePokemon().heal(ActivePokemon().MaxHealth);
                OpposingPokemon().heal(OpposingPokemon().MaxHealth);
                keyInputs.setState(new TitleScreen(keyInputs));
                break;
            case(1):
                keyInputs.setState( new SelectionState(keyInputs));
                break;
        }
    }


    public void escapePressed() {
        keyInputs.setState(new SelectionState(keyInputs));
    }
}
