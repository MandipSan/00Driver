package abyssproductions.double0driver.GameObjects;

/**
 * Created by Mandip Sangha on 2/1/2017.
 */

public class Enemy extends Sprite {
    //  PURPOSE:    Holds the enemy's type
    private EnemyType myType;
    //  PURPOSE:    Holds the different type of enemies
    public enum EnemyType{BasicCar, MachineGunCar, DronePickup, SpikeVan, Helicopter}

    /*  PURPOSE:    Constructor for the basic enemy that sets the default values for the object
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public Enemy(){

    }

    /*  PURPOSE:    Updates the basic enemy's logic
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void update(){

    }

    /*  PURPOSE:    Spawn the enemy of the type given at the location given
        INPUT:      enemyType           - The type of enemy to spawn
                    x                   - The x location to spawn the enemy from
                    y                   - The y location to spawn the enemy from
        OUTPUT:     NONE
     */
    public void spawn(EnemyType enemyType, float x, float y){

    }
}
