/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;
import processing.core.PApplet;

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
    private int width = pixelW*row;
    private int height = pixelL*col;
    private Person person;
    private Person person2;
    public int speed = 4;
    private int stage = 0;
    private boolean keyUP, keyDown, keyLeft, keyRight;
    
    public void settings(){
        size((width), height);  
    }
    
    public void setup(){
        background(255);
        person = new Person(this, width/2, height/2,"images/WhiteSnake_0 (1) (1).png");
        person2 = new Person(this, 400, 400,"images/BiggerGrassBlock.png");
    }
    
    public void draw() {
    background(255);
        person2.draw();
        person.draw();

        // can be changed to universal speed when needed
        // add to person class
       int dx = 0;
       int dy = 0;
        
        if (keyLeft) dx -= speed;
        if (keyRight) dx += speed;
        if (keyUP) dy -= speed;
        if (keyDown) dy += speed;
        person.displayInfo(this);
        person.move(dx, dy);
  }
    public void keyPressed() {
        if (key == 'a') keyLeft = true; // LEFT
        else if (key == 'd') keyRight = true; // RIGHT
        if (key == 'w') keyUP = true; // UP
        else if (key == 's') keyDown = true; // DOWN 
   }

    public void keyReleased() {
        if (key == 'a') {
            keyLeft = false;
        }else if (key == 'd') {
            keyRight = false;
        }if (key == 'w') {
            keyUP = false;
        }else if (key == 's') {
            keyDown = false;
        }
    }


}

