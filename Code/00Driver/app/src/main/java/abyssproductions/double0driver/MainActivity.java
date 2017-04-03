package abyssproductions.double0driver;

import android.support.v4.app.BundleCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import abyssproductions.double0driver.GameEngine.GameSurfaceView;
import abyssproductions.double0driver.GameMenu.CreditScreen;
import abyssproductions.double0driver.GameMenu.GameScreen;
import abyssproductions.double0driver.GameMenu.HelpScreen;
import abyssproductions.double0driver.GameMenu.HighScoreScreen;
import abyssproductions.double0driver.GameMenu.SettingScreen;
import abyssproductions.double0driver.GameMenu.StartScreen;
import abyssproductions.double0driver.GameMenu.UpgradeScreen;

public class MainActivity extends AppCompatActivity {
    //  PURPOSE:    Pointer to the start screen fragment
    private StartScreen startScreen;
    //  PURPOSE:    Pointer to the game screen fragment
    private GameScreen gameScreen;
    //  PURPOSE:    Pointer to the credit screen fragment
    private CreditScreen creditScreen;
    //  PURPOSE:    Pointer to the help screen fragment
    private HelpScreen helpScreen;
    //  PURPOSE:    Pointer to the setting screen fragment
    private SettingScreen settingScreen;
    //  PURPOSE:    Pointer to the upgrade screen fragment
    private UpgradeScreen upgradeScreen;
    //  PURPOSE:    Pointer to the high score screen fragment
    private HighScoreScreen highScoreScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startScreen = StartScreen.newInstance();
        gameScreen = GameScreen.newInstance();
        creditScreen = CreditScreen.newInstance();
        helpScreen = HelpScreen.newInstance();
        settingScreen = SettingScreen.newInstance();
        highScoreScreen = highScoreScreen.newInstance();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_main, startScreen, "StartScreen")
                    .commit();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        GameGlobals.getInstance().mySoundEffects.releaseSoundPool();
    }

    /** PURPOSE:    Used to change to the specified fragment
     *  INPUT:      fragTag             - Holds the tag for the fragment to change too
     *  OUTPUT:     NONE
     */
    public void changeFrags(String fragTag){
        changeFrags(fragTag,null);
    }

    /** PURPOSE:    Used to change to the specified fragment
     *  INPUT:      fragTag             - Holds the tag for the fragment to change too
     *              bundle              - Holds data passed between fragments
     *  OUTPUT:     NONE
     */
    public void changeFrags(String fragTag, Bundle bundle){
        Fragment temp = null;
        switch (fragTag){
            case "StartScreen":
                temp = startScreen;
                break;
            case "GameScreen":
                temp = gameScreen;
                break;
            case "CreditScreen":
                temp = creditScreen;
                break;
            case "HelpScreen":
                temp = helpScreen;
                break;
            case "SettingScreen":
                temp = settingScreen;
                break;
            case "UpgradeScreen":
                temp = upgradeScreen;
                break;
            case "HighscoreScreen":
                highScoreScreen.passData(bundle);
                temp = highScoreScreen;
                break;
        }
        if(temp != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_main, temp, fragTag)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
