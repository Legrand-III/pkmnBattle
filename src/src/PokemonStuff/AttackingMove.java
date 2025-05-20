package PokemonStuff;


import NonVolatileStatusConditions.*;

import java.util.ArrayList;
import java.util.List;

import static States.BattleState.turnOrder;

public class AttackingMove extends Move{
    public AttackingMove(String name, String type, String category, int pp, int power, int accuracy, int priority, String effect, String info1, String info2, String shortenedName){
        super(name, type, category, pp, power, accuracy, priority, info1, info2, shortenedName, effect, "none");
    }

    @Override
    public String[][] useMove(Pokemon user, Pokemon target){
        usePP();
        int ansIndex = 0;
        String[][] ans = new String[3][2];
        if(target.isProtecting()){
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

        if(this.Name.equals("Sucker Punch")){
            if(!turnOrder[0].getKey().team()[0].equals(user) || turnOrder[1].getValue().Category.equals("Status")){
                //only works if the user goes first and if the target is attacking
                ans[0][0] = "But it";
                ans[0][1] = "failed...";
                return ans;
            }
        }
        int startingHealth = target.getCurrentHealth();

        if(this.Category.equals("Physical")){//physical attack
            double burn = 1;
            if(user.getNonVolatileStatus()!= null && user.getNonVolatileStatus().Condition.equals("Burn")){burn = 0.5;}
            double targetDefense;
            double userAttack;
            if(Math.random() > 0.95){
                critical = 2;
                if(user.effectiveStat(user.Attack, user.AtkMultiplier()) < user.Attack){
                    userAttack = user.Attack;
                }
                else{
                    userAttack = user.effectiveStat(user.Attack, user.AtkMultiplier());
                }

                if(target.effectiveStat(target.Defense, target.DefMultiplier()) > target.Defense){
                    targetDefense = target.Defense;
                }
                else{
                    targetDefense = target.effectiveStat(target.Defense, target.DefMultiplier());
                }
            }
            else{
                critical = 1;
                userAttack = user.effectiveStat(user.Attack, user.AtkMultiplier());
                targetDefense = target.effectiveStat(target.Defense, target.DefMultiplier());
            }
            System.out.println(user.Name + "'s effective attack = " + user.effectiveStat(user.Attack, user.AtkMultiplier()));
            damage = (int)((((22 * this.Power *
                    userAttack / (targetDefense)) /50)+2)
                    * effectiveness * critical * sameTypeBonus * burn);
            System.out.println("DMG = " + damage);
        }
        else{//special attack
            double targetSpDefense;
            double userSpAttack;
            if(Math.random() > 0.95){
                critical = 2;
                if(user.effectiveStat(user.SpAttack, user.SpAtkMultiplier()) < user.SpAttack){
                    userSpAttack = user.SpAttack;
                }
                else{
                    userSpAttack = user.effectiveStat(user.SpAttack, user.SpAtkMultiplier());
                }

                if(target.effectiveStat(target.SpDefense, target.SpDefMultiplier()) > target.SpDefense){
                    targetSpDefense = target.SpDefense;
                }
                else{
                    targetSpDefense = target.effectiveStat(target.SpDefense, target.SpDefMultiplier());
                }
            }
            else{
                critical = 1;
                userSpAttack = user.effectiveStat(user.SpAttack, user.SpAtkMultiplier());
                targetSpDefense = target.effectiveStat(target.SpDefense, target.SpDefMultiplier());

            }
            System.out.println(user.Name + "'s effective Special Attack = " + user.effectiveStat(user.SpAttack, user.SpAtkMultiplier()));
            damage = (int)((((22 * this.Power *
                    userSpAttack / (targetSpDefense)) /50)+2)
                    * effectiveness * critical * sameTypeBonus);
            System.out.println("DMG = " + damage);
        }

        target.takeDamage(damage);


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
                            target.getNonVolatileStatus() == null && !target.Type.equals("Electric")
                            && (target.Type2 == null || !target.Type2.equals("Electric"))){
                        target.setNonVolatileStatus(new Paralysis(target));
                        ans[ansIndex][0] = target.Name;
                        ans[ansIndex][1] = "was paralyzed!";
                    }
                    break;
                case("BURN"):
                    if(Integer.parseInt(effectList.get(1)) - (Math.random() * 100) > 0 &&
                            target.getNonVolatileStatus() == null && !target.Type.equals("Fire")
                            && (target.Type2 == null || !target.Type2.equals("Fire"))) {
                        target.setNonVolatileStatus(new Burn(target));
                        ans[ansIndex][0] = target.Name;
                        ans[ansIndex][1] = "was burnt!";
                    }
                    break;
                case("POISON"):
                    if(Integer.parseInt(effectList.get(1)) - (Math.random() * 100) > 0 &&
                            target.getNonVolatileStatus() == null && !target.Type.equals("Poison")
                            && (target.Type2 == null || !target.Type2.equals("Poison"))
                            && !target.Type.equals("Steel")
                            && (target.Type2 == null || !target.Type2.equals("Steel"))) {
                        target.setNonVolatileStatus(new Poison(target));
                        ans[ansIndex][0] = target.Name;
                        ans[ansIndex][1] = "was poisoned!";
                    }
                    break;
                case("SLEEP"):
                    if(Integer.parseInt(effectList.get(1)) - (Math.random() * 100) > 0 &&
                            target.getNonVolatileStatus() == null){
                        target.setNonVolatileStatus(new Sleep(target));
                        ans[ansIndex][0] = target.Name;
                        ans[ansIndex][1] = "fell asleep!";
                    }
                    break;
                case("FREEZE"):
                    if(Integer.parseInt(effectList.get(1)) - (Math.random() * 100) > 0 &&
                            target.getNonVolatileStatus() == null && !target.Type.equals("Ice")
                            && (target.Type2 == null || !target.Type2.equals("Ice"))) {
                        target.setNonVolatileStatus(new Freeze(target));
                        ans[ansIndex][0] = target.Name;
                        ans[ansIndex][1] = "was frozen solid!";
                    }
                    break;


                case("ENEMYDEF"):
                    if(Integer.parseInt(effectList.get(1)) - (Math.random() * 100) > 0
                    && (target.DefMultiplier() > -6 && target.DefMultiplier() < 6)){
                        target.changeDefMultiplier(Integer.parseInt(effectList.get(2)));
                        ans[ansIndex][0] = target.Name + "'s";
                        ans[ansIndex][1] = "Defense was lowered!";
                    }
                    break;
                case("ENEMYSpDEF"):
                    if(Integer.parseInt(effectList.get(1)) - (Math.random() * 100) > 0
                            && (target.SpDefMultiplier() > -6 && target.SpDefMultiplier() < 6)){

                        target.changeSpDefMultiplier(Integer.parseInt(effectList.get(2)));
                        ans[ansIndex][0] = target.Name + "'s";
                        ans[ansIndex][1] = "Special Defense was lowered!";
                    }
                    break;

                case("ENEMYATK"):
                    if(Integer.parseInt(effectList.get(1)) - (Math.random() * 100) > 0
                            && (target.AtkMultiplier() > -6 && target.AtkMultiplier() < 6)){
                        target.changeAtkMultiplier(Integer.parseInt(effectList.get(2)));
                        ans[ansIndex][0] = target.Name + "'s";
                        ans[ansIndex][1] = "Attack was lowered!";
                    }
                    break;

                case("ENEMYSpATK"):
                    if(Integer.parseInt(effectList.get(1)) - (Math.random() * 100) > 0
                            && (target.SpAtkMultiplier() > -6 && target.SpAtkMultiplier() < 6)){
                        target.changeSpAtkMultiplier(Integer.parseInt(effectList.get(2)));
                        ans[ansIndex][0] = target.Name + "'s";
                        ans[ansIndex][1] = "Special Attack was lowered!";
                    }
                    break;

                case("ENEMYSPD"):
                    if(Integer.parseInt(effectList.get(1)) - (Math.random() * 100) > 0
                            && (target.SpdMultiplier() > -6 && target.SpdMultiplier() < 6)){
                        target.changeSpdMultiplier(Integer.parseInt(effectList.get(2)));
                        ans[ansIndex][0] = target.Name + "'s";
                        ans[ansIndex][1] = "Speed was lowered!";
                    }
                    break;

                case("SELFDEF"):
                    if(Integer.parseInt(effectList.get(1)) - (Math.random() * 100) > 0
                            && (user.DefMultiplier() > -6 && user.DefMultiplier() < 6)){
                        user.changeDefMultiplier(Integer.parseInt(effectList.get(2)));
                        ans[ansIndex][0] = user.Name + "'s";
                        ans[ansIndex][1] = "Defense was raised!";
                    }
                    break;

                case("SELFSpDEF"):
                    if(Integer.parseInt(effectList.get(1)) - (Math.random() * 100) > 0
                            && (user.SpDefMultiplier() > -6 && user.SpDefMultiplier() < 6)){
                        user.changeSpDefMultiplier(Integer.parseInt(effectList.get(2)));
                        ans[ansIndex][0] = user.Name + "'s";
                        ans[ansIndex][1] = "Special Defense was raised!";
                    }
                    break;


                case("SELFATK"):
                    if(Integer.parseInt(effectList.get(1)) - (Math.random() * 100) > 0
                            && (user.AtkMultiplier() > -6 && user.AtkMultiplier() < 6)){
                        user.changeAtkMultiplier(Integer.parseInt(effectList.get(2)));
                        ans[ansIndex][0] = user.Name + "'s";
                        ans[ansIndex][1] = "Attack was raised!";
                    }
                    break;

                case("SELFSpATK"):
                    if(Integer.parseInt(effectList.get(1)) - (Math.random() * 100) > 0
                            && (user.SpAtkMultiplier() > -6 && user.SpAtkMultiplier() < 6)){
                        user.changeSpAtkMultiplier(Integer.parseInt(effectList.get(2)));
                        ans[ansIndex][0] = user.Name + "'s";
                        ans[ansIndex][1] = "Special Attack was raised!";
                    }
                    break;

                case("SELFSPD"):
                    if(Integer.parseInt(effectList.get(1)) - (Math.random() * 100) > 0
                            && (user.SpdMultiplier() > -6 && user.SpdMultiplier() < 6)){
                        user.changeSpdMultiplier(Integer.parseInt(effectList.get(2)));
                        if(Integer.parseInt(effectList.get(2)) > 0) {
                            ans[ansIndex][0] = user.Name + "'s";
                            ans[ansIndex][1] = "Speed was raised!";
                        }
                        else{
                            ans[ansIndex][0] = user.Name + "'s";
                            ans[ansIndex][1] = "Speed was lowered...";
                        }
                    }
                    break;
                case("RECOIL"):
                    if(effectList.get(1).equals("DMG")){
                        if(damage > startingHealth){
                            damage = startingHealth;
                        }
                        user.takeDamage((int)(damage * ((Double.parseDouble(effectList.get(2)))/100)));

                    }
                    ans[ansIndex][0] = user.Name + " took";
                    ans[ansIndex][1] = "recoil damage!";
                    System.out.println("Recoil = " + (damage * ((Double.parseDouble(effectList.get(2)))/100)));
            }

        }
        return ans;
    }

}
