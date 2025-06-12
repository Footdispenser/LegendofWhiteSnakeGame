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
public class Projectiles {
    PApplet app;
    public float x, y, vx, vy;
    public float speed = 5;
    public int size = 20;
    public boolean active = true;
    PImage image;

    
    public Projectiles(PApplet app, float startX, float startY, float targetX, float targetY){
         this.app = app;
         this.x = startX;
         this.y = startY;
         
         float angle = PApplet.atan2(targetY - startY, targetX -startX);
         // components
         this.vx = PApplet.cos(angle) * speed;
         this.vy = PApplet.sin(angle) * speed;
         this.image = app.loadImage("images/ball (1).png");
    }// end main constructor
    
    public void update(){
        x+=vx;
        y+=vy;
        
        // deactivate after out of map
        if(x<0  || y<0 ){
            active =false;
        }// end if
    }//end
    public void draw(){
        app.fill(0);
//        app.rect(x, y, size, size);
        app.image(image, x, y);
    }//end
    
    public boolean hits(Enemy e){
        return(x > e.x && x < e.x + e.w &&
                y>e.y && y<e.y+e.h);
    }//end
}// end class
