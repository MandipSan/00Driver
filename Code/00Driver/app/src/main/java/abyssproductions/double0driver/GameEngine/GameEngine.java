package abyssproductions.double0driver.GameEngine;

import android.graphics.Canvas;

import abyssproductions.double0driver.GameGlobals;
import abyssproductions.double0driver.GameObjects.Player;

/**
 * Created by Mandip Sangha on 2/14/2017.
 */

public class GameEngine {
    //  PURPOSE:    Holds the pointer to the player object
    private Player player;

    /** PURPOSE:    Constructor for the GameEngine that set the default value for the object
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    public GameEngine(){
        //player = new Player();
    }

    /** PURPOSE:    Updates the logic for the game
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    public void update(){
        for(int i = 0; i < GameGlobals.getInstance().myProjectiles.length; i++){
            if(GameGlobals.getInstance().myProjectiles[i]!=null)
                GameGlobals.getInstance().myProjectiles[i].update();
        }

        player.update();
    }

    /** PURPOSE:    Draws the whole game world
     *  INPUT:      canvas              - Pointer to the surface screen's canvas
     *  OUTPUT:     NONE
     */
    public void draw(Canvas canvas){
        for(int i = 0; i < GameGlobals.getInstance().myProjectiles.length; i++){
            if(GameGlobals.getInstance().myProjectiles[i]!=null)
                GameGlobals.getInstance().myProjectiles[i].draw(canvas);
        }

        player.draw(canvas);
    }


}
