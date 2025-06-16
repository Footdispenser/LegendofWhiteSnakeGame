package main;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * this level class represents a game level  with tile based maps
 * it handles level rendering, collsion detecion, and special tile mangement
 * @author 342964137
 * referenced from John McCaffery on youtube
 */

public class level {
    PApplet app;
    private int [][] tilemap;
    public int cellWidth, cellHeight;
    public ArrayList<int[]> enemySpawnPoints;
    public ArrayList<int[]> telepoints;
    public int[] playerSpawnPoint;
    Person p;
    int rows, cols;
    private PImage backgroundImage; // Added for background image
    private boolean showTiles; // Toggle for debug view
    /**
     * default constructor setting the cell with and height values
     */
    public level(){
        this.cellWidth = 64;
        this.cellHeight = 64;

    }
    /**
     * Mian constructor for creating a level with tilemap and background
     * 
     * @param app The PApplet insatnce
     * @param tilemap 2D array representing the level layout
     * @param playerSpawn Player spawn corrdinates [x,y]
     * @param p Reference to player/person object
     * @param bgImagePath path the background image/what it gonna be
     */
    public level(PApplet app, int [][] tilemap, int [] playerSpawn, Person p, String bgImagePath){
        this.app = app;
        this.p = p;
        this.tilemap = tilemap;
        this.rows = tilemap.length;
        this.cols = tilemap[0].length;
        this.cellWidth = 64;
        this.cellHeight = 64;
        this.playerSpawnPoint = playerSpawn;
        this.enemySpawnPoints = new ArrayList<>();
        this.telepoints = new ArrayList<>();
        this.showTiles = false; // default to false to show background

        // loads background image
        if (bgImagePath != null && !bgImagePath.isEmpty()) {
            this.backgroundImage = app.loadImage(bgImagePath);
        }
        
        scanSpecialTiles();
    }
    /**
    * Toggle between background and tile debug view
    **/
    public void toggleDebugView() {
        showTiles = !showTiles;
    }
    
    /**
     * Scans the tilemap for special tiles (enemy spawns and teleport points).
     */
    public void scanSpecialTiles(){
        for(int y =0; y<tilemap.length; y++){
            for(int x=0;x<tilemap[y].length; x++){
                if(tilemap[y][x] == 3){ // enemy spawn
                    enemySpawnPoints.add(new int[]{
                    x * cellWidth,
                    y * cellHeight,
                    cellWidth,
                    cellHeight
                    });// end enemySpawnPoints
                }// end if
                else if( tilemap [y][x] == 2){
                    telepoints.add(new int[]{
                        x * cellWidth + cellWidth / 2,
                        y * cellHeight + cellHeight / 2,
                        cellWidth,
                        cellHeight
                    });// end telepoints
                }// end else if
            }// end for
        }// end for
    }// end method
    ///// gettor methods
    /**
     * Gets the player spawn point coordinates.
     *
     * @return Array containing [x,y] spawn coordinates
     */
    public int[] getPlayerSpawnPoint(){
        return playerSpawnPoint;
    }// end gettor
    
    /**
     * Gets all enemy spawn points in the level.
     * @return List of enemy spawn points [x,y,width,height]
     */
    public ArrayList<int[]> getEnemySpawns(){
        return enemySpawnPoints;
    }
    
    /**
     * Draws the level - either background image or tile debug view.
     */
    public void draw(){
        if (backgroundImage != null && !showTiles) { // draws image background
            app.image(backgroundImage, 0, 0);
        } else { //  draws tile view to see hitboxs and reducing lag
            app.background(255);
            for(int y =0; y<tilemap.length; y++){
                for(int x=0;x<tilemap[y].length; x++){
                    switch (tilemap[y][x]){
                        case 0: app.fill(80); break;
                        case 1: app.fill(114, 188, 128); break;
                        case 2: app.fill(0, 255, 255); break;
                        case 3: app.fill(0, 55, 255); break;
                    }
                    app.rect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
                }
            }
        }
    }
    
    /**
     * Checks if a projectile is colliding with a wall tile.
     * @param p The projectile to check
     * @return true if colliding with wall, false otherwise
     * referenced from John McCaffery on youtube
     */
    public boolean isProjectileCollidingWithWall(Projectiles p) {
        // Convert projectile position to grid coordinates
        int gridX = (int) (p.x / cellWidth);
        int gridY = (int) (p.y / cellHeight);

        // Check if the projectile is within the map bounds
        if (gridX >= 0 && gridX < cols && gridY >= 0 && gridY < rows) {
            // Check if the tile is a wall (1)
            return tilemap[gridY][gridX] == 1;
        }
        return false;
    }
    /**
     * Handles player collision with wall tiles. Resolves collisions by pushing
     * player out of walls.
     * referenced from John McCaffery on youtube
     */
    public void isPlayerColliding() {
        // cycle through array
        rows = tilemap.length;
        cols = tilemap[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (tilemap[i][j] == 1) { // If tile is a wall
                    int wallLeft = j * cellWidth;
                    int wallRight = wallLeft + cellWidth;
                    int wallTop = i * cellHeight;
                    int wallBottom = wallTop + cellHeight;

                    // player hitbox
                    int playerLeft = p.x + p.hitboxOffsetX;
                    int playerRight = playerLeft + p.hitboxWidth;
                    int playerTop = p.y;
                    int playerBottom = playerTop + p.hitboxHeight;

                    // check if player is intersecting with the wall
                    if (playerRight > wallLeft
                            && playerLeft < wallRight
                            && playerBottom > wallTop
                            && playerTop < wallBottom) {

                        // Calculate overlap on each side
                        int overlapLeft = playerRight - wallLeft;
                        int overlapRight = wallRight - playerLeft;
                        int overlapTop = playerBottom - wallTop;
                        int overlapBottom = wallBottom - playerTop;

                        // find the smallest overlap to resolve collision
                        int minOverlap = Math.min(Math.min(overlapLeft, overlapRight),
                                Math.min(overlapTop, overlapBottom));

                        // resolve collision based on the smallest overlap
                        if (minOverlap == overlapLeft) {
                            p.x = wallLeft - p.hitboxWidth - p.hitboxOffsetX; // Push left
                            p.dx = 0;
                        } else if (minOverlap == overlapRight) {
                            p.x = wallRight - p.hitboxOffsetX; // Push right
                            p.dx = 0;
                        } else if (minOverlap == overlapTop) {
                            p.y = wallTop - p.hitboxHeight; // Push up
                            p.dy = 0;
                        } else if (minOverlap == overlapBottom) {
                            p.y = wallBottom; // Push down
                            p.dy = 0;
                        }

                    }
                }
            }
        }
    }

}
