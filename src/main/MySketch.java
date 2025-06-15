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

    private ArrayList<Enemy> enemies = new ArrayList<>();
    
    ///// projectile properties
    private ArrayList <Projectiles> projectiles = new ArrayList<>();
    private int shootingCD = 0;
    private final int SHOOT_DELAY = 10; // frames
    
    // other misc
    private int stage = 0;
    
    private Camera camera;
    private level[] levels;
    private int currLevel = 0;
    
    public void settings(){
        size((width), height);  
    }
    
    public void setup(){
        person = new Person(this);
        camera = new Camera(this);
        levels = new level[2];
        ////////////////////////// Level 1
        int map1[][] = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3},
            {3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3},
            {3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
        int [] playerSpawn1 = {740, 1300};
        levels[0] = new level(this, map1, playerSpawn1,person);
        ////////////////////////////// level 2
        int map2[][] = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3},
            {3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3},
            {3, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 1, 3},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
        int[] playerSpawn2 = {740, 1300};
        levels[1] = new level(this, map2, playerSpawn2 , person);

//        background(0);
        setPlayerPos();
        initializeEnemies();

    }
    ///////////////// SETUP METHODS //////////////////////////
    private void setPlayerPos(){
        int[]spawn = levels[currLevel].getPlayerSpawnPoint();
        person.x = spawn[0];
        person.y = spawn[1];
        
    }
    private void initializeEnemies() {
        enemies.clear();
        ArrayList<int[]> spawns = levels[currLevel].getEnemySpawns();
        

        for (int k = 0; k < spawns.size(); k++) {
            int[] pos = spawns.get(k);
            enemies.add(new Enemy(this, pos[0], pos[1], person,
                    person.x, person.y,
                    person.playerImages[0].width,
                    person.playerImages[0].height));

        }
    }

    private void checkTeleport() {
        for (int[] teleport : levels[currLevel].telepoints) {
            if (person.x + person.w > teleport[0]
                    && person.x < teleport[0] + teleport[2]
                    && person.y + person.h > teleport[1]
                    && person.y < teleport[1] + teleport[3]) {

                // change level
                currLevel = (currLevel + 1) % levels.length;

                // Reset player to new level's spawn point
                setPlayerPos();

                // Create enemies for new level
                initializeEnemies();

                break;
            }
        }
    }
    ////////////////////// DRAW //////////////////////////////////
    public void draw() {
        background(0);
        switch (stage){
            case 0:
                drawGame();
                break;
            case 1:
                //// draw death message
                fill(0, 0, 0, 200);
                rect(0, 0, width, height);

                // Death message
                fill(255);
                textSize(48);
                textAlign(CENTER, CENTER);
                text("YOU DIED", width / 2, height / 2 - 50);

        }
    }///////////////////////////// END DRAW ///////////////////////////////////////
    
    private void drawGame(){
        /// checks if teleported or not / checks current stage
        checkTeleport();

        // send the player information to the camera for accurate following
        camera.follow(person.x, person.y, width, height,
                person.playerImages[0].width, person.playerImages[0].height);

        camera.apply();// activates the camera/applys it

        ///////////////////////// level /////////////////////////////////////
        levels[currLevel].draw();
        levels[currLevel].isPlayerColliding();

        ///////////////////////// player/person /////////////////////////////////////
        person.update(); // player movements
        person.draw(); // player looks
        person.displayInfo(this); // testing - display player x and y

        ///////////////////////// enemies /////////////////////////////////////
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy e = enemies.get(i);
            e.update(levels[currLevel]);
            if (e.isDead) {
                continue; // skips dead enemies so you dont take invisible dmg
            }

            e.EnemyCollisions(enemies);

            if (rectangleIntersect(person, e)) {
                e.attack(person);
//                fill(255, 0, 0, 25);
//                rect(0, 0, width, height);
                if(person.currHp <=0){ // if the player dies
                    stage += 1;
                }

            }
            e.draw();

        }

        //////////////////////// projectiles ////////////////////////////////
        for (int i = projectiles.size() - 1; i >= 0; i--) {
            Projectiles p = projectiles.get(i);
            p.update(levels[currLevel]);
            p.draw();
            // enemy collision check
            for (int j = enemies.size() - 1; j >= 0; j--) {
                Enemy e = enemies.get(j);
                if (!e.isDead && p.hits(e)) {// only checks alive enemies so projectiles dont hit inisible enemies
                    e.die(); // kills the enemy/ sets isDead to true
                    p.active = false;
                    break;
                }
            }
            // removes the projectile
            if (!p.active) {
                projectiles.remove(i);
            }
        }
        if (shootingCD > 0) {
            shootingCD--;
        }

        // resets
        resetMatrix();

        ///////////////////////// hp bar /////////////////////////////////////
        person.drawHpBar(this);

        // testing - shows corrdinates of the camera and player
        fill(0);
        text("Player: x=" + person.x + " , y=" + person.y, 500, 20);
        text("Camera: x=" + camera.x + " , y=" + camera.y, 500, 40);
    }
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
    
    //////////////////////// COLLISON DETECTION /////////////////////////////
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
    
    ///////////////// MOUSE TRACKING AND PROJECTILE ///////////////////////
    public void mousePressed(){
        if(shootingCD <= 0){
            float startX =person.x + person.playerImages[0].width/2;
            float startY = person.y + person.playerImages[0].height/2;
            projectiles.add(new Projectiles(this, startX, startY, mouseX+camera.x, mouseY + camera.y));
            shootingCD = SHOOT_DELAY;
        }
    }
    
}
