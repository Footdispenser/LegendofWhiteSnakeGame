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

public class Enemy {
    public boolean keyLeft, keyRight, keyUP, keyDown;
    public int x,y,dx,dy, w, h;
    PApplet app;
    private int targetX,targetY;
    private float pW, pH;
    private int speed = 3;
    private Person person;

    public Enemy(PApplet app, int x, int y, Person person, int targetX, int targetY, float pW, float pH){
        this.app = app;
        this.x = x;
        this.y = y;
        this.w = 64;
        this.h = 64;
        this.pW =pW;
        this.pH = pH;
        this.person = person;
        this.targetX = targetX;
        this.targetY= targetY;
        keyLeft =false;
        keyRight =false;
        keyUP =false;
        keyDown = false;
    }
    
    
    public void update(){
        
        targetX = person.x;
        targetY = person.y;

       float distApart = PApplet.dist(x+(w/2),y+h/2, targetX+(pW/2),targetY + (pH/2));
       //chase
       if(distApart < 300){
           if(x<targetX){
               keyRight = true;
               keyLeft = false;
           } else if(x>targetX){
               keyRight = false;
               keyLeft = true;
           }if(y<targetY){
               keyDown = true;
               keyUP = false;
           } else if(y > targetY){
               keyDown = false;
               keyUP = true;
           }
       }else{
           keyLeft = false;
           keyRight = false;
           keyUP = false;
           keyDown = false; 
       }// end chase
        if (keyLeft) dx -= speed; 
        if (keyRight)dx += speed;
        if (keyUP) dy -= speed;
        if (keyDown)dy += speed;
        x+=dx;
        y+=dy;
        dx =0;
        dy=0;
    }
    
    public void draw(){
        app.fill(0,255,255);
        app.rect(x, y, w, h);
    }


}
