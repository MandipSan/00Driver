package abyssproductions.double0driver.GameObjects;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.util.Log;

import java.util.Random;

import abyssproductions.double0driver.GameGlobals;
import abyssproductions.double0driver.GameObjects.ProjectileObjects.MachineGunProjectile;

/**
 * Created by Mandip Sangha on 2/1/2017.
 * Lasted Edited by Mandip Sangha on 2/26/17
 */

public class Enemy extends Sprite {
    //  PURPOSE:    The max amount of time that the helicopter does one movement pattern
    private final static int changeMovementMax = 15;
    //  PURPOSE:    Timer before helicopter changes it movement
    private int changeMovement;
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
        OUTPUT:     NONE
     */
    public Enemy(Bitmap image, int imageWidth, int imageHeight, EnemyType enemyType, float x, float y){
        super(image, imageWidth, imageHeight, false);
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
                loadSingleWeapon(WeaponTypes.Missile);
                break;
        }
        //TODO:Double check on final pass
        increaseMaxAmmo(getWeaponType(),1000);
        increaseAmmo(getWeaponType(),1000);
        increaseHealth(20);
        increaseMaxHealth(20);
        //TODO:^^^^^^^^^^^^^^^^^^^^^^^^^^^

        movement = true;
        //Sets enemy velocity
        RectF temp = getDimensions();
        if(enemyType != EnemyType.Helicopter) {

            if(y > (GameGlobals.getInstance().getScreenHeight()/2)){
                myVelocity.set(0,-1*GameGlobals.enemiesUniVelocity);
                temp.offsetTo(x,y);
            }else{
                myVelocity.set(0,GameGlobals.enemiesUniVelocity);
                setImageFlip();
                temp.offsetTo(x,y-imageHeight);
            }
            setMyDimensions(temp);
        }else {
            temp.offsetTo(x,0-temp.bottom);
            setMyDimensions(temp);
            myVelocity.set(0,GameGlobals.enemiesUniVelocity);
        }
    }

    /*  PURPOSE:    Updates the basic enemy's logic
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void update(int playerX, int playerY){
        super.update();
        if(getHealth() > 0) {
            if(movement)move();
            if (myType == EnemyType.MachineGunCar)fire(playerX, playerY);
        }
        movement =true;
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
                if(myDim.left <= playerX && myDim.right >= playerX
                        && Math.abs(myDim.centerY()-playerY) <=
                        GameGlobals.getInstance().getFiringDistance()){
                    if(myVelocity.y <= 0){
                        fire(myDim.centerX(),myDim.top,-1);
                    }else if(myVelocity.y > 0){
                        fire(myDim.centerX(),myDim.bottom,1);
                    }
                }
                break;
            case DronePickup:
                //TODO:Uncomment when merge to test with Drone projectile
                /*if(myDim.left < playerX && myDim.right > playerX){
                    if(myVelocity.y <= 0 && myDim.top > playerY){
                        fire(myDim.centerX(),myDim.centerY(),-1);
                    }else if(myVelocity.y > 0 && myDim.bottom < playerY){
                        fire(myDim.centerX(),myDim.centerY(),1);
                    }
                }*/
                break;
            case SpikeVan:
                //TODO:Uncomment when merge to test with Spike projectile
                /*if(myDim.left < playerX && myDim.right > playerX && myVelocity.y < 0
                    && myDim.top > playerY){
                    fire(myDim.centerX(),myDim.centerY(),-1);
                }*/
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

    /*  PURPOSE:    Return's if the enemy is dead
        INPUT:      NONE
        OUTPUT:     Return a boolean if the health equals zero
     */
    public boolean isDead(){
        return getHealth() <= 0 && getDestroyedFinish();
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
                int rand = random.nextInt(8);
                switch (rand){
                    //Stop
                    case 0:
                        myVelocity.set(0,0);
                        break;
                    //Move Right
                    case 1:
                        myVelocity.set(GameGlobals.enemiesUniVelocity, 0);
                        break;
                    //Move Left
                    case 2:
                        myVelocity.set(-GameGlobals.enemiesUniVelocity, 0);
                        break;
                    //Move Up
                    case 3:
                        myVelocity.set(0, -GameGlobals.enemiesUniVelocity);
                        break;
                    //Move Down
                    case 4:
                        myVelocity.set(0, GameGlobals.enemiesUniVelocity);
                        break;
                    //Move Up Right
                    case 5:
                        myVelocity.set(GameGlobals.enemiesUniVelocity,-GameGlobals.enemiesUniVelocity);
                        break;
                    //Move Up Left
                    case 6:
                        myVelocity.set(-GameGlobals.enemiesUniVelocity,-GameGlobals.enemiesUniVelocity);
                        break;
                    //Move Down Right
                    case 7:
                        myVelocity.set(GameGlobals.enemiesUniVelocity,GameGlobals.enemiesUniVelocity);
                        break;
                    //Move Down Left
                    case 8:
                        myVelocity.set(-GameGlobals.enemiesUniVelocity,GameGlobals.enemiesUniVelocity);
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
}
