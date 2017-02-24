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
        moveVertical(myVelocity.y);
    }

    /*  PURPOSE:    Launches the projectile from the X and Y position given 
        INPUT:      x                   - The X position to launch the projectile from
                    y                   - The Y position to launch the projectile from
                    direction           - The direction the projectile will travel (-1 for down, 1
                                            for up)
        OUTPUT:     NONE
     */
    protected void launch(float x, float y,int direction, Projectile p){
        RectF temp = getDimensions();
        //TODO:Mark look over this as to it keeps the projectiles from cuasing a collision with there
        //TODO: launch objects
        if(direction < 0) temp.offsetTo(x, y-temp.height());
        else temp.offsetTo(x, y+temp.height());
        p.setMyDimensions(temp);
        //Find first empty spot in projectiles array
        for(int i = 0; i < GameGlobals.getInstance().myProjectiles.length; i++) {
            if(GameGlobals.getInstance().myProjectiles[i] == null) {
                GameGlobals.getInstance().myProjectiles[i] = p;
                break;
            }
        }
    }
    /*  PURPOSE: Method to be overriden by subclasses
        INPUT:  float x
                float y
                int direction
        OUTPUT: NONE
     */
    protected void launch(float x, float y, int direction) {}

    /*  PURPOSE:    Returns if the projectile is active
        INPUT:      NONE
        OUTPUT:     Returns boolean of whether active if yes true; if not false
     */
    public boolean getActive(){
        return !myVelocity.equals(0,0);
    }
}
