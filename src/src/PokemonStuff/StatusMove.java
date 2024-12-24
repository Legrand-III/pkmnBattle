package PokemonStuff;

import Main.GamePanel;

import java.util.ArrayList;

public class StatusMove extends Move{
    public StatusMove(String name, String type, String category, int pp, int power, int accuracy, int priority,
                      String effect, String statusType){
        super(name, type, category, pp, power, accuracy, priority);
        this.Effect = effect;
        this.StatusType = statusType;
    }

    public String[] useMove(Pokemon user, Pokemon target){
        String[] ans = new String[3];
        if(StatusType.equals("Protect")){
            ans[0] = user.Name + " used\n"
                    + this.Name;
            if(protectValidity(user)){
                ans[1] = null;
            }
            else{
                ans[1] = "But it \n" +
                        "failed...";
            }
            return ans;
        }
        if(StatusType.equals("SelfStatus")){//target = self instead
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
            ans[1] = target.Name + "'s stats decreased\n" +
                    statsChanged(statsThatDecreased);
            if(!statsThatIncreased.isEmpty()){
                ans[2] = target.Name + "'s stats increased\n" +
                        statsChanged(statsThatIncreased);
            }
        }
        else{
            ans[1] = target.Name + "'s stats increased\n" +
                    statsChanged(statsThatIncreased);
        }

        return ans;
    }

    public boolean protectValidity(Pokemon user){ //if protect is used too often it fails
        Move[] previousMoves = user.previousMoves;
        if(previousMoves[0] == null || !previousMoves[0].StatusType.equals("Protect")){
            //first time trying to protect, successful
            user.protecting = true;
        }
        double protectChance;
        if(!previousMoves[1].StatusType.equals("Protect")){//protect used twice in a row
            protectChance = Math.random();
            if(protectChance>0.5){
                user.protecting = true;
            }
        }
        if(!previousMoves[2].StatusType.equals("Protect")){//protect used 3 times in a row, tiny chance
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
