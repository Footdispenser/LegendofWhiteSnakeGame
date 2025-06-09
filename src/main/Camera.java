/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author shuze
 */
import processing.core.PApplet;

public class Camera {
    public float x, y;
    private float targetX, targetY;
    private float smoothness = (float) 1;
    private PApplet app;

    
    public Camera(PApplet app){
        this.app = app;
        this.x= 0;
        this.y =0;
    }
    
    public void follow( float targetX, float targetY, float screenW, float screenH, float pW, float pH){
        this.x = targetX - screenW /2 + pW/2;
        this.y = targetY - screenH/2 + pH/2;
        
//        x = PApplet.lerp(x,targetX, smoothness);
//        y = PApplet.lerp(y,targetY, smoothness);
        
    }
    public void apply(){
        app.translate(-x,-y);
    }
    
}
