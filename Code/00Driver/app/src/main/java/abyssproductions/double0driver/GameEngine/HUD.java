package abyssproductions.double0driver.GameEngine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;

import abyssproductions.double0driver.GameGlobals;
import abyssproductions.double0driver.GameObjects.Sprite;
import abyssproductions.double0driver.R;

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
    //  PURPOSE:    Holds the players current number of lives
    private int numLives;
    //  PURPOSE:    Holds the dimension of the fire button
    private Rect fireButtonDim;
    //  PURPOSE:    Holds the dimension of the switch button
    private Rect switchButtonDim;
    //  PURPOSE:    Holds the button's image size
    private Rect buttonsImageSize;
    //  PURPOSE:    Holds the fire button's image
    private Bitmap fireButtonImage;
    //  PURPOSE:    Holds the switch button's image
    private Bitmap switchButtonImage;
    //  PURPOSE:    Holds the player's life image
    private Bitmap playerLifeImage;
    //  PURPOSE:    Holds the current score
    private int score;
    //  PURPOSE:    Holds the Y location for the text
    private int healthTextYPos;
    //  PURPOSE:    Holds the Y location for the text
    private int textYPos;
    //  PURPOSE:    Holds the primary weapon ammo amount
    private int primaryWeaponAmmo;
    //  PURPOSE:    Holds the amount of time that the pop text will appear
    private int popTextTimer;
    //  PURPOSE:    Holds the position for the pop text to appear
    private Point popTextPos;
    //  PURPOSE:    Holds the pop text that appears
    private String popText;
    //  PURPOSE:    Holds the paint setting for the pop textdrawable objects
    private Paint popTextPaint;
    //  PURPOSE:    Holds the paint setting for the drawable objects
    private Paint paint;

    /** PURPOSE:    Constructor for the HUD that set the default value for the object
     *  INPUT:      primaryWeaponType   - The primary weapon type the player is using
     *              secondaryWeaponType - The secondary weapon type the player is using
     *  OUTPUT:     NONE
     */
    public HUD(Sprite.WeaponTypes primaryWeaponType, Sprite.WeaponTypes secondaryWeaponType){
        float heightRatio = GameGlobals.getInstance().getScreenHeight()/1752f;
        float widthRatio = GameGlobals.getInstance().getScreenWidth()/1080f;
        numLives = 3;
        score = 0;
        healthBar = new Rect(0,(int)(50*heightRatio),GameGlobals.getInstance().getScreenWidth(),
                (int)(100*heightRatio));
        fireButtonDim = new Rect(0,0,(int)(300*widthRatio),(int)(300*heightRatio));

        fireButtonDim.offset((int)(50*widthRatio), GameGlobals.getInstance().getScreenHeight()-
                fireButtonDim.height()-(int)(100*heightRatio));

        switchButtonDim = new Rect(0,0,(int)(200*widthRatio),(int)(200*heightRatio));

        switchButtonDim.offset(GameGlobals.getInstance().getScreenWidth()-
                switchButtonDim.width()-(int)(50*widthRatio),
                GameGlobals.getInstance().getScreenHeight()-
                        switchButtonDim.height()-(int)(100*heightRatio));

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(healthBar.height());
        popTextPaint = new Paint();
        popTextPaint.setStyle(Paint.Style.FILL);
        popTextPaint.setTextSize(healthBar.height()/2);
        textYPos = (int)paint.getTextSize();
        healthTextYPos = healthBar.top+(int)paint.getTextSize();
        currentWeaponTypes(primaryWeaponType,secondaryWeaponType);

        buttonsImageSize = new Rect(0,0,GameGlobals.getInstance().getImageResources().getInteger(
                R.integer.ButtonImageSize),GameGlobals.getInstance().getImageResources().getInteger(
                R.integer.ButtonImageSize));

        Matrix tempMatrix = new Matrix();
        tempMatrix.setScale(0.45f,0.45f);
        /*Rect orgImageSize = new Rect(0,0,GameGlobals.getInstance().getImageResources().
                getInteger(R.integer.VehicleImageWidth),GameGlobals.getInstance().
                getImageResources().getInteger(R.integer.PlayerImageHeight));
        RectF newImageSize = new RectF(0,0,(textYPos/GameGlobals.getInstance().getImageResources().
                getInteger(R.integer.VehicleImageWidth))*textYPos,textYPos);*/
        playerLifeImage = Bitmap.createBitmap(GameGlobals.getInstance().getImages().
                getPlayerImage(),0,0, GameGlobals.getInstance().getImageResources().
                getInteger(R.integer.VehicleImageWidth),GameGlobals.getInstance().
                getImageResources().getInteger(R.integer.PlayerImageHeight),tempMatrix,false);
        popTextPos =new Point(0,0);
        popTextTimer = 0;
        popText = "";
    }

    /** PURPOSE:    Draws the HUD
     *  INPUT:      canvas              - Pointer to the surface screen's canvas
     *  OUTPUT:     NONE
     */
    public void draw(Canvas canvas){
        paint.setTextSize(healthBar.height());
        paint.setColor(Color.WHITE);
        canvas.drawText("Score: " + score,0, textYPos,paint);
        int temp = GameGlobals.getInstance().getScreenWidth()-playerLifeImage.getWidth();
        for(int i = 0; i < numLives; i++){
            canvas.drawBitmap(playerLifeImage,temp,0,paint);
            temp = temp - 2 - playerLifeImage.getWidth();
        }
        canvas.drawText("Lives: ", temp - 100, textYPos,paint);

        paint.setColor(Color.RED);
        canvas.drawRect(healthBar,paint);
        paint.setColor(Color.WHITE);
        canvas.drawText("" + curHealth + "/" + maxHealth,0,healthTextYPos,paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(healthBar.height()*2);
        canvas.drawBitmap(fireButtonImage,buttonsImageSize,fireButtonDim,paint);
        canvas.drawText("" + primaryWeaponAmmo,fireButtonDim.centerX()-(paint.getTextSize()),
                fireButtonDim.centerY()+(paint.getTextSize()/2),paint);
        canvas.drawBitmap(switchButtonImage,buttonsImageSize,switchButtonDim,paint);
        if(popTextTimer > 0)canvas.drawText(popText,popTextPos.x,popTextPos.y,popTextPaint);
    }

    /** PURPOSE:    Reset the HUD to initial state for new game
     *  INPUT:      primaryWeaponType   - The primary weapon type the player is using
     *              secondaryWeaponType - The secondary weapon type the player is using
     *  OUTPUT:     NONE
     */
    public void reset(Sprite.WeaponTypes primaryWeaponType, Sprite.WeaponTypes secondaryWeaponType){
        numLives = 3;
        score = 0;
        currentWeaponTypes(primaryWeaponType,secondaryWeaponType);
    }

    /** PURPOSE:    Calculates the length of the health bar
     *  INPUT:      newCurHealth        - The current health value
     *              newMaxHealth        - The maximum health value
     *  OUTPUT:     NONE
     */
    public void setHealthLevels(float newCurHealth, float newMaxHealth){
        healthBar.right = (int)((newCurHealth/newMaxHealth)*
                GameGlobals.getInstance().getScreenWidth());
        curHealth = (int)newCurHealth;
        maxHealth = (int)newMaxHealth;
    }

    /** PURPOSE:    Sets the number of current lives
     *  INPUT:      newNumLives         - Holds the new number of lives
     *  OUTPUT:     NONE
     */
    public void setNumLives(int newNumLives){
        numLives = newNumLives;
    }

    /** PURPOSE:    Decrease the number of lives by one when the curHealth is 0 and destroyed is true
     *  INPUT:      destroyed           - Holds whether the destroyed animation is complete
     *  OUTPUT:     NONE
     */
    public void lifeLost(boolean destroyed){
        if (destroyed && curHealth <= 0){
            numLives--;
        }
    }

    /** PURPOSE:    Updates the HUD objects
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    public void update(){
        score++;
        popTextTimer--;
        if(popTextPaint.getAlpha()<=0)popTextPaint.setAlpha(popTextPaint.getAlpha()-9);
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

    /** PURPOSE:    Sets the current button image for the primary and secondary active weapons
     *  INPUT:      primary             - The current active primary weapon
     *              secondary           - The current active secondary weapon
     *  OUTPUT:     NONE
     */
    public void currentWeaponTypes(Sprite.WeaponTypes primary, Sprite.WeaponTypes secondary){
        switch (primary) {
            case MachineGun:
                fireButtonImage = GameGlobals.getInstance().getImages().getMachineGunButtonImage();
                break;
            case Missile:
                fireButtonImage = GameGlobals.getInstance().getImages().getMissileLauncherButtonImage();
                break;
            case Flamethrower:
                fireButtonImage = GameGlobals.getInstance().getImages().getFlameThrowerButtonImage();
                break;
            case Laser:
                fireButtonImage = GameGlobals.getInstance().getImages().getLaserCannonButtonImage();
                break;
        }
        switch (secondary) {
            case MachineGun:
                switchButtonImage = GameGlobals.getInstance().getImages().getMachineGunButtonImage();
                break;
            case Missile:
                switchButtonImage = GameGlobals.getInstance().getImages().getMissileLauncherButtonImage();
                break;
            case Flamethrower:
                switchButtonImage = GameGlobals.getInstance().getImages().getFlameThrowerButtonImage();
                break;
            case Laser:
                switchButtonImage = GameGlobals.getInstance().getImages().getLaserCannonButtonImage();
                break;
        }
    }

    /** PURPOSE:    Sets the current primary weapons ammo amount
     *  INPUT:      amount              - The current ammo amount
     *  OUTPUT:     NONE
     */
    public void setCurrentWeaponAmmo(int amount){
        primaryWeaponAmmo = amount;
    }

    /** PURPOSE:    Sets the pop text for the ammo item the player retrieved
     *  INPUT:      type                - The type of weapon that ammo is for
     *              increaseAmount      - The amount the ammo increased by
     *  OUTPUT:     NONE
     */
    public void setAmmoIncreasePopText(Sprite.WeaponTypes type, int increaseAmount){
        popText = type.toString() + "ammo increased by " + increaseAmount;
        popTextTimer = 30;
        popTextPaint.setAlpha(255);
    }

    /** PURPOSE:    Sets the pop text for the health item the player retrieved
     *              increaseAmount      - The amount the health increased by
     *  OUTPUT:     NONE
     */
    public void setHealthIncreasePopText(int increaseAmount){
        popText = "Health was restored by " + increaseAmount;
        popTextTimer = 30;
        popTextPaint.setAlpha(255);
    }

    /** PURPOSE:    Sets the pop text position
     *              x                   - The x position for the text
     *              y                   - The y position for the text
     *  OUTPUT:     NONE
     */
    public void setPopTextPos(int x, int y){
        Rect bounds = new Rect();
        popTextPaint.getTextBounds(popText, 0, popText.length(), bounds);
        popTextPos.set((int)Math.abs((x-(bounds.width()*.33))),y);
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
        else if(button == 1 && switchButtonDim.contains((int)x,(int)y)) return true;
        return false;
    }

    /** PURPOSE:    Returns the score
     *  INPUT:      NONE
     *  OUTPUT:     Returns an int with the value of the score
     */
    public int getScore(){
        return score;
    }

    /** PURPOSE:    Returns the current number of life
     *  INPUT:      NONE
     *  OUTPUT:     Returns an int with the number of the lives
     */
    public int getNumLives(){
        return numLives;
    }
}
