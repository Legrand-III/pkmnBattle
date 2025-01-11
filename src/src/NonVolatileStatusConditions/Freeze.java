package NonVolatileStatusConditions;

import PokemonStuff.Pokemon;

import java.awt.*;

import static Main.GamePanel.tileSize;

public class Freeze extends StatusCond{
    //may not be able to attack

    public Freeze(Pokemon effectedPokemon){
        super(effectedPokemon);
        cureAtStart = true;
        Condition = "Freeze";

    }

    @Override
    public boolean StartOfTurn() {
        if(Math.random() < 0.8){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public void PrintstatusMessage(Graphics2D graphics2D) {
        graphics2D.setFont(new Font("times", Font.BOLD, 48));
        graphics2D.drawString(effectedPokemon.Name + " is", tileSize, tileSize *13 - (tileSize/8));
        graphics2D.drawString("frozen solid!",
                tileSize, tileSize *15 - (tileSize/8));
    }

    public void PrintCured(Graphics2D graphics2D) {
        graphics2D.setFont(new Font("times", Font.BOLD, 48));
        graphics2D.drawString(effectedPokemon.Name, tileSize, tileSize *13 - (tileSize/8));
        graphics2D.drawString("thawed out!",
                tileSize, tileSize *15 - (tileSize/8));
    }
}
