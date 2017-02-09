package abyssproductions.double0driver.GameObjects;



/**
 * Created by Mandip Sangha on 2/1/2017.
 * Edited by Mark Reffel on 2/9/2017
 */

public class Projectile extends GameObject {
    /*  PURPOSE:    Constructor for the projectile that sets the default values for the object
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public Projectile(int imageReference, int width, int height){
        super(imageReference, width, height);
    }

    /*  PURPOSE:    Updates the projectile's logic
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void update(){

    }

    /*  PURPOSE:    Launches the projectile from the X and Y position given 
        INPUT:      x                   - The X position to launch the projectile from
                    y                   - The Y position to launch the projectile from
        OUTPUT:     NONE
     */
    public void launched(float x, float y){

    }
}
