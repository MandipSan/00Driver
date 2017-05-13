package abyssproductions.double0driver.GameObjects.ProjectileObjects;

import android.graphics.Bitmap;
import android.graphics.Rect;

import abyssproductions.double0driver.GameGlobals;
import abyssproductions.double0driver.GameObjects.Projectile;
import abyssproductions.double0driver.R;

/**
 * Created by Mandip Sangha on 3/6/2017.
 */

public class SpikeProjectile extends Projectile {

    /*  PURPOSE:    Constructor for the Flame Thrower Projectile is to set the default values for the object
        INPUT:      image               - The image of the object
                    imageWidth          - The width of a single image in the image sheet
                    imageHeight         - The height of a single image in the image sheet
        OUTPUT:     NONE
        */
    private SpikeProjectile(Bitmap image, int imageWidth, int imageHeight) {
        super(image, imageWidth, imageHeight, 2, 1);
        myDamage = GameGlobals.getInstance().getImageResources().
                getInteger(R.integer.SProDefaultDamage);
    }

    /*  PURPOSE:    Constructor for the projectile that sets the default values to null and 0
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public SpikeProjectile() {
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
        SpikeProjectile p = new
                SpikeProjectile(GameGlobals.getInstance().getImages().getSpikeProImage(),
                GameGlobals.getInstance().getImageResources().
                        getInteger(R.integer.SpikeProImageWidth),
                GameGlobals.getInstance().getImageResources().
                        getInteger(R.integer.SpikeProImageHeight));
        //TODO:Need remove hard coded values
        p.setMyCollisionBounds(new Rect(0,0,102,25));
        p.resetWidthAndHeight(102,25);
        p.setDamage(myDamage);
        p.myVelocity.set(0,GameGlobals.getInstance().getImageResources().
                getInteger(R.integer.EnemyYVelocity));
        GameGlobals.getInstance().mySoundEffects.playSoundEffect(GameGlobals.getInstance().
                getImageResources().getInteger(R.integer.SESpikeStripID),0);
        super.launch(x, y, direction, p);
    }

    /*  PURPOSE:    Sets projectiles damage amount to the amount for the new level
        INPUT:      newLevelDamage           - The new level that the damage is at
        OUTPUT:     NONE
     */
    @Override
    public void setDamageLevel(int newDamageLevel){
        myDamage = GameGlobals.getInstance().getImageResources().
                getInteger(R.integer.SProDefaultDamage)*newDamageLevel;
    }
}
