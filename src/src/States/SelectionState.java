package States;

import Main.*;
import PokemonStuff.Pokemon;

import java.awt.*;

import static Main.GamePanel.*;


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
    }
    @Override
    public void update() {
        subState.update();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D)graphics;



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

class mainMenu extends SubState{
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
        selectionState.drawTextBox(graphics2D, 11, 5);

        //options box
        selectionState.drawOptionsBox(graphics2D, 11, 11, 5);


        graphics2D.setFont(new Font("times", Font.BOLD, 48));
        graphics2D.drawString("What should", tileSize, tileSize *13 - (tileSize/8));
        graphics2D.drawString(ActivePokemon().Name + " do?",
                tileSize, tileSize *15 - (tileSize/8));
        graphics2D.drawString("Fight!", tileSize*12, tileSize *13 - (tileSize/8));
        graphics2D.drawString("Status", tileSize*18, tileSize *13 - (tileSize/8));
        graphics2D.drawString("Pokemon", tileSize*12, tileSize *15 - (tileSize/8));
        graphics2D.drawString("Quit", tileSize*18, tileSize *15 - (tileSize/8));


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
                selectionState.keyInputs.setState(new StatusState(selectionState.keyInputs));
                break;
            case(2):
                selectionState.keyInputs.setState(new TrainerTeamState(selectionState.keyInputs));
                break;
            case(3):
                selectionState.keyInputs.setState(new QuitState(selectionState.keyInputs));
                break;
        }
    }

    public void escapePressed(){}
}

class fightMenu extends SubState{
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
        selectionState.drawTextBox(graphics2D, 14, 5);

        selectionState.drawOptionsBox(graphics2D, 14, 8, 5);


        graphics2D.setFont(new Font("times", Font.BOLD, 40));
        graphics2D.drawString(ActivePokemon().moves[0].ShortenedName, tileSize, tileSize *13 - (tileSize/8));
        graphics2D.drawString(ActivePokemon().moves[1].ShortenedName, tileSize*8, tileSize *13 - (tileSize/8));
        graphics2D.drawString(ActivePokemon().moves[2].ShortenedName, tileSize, tileSize *15 - (tileSize/8));
        graphics2D.drawString(ActivePokemon().moves[3].ShortenedName, tileSize*8, tileSize *15 - (tileSize/8));

        //move info
            graphics2D.drawString("PP:   " + ActivePokemon().moves[selectionState.selectedOption].getRemainingPP() +
                    " / " + ActivePokemon().moves[selectionState.selectedOption].MaxPP, tileSize * 15 , tileSize * 13 - (tileSize/8));

            graphics2D.drawString("Type: " + ActivePokemon().moves[selectionState.selectedOption].Type, tileSize * 15, tileSize * 15 - (tileSize/8));


        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.setColor(Color.red);
        switch (selectionState.selectedOption){ // highlight option
            case(0):
                graphics2D.drawRoundRect(tileSize-5, tileSize *12,
                        tileSize*5 + tileSize/2 + 5, tileSize + 5, 25, 25);
                break;
            case(1):
                graphics2D.drawRoundRect(tileSize*8 -5, tileSize *12,
                        tileSize*5 + tileSize/2 + 5, tileSize + 5, 25, 25);
                break;
            case(2):
                graphics2D.drawRoundRect(tileSize-5, tileSize *14,
                        tileSize*5 + tileSize/2 + 5, tileSize + 5, 25, 25);
                break;
            case(3):
                graphics2D.drawRoundRect(tileSize*8 -5, tileSize *14,
                        tileSize*5 + tileSize/2 + 5, tileSize + 5, 25, 25);
                break;
        }



    }

    @Override
    public void spacePressed() {
        if(ActivePokemon().moves[selectionState.selectedOption].getRemainingPP() == 0){
            System.out.println("This move has no more PP!");
        }
        else {
            this.selectionState.keyInputs.setState(new BattleState(selectionState.keyInputs,
                    ActivePokemon().moves[selectionState.selectedOption],
                    OpposingTrainer().determineMove()));
        }

    }
    public void escapePressed(){
        selectionState.subState = new mainMenu(selectionState);
    }
}
