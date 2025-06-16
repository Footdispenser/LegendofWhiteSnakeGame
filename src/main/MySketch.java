/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;
import processing.core.PApplet;
import processing.core.PImage;
import java.io.*;
import java.util.ArrayList;
/**
 * The main game class that extends PApplet to create a 2D game.
 * This class handles game initialization, rendering, input processing, and game logic.
 * It includes player movement, enemy AI, projectile mechanics, level management,
 * and story/dialogue systems.
 *
 * @author 342964137/ Brandon Dong
 */
public class MySketch extends PApplet {
    // screen settings
    public int pixelW = 32;
    public int pixelL = 32;
    public int col = 25;
    public int row = 50;
    public int width = pixelW*row;
    public int height = pixelL*col;
    // end screen settings
    // perosn attributes
    
    private Person person;

    private ArrayList<Enemy> enemies = new ArrayList<>();
    
    ///// projectile properties
    private ArrayList <Projectiles> projectiles = new ArrayList<>();
    private int shootingCD = 0;
    private final int SHOOT_DELAY = 10; // frames
    
    // other misc
    private int stage = 2;
    
    private Camera camera;
    private level[] levels;
    private int currLevel = 0;
    
    private final int STORY_PAGES = 27; /////// NUMBER OF PAGES IN THE STORY
    private int currStoryStage = 0; ////// TRACKER
    private final int ENDING_PAGES = 21; // NUMBER OF ENDING PAGES IN THE STORY

//    private boolean storyShown = false;
    private PImage[] storyImages = new PImage[STORY_PAGES]; 
    private PImage[] endingImages = new PImage[ENDING_PAGES];

    PImage screenEff;

    ////// dialogue
    private Dialogue dialogueSystem;

    private boolean isReviewTime = false;
    private String currReview = "";
    private PrintWriter reviewWriter;
    
    /**
     * Processing setup method that initializes the game window size.
     */
    public void settings(){
        size((width), height);  
    }
    
    /**
     * Initializes all game objects, loads resources, and sets up the initial
     * game state.
     */
    public void setup(){
        person = new Person(this);
        camera = new Camera(this);
        levels = new level[3];
        int map1[][] = {
            {1, 2, 2, 2, 2, 2, 2, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 3, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
        };
        int[] playerSpawn1 = {204, 900};
        levels[0] = new level(this, map1, playerSpawn1, person,"images/maps/mapBG_0.png");
        ////////////////////////// Level 1
        int map2[][] = { // 25 by24
            {1, 2, 2, 2, 2, 2, 2, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {3, 0, 0, 0, 0, 0, 0, 3},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {3, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 3, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
        };
        int [] playerSpawn2 = {204, 900};
        levels[1] = new level(this, map2, playerSpawn2, person, "images/maps/mapBG_1.png");
        ////////////////////////////// level 2
        int map3[][] = {
            {1, 1, 3, 1, 3, 1, 1, 1, 3, 1, 1, 2, 2, 1, 1, 1, 1, 3, 1, 1, 3, 1, 1, 1, 1},
            {3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3},
            {3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 3, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1},
            {3, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 3},
            {1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1},
            {3, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 3},
            {1, 1, 0, 0, 0, 0, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1},
            {3, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 3},
            {3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1},
            {3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
        int[] playerSpawn3 = {740, 1300};
        levels[2] = new level(this, map3, playerSpawn3 , person,"images/maps/mapBG.png");

//        background(0);

        String dialogueFilePath = sketchPath("Dialogue.txt");
        dialogueSystem = new Dialogue(this, dialogueFilePath);
        dialogueSystem.setDialoguePage(22);
        dialogueSystem.resetDialogue();
        
        setPlayerPos();
        initializeEnemies();
        screenEff = loadImage("images/test (1).png");
        // load all the story images
        for (int i = 0; i < STORY_PAGES; i++) {
            String storyFormat = String.format("%02d", i);// formats it into 2 decimals
            storyImages[i] = loadImage("images/story/story_" + storyFormat + ".png");

        }
        
        for (int i = 0; i < ENDING_PAGES; i++) {
            String endingFormat = String.format("%02d", i);
            endingImages[i] = loadImage("images/ending/ending_" + endingFormat + ".png");
        }
        
        initReviewWriter();


    }
    ///////////////// SETUP METHODS //////////////////////////
    /**
     * Sets the player's position to the current level's spawn point.
     */
    private void setPlayerPos(){
        int[]spawn = levels[currLevel].getPlayerSpawnPoint();
        person.x = spawn[0];
        person.y = spawn[1];
        
    }
    /**
     * Initializes enemies for the current level based on spawn points.
     */
    private void initializeEnemies() {
        enemies.clear();
        ArrayList<int[]> spawns = levels[currLevel].getEnemySpawns();
        

        for (int k = 0; k < spawns.size(); k++) {
            int[] pos = spawns.get(k);
            enemies.add(new Enemy(this, pos[0], pos[1], person,
                    person.x, person.y,
                    person.playerImages[0].width,
                    person.playerImages[0].height));

        }
    }
    /**
     * checks if the player has collided with a teleport tile and level
     * transitions.
     */
    private void checkTeleport() {
        for (int[] teleport : levels[currLevel].telepoints) {
            if (person.x + person.w > teleport[0]
                    && person.x < teleport[0] + teleport[2]
                    && person.y + person.h > teleport[1]
                    && person.y < teleport[1] + teleport[3]) {
                // checks if the player is on level 3
                if (currLevel == 2) { // Since levels are 0-indexed, level 3 is index 2
                    stage = 3; // Set to ending story stage
                    currStoryStage = 0; // Reset story counter
                    return;
                }

                // change level
                currLevel = (currLevel + 1) % levels.length;

                // Reset player to new level's spawn point
                setPlayerPos();

                // Create enemies for new level
                initializeEnemies();

                break;
            }
        }
    }
    ////////////////////// DRAW //////////////////////////////////
    /**
     * Main game loop that draws different game stages based on the current
     * stage.
     */
    public void draw() {
        background(0);
        switch (stage){
            case 0:
                drawGame();
                image(screenEff, 0, 0);
                person.drawHpBar(this);
                break;
            case 1:
                //// draw death message
                fill(0, 0, 0, 200);
                rect(0, 0, width, height);

                // Death message
                fill(255);
                textSize(48);
                textAlign(CENTER, CENTER);
                text("YOU DIED", width / 2, height / 2 - 50);
                break;
            case 2:
                story();
                break;
            case 3:
                if (isReviewTime) { //  if review is set to true or finished reading the story
                    drawReviewInput();
                } else {
                    ending();
                }
                break;
        }// end swicth
    }///////////////////////////// END DRAW ///////////////////////////////////////

    /**
     * Draws the main game, player, enemies, and projectiles.
     */

    private void drawGame(){
        /// checks if teleported or not / checks current stage
        checkTeleport();

        // send the player information to the camera for accurate following
        camera.follow(person.x, person.y, width, height,
                person.playerImages[0].width, person.playerImages[0].height);

        camera.apply();// activates the camera/applys it

        ///////////////////////// level /////////////////////////////////////
        levels[currLevel].draw();
        levels[currLevel].isPlayerColliding();

        ///////////////////////// player/person /////////////////////////////////////
        person.update(); // player movements
        person.draw(); // player looks
//        person.displayInfo(this); // testing - display player x and y

        ///////////////////////// enemies /////////////////////////////////////
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy e = enemies.get(i);
            e.update(levels[currLevel]);
            if (e.isDead) {
                continue; // skips dead enemies so you dont take invisible dmg
            }
            e.EnemyCollisions(enemies);
            if (rectangleIntersect(person, e)) {
                e.attack(person);
                if(person.currHp <=0){ // if the player dies
                    stage = 1;
                }
            }
            e.draw();
        }

        //////////////////////// projectiles ////////////////////////////////
        for (int i = projectiles.size() - 1; i >= 0; i--) {
            Projectiles p = projectiles.get(i);
            p.update(levels[currLevel]);
            p.draw();
            // enemy collision check
            for (int j = enemies.size() - 1; j >= 0; j--) {
                Enemy e = enemies.get(j);
                if (!e.isDead && p.hits(e)) {// only checks alive enemies so projectiles dont hit inisible enemies
                    e.die(); // kills the enemy/ sets isDead to true
                    p.active = false;
                    break;
                }
            }
            // removes the projectile
            if (!p.active) {
                projectiles.remove(i);
            }
        }
        if (shootingCD > 0) {
            shootingCD--;
        }

        // resets
        resetMatrix();
       
        // testing - shows corrdinates of the camera and player
        fill(0);
        text("Player: x=" + person.x + " , y=" + person.y, 500, 20);
        text("Camera: x=" + camera.x + " , y=" + camera.y, 500, 40);
    }
    ////////////////////////////// KEYBOARD INPUTS ////////////////////////////////
    /**
     * Handles key press events for player movement and when game stage changes.
     */

    @Override
    public void keyPressed() {
        if (key == 't' || key == 'T') { // Press T to toggle tile debug view
        levels[currLevel].toggleDebugView();
        }

        if (stage == 0){// controls for when the game starts
            if (key == 'a'|| key == 'A') person.keyLeft = true; // LEFT
            else if (key == 'd'|| key == 'D') person.keyRight = true; // RIGHT
            if (key == 'w'|| key == 'W') person.keyUP = true; // UP
            else if (key == 's'|| key == 'S') person.keyDown = true; // DOWN 
        } 
        else if(stage == 2){ /// to cycle through the story
            if (key == ' ') {
                if (dialogueSystem.isInDialogue()) { // if current page/scene requires dialog
                    dialogueSystem.nextDialogueLine();
                } else {
                    currStoryStage++;
                    if (currStoryStage >= STORY_PAGES) {
                        stage = 0;
                        currStoryStage = 0;
                        dialogueSystem.resetDialogue();
                    }// end if
                }// end else
            }// end if
        }// end else
        else if (stage == 3) { // ending story
            if (key == ' ') {
                currStoryStage++;
                if (currStoryStage >= ENDING_PAGES) {
                    promptForReview();
                }
            }
        }
        // if its time for review wowowow
        if (isReviewTime && (key == ENTER || key == RETURN)) {
            if (!currReview.trim().isEmpty()) {
                // put to file
                reviewWriter.println(currReview);
                reviewWriter.flush();
                // reset and exit
                currReview = "";
                isReviewTime = false;
                exit();
            }
        }
    }
    /**
     * Handles key release for stopping player movement.
     */

    @Override
    public void keyReleased() {
        if (key == 'a' || key == 'A') {
            person.keyLeft = false;
        }else if (key == 'd'|| key == 'D') {
            person.keyRight = false;
        }if (key == 'w'|| key == 'W') {
            person.keyUP = false;
        }else if (key == 's'|| key == 'S') {
            person.keyDown = false;
        }
    }

    //////////////////////// COLLISON DETECTION /////////////////////////////
        /**
     * Checks for collision between player and enemy rectangles.
     * referenced from John McCaffery on youtube
     * @param r1 The player rectangle
     * @param r2 The enemy rectangle
     * @return true if rectangles intersect, false otherwise
     */
    boolean rectangleIntersect(Person r1, Enemy r2){
        float distX = abs((r1.x+r1.w/2) - (r2.x+r2.w/2));
        float distY = abs((r1.y+r1.h/2)-(r2.y+r2.h/2));
        float combinedHalfWidths = r1.w/2+r2.w/2;
        float combinedHaldHeights = r1.h/2 +r2.h/2;
        
        if(distX<combinedHalfWidths){
            if(distY<combinedHaldHeights){
                return true;
            }
        }
    return false;
    }
    
    ///////////////// MOUSE TRACKING AND PROJECTILE ///////////////////////
    /**
     * Handles mouse press events for shooting projectiles.
     * referenced from John McCaffery on youtube
     */
    @Override
    public void mousePressed(){
        if(shootingCD <= 0){
            float startX =person.x + person.playerImages[0].width/2;
            float startY = person.y + person.playerImages[0].height/2;
            projectiles.add(new Projectiles(this, startX, startY, mouseX+camera.x, mouseY + camera.y));
            shootingCD = SHOOT_DELAY;
        }
    }
    /**
     * displays current story page and handles dialogue.
     * 
     */
    public void story(){
        image(storyImages[currStoryStage], 0, 0, width, height);
        
        if (currStoryStage == dialogueSystem.getDialoguePage() && !dialogueSystem.isDialogueFinished()) {
            dialogueSystem.setInDialogue(true);
            dialogueSystem.displayDialogue();
        }
    }
    
    /////// settor methods basically
    /**
     * Starts the story. 
     */
    public void startStory(){
        stage = 2;
    }
    /**
     * ends story starts the main gmae loop. 
     */
    public void endStory(){
        stage = 0;
    }
    /**
     * advances the story. 
     */
    public void showNextStoryPage() {
        currStoryStage++;
        if (currStoryStage >= STORY_PAGES) {
            endStory();
        }
    }
    /**
     * display ending page
     */
    // ending pages
    private void ending() {
        image(endingImages[currStoryStage], 0, 0, width, height);
    }
    /**
     * Initializes review write for player feedbakc
     * debugged by deepseek
     */
    private void initReviewWriter() {
    try { 
        reviewWriter = new PrintWriter(new FileWriter("reviews.txt"), true);
        } catch (IOException e) {
            println("Error creating review file: " + e.getMessage());
        }
    }
    /**
     * Prompts user for review after game ends. 
     */
    private void promptForReview() {
        isReviewTime = true;
        currReview = "";
    }
    /**
     * draws review screen.
     */
    private void drawReviewInput() {
        background(0);
        fill(255);
        textSize(32);
        textAlign(CENTER, CENTER);
        text("Please leave your review:", width / 2, height / 2 - 50);
        // user input
        textSize(24);
        text(currReview, width / 2, height / 2);

        textSize(18);
        text("Press ENTER to submit", width / 2, height / 2 + 50);
    }
    /**
     * exits when user write review.
     */
    @Override
    public void exit() { //  closes the file rrite wehn program close
        if (reviewWriter != null) {
            reviewWriter.close();
        }
        super.exit();
    }
    /**
     * Handles keyboard typings in review.
     */
    @Override
    public void keyTyped() {
        if (isReviewTime) {
            /// can delete wow
            if (key == '\b' && currReview.length() > 0) {
                currReview = currReview.substring(0, currReview.length() - 1);
            } // only get characters 
            else if (key != '\n' && key != '\r') {
                currReview += key;
            }
        }
    }

}
