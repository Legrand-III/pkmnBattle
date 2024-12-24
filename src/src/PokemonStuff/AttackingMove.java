package PokemonStuff;


public class AttackingMove extends Move{
    public AttackingMove(String name, String type, String category, int pp, int power, int accuracy, int priority, String effect){
        super(name, type, category, pp, power, accuracy, priority);
        this.Effect = effect;
    }
    public String[] useMove(Pokemon user, Pokemon target){
        String[] ans = new String[2];
        ans[0] = user.Name + " used \n" +
                this.Name;
        if(target.protecting){
            ans[1] = target.Name + " blocked \n" +
                    "the hit!";
            return ans;
        }

        double effectiveness = calculateEffectiveness(target.Type);
        if(target.Type2 != null){
            effectiveness *= calculateEffectiveness(target.Type2);
        }
        int critical = (int)(Math.random() * 2) + 1;
        double sameTypeBonus = 1;
        if(this.Type.equals(user.Type) || this.Type.equals(user.Type2)){
            sameTypeBonus += 0.5;
        }
        int damage;
        if(this.Category.equals("Physical")){//physical attack
            damage = (int)(( (double)(22 * this.Power * (user.Attack / target.Defense) ) /50)+2
                    * effectiveness * critical * sameTypeBonus);
        }
        else{//special attack
            damage = (int)(( (double)(22 * this.Power * (user.SpAttack / target.SpDefense) ) /50)+2
                    * effectiveness * critical * sameTypeBonus);
        }


        target.CurrentHealth -= damage;
        if(target.CurrentHealth < 0){
            target.CurrentHealth = 0;
        }

        if(effectiveness == 0){
            ans[1] = "The target\n" +
                    "was immune!";
        }
        else if(effectiveness > 1){
            ans[1] = "It's super\n" +
                    "effective!";
        }
        else if(effectiveness < 1){
            ans[1] = "It's not very\n" +
                    "effective...";
        }

        return ans;
    }



}
