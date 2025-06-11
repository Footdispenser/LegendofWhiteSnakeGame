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
public class tilemap{
    // 1 is wall 0 is floor 2 is tele pad
    PApplet app;
    Person p;
    int maprow[] = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    int [][]tilemap = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
    int rows, cols;
    int cellWidth, cellHeight;
    
    public tilemap(PApplet app, Person p){
        this.app =app;
        this.p = p;
        this.rows = tilemap.length;
        this.cols = tilemap[0].length;
        cellWidth = 64;
        cellHeight = 64;
    }
    public void draw(){
        app.background(255);
        rendermap();
        isPlayerColliding();
    }
    
    public void rendermap(){
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<cols;j++){
                switch(tilemap[i][j]){
                    case 0:
                        app.fill(80);
                        app.rect(j*cellWidth,i*cellHeight,cellWidth,cellHeight);
                        break;
                    case 1:
                        app.fill(114,188,128);
                        app.rect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                        break;
                    case 2:
                        app.fill(0, 255, 255);
                        app.rect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                        break;

                    default:
                        System.out.println("map uhoh");
                }// end switch
            }//end col for
        }//end for row
    }// end render
    
    public void isPlayerColliding() {
        // cycle through array
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

                    // Check if player is intersecting with the wall
                    if (playerRight > wallLeft
                            && playerLeft < wallRight
                            && playerBottom > wallTop
                            && playerTop < wallBottom) {

                        // Calculate overlap on each side
                        int overlapLeft = playerRight - wallLeft;
                        int overlapRight = wallRight - playerLeft;
                        int overlapTop = playerBottom - wallTop;
                        int overlapBottom = wallBottom - playerTop;

                        // Find the smallest overlap to resolve collision
                        int minOverlap = Math.min(Math.min(overlapLeft, overlapRight),
                                Math.min(overlapTop, overlapBottom));

                        // Resolve collision based on the smallest overlap
                        if (minOverlap == overlapLeft) {
                            p.x = wallLeft - p.hitboxWidth - p.hitboxOffsetX; // Push player left
                            p.dx = 0;
                        } else if (minOverlap == overlapRight) {
                            p.x = wallRight - p.hitboxOffsetX; // Push player right
                            p.dx = 0;
                        } else if (minOverlap == overlapTop) {
                            p.y = wallTop - p.hitboxHeight; // Push player up
                            p.dy = 0; 
                        } else if (minOverlap == overlapBottom) {
                            p.y = wallBottom; // Push player down
                            p.dy = 0; 
                        }


                    }
                }
            }
        }
    }
}
