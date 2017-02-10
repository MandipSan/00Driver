package abyssproductions.double0driver;

import android.content.res.Resources;

import abyssproductions.double0driver.GameObjects.Projectile;

/**
 * Created by Mandip Sangha on 2/3/2017.
 */
public class GameGlobals {
    //  PURPOSE:    Pointer to the apps resources
    private Resources imageResources;
    //  PURPOSE:    Creates the one instance of the class
    private static GameGlobals ourInstance = new GameGlobals();
    //  PURPOSE:    Holds an array of the projectiles used by the enemies and player
    public Projectile [] myProjectiles;
    //  PURPOSE:    Holds the number of projectiles in the projectile's array
    public final static int myProjectileArrSize = 10;
    //  PURPOSE:    Holds the default player's horizontal velocity
    public final static int playerHorizontalVel = 5;
    //  PURPOSE:    Holds the distance of the lane transfers
    public final static int playerVelocityReset = 30;


    /*  PURPOSE:    Constructor for the game globals that sets the default values for the object
        INPUT:      NONE
        OUTPUT:     NONE
     */
    private GameGlobals() {
        imageResources = null;

    }

    /*  PURPOSE:    Loads the pointer variables only if imageResource is set and returns true else
                        returns false
        INPUT:      NONE
        OUTPUT:     Returns true if imageResources is load else false
     */
    public boolean loadPointers(){
        if(imageResources != null){
            myProjectiles = new Projectile[myProjectileArrSize];
            for(int i =0; i < myProjectileArrSize; i++){
                myProjectiles[i] = null;
            }
            return true;
        }
        return false;
    }

    /*  PURPOSE:    Set the image resource variable to a new resource (this is so that the app
                        resource can be access everywhere)
        INPUT:      newImageResource    - The new pointer to the app resources
        OUTPUT:     NONE
     */
    public void setImageResources(Resources newImageResources){
        imageResources = newImageResources;
    }

    /*  PURPOSE:    Returns the instance of the class
        INPUT:      NONE
        OUTPUT:     Return GameGlobals type instance
     */
    public static GameGlobals getInstance() {
        return ourInstance;
    }

    /*  PURPOSE:    Returns the pointer to the app resource
        INPUT:      NONE
        OUTPUT:     Return Resources type pointer
     */
    public Resources getImageResources(){
        return imageResources;
    }
}
