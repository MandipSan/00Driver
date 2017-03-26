package abyssproductions.double0driver;

import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import abyssproductions.double0driver.GameEngine.GameSurfaceView;
import abyssproductions.double0driver.GameMenu.GameScreen;
import abyssproductions.double0driver.GameMenu.StartScreen;

public class MainActivity extends AppCompatActivity {
    private GameSurfaceView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_main, StartScreen.newInstance(), "StartScreen")
                    .commit();
        }

        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        gameView = new GameSurfaceView(this);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setContentView(gameView);//R.layout.activity_main);*/
    }

    /** PURPOSE:    Detects the touch inputs and returns the super methods event
     *  INPUT:      event               - Holds the type of event that happened
     *  OUTPUT:     Returns a boolean
     */
    @Override
    public boolean onTouchEvent(MotionEvent event){
        gameView.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        GameGlobals.getInstance().mySoundEffects.releaseSoundPool();
    }

    /** PURPOSE:    Used to change to the specified fragment
     *  INPUT:      fragTag             - Holds the tag for the fragment to change too
     *  OUTPUT:     Returns a boolean
     */
    public void changeFrags(String fragTag){
        /*getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main, GameScreen.newInstance(), fragTag)
                .addToBackStack(null)
                .commit();*/
    }
}
