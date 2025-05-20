package PokemonStuff;

import Main.GamePanel;
import NonVolatileStatusConditions.*;

import java.rmi.UnexpectedException;
import java.util.ArrayList;

public class StatusMove extends Move{
    public StatusMove(String name, String type, String category, int pp, int power, int accuracy, int priority,
                      String effect, String statusType, String info1, String info2, String shortenedName){
        super(name, type, category, pp, power, accuracy, priority, info1, info2, shortenedName, effect, statusType);
    }

    @Override
    public String[][] useMove(Pokemon user, Pokemon target){
        usePP();
        String[][] ans = new String[2][2];
        if(StatusType.equals("Protect")){
            if(protectValidity(user)){
                ans[0][0] = user.Name;
                ans[0][1] = "protected themself!";
            }
            else{
                ans[0][0] = "But it";
                ans[0][1] = "failed...";
            }
            return ans;
        }
        if(StatusType.equals("SelfHeal")){
            String[] effectList = Effect.split("!");
            user.heal((int)(user.MaxHealth * (Double.parseDouble(effectList[1])/100)));
            ans[0][0] = user.Name + " healed";
            ans[0][1] = "themself!";

            if(effectList.length > 2 && effectList[2].equals("SLEEP")){
                ans[1][0] = user.Name + " fell";
                ans[1][1] = "asleep!";
                user.setNonVolatileStatus(new Sleep(user));
            }

            return ans;
        }

        if(StatusType.equals("EnemyStatusCond")){
            if(target.isProtecting()){
                ans[0][0] = target.Name + " protected";
                ans[0][1] = "themself!";
                return ans;
            }
            if(this.Accuracy - (Math.random() * 100) < 0){
                ans[0][0] = "The attack";
                ans[0][1] = "missed!";
                return ans;
            }
            switch(Effect){
                case("BURN"):
                    if(target.getNonVolatileStatus() != null ||
                            target.Type.equals("Fire") || (target.Type2 != null && target.Type2.equals("Fire"))){
                        ans[0][0] = "But it";
                        ans[0][1] = "failed...";
                        return ans;
                    }
                    ans[0][0] = target.Name;
                    ans[0][1] = "was burnt!";
                    target.setNonVolatileStatus(new Burn(target));
                    return ans;
                case("SLEEP"):
                    if(target.getNonVolatileStatus() != null){
                        ans[0][0] = "But it";
                        ans[0][1] = "failed...";
                        return ans;
                    }
                    ans[0][0] = target.Name;
                    ans[0][1] = "fell asleep!";
                    target.setNonVolatileStatus(new Sleep(target));
                    return ans;
                case("PARALYSIS"):
                    if(target.getNonVolatileStatus() != null ||
                            target.Type.equals("Electric") || (target.Type2 != null && target.Type2.equals("Electric"))){
                        ans[0][0] = "But it";
                        ans[0][1] = "failed...";
                        return ans;
                    }
                    ans[0][0] = target.Name;
                    ans[0][1] = "was paralyzed!";
                    target.setNonVolatileStatus(new Paralysis(target));
                    return ans;
                case("POISON"):
                    if(target.getNonVolatileStatus() != null ||
                            target.Type.equals("Poison") || (target.Type2 != null && target.Type2.equals("Poison")) ||
                    target.Type.equals("Steel") || (target.Type2 != null && target.Type2.equals("Steel"))){
                        ans[0][0] = "But it";
                        ans[0][1] = "failed...";
                        return ans;
                    }
                    ans[0][0] = target.Name;
                    ans[0][1] = "was badly poisoned!";
                    target.setNonVolatileStatus(new Poison(target));
                    return ans;
                default:
                    System.out.println("error in effect file, " + Effect);
            }

        }

        if(StatusType.equals("SelfStat")){//target = self instead
            target = user;
        }
        if(target.isProtecting()){
            ans[0][0] = target.Name + " protected";
            ans[0][1] = "themself!";
            return ans;
        }
        if(this.Accuracy - (Math.random() * 100) < 0){
            ans[0][0] = "The attack";
            ans[0][1] = "missed!";
            return ans;
        }
        String[] effectList = Effect.split("!");
        if(effectList[0].equals("none")){
            return ans;
        }
        ArrayList<String> statsThatIncreased = new ArrayList<>();
        ArrayList<String> statsThatDecreased = new ArrayList<>();
        for(int i = 0; i < effectList.length-1; i+= 2){
            String stat = effectList[i];
            int statChange = Integer.parseInt(effectList[i+1]);
            switch(stat){
                case("ATK"):
                    target.changeAtkMultiplier(statChange);
                    break;
                case("SpATK"):
                    target.changeSpAtkMultiplier(statChange);
                    break;
                case("DEF"):
                    target.changeDefMultiplier(statChange);
                    break;
                case("SpDEF"):
                    target.changeSpDefMultiplier(statChange);
                    break;
                case("SPD"):
                    target.changeSpdMultiplier(statChange);
                    break;
                default:
                    System.out.println("error in stat decreasing file, " + stat);
            }
            if(statChange > 0){
                statsThatIncreased.add(stat);
            }
            else{
                statsThatDecreased.add(stat);
            }
        }
        if(!statsThatDecreased.isEmpty()){
            ans[0][0] = target.Name + "'s stats decreased...";
            ans[0][1] = statsChanged(statsThatDecreased);
            if(!statsThatIncreased.isEmpty()){
                ans[1][0] = target.Name + "'s stats increased!";
                ans[1][1] = statsChanged(statsThatIncreased);
            }
        }
        else{
            ans[0][0] = target.Name + "'s stats increased!";
            ans[0][1] = statsChanged(statsThatIncreased);
        }

        return ans;
    }

    public boolean protectValidity(Pokemon user){ //if protect is used too often it fails
        Move[] previousMoves = user.getPreviousMoves();
        if(previousMoves[0] == null || !previousMoves[0].StatusType.equals("Protect")){
            //first time trying to protect, successful
            user.setProtecting(true);
            return true;
        }
        double protectChance;
        if(previousMoves[1] == null ||!previousMoves[1].StatusType.equals("Protect")){//protect used twice in a row
            protectChance = Math.random();
            if(protectChance>0.66){ //1/3 success rate
                user.setProtecting(true);
            }
        }
        else if(previousMoves[2] == null || !previousMoves[2].StatusType.equals("Protect")){//protect used 3 times in a row, tiny chance
            protectChance = Math.random();
            if(protectChance>0.88){ //1/9 success rate
                user.setProtecting(true);
            }
        }
        //always fails
        return user.isProtecting();
    }
    public String statsChanged(ArrayList<String> statList){
        String ans = "";
        for (String stat : statList) {
            ans += stat;
            ans += ", ";
        }
        ans = ans.substring(0, ans.length()-2);
        return ans;
    }


}
