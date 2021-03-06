package abyssproductions.double0driver.GameObjects;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.util.Log;

import java.util.Random;

import abyssproductions.double0driver.GameGlobals;
import abyssproductions.double0driver.GameObjects.ProjectileObjects.MachineGunProjectile;
import abyssproductions.double0driver.R;

/**
 * Created by Mandip Sangha on 2/1/2017.
 * Lasted Edited by Mandip Sangha on 2/26/17
 */

public class Enemy extends Sprite {
    //  PURPOSE:    The max amount of time that the helicopter does one movement pattern
    private final static int changeMovementMax = 15;
    //  PURPOSE:    Timer before helicopter changes it movement
    private int changeMovement;
    //  PURPOSE:    Holds the sound id for the ambulance playing sound effect
    private int ambulanceSEId;
    //  PURPOSE:    Used to help randomize helicopters movement
    private Random random;
    //  PURPOSE:    Holds the enemy's type
    private EnemyType myType;
    //  PURPOSE:    Holds whether the enemy can move
    private boolean movement;
    //  PURPOSE:    Holds the different type of enemies
    public enum EnemyType{BasicCar, MachineGunCar, DronePickup, SpikeVan, Helicopter, Ambulance,
        UpgradeTruck, AmmoTruck}

    /*  PURPOSE:    Constructor for the basic enemy that sets the default values for the object
                        and the point it is suppose to spawn from
        INPUT:      image               - The image of the object
                    imageWidth          - The width of a single image in the image sheet
                    imageHeight         - The height of a single image in the image sheet
                    enemyType           - The type of enemy to spawn
                    x                   - The x location to spawn the enemy from(The left in rect
                                            object)
                    y                   - The y location to spawn the enemy from(The top in rect
                                            object)
                    displayWidth        - The width that the object should have when displayed
                    displayHeight       - The height that the object should have when displayed
                    myLevel             - The level that the enemy is spawning at
        OUTPUT:     NONE
     */
    public Enemy(Bitmap image, int imageWidth, int imageHeight, EnemyType enemyType, float x,
                 float y, int displayWidth, int displayHeight, int myLevel){
        super(image, imageWidth, imageHeight, false);
        setMyAniDelayMax(GameGlobals.getInstance().getImageResources().
                getInteger(R.integer.DestroyAnimateState),2);
        changeMovement = 0;
        random = new Random();
        myType = enemyType;
        switch (myType){
            case MachineGunCar:
                loadSingleWeapon(WeaponTypes.MachineGun);
                break;
            case DronePickup:
                loadSingleWeapon(WeaponTypes.Missile);
                break;
            case SpikeVan:
                loadSingleWeapon(WeaponTypes.Spike);
                break;
        }
        increaseMaxAmmo(getWeaponType(),GameGlobals.getInstance().getImageResources()
                .getInteger(R.integer.EnemyDefaultAmmo));
        increaseAmmo(getWeaponType(),GameGlobals.getInstance().getImageResources()
                .getInteger(R.integer.EnemyDefaultAmmo));
        setEnemyLevel(myLevel);

        movement = true;
        //Resize object to current display limits
        resetWidthAndHeight(displayWidth, displayHeight);
        //Sets enemy velocity
        RectF temp = getDimensions();
        int velocity = GameGlobals.getInstance().getImageResources().getInteger(R.integer.EnemyYVelocity);
        if(enemyType != EnemyType.Helicopter) {
            if(y > (GameGlobals.getInstance().getScreenHeight()/2)){
                myVelocity.set(0,-1*velocity);
                temp.offsetTo(x,y);
            }else{
                myVelocity.set(0,velocity);
                setImageFlip();
                temp.offsetTo(x,y-temp.height());
            }
            setMyDimensions(temp);
        }else {
            temp.offsetTo(x,0-temp.bottom);
            setMyDimensions(temp);
            myVelocity.set(0,velocity);
        }

        if(myType == EnemyType.Ambulance)ambulanceSEId = GameGlobals.getInstance().mySoundEffects.
                playSoundEffect(GameGlobals.getInstance().getImageResources().
                        getInteger(R.integer.SEAmbulanceID),-1);
    }

    /*  PURPOSE:    Updates the basic enemy's logic
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void update(int playerX, int playerY){
        super.update();
        if(movement)move();
        if(getHealth() > 0) {
            if (myType == EnemyType.MachineGunCar || myType == EnemyType.DronePickup ||
                    myType == EnemyType.SpikeVan)
                fire(playerX, playerY);
        }else{
            if(myVelocity.y < 0)myVelocity.y *= -1;
        }
        movement = true;
    }

    /*  PURPOSE:    Fire the enemy projectiles
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void fire(int playerX, int playerY){
        RectF myDim = getDimensions();
        //TODO:TO BE DISCUSS FIRST THEN DESIGNED
        switch(myType){
            case MachineGunCar:
            case DronePickup:
                if(myDim.left <= playerX && myDim.right >= playerX
                        && Math.abs(myDim.centerY()-playerY) <=
                        GameGlobals.getInstance().getFiringDistance()){
                    if(myVelocity.y <= 0 && myDim.top > playerY){
                        fire(myDim.centerX(),myDim.top,-1);
                    }else if(myVelocity.y > 0 && myDim.top < playerY){
                        fire(myDim.centerX(),myDim.bottom,1);
                    }
                }
                break;
            case SpikeVan:
                if(myDim.left < playerX && myDim.right > playerX && myDim.centerY() < playerY
                        && Math.abs(myDim.centerY()-playerY) <=
                        GameGlobals.getInstance().getFiringDistance()){
                    if(myVelocity.y <= 0){
                        fire(myDim.left,myDim.bottom,1);
                    }else if(myVelocity.y > 0){
                        fire(myDim.left,myDim.top,-1);
                    }
                }
                break;
            case Helicopter:
                if(myDim.left < playerX && myDim.right > playerX){
                    setWeaponType(WeaponTypes.MachineGun);
                }else{
                    //TODO:Uncomment when merge to test with missile projectile NOTE:
                    //TODO: Still not decide if this will be kepted
                    //setWeaponType(WeaponTypes.Missile);
                }
                fire(myDim.centerX(),myDim.bottom,-1);
                fire(myDim.left,myDim.bottom,-1);
                fire(myDim.right,myDim.bottom,-1);
                break;
        }
    }

    /*  PURPOSE:    Set enemy's movement to stop
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void stopMovement(){
        movement = false;
    }

    /*  PURPOSE:    Kills the ambulance sound effect
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void killSoundEffect(){
        if (myType == EnemyType.Ambulance)GameGlobals.getInstance().mySoundEffects.stopSoundEffect(ambulanceSEId);
    }

    /*  PURPOSE:    Return's if the enemy is running
        INPUT:      NONE
        OUTPUT:     Return's a boolean of whether enemy is running or not
     */
    public boolean carRunning(){
        return movement;
    }

    /*  PURPOSE:    Return the enemy type
        INPUT:      NONE
        OUTPUT:     Return a EnemyType variable back with the enemy's type
     */
    public EnemyType getMyType(){
        return myType;
    }

    /*  PURPOSE:    Return's if the enemy is dead and the animation is finished
        INPUT:      NONE
        OUTPUT:     Return a boolean if the health equals zero and the animation is finished
     */
    public boolean isDead(){
        return getHealth() <= 0 && getDestroyedFinish();
    }

    /*  PURPOSE:    Return's if the enemy is dead and the animation is not finished
        INPUT:      NONE
        OUTPUT:     Return a boolean if the health equals zero and the animation is finished
     */
    public boolean isInDestroyState(){
        if(getHealth() <= 0 && !getDestroyedFinish()){
            GameGlobals.getInstance().mySoundEffects.stopSoundEffect(ambulanceSEId);
            return true;
        }
        return false;
    }

    /*  PURPOSE:    Enemy's movement logic
        INPUT:      NONE
        OUTPUT:     NONE
     */
    private void move(){
        RectF temp = getDimensions();
        //Use indicate if helicopter is fully on the screen
        boolean heliReady = false;
        if(myType != EnemyType.Helicopter){
            moveVertical(myVelocity.y);
        }else{
            //Stops the helicopter from making random movement until the helicopter is full on the
            // game screen
            if(temp.top >= 0){
                heliReady = true;
            }
            //Randomly picks the helicopter next movement pattern
            if(changeMovement <= 0 && heliReady){
                int velocity = GameGlobals.getInstance().getImageResources().getInteger(R.integer.EnemyYVelocity);
                int rand = random.nextInt(8);
                switch (rand){
                    //Stop
                    case 0:
                        myVelocity.set(0,0);
                        break;
                    //Move Right
                    case 1:
                        myVelocity.set(velocity, 0);
                        break;
                    //Move Left
                    case 2:
                        myVelocity.set(-velocity, 0);
                        break;
                    //Move Up
                    case 3:
                        myVelocity.set(0, -velocity);
                        break;
                    //Move Down
                    case 4:
                        myVelocity.set(0, velocity);
                        break;
                    //Move Up Right
                    case 5:
                        myVelocity.set(velocity,-velocity);
                        break;
                    //Move Up Left
                    case 6:
                        myVelocity.set(-velocity,-velocity);
                        break;
                    //Move Down Right
                    case 7:
                        myVelocity.set(velocity,velocity);
                        break;
                    //Move Down Left
                    case 8:
                        myVelocity.set(-velocity,velocity);
                        break;
                }
                changeMovement = changeMovementMax;
            }
            changeMovement--;
            //Checks to make sure helicopter isn't going to run from the screen anywhere
            if(temp.right+myVelocity.x > GameGlobals.getInstance().getScreenWidth() ||
                    temp.left+myVelocity.x < 0){
                int vel = myVelocity.y;
                myVelocity.set(0,vel);
            }
            if((temp.bottom+myVelocity.y > GameGlobals.getInstance().getScreenWidth() ||
                    temp.top+myVelocity.y < 0) && heliReady){
                int vel = myVelocity.x;
                myVelocity.set(vel,0);
            }

            moveHorizontal(myVelocity.x);
            moveVertical(myVelocity.y);
        }
    }

    /*  PURPOSE:    Sets the enemy current level
        INPUT:      newLevel            - The level that the enemy is at
        OUTPUT:     NONE
     */
    private void setEnemyLevel(int newLevel){
        increaseDamageLevel(getWeaponType(),newLevel);
        increaseMaxHealth(GameGlobals.getInstance().getImageResources()
                .getInteger(R.integer.EnemyDefaultHealth)*newLevel);
        increaseHealth(GameGlobals.getInstance().getImageResources()
                .getInteger(R.integer.EnemyDefaultHealth)*newLevel);
    }
}
