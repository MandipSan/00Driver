package abyssproductions.double0driver.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Mandip Sangha on 1/31/2017.
 */

public class GameObject {
    //  PURPOSE:    Holds the object's current location and size
    private Rect myDimensions;
    //  PURPOSE:    Holds the object's image
    protected Bitmap myImage;
    //  PURPOSE:    Holds the object's movement velocity
    protected Point myVelocity;

    /*  PURPOSE:    Constructor for the Game Object that set the default value for the object
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public GameObject(){

    }

    /*  PURPOSE:    Draws the game object's image to the screen
        INPUT:      canvas          - Pointer to the surface screen's canvas
        OUTPUT:     NONE
    */
    public void draw(Canvas canvas){

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

    /*  PURPOSE:    Return's the game object's current location and size
        INPUT:      NONE
        OUTPUT:     A Rect object with the data
    */
    public Rect getDimensions(){
        return myDimensions;
    }

    /*  PURPOSE:    Set's the game object's current position and size
        INPUT:      newDimension    - The new position and size to set
        OUTPUT:     NONE
    */
    public void setMyDimensions(Rect newDimension){

    }

    /*  PURPOSE:    Move's the game object vertically by the amount given
        INPUT:      moveBy          - The amount to move the game object by
        OUTPUT:     NONE
    */
    protected void moveVertical(int moveBy){

    }

    /*  PURPOSE:    Move's the game object horizontally by the amount given
        INPUT:      moveBy          - The amount to move the game object by
        OUTPUT:     NONE
    */
    protected void moveHorizontal(int moveBy){

    }
}
