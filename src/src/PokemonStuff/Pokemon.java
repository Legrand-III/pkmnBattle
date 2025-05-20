package PokemonStuff;

import NonVolatileStatusConditions.StatusCond;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static Main.GamePanel.pokemonData;

/**
 * the Pokemon! Holds stats, moves, and such so that battling is possible
 */
public class Pokemon {
    //Main stuff, stats + the pokemon
    public BufferedImage frontSprite, backSprite;
    public final String Name;
    public final String Type, Type2;
    public final int MaxHealth,  Attack, Defense, SpAttack, SpDefense, Speed;
    private int CurrentHealth;

    public final Move[] moves;
    private boolean protecting = false;
    private Move[] previousMoves = new Move[4];
    private StatusCond nonVolatileStatus = null; //sleep, burn, paralysis, etc

    //multipliers, stat changing
    private int AtkMultiplier, DefMultiplier, SpAtkMultiplier, SpDefMultiplier, SpdMultiplier;


    /**
     * Main Pokemon Constructor used for creating pokemon on each player's team
     * @param name The pokemon's name, used to get data from pokemonData hashmap
     */
    public Pokemon(String name){ //constructor used for team pokemon
        Pokemon pkmn = pokemonData.get(name);
        this.Name = pkmn.Name; this.Type = pkmn.Type; this.Type2 = pkmn.Type2;
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
    /**
     * Constructor used by the initialization to copy pokemon data from the CSV file
     */
    public Pokemon(String name, String type, String type2, int maxHealth, int attack, int defense,
                   int spAttack, int spDefense, int speed, String frontSpritePath, String backSpritePath,
                   Move m0, Move m1, Move m2, Move m3){ //constructor used for blueprint
        this.Name = name; this.Type = type;
        if(type2.equals("none")){
            this.Type2 = null;
        }
        else{
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

    public Move copyMoveData(Move move){
        switch(move.Category){
            case ("Physical"), ("Special"):
                return new AttackingMove(move.Name, move.Type, move.Category, move.MaxPP, move.Power, move.Accuracy,
                        move.Priority, move.Effect, move.MoveInfo1, move.MoveInfo2, move.ShortenedName);

            case("Status"):
                return new StatusMove(move.Name, move.Type, move.Category, move.MaxPP, move.Power, move.Accuracy,
                        move.Priority, move.Effect, move.StatusType, move.MoveInfo1, move.MoveInfo2, move.ShortenedName);
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
            return baseStat * (2/((double)statMultiplier + 2));
        }
        if(statMultiplier > 0){
            return baseStat * (((double)(statMultiplier + 2))/2);
        }
        return baseStat;
    }
    public Move[] getPreviousMoves(){
        return this.previousMoves;
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

    public void takeDamage(int damage){
        this.CurrentHealth -= damage;
        if(this.CurrentHealth < 0){
            this.CurrentHealth = 0;
        }
    }
    public void heal(int healAmt){
        this.CurrentHealth += healAmt;
        //validate
        if(this.CurrentHealth > this.MaxHealth){
            this.CurrentHealth = this.MaxHealth;
        }
    }
    public int getCurrentHealth() {
        return CurrentHealth;
    }


    public StatusCond getNonVolatileStatus() {
        return nonVolatileStatus;
    }
    public void setNonVolatileStatus(StatusCond nonVolatileStatus) {
        this.nonVolatileStatus = nonVolatileStatus;
    }


    public boolean isProtecting() {
        return protecting;
    }
    public void setProtecting(boolean protecting) {
        this.protecting = protecting;
    }

    public int AtkMultiplier() {
        return AtkMultiplier;
    }
    public void changeAtkMultiplier(int atkMultiplier) {
        this.AtkMultiplier += atkMultiplier;
        //validate
        if(this.AtkMultiplier > 6){
            this.AtkMultiplier = 6;
        }
        else if(this.AtkMultiplier < -6){
            this.AtkMultiplier = -6;
        }
    }

    public int SpAtkMultiplier() {
        return SpAtkMultiplier;
    }

    public void changeSpAtkMultiplier(int spAtkMultiplier) {
        this.SpAtkMultiplier += spAtkMultiplier;
        //validate
        if(this.SpAtkMultiplier > 6){
            this.SpAtkMultiplier = 6;
        }
        else if(this.SpAtkMultiplier < -6){
            this.SpAtkMultiplier = -6;
        }
    }

    public int DefMultiplier() {
        return DefMultiplier;
    }
    public void changeDefMultiplier(int defMultiplier) {
        this.DefMultiplier += defMultiplier;
        //validate
        if(this.DefMultiplier > 6){
            this.DefMultiplier = 6;
        }
        else if(this.DefMultiplier < -6){
            this.DefMultiplier = -6;
        }
    }

    public int SpDefMultiplier() {
        return SpDefMultiplier;
    }
    public void changeSpDefMultiplier(int spDefMultiplier) {
        this.SpDefMultiplier += spDefMultiplier;
        //validate
        if(this.SpDefMultiplier > 6){
            this.SpDefMultiplier = 6;
        }
        else if(this.SpDefMultiplier < -6){
            this.SpDefMultiplier = -6;
        }
    }

    public int SpdMultiplier() {
        return SpdMultiplier;
    }

    public void changeSpdMultiplier(int spdMultiplier) {
        this.SpdMultiplier += spdMultiplier;
        //validate
        if(this.SpdMultiplier > 6){
            this.SpdMultiplier = 6;
        }
        else if(this.SpdMultiplier < -6){
            this.SpdMultiplier = -6;
        }
    }
}