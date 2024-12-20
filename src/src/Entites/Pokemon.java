package Entites;

import java.awt.image.BufferedImage;

public class Pokemon {
    //Main stuff, static stats + the pokemon
    BufferedImage frontSprite, backSprite;
    String Name;
    int MaxHealth, Attack, Defense, SpAttack, SpDefense, Speed;

    //multipliers, stat changing
    int AtkMultiplier, DefMultiplier, SpAMultiplier, SpDMultiplier, SpdMultiplier;

    public Pokemon(){
        Name = "togepi";
    }

    public void resetStatChanges(){
        AtkMultiplier = 0;
        DefMultiplier = 0;
        SpAMultiplier = 0;
        SpDMultiplier = 0;
        SpdMultiplier = 0;
    }
}
