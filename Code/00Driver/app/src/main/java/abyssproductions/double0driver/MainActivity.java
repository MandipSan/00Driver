package abyssproductions.double0driver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import abyssproductions.double0driver.GameEngine.GameSurfaceView;

public class MainActivity extends AppCompatActivity {
    private GameSurfaceView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameSurfaceView(this);
        setContentView(gameView);//R.layout.activity_main);
    }

    /** PURPOSE:    Detects the touch inputs and returns the super methods event
     *  INPUT:      event               - Holds the type of event that happened
     *  OUTPUT:     Returns a boolean
     */
    @Override
    public boolean onTouchEvent(MotionEvent event){
        gameView.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
