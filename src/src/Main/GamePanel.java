package Main;

import PokemonStuff.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * the game panel!!
 * holds the majority of the stuff for the game to function
 */
public class GamePanel extends JPanel implements Runnable{
    public final static int tileSize = 48; //each 1 tile is 48x48 pixels
    public final static int rows = 16; //the screen has 16 rows
    public final static int columns = 22; //the screen has 22 columns
    public final static int screenHeight = tileSize * rows; // 16 tiles tall
    public final static int screenWidth = tileSize * columns; //22 tiles wide
    final int fps = 60;

    PlayerKeyInputs keyInputs;

    BufferedImage backgroundImage;

    Thread gameThread;


    public final static HashMap<String, Move> moveData = storeMoveInfo("out/res/moves.csv");
    public final static HashMap<String, Pokemon> pokemonData = storePokemonInfo("out/res/pokemon.csv");
    private static Pokemon activePokemon;
    private static Pokemon opposingPokemon;
    private static Trainer player = new Trainer(null, null, null);
    private static Trainer opposingTrainer = new Trainer(null, null, null);
    public static ArrayList<Pokemon> pokemonArrayList = new ArrayList<>(pokemonData.values());


    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new Color(0, 200, 0));
        this.setDoubleBuffered(true);

        this.setFocusable(true);
        this.keyInputs = new PlayerKeyInputs(this);
        this.addKeyListener(keyInputs);

        try{
            backgroundImage = ImageIO.read(new File("out/res/lhu26US.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static ArrayList<String> readFile(String filename){
        try{
            return new ArrayList<>(Files.readAllLines(Paths.get(filename)));
        }
        catch(IOException e){
            return new ArrayList<>();
        }
    }

    //String-Name,String-Type,String-Category,int-PP,int-Power,int-Accuracy

    /**
     * Reads a CSV file containing all the <Code>moves</Code> a <Code>Pokemon</Code> can use and stores them in a hashmap
     * @param filename the file to be read
     * @return a <Code>HashMap</Code> containing every move where the key is a string corresponding
     * to the move name and the value is the <Code>Move</Code> itself
     */
    public static HashMap<String, Move> storeMoveInfo(String filename){
        ArrayList<String> lines = readFile(filename);
        HashMap<String, Move> ans = new HashMap<>();

        for(int i = 1; i < lines.size(); i++){
            ArrayList<String> splits = new ArrayList<>(Arrays.asList(lines.get(i).split(",")));

            String name = splits.get(0);
            String type = splits.get(1);
            String category = splits.get(2);
            int pp = Integer.parseInt(splits.get(3));
            int power = Integer.parseInt(splits.get(4));
            int accuracy = Integer.parseInt(splits.get(5));
            int priority = Integer.parseInt(splits.get(6));
            String effect = splits.get(7);
            String statusType = splits.get(8);
            String info1 = splits.get(9);
            String info2 = splits.get(10);
            String shortenedName = splits.get(11);
            switch(category){
                case ("Physical"), ("Special"):
                    Move attack = new AttackingMove(name, type, category, pp, power, accuracy, priority, effect, info1, info2, shortenedName);
                    ans.put(name, attack);
                    break;
                case("Status"):
                    Move statusMove = new StatusMove(name, type, category, pp, power, accuracy, priority, effect, statusType, info1, info2, shortenedName);
                    ans.put(name, statusMove);
                    break;

            }


        }
        return ans;
    }
    //STRING-Name,STRING-Ability,INT-MaxHealth,INT-Attack,INT-Defense,INT-SpAttack,INT-SpDefense,
    // INT-Speed,Front-Sprite,Back-Sprite,move1,move2,move3,move4

    /**
     * reads a CSV file containing all the <Code>Pokemon</Code> and stores them in a hashmap
     * @param filename the file to be read
     * @return a hashmap where the keys are the pokemon name and the values are the <Code>Pokemon</Code> objects
     */
    public static HashMap<String, Pokemon> storePokemonInfo(String filename){
        ArrayList<String> lines = readFile(filename);
        HashMap<String, Pokemon> ans = new HashMap<>();

        for(int i = 1; i < lines.size(); i++){
            ArrayList<String> splits = new ArrayList<>(Arrays.asList(lines.get(i).split(",")));

            String name = splits.get(0); String type = splits.get(1); String type2 = splits.get(2);
            int maxHealth = Integer.parseInt(splits.get(3));
            int attack = Integer.parseInt(splits.get(4)); int defense = Integer.parseInt(splits.get(5));
            int spAttack = Integer.parseInt(splits.get(6)); int spDefense = Integer.parseInt(splits.get(7));
            int speed = Integer.parseInt(splits.get(8));
            String frontSprite = splits.get(9); String backSprite = splits.get(10);
            Move move1 = moveData.get(splits.get(11)); Move move2 = moveData.get(splits.get(12));
            Move move3 = moveData.get(splits.get(13)); Move move4 = moveData.get(splits.get(14));

            Pokemon pokemon = new Pokemon(name, type, type2, maxHealth, attack, defense,
                    spAttack, spDefense, speed, frontSprite, backSprite, move1, move2, move3, move4);
            ans.put(name, pokemon);
        }
        return ans;
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){
        double drawInterval= 1000000000.0/fps; //the game's display will be updated 60 times per second
        double delta = 0; //represents difference in time between current time and last checked time
        long lastTime = System.nanoTime(); //returns current time in nanoseconds
        long currentTime = System.nanoTime();

        while(gameThread != null){
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if(delta >= 1) {
                //update game info EX: position

                //display new info
                repaint();
                delta--;
            }

        }

    }

    /**
     * draws the background and tiles of the game
     * @param graphics the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D)graphics;

        graphics2D.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight - (5 * tileSize),null);
        //display tiles
        int x;
        int y = 0;
        for(int row = 0; row < rows + 2; row++){
            x = 0;
            for(int column = 0; column < columns + 2; column++){
                graphics.drawRect(x, y, tileSize, tileSize);
                x+= tileSize;
            }
            y += tileSize;
        }

        keyInputs.getState().paintComponent(graphics2D);


        graphics2D.dispose();
    }


    public static Trainer Player() {
        return player;
    }

    public static Trainer OpposingTrainer() {
        return opposingTrainer;
    }

    public static Pokemon ActivePokemon() {
        return activePokemon;
    }

    public static void setActivePokemon(Pokemon pokemon) {
        activePokemon = pokemon;
    }

    public static Pokemon OpposingPokemon() {
        return opposingPokemon;
    }

    public static void setOpposingPokemon(Pokemon pokemon) {
        opposingPokemon = pokemon;
    }
}
