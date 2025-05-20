package States;

import Main.PlayerKeyInputs;

import java.awt.*;

import static Main.GamePanel.*;
import static java.lang.Thread.sleep;

public class ResultsScreen extends AbstractState{
    PlayerKeyInputs keyInputs;
    String result;
    Boolean displayed = false;

    public ResultsScreen(PlayerKeyInputs keyInputs){
        this.keyInputs = keyInputs;
        if(ActivePokemon().getCurrentHealth() == 0 && OpposingPokemon().getCurrentHealth() == 0){
            this.result = "tie";
        }
        else if(ActivePokemon().getCurrentHealth() == 0){
            this.result = "lose";
        }
        else{
            this.result = "win";
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        if(displayed){
            try{sleep(5000);}
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            this.keyInputs.setState(new TitleScreen(this.keyInputs));
            return;
        }
        //player health box
        Graphics2D graphics2D = (Graphics2D)graphics;
        graphics2D.setColor(new Color(255,255,255,150)); //background = white + low opacity
        graphics2D.fillRoundRect(tileSize*12, tileSize*8, tileSize*9, tileSize*3, 35, 35);

        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.setColor(Color.white);
        graphics2D.drawRoundRect(tileSize*12, tileSize*8 + 2,
                tileSize*9 - 2, tileSize*3 - 4, 25, 25);

        //enemy health box
        graphics2D.setColor(new Color(255,255,255,150)); //background = white + low opacity
        graphics2D.fillRoundRect(tileSize, tileSize, tileSize*9, tileSize*3, 35, 35);

        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.setColor(Color.white);
        graphics2D.drawRoundRect(tileSize, tileSize + 2,
                tileSize*9 - 2, tileSize*3 - 4, 25, 25);


        graphics2D.setFont(new Font("times", Font.BOLD, 48));
        graphics2D.setColor(Color.black);
        graphics2D.drawString(ActivePokemon().Name, tileSize*13, tileSize * 9 - (tileSize/8));
        graphics2D.drawString("HP: " + ActivePokemon().getCurrentHealth() + " / "
                + ActivePokemon().MaxHealth, tileSize*14, tileSize * 11 - (tileSize/8));

        graphics2D.drawString(OpposingPokemon().Name, tileSize*2, tileSize * 2 - (tileSize/8));
        graphics2D.drawString("HP: " + OpposingPokemon().getCurrentHealth() + " / "
                + OpposingPokemon().MaxHealth, tileSize*3, tileSize * 4 - (tileSize/8));

        displayResult(graphics2D);



    }

    public void displayResult(Graphics2D graphics2D){
        drawTextBox(graphics2D, columns, 5);
        graphics2D.setFont(new Font("times", Font.BOLD, 48));

        if(this.result.equals("win")){
            graphics2D.drawImage(ActivePokemon().backSprite, tileSize*4, tileSize*7 - 2, tileSize*4, tileSize*4, null);
            graphics2D.drawString("Congratulations! You defeated", tileSize, tileSize *13 - (tileSize/8));
            graphics2D.drawString("your opponent!",
                    tileSize, tileSize *15 - (tileSize/8));
        }
        else if(this.result.equals("lose")){
            graphics2D.drawImage(OpposingPokemon().frontSprite, tileSize*14, tileSize*3, tileSize*4, tileSize*4, null);
            graphics2D.drawString("You lost :(", tileSize, tileSize *13 - (tileSize/8));
            graphics2D.drawString("Better luck next time",
                    tileSize, tileSize *15 - (tileSize/8));
        }
        else{
            graphics2D.drawString("The match ended in a tie!", tileSize, tileSize *13 - (tileSize/8));
            graphics2D.drawString("How shocking",
                    tileSize, tileSize *15 - (tileSize/8));
        }
        displayed = true;
    }

    @Override
    public void update() {

    }
}
