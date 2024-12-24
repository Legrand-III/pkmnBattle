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
    public String StatusType;
    //maybe contact and such later
    public Move(String name, String type, String category, int pp, int power, int accuracy, int priority){
        this.Name = name;
        this.Type = type;
        this.Category = category;
        this.MaxPP = pp;
        this.RemainingPP = pp;
        this.Power = power;
        this.Accuracy = accuracy;
        this.Priority = priority;
    }
    public String[] useMove(Pokemon user, Pokemon target){return new String[0];}

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
        }
        return ans;
    }

}
