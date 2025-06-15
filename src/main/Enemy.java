/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author 342964137
 */
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;

public class Enemy {
    public boolean keyLeft, keyRight, keyUP, keyDown;
    public int x,y,dx,dy, w, h;
    PApplet app;
    private int targetX,targetY;
    private float pW, pH;
    private int speed = 5;
    private Person person;
    // respawn for the enemy
    private int spawnX, spawnY;
    private final float pushForce = 0.9f;
    
    private int lastAttackTime = 0;
    private final int attackCooldown = 120; // 2 seconds at 60FPS
    private final int attackDamage = 5;

    public  boolean isDead = false;
    private int respawnTimer = 0;
    private final int RESPAWN_DELAY = 60 * 5; // change second number cuz it represents seconds
    
    ///////// ANIMATIONS ///////////////////
    public PImage enemyImages[];
    private int currentFrame = 0;
    private int frameCount = 0;
    int enemyFrames, offset;
    private int frameRep = 8;
    private int animationSpeed = 3;
    
    private float actualX, actualY; // for smoothing
    private float moveLerp = 0.3f; // the range for lerp - change for how smooth you want the movement - lower  = smoother

    
    public Enemy(PApplet app, int x, int y, Person person, int targetX, int targetY, float pW, float pH){
        this.app = app;
        this.x = x;
        this.y = y;
        this.w = 64;
        this.h = 64;
        this.pW =pW;
        this.pH = pH;
        this.spawnX = x;
        this.spawnY = y;
        this.person = person;
        this.targetX = targetX;
        this.targetY= targetY;
        keyLeft =false;
        keyRight =false;
        keyUP =false;
        keyDown = false;
        
        // load all images frames
        this.offset = 0;
        enemyFrames = 16;
        enemyImages = new PImage[enemyFrames];
        for (int i = 0; i < enemyFrames; i++) {
            String frameNum = String.format("%02d", i);// formats it into 2 decimals
            enemyImages[i] = app.loadImage(
                    "images/Enemy/Enemy_" + frameNum + ".png");
        }
    }
    
    // sets the enemies postion to their intial pos/ spawn point
    public void respawn(){
        this.x = spawnX;
        this.y = spawnY;
    }
    // if this method is called changes the boolean to true
    public void die() {
        isDead = true;
        respawnTimer = RESPAWN_DELAY;
    }
    
    public boolean collidesWith(Enemy other) {
        return !(x >= other.x + other.w
                || x + w <= other.x
                || y >= other.y + other.h
                || y + h <= other.y);
    }
    public void EnemyCollisions(ArrayList<Enemy> allEnemies) {
        for (Enemy other : allEnemies) {
            if (other != this && collidesWith(other)) {
                resolveCollision(other);
            }
        }
    }
    
    private void resolveCollision(Enemy other) {
        // Calculate center points
        float thisCenterX = x + w / 2;
        float thisCenterY = y + h / 2;
        float otherCenterX = other.x + other.w / 2;
        float otherCenterY = other.y + other.h / 2;

        // Calculate difference vector
        float dx = thisCenterX - otherCenterX;
        float dy = thisCenterY - otherCenterY;
        float distance = PApplet.max(1, PApplet.sqrt(dx * dx + dy * dy));

        // apply push force
        dx /= distance;
        dy /= distance;

        x += dx * pushForce;
        y += dy * pushForce;
        other.x -= dx * pushForce;
        other.y -= dy * pushForce;
        keyLeft = false;
        keyRight = false;
        keyUP = false;
        keyDown = false;


    }
    
    public boolean canAttack() {
        return app.millis() - lastAttackTime > attackCooldown;
    }
    
    public void attack(Person player) {
        if (canAttack()) {
            player.takeDamage(attackDamage);
            lastAttackTime = app.millis();
        }
    }


    public void update(level level){
        // checks the respawn time and updates it
        if (isDead) {
            respawnTimer--;
            if (respawnTimer <= 0) {
                respawn();
                isDead = false;
            }
            return;
        }
        keyLeft = false;
        keyRight = false;
        keyUP = false;
        keyDown = false;
        
        // updates the and tracks the players coordinates
        targetX = person.x;
        targetY = person.y;

       float distApart = PApplet.dist(x+(w/2),y+h/2, targetX+(pW/2),targetY + (pH/2));
       //chase
       if(distApart < 700){
           if(x<targetX+ pW/2 - 10){
               keyRight = true;
               keyLeft = false;
               offset = 0;

           } else if(x>targetX+ pW/2 - 10){
               keyRight = false;
               keyLeft = true;
               offset = 8;
           }if(y<targetY+ pH/2 - 10){
               keyDown = true;
               keyUP = false;
           } else if(y > targetY+ pH/2 - 10){
               keyDown = false;
               keyUP = true;
           }else{
                keyLeft = false;
                keyRight = false;
                keyUP = false;
                keyDown = false; 
           }
       }
       // end chase
  
        dx =0;
        dy=0;
        if (keyLeft) dx -= speed; 
        if (keyRight)dx += speed; 
        if (keyUP) dy -= speed;
        if (keyDown)dy += speed;
        x+=dx;
        y+=dy;

        
        frameCount++;
        if (frameCount % animationSpeed == 0) {
            currentFrame = (currentFrame + 1) % frameRep;
        }
        // smoothing the x, y for smooth movment lerp() find closest/range of two variables
        actualX = PApplet.lerp(actualX, x, moveLerp);
        actualY = PApplet.lerp(actualY, y, moveLerp);
    }
    
    public void draw(){
        if (isDead) return; // dont draw if the enemy is dead  
        app.image(enemyImages[currentFrame + offset], actualX, actualY);
//        app.fill(0,255,255);
//        app.rect(x, y, w, h);
    }
    

    

}
