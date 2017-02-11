package abyssproductions.double0driver.GameObjects;

import android.graphics.RectF;

import abyssproductions.double0driver.GameGlobals;
import abyssproductions.double0driver.GameObjects.ProjectileObjects.MachineGunProjectile;

/**
 * Created by Mandip Sangha on 2/1/2017.
 * Edited by Mark Reffel on 2/9/2017
 */

public class Enemy extends Sprite {
    //  PURPOSE:    Holds the enemy's type
    private EnemyType myType;
    //  PURPOSE:    Holds the different type of enemies
    public enum EnemyType{BasicCar, MachineGunCar, DronePickup, SpikeVan, Helicopter}

    /*  PURPOSE:    Constructor for the basic enemy that sets the default values for the object
        INPUT:      imageReference      - Reference's the image to be load
                    imageWidth          - The width of a single image in the image sheet
                    imageHeight         - The height of a single image in the image sheet
        OUTPUT:     NONE
     */
    public Enemy(int imageReference, int width, int height){
        super(imageReference, width, height);
    }

    /*  PURPOSE:    Updates the basic enemy's logic
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void update(){
        super.update();
        if(myType != EnemyType.Helicopter){
            moveVertical(myVelocity.y);
        }else{
            moveHorizontal(myVelocity.x);
            moveVertical(myVelocity.y);
        }
    }

    /*  PURPOSE:    Spawn the enemy of the type given at the location given
        INPUT:      enemyType           - The type of enemy to spawn
                    x                   - The x location to spawn the enemy from(The left in rect
                                            object)
                    y                   - The y location to spawn the enemy from(The top in rect
                                            object)
        OUTPUT:     NONE
     */
    public void spawn(EnemyType enemyType, float x, float y){
        myType = enemyType;
        RectF temp = getDimensions();
        temp.offsetTo(x,y);
        setMyDimensions(temp);
        if(enemyType != EnemyType.Helicopter) {
            if(y < (GameGlobals.getInstance().getScreenHeight()/2)){
                myVelocity.set(0,-1*GameGlobals.enemiesUniVelocity);
            }else{
                myVelocity.set(0,GameGlobals.enemiesUniVelocity);
            }
        }else {
            //TODO:HELICOPTER SPAWNING STILL TO BE DESIGNED
        }
    }

    /*  PURPOSE:    Fire the enemy projectiles
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void fire(){
        //TODO:TO BE DISCUSS FIRST THEN DESIGNED
        switch(myType){
            case MachineGunCar:
                break;
            case DronePickup:
                break;
            case SpikeVan:
                break;
            case Helicopter:
                break;
        }
    }

    /*  PURPOSE:    Return the enemy type
        INPUT:      NONE
        OUTPUT:     Return a EnemyType variable back with the enemy's type
     */
    public EnemyType getMyType(){
        return myType;
    }
}
