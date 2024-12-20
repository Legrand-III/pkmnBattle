package States;

import java.awt.*;

public abstract class AbstractState {
    public abstract void update();
    public abstract void paintComponent(Graphics graphics);
    public abstract void changeStates(String state);
    public void upPressed(){}
    public void downPressed(){}
    public void leftPressed(){}
    public void rightPressed(){}
    public void escapePressed(){}
    public void spacePressed(){}
}
