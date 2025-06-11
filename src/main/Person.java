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

    /////////////// player animation
    public PImage playerImages[];
    private int currentFrame = 0;
    private int frameCount = 0;
    int playerFrames, offset;
    private int frameRep = 8;
    private int animationSpeed = 4;
    
    // main constructor
    public Person(PApplet app) {
        this.app = app;
        this.x = app.width/2; // starts player form the middle of the screen
        this.y = app.height/2;

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
        //boundary check    
//        if(x+ playerImages[0].width >= app.width){ // doesnt go past furthest right
//            x=app.width-playerImages[0].width;
//        }else if (x <= 0){ // left
//            x=0;
//        }if (y+playerImages[0].height>= app.height){ // down
//            y=app.height-playerImages[0].height;
//        }else if (y<=0){ // up
//            y=0;
//        }
        frameCount++;
        if (frameCount % animationSpeed == 0) { 
            currentFrame = (currentFrame + 1) % frameRep;
        }
        // resests 
        dx = 0;
        dy = 0;
    }
    // drawing the player
    public void draw(){
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
