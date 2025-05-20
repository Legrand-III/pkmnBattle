package States;

import Main.PlayerKeyInputs;
import Main.Trainer;
import PokemonStuff.Pokemon;

import java.awt.*;

import static Main.GamePanel.*;

public class SummaryState extends AbstractState{

    protected PlayerKeyInputs keyInputs;
    SubState subState;
    int optionNum;
    Pokemon[] teamCopy = new Pokemon[3];

    public SummaryState(PlayerKeyInputs keyInputs, int selectedPokemon){
        this.keyInputs = keyInputs;
        optionNum = 0;
        teamCopy[0] = Player().team[selectedPokemon];
        selectedPokemon+=1;
        if(selectedPokemon > 2){
            selectedPokemon = 0;
        }
        teamCopy[1] = Player().team[selectedPokemon];
        selectedPokemon+=1;
        if(selectedPokemon > 2){
            selectedPokemon = 0;
        }
        teamCopy[2] = Player().team[selectedPokemon];
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
        if(teamCopy[0].getCurrentHealth() == 0){graphics2D.setColor(new Color(200, 0, 0, 150)); }
        else{graphics2D.setColor(new Color(255,255,255,150));} //background = white + low opacity
        graphics2D.fillRoundRect(0, 0, tileSize*11, tileSize*11, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect( 0,2,
                tileSize*11 - 2, tileSize*11 - 4, 25, 25);

        graphics2D.drawImage(teamCopy[0].frontSprite, 0, 0, tileSize*4, tileSize*4, null);
        graphics2D.setFont(new Font("times", Font.BOLD|Font.ITALIC, 38));
        graphics2D.drawString(teamCopy[0].Name, tileSize * 5, tileSize*2 - (tileSize/8));
        graphics2D.drawString("Type: " + teamCopy[0].Type, tileSize * 5, tileSize*3 - (tileSize/8));
        if(teamCopy[0].Type2 != null){
            graphics2D.drawString(" / " + teamCopy[0].Type2, tileSize * 5, tileSize*4 - (tileSize/8));
        }

        //stats
        graphics2D.drawString("HP: " + teamCopy[0].getCurrentHealth() + " / " + teamCopy[0].MaxHealth, tileSize, tileSize*5 - (tileSize/8));
        graphics2D.drawString("ATK: " + teamCopy[0].Attack, tileSize, tileSize*6 - (tileSize/8));
        graphics2D.drawString("DEF: " + teamCopy[0].Defense, tileSize, tileSize*7 - (tileSize/8));
        graphics2D.drawString("SpATK: " + teamCopy[0].SpAttack, tileSize, tileSize*8 - (tileSize/8));
        graphics2D.drawString("SpDEF: " + teamCopy[0].SpDefense, tileSize, tileSize*9 - (tileSize/8));
        graphics2D.drawString("SPD: " + teamCopy[0].Speed, tileSize, tileSize*10 - (tileSize/8));

        //moves
        graphics2D.setColor(new Color(255,255,255,150)); //background = white + low opacity
        graphics2D.fillRoundRect(tileSize*11, tileSize*3, tileSize*11, tileSize*8, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect(tileSize*11,tileSize*3 + 2,
                tileSize*11 - 2, tileSize*8 - 4, 25, 25);

        graphics2D.drawString("Moves", tileSize*15, tileSize*4 - (tileSize / 8));
        if (optionNum == 2) {graphics2D.setColor(new Color(255,209,220));}
        else{graphics2D.setColor(new Color(255,255,255));}
        graphics2D.drawString(teamCopy[0].moves[0].Name, tileSize*13, tileSize*6 - (tileSize / 8));

        if (optionNum == 3) {graphics2D.setColor(new Color(255,209,220));}
        else{graphics2D.setColor(new Color(255,255,255));}
        graphics2D.drawString(teamCopy[0].moves[1].Name, tileSize*13, tileSize*7 - (tileSize / 8));

        if (optionNum == 4) {graphics2D.setColor(new Color(255,209,220));}
        else{graphics2D.setColor(new Color(255,255,255));}
        graphics2D.drawString(teamCopy[0].moves[2].Name, tileSize*13, tileSize*8 - (tileSize / 8));

        if (optionNum == 5) {graphics2D.setColor(new Color(255,209,220));}
        else{graphics2D.setColor(new Color(255,255,255));}
        graphics2D.drawString(teamCopy[0].moves[3].Name, tileSize*13, tileSize*9 - (tileSize / 8));


        //pokemon b
        if(teamCopy[1].getCurrentHealth() == 0){graphics2D.setColor(new Color(200, 0, 0, 150)); }
        else{graphics2D.setColor(new Color(255,255,255,150));}
        graphics2D.fillRoundRect(tileSize*11, 0, tileSize*5 + tileSize/2, tileSize*3, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect(tileSize*11,2,
                tileSize*5 + tileSize/2 - 2, tileSize*3 - 4, 25, 25);



        //pokemon c
        if(teamCopy[2].getCurrentHealth() == 0){graphics2D.setColor(new Color(200, 0, 0, 150)); }
        else{graphics2D.setColor(new Color(255,255,255,150));}
        graphics2D.fillRoundRect(tileSize*16 + tileSize/2, 0, tileSize*5 + tileSize/2, tileSize*3, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect(tileSize*16 + tileSize/2,2,
                tileSize*5 + tileSize/2 - 2, tileSize*3 - 4, 25, 25);




        if(teamCopy[0].getNonVolatileStatus() != null){
            drawStatus(graphics2D, teamCopy[0]);
        }

        switch(optionNum){
            case(0):
                graphics2D.setColor(new Color(255,170,180,150));
                graphics2D.fillRoundRect(tileSize*11, 0, tileSize*5 + tileSize/2, tileSize*3, 35, 35);
                break;
            case(1):
                graphics2D.setColor(new Color(255,170,180,150));
                graphics2D.fillRoundRect(tileSize*16 + tileSize/2, 0, tileSize*5 + tileSize/2, tileSize*3, 35, 35);
                break;

            case(2):
                graphics2D.setColor(new Color(255,209,220));
                graphics2D.drawString("♡", tileSize*12 + tileSize/4, tileSize*6 - (tileSize / 8));
                break;
            case(3):
                graphics2D.setColor(new Color(255,209,220));
                graphics2D.drawString("♡", tileSize*12 + tileSize/4, tileSize*7 - (tileSize / 8));
                break;
            case(4):
                graphics2D.setColor(new Color(255,209,220));
                graphics2D.drawString("♡", tileSize*12 + tileSize/4, tileSize*8 - (tileSize / 8));
                break;
            case(5):
                graphics2D.setColor(new Color(255,209,220));
                graphics2D.drawString("♡", tileSize*12 + tileSize/4, tileSize*9 - (tileSize / 8));
                break;

        }

        graphics2D.drawImage(teamCopy[1].frontSprite, tileSize*12 + tileSize/2, 0, tileSize*3, tileSize*3, null);
        graphics2D.drawImage(teamCopy[2].frontSprite, tileSize*18 , 0, tileSize*3, tileSize*3, null);

        if(optionNum > 1){
            drawMoveInfo(graphics2D);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void escapePressed() {
        keyInputs.setState(new TrainerTeamState(keyInputs));
    }

    private void drawStatus(Graphics2D graphics2D, Pokemon effectedPokemon) {
        switch (effectedPokemon.getNonVolatileStatus().Condition) {
            case ("Burn"):
                    graphics2D.setColor(new Color(200, 0, 0, 150));
                    graphics2D.fillRoundRect(tileSize * 9 - 2,  4, tileSize * 2 - 2, tileSize + 4, 25, 25);
                    graphics2D.setFont(new Font("times", Font.BOLD, 32));
                    graphics2D.setColor(Color.white);
                    graphics2D.drawString("BRN", tileSize * 9 + tileSize / 4, tileSize - (tileSize / 8));
                break;
            case ("Sleep"):
                    graphics2D.setColor(new Color(100, 227, 233, 150));
                    graphics2D.fillRoundRect(tileSize * 9 - 2, 4, tileSize * 2 - 2, tileSize + 4, 25, 25);
                    graphics2D.setFont(new Font("times", Font.BOLD, 32));
                    graphics2D.setColor(Color.white);
                    graphics2D.drawString("SLP", tileSize * 9 + tileSize / 4,  tileSize - (tileSize / 8));
                break;
            case ("Poison"):
                    graphics2D.setColor(new Color(128, 0, 128, 150));
                    graphics2D.fillRoundRect(tileSize * 9 - 2,  4, tileSize * 2 - 2, tileSize + 4, 25, 25);
                    graphics2D.setFont(new Font("times", Font.BOLD, 32));
                    graphics2D.setColor(Color.white);
                    graphics2D.drawString("PSN", tileSize * 9 + tileSize / 4, tileSize - (tileSize / 8));
                break;
            case ("Paralysis"):
                    graphics2D.setColor(new Color(255, 255, 50, 150));
                    graphics2D.fillRoundRect(tileSize * 9 - 2, 4, tileSize * 2 - 2, tileSize + 4, 25, 25);
                    graphics2D.setFont(new Font("times", Font.BOLD, 32));
                    graphics2D.setColor(Color.white);
                    graphics2D.drawString("PRZ", tileSize * 9 + tileSize / 4, tileSize - (tileSize / 8));
                break;
            case ("Freeze"):
                    graphics2D.setColor(new Color(70, 189, 255, 150));
                    graphics2D.fillRoundRect(tileSize * 9 - 2, 4, tileSize * 2 - 2, tileSize + 4, 25, 25);
                    graphics2D.setFont(new Font("times", Font.BOLD, 32));
                    graphics2D.setColor(Color.white);
                    graphics2D.drawString("FRZ", tileSize * 9 + tileSize / 4, tileSize - (tileSize / 8));
                break;
        }

    }
    private void drawMoveInfo(Graphics2D graphics2D){
        this.drawOptionsBox(graphics2D, 0, 8, 5);

        graphics2D.setFont(new Font("times", Font.BOLD|Font.ITALIC, 30));

        graphics2D.drawString("PP:   " + teamCopy[0].moves[optionNum-2].getRemainingPP() +
                " / " + teamCopy[0].moves[optionNum-2].MaxPP, tileSize , tileSize * 12 - (tileSize/8));

        graphics2D.drawString("Type: " + teamCopy[0].moves[optionNum-2].Type, tileSize, tileSize * 13 - (tileSize/8));
        if(teamCopy[0].moves[optionNum-2].Power == -1){
            graphics2D.drawString("Power: N/A", tileSize, tileSize * 14  - (tileSize/8));

        }
        else{
            graphics2D.drawString("Power: " + teamCopy[0].moves[optionNum-2].Power, tileSize, tileSize * 14 - (tileSize/8));
        }
        if(teamCopy[0].moves[optionNum-2].Accuracy == 999999){
            graphics2D.drawString("Accuracy: N/A", tileSize, tileSize * 15  - (tileSize/8));
        }
        else{
            graphics2D.drawString("Accuracy: " + teamCopy[0].moves[optionNum-2].Accuracy, tileSize, tileSize * 15 - (tileSize/8));

        }

        graphics2D.drawString("Category: " + teamCopy[0].moves[optionNum-2].Category, tileSize, tileSize * 16 - (tileSize/8));

        graphics2D.drawString(teamCopy[0].moves[optionNum-2].MoveInfo1, tileSize * 9, tileSize * 13 - (tileSize/8));
        graphics2D.drawString(teamCopy[0].moves[optionNum-2].MoveInfo2, tileSize * 9, tileSize * 15 - (tileSize/8));

    }

    @Override
    public void upPressed() {
        if(optionNum == 1 || optionNum == 0){
            optionNum = 5;
        }
        else{
            optionNum -=1;
        }
    }

    @Override
    public void downPressed() {
        if(optionNum == 1 || optionNum == 0){
            optionNum = 2;
        }
        else{
            optionNum +=1;
        }
        if(optionNum > 5){
            optionNum = 0;
        }
    }

    @Override
    public void leftPressed() {
        if(optionNum == 1){
            optionNum = 0;
        }
        else if(optionNum == 0){
            optionNum = 1;
        }
    }

    @Override
    public void rightPressed() {
        if(optionNum == 1){
            optionNum = 0;
        }
        else if(optionNum == 0){
            optionNum = 1;
        }
    }

    @Override
    public void spacePressed() {
        if(optionNum == 1 || optionNum == 0){
            Pokemon temp = teamCopy[0];
            teamCopy[0] = teamCopy[optionNum+1];
            teamCopy[optionNum+1] = temp;
        }
    }
}
