package abyssproductions.double0driver.GameEngine;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import abyssproductions.double0driver.GameGlobals;
import abyssproductions.double0driver.GameObjects.Player;
import abyssproductions.double0driver.R;

/**
 * Created by Mandip Sangha on 2/14/2017.
 */

public class GameEngine {
    //  PURPOSE:    Holds the pointer to the player object
    private Player player;
    //  PURPOSE:    Holds whether the fire button is pressed down
    private boolean playerFire;


    private HUD gHUD;

    /** PURPOSE:    Constructor for the GameEngine that set the default value for the object
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    public GameEngine(){
        GameGlobals.getInstance().loadPointers();
        //TODO:Value need to be changed
        player = new Player(R.drawable.test,50,50);
        RectF temp = player.getDimensions();
        temp.offset(500,500);
        player.setMyDimensions(temp);


        gHUD = new HUD();
        playerFire = false;

    }

    /** PURPOSE:    Updates the logic for the game
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    public void update(){
        gHUD.updateScore();

        for(int i = 0; i < GameGlobals.getInstance().myProjectiles.length; i++){
            if(GameGlobals.getInstance().myProjectiles[i]!=null) {
                GameGlobals.getInstance().myProjectiles[i].update();
                if(GameGlobals.getInstance().myProjectiles[i].getDimensions().top >= 1800 ||
                        GameGlobals.getInstance().myProjectiles[i].getDimensions().bottom <=0 ){
                    GameGlobals.getInstance().myProjectiles[i] = null;
                }
            }
        }

        if(playerFire)player.fireWeapon();
        player.update();

        gHUD.setHealthLevels(player.getHealth(),player.getMaxHealth());
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

        gHUD.draw(canvas);
    }

    /** PURPOSE:    Calls the players fire when the pressed is set true
     *  INPUT:      pressed             - Holds whether the screen is pressed
     *              x                   - The x point that was pressed
     *              y                   - The y point that was pressed
     *  OUTPUT:     NONE
     */
    public void isPressed(boolean pressed, float x, float y){
        playerFire = pressed && gHUD.buttonPressed(0,x,y);
        if(gHUD.buttonPressed(1,x,y))player.switchWeapon();
    }

    //  PURPOSE:    Class used to detected if the fling gesture occurred
    public class GameGestureListener extends GestureDetector.SimpleOnGestureListener{

        /** PURPOSE:    Calls the players movement method when fling
         *  INPUT:      event1          -
         *              event2          -
         *              velocityX       -
         *              velocityY       -
         *  OUTPUT:     Returns true
         */
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX,
                               float velocityY){
            //The max difference in the swipe need
            int swipeMinDiff = 200;
            float rightSwipeDiff = event1.getX() - event2.getX();
            float leftSwipeDiff = event2.getX() - event1.getX();

            if(rightSwipeDiff >= swipeMinDiff){
                player.moveLeft();
                Log.d("Swiped","Left");
            }else if(leftSwipeDiff >= swipeMinDiff){
                player.moveRight();
                Log.d("Swiped","Right");
            }

            return true;
        }
    }

    /** PURPOSE:    Checks the collision of the various game objects
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    private void checkCollision(){

    }


}
