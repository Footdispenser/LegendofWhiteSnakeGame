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
        if (keyPressed) {
            if (keyCode == LEFT) {
              person.move(-5, 0);
            } else if (keyCode == RIGHT) {
              person.move(5, 0);
            } else if (keyCode == UP) {
              person.move(0, -5);
            } else if (keyCode == DOWN) {
              person.move(0, 5);
            }
        }
  }

}
