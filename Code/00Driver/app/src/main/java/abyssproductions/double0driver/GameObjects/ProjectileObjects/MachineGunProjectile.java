package abyssproductions.double0driver.GameObjects.ProjectileObjects;

import android.graphics.Bitmap;
import android.graphics.RectF;

import abyssproductions.double0driver.GameGlobals;
import abyssproductions.double0driver.GameObjects.Projectile;
import abyssproductions.double0driver.R;

/**
 * Created by Mark Reffel on 2/8/17.
 * Lasted Edited by Mandip Sangha on 2/26/17
 */

public class MachineGunProjectile extends Projectile {


    /*  PURPOSE:    Constructor for the Flame Thrower Projectile is to set the default values for the object
        INPUT:      image               - The image of the object
                    imageWidth          - The width of a single image in the image sheet
                    imageHeight         - The height of a single image in the image sheet
        OUTPUT:     NONE
        */
    private MachineGunProjectile(Bitmap image, int imageWidth, int imageHeight) {
        super(image, imageWidth, imageHeight);
    }


    /*  PURPOSE:    Default constructor creates default projector using above constructor
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public MachineGunProjectile() {
        this(null, 50, 50);
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
