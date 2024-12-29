package States;

import java.awt.*;

public abstract class SubState {
    public abstract void update();
    public abstract void draw(Graphics2D graphics2D);
    public abstract void spacePressed();
    public abstract void escapePressed();
    public void upPressed(){}
    public void downPressed(){}
    public void leftPressed(){}
    public void rightPressed(){}

}
