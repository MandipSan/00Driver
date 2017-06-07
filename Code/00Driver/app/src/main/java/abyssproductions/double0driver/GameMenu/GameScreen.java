package abyssproductions.double0driver.GameMenu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import abyssproductions.double0driver.GameEngine.GameSurfaceView;
import abyssproductions.double0driver.GameGlobals;
import abyssproductions.double0driver.MainActivity;
import abyssproductions.double0driver.R;
import abyssproductions.double0driver.Utilities.Music;
import abyssproductions.double0driver.Utilities.SoundEffects;

/**
 * Created by Mandip Sangha on 3/25/2017.
 */


public class GameScreen extends Fragment implements GameSurfaceView.ScreenChange {
    private GameSurfaceView game;
    private ViewGroup layout;
    private Boolean resetGame;

    public static GameScreen newInstance(){
        return new GameScreen();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        if(game == null) {
            resetGame = false;
            View view = inflater.inflate(R.layout.game_screen,container,false);
            layout = (ViewGroup)view.findViewById(R.id.game_screen);
            if(GameGlobals.getInstance().mySoundEffects == null)GameGlobals.getInstance().mySoundEffects = new SoundEffects(getContext());
            if(GameGlobals.getInstance().myMusic == null)GameGlobals.getInstance().myMusic = new Music(getContext());
            game = new GameSurfaceView(getContext(), this);
            game.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            layout.addView(game);
        }else{
            if(resetGame){
                game.reset();
                resetGame = false;
            }
        }
        GameGlobals.getInstance().myMusic.playMusic(GameGlobals.getInstance().getImageResources().getInteger(R.integer.MGameMusic));
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        GameGlobals.getInstance().mySoundEffects.resumeAllSoundEffect();
    }

    @Override
    public void onPause(){
        super.onPause();
        GameGlobals.getInstance().mySoundEffects.pauseAllSoundEffect();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }

    @Override
    public void gameOver(int score){
        Bundle bundle = new Bundle();
        bundle.putInt("score",score);
        ((MainActivity)getActivity()).changeFrags("HighscoreScreen",bundle);
    }

    @Override
    public void sentUpgradeData(Bundle bundle){
        ((MainActivity)getActivity()).changeFrags("UpgradeScreen",bundle);
    }

    public void passData(Bundle bundle){
        if(bundle == null){
            resetGame = true;
        }else {
            game.receivedUpgradeData(bundle);
        }
    }
}
