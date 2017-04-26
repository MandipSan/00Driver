package abyssproductions.double0driver;

import android.content.res.Resources;

import abyssproductions.double0driver.GameObjects.Enemy;
import abyssproductions.double0driver.GameObjects.Projectile;
import abyssproductions.double0driver.Utilities.GameImages;
import abyssproductions.double0driver.Utilities.SoundEffects;

/**
 * Created by Mandip Sangha on 2/3/2017.
 */
public class GameGlobals {
    //  PURPOSE:    Pointer to the apps resources
    private Resources imageResources;
    //  PURPOSE:    Creates the one instance of the class
    private static GameGlobals ourInstance = new GameGlobals();
    //  PURPOSE:    Holds the screen height
    private int screenHeight;
    //  PURPOSE:    Holds the screen width
    private int screenWidth;
    //  PURPOSE:    Holds the images for the game
    private GameImages images;
    //  PURPOSE:    Holds whether to display or not the mini health bar
    private boolean displayMiniHealthBar;
    //  PURPOSE:    Holds the stopping distance for vehicle from one and another
    private int stoppingDistance;
    //  PURPOSE:    Holds the firing distance need for the enemy to fire
    private int firingDistance;
    //  PURPOSE:    Holds the y velocity for all enemies
    public final static int enemiesUniVelocity = 10;
    //  PURPOSE:    Holds an array of the projectiles used by the enemies and player
    public Projectile [] myProjectiles;
    //  PURPOSE:    Hold the pointer to the game sound effects
    public SoundEffects mySoundEffects;

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
            images = new GameImages();
            myProjectiles = new Projectile[imageResources.getInteger(R.integer.ProjectileArraySize)];
            for(int i =0; i < myProjectiles.length; i++){
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

    /*  PURPOSE:    Set the screen height
        INPUT:      height              - The height of the screen
        OUTPUT:     NONE
     */
    public void setScreenHeight(int height){
        screenHeight = height;
    }

    /*  PURPOSE:    Set the screen width
        INPUT:      width               - The width of the screen
        OUTPUT:     NONE
     */
    public void setScreenWidth(int width){
        screenWidth = width;
    }

    /*  PURPOSE:    Set whether the mini health bar is active or not
        INPUT:      active              - Whether to active the mini health bar or not
        OUTPUT:     NONE
    */
    public void setDisplayMiniHealthBar(boolean active){
        displayMiniHealthBar = active;
    }

    /*  PURPOSE:    Set the stopping distance
        INPUT:      distance            - The distance
        OUTPUT:     NONE
     */
    public void setStoppingDistance(int distance){
        stoppingDistance = distance;
    }

    /*  PURPOSE:    Set the firing distance
        INPUT:      distance            - The distance
        OUTPUT:     NONE
     */
    public void setFiringDistance(int distance){
        firingDistance = distance;
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

    /*  PURPOSE:    Returns the screen height
        INPUT:      NONE
        OUTPUT:     Return int of the screen height
     */
    public int getScreenHeight(){
        return screenHeight;
    }

    /*  PURPOSE:    Returns the screen width
        INPUT:      NONE
        OUTPUT:     Return int of the screen width
     */
    public int getScreenWidth(){
        return screenWidth;
    }

    /*  PURPOSE:    Returns the whether mini health bar is active
        INPUT:      NONE
        OUTPUT:     Return boolean of displayMiniHealthBar
     */
    public boolean getDisplayMiniHealthBar(){
        return displayMiniHealthBar;
    }

    /*  PURPOSE:    Returns the stopping distance
        INPUT:      NONE
        OUTPUT:     Return int of the stopping distance
     */
    public int getStoppingDistance(){
        return stoppingDistance;
    }

    /*  PURPOSE:    Returns the firing distance
        INPUT:      NONE
        OUTPUT:     Return int of the firing distance
     */
    public int getFiringDistance(){
        return firingDistance;
    }

    /*  PURPOSE:    Returns the pointer to images
        INPUT:      NONE
        OUTPUT:     Return GameImages pointer
     */
    public GameImages getImages(){
        return images;
    }
}
