package PokemonStuff;

import Main.GamePanel;

import java.rmi.UnexpectedException;
import java.util.ArrayList;

public class StatusMove extends Move{
    public StatusMove(String name, String type, String category, int pp, int power, int accuracy, int priority,
                      String effect, String statusType){
        super(name, type, category, pp, power, accuracy, priority);
        this.Effect = effect;
        this.StatusType = statusType;
    }

    public String[][] useMove(Pokemon user, Pokemon target){
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
        if(StatusType.equals("SelfStat")){//target = self instead
            target = user;
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
                    target.AtkMultiplier += statChange;
                    if(target.AtkMultiplier > 6){
                        target.AtkMultiplier = 6;
                    }
                    if(target.AtkMultiplier < -6){
                        target.AtkMultiplier = -6;
                    }

                    break;
                case("SPATK"):
                    target.SpAtkMultiplier += statChange;
                    if(target.SpAtkMultiplier > 6){
                        target.SpAtkMultiplier = 6;
                    }
                    if(target.SpAtkMultiplier < -6){
                        target.SpAtkMultiplier = -6;
                    }
                    break;
                case("DEF"):
                    target.DefMultiplier += statChange;
                    if(target.DefMultiplier > 6){
                        target.DefMultiplier = 6;
                    }
                    if(target.DefMultiplier < -6){
                        target.DefMultiplier = -6;
                    }
                    break;
                case("SPDEF"):
                    target.SpDefMultiplier += statChange;
                    if(target.SpDefMultiplier > 6){
                        target.SpDefMultiplier = 6;
                    }
                    if(target.SpDefMultiplier < -6){
                        target.SpDefMultiplier = -6;
                    }
                    break;
                case("SPD"):
                    target.SpdMultiplier += statChange;
                    if(target.SpdMultiplier > 6){
                        target.SpdMultiplier = 6;
                    }
                    if(target.SpdMultiplier < -6){
                        target.SpdMultiplier = -6;
                    }
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
        Move[] previousMoves = user.previousMoves;
        if(previousMoves[0] == null || !previousMoves[0].StatusType.equals("Protect")){
            //first time trying to protect, successful
            user.protecting = true;
            return true;
        }
        double protectChance;
        if(!previousMoves[1].StatusType.equals("Protect")){//protect used twice in a row
            protectChance = Math.random();
            if(protectChance>0.5){
                user.protecting = true;
            }
        }
        else if(!previousMoves[2].StatusType.equals("Protect")){//protect used 3 times in a row, tiny chance
            protectChance = Math.random();
            if(protectChance>0.75){
                user.protecting = true;
            }
        }
        //always fails
        return user.protecting;
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
