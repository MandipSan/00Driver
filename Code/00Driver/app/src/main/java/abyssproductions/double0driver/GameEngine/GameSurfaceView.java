package abyssproductions.double0driver.GameEngine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import abyssproductions.double0driver.GameGlobals;

/**
 * Created by Mandip Sangha on 2/14/2017.
 */

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private GameEngine gameEngine;

    /** PURPOSE:    Constructor for the GameSurfaceView that set the default value for the view
     *  INPUT:      context             - The context from the activity
     *  OUTPUT:     NONE
     */
    public GameSurfaceView(Context context){
        super(context);
        getHolder().addCallback(this);
        gameEngine = new GameEngine();
        gameThread = null;
        GameGlobals.getInstance().setImageResources(getResources());
    }

    /** PURPOSE:    Starts the thread and set the other variable need when the surface is created
     *  INPUT:      holder              -
     *  OUTPUT:     NONE
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder){
        if(gameThread == null){
            gameThread = new GameThread(getHolder(),this);
            gameThread.setGameRunning(true);
            gameThread.start();
        }else{
            gameThread.setGameRunning(true);
            gameThread.start();
        }
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
     *  INPUT:      holder             -
     *  OUTPUT:     NONE
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        gameThread.setGameRunning(false);
        gameThread.interrupt();
    }

    /** PURPOSE:    Draws what is to be displayed on the screen
     *  INPUT:      canvas             - Pointer to the surface screen's canvas
     *  OUTPUT:     NONE
     */
    @Override
    public void draw(Canvas canvas){
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        gameEngine.draw(canvas);
    }
}
