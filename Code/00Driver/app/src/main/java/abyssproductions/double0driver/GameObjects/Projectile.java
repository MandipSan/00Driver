package abyssproductions.double0driver.GameObjects;

import android.graphics.RectF;

import abyssproductions.double0driver.GameGlobals;
import abyssproductions.double0driver.GameObjects.ProjectileObjects.MachineGunProjectile;
import abyssproductions.double0driver.R;

/**
 * Created by Mandip Sangha on 2/1/2017.
 * Edited by Mark Reffel on 2/9/2017
 */

public class Projectile extends GameObject {
    /*  PURPOSE:    Constructor for the projectile that sets the default values for the object
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public Projectile(int imageReference, int width, int height){
        super(imageReference, width, height);
    }

    public Projectile() {
        this(R.drawable.test, 50, 50);
    }

    /*  PURPOSE:    Updates the projectile's logic
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void update(){
        super.update();
        RectF tempDim = getDimensions();
        moveVertical(myVelocity.y);
        if(tempDim.bottom < 0 || tempDim.top > 555/*NEED TO CHANGE VAR*/)myVelocity.set(0,0);
    }

    /*  PURPOSE:    Launches the projectile from the X and Y position given 
        INPUT:      x                   - The X position to launch the projectile from
                    y                   - The Y position to launch the projectile from
                    direction           - The direction the projectile will travel (-1 for down, 1
                                            for up)
        OUTPUT:     NONE
     */
    public void launch(float x, float y,int direction, Projectile p){
        RectF temp = getDimensions();
        temp.offsetTo(x, y);
        p.setMyDimensions(temp);
        //Find first empty spot in projectiles array
        for(int i = 0; i < GameGlobals.getInstance().myProjectiles.length; i++) {
            if(GameGlobals.getInstance().myProjectiles[i] == null) {
                GameGlobals.getInstance().myProjectiles[i] = p;
                break;
            }
        }
    }

    /*  PURPOSE:    Returns if the projectile is active
        INPUT:      NONE
        OUTPUT:     Returns boolean of whether active if yes true; if not false
     */
    public boolean getActive(){
        return !myVelocity.equals(0,0);
    }
}
