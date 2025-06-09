/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;
import processing.core.PApplet;
import processing.core.PImage;

/**
 *
 * @author 342964137
 */
public class MySketch extends PApplet {
    // screen settings
    private int pixelW = 32;
    private int pixelL = 32;
    private int col = 25;
    private int row = 50;
    public int width = pixelW*row;
    public int height = pixelL*col;
    // end screen settings
    // perosn attributes
    
    private Person person;
    // other misc
    private int stage = 0;
    
    private Camera camera;
    
    public void settings(){
        size((width), height);  
    }
    public void setup(){
        background(255);
        person = new Person(this);
        camera = new Camera(this);
    }
    
    public void draw() {
        // send the player information to the camera for accurate following
        camera.follow( person.x, person.y, width, height, 
                person.playerImages[0].width, person.playerImages[0].height);
        // activates the camera/applys it
        camera.apply();
        // draws the background 
        background(255);
        
        // testing black rectangle
        fill(0);
        rect(500,600, 50, 60);
        
        // drawing in the player
        person.update(); // player movements
        person.draw(); // player looks
        person.displayInfo(this); // testing - display player x and y
        // rests
        resetMatrix();
        // testing - shows corrdinates of the camera and player
        fill(0);
        text("Player: x=" + person.x + " , y=" + person.y, 500, 20);
        text("Camera: x=" + camera.x + " , y=" + camera.y, 500, 40);
    }
    
    ////////////////////////////// KEYBOARD INPUTS ////////////////////////////////
    public void keyPressed() {
        if (key == 'a') person.keyLeft = true; // LEFT
        else if (key == 'd') person.keyRight = true; // RIGHT
        if (key == 'w') person.keyUP = true; // UP
        else if (key == 's') person.keyDown = true; // DOWN 
   }
    public void keyReleased() {
        if (key == 'a') {
            person.keyLeft = false;
        }else if (key == 'd') {
            person.keyRight = false;
        }if (key == 'w') {
            person.keyUP = false;
        }else if (key == 's') {
            person.keyDown = false;
        }
    }


}

