package States;

import Main.PlayerKeyInputs;
import PokemonStuff.Switch;
import PokemonStuff.Wait;

import java.awt.*;

import static Main.GamePanel.*;

public class TrainerTeamState extends AbstractState{
    protected PlayerKeyInputs keyInputs;
    SubState subState;

    public TrainerTeamState(PlayerKeyInputs keyInputs){
        this.keyInputs = keyInputs;
        this.subState = new selectPokemon(this);
    }

    @Override
    public void update() {

    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D)graphics;
        graphics2D.setFont(new Font("times", Font.BOLD|Font.ITALIC, 38));

        //main background
        graphics2D.setColor(new Color(0,0,0,150)); //background = black + low opacity
        graphics2D.fillRoundRect(0, 0, screenWidth, tileSize*11, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect( 0,2,
                screenWidth - 2, tileSize*11 - 4, 25, 25);

        //active pokemon
        if(activePokemon.CurrentHealth == 0){
            graphics2D.setColor(new Color(255,0,0,150)); //background = red + low opacity
        }
        else {
            graphics2D.setColor(new Color(255, 255, 255, 150)); //background = white + low opacity
        }
        graphics2D.fillRoundRect(tileSize, tileSize*3, tileSize*9, tileSize*5, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.drawRoundRect(tileSize, tileSize*3 + 2,
                tileSize*9 - 2, tileSize*5 - 4, 25, 25);
        graphics2D.drawImage(activePokemon.frontSprite, tileSize*2, tileSize*4 - 2, tileSize*2, tileSize*2, null);
        graphics2D.drawString(activePokemon.Name, tileSize*5, tileSize*5 - (tileSize/8));
        graphics2D.drawString("HP: " + activePokemon.CurrentHealth + " / " + activePokemon.MaxHealth,
                tileSize*4, tileSize*7 - (tileSize/8));

        //pokemon b
        if(player.team[1].CurrentHealth == 0){
            graphics2D.setColor(new Color(255,0,0,150)); //background = red + low opacity
        }
        else {
            graphics2D.setColor(new Color(255, 255, 255, 150)); //background = white + low opacity
        }
        graphics2D.fillRoundRect(tileSize*12, tileSize, tileSize*9, tileSize*4, 35, 35);


        graphics2D.setColor(Color.white);
        graphics2D.drawRoundRect(tileSize*12, tileSize + 2,
                tileSize*9 - 2, tileSize*4 - 4, 25, 25);
        graphics2D.drawImage(player.team[1].frontSprite, tileSize*13, tileSize*2 - 2, tileSize*2, tileSize*2, null);
        graphics2D.drawString(player.team[1].Name, tileSize*16, tileSize*3 - (tileSize/8));
        graphics2D.drawString("HP: " + player.team[1].CurrentHealth + " / " + player.team[1].MaxHealth,
                tileSize*15, tileSize*4 - (tileSize/8));

        //pokemon c
        if(player.team[2].CurrentHealth == 0){
            graphics2D.setColor(new Color(255,0,0,150)); //background = red + low opacity
        }
        else {
            graphics2D.setColor(new Color(255, 255, 255, 150)); //background = white + low opacity
        }
        graphics2D.fillRoundRect(tileSize*12, tileSize*6, tileSize*9, tileSize*4, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.drawRoundRect(tileSize*12, tileSize*6 + 2,
                tileSize*9 - 2, tileSize*4 - 4, 25, 25);
        graphics2D.drawImage(player.team[2].frontSprite, tileSize*13, tileSize*7 - 2, tileSize*2, tileSize*2, null);
        graphics2D.drawString(player.team[2].Name, tileSize*16, tileSize*8 - (tileSize/8));
        graphics2D.drawString("HP: " + player.team[2].CurrentHealth + " / " + player.team[2].MaxHealth,
                tileSize*15, tileSize*9 - (tileSize/8));

        subState.draw(graphics2D);


    }

    @Override
    public void upPressed() {
        subState.upPressed();
    }

    @Override
    public void downPressed() {
        subState.downPressed();
    }

    @Override
    public void rightPressed() {
        subState.rightPressed();
    }

    @Override
    public void leftPressed() {
        subState.leftPressed();
    }

    @Override
    public void escapePressed() {
        subState.escapePressed();
    }

    @Override
    public void spacePressed() {
        subState.spacePressed();
    }
}
class selectPokemon extends SubState{
    TrainerTeamState trainerTeamState;
    int optionNum = 0;
    public selectPokemon(TrainerTeamState trainerTeamState){
        this.trainerTeamState = trainerTeamState;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D graphics2D) {
        trainerTeamState.drawTextBox(graphics2D, columns, 5);

        graphics2D.setFont(new Font("times", Font.BOLD, 48));
        graphics2D.drawString("Who will you", tileSize, tileSize *13 - (tileSize/8));
        graphics2D.drawString("swap into?", tileSize, tileSize *15 - (tileSize/8));

        switch(optionNum){
            case(0):
                graphics2D.setColor(Color.red);
                graphics2D.drawRoundRect(tileSize, tileSize*3 + 2,
                        tileSize*9 - 2, tileSize*5 - 4, 25, 25);
                break;
            case(1):
                graphics2D.setColor(Color.red);
                graphics2D.drawRoundRect(tileSize*12, tileSize + 2,
                        tileSize*9 - 2, tileSize*4 - 4, 25, 25);
                break;
            case(2):
                graphics2D.setColor(Color.red);
                graphics2D.drawRoundRect(tileSize*12, tileSize*6 + 2,
                        tileSize*9 - 2, tileSize*4 - 4, 25, 25);

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + optionNum);
        }
    }

    public void upPressed() {
        if(optionNum == 1){
            optionNum = 2;
        }
        else{
            optionNum = 1;
        }
    }

    public void downPressed() {
        if(optionNum == 2){
            optionNum = 1;
        }
        else{
            optionNum = 2;
        }
    }

    public void rightPressed() {
        if(optionNum == 0){
            optionNum = 1;
        }
        else{
            optionNum = 0;
        }
    }

    public void leftPressed() {
        if(optionNum == 0){
            optionNum = 1;
        }
        else{
            optionNum = 0;
        }
    }

    @Override
    public void spacePressed() {
        trainerTeamState.subState = new summaryOrSwitch(trainerTeamState, optionNum);
    }

    @Override
    public void escapePressed() {
        trainerTeamState.keyInputs.state = new SelectionState(trainerTeamState.keyInputs);
    }
}
class summaryOrSwitch extends SubState{
    TrainerTeamState trainerTeamState;
    int selectedPokemon;
    int optionNum = 0;
    public summaryOrSwitch(TrainerTeamState trainerTeamState, int selectedPokemon){
        this.trainerTeamState = trainerTeamState;
        this.selectedPokemon = selectedPokemon;
    }
    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D graphics2D) {

        trainerTeamState.drawTextBox(graphics2D, 11, 5);

        trainerTeamState.drawOptionsBox(graphics2D, 11, 11, 5);

        graphics2D.setFont(new Font("times", Font.BOLD, 48));
        graphics2D.drawString("What would you", tileSize, tileSize *13 - (tileSize/8));
        graphics2D.drawString("like to do?", tileSize, tileSize *15 - (tileSize/8));

        graphics2D.drawString("Swap in!", tileSize*15, tileSize *13 - (tileSize/8));
        graphics2D.drawString("Summary", tileSize*12, tileSize *15 - (tileSize/8));
        graphics2D.drawString("Back", tileSize*18, tileSize *15 - (tileSize/8));

        switch(selectedPokemon){
            case(0):
                graphics2D.setColor(Color.red);
                graphics2D.drawRoundRect(tileSize, tileSize*3 + 2,
                        tileSize*9 - 2, tileSize*5 - 4, 25, 25);
                break;
            case(1):
                graphics2D.setColor(Color.red);
                graphics2D.drawRoundRect(tileSize*12, tileSize + 2,
                        tileSize*9 - 2, tileSize*4 - 4, 25, 25);
                break;
            case(2):
                graphics2D.setColor(Color.red);
                graphics2D.drawRoundRect(tileSize*12, tileSize*6 + 2,
                        tileSize*9 - 2, tileSize*4 - 4, 25, 25);

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + optionNum);
        }

        switch(optionNum){
            case(0):
                graphics2D.setColor(Color.red);
                graphics2D.drawRoundRect(tileSize*15 -5, tileSize *12,
                        tileSize*5 + 7, tileSize + 5, 25, 25);
                break;
            case(1):
                graphics2D.setColor(Color.red);
                graphics2D.drawRoundRect(tileSize*12 -5, tileSize *14,
                        tileSize*5, tileSize + 5, 25, 25);
                break;
            case(2):
                graphics2D.setColor(Color.red);
                graphics2D.drawRoundRect(tileSize*18 -5, tileSize *14,
                        tileSize*3 + 7, tileSize + 5, 25, 25);

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + optionNum);
        }
    }

    public void upPressed() {
        if(optionNum == 0){
            optionNum = 1;
        }
        else{
            optionNum = 0;
        }
    }

    public void downPressed() {
        if(optionNum == 0){
            optionNum = 1;
        }
        else{
            optionNum = 0;
        }
    }

    public void rightPressed() {
        if(optionNum == 2){
            optionNum = 1;
        }
        else if(optionNum == 1){
            optionNum = 2;
        }
    }

    public void leftPressed() {
        if(optionNum == 2){
            optionNum = 1;
        }
        else if(optionNum == 1){
            optionNum = 2;
        }
    }

    @Override
    public void spacePressed() {
        switch(optionNum){
            case(0)://switch out
                if(selectedPokemon == 0){
                    System.out.println("this pokemon is already active!");
                }
                else if(player.team[selectedPokemon].CurrentHealth > 0){//change
                    if(activePokemon.CurrentHealth == 0){
                        trainerTeamState.keyInputs.state = new BattleState(trainerTeamState.keyInputs,
                                new Switch(selectedPokemon, player), new Wait());
                    }
                    else if(opposingPokemon.CurrentHealth != 0) {
                        trainerTeamState.keyInputs.state = new BattleState(trainerTeamState.keyInputs,
                                new Switch(selectedPokemon, player), opposingTrainer.determineMove());
                    }
                    else{

                        if(opposingTrainer.team[1].CurrentHealth != 0){
                            trainerTeamState.keyInputs.state = new BattleState(trainerTeamState.keyInputs,
                                    new Switch(selectedPokemon, player), new Switch(1, opposingTrainer));
                        }
                        else if(opposingTrainer.team[2].CurrentHealth != 0){
                            trainerTeamState.keyInputs.state = new BattleState(trainerTeamState.keyInputs,
                                    new Switch(selectedPokemon, player), new Switch(2, opposingTrainer));
                        }
                        else{
                            trainerTeamState.keyInputs.state = new BattleState(trainerTeamState.keyInputs,
                                    new Switch(selectedPokemon, player), new Wait());
                        }

                    }
                }
                else{
                    System.out.println("This pokemon is not healthy enough to fight!");
                }
                break;
            case(1):
                trainerTeamState.keyInputs.state = new SummaryState(trainerTeamState.keyInputs, selectedPokemon);
                break;
            case(2):
                trainerTeamState.subState = new selectPokemon(trainerTeamState);
                break;
        }
    }

    @Override
    public void escapePressed() {
        trainerTeamState.subState = new selectPokemon(trainerTeamState);
    }
}
