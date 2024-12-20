package States;

import java.awt.*;

public interface SubState {
    public void update();
    public void draw(Graphics2D graphics);
    public void spacePressed();
}
