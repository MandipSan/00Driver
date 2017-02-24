package abyssproductions.double0driver.GameEngine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Mandip Sangha on 2/15/2017.
 */

public class HUD {
    //  PURPOSE:    Holds the dimension of the health bar
    private Rect healthBar;
    //  PURPOSE:    Holds the max health value
    private int maxHealth;
    //  PURPOSE:    Holds the current health value
    private int curHealth;
    //  PURPOSE:    Holds the dimension of the fire button
    private Rect fireButtonDim;
    //  PURPOSE:    Holds the dimension of the switch button
    private Rect switchButtonDim;
    //  PURPOSE:    Holds the current score
    private int score;
    //  PURPOSE:    Holds the paint setting for the drawable objects
    private Paint paint;

    /** PURPOSE:    Constructor for the HUD that set the default value for the object
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    public HUD(){
        score = 0;
        healthBar = new Rect(0,50,1200,100);
        fireButtonDim = new Rect(0,0,300,100);
        fireButtonDim.offset(100,1500);
        switchButtonDim = new Rect(0,0,300,100);
        switchButtonDim.offset(600,1500);
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(50);
    }

    /** PURPOSE:    Draws the HUD
     *  INPUT:      canvas              - Pointer to the surface screen's canvas
     *  OUTPUT:     NONE
     */
    public void draw(Canvas canvas){
        paint.setColor(Color.WHITE);
        canvas.drawText("Score: " + score,0,40,paint);
        paint.setColor(Color.RED);
        canvas.drawRect(healthBar,paint);
        paint.setColor(Color.WHITE);
        canvas.drawText("" + curHealth + "/" + maxHealth,0,80,paint);
        canvas.drawRect(fireButtonDim,paint);
        canvas.drawRect(switchButtonDim,paint);
    }

    /** PURPOSE:    Calculates the length of the health bar
     *  INPUT:      newCurHealth           - The current health value
     *              newMaxHealth           - The maximum health value
     *  OUTPUT:     NONE
     */
    public void setHealthLevels(float newCurHealth, float newMaxHealth){
        healthBar.right = (int)((newCurHealth/newMaxHealth)*1200);
        curHealth = (int)newCurHealth;
        maxHealth = (int)newMaxHealth;
    }

    /** PURPOSE:    Increase the score
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    public void updateScore(){
        score++;
    }

    /** PURPOSE:    Increase the score by the amount given
     *  INPUT:      increaseBy          - The amount the score is to be increased by
     *  OUTPUT:     NONE
     */
    public void scoreIncreaseBy(int increaseBy){
        score+=increaseBy;
    }

    /** PURPOSE:    Reduce the score by the amount given
     *  INPUT:      reduceBy            - The amount to reduce the score by
     *  OUTPUT:     NONE
     */
    public void reduceScoreBy(int reduceBy){
        score -=reduceBy;
    }

    /** PURPOSE:    Checks if the a button was pressed returns the result
     *  INPUT:      button              - The button to check(NOTE:0 is the fire button and 1 is the
     *                                      switch button)
     *              x                   - The x value of the press location
     *              y                   - The y value of the press location
     *  OUTPUT:     Returns a boolean value of true when pressed else false
     */
    public boolean buttonPressed(int button, float x, float y){
        if(button == 0 && fireButtonDim.contains((int)x,(int)y))return true;
        else if(button == 1 && switchButtonDim.contains((int)x,(int)y))return true;
        return false;
    }

    /** PURPOSE:    Returns the score
     *  INPUT:      NONE
     *  OUTPUT:     Returns an int with the value of the score
     */
    public int getScore(){
        return score;
    }
}
