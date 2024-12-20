package States;

import Main.*;

import java.awt.*;

import static Main.GamePanel.tileSize;

public class BattleState extends AbstractState{
    private GamePanel gp;
    private SubState subState;
    protected int selectedOption;
    public BattleState(GamePanel gamePanel){
        this.gp = gamePanel;
        selectedOption = 0;
        //      [FIGHT]-0  |   Status-1
        //      Pokemon-2  |    Quit-3
        this.subState = new mainPart(this);
    }
    @Override
    public void update() {
        subState.update();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D)graphics;
        subState.draw(graphics2D);

        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.setColor(Color.red);
        switch (selectedOption){
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

    @Override
    public void changeStates(String state){

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
    public void escapePressed(){}
    @Override
    public void spacePressed(){
        subState.spacePressed();
    }


}

class mainPart implements SubState{
    private BattleState battleState;
    public mainPart(BattleState battleState){
        this.battleState = battleState;
    }
    public void spacePressed(){
        switch(battleState.selectedOption){
            case(0):
                //fight menu
                break;
            case(1):
                //status menu
                break;
            case(2):
                //switch menu
                break;
            case(3):
                //quit menu
                break;
        }
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
        graphics2D.drawString("Fight!", tileSize*12, tileSize *13 - (tileSize/8));
        graphics2D.drawString("Status", tileSize*18, tileSize *13 - (tileSize/8));
        graphics2D.drawString("Pokemon", tileSize*12, tileSize *15 - (tileSize/8));
        graphics2D.drawString("Quit", tileSize*18, tileSize *15 - (tileSize/8));
    }
}
