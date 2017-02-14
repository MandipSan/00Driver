package abyssproductions.double0driver.GameEngine;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Mandip Sangha on 2/14/2017.
 */

public class GameThread extends Thread {
    //  PURPOSE:    Hold whether the game loop is running
    private boolean gameRunning;
    //  PURPOSE:    Hold the surface holder from the surface view
    private SurfaceHolder gameSurfaceHolder;
    //  PURPOSE:    Hold the surface view
    private GameSurfaceView gameView;

    /** PURPOSE:    Constructor for the GameThread that set the default value for the thread
     *  INPUT:      surHolder           - The surface's holder
     *              gView               - The game's surface view
     *  OUTPUT:     NONE
     */
    public GameThread(SurfaceHolder surHolder, GameSurfaceView gView){
        super();
        gameView = gView;
        gameSurfaceHolder = surHolder;
        gameRunning = false;
    }

    /** PURPOSE:    Sets the value for gameRunning
     *  INPUT:      setValue            - The value to set
     *  OUTPUT:     NONE
     */
    public void setGameRunning(boolean setValue){
        gameRunning = setValue;
    }

    /** PURPOSE:    Runs the game's game loop calling the update and draw methods
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    @Override
    public void run(){
        Canvas newCanvas = null;
        while(gameRunning){
            newCanvas = null;
            gameView.update();
            try{
                newCanvas = gameSurfaceHolder.lockCanvas(null);
                synchronized(gameSurfaceHolder) {
                    gameView.draw(newCanvas);
                }
            }finally {
                if(newCanvas != null){
                    gameSurfaceHolder.unlockCanvasAndPost(newCanvas);
                }
            }
            try {
                this.sleep(300);
            }catch (InterruptedException e) {
                return;
            } catch (Exception e) {
                return;
            }
        }
    }

    /** PURPOSE:    Return the value for gameRunning
     *  INPUT:      NONE
     *  OUTPUT:     Return a boolean of gameRunning
     */
    public boolean getGameRunning(){
        return gameRunning;
    }
}
