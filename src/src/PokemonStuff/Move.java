package PokemonStuff;

public class Move {
    public String Name;
    public String Type;
    public String Category;
    public int MaxPP;
    public int RemainingPP;
    public int Power;
    public int Accuracy;
    public int Priority;
    public String Effect;
    public String StatusType = "";
    public String MoveInfo1;
    public String MoveInfo2;
    public String ShortenedName;
    //maybe contact and such later
    public Move(String name, String type, String category, int pp, int power, int accuracy, int priority, String info1, String info2, String shortenedName){
        this.Name = name;
        this.Type = type;
        this.Category = category;
        this.MaxPP = pp;
        this.RemainingPP = pp;
        this.Power = power;
        this.Accuracy = accuracy;
        this.Priority = priority;
        this.MoveInfo1 = info1;
        this.MoveInfo2 = info2;
        this.ShortenedName = shortenedName;

    }
    public Move(String input){
        if(input.equals("Switch")){
            this.Priority = 99;
            this.Category = "Switch";
        }
        else if(input.equals("Wait")){
            this.Priority = -99;
            this.Category = "Wait";
        }
        else{
            this.Name = "Struggle";
            this.Type = "None";
            this.Category = "Physical";
            this.Power = 50;
            this.Accuracy = 100;
            this.Priority = 0;
        }
    }
    public String[][] useMove(Pokemon user, Pokemon target){ //struggle
        String[][] ans = new String[2][2];
        if(target.protecting){
            ans[0][0] = target.Name + " blocked";
            ans[0][1] = "the hit!";
            return ans;
        }
        int critical;
        int damage;
            double burn = 1;
            if(user.nonVolatileStatus.Condition.equals("Burn")){burn = 0.5;}
            double targetDefense;
            if(Math.random() > 0.95){
                critical = 2;
                if (target.effectiveStat(target.Defense, target.DefMultiplier) > target.Defense) {
                    targetDefense = target.Defense;
                }
                else{
                    targetDefense = target.effectiveStat(target.Defense, target.DefMultiplier);
                }
            }
            else{
                critical = 1;
                targetDefense = target.effectiveStat(target.Defense, target.DefMultiplier);
            }
            System.out.println(user.Name + "'s effective attack = " + user.effectiveStat(user.Attack, user.AtkMultiplier));
            damage = (int)((((22 * this.Power *
                    (user.effectiveStat(user.Attack, user.AtkMultiplier))
                    / (targetDefense)) /50)+2) * critical  * burn);
            System.out.println("DMG = " + damage);
            target.takeDamage(damage);
        if(critical > 1){
            ans[0][0] = "It's a";
            ans[0][1] = "critical hit!";
        }
        if(ans[0][0] == null){
            ans[0][0] = user.Name + " took";
            ans[0][1] = "recoil damage!";
        }
        else{
            ans[1][0] = user.Name + " took";
            ans[1][1] = "recoil damage!";
        }
        user.takeDamage(user.MaxHealth/4);
        return ans;
    }

    /**
     * 0 = immune
     * 0.5 or 0.25 = not very effective
     *  1 = default
     *  2 or 4 = super effective
     **/
    public double calculateEffectiveness(String type){
        double ans = 1;
        switch(type){
            case("Normal"):
                //weakness
                if(this.Type.equals("Fighting")){
                    ans *=2;
                }
                //immunity
                else if(this.Type.equals("Ghost")){
                    ans *=0;
                }
                break;

            case("Fighting"):
                //weakness
                if(this.Type.equals("Flying") || this.Type.equals("Fairy") || this.Type.equals("Psychic")){
                    ans *= 2;
                }
                //resistances
                else if(this.Type.equals("Rock") || this.Type.equals("Dark") || this.Type.equals("Bug")){
                    ans/=2;
                }
                break;

            case("Flying"):
                //weakness
                if(this.Type.equals("Electric") || this.Type.equals("Rock") || this.Type.equals("Ice")){
                    ans *= 2;
                }
                //immunity
                else if(this.Type.equals("Ground")){
                    ans*=0;
                }
                //resistance
                else if(this.Type.equals("Fighting") || this.Type.equals("Bug") || this.Type.equals("Grass")){
                    ans/=2;
                }
                break;

            case("Poison"):
                //weakness
                if(this.Type.equals("Ground") ||this.Type.equals("Psychic")){
                    ans *= 2;
                }
                //resistance
                else if(this.Type.equals("Fighting") || this.Type.equals("Bug") || this.Type.equals("Poison")
                        || this.Type.equals("Grass") || this.Type.equals("Fairy")){
                    ans/=2;
                }
                break;

            case("Ground"):
                //weakness
                if(this.Type.equals("Grass") || this.Type.equals("Ice") || this.Type.equals("Water")){
                    ans *= 2;
                }
                //immunity
                else if(this.Type.equals("Electric")){
                    ans*=0;
                }
                //resistance
                else if(this.Type.equals("Poison") || this.Type.equals("Rock")){
                    ans/=2;
                }
                break;

            case("Rock"):
                //weakness
                if(this.Type.equals("Fighting") || this.Type.equals("Grass") || this.Type.equals("Ground")
                        || this.Type.equals("Steel") || this.Type.equals("Water")){
                    ans *= 2;
                }
                //resistance
                else if(this.Type.equals("Normal") || this.Type.equals("Flying") || this.Type.equals("Poison")
                ){
                    ans/=2;
                }
                break;

            case("Bug"):
                //weakness
                if(this.Type.equals("Fire") || this.Type.equals("Flying") || this.Type.equals("Rock")){
                    ans *= 2;
                }
                //resistance
                else if(this.Type.equals("Fighting") || this.Type.equals("Ground") || this.Type.equals("Grass")){
                    ans/=2;
                }
                break;

            case("Ghost"):
                //weakness
                if(this.Type.equals("Dark") || this.Type.equals("Ghost")){
                    ans *= 2;
                }
                //immunity
                else if(this.Type.equals("Normal") || this.Type.equals("Fighting")){
                    ans*=0;
                }
                //resistance
                else if(this.Type.equals("Poison") || this.Type.equals("Bug")){
                    ans/=2;
                }
                break;

            case("Steel"):
                //weakness
                if(this.Type.equals("Fire") || this.Type.equals("Fighting") || this.Type.equals("Ground")){
                    ans *= 2;
                }
                else if(this.Type.equals("Poison")){
                    ans *= 0;
                }
                //resistance
                else if(this.Type.equals("Normal") || this.Type.equals("Flying") ||
                        this.Type.equals("Rock") || this.Type.equals("Bug") || this.Type.equals("Steel") ||
                        this.Type.equals("Grass") || this.Type.equals("Psychic") || this.Type.equals("Ice") ||
                        this.Type.equals("Dragon") || this.Type.equals("Fairy")){
                    ans/=2;
                }
                break;

            case("Fire"):
                //weakness
                if(this.Type.equals("Water") || this.Type.equals("Rock") || this.Type.equals("Ground")){
                    ans *= 2;
                }
                //resistance
                else if(this.Type.equals("Steel") || this.Type.equals("Bug") || this.Type.equals("Fire") ||
                        this.Type.equals("Grass") || this.Type.equals("Ice") || this.Type.equals("Fairy")){
                    ans/=2;
                }
                break;

            case("Water"):
                //weakness
                if(this.Type.equals("Grass") || this.Type.equals("Electric")){
                    ans *= 2;
                }
                //resistance
                else if(this.Type.equals("Steel") || this.Type.equals("Fire") ||
                        this.Type.equals("Water") || this.Type.equals("Ice")){
                    ans/=2;
                }
                break;

            case("Grass"):
                //weakness
                if(this.Type.equals("Fire") || this.Type.equals("Bug") || this.Type.equals("Ice")
                        || this.Type.equals("Flying") || this.Type.equals("Poison")){
                    ans *= 2;
                }
                //resistance
                else if(this.Type.equals("Ground") || this.Type.equals("Grass") ||
                        this.Type.equals("Water") || this.Type.equals("Electric")){
                    ans/=2;
                }
                break;

            case("Electric"):
                //weakness
                if(this.Type.equals("Ground")){
                    ans *= 2;
                }
                //resistance
                else if(this.Type.equals("Steel") || this.Type.equals("Flying") ||
                        this.Type.equals("Electric")){
                    ans/=2;
                }
                break;

            case("Psychic"):
                //weakness
                if(this.Type.equals("Dark") || this.Type.equals("Ghost") || this.Type.equals("Bug")){
                    ans *= 2;
                }
                //resistance
                else if(this.Type.equals("Fighting") || this.Type.equals("Psychic")){
                    ans/=2;
                }
                break;

            case("Ice"):
                //weakness
                if(this.Type.equals("Fighting") || this.Type.equals("Fire") || this.Type.equals("Rock")
                        || this.Type.equals("Steel")){
                    ans *= 2;
                }
                //resistance
                else if(this.Type.equals("Ice")){
                    ans/=2;
                }
                break;

            case("Dragon"):
                //weakness
                if(this.Type.equals("Dragon") || this.Type.equals("Fairy") || this.Type.equals("Ice")){
                    ans *= 2;
                }
                //resistance
                else if(this.Type.equals("Fire") || this.Type.equals("Water") ||
                        this.Type.equals("Grass") || this.Type.equals("Electric")){
                    ans/=2;
                }
                break;

            case("Dark"):
                //weakness
                if(this.Type.equals("Bug") || this.Type.equals("Fighting") || this.Type.equals("Fairy")){
                    ans *= 2;
                }
                //immunity
                else if(this.Type.equals("Psychic")){
                    ans*=0;
                }
                //resistance
                else if(this.Type.equals("Ghost") || this.Type.equals("Dark")){
                    ans/=2;
                }
                break;

            case("Fairy"):
                //weakness
                if(this.Type.equals("Steel") || this.Type.equals("Poison")){
                    ans *= 2;
                }
                else if(this.Type.equals("Dragon")){
                    ans *= 0;
                }
                //resistance
                else if(this.Type.equals("Fighting") || this.Type.equals("Bug") ||
                        this.Type.equals("Dark")){
                    ans/=2;
                }
                break;
            case("None"):
                break;
            default:
                System.out.println("error in pokemon typing");
        }
        return ans;
    }

}
