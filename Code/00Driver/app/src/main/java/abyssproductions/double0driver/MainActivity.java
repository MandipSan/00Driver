package abyssproductions.double0driver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import abyssproductions.double0driver.GameEngine.GameSurfaceView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameSurfaceView(this));//R.layout.activity_main);
    }
}
