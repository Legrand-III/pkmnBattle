package States;

import java.awt.*;

public interface SubState {
    public void update();
    public void draw(Graphics2D graphics2D);
    public void spacePressed();
    public void escapePressed();
}
