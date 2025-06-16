package main;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author shuze
 */

import processing.core.PApplet;
/**
 * The GameObject class is the base class for all game entities. It
 * provides properties and methods like
 *  position, collision detection, and basic rendering/drawing.
 * @author shuze
 */
public class GameObject {
    public PApplet app;
    public float x, y;
    public int w, h;
    public float dx, dy;
    /**
     * Constructs a new GameObject with default properties.
     * @param app The PApplet instance
     * @param x Initial x-coordinate position
     * @param y Initial y-coordinate position
     */
    public GameObject(PApplet app, float x, float y) {
        this.app = app;
        this.x = x;
        this.y = y;
    }
    /**
     * Update method to be overridden by subclasses. 
     */
    public void update() {} 
    /**
     * draw method to be overridden by subclasses. 
     */
    public void draw() {}
    /**
     * Checks for collision with another game object. Uses axis-aligned bounding
     * box collision detection.
     *
     * @param other The other GameObject to check against
     * @return true if objects are colliding, false otherwise
     */
    public boolean collidesWith(GameObject other) {
        return !(x >= other.x + other.w ||
                x + w <= other.x ||
                y >= other.y + other.h ||
                y + h <= other.y);
    }
    /**
     * Gives a response when interacting with another game object.
     * To be overridden by subclasses.
     * @param other The GameObject being interacted with
     * @return An interaction response string
     */
    public String respondTo(GameObject other) {
        return ""; // Default empty response
    }
    
    // getters
    /**
     * Gets the x-coordinate position.
     *
     * @return Current x position
     */
    public float getX() { return x; }
    /**
     * Gets the y-coordinate position.
     *
     * @return Current y position
     */
    public float getY() { return y; }
    /**
     * Gets the width of the object.
     *
     * @return Object width
     */
    public int getWidth() { return w; }
    /**
     * Gets the height of the object.
     *
     * @return Object height
     */
    public int getHeight() { return h; }
}