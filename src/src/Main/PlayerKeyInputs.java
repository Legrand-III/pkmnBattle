package Main;

import States.AbstractState;
import States.SelectionState;
import States.TitleScreen;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * reads the players keystrokes and displays an action based on the state of the game
 */
public class PlayerKeyInputs implements KeyListener {
    public final GamePanel gp;

    private AbstractState state;

    public PlayerKeyInputs(GamePanel gp){
        this.gp = gp;
        this.state = new TitleScreen(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * reads the players keystrokes and does the appropriate action based on the key pressed and the current state
     * @param e the key you've pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_W, KeyEvent.VK_UP:
                state.upPressed();
                break;

            case KeyEvent.VK_A, KeyEvent.VK_LEFT:
                state.leftPressed();
                break;

            case KeyEvent.VK_S, KeyEvent.VK_DOWN:
                state.downPressed();
                break;

            case KeyEvent.VK_D, KeyEvent.VK_RIGHT:
                state.rightPressed();
                break;

            case KeyEvent.VK_SPACE, KeyEvent.VK_ENTER:
                state.spacePressed();
                break;

            case KeyEvent.VK_ESCAPE, KeyEvent.VK_X:
                state.escapePressed();
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * gets your current state!
     * @return your current state
     */
    public AbstractState getState() {
        return state;
    }

    /**
     * sets your current state!
     * @param state your new state
     */
    public void setState(AbstractState state) {
        this.state = state;
    }
}
