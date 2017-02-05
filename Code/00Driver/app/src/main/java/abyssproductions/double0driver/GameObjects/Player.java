package abyssproductions.double0driver.GameObjects;

/**
 * Created by Mandip Sangha on 2/1/2017.
 */

public class Player extends Sprite {
    //  PURPOSE:    Holds the player's secondary weapon type
    private WeaponTypes mySecondaryWeapon;

    /*  PURPOSE:    Constructor for the player that sets the default values for the object
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public Player(){
        mySecondaryWeapon = WeaponTypes.MachineGun;
    }

    /*  PURPOSE:    Updates the player's logic
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void update(){

    }

    /*  PURPOSE:    Moves the player to the left
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void moveLeft(){

    }

    /*  PURPOSE:    Moves the player to the right
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void moveRight(){

    }

    /*  PURPOSE:    Fires the primary weapon
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void fireWeapon(){

    }

    /*  PURPOSE:    Switch the primary and secondary weapons
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void switchWeapon(){

    }

    /*  PURPOSE:    Increase the player’s max ammo capacity by amount given for the weapon type
                        given
        INPUT:      weaponType          - The weapon type to increase the max ammo for capacity
                    upgradeMaxAmmoBy    - THe amount to increase the max ammo capacity by
        OUTPUT:     NONE
     */
    public void upgradeAmmo(WeaponTypes weaponType, int upgradeMaxAmmoBy){

    }

    /*  PURPOSE:    Changes the weapon load out for the weapon position given primary or secondary
                        with the new weapon type given and returns whether the change was successful
        INPUT:      weaponPos           - Hold whether the primary or secondary weapon is to be
                                            changed (1 for primary and 2 for secondary)
                    newWeaponType       - The weapon type that is replacing the old weapon type
        OUTPUT:     Returns true if changed; else false
     */
    public boolean changeWeaponLoadOut(int weaponPos, WeaponTypes newWeaponType){
        return false;
    }
}
