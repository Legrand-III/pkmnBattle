package States;

import Main.PlayerKeyInputs;
import PokemonStuff.*;

import java.awt.*;
import java.util.AbstractMap;


public class BattleState extends AbstractState{
    protected PlayerKeyInputs keyInputs;
    protected Pokemon activePokemon;
    protected Move selectedMove;
    protected Pokemon opposingPokemon;
    protected Move opponentMove;
    protected AbstractMap.SimpleEntry<Pokemon, Move>[] turnOrder;
    public BattleState(PlayerKeyInputs keyInputs, Pokemon activePokemon, Move selectedMove,
    Pokemon opposingPokemon, Move opponentMove){
        this.keyInputs = keyInputs;
        this.activePokemon = activePokemon;
        this.selectedMove = selectedMove;
        this.opposingPokemon = opposingPokemon;
        this.opponentMove = opponentMove;
        this.turnOrder = calculateTurnOrder(activePokemon, selectedMove, opposingPokemon, opponentMove);
    }
    @Override
    public void update() {

    }

    @Override
    public void paintComponent(Graphics graphics) {

    }


    public AbstractMap.SimpleEntry<Pokemon,Move>[] calculateTurnOrder(Pokemon activePokemon, Move selectedMove, Pokemon opposingPokemon, Move opponentMove){
        AbstractMap.SimpleEntry<Pokemon,Move>[] ans = new AbstractMap.SimpleEntry[2];
        if(selectedMove.Priority == opponentMove.Priority){//same priority = speed determines
            if(activePokemon.Speed > opposingPokemon.Speed){//player first
                ans[0] = new AbstractMap.SimpleEntry<>(activePokemon, selectedMove);
                ans[1] = new AbstractMap.SimpleEntry<>(opposingPokemon, opponentMove);
                return ans;
            }
            else if(activePokemon.Speed < opposingPokemon.Speed){//enemy first
                ans[0] = new AbstractMap.SimpleEntry<>(opposingPokemon, opponentMove);
                ans[1] = new AbstractMap.SimpleEntry<>(activePokemon, selectedMove);
                return ans;
            }
            //else, speed tie, random order
            int first = (int)(Math.random()*2);
            if(first == 0){//player first
                ans[0] = new AbstractMap.SimpleEntry<>(activePokemon, selectedMove);
                ans[1] = new AbstractMap.SimpleEntry<>(opposingPokemon, opponentMove);
            }
            else{ //enemy first
                ans[0] = new AbstractMap.SimpleEntry<>(opposingPokemon, opponentMove);
                ans[1] = new AbstractMap.SimpleEntry<>(activePokemon, selectedMove);
            }
            return ans;
        }
        //else, different priorities
        if(selectedMove.Priority > opponentMove.Priority){//player first
            ans[0] = new AbstractMap.SimpleEntry<>(activePokemon, selectedMove);
            ans[1] = new AbstractMap.SimpleEntry<>(opposingPokemon, opponentMove);
        }
        else{//(selectedMove.Priority < opponentMove.Priority) --> enemy first
            ans[0] = new AbstractMap.SimpleEntry<>(opposingPokemon, opponentMove);
            ans[1] = new AbstractMap.SimpleEntry<>(activePokemon, selectedMove);
        }
        return ans;
    }
    public void playTurn(){

    }
    public void useMove(){

    }

}
