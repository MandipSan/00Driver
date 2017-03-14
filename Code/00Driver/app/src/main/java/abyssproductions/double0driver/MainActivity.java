package abyssproductions.double0driver;

import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import abyssproductions.double0driver.GameEngine.GameSurfaceView;

public class MainActivity extends AppCompatActivity {
    private GameSurfaceView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        gameView = new GameSurfaceView(this);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setContentView(gameView);//R.layout.activity_main);
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
}
