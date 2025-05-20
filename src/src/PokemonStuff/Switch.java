package PokemonStuff;

import Main.Trainer;

import static Main.GamePanel.*;

public class Switch extends Move{
    public Pokemon previousActive;
    public int newActive;
    public Trainer trainer;

    public Switch(int newActive, Trainer trainer){
        super("Switch");
        this.previousActive = trainer.team()[0];
        this.newActive = newActive;
        this.trainer = trainer;
    }

    @Override
    public String[][] useMove(Pokemon user, Pokemon target) {
        String[][] ans = new String[2][2];
        ans[0][0] = trainer.team()[newActive].Name;
        ans[0][1] = "was sent in!";

        trainer.team()[0] = trainer.team()[newActive];
        trainer.team()[newActive] = previousActive;

        previousActive.switchOut();

        if(trainer.equals(Player())){setActivePokemon(trainer.team()[0]);}

        else{setOpposingPokemon(trainer.team()[0]);}


        return ans;
    }
}
