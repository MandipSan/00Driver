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
    //  PURPOSE:    Holds an array of the different weapons
    private Weapon [] myWeapons;
    //  PURPOSE:    Holds the current active weapon
    private WeaponTypes myWeapon;
    //  PURPOSE:    Holds the different types of weapons
    public enum WeaponTypes {MachineGun, Missile, Flamethrower, Laser}

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
        myWeapons = new Weapon[WeaponTypes.values().length];
        //myWeapons[WeaponTypes.MachineGun.ordinal()] = new Weapon(10,10,30,WeaponTypes.MachineGun, new );
        //myWeapons[WeaponTypes.Missile.ordinal()] = new Weapon(10,10,30,WeaponTypes.Missile, new );
        //myWeapons[WeaponTypes.Flamethrower.ordinal()] = new Weapon(10,10,30,WeaponTypes.Flamethrower, new );
        //myWeapons[WeaponTypes.Laser.ordinal()] = new Weapon(10,10,30,WeaponTypes.Laser, new );
    }

    /*  PURPOSE:    Updates the sprite's logic
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void update() {
        super.update();
        for (int i = 0; i < WeaponTypes.values().length; i++) {
            if (myWeapons[i].delayFire > 0) myWeapons[i].delayFire--;
        }
    }

    /*  PURPOSE:    Increase the current health by the amount given up to the max health
        INPUT:      increaseBy          - The amount the current health is to be increased by
        OUTPUT:     NONE
     */
    public void increaseHealth(int increaseBy) {
        if (myHealth + increaseBy <= myMaxHealth) myHealth += increaseBy;
    }

    /*  PURPOSE:    Decrease the current health by amount given up to zero
        INPUT:      decreaseBy          - The amount the current health is to be decreased by
        OUTPUT:     NONE
     */
    public void decreaseHealth(int decreaseBy) {
        if (myHealth - decreaseBy >= 0) myHealth -= decreaseBy;
    }

    /*  PURPOSE:    Increase the sprite’s max health by amount given
        INPUT:      amount              - The amount to set max health
        OUTPUT:     NONE
     */
    public void increaseMaxHealth(int amount) {
        myMaxHealth += amount;
    }

    /*  PURPOSE:    Increase the ammo amount for the give weapon type up to its max ammo
        INPUT:      weaponType          - The weapon type to increase the ammo for
                    increaseBy          - The amount to increase he ammo by
        OUTPUT:     NONE
     */
    public void increaseAmmo(WeaponTypes weaponType, int increaseBy) {
        if (myWeapons[weaponType.ordinal()].ammo +
                increaseBy <= myWeapons[weaponType.ordinal()].maxAmmo) {
            myWeapons[weaponType.ordinal()].ammo += increaseBy;
        }
    }

    /*  PURPOSE:    Increase the max ammo by amount given for the given weapon type
        INPUT:      weaponType          - The weapon type to increase the ammo for
                    amount              - The amount to set max ammo
        OUTPUT:     NONE
     */
    public void increaseMaxAmmo(WeaponTypes weaponType, int amount) {
        myWeapons[weaponType.ordinal()].maxAmmo += amount;
    }

    /*  PURPOSE:    Fires a projectile from the X and Y position given
        INPUT:      x                   - The X position to launch the projectile from
                    y                   - The Y position to launch the projectile from
        OUTPUT:     NONE
     */
    public void fire(float x, float y) {
        RectF temp = getDimensions();
        if (myWeapons[myWeapon.ordinal()].ammo > 1 && myWeapons[myWeapon.ordinal()].delayFire == 0){
            if(myWeapon == WeaponTypes.MachineGun) {
                myWeapons[myWeapon.ordinal()].myProjectile.launched(temp.left + 2, temp.top);
                myWeapons[myWeapon.ordinal()].myProjectile.launched(temp.left - 2, temp.top);
            }else{
                myWeapons[myWeapon.ordinal()].myProjectile.launched(temp.centerX(), temp.top);
            }
        }
    }

    /*  PURPOSE:    Returns the current health
        INPUT:      NONE
        OUTPUT:     Returns an int with the amount of the current health
     */
    public int getHealth() {
        return myHealth;
    }

    /*  PURPOSE:    Returns the max health
        INPUT:      NONE
        OUTPUT:     Returns an int with the amount of the max health
     */
    public int getMaxHealth() {
        return myMaxHealth;
    }

    /*  PURPOSE:    Returns the amount of ammo for the selected weapon type
        INPUT:      weaponType          - The weapon type of the ammo amount to return
        OUTPUT:     Returns an int of the ammo amount
     */
    public int getAmmo(WeaponTypes weaponType) {
        return myWeapons[myWeapon.ordinal()].ammo;
    }

    /*  PURPOSE:    Set's the weapon type of the current active weapon
        INPUT:      weaponType          - The weapon type to change too
        OUTPUT:     NONE
     */
    protected void setWeaponType(WeaponTypes weaponType) {
        myWeapon = weaponType;
    }

    /*  PURPOSE:    Returns the current weapon type
        INPUT:      NONE
        OUTPUT:     Returns an WeaponType var of the current weapon type
     */
    protected WeaponTypes getWeaponType() {
        return myWeapon;
    }

    //  Purpose:    Class hold information for the different weapons types
    private class Weapon {
        //  PURPOSE:    Holds the ammo amount
        public int ammo;
        //  PURPOSE:    Holds the max ammo amount
        public int maxAmmo;
        //  PURPOSE:    Holds the delay between the firing
        public int delayFire;
        //  PURPOSE:    Holds the weapon type
        public Sprite.WeaponTypes myType;
        //  PURPOSE:    Holds the projectile for the weapon
        public Projectile myProjectile;

        /*  PURPOSE:    Constructor for the weapon that sets the default values for the object
            INPUT:      newAmmo             - The new ammo amount to set
                        newMaxAmmo          - The new max ammo amount to set
                        newDelayFire        - The new delay amount for the weapon
                        newType             - The type of weapon it is
                        newProjectile       - The projectile for this type of weapon
            OUTPUT:     NONE
         */
        public Weapon(int newAmmo, int newMaxAmmo, int newDelayFire, Sprite.WeaponTypes newType,
                      Projectile newProjectile) {
            ammo = newAmmo;
            maxAmmo = newMaxAmmo;
            delayFire = newDelayFire;
            myType = newType;
            myProjectile = newProjectile;
        }
    }
}