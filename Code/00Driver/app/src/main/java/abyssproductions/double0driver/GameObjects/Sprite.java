package abyssproductions.double0driver.GameObjects;

import android.graphics.RectF;

/**
 * Created by Mandip Sangha on 1/31/2017.
 * Edited by Mark Reffel on 2/9/2017
 */

public class Sprite extends GameObject {
    //  PURPOSE:    Holds the sprite's current health
    private int myHealth;
    //  PURPOSE:    Holds the sprite's max health
    private int myMaxHealth;
    //  PURPOSE:    Holds the ammo amount for the different type of weapons
    private int [] myAmmo;
    //  PURPOSE:    Holds the max ammo amount for the different type of weapons
    private int [] myMaxAmmo;
    //  PURPOSE:    Holds the delay between the firing for the different type of weapons
    private int [] myFireDelay;
    //  PURPOSE:    Holds the current active weapon
    private WeaponTypes myWeapon;
    //  PURPOSE:    Holds the different types of weapons
    public enum WeaponTypes{MachineGun, Missile, Flamethrower, Laser}

    /*  PURPOSE:    Constructor for the sprite that sets the default values for the object
        INPUT:      imageReference      - Reference's the image to be load
                    imageWidth          - The width of a single image in the image sheet
                    imageHeight         - The height of a single image in the image sheet
        OUTPUT:     NONE
     */

    public Sprite(int imageReference, int width, int height) {
        super(imageReference, width, height);
        myHealth = 100;
        myMaxHealth = 100;
        myWeapon = WeaponTypes.MachineGun;
        myAmmo = new int[WeaponTypes.values().length];
        myMaxAmmo = new int[WeaponTypes.values().length];
        myFireDelay = new int[WeaponTypes.values().length];
        for (int i = 0; i < WeaponTypes.values().length; i++) {
            myAmmo[i] = 10;
            myMaxAmmo[i] = 10;
            myFireDelay[i] = 0;
        }
    }

    /*  PURPOSE:    Updates the sprite's logic
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void update(){
        super.update();
        for(int i = 0; i < WeaponTypes.values().length; i++){
            if(myFireDelay[i] > 0)myFireDelay[i]--;
        }
    }

    /*  PURPOSE:    Increase the current health by the amount given up to the max health
        INPUT:      increaseBy          - The amount the current health is to be increased by
        OUTPUT:     NONE
     */
    public void increaseHealth(int increaseBy){
        if(myHealth + increaseBy <= myMaxHealth) myHealth += increaseBy;
    }

    /*  PURPOSE:    Decrease the current health by amount given up to zero
        INPUT:      decreaseBy          - The amount the current health is to be decreased by
        OUTPUT:     NONE
     */
    public void decreaseHealth(int decreaseBy){
        if(myHealth - decreaseBy >= 0) myHealth -= decreaseBy;
    }

    /*  PURPOSE:    Increase the sprite’s max health by amount given
        INPUT:      amount              - The amount to set max health
        OUTPUT:     NONE
     */
    public void increaseMaxHealth(int amount){
        myMaxHealth += amount;
    }

    /*  PURPOSE:    Increase the ammo amount for the give weapon type up to its max ammo
        INPUT:      weaponType          - The weapon type to increase the ammo for
                    increaseBy          - The amount to increase he ammo by
        OUTPUT:     NONE
     */
    public void increaseAmmo(WeaponTypes weaponType, int increaseBy){
        int i = 0;
        for (WeaponTypes Type : WeaponTypes.values()){
            if(Type == weaponType){
                if(myAmmo[i] + increaseBy <= myMaxAmmo[i]){
                    myAmmo[i] += increaseBy;
                    break;
                }
            }
            i++;
        }
    }

    /*  PURPOSE:    Increase the max ammo by amount given for the given weapon type
        INPUT:      weaponType          - The weapon type to increase the ammo for
                    amount              - The amount to set max ammo
        OUTPUT:     NONE
     */
    public void increaseMaxAmmo(WeaponTypes weaponType, int amount){
        int i = 0;
        for (WeaponTypes Type : WeaponTypes.values()){
            if(Type == weaponType){
                myMaxAmmo[i] += amount;
                break;
            }
            i++;
        }
    }

    /*  PURPOSE:    Fires a projectile from the X and Y position given
        INPUT:      x                   - The X position to launch the projectile from
                    y                   - The Y position to launch the projectile from
        OUTPUT:     NONE
     */
    public void fire(float x, float y){
        RectF temp = getDimensions();
        switch (myWeapon){
            case MachineGun:
                if(myAmmo[0]>1 && myFireDelay[0] == 0){
                    //launched(temp.left+2,temp.top);
                    //launched(temp.right-2,temp.top);
                    //myFireDelay[0] =
                }
                break;
            case Missile:
                if(myAmmo[1]>0 && myFireDelay[1] == 0){
                    //launched(temp.centerX(),temp.centerY());
                    //myFireDelay[1] =
                }
                break;
            case Flamethrower:
                if(myAmmo[2]>0 && myFireDelay[2] == 0){
                    //launched(temp.centerX(),temp.top);
                    //myFireDelay[2] =
                }
                break;
            case Laser:
                if(myAmmo[3]>0 && myFireDelay[3] == 0){
                    //launched(temp.centerX(),temp.centerY());
                    //myFireDelay[3] =
                }
                break;
        }
    }

    /*  PURPOSE:    Returns the current health
        INPUT:      NONE
        OUTPUT:     Returns an int with the amount of the current health
     */
    public int getHealth(){
        return myHealth;
    }

    /*  PURPOSE:    Returns the max health
        INPUT:      NONE
        OUTPUT:     Returns an int with the amount of the max health
     */
    public int getMaxHealth(){
        return myMaxHealth;
    }

    /*  PURPOSE:    Returns the amount of ammo for the selected weapon type
        INPUT:      weaponType          - The weapon type of the ammo amount to return
        OUTPUT:     Returns an int of the ammo amount
     */
    public int getAmmo(WeaponTypes weaponType){
        int i = 0;
        for (WeaponTypes Type : WeaponTypes.values()){
            if(Type == weaponType) return myMaxAmmo[i];
            i++;
        }
        return 0;
    }

    /*  PURPOSE:    Set's the weapon type of the current active weapon
        INPUT:      weaponType          - The weapon type to change too
        OUTPUT:     NONE
     */
    protected void setWeaponType(WeaponTypes weaponType){
        myWeapon = weaponType;
    }

    /*  PURPOSE:    Returns the current weapon type
        INPUT:      NONE
        OUTPUT:     Returns an WeaponType var of the current weapon type
     */
    protected WeaponTypes getWeaponType(){
        return myWeapon;
    }
}
