/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;



import processing.core.PApplet;
import processing.core.PImage;
/**
 * The Person class represents the player character in the game.
 * It handles player movement, animations, health management, and rendering.
 * Extends GameObject to inherit basic game object properties and behaviors.
 *  @author 342964137
 */
public class Person extends GameObject {
    /////////////// player settings
    public int x, y, dx, dy;
    public boolean keyLeft, keyRight, keyUP, keyDown;
    public int speed = 10;
    public int hitboxOffsetX = 25; // 21 pixels right from player.x
    public int hitboxWidth = 68;   // Hitbox width
    public int hitboxHeight = 105; // Hitbox height
    public int maxHp = 100;
    public int currHp = maxHp;
    private int lastHitTime = 0;
    private final int invulnerabilityDuration = 120; // 2 seconds
    
    /////////////// player animation
    public PImage playerImages[];
    private int currentFrame = 0;
    private int frameCount = 0;
    int playerFrames, offset;
    private int frameRep = 8;
    private int animationSpeed = 3; 
    /**
    * Main Constructs that constructs a new Player character.
    * @param app The PApplet instance
    * referenced from John McCaffery on youtube
    */
    // constructor
    public Person(PApplet app) {
        super(app, 740, 1300); // calls parent constructor
        // load all images frames
        this.offset = 0;
        playerFrames = 16;
        playerImages = new PImage[playerFrames];
        for (int i = 0; i < playerFrames; i++) {
            String frameNum = String.format("%02d", i);// formats it into 2 decimals
            playerImages[i] = app.loadImage(
                    "images/White Snake Frames/WhiteSnake_" + frameNum + ".png");
        }
        this.w = playerImages[0].width;
        this.h = playerImages[0].height;
    }
    /**
     * Applies damage to the player with invulnerability frames.
     *
     * @param damage The amount of damage to take
     * referenced from John McCaffery on youtube
     */
    public void takeDamage(int damage) {
        // millis() is just converting to milliseconds
        if (app.millis() - lastHitTime > invulnerabilityDuration) {
            currHp = PApplet.max(0, currHp - damage);
            lastHitTime = app.millis();

            if (currHp <= 0) {
                // player death player death here
                System.out.println("Player died!");
            }
        }
    }
    /**
     * Updates player position and animation based on input. Called once per
     * frame.
     * referenced from John McCaffery on youtube
     */
    // for movement/animations
    public void update(){
        //////////////////// player movement ////////////////////////////////
        if (keyLeft){
            dx -= speed;
            offset = 8;// changes animation when it moves left
        }
        if (keyRight){
            dx += speed;
            offset = 0;// animation for moving right
        } 
        if (keyUP) dy -= speed; // when moving upwards
        if (keyDown) dy += speed; // when moving downwards
        
        // code to move the player
        x += dx;
        y += dy;
        
        ////////////////////////////////////////////////////////////////////
        frameCount++;
        if (frameCount % animationSpeed == 0) { 
            currentFrame = (currentFrame + 1) % frameRep;
        }
        // resets 
        dx = 0;
        dy = 0;
    }
    
    // draws player hp bar
    /**
     * Draws the player's health bar on screen.
     *
     * @param app The PApplet instance to draw to
     */
    public void drawHpBar(PApplet app) {
        app.pushStyle();

        float barWidth = 200;
        float barHeight = 20;
        float hpPercent = (float) currHp / maxHp;

        // Background (grey)
        app.fill(50);
        app.rect(20, 20, barWidth, barHeight);

        // Health (red)
        app.fill(255, 0, 0);
        app.rect(20, 20, barWidth * hpPercent, barHeight);

        // Border (white)
        app.noFill();
        app.stroke(255);
        app.rect(20, 20, barWidth, barHeight);

        // text (white)
        app.fill(255);
        app.textSize(16);
        app.textAlign(PApplet.LEFT, PApplet.TOP);
        app.text("HP: " + currHp + "/" + maxHp, 25, 22);

        // redo it drawing style
        app.popStyle();
    }

    // drawing the player
    /**
     * Draws the player character at its current position. 
     * Overrides the GameObject draw method
     */
    @Override
    public void draw(){
        // draws the player
        app.image(playerImages[currentFrame+offset], x, y);
    }

//    // players coordinates
//    public void displayInfo(PApplet p) {
//        app.fill(0);
//        app.text("X: " + x, x, y-20);
//        app.text("Y: " + y, x, y-10);
//    }
    /**
     * Generates a response when interacting with another GameObject.
     *
     * @param other The GameObject being interacted with
     * @return A response string based on the interaction
     */
    public String respondTo(GameObject other){
        if(other instanceof Enemy){
            return "Hi";
        } 
        return "wefohi";
    }
}