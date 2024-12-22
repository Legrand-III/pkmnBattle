package States;

import Main.*;
import PokemonStuff.Pokemon;

import java.awt.*;

import static Main.GamePanel.tileSize;
import static Main.GamePanel.activePokemon;
import static Main.GamePanel.opposingPokemon;



public class SelectionState extends AbstractState{
    protected SubState subState;
    protected int selectedOption;
    protected PlayerKeyInputs keyInputs;
    public SelectionState(PlayerKeyInputs keyInputs){
        this.keyInputs = keyInputs;
        selectedOption = 0;
        //      [FIGHT]-0  |   Status-1
        //      Pokemon-2  |    Quit-3
        this.subState = new mainMenu(this);

        //change
       activePokemon = new Pokemon("Temp");
       opposingPokemon = new Pokemon("Evil Temp");
    }
    @Override
    public void update() {
        subState.update();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D)graphics;
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.setColor(Color.red);
        graphics2D.drawRect(tileSize*4, tileSize*7, tileSize*4, tileSize*4);
        graphics2D.drawImage(activePokemon.backSprite, tileSize*4, tileSize*7 - 2, tileSize*4, tileSize*4, null);
        graphics2D.drawImage(opposingPokemon.frontSprite, tileSize*14, tileSize*3, tileSize*4, tileSize*4, null);
        subState.draw(graphics2D);
    }

    @Override
    public void upPressed(){
        if(selectedOption >= 2){
            selectedOption -=2;
        }
    }

    @Override
    public void downPressed(){
        if(selectedOption <= 1){
            selectedOption += 2;
        }
    }

    @Override
    public void leftPressed(){
        if(selectedOption % 2 == 1){
            selectedOption -= 1;
        }
    }

    @Override
    public void rightPressed(){
        if(selectedOption % 2 == 0){
            selectedOption += 1;
        }
    }
    @Override
    public void escapePressed(){
        subState.escapePressed();
    }
    @Override
    public void spacePressed(){
        subState.spacePressed();
    }

}

class mainMenu implements SubState{
    private SelectionState selectionState;
    public mainMenu(SelectionState selectionState){
        this.selectionState = selectionState;
        this.selectionState.selectedOption = 0;
    }
    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D graphics2D) {
        //textbox
        graphics2D.setColor(new Color(0,0,0,150)); //background = black + low opacity
        graphics2D.fillRoundRect(0, tileSize*11, tileSize*11, tileSize*5, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect( 0, tileSize*11 + 2,
                tileSize*11 - 2, tileSize*5 - 4, 25, 25);


        //options box
        graphics2D.setColor(new Color(255,255,255,150)); //background = white + low opacity
        graphics2D.fillRoundRect(tileSize*11, tileSize*11, tileSize*11, tileSize*5, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect(tileSize*11, tileSize*11 + 2,
                tileSize*11 - 2, tileSize*5 - 4, 25, 25);

        graphics2D.setFont(new Font("times", Font.BOLD, 48));
        graphics2D.drawString("What should", tileSize, tileSize *13 - (tileSize/8));
        graphics2D.drawString(activePokemon.Name + " do?",
                tileSize, tileSize *15 - (tileSize/8));
        graphics2D.drawString("Fight!", tileSize*12, tileSize *13 - (tileSize/8));
        graphics2D.drawString("Status", tileSize*18, tileSize *13 - (tileSize/8));
        graphics2D.drawString("Pokemon", tileSize*12, tileSize *15 - (tileSize/8));
        graphics2D.drawString("Quit", tileSize*18, tileSize *15 - (tileSize/8));

        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.setColor(Color.red);
        switch (selectionState.selectedOption){
            case(0):
                graphics2D.drawRoundRect(tileSize*12 -5, tileSize *12,
                        tileSize*3 + 7, tileSize + 5, 25, 25);
                break;
            case(1):
                graphics2D.drawRoundRect(tileSize*18 -5, tileSize *12,
                        tileSize*3 + 7, tileSize + 5, 25, 25);
                break;
            case(2):
                graphics2D.drawRoundRect(tileSize*12 -5, tileSize *14,
                        tileSize*5, tileSize + 5, 25, 25);
                break;
            case(3):
                graphics2D.drawRoundRect(tileSize*18 -5, tileSize *14,
                        tileSize*3 + 7, tileSize + 5, 25, 25);
                break;
        }
    }
    public void spacePressed(){
        switch(selectionState.selectedOption){
            case(0):
                selectionState.subState = new fightMenu(selectionState);
                break;
            case(1):
                //status menu
                break;
            case(2):
                //switch menu
                break;
            case(3):
                selectionState.keyInputs.state = new QuitState(selectionState.keyInputs);
                break;
        }
    }

    public void escapePressed(){}
}

class fightMenu implements SubState{
    private SelectionState selectionState;

    public fightMenu(SelectionState selectionState){
        this.selectionState = selectionState;
        this.selectionState.selectedOption = 0;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D graphics2D) {

        graphics2D.setColor(new Color(255,255,255,150)); //background = black + low opacity
        graphics2D.fillRoundRect(0, tileSize*11, tileSize*14, tileSize*5, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect( 0, tileSize*11 + 2,
                tileSize*14 - 2, tileSize*5 - 4, 25, 25);


        //options box
        graphics2D.setColor(new Color(0,0,0,150)); //background = white + low opacity
        graphics2D.fillRoundRect(tileSize*14, tileSize*11, tileSize*8, tileSize*5, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect(tileSize*14, tileSize*11 + 2,
                tileSize*8 - 2, tileSize*5 - 4, 25, 25);

        graphics2D.setFont(new Font("times", Font.BOLD, 48));
        graphics2D.drawString(activePokemon.moves[0].Name, tileSize, tileSize *13 - (tileSize/8));
        graphics2D.drawString(activePokemon.moves[1].Name, tileSize*8, tileSize *13 - (tileSize/8));
        graphics2D.drawString(activePokemon.moves[2].Name, tileSize, tileSize *15 - (tileSize/8));
        graphics2D.drawString(activePokemon.moves[3].Name, tileSize*8, tileSize *15 - (tileSize/8));

        //move info
            graphics2D.drawString("PP:   " + activePokemon.moves[selectionState.selectedOption].RemainingPP +
                    " / " + activePokemon.moves[selectionState.selectedOption].MaxPP, tileSize * 15 , tileSize * 13 - (tileSize/8));

            graphics2D.drawString("Type: " + activePokemon.moves[selectionState.selectedOption].Type, tileSize * 15, tileSize * 15 - (tileSize/8));


        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.setColor(Color.red);
        switch (selectionState.selectedOption){ // highlight option
            case(0):
                graphics2D.drawRoundRect(tileSize-5, tileSize *12,
                        tileSize*5 + 5, tileSize + 5, 25, 25);
                break;
            case(1):
                graphics2D.drawRoundRect(tileSize*8 -5, tileSize *12,
                        tileSize*5 + 5, tileSize + 5, 25, 25);
                break;
            case(2):
                graphics2D.drawRoundRect(tileSize-5, tileSize *14,
                        tileSize*5 + 5, tileSize + 5, 25, 25);
                break;
            case(3):
                graphics2D.drawRoundRect(tileSize*8 -5, tileSize *14,
                        tileSize*5 + 5, tileSize + 5, 25, 25);
                break;
        }



    }

    @Override
    public void spacePressed() {
        //new state for battle part
        //selection.activePokemon.moves[battleState.selectedOption]
        this.selectionState.keyInputs.state = new BattleState(selectionState.keyInputs, activePokemon,
                activePokemon.moves[selectionState.selectedOption],
                opposingPokemon, opposingPokemon.moves[0] );

    }
    public void escapePressed(){
        selectionState.subState = new mainMenu(selectionState);
    }
}
