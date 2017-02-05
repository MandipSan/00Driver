package abyssproductions.double0driver.GameObjects;

/**
 * Created by Mandip Sangha on 1/31/2017.
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
    //  PURPOSE:    Holds the current active weapon
    private WeaponTypes myWeapon;
    //  PURPOSE:    Holds the different types of weapons
    public enum WeaponTypes{MachineGun, Missile, Flamethrower, Laser}

    /*  PURPOSE:    Constructor for the sprite that sets the default values for the object
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public Sprite(){
        myHealth = 100;
        myMaxHealth = 100;
        myWeapon = WeaponTypes.MachineGun;
        myAmmo = new int[WeaponTypes.values().length];
        myMaxAmmo = new int[WeaponTypes.values().length];
        for(int i = 0; i < WeaponTypes.values().length; i++){
            myAmmo[i] = 10;
            myMaxAmmo[i] = 10;
        }
    }

    /*  PURPOSE:    Updates the sprite's logic
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void update(){

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
