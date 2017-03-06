package abyssproductions.double0driver.GameObjects.ProjectileObjects;

import android.graphics.Bitmap;
import android.graphics.Rect;

import abyssproductions.double0driver.GameGlobals;
import abyssproductions.double0driver.GameObjects.Projectile;
import abyssproductions.double0driver.R;

/**
 * Created by Mark Reffel on 2/8/17.
 * Lasted Edited by Mandip Sangha on 2/26/17
 */

public class MachineGunProjectile extends Projectile {

    /*  PURPOSE:    Constructor for the Machine Gun Projectile is to set the default values for the
                        object
        INPUT:      image               - The image of the object
                    imageWidth          - The width of a single image in the image sheet
                    imageHeight         - The height of a single image in the image sheet
        OUTPUT:     NONE
        */
    private MachineGunProjectile(Bitmap image, int imageWidth, int imageHeight) {
        super(image, imageWidth, imageHeight, 2, 1);
        //TODO:Damage amount to be checked
        myDamage = 10;
    }

    /*  PURPOSE:    Constructor for the projectile that sets the default values to null and 0
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public MachineGunProjectile() {
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
        MachineGunProjectile p = new
                MachineGunProjectile(GameGlobals.getInstance().getImages().getMachineGunProImage(),
                GameGlobals.getInstance().getImageResources().
                        getInteger(R.integer.MachineGunProImageWidth),
                GameGlobals.getInstance().getImageResources().
                        getInteger(R.integer.MachineGunProImageHeight));
        p.setMyCollisionBounds(new Rect(0,0,10,10));
        p.resetWidthAndHeight(10,10);
        p.setDamage(myDamage);
        p.myVelocity.set(0,direction*GameGlobals.getInstance().getImageResources().
                getInteger(R.integer.MGProYVelocity));
        super.launch(x, y, direction, p);
    }

    /*  PURPOSE:    Sets projectiles damage amount to the amount for the new level
        INPUT:      newLevelDamage           - The new level that the damage is at
        OUTPUT:     NONE
     */
    @Override
    public void setDamageLevel(int newDamageLevel){
        //TODO:Need to change value from hard coded
        myDamage = 10*newDamageLevel;
    }

}
