package abyssproductions.double0driver.GameObjects;

import android.graphics.RectF;

import abyssproductions.double0driver.GameGlobals;

/**
 * Created by Mandip Sangha on 2/1/2017.
 */

public class Projectile extends GameObject {
    /*  PURPOSE:    Constructor for the projectile that sets the default values for the object
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public Projectile(){

    }

    /*  PURPOSE:    Updates the projectile's logic
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void update(){
        RectF tempDim = getDimensions();
        moveVertical(myVelocity.y);
        if(tempDim.bottom < 0 || tempDim.top > 555/*NEED TO CHANGE VAR*/)myVelocity.set(0,0);
    }

    /*  PURPOSE:    Launches the projectile from the X and Y position given 
        INPUT:      x                   - The X position to launch the projectile from
                    y                   - The Y position to launch the projectile from
                    direction           - The direction the projectile will travel (0 for down, 1
                                            for up)
        OUTPUT:     NONE
     */
    public void launched(float x, float y,int direction){
        RectF temp = getDimensions();
        temp.offsetTo(x,y);
        setMyDimensions(temp);
        if(direction == 0)myVelocity.set(0,1);
        else myVelocity.set(0,-1);
    }
}
