package NonVolatileStatusConditions;

import PokemonStuff.Pokemon;

import java.awt.*;

public abstract class StatusCond {
    Pokemon effectedPokemon;
    public boolean cureAtStart = false;
    public String Condition;
    public StatusCond(Pokemon effectedPokemon){
        this.effectedPokemon = effectedPokemon;
    }

    /**
     * determines if a pokemon may act at the beginning of a turn
     * @return true by default
     */
    public boolean StartOfTurn(){
        return true;
    }

    /**
     * determines if anything should happen at the end of a turn
     * @return false by default
     */
    public boolean EndofTurn(){
        return false;
    }

    public abstract void PrintstatusMessage(Graphics2D graphics2D);

    public abstract void PrintCured(Graphics2D graphics2D);
}
