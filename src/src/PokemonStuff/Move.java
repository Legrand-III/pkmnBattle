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

}
