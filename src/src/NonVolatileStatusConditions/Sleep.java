package NonVolatileStatusConditions;

import PokemonStuff.Pokemon;

import java.awt.*;

import static Main.GamePanel.tileSize;

/**
 * Status Condition- Sleep! The effected pokemon may be unable to act at the start of the turn.
 * Sleep lasts for a minimum of one turn and a maximum of 3 turns.
 * A pokemon has a 50% chance of waking up on any given turn.
 * Cures on Successful action
 */
public class Sleep extends StatusCond{
    //may not be able to attack for at most 3 turns, at least 1 turn
    int turnsAsleep = 0;

    public Sleep(Pokemon effectedPokemon){
        super(effectedPokemon);
        cureAtStart = true;
        Condition = "Sleep";
    }

    /**
     * Does the pokemon wake up?
     * guaranteed to sleep for at least 1 turn, guaranteed to wake up after 3 failed actions.
     * 50% chance to wake up otherwise
     * @return true if the pokemon wakes up, false otherwise
     */
    @Override
    public boolean StartOfTurn(){ //a sleeping pokemon can't attack
        if(turnsAsleep == 3){
            return true;
        }
        if(turnsAsleep == 0 || Math.random() < 0.5){
            turnsAsleep ++;
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
        graphics2D.drawString("fast asleep!",
                tileSize, tileSize *15 - (tileSize/8));
    }

    public void PrintCured(Graphics2D graphics2D) {
        graphics2D.setFont(new Font("times", Font.BOLD, 48));
        graphics2D.drawString(effectedPokemon.Name, tileSize, tileSize *13 - (tileSize/8));
        graphics2D.drawString("woke up!",
                tileSize, tileSize *15 - (tileSize/8));
    }
}
