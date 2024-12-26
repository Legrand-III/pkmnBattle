package PokemonStuff;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static Main.GamePanel.pokemonData;


public class Pokemon {
    //Main stuff, static stats + the pokemon
    public BufferedImage frontSprite, backSprite;
    public String Name;
    String Type, Type2; //object perchance, implement later lol
    public int MaxHealth, CurrentHealth, Attack, Defense, SpAttack, SpDefense, Speed;

    public Move[] moves;
    public boolean protecting = false;
    public Move[] previousMoves = new Move[4];
    public String nonVolatileStatus; //sleep, burn, paralysis, etc
    public ArrayList<String> volatileStatus; //confuse, taunt, torment, infatuation, etc

    //multipliers, stat changing
    String Status; //maybe object
    public int AtkMultiplier, DefMultiplier, SpAtkMultiplier, SpDefMultiplier, SpdMultiplier;
    public Pokemon(String name){ //constructor used for team pokemon
        createPokemon(pokemonData.get(name));
    }

    public Pokemon(String name, String type, String type2, int maxHealth, int attack, int defense,
                   int spAttack, int spDefense, int speed, String frontSpritePath, String backSpritePath,
                   Move m0, Move m1, Move m2, Move m3){ //constructor used for blueprint
        this.Name = name; this.Type = type;
        if(!type2.equals("none")){
            this.Type2 = type2;
        }
        this.MaxHealth = maxHealth; this.CurrentHealth = maxHealth;
        this.Attack = attack; this.Defense = defense;
        this.SpAttack = spAttack; this.SpDefense = spDefense;
        this.Speed = speed;
        this.moves = new Move[4];

        //new object so PP can be represented accurately
        moves[0] = m0; moves[1] = m1; moves[2] = m2; moves[3] = m3;

        try{
            this.frontSprite = ImageIO.read(new File(frontSpritePath));
            this.backSprite = ImageIO.read(new File(backSpritePath));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void createPokemon(Pokemon pkmn){
        this.Name = pkmn.Name; this.Type = pkmn.Type;
        this.MaxHealth = pkmn.MaxHealth; this.CurrentHealth = pkmn.MaxHealth;
        this.Attack = pkmn.Attack; this.Defense = pkmn.Defense;
        this.SpAttack = pkmn.SpAttack; this.SpDefense = pkmn.SpDefense;
        this.Speed = pkmn.Speed;
        this.moves = new Move[4];

        //new object so PP can be represented accurately
        moves[0] = copyMoveData(pkmn.moves[0]); moves[1] = copyMoveData(pkmn.moves[1]);
        moves[2] = copyMoveData(pkmn.moves[2]); moves[3] = copyMoveData(pkmn.moves[3]);

        this.frontSprite = pkmn.frontSprite;
        this.backSprite = pkmn.backSprite;
    }
    public Move copyMoveData(Move move){
        switch(move.Category){
            case ("Physical"), ("Special"):
                return new AttackingMove(move.Name, move.Type, move.Category, move.MaxPP, move.Power, move.Accuracy,
                        move.Priority, move.Effect);

            case("Status"):
                return new StatusMove(move.Name, move.Type, move.Category, move.MaxPP, move.Power, move.Accuracy,
                        move.Priority, move.Effect, move.StatusType);
        }
        System.out.println("unable to find move category");
        return null;

    }

    public void resetStatChanges(){
        AtkMultiplier = 0;
        DefMultiplier = 0;
        SpAtkMultiplier = 0;
        SpDefMultiplier = 0;
        SpdMultiplier = 0;
    }

    public void switchOut(){
        resetStatChanges();
        previousMoves[0] = null;
        previousMoves[1] = null;
        previousMoves[2] = null;
        previousMoves[3] = null;

    }
    public double effectiveStat(int baseStat, int statMultiplier){
        if(statMultiplier < 0){
            statMultiplier*= -1;
            return 2/((double)statMultiplier + 2);
        }
        if(statMultiplier > 0){
            return ((double)(statMultiplier + 2))/2;
        }
        return baseStat;
    }
    public void addUsedMove(Move move){
        if(this.previousMoves[0] == null){
            previousMoves[0] = move;
        }
        else if(this.previousMoves[1] == null){
            previousMoves[1] = previousMoves[0];
            previousMoves[0] = move;
        }
        else{
            previousMoves[2] = previousMoves[1];
            previousMoves[1] = previousMoves[0];
            previousMoves[0] = move;
        }
    }


}
