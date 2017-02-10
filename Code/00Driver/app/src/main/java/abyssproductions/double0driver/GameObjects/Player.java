package abyssproductions.double0driver.GameObjects;

import android.graphics.RectF;

import abyssproductions.double0driver.GameGlobals;

/**
 * Created by Mandip Sangha on 2/1/2017.
 * Edited by Mark Reffel on 2/9/2017
 */

public class Player extends Sprite {
    //  PURPOSE:    Holds the player's secondary weapon type
    private WeaponTypes mySecondaryWeapon;
    //  PURPOSE:    Holds the player's count for when the player is done transitioning between lanes
    private int velocityReset;

    /*  PURPOSE:    Constructor for the player that sets the default values for the object
        INPUT:      imageReference      - Reference's the image to be load
                    imageWidth          - The width of a single image in the image sheet
                    imageHeight         - The height of a single image in the image sheet
        OUTPUT:     NONE
     */
    public Player(int imageReference, int width, int height){
        super(imageReference, width, height);
        mySecondaryWeapon = WeaponTypes.MachineGun;
        velocityReset = 0;
    }

    /*  PURPOSE:    Updates the player's logic
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void update(){
        super.update();
        if(velocityReset > 0)moveHorizontal(myVelocity.x);
        if(velocityReset < 0)myVelocity.set(0, 0);
        velocityReset-=Math.abs(myVelocity.x);
    }

    /*  PURPOSE:    Moves the player to the left
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void moveLeft(){
        myVelocity.set(-1*GameGlobals.playerHorizontalVel,0);
        velocityReset = GameGlobals.playerVelocityReset - velocityReset;
    }

    /*  PURPOSE:    Moves the player to the right
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void moveRight(){
        myVelocity.set(GameGlobals.playerHorizontalVel,0);
        velocityReset = GameGlobals.playerVelocityReset - velocityReset;
    }

    /*  PURPOSE:    Fires the primary weapon
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void fireWeapon(){
        RectF tempPosition = getDimensions();
        fire(tempPosition.centerX(), tempPosition.top);
    }

    /*  PURPOSE:    Switch the primary and secondary weapons
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void switchWeapon(){
        WeaponTypes temp = getWeaponType();
        setWeaponType(mySecondaryWeapon);
        mySecondaryWeapon = temp;
    }

    /*  PURPOSE:    Increase the player’s max ammo capacity by amount given for the weapon type
                        given
        INPUT:      weaponType          - The weapon type to increase the max ammo for capacity
                    upgradeMaxAmmoBy    - THe amount to increase the max ammo capacity by
        OUTPUT:     NONE
     */
    public void upgradeAmmo(WeaponTypes weaponType, int upgradeMaxAmmoBy){
        increaseMaxAmmo(weaponType,upgradeMaxAmmoBy);
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
}
