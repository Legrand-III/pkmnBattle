package States;

import Main.PlayerKeyInputs;
import Main.Trainer;
import PokemonStuff.Pokemon;

import java.awt.*;

import static Main.GamePanel.*;

public class SummaryState extends AbstractState{

    protected PlayerKeyInputs keyInputs;
    SubState subState;
    int[][] optionNum;
    Pokemon[] teamCopy = new Pokemon[3];

    public SummaryState(PlayerKeyInputs keyInputs, int selectedPokemon){
        this.keyInputs = keyInputs;
        int[][] optionNum = new int[2][10];
        teamCopy[0] = player.team[selectedPokemon];
        selectedPokemon+=1;
        if(selectedPokemon > 2){
            selectedPokemon = 0;
        }
        teamCopy[1] = player.team[selectedPokemon];
        selectedPokemon+=1;
        if(selectedPokemon > 2){
            selectedPokemon = 0;
        }
        teamCopy[2] = player.team[selectedPokemon];
    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D)graphics;

        drawTextBox(graphics2D, columns, 5);

        //main background
        graphics2D.setColor(new Color(0,0,0,150)); //background = black + low opacity
        graphics2D.fillRoundRect(0, 0, screenWidth, tileSize*11, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect( 0,2,
                screenWidth - 2, tileSize*11 - 4, 25, 25);

        //selected pokemon
        graphics2D.setColor(new Color(255,255,255,150)); //background = white + low opacity
        graphics2D.fillRoundRect(0, 0, tileSize*11, tileSize*11, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect( 0,2,
                tileSize*11 - 2, tileSize*11 - 4, 25, 25);

        graphics2D.drawImage(teamCopy[0].frontSprite, 0, 0, tileSize*4, tileSize*4, null);
        graphics2D.setFont(new Font("times", Font.BOLD|Font.ITALIC, 38));
        graphics2D.drawString(teamCopy[0].Name, tileSize * 5, tileSize*2 - (tileSize/8));

        //displayed info

        //stats
        graphics2D.drawString("HP: " + teamCopy[0].CurrentHealth + " / " + teamCopy[0].MaxHealth, tileSize, tileSize*5 - (tileSize/8));
        graphics2D.drawString("ATK: " + teamCopy[0].Attack, tileSize, tileSize*6 - (tileSize/8));
        graphics2D.drawString("DEF: " + teamCopy[0].Defense, tileSize, tileSize*7 - (tileSize/8));
        graphics2D.drawString("SpATK: " + teamCopy[0].SpAttack, tileSize, tileSize*8 - (tileSize/8));
        graphics2D.drawString("SpDEF: " + teamCopy[0].SpDefense, tileSize, tileSize*9 - (tileSize/8));
        graphics2D.drawString("SPD: " + teamCopy[0].Speed, tileSize, tileSize*10 - (tileSize/8));
        //moves

        //pokemon b

        //pokemon c
    }

    @Override
    public void update() {

    }

    @Override
    public void escapePressed() {
        keyInputs.state = new TrainerTeamState(keyInputs);
    }
}
