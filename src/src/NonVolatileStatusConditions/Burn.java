package NonVolatileStatusConditions;

import PokemonStuff.Pokemon;

import java.awt.*;

import static Main.GamePanel.tileSize;

public class Burn extends StatusCond{
    //reduces effective physical attack and deals damage

    public Burn(Pokemon effectedPokemon){
        super(effectedPokemon);
        Condition = "Burn";
    }

    @Override
    public boolean EndofTurn() {
        effectedPokemon.CurrentHealth -= (effectedPokemon.MaxHealth/8);
        if(effectedPokemon.CurrentHealth < 0){
            effectedPokemon.CurrentHealth = 0;
        }
        return true;
    }

    @Override
    public void PrintstatusMessage(Graphics2D graphics2D) {
        graphics2D.setFont(new Font("times", Font.BOLD, 48));
        graphics2D.drawString(effectedPokemon.Name + " was", tileSize, tileSize *13 - (tileSize/8));
        graphics2D.drawString("hurt by their burn!",
                tileSize, tileSize *15 - (tileSize/8));
    }

    @Override
    public void PrintCured(Graphics2D graphics2D) {
        graphics2D.setFont(new Font("times", Font.BOLD, 48));
        graphics2D.drawString(effectedPokemon.Name + " was", tileSize, tileSize *13 - (tileSize/8));
        graphics2D.drawString("cured of their burn!",
                tileSize, tileSize *15 - (tileSize/8));
    }
}
