package abyssproductions.double0driver.GameObjects;

import android.graphics.Bitmap;
import android.graphics.RectF;
import abyssproductions.double0driver.GameGlobals;
import abyssproductions.double0driver.GameObjects.ProjectileObjects.MachineGunProjectile;
import abyssproductions.double0driver.R;

/**
 * Created by Mandip Sangha on 2/1/2017.
 * Lasted Edited by Mandip Sangha on 2/26/17
 */

public class Projectile extends GameObject {
    //  PURPOSE:    Holds the projectiles damage amount
    protected int myDamage;
    //  PURPOSE:    Holds the amount of time that the flame is a live
    protected int lifeCount;

    /*  PURPOSE:    Constructor for the projectile that sets the default values for the object
        INPUT:      image               - The image of the object
                    imageWidth          - The width of a single image in the image sheet
                    imageHeight         - The height of a single image in the image sheet
                    imageSheetRow       - The number of rows in the image sheet
                    imageSheetColumn    - The number of columns in the image sheet
        OUTPUT:     NONE
     */
    public Projectile(Bitmap image, int imageWidth, int imageHeight, int imageSheetRow,
                      int imageSheetColumn){
        super(image, imageWidth, imageHeight, imageSheetRow, imageSheetColumn);
        //Keep set to 1 for projectiles that don't use the lifeCount so as to not be unintentionally
        //  removed
        lifeCount = 1;
    }

    /*  PURPOSE:    Constructor for the projectile that sets the default values for the object
        INPUT:      image               - The image of the object
                    imageWidth          - The width of a single image in the image sheet
                    imageHeight         - The height of a single image in the image sheet
        OUTPUT:     NONE
     */
    public Projectile(Bitmap image, int imageWidth, int imageHeight){
        super(image, imageWidth, imageHeight);
        //Keep set to 1 for projectiles that don't use the lifeCount so as to not be unintentionally
        //  removed
        lifeCount = 1;
    }

    /*  PURPOSE:    Constructor for the projectile that sets the default values to null and 0
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public Projectile() {
        this(null, 0, 0);
    }

    /*  PURPOSE:    Updates the projectile's logic
        INPUT:      NONE
        OUTPUT:     NONE
     */
    @Override
    public void update(){
        super.update();
        moveVertical(myVelocity.y);
    }

    /*  PURPOSE:    Sets projectiles damage amount to the amount for the new level(To be overridden
                        be subclass)
        INPUT:      newDamageLevel           - The new level that the damage is at
        OUTPUT:     NONE
     */
    public void setDamageLevel(int newDamageLevel){
    }

    /*  PURPOSE:    Return's the amount of damage
        INPUT:      NONE
        OUTPUT:     Returns an int with the amount of damage
     */
    public int getMyDamage(){
        return myDamage;
    }

    /*  PURPOSE:    Return's if the life count is greater than zero
        INPUT:      NONE
        OUTPUT:     Returns boolean of the result
     */
    public boolean getLife(){
        return (lifeCount > 0);
    }

    /*  PURPOSE:    Sets projectiles damage amount
        INPUT:      newDamage           - The new amount that the damage is at
        OUTPUT:     NONE
     */
    protected void setDamage(int newDamage){
        myDamage = newDamage;
    }

    /*  PURPOSE:    Launches the projectile from the X and Y position given 
        INPUT:      x                   - The X position to launch the projectile from
                    y                   - The Y position to launch the projectile from
                    direction           - The direction the projectile will travel (-1 for down, 1
                                            for up)
        OUTPUT:     NONE
     */
    protected void launch(float x, float y,int direction, Projectile p){
        RectF temp = p.getDimensions();
        //TODO:Mark look over this as to it keeps the projectiles from causing a collision with there
        //TODO: launch objects
        if(direction < 0)temp.offsetTo(x, y-temp.height());
        else{
            temp.offsetTo(x, y+temp.height());
            p.setImageFlip();
        }
        p.setMyDimensions(temp);
        //Find first empty spot in projectiles array
        for(int i = 0; i < GameGlobals.getInstance().myProjectiles.length; i++) {
            if(GameGlobals.getInstance().myProjectiles[i] == null) {
                GameGlobals.getInstance().myProjectiles[i] = p;
                break;
            }
        }
    }

    /*  PURPOSE:    Method to be overridden by subclasses
        INPUT:      x                   - The X position to launch the projectile from
                    y                   - The Y position to launch the projectile from
                    direction           - The direction the projectile will travel (-1 for down, 1
                                            for up)
        OUTPUT: NONE
     */
    protected void launch(float x, float y, int direction) {}

}
