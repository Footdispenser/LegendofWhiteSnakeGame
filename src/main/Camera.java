/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;


import processing.core.PApplet;
/**
 * The Camera class handles player tracking
 * It manages the visible area of the game world by adjusting the
 * drawing coordinates.
 * referenced from deepseek for translate method
 * @author shuze
 */
public class Camera {
    public float x, y;
    private float targetX, targetY;
    private PApplet app;

    /**
     * Constructs a new Camera instance.
     *
     * @param app The PApplet instance used for rendering
     */
    public Camera(PApplet app){
        this.app = app;
        this.x= 0;
        this.y =0;
    }
    /**
     * Updates the camera's target position to follow a specified object.
     * Centers the viewing area/camera on the target while adding the  screen
     * dimensions.
     *
     * @param targetX The x-coordinate of the target to follow
     * @param targetY The y-coordinate of the target to follow
     * @param screenW The width of the screen
     * @param screenH The height of the screen
     * @param pW The width of the target object (for precise centering)
     * @param pH The height of the target object (for precise centering)
     */
    public void follow( float targetX, float targetY, float screenW, float screenH, float pW, float pH){
        this.x = targetX - screenW /2 + pW/2;
        this.y = targetY - screenH/2 + pH/2;       
    }
    /**
     * applies the camera's current position to the rendering of the camera.
     * Translates the drawing coordinates to create the camera effect.
     */
    public void apply(){
        app.translate(-x,-y);
    }
    
}
