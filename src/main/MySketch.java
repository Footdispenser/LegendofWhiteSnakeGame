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
    public int pixelW = 32;
    public int pixelL = 32;
    public int col = 25;
    public int row = 50;
    public int width = pixelW*row;
    public int height = pixelL*col;
    // end screen settings
    // perosn attributes
    
    private Person person;
    private Enemy e1;
    private tilemap map;
    // other misc
    private int stage = 0;
    
    private Camera camera;
    
    public void settings(){
        size((width), height);  
    }
    public void setup(){
        background(255);
        map = new tilemap(this, person);
        person = new Person(this);
        e1 = new Enemy(this, 500, 600,person, person.x, person.y,person.playerImages[0].width, person.playerImages[0].height);
        camera = new Camera(this);
    }
    
    public void draw() {
        // send the player information to the camera for accurate following
        camera.follow( person.x, person.y, width, height, 
        person.playerImages[0].width, person.playerImages[0].height);
        // activates the camera/applys it
        camera.apply();
        // draws the background 
        map.draw();
        
        // testing black rectangle
        fill(0);
        rect(500,600, 50, 60);
        
        // drawing in the player
        person.update(); // player movements
        person.draw(); // player looks
        person.displayInfo(this); // testing - display player x and y
        e1.update();
        e1.draw();
        // rests
        resetMatrix();
        // testing - shows corrdinates of the camera and player
        fill(0);
        text("Player: x=" + person.x + " , y=" + person.y, 500, 20);
        text("Camera: x=" + camera.x + " , y=" + camera.y, 500, 40);
    }// end draw
    
    ////////////////////////////// KEYBOARD INPUTS ////////////////////////////////
    public void keyPressed() {
        if (key == 'a'|| key == 'A') person.keyLeft = true; // LEFT
        else if (key == 'd'|| key == 'D') person.keyRight = true; // RIGHT
        if (key == 'w'|| key == 'W') person.keyUP = true; // UP
        else if (key == 's'|| key == 'S') person.keyDown = true; // DOWN 
   }
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
}

