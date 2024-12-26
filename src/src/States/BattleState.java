package States;

import Main.PlayerKeyInputs;
import PokemonStuff.*;

import java.awt.*;
import java.util.AbstractMap;

import static Main.GamePanel.*;
import static java.lang.Thread.sleep;


public class BattleState extends AbstractState{
    protected PlayerKeyInputs keyInputs;
    protected Move selectedMove;
    protected Move opponentMove;
    protected AbstractMap.SimpleEntry<Pokemon, Move>[] turnOrder;
    protected int turnPart = 0;
    boolean printed = false;
    String[][] moveText;
    public BattleState(PlayerKeyInputs keyInputs, Move selectedMove,
     Move opponentMove){
        this.keyInputs = keyInputs;
        this.selectedMove = selectedMove;
        this.opponentMove = opponentMove;
        this.turnOrder = calculateTurnOrder(selectedMove, opponentMove);
    }
    @Override
    public void update() {

    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D)graphics;

        graphics2D.drawImage(activePokemon.backSprite, tileSize*4, tileSize*7 - 2, tileSize*4, tileSize*4, null);
        graphics2D.drawImage(opposingPokemon.frontSprite, tileSize*14, tileSize*3, tileSize*4, tileSize*4, null);

        //textbox
        graphics2D.setColor(new Color(0,0,0,150)); //background = black + low opacity
        graphics2D.fillRoundRect(0, tileSize*11, tileSize*22, tileSize*5, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect( 0, tileSize*11 + 2,
                tileSize*22 - 2, tileSize*5 - 4, 25, 25);

        try {
            playTurn(graphics2D);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }



    public AbstractMap.SimpleEntry<Pokemon,Move>[] calculateTurnOrder(Move selectedMove, Move opponentMove){
        AbstractMap.SimpleEntry<Pokemon,Move>[] ans = new AbstractMap.SimpleEntry[2];
        if(selectedMove.Priority == opponentMove.Priority){//same priority = speed determines
            double activeEffSpeed = activePokemon.effectiveStat(activePokemon.Speed, activePokemon.SpdMultiplier);
            double opposingEffSpeed = opposingPokemon.effectiveStat(opposingPokemon.Speed, opposingPokemon.SpdMultiplier);

            if(activeEffSpeed > opposingEffSpeed){//player first
                ans[0] = new AbstractMap.SimpleEntry<>(activePokemon, selectedMove);
                ans[1] = new AbstractMap.SimpleEntry<>(opposingPokemon, opponentMove);
                return ans;
            }
            else if(activeEffSpeed < opposingEffSpeed){//enemy first
                ans[0] = new AbstractMap.SimpleEntry<>(opposingPokemon, opponentMove);
                ans[1] = new AbstractMap.SimpleEntry<>(activePokemon, selectedMove);
                return ans;
            }
            //else, speed tie, random order
            int first = (int)(Math.random()*2);
            if(first == 0){//player first
                ans[0] = new AbstractMap.SimpleEntry<>(activePokemon, selectedMove);
                ans[1] = new AbstractMap.SimpleEntry<>(opposingPokemon, opponentMove);
            }
            else{ //enemy first
                ans[0] = new AbstractMap.SimpleEntry<>(opposingPokemon, opponentMove);
                ans[1] = new AbstractMap.SimpleEntry<>(activePokemon, selectedMove);
            }
            return ans;
        }
        //else, different priorities
        if(selectedMove.Priority > opponentMove.Priority){//player first
            ans[0] = new AbstractMap.SimpleEntry<>(activePokemon, selectedMove);
            ans[1] = new AbstractMap.SimpleEntry<>(opposingPokemon, opponentMove);
        }
        else{//(selectedMove.Priority < opponentMove.Priority) --> enemy first
            ans[0] = new AbstractMap.SimpleEntry<>(opposingPokemon, opponentMove);
            ans[1] = new AbstractMap.SimpleEntry<>(activePokemon, selectedMove);
        }
        return ans;
    }
    public void playTurn(Graphics2D graphics2D) throws InterruptedException {
        Pokemon PokemonA = turnOrder[0].getKey();
        Move MoveA = turnOrder[0].getValue();
        Pokemon PokemonB = turnOrder[1].getKey();
        Move MoveB = turnOrder[1].getValue();
        if(turnPart == 0){
            printUsedMove(PokemonA.Name, MoveA.Name, graphics2D);
            turnPart+= 1;
        }
        else if(turnPart == 1){
            sleep(2000);
            moveText = MoveA.useMove(PokemonA, PokemonB);
            MoveA.RemainingPP -=1;
            PokemonA.addUsedMove(MoveA);
            if(moveText[0][0] != null) {
                printMoveOutput(graphics2D, 0);
                printed = true;
            }
            turnPart+=1;

        }
        else if(turnPart == 2){
            if(printed) {sleep(2000);}
            if(moveText[1][0] != null){
                printMoveOutput(graphics2D, 1);
                turnPart+=1;
                printed = true;
            }
            else{
                turnPart+=1;
                printed = false;
            }

        }

        else if(turnPart == 3){
            moveText = null;
            if(PokemonB.CurrentHealth > 0){
                printUsedMove(PokemonB.Name, MoveB.Name, graphics2D);
                turnPart+=1;
            }
            else{
                graphics2D.drawString(PokemonB.Name + " fainted.", tileSize, tileSize *13 - (tileSize/8));
                turnPart = 4;
            }
        }
        else if(turnPart == 4){
            sleep(2000);
            moveText = MoveB.useMove(PokemonB, PokemonA);
            MoveB.RemainingPP -=1;
            PokemonB.addUsedMove(MoveB);
            if(moveText[0][0] != null) {
                printMoveOutput(graphics2D, 0);
                printed = true;
            }
            turnPart+=1;
        }
        else if(turnPart == 5){
            if(printed){sleep(2000);}
            if(moveText[1][0] != null){
                printMoveOutput(graphics2D, 1);
                turnPart+=1;
                printed = true;
            }
            else{
                turnPart+=1;
                printed = false;
            }
        }
        else{
            if(printed){sleep(2000);}
            activePokemon.protecting = false;
            opposingPokemon.protecting = false;
            this.keyInputs.state = new SelectionState(this.keyInputs);
        }



    }

    public void printUsedMove(String user, String move, Graphics2D graphics2D){
        graphics2D.setFont(new Font("times", Font.BOLD, 48));
        graphics2D.drawString(user + " used", tileSize, tileSize *13 - (tileSize/8));
        graphics2D.drawString(move + "!",
                tileSize, tileSize *15 - (tileSize/8));
    }
    public void printMoveOutput(Graphics2D graphics2D, int index){
        graphics2D.setFont(new Font("times", Font.BOLD, 48));
        graphics2D.drawString(moveText[index][0], tileSize, tileSize *13 - (tileSize/8));
        graphics2D.drawString(moveText[index][1],
                tileSize, tileSize *15 - (tileSize/8));
    }


}
