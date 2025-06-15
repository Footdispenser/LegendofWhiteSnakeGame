/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author 342964137
 */
import processing.core.PApplet;
import processing.core.PImage;

public class Person {
    private PApplet app;
    /////////////// player settings
    public int x,y,dx,dy, w, h;
    public boolean keyLeft, keyRight, keyUP, keyDown;
    public int speed = 8;
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
    
    // main constructor
    public Person(PApplet app) {
        this.app = app;
        this.x = 740; // starts player form the middle of the screen
        this.y = 1300;

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
    
    public void takeDamage(int damage) {
        // millis() is just converting to milisceonds
        if (app.millis() - lastHitTime > invulnerabilityDuration) {
            currHp = PApplet.max(0, currHp - damage);
            lastHitTime = app.millis();

            if (currHp <= 0) {
                // player death player death here
                System.out.println("Player died!");
            }
        }
    }

    // for movement/animations
    public void update(){
        //////////////////// player movement ////////////////////////////////
        if (keyLeft){
            dx -= speed;
            offset = 8;// changes animation when it moves left
        }if (keyRight){
            dx += speed;
            offset = 0;// animation for moving right
        } 
        if (keyUP) dy -= speed; // when moving upwards
        if (keyDown) dy += speed; // when moving downards
        // code to move the player
        x += dx;
        y += dy;
        ////////////////////////////////////////////////////////////////////
        frameCount++;
        if (frameCount % animationSpeed == 0) { 
            currentFrame = (currentFrame + 1) % frameRep;
        }
        // resests 
        dx = 0;
        dy = 0;
    }
    // draws player hp bar
    public void drawHpBar(PApplet app) {
        float barWidth = 200;
        float barHeight = 20;
        float hpPercent = (float) currHp / maxHp; // converts to percentage

        // Background
        app.fill(50);
        app.rect(20, 20, barWidth, barHeight);

        // Health fill
        app.fill(255, 0, 0);
        app.rect(20, 20, barWidth * hpPercent, barHeight);

        // Text
        app.fill(255);
        app.textSize(16);
        app.text("HP: " + currHp + "/" + maxHp, 25, 35);
    }

    // drawing the player
    public void draw(){
//        app.fill(128, 128, 128);
//        app.circle(x + playerImages[0].width / 2, y + playerImages[0].height / 2, 400);
        // shows player hitbox
        app.fill(255,0,0);
        app.rect(x + hitboxOffsetX, y, hitboxWidth, hitboxHeight);

        // draws the player
        app.image(playerImages[currentFrame+offset], x, y);
    }
    // players coordinates
    public void displayInfo(PApplet p) {
        app.fill(0);
        app.text("X: " + x, x, y-20);
        app.text("Y: " + y, x, y-10);
    }
}
