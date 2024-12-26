package PokemonStuff;


public class AttackingMove extends Move{
    public AttackingMove(String name, String type, String category, int pp, int power, int accuracy, int priority, String effect){
        super(name, type, category, pp, power, accuracy, priority);
        this.Effect = effect;
    }
    public String[][] useMove(Pokemon user, Pokemon target){
        String[][] ans = new String[2][2];
        if(target.protecting){
            ans[0][0] = target.Name + " blocked";
            ans[0][1] = "the hit!";
            return ans;
        }

        double effectiveness = calculateEffectiveness(target.Type);
        if(target.Type2 != null){
            effectiveness *= calculateEffectiveness(target.Type2);
        }
        int critical;
        if(Math.random() > 0.95){
            critical = 2;
        }
        else{
            critical = 1;
        }
        double sameTypeBonus = 1;
        if(this.Type.equals(user.Type) || this.Type.equals(user.Type2)){
            sameTypeBonus += 0.5;
        }
        int damage;
        if(this.Category.equals("Physical")){//physical attack
            damage = (int)(((22 * this.Power *
                    (user.effectiveStat(user.Attack, user.AtkMultiplier))
                    / (target.effectiveStat(target.Defense, target.DefMultiplier))) /50)+2
                    * effectiveness * critical * sameTypeBonus);
        }
        else{//special attack
            damage = (int)(((22 * this.Power *
                    (user.effectiveStat(user.SpAttack, user.SpAtkMultiplier))
                    / (target.effectiveStat(target.SpDefense, target.SpDefMultiplier))) /50)+2
                    * effectiveness * critical * sameTypeBonus);
        }

        target.CurrentHealth -= damage;
        if(target.CurrentHealth < 0){
            target.CurrentHealth = 0;
        }

        if(effectiveness == 0){
            ans[0][0] = "The target";
            ans[0][1] = "was immune!";
        }
        else if(effectiveness > 1){
            ans[0][0] = "It's super";
            ans[0][1] = "effective!";
        }
        else if(effectiveness < 1){
            ans[0][0] = "It's not very";
            ans[0][1] = "effective...";
        }
        if(critical > 1){
            if(ans[0][0] == null){
                ans[0][0] = "It's a";
                ans[0][1] = " critical hit!";
            }
            else{
                ans[1][0] = "It's a";
                ans[1][1] = " critical hit!";
            }
        }

        return ans;
    }



}
