package NonVolatileStatusConditions;

import PokemonStuff.Pokemon;

import java.awt.*;

import static Main.GamePanel.tileSize;

/**
 * Status Condition- Poison! The effected pokemon takes increasing damage at the end of each turn,
 * starting at 1/16 of their max health, then increasing by 1/16 for each subsequent turn
 */
public class Poison extends StatusCond{
    //deals increasing damage over time

    int turnsPoisoned = 1;

    public Poison(Pokemon effectedPokemon){
        super(effectedPokemon);
        Condition = "Poison";
    }

    /**
     * take increasing damage depending on how many turns they've been poisoned
     * @return true, showing something should happen at the end of the turn
     */
    @Override
    public boolean EndofTurn() {
        effectedPokemon.takeDamage((effectedPokemon.MaxHealth * turnsPoisoned) / 16);
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
