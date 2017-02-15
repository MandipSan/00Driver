package abyssproductions.double0driver.GameEngine;

import android.graphics.Canvas;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

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

        //player.update();
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

        //player.draw(canvas);
    }

    public class GameGestureListener extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onDown(MotionEvent event){
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX,
                               float velocityY){
            //The max difference in the swipe need
            int swipeMinDiff = 200;
            float rightSwipeDiff = event1.getX() - event2.getX();
            float leftSwipeDiff = event2.getX() - event1.getX();

            if(rightSwipeDiff >= swipeMinDiff){
                Log.d("Swiped","Right");
            }else if(leftSwipeDiff >= swipeMinDiff){
                Log.d("Swiped","Left");
            }

            return true;
        }
    }
}
