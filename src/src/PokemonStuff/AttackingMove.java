package PokemonStuff;


import NonVolatileStatusConditions.*;

import java.util.ArrayList;
import java.util.List;

public class AttackingMove extends Move{
    public AttackingMove(String name, String type, String category, int pp, int power, int accuracy, int priority, String effect, String info1, String info2, String shortenedName){
        super(name, type, category, pp, power, accuracy, priority, info1, info2, shortenedName);
        this.Effect = effect;
    }
    public String[][] useMove(Pokemon user, Pokemon target){
        this.RemainingPP -=1;
        int ansIndex = 0;
        String[][] ans = new String[3][2];
        if(target.protecting){
            ans[0][0] = target.Name + " blocked";
            ans[0][1] = "the hit!";
            return ans;
        }
        if(this.Accuracy - (Math.random() * 100) < 0){
            ans[0][0] = "The attack";
            ans[0][1] = "missed!";
            return ans;
        }

        double effectiveness = calculateEffectiveness(target.Type);
        if(target.Type2 != null){
            effectiveness *= calculateEffectiveness(target.Type2);
        }
        if(effectiveness == 0){
            ans[0][0] = "The target";
            ans[0][1] = "was immune!";
            return ans;
        }
        int critical;

        double sameTypeBonus = 1;
        if(this.Type.equals(user.Type) || this.Type.equals(user.Type2)){
            sameTypeBonus += 0.5;
        }
        int damage;
        if(this.Category.equals("Physical")){//physical attack
            double burn = 1;
            if(user.nonVolatileStatus!= null && user.nonVolatileStatus.Condition.equals("Burn")){burn = 0.5;}
            double targetDefense;
            if(Math.random() > 0.95){
                critical = 2;
                targetDefense = target.Defense;
            }
            else{
                critical = 1;
                targetDefense = target.effectiveStat(target.Defense, target.DefMultiplier);
            }
            System.out.println(user.Name + "'s effective attack = " + user.effectiveStat(user.Attack, user.AtkMultiplier));
            damage = (int)((((22 * this.Power *
                    (user.effectiveStat(user.Attack, user.AtkMultiplier))
                    / (targetDefense)) /50)+2)
                    * effectiveness * critical * sameTypeBonus * burn);
            System.out.println("DMG = " + damage);
        }
        else{//special attack
            double targetSpDefense;
            if(Math.random() > 0.95){
                critical = 2;
                targetSpDefense = target.SpDefense;
            }
            else{
                critical = 1;
                targetSpDefense = target.effectiveStat(target.SpDefense, target.SpDefMultiplier);

            }
            System.out.println(user.Name + "'s effective Special Attack = " + user.effectiveStat(user.SpAttack, user.SpAtkMultiplier));
            damage = (int)((((22 * this.Power *
                    (user.effectiveStat(user.SpAttack, user.SpAtkMultiplier))
                    / (targetSpDefense)) /50)+2)
                    * effectiveness * critical * sameTypeBonus);
            System.out.println("DMG = " + damage);
        }

        target.CurrentHealth -= damage;
        if(target.CurrentHealth < 0){
            target.CurrentHealth = 0;
        }


        if(effectiveness > 1){
            ans[ansIndex][0] = "It's super";
            ans[ansIndex][1] = "effective!";
            ansIndex++;
        }
        else if(effectiveness < 1){
            ans[ansIndex][0] = "It's not very";
            ans[ansIndex][1] = "effective...";
            ansIndex++;
        }
        if(critical > 1){
            ans[ansIndex][0] = "It's a";
            ans[ansIndex][1] = "critical hit!";
            ansIndex++;
        }

        if(!this.Effect.equals("none")){
            ArrayList<String> effectList = new ArrayList<>(List.of(Effect.split("!")));

            switch(effectList.get(0)){
                case("PARALYSIS"):
                    if(Integer.parseInt(effectList.get(1)) - (Math.random() * 100) > 0 &&
                            target.nonVolatileStatus == null && !target.Type.equals("Electric")
                            && (target.Type2 == null || !target.Type2.equals("Electric"))){
                        target.nonVolatileStatus = new Paralysis(target);
                        ans[ansIndex][0] = target.Name;
                        ans[ansIndex][1] = "was paralyzed!";
                    }
                    break;
                case("BURN"):
                    if(Integer.parseInt(effectList.get(1)) - (Math.random() * 100) > 0 &&
                            target.nonVolatileStatus == null && !target.Type.equals("Fire")
                            && (target.Type2 == null || !target.Type2.equals("Fire"))) {
                        target.nonVolatileStatus = new Burn(target);
                        ans[ansIndex][0] = target.Name;
                        ans[ansIndex][1] = "was burnt!";
                    }
                    break;
                case("POISON"):
                    if(Integer.parseInt(effectList.get(1)) - (Math.random() * 100) > 0 &&
                            target.nonVolatileStatus == null && !target.Type.equals("Poison")
                            && (target.Type2 == null || !target.Type2.equals("Poison"))
                            && !target.Type.equals("Steel")
                            && (target.Type2 == null || !target.Type2.equals("Steel"))) {
                        target.nonVolatileStatus = new Poison(target);
                        ans[ansIndex][0] = target.Name;
                        ans[ansIndex][1] = "was poisoned!";
                    }
                    break;
                case("SLEEP"):
                    if(Integer.parseInt(effectList.get(1)) - (Math.random() * 100) > 0 &&
                            target.nonVolatileStatus == null){
                        target.nonVolatileStatus = new Sleep(target);
                        ans[ansIndex][0] = target.Name;
                        ans[ansIndex][1] = "fell asleep!";
                    }
                    break;
                case("FREEZE"):
                    if(Integer.parseInt(effectList.get(1)) - (Math.random() * 100) > 0 &&
                            target.nonVolatileStatus == null && !target.Type.equals("Ice")
                            && (target.Type2 == null || !target.Type2.equals("Ice"))) {
                        target.nonVolatileStatus = new Freeze(target);
                        ans[ansIndex][0] = target.Name;
                        ans[ansIndex][1] = "was frozen solid!";
                    }
                    break;


                case("ENEMYDEF"):
                    if(Integer.parseInt(effectList.get(1)) - (Math.random() * 100) > 0
                    && (target.DefMultiplier > -6 && target.DefMultiplier < 6)){
                        target.DefMultiplier += Integer.parseInt(effectList.get(2));
                        if(target.DefMultiplier > 6){
                            target.DefMultiplier = 6;
                        }
                        else if(target.DefMultiplier < -6){
                            target.DefMultiplier = -6;
                        }
                        ans[ansIndex][0] = target.Name + "'s";
                        ans[ansIndex][1] = "Defense was lowered!";
                    }
                    break;
                case("ENEMYSpDEF"):
                    if(Integer.parseInt(effectList.get(1)) - (Math.random() * 100) > 0
                            && (target.SpDefMultiplier > -6 && target.SpDefMultiplier < 6)){
                        target.SpDefMultiplier += Integer.parseInt(effectList.get(2));
                        if(target.SpDefMultiplier > 6){
                            target.SpDefMultiplier = 6;
                        }
                        else if(target.SpDefMultiplier < -6){
                            target.SpDefMultiplier = -6;
                        }
                        ans[ansIndex][0] = target.Name + "'s";
                        ans[ansIndex][1] = "Special Defense was lowered!";
                    }
                    break;

                case("ENEMYATK"):
                    if(Integer.parseInt(effectList.get(1)) - (Math.random() * 100) > 0
                            && (target.AtkMultiplier > -6 && target.AtkMultiplier < 6)){
                        target.AtkMultiplier += Integer.parseInt(effectList.get(2));
                        if(target.AtkMultiplier > 6){
                            target.AtkMultiplier = 6;
                        }
                        else if(target.AtkMultiplier < -6){
                            target.AtkMultiplier = -6;
                        }
                        ans[ansIndex][0] = target.Name + "'s";
                        ans[ansIndex][1] = "Attack was lowered!";
                    }
                    break;

                case("ENEMYSpATK"):
                    if(Integer.parseInt(effectList.get(1)) - (Math.random() * 100) > 0
                            && (target.SpAtkMultiplier > -6 && target.SpAtkMultiplier < 6)){
                        target.SpAtkMultiplier += Integer.parseInt(effectList.get(2));
                        if(target.SpAtkMultiplier > 6){
                            target.SpAtkMultiplier = 6;
                        }
                        else if(target.SpAtkMultiplier < -6){
                            target.SpAtkMultiplier = -6;
                        }
                        ans[ansIndex][0] = target.Name + "'s";
                        ans[ansIndex][1] = "Special Attack was lowered!";
                    }
                    break;

                case("ENEMYSPD"):
                    if(Integer.parseInt(effectList.get(1)) - (Math.random() * 100) > 0
                            && (target.SpdMultiplier > -6 && target.SpdMultiplier < 6)){
                        target.SpdMultiplier += Integer.parseInt(effectList.get(2));
                        if(target.SpdMultiplier > 6){
                            target.SpdMultiplier = 6;
                        }
                        else if(target.SpdMultiplier < -6){
                            target.SpdMultiplier = -6;
                        }
                        ans[ansIndex][0] = target.Name + "'s";
                        ans[ansIndex][1] = "Speed was lowered!";
                    }
                    break;

                case("SELFDEF"):
                    if(Integer.parseInt(effectList.get(1)) - (Math.random() * 100) > 0
                            && (user.DefMultiplier > -6 && user.DefMultiplier < 6)){
                        user.DefMultiplier += Integer.parseInt(effectList.get(2));
                        if(user.DefMultiplier > 6){
                            user.DefMultiplier = 6;
                        }
                        else if(user.DefMultiplier < -6){
                            user.DefMultiplier = -6;
                        }
                        ans[ansIndex][0] = user.Name + "'s";
                        ans[ansIndex][1] = "Defense was raised!";
                    }
                    break;

                case("SELFSpDEF"):
                    if(Integer.parseInt(effectList.get(1)) - (Math.random() * 100) > 0
                            && (user.SpDefMultiplier > -6 && user.SpDefMultiplier < 6)){
                        user.SpDefMultiplier += Integer.parseInt(effectList.get(2));
                        if(user.SpDefMultiplier > 6){
                            user.SpDefMultiplier = 6;
                        }
                        else if(user.SpDefMultiplier < -6){
                            user.SpDefMultiplier = -6;
                        }
                        ans[ansIndex][0] = user.Name + "'s";
                        ans[ansIndex][1] = "Special Defense was raised!";
                    }
                    break;

                case("SELFATK"):
                    if(Integer.parseInt(effectList.get(1)) - (Math.random() * 100) > 0
                            && (user.AtkMultiplier > -6 && user.AtkMultiplier < 6)){
                        user.AtkMultiplier += Integer.parseInt(effectList.get(2));
                        if(user.AtkMultiplier > 6){
                            user.AtkMultiplier = 6;
                        }
                        else if(user.AtkMultiplier < -6){
                            user.AtkMultiplier = -6;
                        }
                        ans[ansIndex][0] = user.Name + "'s";
                        ans[ansIndex][1] = "Attack was raised!";
                    }
                    break;

                case("SELFSpATK"):
                    if(Integer.parseInt(effectList.get(1)) - (Math.random() * 100) > 0
                            && (user.SpAtkMultiplier > -6 && user.SpAtkMultiplier < 6)){
                        user.SpAtkMultiplier += Integer.parseInt(effectList.get(2));
                        if(user.SpAtkMultiplier > 6){
                            user.SpAtkMultiplier = 6;
                        }
                        else if(user.SpAtkMultiplier < -6){
                            user.SpAtkMultiplier = -6;
                        }
                        ans[ansIndex][0] = user.Name + "'s";
                        ans[ansIndex][1] = "Special Attack was raised!";
                    }
                    break;

                case("SELFSPD"):
                    if(Integer.parseInt(effectList.get(1)) - (Math.random() * 100) > 0
                            && (user.SpdMultiplier > -6 && user.SpdMultiplier < 6)){
                        user.SpdMultiplier += Integer.parseInt(effectList.get(2));
                        if(user.SpdMultiplier > 6){
                            user.SpdMultiplier = 6;
                        }
                        else if(user.SpdMultiplier < -6){
                            user.SpdMultiplier = -6;
                        }
                        ans[ansIndex][0] = user.Name + "'s";
                        ans[ansIndex][1] = "Speed was raised!";
                    }
                    break;

            }

        }
        return ans;
    }

}
