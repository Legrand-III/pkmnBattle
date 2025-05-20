package Main;

import PokemonStuff.Move;
import PokemonStuff.Pokemon;

import java.util.AbstractMap;
import java.util.ArrayList;

import static Main.GamePanel.*;

public class Trainer {
    public Pokemon[] team;
    public Trainer(Pokemon a, Pokemon b, Pokemon c){
        team = new Pokemon[3];
        team[0] = a;
        team[1] = b;
        team[2] = c;
    }
    public Move determineMove(){
        System.out.println("=========");

        ArrayList<AbstractMap.SimpleEntry<Move, Integer>> possibleOptions = new ArrayList<>();

        //switch out? maybe

        possibleOptions.add(new AbstractMap.SimpleEntry<>(team[0].moves[0], 0));
        possibleOptions.add(new AbstractMap.SimpleEntry<>(team[0].moves[1], 0));
        possibleOptions.add(new AbstractMap.SimpleEntry<>(team[0].moves[2], 0));
        possibleOptions.add(new AbstractMap.SimpleEntry<>(team[0].moves[3], 0));
        AbstractMap.SimpleEntry<Move, Integer> bestOption = new AbstractMap.SimpleEntry<>(new Move("Struggle"), Integer.MIN_VALUE);

        for(AbstractMap.SimpleEntry<Move, Integer> option: possibleOptions){ //get numbers
            option.setValue(determineSuccess(option.getKey()));

            if(option.getValue() > bestOption.getValue()){
                bestOption = new AbstractMap.SimpleEntry<>(option.getKey(), option.getValue());
            }
        }

        return bestOption.getKey();
    }

    public Integer determineSuccess(Move move){
        int ans = 0;
        if(move.getRemainingPP() == 0){
            ans = Integer.MIN_VALUE; //invalid move
            return ans;
        }
        if(move.Category.equals("Status")) {
            switch (move.StatusType) {
                case ("Protect"):
                    if (OpposingPokemon().getPreviousMoves()[0] == null || !OpposingPokemon().getPreviousMoves()[0].StatusType.equals("Protect")) {
                        //first time trying to protect, successful
                        ans += 50;
                    } else if (OpposingPokemon().getPreviousMoves()[1] == null || !OpposingPokemon().getPreviousMoves()[1].StatusType.equals("Protect")) {//protect used twice in a row
                        ans -= 25;
                    } else if (OpposingPokemon().getPreviousMoves()[2] == null || !OpposingPokemon().getPreviousMoves()[2].StatusType.equals("Protect")) {//protect used 3 times in a row, tiny chance
                        ans -= 50;
                    } else {
                        ans -= 100;
                    }
                    break;

                case ("SelfHeal"):
                    if (OpposingPokemon().getCurrentHealth() < 0.6 * OpposingPokemon().MaxHealth) { //if your health is low, prioritize healing
                        ans += 600;
                    }
                    break;
                case ("SelfStat"):
                    ans = statStuff(move, OpposingPokemon());
                    break;
                case ("EnemyStat"):
                    ans = statStuff(move, OpposingPokemon());
                    break;

                case ("EnemyStatusCond"):
                    ans = statusThings(move);
                    break;
                default:
                    ans = 10;
            }
        }


        else{ //attacking move
            double effectiveness = move.calculateEffectiveness(ActivePokemon().Type);
            if(ActivePokemon().Type2 != null){
                effectiveness *= move.calculateEffectiveness(ActivePokemon().Type2);
            }
            double sameTypeBonus = 1;
            if(move.Type.equals(OpposingPokemon().Type) || move.Type.equals(OpposingPokemon().Type2)){
                sameTypeBonus += 0.5;
            }
            int possibleDamage;
            if(move.Category.equals("Physical")) {//attacking moves
                possibleDamage = (int) ((((22 * move.Power *
                        (OpposingPokemon().effectiveStat(OpposingPokemon().Attack, OpposingPokemon().AtkMultiplier()))
                        / (ActivePokemon().effectiveStat(ActivePokemon().Defense, ActivePokemon().DefMultiplier()))) / 50) + 2)
                        * effectiveness * sameTypeBonus);
            }
            else{
                possibleDamage = (int) ((((22 * move.Power *
                        (OpposingPokemon().effectiveStat(OpposingPokemon().SpAttack, OpposingPokemon().SpAtkMultiplier()))
                        / (ActivePokemon().effectiveStat(ActivePokemon().SpDefense, ActivePokemon().SpDefMultiplier()))) / 50) + 2)
                        * effectiveness * sameTypeBonus);
            }
                if (possibleDamage == 0){ //if the move will deal damage
                    ans = -100;
                    return ans;
                }
                else{
                    ans+= possibleDamage;
                }
                if(possibleDamage < (0.125 * ActivePokemon().MaxHealth)){
                    ans -=possibleDamage/2;
                }
                if(effectiveness>1) {
                    ans *= (int)effectiveness;
                }
                else{
                    ans /= 2;
                }
                if(possibleDamage >= ActivePokemon().getCurrentHealth() ){//the move is guaranteed to KO, use it!
                    ans+=100000;
                    if(move.Priority > 0){
                        ans += 100000;
                    }
                }

        }

        System.out.println(move.Name + " recieved a score of " + ans);
        return ans;
    }

    public int statStuff(Move move, Pokemon target) {
        String[] effectList = move.Effect.split("!");
        int ans = 0;
        for (int i = 0; i < effectList.length - 1; i += 2) {
            String stat = effectList[i];
            int statChange = Integer.parseInt(effectList[i + 1]);
            switch (stat) {
                case ("ATK"):
                    if (target.AtkMultiplier() > 0) {
                        ans += ((6 / target.AtkMultiplier()) - 1) * 5 * statChange;
                    } else {
                        ans += (6 * (Math.abs(target.AtkMultiplier()) + 5)) * statChange;
                    }
                    break;
                case ("SpATK"):
                    if (target.SpAtkMultiplier() > 0) {
                        ans += ((6 / target.SpAtkMultiplier()) - 1) * 5 * statChange;
                    } else {
                        ans += (6 * (Math.abs(target.SpAtkMultiplier()) + 5)) * statChange;
                    }
                    break;
                case ("DEF"):
                    if (target.DefMultiplier() > 0) {
                        ans += ((6 / target.DefMultiplier()) - 1) * 5 * statChange;
                    } else {
                        ans += (6 * (Math.abs(target.DefMultiplier()) + 5)) * statChange;
                    }
                    break;
                case ("SpDEF"):
                    if (target.SpDefMultiplier() > 0) {
                        ans += ((6 / target.SpDefMultiplier()) - 1) * 5 * statChange;
                    } else {
                        ans += (6 * (Math.abs(target.SpDefMultiplier()) + 5)) * statChange;
                    }
                    break;
                case ("SPD"):
                    if (target.SpdMultiplier() > 0) {
                        ans += ((6 / target.SpdMultiplier()) - 1) * 5 * statChange;
                    } else {
                        ans += (6 * (Math.abs(target.SpdMultiplier()) + 5)) * statChange;
                    }
                    break;
            }
        }
        return ans;
    }

    public int statusThings(Move move){
        int ans = 0;
        switch (move.Effect) {
            case ("BURN"):
                if (ActivePokemon().getNonVolatileStatus() != null ||
                        ActivePokemon().Type.equals("Fire") || (ActivePokemon().Type2 != null && ActivePokemon().Type2.equals("Fire"))) {
                    ans = Integer.MIN_VALUE + 1;
                    return ans;
                }
                ans = 500;
                return ans;
            case ("SLEEP"):
                if (ActivePokemon().getNonVolatileStatus() != null) {
                    ans = Integer.MIN_VALUE + 1;
                    return ans;
                }
                ans = 500;
                return ans;
            case ("PARALYSIS"):
                if (ActivePokemon().getNonVolatileStatus() != null ||
                        ActivePokemon().Type.equals("Electric") || (ActivePokemon().Type2 != null && ActivePokemon().Type2.equals("Electric"))) {
                    ans = Integer.MIN_VALUE + 1;
                    return ans;
                }
                ans = 500;
                return ans;
            case ("POISON"):
                if (ActivePokemon().getNonVolatileStatus() != null ||
                        ActivePokemon().Type.equals("Poison") || (ActivePokemon().Type2 != null && ActivePokemon().Type2.equals("Poison"))
                || ActivePokemon().Type.equals("Steel") || (ActivePokemon().Type2 != null && ActivePokemon().Type2.equals("Steel"))) {
                    ans = Integer.MIN_VALUE + 1;
                    return ans;
                }
                ans = 500;
                return ans;
            default:
                System.out.println("error in effect file, " + move.Effect);
                return ans;
        }
    }
}
