package PokemonStuff;

public class Move {
    public String Name;
    public String Type;
    public String Category;
    public int MaxPP;
    public int RemainingPP;
    public int Power;
    public int Accuracy;
    //maybe contact and such later
    public Move(String name, String type, String category, int pp, int power, int accuracy){
        this.Name = name;
        this.Type = type;
        this.Category = category;
        this.MaxPP = pp;
        this.RemainingPP = pp;
        this.Power = power;
        this.Accuracy = accuracy;
    }

}
