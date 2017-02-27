package abyssproductions.double0driver.GameObjects.ProjectileObjects;


import android.graphics.Bitmap;

import abyssproductions.double0driver.GameGlobals;
import abyssproductions.double0driver.GameObjects.Projectile;
import abyssproductions.double0driver.R;

import static android.R.attr.direction;
import static android.R.attr.x;
import static android.R.attr.y;

/**
 * Created by Mark Reffel on 2/8/17.
 * Lasted Edited by Mandip Sangha on 2/26/17
 */

public class FlameThrowerProjectile extends Projectile {

    /*  PURPOSE:    Constructor for the Flame Thrower Projectile is to set the default values for the object
        INPUT:      image               - The image of the object
                    imageWidth          - The width of a single image in the image sheet
                    imageHeight         - The height of a single image in the image sheet
        OUTPUT:     NONE
        */

    private FlameThrowerProjectile (Bitmap image, int imageWidth, int imageHeight) {
        super(image, imageWidth, imageHeight);
    }

    /*  PURPOSE:    Constructor for the projectile that sets the default values to null and 0
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public FlameThrowerProjectile() {
        this(null, 0, 0);
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
        FlameThrowerProjectile p = new
                FlameThrowerProjectile(GameGlobals.getInstance().getImages().getFlameProImage(),0,0);
        p.myVelocity.set(0,direction*25);
        super.launch(x, y, direction, p);
    }

}
