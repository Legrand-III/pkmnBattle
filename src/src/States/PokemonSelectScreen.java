package States;

import Main.PlayerKeyInputs;
import PokemonStuff.Pokemon;

import java.awt.*;

import static Main.GamePanel.*;
import static Main.GamePanel.tileSize;

public class PokemonSelectScreen extends AbstractState{ //room for 40(?) pkmn

    public PlayerKeyInputs keyInputs;
    int optionY = 0;
    int optionX = 0;
    SubState subState;
    public PokemonSelectScreen(PlayerKeyInputs keyInputs){
        this.keyInputs = keyInputs;
        if(opposingTrainer.team[2] != null){
            this.subState = new readyToBattle(this);
        }
        else {
            this.subState = new playerSelectTeam(this);
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D)graphics;
        graphics2D.setColor(new Color(0,0,0,150));
        graphics2D.fillRect(0,0, screenWidth, screenHeight);

        graphics2D.setFont(new Font("times", Font.BOLD, 48));
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString("Select Pokemon!!", (tileSize), tileSize * 2 -  (tileSize/8));

        graphics2D.setFont(new Font("times", Font.BOLD|Font.ITALIC, 24));

        graphics2D.drawString("Current Team ~", (tileSize * 11), tileSize);

        graphics2D.drawString("Opposing Team ~", (tileSize * 11), tileSize*3);

        drawSelection(graphics2D);
        drawPokemon(graphics2D);
        drawPlayerTeam(graphics2D);
        drawEnemyTeam(graphics2D);

        subState.draw(graphics2D);

    }

    @Override
    public void update() {

    }

    @Override
    public void upPressed() {
        subState.upPressed();
    }

    @Override
    public void downPressed() {
        subState.downPressed();
    }

    @Override
    public void leftPressed() {
        subState.leftPressed();
    }

    @Override
    public void rightPressed() {
        subState.rightPressed();
    }

    @Override
    public void spacePressed() {
        subState.spacePressed();
    }

    @Override
    public void escapePressed() {
        subState.escapePressed();
    }

    public void drawPokemon(Graphics2D graphics2D){
        int xPos = 1;
        int yPos = 3;
        for(Pokemon pokemon : pokemonArrayList){
            graphics2D.drawImage(pokemon.frontSprite, tileSize * xPos, tileSize * yPos, tileSize*2, tileSize*2, null);
            if(xPos == 19){
                xPos = 1;
                yPos += 2;
            }
            else{
                xPos += 2;
            }
        }
    }

    public void drawSelection(Graphics2D graphics2D){
        drawTextBox(graphics2D, 11, 5);
        drawOptionsBox(graphics2D, 11, 11, 5);

        graphics2D.setColor(new Color(255,170,180, 150));

        graphics2D.fillRoundRect(tileSize + (2*tileSize * optionX), 3*tileSize + (2*tileSize * optionY),
                tileSize * 2, tileSize * 2, 25, 25);


        int selectedPkmn = optionY * 10 + optionX;
        if(selectedPkmn < pokemonArrayList.size()){
            graphics2D.drawImage(pokemonArrayList.get(selectedPkmn).frontSprite,
                    tileSize, tileSize * 12 - (tileSize/8), tileSize*3, tileSize*3, null);
            graphics2D.setFont(new Font("times", Font.BOLD, 48));
            graphics2D.setColor(Color.WHITE);

            graphics2D.drawString(pokemonArrayList.get(selectedPkmn).Name, tileSize*4, tileSize * 13 - (tileSize/8));

            graphics2D.setFont(new Font("times", Font.BOLD | Font.ITALIC, 32));

            graphics2D.drawString("Type: " + pokemonArrayList.get(selectedPkmn).Type, tileSize*4, tileSize * 14 - (tileSize/8));
            if(pokemonArrayList.get(selectedPkmn).Type2 != null){
                graphics2D.drawString("/ " + pokemonArrayList.get(selectedPkmn).Type2, tileSize*5, tileSize * 15 - (tileSize/8));
            }

            graphics2D.setFont(new Font("times", Font.BOLD, 35));

            graphics2D.drawString(pokemonArrayList.get(selectedPkmn).moves[0].ShortenedName, tileSize*12, tileSize * 13 - (tileSize/8));
            graphics2D.drawString(pokemonArrayList.get(selectedPkmn).moves[1].ShortenedName, tileSize*17, tileSize * 13 - (tileSize/8));
            graphics2D.drawString(pokemonArrayList.get(selectedPkmn).moves[2].ShortenedName, tileSize*12, tileSize * 15 - (tileSize/8));
            graphics2D.drawString(pokemonArrayList.get(selectedPkmn).moves[3].ShortenedName, tileSize*17, tileSize * 15 - (tileSize/8));


        }
    }

    public void drawPlayerTeam(Graphics2D graphics2D){
        if(player.team[0] == null){
            return;
        }
        graphics2D.drawImage(player.team[0].frontSprite, (tileSize * 16), 0, tileSize, tileSize, null);
        if(player.team[1] == null){
            return;
        }
        graphics2D.drawImage(player.team[1].frontSprite, (tileSize * 17), 0, tileSize, tileSize, null);
        if(player.team[2] == null){
            return;
        }
        graphics2D.drawImage(player.team[2].frontSprite, (tileSize * 18), 0, tileSize, tileSize, null);

    }
    public void drawEnemyTeam(Graphics2D graphics2D){
        if(opposingTrainer.team[0] == null){
            return;
        }
        graphics2D.drawImage(opposingTrainer.team[0].frontSprite, (tileSize * 16), tileSize*2, tileSize, tileSize, null);
        if(opposingTrainer.team[1] == null){
            return;
        }
        graphics2D.drawImage(opposingTrainer.team[1].frontSprite, (tileSize * 17), tileSize*2, tileSize, tileSize, null);
        if(opposingTrainer.team[2] == null){
            return;
        }
        graphics2D.drawImage(opposingTrainer.team[2].frontSprite, (tileSize * 18), tileSize*2, tileSize, tileSize, null);
    }

    public boolean removePokemon(){
        if(opposingTrainer.team[2] != null){
            opposingTrainer.team[2] = null;
            return true;
        }
        if(opposingTrainer.team[1] != null){
            opposingTrainer.team[1] = null;
            return true;
        }
        if(opposingTrainer.team[0] != null){
            opposingTrainer.team[0] = null;
            return true;
        }
        if(player.team[2] != null){
            player.team[2] = null;
            return true;
        }
        if(player.team[1] != null){
            player.team[1] = null;
            return true;
        }
        if(player.team[0] != null){
            player.team[0] = null;
            return true;
        }
        return false;
    }

    public void finalizeTeams(){
        player.team[0] = new Pokemon(player.team[0].Name);
        player.team[1] = new Pokemon(player.team[1].Name);
        player.team[2] = new Pokemon(player.team[2].Name);
        activePokemon = player.team[0];

        opposingTrainer.team[0] = new Pokemon(opposingTrainer.team[0].Name);
        opposingTrainer.team[1] = new Pokemon(opposingTrainer.team[1].Name);
        opposingTrainer.team[2] = new Pokemon(opposingTrainer.team[2].Name);
        opposingPokemon = opposingTrainer.team[0];
    }

}

class playerSelectTeam extends SubState{

    PokemonSelectScreen State;
    public playerSelectTeam(PokemonSelectScreen pokemonSelectScreen){
        this.State = pokemonSelectScreen;

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D graphics2D) {

    }

    @Override
    public void spacePressed() {
        int selectedPkmn = State.optionY * 10 + State.optionX;
        if(selectedPkmn < pokemonArrayList.size()){
            State.subState = new confirmSelection(State);
        }
    }

    @Override
    public void escapePressed() {
        if(!State.removePokemon()){
            State.subState = new confirmQuit(State);
        }
    }

    @Override
    public void upPressed() {
        if(State.optionY == 0){
            State.optionY += 3;
        }
        else {
            State.optionY -= 1;
        }
    }

    @Override
    public void downPressed() {
        if(State.optionY == 3){
            State.optionY -= 3;
        }
        else {
            State.optionY += 1;
        }
    }

    @Override
    public void leftPressed() {
        if(State.optionX == 0){
            State.optionX += 9;
        }
        else {
            State.optionX -= 1;
        }
    }

    @Override
    public void rightPressed() {
        if(State.optionX == 9){
            State.optionX -= 9;
        }
        else {
            State.optionX += 1;
        }
    }
}

class confirmQuit extends SubState{

    PokemonSelectScreen State;
    int optionNum = 0;
    public confirmQuit(PokemonSelectScreen pokemonSelectScreen){
        this.State = pokemonSelectScreen;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(new Color(0,0,0,150)); //background = white + low opacity
        graphics2D.fillRoundRect(tileSize, tileSize*3, tileSize*10, tileSize*8, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.drawRoundRect(tileSize, tileSize*3 + 2,
                tileSize*10 - 2, tileSize*8 - 4, 25, 25);

        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString("Are you sure", (tileSize*2), tileSize * 5 -  (tileSize/8));
        graphics2D.drawString("you'd like to quit?", (tileSize*2), tileSize * 6 -  (tileSize/8));

        if(optionNum == 0){
            graphics2D.setColor(new Color(255,170,180));
            graphics2D.drawString("♡", (tileSize * 3), tileSize * 8 -  (tileSize/8));
        }
        graphics2D.drawString("Yes", (tileSize*4), tileSize * 8 -  (tileSize/8));
        graphics2D.setColor(Color.WHITE);
        if(optionNum == 1){
            graphics2D.setColor(new Color(255,170,180));
            graphics2D.drawString("♡", (tileSize * 3), tileSize * 9 -  (tileSize/8));
        }
        graphics2D.drawString("No", (tileSize*4), tileSize * 9 -  (tileSize/8));
    }

    @Override
    public void upPressed() {
        optionNum = (optionNum+1)%2;
    }

    @Override
    public void downPressed() {
        optionNum = (optionNum+1)%2;
    }

    @Override
    public void spacePressed() {
        if(optionNum == 0){
            State.keyInputs.state = new TitleScreen(State.keyInputs);
        }
        else{
            State.subState = new playerSelectTeam(State);
        }
    }

    @Override
    public void escapePressed() {
        State.subState = new playerSelectTeam(State);
    }
}

class confirmSelection extends SubState {
    int optionNum = 0;
    Pokemon selectedPokemon;


    PokemonSelectScreen State;

    public confirmSelection(PokemonSelectScreen pokemonSelectScreen) {
        this.State = pokemonSelectScreen;
        selectedPokemon = pokemonArrayList.get(State.optionY * 10 + State.optionX);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D graphics2D) {
        if(State.optionX > 4){
            graphics2D.setColor(new Color(0,0,0,150)); //background = white + low opacity
            graphics2D.fillRoundRect(tileSize, tileSize*3, tileSize*10, tileSize*8, 35, 35);

            graphics2D.setColor(Color.white);
            graphics2D.drawRoundRect(tileSize, tileSize*3 + 2,
                    tileSize*10 - 2, tileSize*8 - 4, 25, 25);

            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString("What would you like", (tileSize*2), tileSize * 5 -  (tileSize/8));
            graphics2D.drawString("to do?", (tileSize*2), tileSize * 6 -  (tileSize/8));

            if(optionNum == 0){
                graphics2D.setColor(new Color(255,170,180));
                graphics2D.drawString("♡", (tileSize * 3), tileSize * 8 -  (tileSize/8));
            }
            graphics2D.drawString("Add Pokemon", (tileSize*4), tileSize * 8 -  (tileSize/8));
            graphics2D.setColor(Color.WHITE);
            if(optionNum == 1){
                graphics2D.setColor(new Color(255,170,180));
                graphics2D.drawString("♡", (tileSize * 3), tileSize * 9 -  (tileSize/8));
            }
            graphics2D.drawString("Summary", (tileSize*4), tileSize * 9 -  (tileSize/8));
            graphics2D.setColor(Color.WHITE);
            if(optionNum == 2){
                graphics2D.setColor(new Color(255,170,180));
                graphics2D.drawString("♡", (tileSize * 3), tileSize * 10 -  (tileSize/8));
            }
            graphics2D.drawString("Back", (tileSize*4), tileSize * 10 -  (tileSize/8));



        }
        else{
            graphics2D.setColor(new Color(0,0,0,150)); //background = white + low opacity
            graphics2D.fillRoundRect(tileSize*11, tileSize*3, tileSize*10, tileSize*8, 35, 35);

            graphics2D.setColor(Color.white);
            graphics2D.drawRoundRect(tileSize*11, tileSize*3 + 2,
                    tileSize*10 - 2, tileSize*8 - 4, 25, 25);

            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString("What would you like", (tileSize*12), tileSize * 5 -  (tileSize/8));
            graphics2D.drawString("to do?", (tileSize*12), tileSize * 6 -  (tileSize/8));


            if(optionNum == 0){
                graphics2D.setColor(new Color(255,170,180));
                graphics2D.drawString("♡", (tileSize * 12), tileSize * 8 -  (tileSize/8));
            }
            graphics2D.drawString("Add Pokemon", (tileSize*14), tileSize * 8 -  (tileSize/8));
            graphics2D.setColor(Color.WHITE);
            if(optionNum == 1){
                graphics2D.setColor(new Color(255,170,180));
                graphics2D.drawString("♡", (tileSize * 12), tileSize * 9 -  (tileSize/8));
            }
            graphics2D.drawString("Summary", (tileSize*14), tileSize * 9 -  (tileSize/8));
            graphics2D.setColor(Color.WHITE);
            if(optionNum == 2){
                graphics2D.setColor(new Color(255,170,180));
                graphics2D.drawString("♡", (tileSize * 12), tileSize * 10 -  (tileSize/8));
            }
            graphics2D.drawString("Back", (tileSize*14), tileSize * 10 -  (tileSize/8));
        }
    }

    @Override
    public void spacePressed() {
        if(optionNum == 0){
            addPokemon();
        }
        else if(optionNum == 1){
            State.subState = new miniSummary(State);
        }
        else{
            State.subState = new playerSelectTeam(State);
        }

    }
    public void addPokemon(){
        if(player.team[0] == null){
            player.team[0] = selectedPokemon;
        }
        else if(player.team[1] == null){
            player.team[1] = selectedPokemon;
        }
        else if(player.team[2] == null){
            player.team[2] = selectedPokemon;
        }
        else if(opposingTrainer.team[0] == null){
            opposingTrainer.team[0] = selectedPokemon;
        }
        else if(opposingTrainer.team[1] == null){
            opposingTrainer.team[1] = selectedPokemon;
        }
        else{
            opposingTrainer.team[2] = selectedPokemon;
            State.subState = new readyToBattle(State);
            return;
        }
        State.subState = new playerSelectTeam(State);
    }

    @Override
    public void escapePressed() {
        State.subState = new playerSelectTeam(State);
    }

    @Override
    public void upPressed() {
        if(optionNum == 0){
            optionNum = 2;
        }
        else {
            optionNum = (optionNum - 1) % 3;
        }
    }

    @Override
    public void downPressed() {
        optionNum = (optionNum+1)%3;
    }
}

class miniSummary extends SubState{
    PokemonSelectScreen State;
    int optionNum = 0;
    Pokemon selectedPokemon;

    public miniSummary(PokemonSelectScreen State){
        this.State = State;
        selectedPokemon = pokemonArrayList.get(State.optionY * 10 + State.optionX);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0,0, screenWidth, screenHeight);

        State.drawTextBox(graphics2D, columns, 5);

        //main background
        graphics2D.setColor(new Color(0,0,0,150)); //background = black + low opacity
        graphics2D.fillRoundRect(0, 0, screenWidth, tileSize*11, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect( 0,2,
                screenWidth - 2, tileSize*11 - 4, 25, 25);

        //selected pokemon
        if(selectedPokemon.CurrentHealth == 0){graphics2D.setColor(new Color(200, 0, 0, 150)); }
        else{graphics2D.setColor(new Color(255,255,255,150));} //background = white + low opacity
        graphics2D.fillRoundRect(0, 0, tileSize*11, tileSize*11, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect( 0,2,
                tileSize*11 - 2, tileSize*11 - 4, 25, 25);

        graphics2D.drawImage(selectedPokemon.frontSprite, 0, 0, tileSize*4, tileSize*4, null);
        graphics2D.setFont(new Font("times", Font.BOLD|Font.ITALIC, 38));
        graphics2D.drawString(selectedPokemon.Name, tileSize * 5, tileSize*2 - (tileSize/8));
        graphics2D.drawString("Type: " + selectedPokemon.Type, tileSize * 5, tileSize*3 - (tileSize/8));
        if(selectedPokemon.Type2 != null){
            graphics2D.drawString(" / " + selectedPokemon.Type2, tileSize * 5, tileSize*4 - (tileSize/8));
        }

        //stats
        graphics2D.drawString("HP: " + selectedPokemon.CurrentHealth + " / " + selectedPokemon.MaxHealth, tileSize, tileSize*5 - (tileSize/8));
        graphics2D.drawString("ATK: " + selectedPokemon.Attack, tileSize, tileSize*6 - (tileSize/8));
        graphics2D.drawString("DEF: " + selectedPokemon.Defense, tileSize, tileSize*7 - (tileSize/8));
        graphics2D.drawString("SpATK: " + selectedPokemon.SpAttack, tileSize, tileSize*8 - (tileSize/8));
        graphics2D.drawString("SpDEF: " + selectedPokemon.SpDefense, tileSize, tileSize*9 - (tileSize/8));
        graphics2D.drawString("SPD: " + selectedPokemon.Speed, tileSize, tileSize*10 - (tileSize/8));

        //moves
        graphics2D.setColor(new Color(255,255,255,150)); //background = white + low opacity
        graphics2D.fillRoundRect(tileSize*11, tileSize*3, tileSize*11, tileSize*8, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect(tileSize*11,tileSize*3 + 2,
                tileSize*11 - 2, tileSize*8 - 4, 25, 25);

        graphics2D.drawString("Moves", tileSize*15, tileSize*4 - (tileSize / 8));
        if (optionNum == 0) {graphics2D.setColor(new Color(255,209,220));}
        else{graphics2D.setColor(new Color(255,255,255));}
        graphics2D.drawString(selectedPokemon.moves[0].Name, tileSize*13, tileSize*6 - (tileSize / 8));

        if (optionNum == 1) {graphics2D.setColor(new Color(255,209,220));}
        else{graphics2D.setColor(new Color(255,255,255));}
        graphics2D.drawString(selectedPokemon.moves[1].Name, tileSize*13, tileSize*7 - (tileSize / 8));

        if (optionNum == 2) {graphics2D.setColor(new Color(255,209,220));}
        else{graphics2D.setColor(new Color(255,255,255));}
        graphics2D.drawString(selectedPokemon.moves[2].Name, tileSize*13, tileSize*8 - (tileSize / 8));

        if (optionNum == 3) {graphics2D.setColor(new Color(255,209,220));}
        else{graphics2D.setColor(new Color(255,255,255));}
        graphics2D.drawString(selectedPokemon.moves[3].Name, tileSize*13, tileSize*9 - (tileSize / 8));

        switch(optionNum){
            case(0):
                graphics2D.setColor(new Color(255,209,220));
                graphics2D.drawString("♡", tileSize*12 + tileSize/4, tileSize*6 - (tileSize / 8));
                break;
            case(1):
                graphics2D.setColor(new Color(255,209,220));
                graphics2D.drawString("♡", tileSize*12 + tileSize/4, tileSize*7 - (tileSize / 8));
                break;
            case(2):
                graphics2D.setColor(new Color(255,209,220));
                graphics2D.drawString("♡", tileSize*12 + tileSize/4, tileSize*8 - (tileSize / 8));
                break;
            case(3):
                graphics2D.setColor(new Color(255,209,220));
                graphics2D.drawString("♡", tileSize*12 + tileSize/4, tileSize*9 - (tileSize / 8));
                break;
        }

        drawMoveInfo(graphics2D);
    }

    private void drawMoveInfo(Graphics2D graphics2D){
        State.drawOptionsBox(graphics2D, 0, 8, 5);

        graphics2D.setFont(new Font("times", Font.BOLD|Font.ITALIC, 30));

        graphics2D.drawString("PP:   " + selectedPokemon.moves[optionNum].RemainingPP +
                " / " + selectedPokemon.moves[optionNum].MaxPP, tileSize , tileSize * 12 - (tileSize/8));


        graphics2D.drawString("Type: " + selectedPokemon.moves[optionNum].Type, tileSize, tileSize * 13  - (tileSize/8));
       if(selectedPokemon.moves[optionNum].Power == -1){
           graphics2D.drawString("Power: N/A", tileSize, tileSize * 14 - (tileSize/8));

       }
       else{
           graphics2D.drawString("Power: " + selectedPokemon.moves[optionNum].Power, tileSize, tileSize * 14 - (tileSize/8));

       }

       if(selectedPokemon.moves[optionNum].Accuracy == 999999){
           graphics2D.drawString("Accuracy: N/A", tileSize, tileSize * 15  - (tileSize/8));
       }
       else{
           graphics2D.drawString("Accuracy: " + selectedPokemon.moves[optionNum].Accuracy, tileSize, tileSize * 15  - (tileSize/8));

       }

        graphics2D.drawString("Category: " + selectedPokemon.moves[optionNum].Category, tileSize, tileSize * 16  - (tileSize/8));

        graphics2D.drawString(selectedPokemon.moves[optionNum].MoveInfo1, tileSize * 9, tileSize * 13 - (tileSize/8));
        graphics2D.drawString(selectedPokemon.moves[optionNum].MoveInfo2, tileSize * 9, tileSize * 15 - (tileSize/8));

    }

    @Override
    public void spacePressed() {

    }

    @Override
    public void escapePressed() {
        State.subState = new confirmSelection(State);
    }

    @Override
    public void upPressed() {
        optionNum--;
        if(optionNum < 0){
            optionNum = 3;
        }
    }

    @Override
    public void downPressed() {
        optionNum++;
        if(optionNum > 3){
            optionNum = 0;
        }
    }
}

class readyToBattle extends SubState{

    PokemonSelectScreen State;
    int optionNum = 0;
    public readyToBattle(PokemonSelectScreen pokemonSelectScreen){
        this.State = pokemonSelectScreen;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(new Color(0,0,0,150)); //background = white + low opacity
        graphics2D.fillRoundRect(tileSize, tileSize*3, tileSize*10, tileSize*8, 35, 35);

        graphics2D.setColor(Color.white);
        graphics2D.drawRoundRect(tileSize, tileSize*3 + 2,
                tileSize*10 - 2, tileSize*8 - 4, 25, 25);

        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString("Are you ready", (tileSize*2), tileSize * 5 -  (tileSize/8));
        graphics2D.drawString("to fight?", (tileSize*2), tileSize * 6 -  (tileSize/8));

        if(optionNum == 0){
            graphics2D.setColor(new Color(255,170,180));
            graphics2D.drawString("♡", (tileSize * 3), tileSize * 8 -  (tileSize/8));
        }
        graphics2D.drawString("Yes", (tileSize*4), tileSize * 8 -  (tileSize/8));
        graphics2D.setColor(Color.WHITE);
        if(optionNum == 1){
            graphics2D.setColor(new Color(255,170,180));
            graphics2D.drawString("♡", (tileSize * 3), tileSize * 9 -  (tileSize/8));
        }
        graphics2D.drawString("No", (tileSize*4), tileSize * 9 -  (tileSize/8));
    }

    @Override
    public void upPressed() {
        optionNum = (optionNum+1)%2;
    }

    @Override
    public void downPressed() {
        optionNum = (optionNum+1)%2;
    }

    @Override
    public void spacePressed() {
        if(optionNum == 0){
            State.finalizeTeams();
            State.keyInputs.state = new SelectionState(State.keyInputs);
        }
        else{
            State.removePokemon();
            State.subState = new playerSelectTeam(State);
        }
    }

    @Override
    public void escapePressed() {
        State.removePokemon();
        State.subState = new playerSelectTeam(State);
    }
}