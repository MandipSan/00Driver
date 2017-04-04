package abyssproductions.double0driver.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import abyssproductions.double0driver.GameGlobals;
import abyssproductions.double0driver.R;

/**
 * Created by Mandip Sangha on 2/1/2017.
 * Lasted Edited by Mandip Sangha on 2/26/17
 */

public class Player extends Sprite {
    //  PURPOSE:    Holds the player's secondary weapon type
    private WeaponTypes mySecondaryWeapon;
    //  PURPOSE:    Holds the player's count for when the player is done transitioning between lanes
    private int velocityReset;
    //  PURPOSE:    Holds the player's max count for when the player is done transitioning between
    //                  lanes
    private int velocityResetMax;
    //  PURPOSE:    Holds the active weapon's image
    private Bitmap weaponImage;
    //  PURPOSE:    Holds the active weapon's image height and width
    private int weaponImageHeight, weaponImageWidth;

    /*  PURPOSE:    Constructor for the player that sets the default values for the object
        INPUT:      image               - The image of the object
                    imageWidth          - The width of a single image in the image sheet
                    imageHeight         - The height of a single image in the image sheet
        OUTPUT:     NONE
     */
    public Player(Bitmap image, int imageWidth, int imageHeight){
        super(image, imageWidth, imageHeight);
        mySecondaryWeapon = WeaponTypes.MachineGun;
        velocityReset = 0;
        velocityResetMax = 10;
        weaponImage = GameGlobals.getInstance().getImages().getMachineGunImage();
        weaponImageWidth = GameGlobals.getInstance().getImageResources().
                getInteger(R.integer.VehicleImageWidth);
        weaponImageHeight = GameGlobals.getInstance().getImageResources().
                getInteger(R.integer.MachineGunImageHeight);
    }

    /*  PURPOSE:    Updates the player's logic
        INPUT:      NONE
        OUTPUT:     NONE
     */
    @Override
    public void update(){
        super.update();
        if(velocityReset > 0)moveHorizontal(myVelocity.x);
        if(velocityReset <= 0)myVelocity.set(0, 0);
        velocityReset-=Math.abs(myVelocity.x);
    }

    /*  PURPOSE:    Draws the player and it's weapon
        INPUT:      NONE
        OUTPUT:     NONE
     */
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawBitmap(weaponImage,new Rect(0,0,weaponImageWidth,weaponImageHeight),
                new RectF(getDimensions().left, getDimensions().top,getDimensions().right,
                        getDimensions().top+weaponImageHeight),new Paint());
    }

    /*  PURPOSE:    Resets the player back to the default values
        INPUT:      NONE
        OUTPUT:     NONE
     */
    @Override
    public void reset(){
        super.reset();
        setWeaponType(WeaponTypes.MachineGun);
        mySecondaryWeapon = WeaponTypes.MachineGun;
    }

    /*  PURPOSE:    Moves the player to the left
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void moveLeft(){
        //Used to adjust remainder in the lanes size
        RectF temp = getDimensions();
        int value = (velocityResetMax% GameGlobals.getInstance().getImageResources().
                getInteger(R.integer.PlayerHorVelocity));
        temp.offset(-value,0);
        setMyDimensions(temp);

        myVelocity.set(-1*GameGlobals.getInstance().getImageResources().
                getInteger(R.integer.PlayerHorVelocity),0);
        velocityReset = (velocityResetMax-value) - velocityReset;
    }

    /*  PURPOSE:    Moves the player to the right
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void moveRight(){
        //Used to adjust remainder in the lanes size
        RectF temp = getDimensions();
        int value = (velocityResetMax% GameGlobals.getInstance().getImageResources().
                getInteger(R.integer.PlayerHorVelocity));
        temp.offset(value,0);
        setMyDimensions(temp);

        myVelocity.set(GameGlobals.getInstance().getImageResources().
                getInteger(R.integer.PlayerHorVelocity),0);
        velocityReset = (velocityResetMax-value) - velocityReset;
    }

    /*  PURPOSE:    Fires the primary weapon
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void fireWeapon(){
        RectF tempPosition = getDimensions();
        fire(tempPosition.centerX(), tempPosition.top,-1);
    }

    /*  PURPOSE:    Switch the primary and secondary weapons
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void switchWeapon(){
        WeaponTypes temp = getWeaponType();
        setWeaponType(mySecondaryWeapon);
        mySecondaryWeapon = temp;
        switch (getWeaponType()){
            case MachineGun:
                weaponImage = GameGlobals.getInstance().getImages().getMachineGunImage();
                weaponImageHeight = GameGlobals.getInstance().getImageResources().
                        getInteger(R.integer.MachineGunImageHeight);
                break;
            case Missile:
                weaponImage = GameGlobals.getInstance().getImages().getMissileLauncherImage();
                weaponImageHeight = GameGlobals.getInstance().getImageResources().
                        getInteger(R.integer.MissileLauncherImageHeight);
                break;
            case Laser:
                weaponImage = GameGlobals.getInstance().getImages().getLaserCannonImage();
                weaponImageHeight = GameGlobals.getInstance().getImageResources().
                        getInteger(R.integer.LaserCannonImageHeight);
                break;
            case Flamethrower:
                weaponImage = GameGlobals.getInstance().getImages().getFlameThrowerImage();
                weaponImageHeight = GameGlobals.getInstance().getImageResources().
                        getInteger(R.integer.FlameThrowerImageHeight);
                break;
        }
    }

    /*  PURPOSE:    Increase the player’s max ammo capacity by amount given for the weapon type
                        given
        INPUT:      weaponType          - The weapon type to increase the max ammo for capacity
                    upgradeMaxAmmoBy    - The amount to increase the max ammo capacity by
        OUTPUT:     NONE
     */
    public void upgradeAmmo(WeaponTypes weaponType, int upgradeMaxAmmoBy){
        increaseMaxAmmo(weaponType,upgradeMaxAmmoBy);
    }

    /*  PURPOSE:    Calculates the max velocity reset need for lane transition
        INPUT:      laneSize            - The size of the lanes
        OUTPUT:     NONE
     */
    public void setLaneTransitionMax(int laneSize){
        velocityResetMax = laneSize;
    }

    /*  PURPOSE:    Revives the player once the destroy animation end and return if it was successful
        INPUT:      NONE
        OUTPUT:     Returns true or false depending on whether it revived or not
     */
    public boolean revivePlayer(){
        if(getDestroyedFinish()){
            changeAniState(GameGlobals.getInstance().getImageResources().
                    getInteger(R.integer.NormalAnimateState));
            increaseHealth(getMaxHealth());
            return true;
        }
        return false;
    }

    /*  PURPOSE:    Changes the weapon load out for the weapon position given primary or secondary
                        with the new weapon type given and returns whether the change was successful
        INPUT:      weaponPos           - Hold whether the primary or secondary weapon is to be
                                            changed (1 for primary and 2 for secondary)
                    newWeaponType       - The weapon type that is replacing the old weapon type
        OUTPUT:     Returns true if changed; else false
     */
    public boolean changeWeaponLoadOut(int weaponPos, WeaponTypes newWeaponType){
        if(weaponPos == 1){
            setWeaponType(newWeaponType);
            return true;
        }else if(weaponPos == 2){
            mySecondaryWeapon = newWeaponType;
            return true;
        }
        return false;
    }

    /*  PURPOSE:    Return's the player's primary weapon type
        INPUT:      NONE
        OUTPUT:     Return the primary weapon type as WeaponTypes
     */
    public WeaponTypes getMyPrimaryWeapon(){
        return getWeaponType();
    }

    /*  PURPOSE:    Return's the player's secondary weapon type
        INPUT:      NONE
        OUTPUT:     Return the secondary weapon type as WeaponTypes
     */
    public WeaponTypes getMySecondaryWeapon(){
        return mySecondaryWeapon;
    }
}
