package NonVolatileStatusConditions;

import PokemonStuff.Pokemon;

import java.awt.*;

import static Main.GamePanel.tileSize;

public class Sleep extends StatusCond{
    //may not be able to attack for at most 3 turns, at least 1 turn
    int turnsAsleep = 0;

    public Sleep(Pokemon effectedPokemon){
        super(effectedPokemon);
        cureAtStart = true;
        Condition = "Sleep";
    }
    @Override
    public boolean StartOfTurn(){ //a sleeping pokemon can't attack
        if(turnsAsleep == 3){
            return true;
        }
        if(turnsAsleep == 0){
            turnsAsleep++;
            return false;
        }

        if(Math.random() < 0.5){
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
