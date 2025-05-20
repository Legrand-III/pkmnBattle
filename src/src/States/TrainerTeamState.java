package States;

import Main.PlayerKeyInputs;
import PokemonStuff.Pokemon;
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
        if(ActivePokemon().getCurrentHealth() == 0){
            graphics2D.setColor(new Color(255,0,0,150)); //background = red + low opacity
        }
        else {
            graphics2D.setColor(new Color(255, 255, 255, 150)); //background = white + low opacity
        }
        graphics2D.fillRoundRect(tileSize, tileSize*3, tileSize*9, tileSize*5, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.drawRoundRect(tileSize, tileSize*3 + 2,
                tileSize*9 - 2, tileSize*5 - 4, 25, 25);
        graphics2D.drawImage(ActivePokemon().frontSprite, tileSize*2, tileSize*4 - 2, tileSize*2, tileSize*2, null);
        graphics2D.drawString(ActivePokemon().Name, tileSize*5, tileSize*5 - (tileSize/8));
        graphics2D.drawString("HP: " + ActivePokemon().getCurrentHealth() + " / " + ActivePokemon().MaxHealth,
                tileSize*4, tileSize*7 - (tileSize/8));

        //pokemon b
        if(Player().team()[1].getCurrentHealth() == 0){
            graphics2D.setColor(new Color(255,0,0,150)); //background = red + low opacity
        }
        else {
            graphics2D.setColor(new Color(255, 255, 255, 150)); //background = white + low opacity
        }
        graphics2D.fillRoundRect(tileSize*12, tileSize, tileSize*9, tileSize*4, 35, 35);


        graphics2D.setColor(Color.white);
        graphics2D.drawRoundRect(tileSize*12, tileSize + 2,
                tileSize*9 - 2, tileSize*4 - 4, 25, 25);
        graphics2D.drawImage(Player().team()[1].frontSprite, tileSize*13, tileSize*2 - 2, tileSize*2, tileSize*2, null);
        graphics2D.drawString(Player().team()[1].Name, tileSize*16, tileSize*3 - (tileSize/8));
        graphics2D.drawString("HP: " + Player().team()[1].getCurrentHealth() + " / " + Player().team()[1].MaxHealth,
                tileSize*15, tileSize*4 - (tileSize/8));

        //pokemon c
        if(Player().team()[2].getCurrentHealth() == 0){
            graphics2D.setColor(new Color(255,0,0,150)); //background = red + low opacity
        }
        else {
            graphics2D.setColor(new Color(255, 255, 255, 150)); //background = white + low opacity
        }
        graphics2D.fillRoundRect(tileSize*12, tileSize*6, tileSize*9, tileSize*4, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.drawRoundRect(tileSize*12, tileSize*6 + 2,
                tileSize*9 - 2, tileSize*4 - 4, 25, 25);
        graphics2D.drawImage(Player().team()[2].frontSprite, tileSize*13, tileSize*7 - 2, tileSize*2, tileSize*2, null);
        graphics2D.drawString(Player().team()[2].Name, tileSize*16, tileSize*8 - (tileSize/8));
        graphics2D.drawString("HP: " + Player().team()[2].getCurrentHealth() + " / " + Player().team()[2].MaxHealth,
                tileSize*15, tileSize*9 - (tileSize/8));

        if(Player().team()[0].getNonVolatileStatus() != null){
            drawStatus(graphics2D, Player().team()[0], tileSize*8, tileSize*3);
        }
        if(Player().team()[1].getNonVolatileStatus() != null){
            drawStatus(graphics2D, Player().team()[1], tileSize*19, tileSize);
        }
        if(Player().team()[2].getNonVolatileStatus() != null){
            drawStatus(graphics2D, Player().team()[2], tileSize*19, tileSize*6);
        }


        subState.draw(graphics2D);


    }

    private void drawStatus(Graphics2D graphics2D, Pokemon effectedPokemon, int xPos, int yPos) {
        switch (effectedPokemon.getNonVolatileStatus().Condition) {
            case ("Burn"):
                graphics2D.setColor(new Color(200, 0, 0, 150));
                graphics2D.fillRoundRect(xPos - 2, yPos, tileSize * 2 - 2, tileSize + 4, 25, 25);
                graphics2D.setFont(new Font("times", Font.BOLD, 32));
                graphics2D.setColor(Color.white);
                graphics2D.drawString("BRN", xPos + tileSize / 4, yPos + tileSize - (tileSize / 8));
                break;
            case ("Sleep"):
                graphics2D.setColor(new Color(100, 227, 233, 150));
                graphics2D.fillRoundRect(xPos - 2, yPos, tileSize * 2 - 2, tileSize + 4, 25, 25);
                graphics2D.setFont(new Font("times", Font.BOLD, 32));
                graphics2D.setColor(Color.white);
                graphics2D.drawString("SLP", xPos + tileSize / 4, yPos + tileSize - (tileSize / 8));
                break;
            case ("Poison"):
                graphics2D.setColor(new Color(128, 0, 128, 150));
                graphics2D.fillRoundRect(xPos - 2, yPos, tileSize * 2 - 2, tileSize + 4, 25, 25);
                graphics2D.setFont(new Font("times", Font.BOLD, 32));
                graphics2D.setColor(Color.white);
                graphics2D.drawString("PSN", xPos + tileSize / 4, yPos + tileSize - (tileSize / 8));
                break;
            case ("Paralysis"):
                graphics2D.setColor(new Color(255, 255, 50, 150));
                graphics2D.fillRoundRect(xPos - 2, yPos, tileSize * 2 - 2, tileSize + 4, 25, 25);
                graphics2D.setFont(new Font("times", Font.BOLD, 32));
                graphics2D.setColor(Color.white);
                graphics2D.drawString("PRZ", xPos + tileSize / 4, yPos + tileSize - (tileSize / 8));
                break;
            case ("Freeze"):
                graphics2D.setColor(new Color(70, 189, 255, 150));
                graphics2D.fillRoundRect(xPos - 2, yPos, tileSize * 2 - 2, tileSize + 4, 25, 25);
                graphics2D.setFont(new Font("times", Font.BOLD, 32));
                graphics2D.setColor(Color.white);
                graphics2D.drawString("FRZ", xPos + tileSize / 4, yPos + tileSize - (tileSize / 8));
                break;
        }
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
        if(ActivePokemon().getCurrentHealth() > 0) {
            trainerTeamState.keyInputs.setState(new SelectionState(trainerTeamState.keyInputs));
        }
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
                else if(Player().team()[selectedPokemon].getCurrentHealth() > 0){//change
                    if(ActivePokemon().getCurrentHealth() == 0){
                        trainerTeamState.keyInputs.setState(new BattleState(trainerTeamState.keyInputs,
                                new Switch(selectedPokemon, Player()), new Wait()));
                    }
                    else if(OpposingPokemon().getCurrentHealth() != 0) {
                        trainerTeamState.keyInputs.setState(new BattleState(trainerTeamState.keyInputs,
                                new Switch(selectedPokemon, Player()), OpposingTrainer().determineMove()));
                    }
                    else{

                        if(OpposingTrainer().team()[1].getCurrentHealth() != 0){
                            trainerTeamState.keyInputs.setState(new BattleState(trainerTeamState.keyInputs,
                                    new Switch(selectedPokemon, Player()), new Switch(1, OpposingTrainer())));
                        }
                        else if(OpposingTrainer().team()[2].getCurrentHealth() != 0){
                            trainerTeamState.keyInputs.setState(new BattleState(trainerTeamState.keyInputs,
                                    new Switch(selectedPokemon, Player()), new Switch(2, OpposingTrainer())));
                        }
                        else{
                            trainerTeamState.keyInputs.setState(new BattleState(trainerTeamState.keyInputs,
                                    new Switch(selectedPokemon, Player()), new Wait()));
                        }

                    }
                }
                else{
                    System.out.println("This pokemon is not healthy enough to fight!");
                }
                break;
            case(1):
                trainerTeamState.keyInputs.setState(new SummaryState(trainerTeamState.keyInputs, selectedPokemon));
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
