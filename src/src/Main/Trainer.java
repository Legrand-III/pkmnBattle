package Main;

import PokemonStuff.Pokemon;

public class Trainer {
    public Pokemon[] team;
    public Trainer(Pokemon a, Pokemon b, Pokemon c){
        team = new Pokemon[3];
        team[0] = a;
        team[1] = b;
        team[2] = c;
    }
}
