package abyssproductions.double0driver.GameObjects.ProjectileObjects;


import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.Random;

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
        //TODO:Damage amount to be checked
        myDamage = 10;
    }

    /*  PURPOSE:    Constructor for the projectile that sets the default values to null and 0
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public FlameThrowerProjectile() {
        this(null, 0, 0);
    }

    /*  PURPOSE:    Updates the projectile's logic
        INPUT:      NONE
        OUTPUT:     NONE
     */
    @Override
    public void update(){
        super.update();
        moveHorizontal(myVelocity.x);
        lifeCount--;
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
        Random random = new Random();
        FlameThrowerProjectile p = new
                FlameThrowerProjectile(GameGlobals.getInstance().getImages().getFlameProImage(),
                GameGlobals.getInstance().getImageResources().
                        getInteger(R.integer.FlameProImageWidth),
                GameGlobals.getInstance().getImageResources().
                        getInteger(R.integer.FlameProImageHeight));
        p.setMyCollisionBounds(new Rect(0,0,10,17));
        p.resetWidthAndHeight(10,17);
        p.setDamage(myDamage);
        int temp = random.nextInt(21)-10;
        p.myVelocity.set(temp,direction*GameGlobals.getInstance().getImageResources().
                getInteger(R.integer.FProYVelocity));
        //TODO:Change from Hardcoded value
        p.lifeCount = random.nextInt(10)+1;
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
