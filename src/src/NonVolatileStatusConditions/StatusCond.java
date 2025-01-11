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

    public boolean StartOfTurn(){
        return true;
    }

    public boolean EndofTurn(){
        return false;
    }

    public abstract void PrintstatusMessage(Graphics2D graphics2D);

    public abstract void PrintCured(Graphics2D graphics2D);
}
