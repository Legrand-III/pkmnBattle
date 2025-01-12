package States;

import Main.PlayerKeyInputs;
import PokemonStuff.Pokemon;

import java.awt.*;

import static Main.GamePanel.*;

public class StatusState extends AbstractState{
    PlayerKeyInputs keyInputs;
    public StatusState(PlayerKeyInputs keyInputs){
        this.keyInputs = keyInputs;
    }
    @Override
    public void update() {

    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D)graphics;
        graphics2D.setColor(new Color(0,0,0,150)); //background = black + low opacity
        graphics2D.fillRoundRect(0, tileSize*11, screenWidth, tileSize*5, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect( 0, tileSize*11 + 2,
                screenWidth - 2, tileSize*5 - 4, 25, 25);

        //player status
        graphics2D.setColor(new Color(0,0,0,150)); //background = black + low opacity
        graphics2D.fillRoundRect(0, 0, tileSize*11, tileSize*11, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect( 0,2,
                tileSize*11 - 2, tileSize*11 - 4, 25, 25);

        graphics2D.drawImage(activePokemon.frontSprite, tileSize, tileSize, tileSize*2, tileSize*2, null);
        graphics2D.setFont(new Font("times", Font.BOLD|Font.ITALIC, 38));
        graphics2D.drawString(activePokemon.Name, tileSize * 4, tileSize*2 - (tileSize/8));
        graphics2D.drawString("HP: " + activePokemon.CurrentHealth + " / " + activePokemon.MaxHealth,
                tileSize * 4, tileSize*3 - (tileSize/8));

        graphics2D.drawString("ATK: ", tileSize, tileSize*5 - (tileSize/8));
        graphics2D.drawString("DEF: ", tileSize, tileSize*6 - (tileSize/8));
        graphics2D.drawString("SpATK: ", tileSize, tileSize*7 - (tileSize/8));
        graphics2D.drawString("SpDEF: ", tileSize, tileSize*8 - (tileSize/8));
        graphics2D.drawString("SPD: ", tileSize, tileSize*9 - (tileSize/8));

        drawStatChanges(activePokemon, "ATK", tileSize * 5 - 6, graphics2D);
        drawStatChanges(activePokemon, "DEF", tileSize * 5 - 6, graphics2D);
        drawStatChanges(activePokemon, "SpATK", tileSize * 5 - 6, graphics2D);
        drawStatChanges(activePokemon, "SpDEF", tileSize * 5 - 6, graphics2D);
        drawStatChanges(activePokemon, "SPD", tileSize * 5 - 6, graphics2D);


        //enemy status
        graphics2D.setColor(new Color(0,0,0,150)); //background = black + low opacity
        graphics2D.fillRoundRect(tileSize*11, 0, tileSize*11, tileSize*11, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect( tileSize*11,2,
                tileSize*11 - 2, tileSize*11 - 4, 25, 25);

        graphics2D.drawImage(opposingPokemon.frontSprite, tileSize*12, tileSize, tileSize*2, tileSize*2, null);
        graphics2D.setFont(new Font("times", Font.BOLD|Font.ITALIC, 38));
        graphics2D.drawString(opposingPokemon.Name, tileSize * 15, tileSize*2 - (tileSize/8));
        graphics2D.drawString("HP: " + opposingPokemon.CurrentHealth + " / " + opposingPokemon.MaxHealth,
                tileSize * 15, tileSize*3 - (tileSize/8));

        graphics2D.drawString("ATK: ", tileSize*12, tileSize*5 - (tileSize/8));
        graphics2D.drawString("DEF: ", tileSize*12, tileSize*6 - (tileSize/8));
        graphics2D.drawString("SpATK: ", tileSize*12, tileSize*7 - (tileSize/8));
        graphics2D.drawString("SpDEF: ", tileSize*12, tileSize*8 - (tileSize/8));
        graphics2D.drawString("SPD: ", tileSize*12, tileSize*9 - (tileSize/8));

        drawStatChanges(opposingPokemon, "ATK", tileSize * 16 - 6, graphics2D);
        drawStatChanges(opposingPokemon, "DEF", tileSize * 16 - 6, graphics2D);
        drawStatChanges(opposingPokemon, "SpATK", tileSize * 16 - 6, graphics2D);
        drawStatChanges(opposingPokemon, "SpDEF", tileSize * 16 - 6, graphics2D);
        drawStatChanges(opposingPokemon, "SPD", tileSize * 16 - 6, graphics2D);

        if(activePokemon.nonVolatileStatus != null){
            drawStatus(graphics2D, activePokemon);
        }
        if(opposingPokemon.nonVolatileStatus != null){
            drawStatus(graphics2D, opposingPokemon);
        }

    }
    private void drawStatChanges(Pokemon subject, String stat, int xPos, Graphics2D graphics2D){
        graphics2D.setColor(Color.white);
        graphics2D.setFont(new Font("times", Font.BOLD, 38));
        int yPos;
        switch (stat){
            case("ATK"):
                yPos = tileSize*5 - (tileSize/8);
                if(subject.AtkMultiplier < 0){
                    int multiplier = subject.AtkMultiplier*-1;
                    drawStatChanges(xPos, yPos, multiplier, "-", graphics2D);
                }
                else if(subject.AtkMultiplier > 0){
                    drawStatChanges(xPos, yPos, subject.AtkMultiplier, "+", graphics2D);
                }
                else{
                    drawBlankStats(xPos, yPos, graphics2D);
                }
                break;
            case("DEF"):
                yPos = tileSize*6 - (tileSize/8);
                if(subject.DefMultiplier < 0){
                    int multiplier = subject.DefMultiplier*-1;
                    drawStatChanges(xPos, yPos, multiplier, "-", graphics2D);
                }
                else if(subject.DefMultiplier > 0){
                    drawStatChanges(xPos, yPos, subject.DefMultiplier, "+", graphics2D);
                }
                else{
                    drawBlankStats(xPos, yPos, graphics2D);
                }
                break;
            case("SpATK"):
                yPos = tileSize*7 - (tileSize/8);
                if(subject.SpAtkMultiplier < 0){
                    int multiplier = subject.SpAtkMultiplier*-1;
                    drawStatChanges(xPos, yPos, multiplier, "-", graphics2D);
                }
                else if(subject.SpAtkMultiplier > 0){
                    drawStatChanges(xPos, yPos, subject.SpAtkMultiplier, "+", graphics2D);
                }
                else{
                    drawBlankStats(xPos, yPos, graphics2D);
                }
                break;
            case("SpDEF"):
                yPos = tileSize*8 - (tileSize/8);
                if(subject.SpDefMultiplier < 0){
                    int multiplier = subject.SpDefMultiplier*-1;
                    drawStatChanges(xPos, yPos, multiplier, "-", graphics2D);
                }
                else if(subject.SpDefMultiplier > 0){
                    drawStatChanges(xPos, yPos, subject.SpDefMultiplier, "+", graphics2D);
                }
                else{
                    drawBlankStats(xPos, yPos, graphics2D);
                }
                break;
            case("SPD"):
                yPos = tileSize*9 - (tileSize/8);
                if(subject.SpdMultiplier < 0){
                    int multiplier = subject.SpdMultiplier*-1;
                    drawStatChanges(xPos, yPos, multiplier, "-", graphics2D);
                }
                else if(subject.SpdMultiplier > 0){
                    drawStatChanges(xPos, yPos, subject.SpdMultiplier, "+", graphics2D);
                }
                else{
                    drawBlankStats(xPos, yPos, graphics2D);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + stat);
        }
    }
    private void drawStatChanges(int xPos, int yPos, int multiplier, String character, Graphics2D graphics2D){
        int i = 0;
        for(; i < multiplier; i++){
            graphics2D.drawString(character, xPos, yPos);
            xPos+=tileSize;
        }
        if(i < 6){
            for(; i < 6; i++){
                graphics2D.drawString("*", xPos, yPos);
                xPos+=tileSize;
            }
        }
    }
    private void drawBlankStats(int xPos,int yPos, Graphics2D graphics2D){
        for(int i = 0; i < 6; i++){
            graphics2D.drawString("*", xPos, yPos);
            xPos+=tileSize;
        }
    }

    private void drawStatus(Graphics2D graphics2D, Pokemon effectedPokemon) {
        switch (effectedPokemon.nonVolatileStatus.Condition) {
            case ("Burn"):
                if (effectedPokemon.equals(activePokemon)) {
                    graphics2D.setColor(new Color(200, 0, 0, 150));
                    graphics2D.fillRoundRect(tileSize * 9 - 2, tileSize + 4, tileSize * 2 - 2, tileSize + 4, 25, 25);
                    graphics2D.setFont(new Font("times", Font.BOLD, 32));
                    graphics2D.setColor(Color.white);
                    graphics2D.drawString("BRN", tileSize * 9 + tileSize / 4, tileSize * 2 - (tileSize / 8));
                } else {
                    graphics2D.setColor(new Color(200, 0, 0, 150));
                    graphics2D.fillRoundRect(screenWidth - tileSize*2 - 2, tileSize + 4, tileSize * 2 - 2, tileSize + 4, 25, 25);
                    graphics2D.setFont(new Font("times", Font.BOLD, 32));
                    graphics2D.setColor(Color.white);
                    graphics2D.drawString("BRN", screenWidth - tileSize*2 + tileSize / 4, tileSize * 2 - (tileSize / 8));
                }
                break;
            case ("Sleep"):
                if (effectedPokemon.equals(activePokemon)) {
                    graphics2D.setColor(new Color(100, 227, 233, 150));
                    graphics2D.fillRoundRect(tileSize * 9 - 2, tileSize + 4, tileSize * 2 - 2, tileSize + 4, 25, 25);
                    graphics2D.setFont(new Font("times", Font.BOLD, 32));
                    graphics2D.setColor(Color.white);
                    graphics2D.drawString("SLP", tileSize * 9 + tileSize / 4, tileSize * 2 - (tileSize / 8));
                } else {
                    graphics2D.setColor(new Color(100, 227, 233, 150));
                    graphics2D.fillRoundRect(screenWidth - tileSize*2 - 2, tileSize + 4, tileSize * 2 - 2, tileSize + 4, 25, 25);
                    graphics2D.setFont(new Font("times", Font.BOLD, 32));
                    graphics2D.setColor(Color.white);
                    graphics2D.drawString("SLP", screenWidth - tileSize*2 + tileSize / 4, tileSize * 2 - (tileSize / 8));
                }
                break;
            case ("Poison"):
                if (effectedPokemon.equals(activePokemon)) {
                    graphics2D.setColor(new Color(128, 0, 128, 150));
                    graphics2D.fillRoundRect(tileSize * 9 - 2, tileSize + 4, tileSize * 2 - 2, tileSize + 4, 25, 25);
                    graphics2D.setFont(new Font("times", Font.BOLD, 32));
                    graphics2D.setColor(Color.white);
                    graphics2D.drawString("PSN", tileSize * 9 + tileSize / 4, tileSize * 2 - (tileSize / 8));
                } else {
                    graphics2D.setColor(new Color(128, 0, 128, 150));
                    graphics2D.fillRoundRect(screenWidth - tileSize*2 - 2, tileSize + 4, tileSize * 2 - 2, tileSize + 4, 25, 25);
                    graphics2D.setFont(new Font("times", Font.BOLD, 32));
                    graphics2D.setColor(Color.white);
                    graphics2D.drawString("PSN", screenWidth - tileSize*2 + tileSize / 4, tileSize * 2 - (tileSize / 8));
                }
                break;
            case ("Paralysis"):
                if (effectedPokemon.equals(activePokemon)) {
                    graphics2D.setColor(new Color(255, 255, 50, 150));
                    graphics2D.fillRoundRect(tileSize * 9 - 2, tileSize + 4, tileSize * 2 - 2, tileSize + 4, 25, 25);
                    graphics2D.setFont(new Font("times", Font.BOLD, 32));
                    graphics2D.setColor(Color.white);
                    graphics2D.drawString("PRZ", tileSize * 9 + tileSize / 4, tileSize * 2 - (tileSize / 8));
                } else {
                    graphics2D.setColor(new Color(255, 255, 50, 150));
                    graphics2D.fillRoundRect(screenWidth - tileSize*2 - 2, tileSize + 4, tileSize * 2 - 2, tileSize + 4, 25, 25);
                    graphics2D.setFont(new Font("times", Font.BOLD, 32));
                    graphics2D.setColor(Color.white);
                    graphics2D.drawString("PRZ", screenWidth - tileSize*2 + tileSize / 4, tileSize * 2 - (tileSize / 8));
                }
                break;
            case ("Freeze"):
                if (effectedPokemon.equals(activePokemon)) {
                    graphics2D.setColor(new Color(70, 189, 255, 150));
                    graphics2D.fillRoundRect(tileSize * 9 - 2, tileSize + 4, tileSize * 2 - 2, tileSize + 4, 25, 25);
                    graphics2D.setFont(new Font("times", Font.BOLD, 32));
                    graphics2D.setColor(Color.white);
                    graphics2D.drawString("FRZ", tileSize * 9 + tileSize / 4, tileSize * 2 - (tileSize / 8));
                } else {
                    graphics2D.setColor(new Color(70, 189, 255, 150));
                    graphics2D.fillRoundRect(screenWidth - tileSize*2 - 2, tileSize + 4, tileSize * 2 - 2, tileSize + 4, 25, 25);
                    graphics2D.setFont(new Font("times", Font.BOLD, 32));
                    graphics2D.setColor(Color.white);
                    graphics2D.drawString("FRZ", screenWidth - tileSize*2 + tileSize / 4, tileSize * 2 - (tileSize / 8));
                }
                break;
        }
    }

    @Override
    public void escapePressed() {
        keyInputs.state = new SelectionState(keyInputs);
    }
}
