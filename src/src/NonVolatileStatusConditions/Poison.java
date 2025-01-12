package NonVolatileStatusConditions;

import PokemonStuff.Pokemon;

import java.awt.*;

import static Main.GamePanel.tileSize;

public class Poison extends StatusCond{
    //deals increasing damage over time

    int turnsPoisoned = 1;

    public Poison(Pokemon effectedPokemon){
        super(effectedPokemon);
        Condition = "Poison";
    }

    @Override
    public boolean EndofTurn() {
        effectedPokemon.CurrentHealth -= (effectedPokemon.MaxHealth * turnsPoisoned) / 16;
        if(effectedPokemon.CurrentHealth < 0){
            effectedPokemon.CurrentHealth = 0;
        }
        turnsPoisoned++;
        return true;
    }

    @Override
    public void PrintstatusMessage(Graphics2D graphics2D) {
        graphics2D.setFont(new Font("times", Font.BOLD, 48));
        graphics2D.drawString(effectedPokemon.Name + " is", tileSize, tileSize *13 - (tileSize/8));
        graphics2D.drawString("badly poisoned!",
                tileSize, tileSize *15 - (tileSize/8));
    }

    public void PrintCured(Graphics2D graphics2D) {
        graphics2D.setFont(new Font("times", Font.BOLD, 48));
        graphics2D.drawString(effectedPokemon.Name + " was", tileSize, tileSize *13 - (tileSize/8));
        graphics2D.drawString("cured of their poison!",
                tileSize, tileSize *15 - (tileSize/8));
    }
}
