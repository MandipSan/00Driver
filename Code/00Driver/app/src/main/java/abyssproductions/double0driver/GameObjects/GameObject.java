package abyssproductions.double0driver.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by Mandip Sangha on 1/31/2017.
 */

public class GameObject {
    //  PURPOSE:    Holds the object's left, top, right, bottom coordinates
    private RectF myDimensions;

    private Paint myPaint;
    //  PURPOSE:    Holds the object's current frame image location
    private Rect myCurFrameLoc;
    //  PURPOSE:    Holds the object's images
    protected Bitmap myImage;
    //  PURPOSE:    Holds the object's movement velocity
    protected Point myVelocity;

    protected AnimateState myCurAniState;
    public enum AnimateState{Normal,Destroyed}

    /*  PURPOSE:    Constructor for the Game Object that set the default value for the object
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public GameObject(){
        myDimensions.set(0,0,0,0);
        myVelocity.set(0,0);
    }

    /*  PURPOSE:    Draws the game object's image to the screen
        INPUT:      canvas          - Pointer to the surface screen's canvas
        OUTPUT:     NONE
    */
    public void draw(Canvas canvas){
        canvas.drawBitmap(myImage,myCurFrameLoc,myDimensions,myPaint);
    }

    /*  PURPOSE:    Updates the game object's logic
        INPUT:      NONE
        OUTPUT:     NONE
    */
    public void update(){

    }

    /*  PURPOSE:    Runs the animation for the game object
        INPUT:      NONE
        OUTPUT:     NONE
    */
    public void animate(){

    }

    /*  PURPOSE:    Set's the game object's current position and size
        INPUT:      newDimension    - The new position and size to set
        OUTPUT:     NONE
    */
    public void setMyDimensions(RectF newDimension){
        myDimensions.set(newDimension);
    }

    /*  PURPOSE:    Return's the game object's left, top, right, bottom coordinates
        INPUT:      NONE
        OUTPUT:     A Rect object with the data
    */
    public RectF getDimensions(){
        return myDimensions;
    }

    /*  PURPOSE:    Move's the game object vertically by the amount given
        INPUT:      moveBy          - The amount to move the game object by
        OUTPUT:     NONE
    */
    protected void moveVertical(float moveBy){
        myDimensions.offset(0,moveBy);
    }

    /*  PURPOSE:    Move's the game object horizontally by the amount given
        INPUT:      moveBy          - The amount to move the game object by
        OUTPUT:     NONE
    */
    protected void moveHorizontal(float moveBy){
        myDimensions.offset(moveBy,0);
    }
}
