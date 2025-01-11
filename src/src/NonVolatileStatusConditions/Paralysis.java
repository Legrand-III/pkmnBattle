package NonVolatileStatusConditions;

import PokemonStuff.Pokemon;

import java.awt.*;

import static Main.GamePanel.tileSize;

public class Paralysis extends StatusCond{
    //may not be able to attack

    public Paralysis(Pokemon effectedPokemon){
        super(effectedPokemon);
        Condition = "Paralysis";
    }
    @Override
    public boolean StartOfTurn() {
        if(Math.random() < 0.25){
            return false;
        }
        return true;
    }

    @Override
    public void PrintstatusMessage(Graphics2D graphics2D) {
        graphics2D.setFont(new Font("times", Font.BOLD, 48));
        graphics2D.drawString(effectedPokemon.Name + " is", tileSize, tileSize *13 - (tileSize/8));
        graphics2D.drawString("paralyzed and can't move!",
                tileSize, tileSize *15 - (tileSize/8));
    }

    public void PrintCured(Graphics2D graphics2D) {
        graphics2D.setFont(new Font("times", Font.BOLD, 48));
        graphics2D.drawString(effectedPokemon.Name + " was", tileSize, tileSize *13 - (tileSize/8));
        graphics2D.drawString("cured of their paralysis!",
                tileSize, tileSize *15 - (tileSize/8));
    }
}
