package abyssproductions.double0driver.GameObjects.ProjectileObjects;

import abyssproductions.double0driver.GameObjects.Projectile;
import abyssproductions.double0driver.R;

/**
 * Created by Mark Reffel on 2/8/17.
 * Edited by Mark Reffel on 2/9/2017
 */

public class FlameThrowerProjectile extends Projectile {

    /*  PURPOSE:    Constructor for the Flame Thrower Projectile is to set the default values for the object
        INPUT:      imageReference      - The image reference for the projectile
                    width               - The width of the projectile
                    height              - The height of the projectile
        OUTPUT:     NONE
        */
    private FlameThrowerProjectile (int ImageReference, int width, int height) {
        super(ImageReference, width, height);
    }

    /*  PURPOSE:    Default constructor creates default projector using above constructor
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public FlameThrowerProjectile () {
        this(R.drawable.flames, 50, 50);
    }
}
