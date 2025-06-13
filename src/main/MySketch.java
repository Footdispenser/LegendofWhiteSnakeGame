/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;
import java.util.ArrayList;
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
//    private Enemy e1;
    private tilemap map;
    private Enemy [] enemies;
    private ArrayList <Projectiles> projectiles = new ArrayList<>();
    private int shootingCD = 0;
    private final int SHOOT_DELAY = 10; // frames
    
    // other misc
    private int stage = 0;
    
    private Camera camera;
    
    public void settings(){
        size((width), height);  
    }
    public void setup(){
        background(0);
        person = new Person(this);
        camera = new Camera(this);
        map = new tilemap(this, person);
        enemies = new Enemy[10];
        ////////// initalise all the enemies
        for(int k = 0;k<enemies.length;k++){
            int[] floorPos = map.getRandomFloorPosition();
            enemies[k] = new Enemy(this, floorPos[0], floorPos[1], 
                    person, person.x, person.y,
                    person.playerImages[0].width, person.playerImages[0].height);
        }


    }
    
    public void draw() {
        // send the player information to the camera for accurate following
        camera.follow( person.x, person.y, width, height, 
                       person.playerImages[0].width, person.playerImages[0].height);
        // activates the camera/applys it
        camera.apply();
        
        // draws the background 
        map.draw();
        
        // drawing in the player
        person.update(); // player movements
        person.draw(); // player looks
        person.displayInfo(this); // testing - display player x and y
        
//        e1.update();
//        e1.draw();
        /////////// ENEMIES /////////////////////////////////////
        for(int k = 0; k < enemies.length; k++) {
            if(enemies[k]!=null){
                enemies[k].update(map);
                enemies[k].draw();
                if(rectangleIntersect(person, enemies[k])){
                    fill(255,0,0,25);
                    rect(0,0,width, height);
                }
            }
        }
        /// projectiles////////////////////////////////
        for(int i = projectiles.size()-1;i>=0; i--){
            Projectiles p = projectiles.get(i);
            p.update(map);
            p.draw();
            // hit enemy
            for(int j = enemies.length-1;j>=0; j--){
                if(enemies[j] != null && p.hits(enemies[j])){
                        // creates new enemy
//                    int[] floorPos = map.getRandomFloorPosition();
//                    enemies[j] = new Enemy(this, floorPos[0], floorPos[1], 
//                        person, person.x, person.y,
//                        person.playerImages[0].width, person.playerImages[0].height);
                    enemies[j] = null;
                    p.active =false;
                    break;
                }
            }
            //removes the enemy
            if(!p.active){
                projectiles.remove(i);
            }
        }
        if(shootingCD>0){
            shootingCD --;
        }
        // resets
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
    // intersects
    boolean rectangleIntersect(Person r1, Enemy r2){
        float distX = abs((r1.x+r1.w/2) - (r2.x+r2.w/2));
        float distY = abs((r1.y+r1.h/2)-(r2.y+r2.h/2));
        float combinedHalfWidths = r1.w/2+r2.w/2;
        float combinedHaldHeights = r1.h/2 +r2.h/2;
        
        if(distX<combinedHalfWidths){
            if(distY<combinedHaldHeights){
                return true;
            }
        }
    return false;
    }
    // mouse press to shoot projectile
    public void mousePressed(){
        if(shootingCD <= 0){
            float startX =person.x + person.playerImages[0].width/2;
            float startY = person.y + person.playerImages[0].height/2;
            projectiles.add(new Projectiles(this, startX, startY, mouseX+camera.x, mouseY + camera.y));
            shootingCD = SHOOT_DELAY;
        }
    }
    
}
