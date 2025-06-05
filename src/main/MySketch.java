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
    private Person person;
    private int stage = 0;
    private boolean keyUP, keyDown, keyLeft, keyRight;

    public void settings(){
        size(1000, 600);  
    }
    
    public void setup(){
        background(255);
        person = new Person(this, 0, 0,"images/car.png");
    }
    
    public void draw() {
    background(255);
        person.draw();

        // can be changed to universal speed when needed
        // add to person class
       int xSpeed = 0;
       int ySpeed = 0;
        
        if (keyLeft) xSpeed -= 5;
        if (keyRight) xSpeed += 5;
        if (keyUP) ySpeed -= 5;
        if (keyDown) ySpeed += 5;
        
        person.move(xSpeed, ySpeed);
  }
    public void keyPressed() {
        if (key == 'a') {
            keyLeft = true;
        }else if (key == 'd') {
            keyRight = true;
        }
        if (key == 'w') {
            keyUP = true;
        }else if (key == 's') {
            keyDown = true;
        }
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

