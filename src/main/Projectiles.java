/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;
import processing.core.PApplet;
import processing.core.PImage;


/**
 * The Projectiles class represents projectiles or bullets fired in the game. It
 * handles projectile movement, collision detection, and rendering.
 * referenced from John McCaffery on youtube
 *  @author 342964137
 */
public class Projectiles {
    PApplet app;
    public float x, y, vx, vy;
    public float speed = 30;
    public int size = 20;
    public boolean active = true;
    PImage image;

    /**
     * Constructs a new Projectile that moves toward a target(mouse) position.
     *
     * @param app The PApplet instance
     * @param startX The initial x-coordinate of the projectile
     * @param startY The initial y-coordinate of the projectile
     * @param targetX The x-coordinate of the target position
     * @param targetY The y-coordinate of the target position
     */
    public Projectiles(PApplet app, float startX, float startY, float targetX, float targetY){
         this.app = app;
         this.x = startX;
         this.y = startY;
         
         float angle = PApplet.atan2(targetY - startY, targetX -startX);
         // components
         this.vx = PApplet.cos(angle) * speed;
         this.vy = PApplet.sin(angle) * speed;
         // what the bullet looks like
         this.image = app.loadImage("images/ball (1).png");
    }// end main constructor
    /**
     * Updates the projectile's position and checks for collisions with walls.
     * 
     *  @param level The current level used for wall collision detection
     */
    public void update(level level){
        x+=vx;
        y+=vy;
        
        // Check for wall collisions
        if (level.isProjectileCollidingWithWall(this)) {
            active = false;
        }

    }//end
    /**
     * Draws the projectile at its current position.
     */
    public void draw(){
        app.fill(0);
        app.image(image, x, y);
    }//end
    /**
     * Checks if the projectile has hit an enemy.
     * @param e The Enemy to collide against
     * @return true if the projectile overlaps with the enemy, false otherwise
     */
    public boolean hits(Enemy e){
        return(x > e.x && x < e.x + e.w &&
                y>e.y && y<e.y+e.h);
    }//end
}// end class
