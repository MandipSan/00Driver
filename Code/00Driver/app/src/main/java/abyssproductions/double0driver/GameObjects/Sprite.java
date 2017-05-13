package abyssproductions.double0driver.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import abyssproductions.double0driver.GameGlobals;
import abyssproductions.double0driver.GameObjects.ProjectileObjects.FlameThrowerProjectile;
import abyssproductions.double0driver.GameObjects.ProjectileObjects.LaserBeamProjectile;
import abyssproductions.double0driver.GameObjects.ProjectileObjects.MachineGunProjectile;
import abyssproductions.double0driver.GameObjects.ProjectileObjects.MissileLauncherProjectile;
import abyssproductions.double0driver.GameObjects.ProjectileObjects.SpikeProjectile;
import abyssproductions.double0driver.R;

/**
 * Created by Mandip Sangha on 1/31/2017.
 * Lasted Edited by Mandip Sangha on 2/26/17
 */

public class Sprite extends GameObject {
    //  PURPOSE:    Holds the sprite's current health
    private int myHealth;
    //  PURPOSE:    Holds the sprite's max health
    private int myMaxHealth;
    //  PURPOSE:    Holds the dimension of the objects health bar
    private Rect myHealthBarDim;
    //  PURPOSE:    Holds whether to display the health bar or not
    private Boolean displayHealthBar;
    //  PURPOSE:    Used to readjust array when only single weapon is loaded
    private int weaponArrAdjustment;
    //  PURPOSE:    Holds an array of the different weapons
    private Weapon [] myWeapons;
    //  PURPOSE:    Holds the current active weapon
    private WeaponTypes myWeaponType;
    //  PURPOSE:    Holds the different types of weapons
    public enum WeaponTypes {MachineGun, Missile, Flamethrower, Laser, Spike}

    /*  PURPOSE:    Constructor for the sprite that sets the default values for the object and loads
                        the weapons if indicated
        INPUT:      image               - The image of the object
                    imageWidth          - The width of a single image in the image sheet
                    imageHeight         - The height of a single image in the image sheet
                    loadAllWeapons      - Holds whether to load all the weapons
        OUTPUT:     NONE
     */
    public Sprite(Bitmap image, int imageWidth, int imageHeight, Boolean loadAllWeapons) {
        super(image, imageWidth, imageHeight);
        myMaxHealth = myHealth = GameGlobals.getInstance().getImageResources().
                getInteger(R.integer.DefaultStartHealth);
        myHealthBarDim = new Rect((int)getDimensions().left,(int)getDimensions().centerY()-5,
                (int)getDimensions().right, (int)(int)getDimensions().centerY()+5);
        displayHealthBar = false;
        myWeapons = null;
        if(loadAllWeapons){
            myWeaponType = WeaponTypes.MachineGun;
            weaponArrAdjustment = 0;
            myWeapons = new Weapon[WeaponTypes.values().length];
            myWeapons[WeaponTypes.MachineGun.ordinal()] = new Weapon(0,0,GameGlobals.getInstance()
                    .getImageResources().getInteger(R.integer.DefaultMachineGunDelay),
                    new MachineGunProjectile());
            myWeapons[WeaponTypes.Missile.ordinal()] = new Weapon(0,0,GameGlobals.getInstance()
                    .getImageResources().getInteger(R.integer.DefaultMissileDelay),
                    new MissileLauncherProjectile());
            myWeapons[WeaponTypes.Flamethrower.ordinal()] = new Weapon(0,0,GameGlobals.getInstance()
                    .getImageResources().getInteger(R.integer.DefaultFlamethrowerDelay),
                    new FlameThrowerProjectile());
            myWeapons[WeaponTypes.Laser.ordinal()] = new Weapon(0,0,GameGlobals.getInstance()
                    .getImageResources().getInteger(R.integer.DefaultLaserDelay),
                    new LaserBeamProjectile());
            myWeapons[WeaponTypes.Spike.ordinal()] = new Weapon(0,0,GameGlobals.getInstance()
                    .getImageResources().getInteger(R.integer.DefaultSpikeDelay),
                    new SpikeProjectile());
        }
    }

    /*  PURPOSE:    Constructor for the sprite that sets the default values for the object and all
                        the different weapon types
        INPUT:      image               - The image of the object
                    imageWidth          - The width of a single image in the image sheet
                    imageHeight         - The height of a single image in the image sheet
        OUTPUT:     NONE
     */
    public Sprite(Bitmap image, int imageWidth, int imageHeight) {
        this(image, imageWidth, imageHeight, true);
    }

    /*  PURPOSE:    Loads a single weapon type that is given
        INPUT:      weapon              - The weapon type to load
        OUTPUT:     NONE
     */
    public void loadSingleWeapon(WeaponTypes weapon){
        myWeapons = new Weapon[1];
        myWeaponType = weapon;
        switch (weapon){
            case MachineGun:
                myWeapons[0] = new Weapon(0,0,GameGlobals.getInstance().getImageResources().
                        getInteger(R.integer.DefaultMachineGunDelay), new MachineGunProjectile());
                myWeaponType = WeaponTypes.MachineGun;
                weaponArrAdjustment = WeaponTypes.MachineGun.ordinal();
                break;
            case Missile:
                myWeapons[0] = new Weapon(0,0,GameGlobals.getInstance().getImageResources().
                        getInteger(R.integer.DefaultMissileDelay), new MissileLauncherProjectile());
                myWeaponType = WeaponTypes.Missile;
                weaponArrAdjustment = WeaponTypes.Missile.ordinal();
                break;
            case Flamethrower:
                myWeapons[0] = new Weapon(0,0,GameGlobals.getInstance().getImageResources().
                        getInteger(R.integer.DefaultFlamethrowerDelay), new FlameThrowerProjectile());
                myWeaponType = WeaponTypes.Flamethrower;
                weaponArrAdjustment = WeaponTypes.Flamethrower.ordinal();
                break;
            case Laser:
                myWeapons[0] = new Weapon(0,0,GameGlobals.getInstance().getImageResources().
                        getInteger(R.integer.DefaultLaserDelay),new LaserBeamProjectile());
                myWeaponType = WeaponTypes.Laser;
                weaponArrAdjustment = WeaponTypes.Laser.ordinal();
                break;
            case Spike:
                myWeapons[0] = new Weapon(0,0,GameGlobals.getInstance().getImageResources().
                        getInteger(R.integer.DefaultSpikeDelay),new SpikeProjectile());
                myWeaponType = WeaponTypes.Spike;
                weaponArrAdjustment = WeaponTypes.Spike.ordinal();
                break;
        }
    }

    /*  PURPOSE:    Draws the sprite's image to the screen
        INPUT:      canvas              - Pointer to the surface screen's canvas
        OUTPUT:     NONE
    */
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if(displayHealthBar) {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.RED);
            canvas.drawRect(myHealthBarDim, paint);
        }
    }

    /*  PURPOSE:    Updates the sprite's logic
        INPUT:      NONE
        OUTPUT:     NONE
     */
    @Override
    public void update() {
        super.update();
        if(myWeapons !=null) {
            for (Weapon w:myWeapons)if (w.sinceDelay > 0)w.sinceDelay--;
        }
        if(displayHealthBar) {
            myHealthBarDim.top = (int) getDimensions().centerY() - 5;
            myHealthBarDim.bottom = (int) getDimensions().centerY() + 5;
            myHealthBarDim.left = (int) getDimensions().left;
            float temp = getDimensions().left +
                    (getDimensions().width() * (float) (myHealth) / (float) (myMaxHealth));
            myHealthBarDim.right = (int) temp;
        }
    }

    /*  PURPOSE:    Reset the sprite back to the default values
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void reset(){
        myMaxHealth = myHealth = GameGlobals.getInstance().getImageResources().
                getInteger(R.integer.DefaultStartHealth);
        myWeaponType = WeaponTypes.MachineGun;
        for (Weapon W: myWeapons) {
            W.myProjectile.setDamageLevel(1);
            W.ammo = 0;
            W.maxAmmo = 0;
            W.sinceDelay = 0;
        }
    }

    /*  PURPOSE:    Turns health bar on and off
        INPUT:      activate            - Hold whether to activate the health bar or not
        OUTPUT:     NONE
     */
    public void displayHealthBar(boolean activate){
        displayHealthBar = activate;
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
        myHealth = (myHealth - decreaseBy < 0) ? 0 : myHealth - decreaseBy;
        if(myHealth <= 0 && getAniState() != GameGlobals.getInstance().getImageResources().
                getInteger(R.integer.DestroyAnimateState)){
            changeAniState(GameGlobals.getInstance().getImageResources().
                    getInteger(R.integer.DestroyAnimateState));
            GameGlobals.getInstance().mySoundEffects.playSoundEffect(GameGlobals.getInstance().
                    getImageResources().getInteger(R.integer.SEExplosionID),0);
        }
    }

    /*  PURPOSE:    Increase the sprite’s max health by amount given
        INPUT:      amount              - The amount to set max health
        OUTPUT:     NONE
     */
    public void increaseMaxHealth(int amount) {
        myMaxHealth += amount;
        myHealth = myMaxHealth;
    }

    /*  PURPOSE:    Increase the ammo amount for the give weapon type up to its max ammo
        INPUT:      weaponType          - The weapon type to increase the ammo for
                    increaseBy          - The amount to increase he ammo by
        OUTPUT:     NONE
     */
    public void increaseAmmo(WeaponTypes weaponType, int increaseBy) {
        if(myWeapons != null && increaseBy > 0){
            if((myWeapons.length == 1 && weaponType == myWeaponType)||(myWeapons.length > 1)){
                if (myWeapons[weaponType.ordinal() - weaponArrAdjustment].ammo +
                        increaseBy <= myWeapons[weaponType.ordinal() -weaponArrAdjustment].maxAmmo){
                    myWeapons[weaponType.ordinal() - weaponArrAdjustment].ammo += increaseBy;
                }else{
                    myWeapons[weaponType.ordinal() - weaponArrAdjustment].ammo =
                            myWeapons[weaponType.ordinal() -weaponArrAdjustment].maxAmmo;
                }
            }
        }
    }

    /*  PURPOSE:    Increase the max ammo by amount given for the given weapon type
        INPUT:      weaponType          - The weapon type to increase the ammo for
                    increaseBy          - The amount to set max ammo
        OUTPUT:     NONE
     */
    public void increaseMaxAmmo(WeaponTypes weaponType, int increaseBy) {
        if(myWeapons != null && increaseBy > 0){
            if((myWeapons.length == 1 && weaponType == myWeaponType)||(myWeapons.length > 1)) {
                myWeapons[weaponType.ordinal() - weaponArrAdjustment].maxAmmo += increaseBy;
            }
        }
    }

    /*  PURPOSE:    Increase the specified weapons damage to the given level
        INPUT:      weaponType          - The weapon type of the ammo amount to return
                    newLevel            - The weapon's new level
        OUTPUT:     NONE
     */
    public void increaseDamageLevel(WeaponTypes weaponType, int newLevel){
        if(myWeapons != null ) {
            if ((myWeapons.length == 1 && weaponType == myWeaponType) || (myWeapons.length > 1)) {
                if(newLevel > 0){
                    myWeapons[weaponType.ordinal()-weaponArrAdjustment].
                            myProjectile.setDamageLevel(newLevel);
                }
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
        return (myWeapons != null &&
                ((myWeapons.length == 1 && weaponType == myWeaponType)||(myWeapons.length > 1)))
                ? myWeapons[weaponType.ordinal()-weaponArrAdjustment].ammo : 0;
    }

    /*  PURPOSE:    Returns the amount of max ammo for the selected weapon type
        INPUT:      weaponType          - The weapon type of the max ammo amount to return
        OUTPUT:     Returns an int of the max ammo amount
     */
    public int getMaxAmmo(WeaponTypes weaponType) {
        return (myWeapons != null &&
                ((myWeapons.length == 1 && weaponType == myWeaponType)||(myWeapons.length > 1)))
                ? myWeapons[weaponType.ordinal()-weaponArrAdjustment].maxAmmo : 0;
    }

    /*  PURPOSE:    Returns the amount of damage for the selected weapon type
        INPUT:      weaponType          - The weapon type of the damage amount to return
        OUTPUT:     Returns an int of the damage amount
     */
    public int getWeaponDamage(WeaponTypes weaponType){
        return (myWeapons != null &&
                ((myWeapons.length == 1 && weaponType == myWeaponType)||(myWeapons.length > 1)))
                ? myWeapons[weaponType.ordinal()-weaponArrAdjustment].myProjectile.getMyDamage() : 0;
    }

    /*  PURPOSE:    Set's the weapon type of the current active weapon
        INPUT:      weaponType          - The weapon type to change too
        OUTPUT:     NONE
     */
    protected void setWeaponType(WeaponTypes weaponType) {
        myWeaponType = weaponType;
    }

    /*  PURPOSE:    Returns the current weapon type
        INPUT:      NONE
        OUTPUT:     Returns an WeaponType var of the current weapon type
     */
    protected WeaponTypes getWeaponType() {
        return myWeaponType;
    }

    /*  PURPOSE:    Fires a projectile from the X and Y position given
        INPUT:      x                   - The X position to launch the projectile from
                    y                   - The Y position to launch the projectile from
                    direction           - The direction that the projectile will fly
        OUTPUT:     NONE
     */
    protected void fire(float x, float y, int direction) {
        RectF temp = getDimensions();
        if(myWeapons != null) {
            if (myWeapons[myWeaponType.ordinal()-weaponArrAdjustment].ammo > 1 &&
                    myWeapons[myWeaponType.ordinal()-weaponArrAdjustment].sinceDelay == 0) {
                if (myWeaponType == WeaponTypes.MachineGun) {
                    myWeapons[myWeaponType.ordinal()-weaponArrAdjustment].myProjectile.launch
                            (x + (float)(temp.width()*.25), y, direction);
                    myWeapons[myWeaponType.ordinal()-weaponArrAdjustment].myProjectile.launch
                            (x - (float)(temp.width()*.25), y, direction);
                    myWeapons[myWeaponType.ordinal()-weaponArrAdjustment].ammo--;
                } else {
                    myWeapons[myWeaponType.ordinal()-weaponArrAdjustment].myProjectile.launch
                            (x, y, direction);
                }
                myWeapons[myWeaponType.ordinal()-weaponArrAdjustment].ammo--;
                myWeapons[myWeaponType.ordinal()-weaponArrAdjustment].sinceDelay =
                        myWeapons[myWeaponType.ordinal()-weaponArrAdjustment].delayFire;
            }
        }
    }

    //  Purpose:    Class hold information for the different weapons types
    private class Weapon {
        //  PURPOSE:    Holds the ammo amount
        public int ammo;
        //  PURPOSE:    Holds the max ammo amount
        public int maxAmmo;
        //  PURPOSE:    Holds the delay between the firing
        public int delayFire;
        //  PURPOSE:    Holds the time since delay
        public int sinceDelay;
        //  PURPOSE:    Holds the projectile for the weapon
        public Projectile myProjectile;

        /*  PURPOSE:    Constructor for the weapon that sets the default values for the object
            INPUT:      newAmmo             - The new ammo amount to set
                        newMaxAmmo          - The new max ammo amount to set
                        newDelayFire        - The new delay amount for the weapon
                        newProjectile       - The projectile for this type of weapon
            OUTPUT:     NONE
         */
        public Weapon(int newAmmo, int newMaxAmmo, int newDelayFire, Projectile newProjectile) {
            ammo = newAmmo;
            maxAmmo = newMaxAmmo;
            delayFire = newDelayFire;
            myProjectile = newProjectile;
            sinceDelay = 0;
        }
    }
}
