package States;

import PokemonStuff.Pokemon;

import java.awt.*;

import static Main.GamePanel.*;
import static Main.GamePanel.tileSize;

public abstract class AbstractState {
    public abstract void update();
    public void paintComponent(Graphics graphics){
        //player health box
        Graphics2D graphics2D = (Graphics2D)graphics;
        graphics2D.setColor(new Color(255,255,255,150)); //background = white + low opacity
        graphics2D.fillRoundRect(tileSize*12, tileSize*8, tileSize*9, tileSize*3, 35, 35);

        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.setColor(Color.white);
        graphics2D.drawRoundRect(tileSize*12, tileSize*8 + 2,
                tileSize*9 - 2, tileSize*3 - 4, 25, 25);

        //enemy health box
        graphics2D.setColor(new Color(255,255,255,150)); //background = white + low opacity
        graphics2D.fillRoundRect(tileSize, tileSize, tileSize*9, tileSize*3, 35, 35);

        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.setColor(Color.white);
        graphics2D.drawRoundRect(tileSize, tileSize + 2,
                tileSize*9 - 2, tileSize*3 - 4, 25, 25);

        if(ActivePokemon().getNonVolatileStatus() != null){
            drawStatus(graphics2D, ActivePokemon());
        }
        if(OpposingPokemon().getNonVolatileStatus() != null){
            drawStatus(graphics2D, OpposingPokemon());
        }


        graphics2D.setFont(new Font("times", Font.BOLD, 48));
        graphics2D.setColor(Color.black);
        graphics2D.drawString(ActivePokemon().Name, tileSize*13, tileSize * 9 - (tileSize/8));
        graphics2D.drawString("HP: " + ActivePokemon().getCurrentHealth() + " / "
                + ActivePokemon().MaxHealth, tileSize*14, tileSize * 11 - (tileSize/8));

        graphics2D.drawString(OpposingPokemon().Name, tileSize*2, tileSize * 2 - (tileSize/8));
        graphics2D.drawString("HP: " + OpposingPokemon().getCurrentHealth() + " / "
                + OpposingPokemon().MaxHealth, tileSize*3, tileSize * 4 - (tileSize/8));

        graphics2D.drawImage(ActivePokemon().backSprite, tileSize*4, tileSize*7 - 2, tileSize*4, tileSize*4, null);
        graphics2D.drawImage(OpposingPokemon().frontSprite, tileSize*14, tileSize*3, tileSize*4, tileSize*4, null);


    }
    public void upPressed(){}
    public void downPressed(){}
    public void leftPressed(){}
    public void rightPressed(){}
    public void escapePressed(){}
    public void spacePressed(){}

    public void drawTextBox(Graphics2D graphics2D, int width, int height){
        graphics2D.setColor(new Color(0,0,0,150)); //background = black + low opacity
        graphics2D.fillRoundRect(0, tileSize*11, tileSize*width, tileSize*height, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect( 0, tileSize*11 + 2,
                tileSize*width - 2, tileSize*height - 4, 25, 25);
    }

    public void drawOptionsBox(Graphics2D graphics2D, int xPos, int width, int height){
        graphics2D.setColor(new Color(255,255,255,150)); //background = white + low opacity
        graphics2D.fillRoundRect(tileSize*xPos, tileSize*11, tileSize*width, tileSize*height, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.drawRoundRect(tileSize*xPos, tileSize*11 + 2,
                tileSize*width - 2, tileSize*height - 4, 25, 25);
    }

    private void drawStatus(Graphics2D graphics2D, Pokemon effectedPokemon){
        switch(effectedPokemon.getNonVolatileStatus().Condition){
            case("Burn"):
                if(effectedPokemon.equals(ActivePokemon())){
                    graphics2D.setColor(new Color(200,0, 0,150));
                    graphics2D.fillRoundRect(tileSize*19 - 2, tileSize*8 + 4, tileSize*2 - 2, tileSize + 4, 25, 25);
                    graphics2D.setFont(new Font("times", Font.BOLD, 32));
                    graphics2D.setColor(Color.black);
                    graphics2D.drawString("BRN", tileSize*19 + tileSize/4, tileSize * 9 - (tileSize/8));
                }
                else{
                    graphics2D.setColor(new Color(200,0, 0,150));
                    graphics2D.fillRoundRect(tileSize*8 - 2, tileSize + 4, tileSize*2 - 2, tileSize + 4, 25, 25);
                    graphics2D.setFont(new Font("times", Font.BOLD, 32));
                    graphics2D.setColor(Color.black);
                    graphics2D.drawString("BRN", tileSize*8 + tileSize/4, tileSize * 2 - (tileSize/8));
                }
                break;
            case("Sleep"):
                if(effectedPokemon.equals(ActivePokemon())){
                        graphics2D.setColor(new Color(100,227, 233,150));
                        graphics2D.fillRoundRect(tileSize*19 - 2, tileSize*8 + 4, tileSize*2 - 2, tileSize + 4, 25, 25);
                        graphics2D.setFont(new Font("times", Font.BOLD, 32));
                        graphics2D.setColor(Color.black);
                        graphics2D.drawString("SLP", tileSize*19 + tileSize/4, tileSize * 9 - (tileSize/8));
                }
                else{
                        graphics2D.setColor(new Color(100,227, 233,150));
                        graphics2D.fillRoundRect(tileSize*8 - 2, tileSize + 4, tileSize*2 - 2, tileSize + 4, 25, 25);
                        graphics2D.setFont(new Font("times", Font.BOLD, 32));
                        graphics2D.setColor(Color.black);
                        graphics2D.drawString("SLP", tileSize*8 + tileSize/4, tileSize * 2 - (tileSize/8));
                }
                break;
            case("Poison"):
                if(effectedPokemon.equals(ActivePokemon())){
                    graphics2D.setColor(new Color(128,0, 128,150));
                    graphics2D.fillRoundRect(tileSize*19 - 2, tileSize*8 + 4, tileSize*2 - 2, tileSize + 4, 25, 25);
                    graphics2D.setFont(new Font("times", Font.BOLD, 32));
                    graphics2D.setColor(Color.black);
                    graphics2D.drawString("PSN", tileSize*19 + tileSize/4, tileSize * 9 - (tileSize/8));
                }
                else{
                    graphics2D.setColor(new Color(128,0, 128,150));
                    graphics2D.fillRoundRect(tileSize*8 - 2, tileSize + 4, tileSize*2 - 2, tileSize + 4, 25, 25);
                    graphics2D.setFont(new Font("times", Font.BOLD, 32));
                    graphics2D.setColor(Color.black);
                    graphics2D.drawString("PSN", tileSize*8 + tileSize/4, tileSize * 2 - (tileSize/8));
                }
                break;
            case("Paralysis"):
                if(effectedPokemon.equals(ActivePokemon())){
                    graphics2D.setColor(new Color(255,255, 50,150));
                    graphics2D.fillRoundRect(tileSize*19 - 2, tileSize*8 + 4, tileSize*2 - 2, tileSize + 4, 25, 25);
                    graphics2D.setFont(new Font("times", Font.BOLD, 32));
                    graphics2D.setColor(Color.black);
                    graphics2D.drawString("PRZ", tileSize*19 + tileSize/4, tileSize * 9 - (tileSize/8));
                }
                else{
                    graphics2D.setColor(new Color(255,255, 50,150));
                    graphics2D.fillRoundRect(tileSize*8 - 2, tileSize + 4, tileSize*2 - 2, tileSize + 4, 25, 25);
                    graphics2D.setFont(new Font("times", Font.BOLD, 32));
                    graphics2D.setColor(Color.black);
                    graphics2D.drawString("PRZ", tileSize*8 + tileSize/4, tileSize * 2 - (tileSize/8));
                }
                break;
            case("Freeze"):
                if(effectedPokemon.equals(ActivePokemon())){
                    graphics2D.setColor(new Color(70,189, 255,150));
                    graphics2D.fillRoundRect(tileSize*19 - 2, tileSize*8 + 4, tileSize*2 - 2, tileSize + 4, 25, 25);
                    graphics2D.setFont(new Font("times", Font.BOLD, 32));
                    graphics2D.setColor(Color.black);
                    graphics2D.drawString("FRZ", tileSize*19 + tileSize/4, tileSize * 9 - (tileSize/8));
                }
                else{
                    graphics2D.setColor(new Color(70,189, 255,150));
                    graphics2D.fillRoundRect(tileSize*8 - 2, tileSize + 4, tileSize*2 - 2, tileSize + 4, 25, 25);
                    graphics2D.setFont(new Font("times", Font.BOLD, 32));
                    graphics2D.setColor(Color.black);
                    graphics2D.drawString("FRZ", tileSize*8 + tileSize/4, tileSize * 2 - (tileSize/8));
                }
                break;
        }
    }
}
