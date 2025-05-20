package States;

import Main.PlayerKeyInputs;
import Main.Trainer;
import PokemonStuff.*;

import java.awt.*;
import java.util.AbstractMap;

import static Main.GamePanel.*;
import static java.lang.Thread.sleep;


public class BattleState extends AbstractState{
    protected PlayerKeyInputs keyInputs;
    protected Move selectedMove;
    protected Move opponentMove;
    public static AbstractMap.SimpleEntry<Trainer, Move>[] turnOrder;
    protected int turnPart = 0;
    protected int ansIndex = 0;
    boolean printed = false;
    String[][] moveText;
    public BattleState(PlayerKeyInputs keyInputs, Move selectedMove,
                       Move opponentMove){
        this.keyInputs = keyInputs;
        this.selectedMove = selectedMove;
        this.opponentMove = opponentMove;
        turnOrder = calculateTurnOrder(selectedMove, opponentMove);
    }
    @Override
    public void update() {

    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D)graphics;

        graphics2D.drawImage(ActivePokemon().backSprite, tileSize*4, tileSize*7 - 2, tileSize*4, tileSize*4, null);
        graphics2D.drawImage(OpposingPokemon().frontSprite, tileSize*14, tileSize*3, tileSize*4, tileSize*4, null);

        drawTextBox(graphics2D, columns, 5);

        try {
            playTurn(graphics2D, this.turnPart);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }



    public AbstractMap.SimpleEntry<Trainer,Move>[] calculateTurnOrder(Move selectedMove, Move opponentMove){
        AbstractMap.SimpleEntry<Trainer,Move>[] ans = new AbstractMap.SimpleEntry[2];
        if(selectedMove.Priority == opponentMove.Priority){//same priority = speed determines
            double activeEffSpeed = ActivePokemon().effectiveStat(ActivePokemon().Speed, ActivePokemon().SpdMultiplier());
            if(ActivePokemon().getNonVolatileStatus() != null &&
                    ActivePokemon().getNonVolatileStatus().Condition.equals("Paralysis")){
                activeEffSpeed /=2;
            }
            double opposingEffSpeed = OpposingPokemon().effectiveStat(OpposingPokemon().Speed, OpposingPokemon().SpdMultiplier());
            if(OpposingPokemon().getNonVolatileStatus() != null &&
                    OpposingPokemon().getNonVolatileStatus().Condition.equals("Paralysis")){
                opposingEffSpeed /=2;
            }
            if(activeEffSpeed > opposingEffSpeed){//player first
                ans[0] = new AbstractMap.SimpleEntry<>(Player(), selectedMove);
                ans[1] = new AbstractMap.SimpleEntry<>(OpposingTrainer(), opponentMove);
                return ans;
            }
            else if(activeEffSpeed < opposingEffSpeed){//enemy first
                ans[0] = new AbstractMap.SimpleEntry<>(OpposingTrainer(), opponentMove);
                ans[1] = new AbstractMap.SimpleEntry<>(Player(), selectedMove);
                return ans;
            }
            //else, speed tie, random order
            int first = (int)(Math.random()*2);
            if(first == 0){//player first
                ans[0] = new AbstractMap.SimpleEntry<>(Player(), selectedMove);
                ans[1] = new AbstractMap.SimpleEntry<>(OpposingTrainer(), opponentMove);
            }
            else{ //enemy first
                ans[0] = new AbstractMap.SimpleEntry<>(OpposingTrainer(), opponentMove);
                ans[1] = new AbstractMap.SimpleEntry<>(Player(), selectedMove);
            }
            return ans;
        }
        //else, different priorities
        if(selectedMove.Priority > opponentMove.Priority){//player first
            ans[0] = new AbstractMap.SimpleEntry<>(Player(), selectedMove);
            ans[1] = new AbstractMap.SimpleEntry<>(OpposingTrainer(), opponentMove);
        }
        else{//(selectedMove.Priority < opponentMove.Priority) --> enemy first
            ans[0] = new AbstractMap.SimpleEntry<>(OpposingTrainer(), opponentMove);
            ans[1] = new AbstractMap.SimpleEntry<>(Player(), selectedMove);
        }
        return ans;
    }
    public void playTurn(Graphics2D graphics2D, int turnPart) throws InterruptedException {
        Pokemon PokemonA = turnOrder[0].getKey().team()[0];
        Move MoveA = turnOrder[0].getValue();
        Pokemon PokemonB = turnOrder[1].getKey().team()[0];
        Move MoveB = turnOrder[1].getValue();
        if(turnPart == 0){
            if(MoveA.Category.equals("Wait")) {
                playTurn(graphics2D, 2);
                return;
            }
            if(MoveA.Category.equals("Switch")){
                printUsedMove(PokemonA.Name, MoveA, graphics2D);
                turnPart = 1;
            }
            else if(PokemonA.getNonVolatileStatus() != null && !PokemonA.getNonVolatileStatus().StartOfTurn()){
                PokemonA.getNonVolatileStatus().PrintstatusMessage(graphics2D);
                printed = true;
                turnPart = 3;
            }
            else{
                if(PokemonA.getNonVolatileStatus() != null && PokemonA.getNonVolatileStatus().cureAtStart){
                    PokemonA.getNonVolatileStatus().PrintCured(graphics2D);
                    PokemonA.setNonVolatileStatus(null);
                    printed = true;
                    turnPart = 0;
                }
                else {
                    if (printed) {
                        sleep(2000);
                        printed = false;
                    }
                    printUsedMove(PokemonA.Name, MoveA, graphics2D);
                    turnPart = 1;
                }
            }
        }
        else if(turnPart == 1){
            sleep(2000);
            moveText = MoveA.useMove(PokemonA, PokemonB);
            PokemonA.addUsedMove(MoveA);
            if(moveText[ansIndex][0] != null) {
                printMoveOutput(graphics2D, ansIndex);
                printed = true;
                ansIndex++;
            }
            turnPart = 2;
        }
        else if(turnPart == 2){
            if(printed) {sleep(2000);}
            if(ansIndex < moveText.length && moveText[ansIndex][0] != null){
                printMoveOutput(graphics2D, ansIndex);
                printed = true;
                ansIndex++;
                turnPart = 2;
            }
            else{
                printed = false;
                turnPart = 3;
            }

        }

        else if(turnPart == 3){
            if(printed) {sleep(2000);printed = false;}
            moveText = null;
            ansIndex = 0;
            if(PokemonB.getCurrentHealth() > 0){
                if(MoveB.Category.equals("Wait")) {
                    playTurn(graphics2D, 7);
                    return;
                }
                if(MoveB.Category.equals("Switch")){
                    printUsedMove(PokemonB.Name, MoveB, graphics2D);
                    turnPart = 4;
                }
                else if(PokemonB.getNonVolatileStatus() != null && !PokemonB.getNonVolatileStatus().StartOfTurn()){
                    PokemonB.getNonVolatileStatus().PrintstatusMessage(graphics2D);
                    printed = true;
                    turnPart = 7;
                }
                else{
                    if(PokemonB.getNonVolatileStatus() != null && PokemonB.getNonVolatileStatus().cureAtStart){
                        PokemonB.getNonVolatileStatus().PrintCured(graphics2D);
                        PokemonB.setNonVolatileStatus(null);
                        printed = true;
                        turnPart = 3;
                    }
                    else{
                        printUsedMove(PokemonB.Name, MoveB, graphics2D);
                        turnPart = 4;
                    }
                }
            }
            else{
                PokemonB.setNonVolatileStatus(null);
                turnPart = 7;
            }
        }
        else if(turnPart == 4){
            sleep(2000);
            moveText = MoveB.useMove(PokemonB, PokemonA);
            PokemonB.addUsedMove(MoveB);
            if(moveText[ansIndex][0] != null) {
                printMoveOutput(graphics2D, ansIndex);
                printed = true;
                ansIndex++;

            }
            turnPart = 5;
        }
        else if(turnPart == 5){
            if(printed){sleep(2000);}
            if(ansIndex < moveText.length && moveText[ansIndex][0] != null){
                printMoveOutput(graphics2D, ansIndex);
                printed = true;
                ansIndex++;
                turnPart = 5;
            }
            else{
                printed = false;
                turnPart = 6;
            }

        }
        else if(turnPart == 6){
            if(printed){sleep(2000);}
            printed = false;
            if(PokemonA.getCurrentHealth() == 0){
                PokemonA.setNonVolatileStatus(null);
            }
            turnPart = 7;
        }
        else if(turnPart == 7){
            if(printed){sleep(2000);}
            printed = false;
            if(!MoveA.Category.equals("Wait") && !MoveB.Category.equals("Wait") &&
                    PokemonA.getCurrentHealth() > 0 && PokemonA.getNonVolatileStatus() != null && PokemonA.getNonVolatileStatus().EndofTurn()){
                PokemonA.getNonVolatileStatus().PrintstatusMessage(graphics2D);
                printed = true;
            }
            turnPart++;
        }
        else if(turnPart == 8){
            if(printed){sleep(2000);}
            printed = false;
            if(!MoveA.Category.equals("Wait") && !MoveB.Category.equals("Wait") &&
                    PokemonB.getCurrentHealth() > 0 && PokemonB.getNonVolatileStatus() != null && PokemonB.getNonVolatileStatus().EndofTurn()){
                PokemonB.getNonVolatileStatus().PrintstatusMessage(graphics2D);
                printed = true;
            }
            turnPart++;
        }
        else if(turnPart == 9){
            if(printed){sleep(2000);}
            if(PokemonA.getCurrentHealth() <= 0){
                graphics2D.drawString(PokemonA.Name + " fainted.", tileSize, tileSize *13 - (tileSize/8));
                printed = true;
                PokemonA.setNonVolatileStatus(null);
            }
            else{
                printed = false;
            }
            turnPart = 10;
        }
        else if(turnPart == 10){
            if(printed){sleep(2000);}
            if(PokemonB.getCurrentHealth() <= 0){
                graphics2D.drawString(PokemonB.Name + " fainted.", tileSize, tileSize *13 - (tileSize/8));
                printed = true;
                PokemonB.setNonVolatileStatus(null);
            }
            else{
                printed = false;
            }
            turnPart = 11;
        }
        else{
            if(printed){sleep(2000);}
            ActivePokemon().setProtecting(false);
            OpposingPokemon().setProtecting(false);

            if(ActivePokemon().getCurrentHealth() == 0){
                ActivePokemon().setNonVolatileStatus(null);
                if(Player().team()[0].getCurrentHealth() == 0 && Player().team()[1].getCurrentHealth() == 0 && Player().team()[2].getCurrentHealth() == 0){
                    OpposingPokemon().setNonVolatileStatus(null);
                    this.keyInputs.setState(new ResultsScreen(this.keyInputs));
                    return;
                }
                this.keyInputs.setState(new TrainerTeamState(this.keyInputs));
                return;
            }

            if(OpposingPokemon().getCurrentHealth() == 0){
                OpposingPokemon().setNonVolatileStatus(null);
                if(OpposingTrainer().team()[1].getCurrentHealth() != 0) {
                    this.keyInputs.setState(new BattleState(this.keyInputs, new Wait(), new Switch(1, OpposingTrainer())));
                }
                else if(OpposingTrainer().team()[2].getCurrentHealth() != 0){
                    this.keyInputs.setState(new BattleState(this.keyInputs, new Wait(), new Switch(2, OpposingTrainer())));
                }
                else{
                    System.out.println("you win!");
                    this.keyInputs.setState(new ResultsScreen(this.keyInputs));
                }
                return;
            }

            this.keyInputs.setState(new SelectionState(this.keyInputs));
        }

        this.turnPart = turnPart;

    }

    public void printUsedMove(String user, Move move, Graphics2D graphics2D){
        graphics2D.setFont(new Font("times", Font.BOLD, 48));
        if(!move.Category.equals("Switch")){
            graphics2D.drawString(user + " used", tileSize, tileSize * 13 - (tileSize / 8));
            graphics2D.drawString(move.Name + "!",
                    tileSize, tileSize * 15 - (tileSize / 8));
        }
        else{
            graphics2D.drawString(user + " switched", tileSize, tileSize * 13 - (tileSize / 8));
            graphics2D.drawString("out!",
                    tileSize, tileSize * 15 - (tileSize / 8));
        }
    }
    public void printMoveOutput(Graphics2D graphics2D, int index){
        graphics2D.setFont(new Font("times", Font.BOLD, 48));
        graphics2D.drawString(moveText[index][0], tileSize, tileSize *13 - (tileSize/8));
        graphics2D.drawString(moveText[index][1],
                tileSize, tileSize *15 - (tileSize/8));
    }


}