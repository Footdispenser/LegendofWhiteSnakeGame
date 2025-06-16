/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;


/**
 * The Dialogue class handles in-game dialogue systems including loading, displaying
 * It got speaker identification
 * referenced from deepseek
 * @author shuze
 */
import processing.core.PApplet;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Dialogue {
    private PApplet app;
    private ArrayList<String> dialogueLines = new ArrayList<>(); /// array to store all the dialogues
    private int currentDialogueLine = 0;
    private boolean inDialogue = false;
    private boolean dialogueFinished = false;
    private String currentSpeaker = "";
    private int dialoguePage;
    private String dialogueFilePath;
    
    /**
     * Constructs a new dialogue system.
     *
     * @param app The PApplet instance
     * @param filePath Path to the dialogue text file
     */
    public Dialogue(PApplet app, String filePath) {
        this.app = app;
        this.dialogueFilePath = filePath;
        loadDialogue();
    }
    
    /**
     * Loads dialogue lines from the text file. File format expects
     * [Speaker] followed by dialogue lines.
     */
    private void loadDialogue() {
        dialogueLines.clear();
        try {
            File file = new File(dialogueFilePath);
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    if (!line.isEmpty()) {
                        dialogueLines.add(line);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error loading dialogue file: " + e.getMessage());
        }
    }
    /**
     * Saves current dialogue lines back to the text file.
     * restores speaker tags and line breaks.
     */
    public void saveDialogue() {
        try {
            try (PrintWriter writer = new PrintWriter(new FileWriter(dialogueFilePath))) {
                for (String line : dialogueLines) {
                    writer.println(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving dialogue file: " + e.getMessage());
        }
    }
    /**
     * Adds a new dialogue entry with speaker and text.
     *
     * @param speaker Name of the speaker
     * @param text Dialogue content
     */
    public void addDialogueLine(String speaker, String text) {
        dialogueLines.add("[" + speaker + "]");
        dialogueLines.add(text);
        saveDialogue();
    }
    /**
     * Displays the current dialogue line in a styled text box. 
     * Seperates the speaker and the dialogue
     */
    public void displayDialogue() {
        if (currentDialogueLine >= dialogueLines.size()) {
            inDialogue = false;
            dialogueFinished = true;
            return;
        }

        String line = dialogueLines.get(currentDialogueLine);
        if (line.startsWith("[")) {
            String[] parts = line.split("\\]", 2);
            if (parts.length >= 2) {
                currentSpeaker = parts[0].substring(1);
                String dialogueText = parts[1].trim();
                drawDialogueBox(currentSpeaker, dialogueText);
            }
        } else {
            // case where line doesn't start with [ (continuation of previous dialogue)
            drawDialogueBox(currentSpeaker, line);
        }
    }
    /**
     * Renders the dialogue box UI with speaker name and text.
     *
     * @param speaker Name of the current speaker
     * @param text Dialogue content to display
     */
    private void drawDialogueBox(String speaker, String text) {
        // semi-transparent black background for dialogue
        app.fill(0, 200);
        app.noStroke();
        app.rect(50, app.height - 150, app.width - 100, 120);
        
        // speaker name
        app.fill(255);
        app.textSize(20);
        app.textAlign(PApplet.LEFT, PApplet.TOP);
        app.text(speaker, 70, app.height - 130);
        
        // dialogue text
        app.fill(255);
        app.textSize(16);
        app.text(text, 70, app.height - 100, app.width - 140, 80);
        
        // "Press space to continue" 
        app.fill(200);
        app.textSize(14);
        app.textAlign(PApplet.RIGHT, PApplet.BOTTOM);
        app.text("Press SPACE to continue...", app.width - 70, app.height - 60);
    }
    /**
     * Advances to the next line of dialogue. handles dialogue
     * completion state.
     */
    public void nextDialogueLine() {
        currentDialogueLine++;
        if (currentDialogueLine >= dialogueLines.size()) {
            inDialogue = false;
            dialogueFinished = true;
        }
    }

    // Getters and setters
    /**
     * Checks if dialogue is currently being displayed.
     *
     * @return true if in dialogue, false otherwise
     */
    public boolean isInDialogue() {
        return inDialogue;
    }
    /**
     * Sets the dialogue active state.
     *
     * @param inDialogue true to start dialogue, false to end
     */
    public void setInDialogue(boolean inDialogue) {
        this.inDialogue = inDialogue;
    }
    /**
     * Checks if all dialogue has been shown.
     *
     * @return true if dialogue complete, false otherwise
     */
    public boolean isDialogueFinished() {
        return dialogueFinished; 
    }
    /**
     * Sets the dialogue completion state.
     *
     * @param dialogueFinished true to mark as complete
     */
    public void setDialogueFinished(boolean dialogueFinished) {
        this.dialogueFinished = dialogueFinished; 
    }
    /**
     * Gets the current dialogue page/section.
     *
     * @return current page number
     */
    public int getDialoguePage() {
        return dialoguePage; 
    }
    /**
     * Sets the current dialogue page/section.
     *
     * @param dialoguePage page number to set
     */
    public void setDialoguePage(int dialoguePage) {
        this.dialoguePage = dialoguePage; 
    }
    /**
     * Gets the current dialogue line index.
     *
     * @return current line number
     */
    public int getCurrentDialogueLine() { return currentDialogueLine; 
    }
    /**
     * Resets dialogue to the beginning. Clears completion state and returns to
     * first line.
     */
    public void resetDialogue() { 
        currentDialogueLine = 0;
        dialogueFinished = false;
    }
}

