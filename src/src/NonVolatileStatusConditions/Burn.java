package NonVolatileStatusConditions;

import PokemonStuff.Pokemon;

import java.awt.*;

import static Main.GamePanel.tileSize;
/**
 * Status condition- Burn! Makes the effected pokemon deal 50% less physical damage
 * and take 1/8 of their max health as damage at the end of each turn
 */
public class Burn extends StatusCond{
    //reduces effective physical attack and deals damage


    public Burn(Pokemon effectedPokemon){
        super(effectedPokemon);
        Condition = "Burn";
    }

    /**
     * makes the pokemon take damage at the end of the turn equal to half 1/8 of their max health
     * @return true, showing something should happen at the end of the turn
     */
    @Override
    public boolean EndofTurn() {
        effectedPokemon.takeDamage(effectedPokemon.MaxHealth/8);
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
