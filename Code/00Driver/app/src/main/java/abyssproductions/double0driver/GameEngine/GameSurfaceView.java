package abyssproductions.double0driver.GameEngine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.View;

import abyssproductions.double0driver.GameGlobals;
import abyssproductions.double0driver.GameMenu.GameScreen;
import abyssproductions.double0driver.Utilities.SoundEffects;

/**
 * Created by Mandip Sangha on 2/14/2017.
 */

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    //  PURPOSE:    Pointer to the thread that cause the game loop
    private GameThread gameThread;
    //  PURPOSE:    Pointer to the game engine
    private GameEngine gameEngine;
    //  PURPOSE:    The pointer that is used to detect gestures
    private GestureDetectorCompat mDetector;
    //  PURPOSE:    Holds the context passed to the view
    private Context contexts;
    //  PURPOSE:    Pointer for screenChange method implementation
    private ScreenChange screenChange;


    /** PURPOSE:    Constructor for the GameSurfaceView that set the default value for the view
     *  INPUT:      context             - The context from the activity
     *  OUTPUT:     NONE
     */
    public GameSurfaceView(Context context, GameScreen gameScreen){
        super(context);
        getHolder().addCallback(this);
        contexts = context;

        screenChange = (ScreenChange)gameScreen;
    }

    /** PURPOSE:    Starts the thread and set the other variable need when the surface is created
     *  INPUT:      holder              -
     *  OUTPUT:     NONE
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder){
        //Loads the game engine other need variable if the game engine wasn't loaded
        if(gameEngine == null){
            GameGlobals.getInstance().setScreenHeight(this.getHeight());
            GameGlobals.getInstance().setScreenWidth(this.getWidth());
            //if(GameGlobals.getInstance().mySoundEffects == null)GameGlobals.getInstance().mySoundEffects = new SoundEffects(contexts);
            gameEngine = new GameEngine();
            mDetector = new GestureDetectorCompat(contexts,gameEngine.new GameGestureListener());
            setUpTouchDetection();
        }
        //Starts the thread
        gameThread = new GameThread(getHolder(),this);
        gameThread.setGameRunning(true);
        gameThread.start();
    }

    /** PURPOSE:    Set the other variable need when the surface is changes
     *  INPUT:      holder              -
     *              format              -
     *              width               -
     *              height              -
     *  OUTPUT:     NONE
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
    }

    /** PURPOSE:    Stop the thread and set the other variable need when the surface is destroyed
     *  INPUT:      holder              -
     *  OUTPUT:     NONE
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean temp = true;
        //Stops the game loop
        gameThread.setGameRunning(false);
        //Waits for the thread to die and then sets the thread to null 
        while(temp){
            try{
                gameThread.join(0);
                temp = false;
            }catch ( InterruptedException e){

            }
        }
        gameThread = null;
    }

    /** PURPOSE:    Draws what is to be displayed on the screen
     *  INPUT:      canvas              - Pointer to the surface screen's canvas
     *  OUTPUT:     NONE
     */
    @Override
    public void draw(Canvas canvas){
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        gameEngine.draw(canvas);
    }

    /** PURPOSE:    Calls the game engine update
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    public void update(){
        gameEngine.update();
        if(gameEngine.getGameOver()) {
            screenChange.gameOver(gameEngine.getScore());
        }
        if(gameEngine.getUpgradeScreenActivated()){
            screenChange.sentUpgradeData(gameEngine.getUpgradeData());
        }
    }

    /** PURPOSE:    Sets up the touch detection
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    public void setUpTouchDetection(){
        this.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent event){
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    gameEngine.isPressed(true,event.getX(),event.getY());
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    gameEngine.isPressed(false,event.getX(),event.getY());
                }
                mDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    /** PURPOSE:    Reset the game
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    public void reset(){
        gameEngine.resetGame();
    }


    /** PURPOSE:    The data received from upgrades
     *  INPUT:      bundle              - The upgrade data
     *  OUTPUT:     NONE
     */
    public void receivedUpgradeData(Bundle bundle){
        gameEngine.setUpgradeData(bundle);
    }

    public interface ScreenChange {
        void gameOver(int score);
        void sentUpgradeData(Bundle bundle);
    }
}
