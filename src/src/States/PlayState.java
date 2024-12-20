package States;

import Main.*;

import java.awt.*;

public class PlayState extends AbstractState{
    private GamePanel gp;
    public PlayState(GamePanel gamePanel){
        this.gp = gamePanel;
    }
    @Override
    public void update() {
    }

    @Override
    public void paintComponent(Graphics graphics) {

    }

    @Override
    public void changeStates(String state){

    }
}
