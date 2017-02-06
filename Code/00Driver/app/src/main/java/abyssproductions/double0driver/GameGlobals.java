package abyssproductions.double0driver;

import android.content.res.Resources;

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
    //  PURPOSE:    Holds the y velocity for all enemies
    public final static int enemiesUniVelocity = 5;

    /*  PURPOSE:    Constructor for the game globals that sets the default values for the object
        INPUT:      NONE
        OUTPUT:     NONE
     */
    private GameGlobals() {
        imageResources = null;
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
        INPUT:      height    - The height of the screen
        OUTPUT:     NONE
     */
    public void setScreenHeight(int height){
        screenHeight = height;
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
}
