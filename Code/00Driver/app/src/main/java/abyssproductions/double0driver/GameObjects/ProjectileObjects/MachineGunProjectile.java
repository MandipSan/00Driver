package abyssproductions.double0driver.GameObjects.ProjectileObjects;

import android.graphics.RectF;

import abyssproductions.double0driver.GameGlobals;
import abyssproductions.double0driver.GameObjects.Projectile;
import abyssproductions.double0driver.R;

/**
 * Created by Mark Reffel on 2/8/17.
 * Edited by Mark Reffel on 2/9/2017
 */

public class MachineGunProjectile extends Projectile {


    /*  PURPOSE:    Constructor for the Flame Thrower Projectile is to set the default values for the object
        INPUT:      imageReference      - The image reference for the projectile
                    width               - The width of the projectile
                    height              - The height of the projectile
        OUTPUT:     NONE
        */
    private MachineGunProjectile(int ImageReference, int width, int height) {
        super(ImageReference, width, height);
    }


    /*  PURPOSE:    Default constructor creates default projector using above constructor
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public MachineGunProjectile() {
        this(R.drawable.test, 50, 50);
    }



    /*  PURPOSE:    Launches the projectile from the X and Y position given
    INPUT:      x                   - The X position to launch the projectile from
                y                   - The Y position to launch the projectile from
                direction           - The direction the projectile will travel (-1 for down, 1
                                        for up)
    OUTPUT:     NONE
    */
    @Override
    public void launch(float x, float y, int direction) {
        MachineGunProjectile p = new MachineGunProjectile();
        p.myVelocity.set(0,direction*20);
        super.launch(x, y, direction, p);
    }

}
