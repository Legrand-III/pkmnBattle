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
    protected AbstractMap.SimpleEntry<Trainer, Move>[] turnOrder;
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
            double activeEffSpeed = activePokemon.effectiveStat(activePokemon.Speed, activePokemon.SpdMultiplier);
            if(activePokemon.nonVolatileStatus != null &&
                    activePokemon.nonVolatileStatus.Condition.equals("Paralysis")){
                activeEffSpeed /=2;
            }
            double opposingEffSpeed = opposingPokemon.effectiveStat(opposingPokemon.Speed, opposingPokemon.SpdMultiplier);
            if(opposingPokemon.nonVolatileStatus != null &&
                    opposingPokemon.nonVolatileStatus.Condition.equals("Paralysis")){
                opposingEffSpeed /=2;
            }
            if(activeEffSpeed > opposingEffSpeed){//player first
                ans[0] = new AbstractMap.SimpleEntry<>(player, selectedMove);
                ans[1] = new AbstractMap.SimpleEntry<>(opposingTrainer, opponentMove);
                return ans;
            }
            else if(activeEffSpeed < opposingEffSpeed){//enemy first
                ans[0] = new AbstractMap.SimpleEntry<>(opposingTrainer, opponentMove);
                ans[1] = new AbstractMap.SimpleEntry<>(player, selectedMove);
                return ans;
            }
            //else, speed tie, random order
            int first = (int)(Math.random()*2);
            if(first == 0){//player first
                ans[0] = new AbstractMap.SimpleEntry<>(player, selectedMove);
                ans[1] = new AbstractMap.SimpleEntry<>(opposingTrainer, opponentMove);
            }
            else{ //enemy first
                ans[0] = new AbstractMap.SimpleEntry<>(opposingTrainer, opponentMove);
                ans[1] = new AbstractMap.SimpleEntry<>(player, selectedMove);
            }
            return ans;
        }
        //else, different priorities
        if(selectedMove.Priority > opponentMove.Priority){//player first
            ans[0] = new AbstractMap.SimpleEntry<>(player, selectedMove);
            ans[1] = new AbstractMap.SimpleEntry<>(opposingTrainer, opponentMove);
        }
        else{//(selectedMove.Priority < opponentMove.Priority) --> enemy first
            ans[0] = new AbstractMap.SimpleEntry<>(opposingTrainer, opponentMove);
            ans[1] = new AbstractMap.SimpleEntry<>(player, selectedMove);
        }
        return ans;
    }
    public void playTurn(Graphics2D graphics2D, int turnPart) throws InterruptedException {
        Pokemon PokemonA = turnOrder[0].getKey().team[0];
        Move MoveA = turnOrder[0].getValue();
        Pokemon PokemonB = turnOrder[1].getKey().team[0];
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
            else if(PokemonA.nonVolatileStatus != null && !PokemonA.nonVolatileStatus.StartOfTurn()){
                PokemonA.nonVolatileStatus.PrintstatusMessage(graphics2D);
                printed = true;
                turnPart = 3;
            }
            else{
                if(PokemonA.nonVolatileStatus != null && PokemonA.nonVolatileStatus.cureAtStart){
                    PokemonA.nonVolatileStatus.PrintCured(graphics2D);
                    PokemonA.nonVolatileStatus = null;
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
            if(moveText[0][0] != null) {
                printMoveOutput(graphics2D, 0);
                printed = true;
            }
            turnPart = 2;
        }
        else if(turnPart == 2){
            if(printed) {sleep(2000);}
            if(moveText[1][0] != null){
                printMoveOutput(graphics2D, 1);
                printed = true;
            }
            else{
                printed = false;
            }
            turnPart = 3;
        }

        else if(turnPart == 3){
            if(printed) {sleep(2000);printed = false;}
            moveText = null;
            if(PokemonB.CurrentHealth > 0){
                if(MoveB.Category.equals("Wait")) {
                    playTurn(graphics2D, 7);
                    return;
                }
                if(MoveB.Category.equals("Switch")){
                    printUsedMove(PokemonB.Name, MoveB, graphics2D);
                    turnPart = 4;
                }
                else if(PokemonB.nonVolatileStatus != null && !PokemonB.nonVolatileStatus.StartOfTurn()){
                    PokemonB.nonVolatileStatus.PrintstatusMessage(graphics2D);
                    printed = true;
                    turnPart = 7;
                }
                else{
                    if(PokemonB.nonVolatileStatus != null && PokemonB.nonVolatileStatus.cureAtStart){
                        PokemonB.nonVolatileStatus.PrintCured(graphics2D);
                        PokemonB.nonVolatileStatus = null;
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
                PokemonB.nonVolatileStatus = null;
                turnPart = 7;
            }
        }
        else if(turnPart == 4){
            sleep(2000);
            moveText = MoveB.useMove(PokemonB, PokemonA);
            PokemonB.addUsedMove(MoveB);
            if(moveText[0][0] != null) {
                printMoveOutput(graphics2D, 0);
                printed = true;
            }
            turnPart = 5;
        }
        else if(turnPart == 5){
            if(printed){sleep(2000);}
            if(moveText[1][0] != null){
                printMoveOutput(graphics2D, 1);
                printed = true;
            }
            else{
                printed = false;
            }
            turnPart = 6;
        }
        else if(turnPart == 6){
            if(printed){sleep(2000);}
            printed = false;
            if(PokemonA.CurrentHealth == 0){
                PokemonA.nonVolatileStatus = null;
            }
            turnPart = 7;
        }
        else if(turnPart == 7){
            if(printed){sleep(2000);}
            printed = false;
            if(!MoveA.Category.equals("Wait") && !MoveB.Category.equals("Wait") &&
                    PokemonA.CurrentHealth > 0 && PokemonA.nonVolatileStatus != null && PokemonA.nonVolatileStatus.EndofTurn()){
                PokemonA.nonVolatileStatus.PrintstatusMessage(graphics2D);
                printed = true;
            }
            turnPart++;
        }
        else if(turnPart == 8){
            if(printed){sleep(2000);}
            printed = false;
            if(!MoveA.Category.equals("Wait") && !MoveB.Category.equals("Wait") &&
                    PokemonB.CurrentHealth > 0 && PokemonB.nonVolatileStatus != null && PokemonB.nonVolatileStatus.EndofTurn()){
                PokemonB.nonVolatileStatus.PrintstatusMessage(graphics2D);
                printed = true;
            }
            turnPart++;
        }
        else if(turnPart == 9){
            if(printed){sleep(2000);}
            if(PokemonA.CurrentHealth <= 0){
                graphics2D.drawString(PokemonA.Name + " fainted.", tileSize, tileSize *13 - (tileSize/8));
                printed = true;
                PokemonA.nonVolatileStatus = null;
            }
            else{
                printed = false;
            }
            turnPart = 10;
        }
        else if(turnPart == 10){
            if(printed){sleep(2000);}
            if(PokemonB.CurrentHealth <= 0){
                graphics2D.drawString(PokemonB.Name + " fainted.", tileSize, tileSize *13 - (tileSize/8));
                printed = true;
                PokemonB.nonVolatileStatus = null;
            }
            else{
                printed = false;
            }
            turnPart = 11;
        }
        else{
            if(printed){sleep(2000);}
            activePokemon.protecting = false;
            opposingPokemon.protecting = false;

            if(activePokemon.CurrentHealth <= 0){
                activePokemon.nonVolatileStatus = null;
                this.keyInputs.state = new TrainerTeamState(this.keyInputs);
                return;
            }

            if(opposingPokemon.CurrentHealth <= 0){
                opposingPokemon.nonVolatileStatus = null;
                if(opposingTrainer.team[1].CurrentHealth != 0) {
                    this.keyInputs.state = new BattleState(this.keyInputs, new Wait(), new Switch(1, opposingTrainer));
                }
                else if(opposingTrainer.team[2].CurrentHealth != 0){
                    this.keyInputs.state = new BattleState(this.keyInputs, new Wait(), new Switch(2, opposingTrainer));
                }
                else{
                    System.out.println("you win!");
                    this.keyInputs.state = new SelectionState(this.keyInputs);
                }
                return;
            }

            this.keyInputs.state = new SelectionState(this.keyInputs);
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
