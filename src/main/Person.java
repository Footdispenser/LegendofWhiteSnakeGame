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
    public int x,y;
    private PImage image;
    private PApplet app;
    private int width, height;
    
    public Person(PApplet p, int x, int y, String imagePath) {
        this.app = p;
        this.x = x;
        this.y = y;
        this.image = app.loadImage(imagePath);
        this.width = image.width;
        this.height = image.height;
    }
    
    public void move(int dx, int dy){
        x += dx;
        y += dy;
    }
    
    public void moveTo(int dx, int dy){
        x = dx;
        y = dy;
    }
    
    public void draw(){
        app.image(image, x, y);
    }
    public void displayInfo(PApplet p) {
        app.fill(0);
        app.text("X: " + x, x, y-20);
        app.text("Y: " + y, x, y-10);
    }


}
