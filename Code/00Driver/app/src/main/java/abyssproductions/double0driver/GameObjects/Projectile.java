package abyssproductions.double0driver.GameObjects;

import android.graphics.BitmapFactory;
import android.graphics.RectF;

import abyssproductions.double0driver.GameGlobals;
import abyssproductions.double0driver.R;

/**
 * Created by Mandip Sangha on 2/1/2017.
 */

public class Projectile extends GameObject {
    /*  PURPOSE:    Constructor for the projectile that sets the default values for the object
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public Projectile(){
        setMyDimensions(new RectF(0, 0, 50, 50));
        setMyImage(BitmapFactory.decodeResource(GameGlobals.getInstance().getImageResources(),
                R.drawable.test));
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

    /*  PURPOSE:    Returns if the projectile is active
        INPUT:      NONE
        OUTPUT:     Returns boolean of whether active if yes true; if not false
     */
    public boolean getActive(){
        return !myVelocity.equals(0,0);
    }
}
