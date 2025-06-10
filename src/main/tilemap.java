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
    // 1 is wall 0 is floor
    PApplet app;
    Person p;
    int maprow[] = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    int [][]tilemap = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
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
                    default:
                        System.out.println("map uhoh");
                }// end switch
            }//end col for
        }//end for row
    }// end render
    
    public void isPlayerColliding(){
        String collisionSide = "none";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
              if(tilemap[i][j]==1){
                  int distX = PApplet.floor((p.x+ p.w/2)-(j*cellWidth+cellWidth/2));
                  int distY = PApplet.floor((p.y + p.h/2) - (i * cellHeight + cellHeight / 2));
                  int combindedHalfW = PApplet.floor(p.w/2+cellWidth/2);
                  int combindedHalfH = PApplet.floor(p.h / 2 + cellHeight / 2);
                  //check for x overlap
                  if(PApplet.abs(distX)<combindedHalfW){
                      //check for y overlap
                      if(PApplet.abs(distY)<combindedHalfH){
                          int overlapX = combindedHalfW-PApplet.abs(distX);
                          int overlapY = combindedHalfW-PApplet.abs(distY);
                          //look for smallest overlaps
                          if(overlapX>= overlapY){
                              //correct Y pos
                              if(distY>0){
                                  collisionSide = "TOP";
                                  p.y += overlapY;
                                  
                              }else{
                                  collisionSide = "BOTTOM";
                                  p.y -= overlapY;
                              }// end y corrections
                          }else{// correct x pos
                              if(distY>0){
                                  collisionSide = "LEFT";
                                  p.x += overlapX;

                              }else{
                                  collisionSide = "RIGHT";
                                  p.x -= overlapX;
                              }
                          }
                      }
                      System.out.println(collisionSide);
                  }
              }  
            }
        }
    }
}
